package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private boolean errorDetected = false;
	private int varCount;
	private int paramCnt;
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		log.info(msg.toString());
	}
	
	public boolean isErrorDetected() {
		return errorDetected;
	}
	
	@Override
	public void visit(MethodTypeName MethodTypeName) {
		if ("main".equalsIgnoreCase(MethodTypeName.getMethodName())) {
			mainPc = Code.pc;
		}
		MethodTypeName.obj.setAdr(Code.pc);
		
		// Collect arguments and local variables.
		SyntaxNode methodNode = MethodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}
	
	@Override
	public void visit(Var Var)
	{
		if (Var.getOptValueAssign().struct != Tab.noType)
		{
				Code.store(Var.obj);
		}
	}
	
	@Override
	public void visit(FuncCall FuncCall)
	{
		Obj func = FuncCall.getDesignator().obj;
		
		int pcRelativeFuncAdr = func.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(pcRelativeFuncAdr);
	}
	
	@Override
	public void visit(StmtFuncCall StmtFuncCall)
	{
		if (StmtFuncCall.getFuncCall().getDesignator().obj.getType() != Tab.noType)
		{
			// if method should return some value (non void type)
			// we are popping this not to stay on stack
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(VarDecl VarDecl) {
		varCount++;
	}

	@Override
	public void visit(FormalParamDecl FormalParam) {
		paramCnt++;
	}	
	
	@Override
	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnExpr ReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(PrintStmt PrintStmt) {
		if(PrintStmt.getExpr().struct == Tab.intType)
		{
			Code.put(Code.const_5);
			Code.put(Code.print);
		} else if (PrintStmt.getExpr().struct == Tab.charType)
		{
			Code.put(Code.const_1);
			Code.put(Code.bprint);
		}
		
	}
	
	@Override
	public void visit(ReadStmt ReadStmt)
	{
		if (ReadStmt.getDesignator().obj.getType() == Tab.charType)
		{
			Code.put(Code.bread);
		} else
		{
			Code.put(Code.read);
		}
		Code.store(ReadStmt.getDesignator().obj);
	}
	
	@Override
	public void visit(AssignmentStmt AssignmentStmt)
	{
		Code.store(AssignmentStmt.getDesignator().obj);
	}
	
	@Override
	public void visit(PostfixStmt PostfixStmt)
	{
		// Pop the value from stack
		Code.put(Code.pop);
	}
	
	@Override
	public void visit(DecOperation DecOperation)
	{
		Code.load(DecOperation.getDesignator().obj);
		Code.load(DecOperation.getDesignator().obj);
		Code.put(Code.const_m1);
		Code.put(Code.add);
		Code.store(DecOperation.getDesignator().obj);
	}
	
	@Override
	public void visit(IncOperation IncOperation)
	{
		Code.load(IncOperation.getDesignator().obj);
		Code.load(IncOperation.getDesignator().obj);
		Code.put(Code.const_1);
		Code.put(Code.add);
		Code.store(IncOperation.getDesignator().obj);
	}
	
	@Override
	public void visit(NoPosfixOperation NoPosfixOperation)
	{
		Code.load(NoPosfixOperation.getDesignator().obj);
	}
	
	@Override
	public void visit(TermAddops TermAddops) {
		Code.put(GetAddInstruction(TermAddops.getAddop().obj));
	}
	
	@Override
	public void visit(FactorMulops FactorMulops)
	{
		Code.put(GetMulInstruction(FactorMulops.getMulop().obj));
	}
	
	int GetMulInstruction(Obj MulopObj)
	{
		switch(MulopObj.getName())
		{
		case "mul":
			return Code.mul;
		case "div":
			return  Code.div;
		case "mod":
			return Code.rem;
		}
		report_error("Generating code error, specified mulop operation not found",null);
		return 0;
	}
	
	int GetAddInstruction(Obj AddopObj)
	{
		switch(AddopObj.getName())
		{
		case "add":
			return Code.add;
		case "sub":
			return Code.sub;
		}
		report_error("Generating code error, specified addop operation not found",null);
		return 0;
	}
	
	@Override
	public void visit(AddopAdd AddopAdd)
	{
		AddopAdd.obj = new Obj(Obj.NO_VALUE, "add", Tab.noType);
	}
	@Override
	public void visit(AddopSub AddopSub)
	{
		AddopSub.obj = new Obj(Obj.NO_VALUE, "sub", Tab.noType);
	}
	@Override
	public void visit(MulopMul MulopMul)
	{
		MulopMul.obj = new Obj(Obj.NO_VALUE, "mul", Tab.noType);
	}
	@Override
	public void visit(MulopDiv MulopDiv)
	{
		MulopDiv.obj = new Obj(Obj.NO_VALUE, "div", Tab.noType);
	}
	@Override
	public void visit(MulopMod MulopMod)
	{
		MulopMod.obj = new Obj(Obj.NO_VALUE, "mod", Tab.noType);
	}
	
	@Override
	public void visit(ConstNum ConstNum)
	{
		Obj constNum = Tab.insert(Obj.Con, "", Tab.intType);
		constNum.setAdr(ConstNum.getNumm());
		Code.load(constNum);
	}
	
	@Override
	public void visit(ConstChar ConstChar)
	{
		Obj constChar = Tab.insert(Obj.Con, ""+ConstChar.getCharr(), Tab.charType);
		constChar.setAdr(ConstChar.getCharr());
		Code.load(constChar);
	}
	
	@Override
	public void visit(ConstBool ConstBool)
	{
		Obj constBool = Tab.insert(Obj.Con, ""+ConstBool.getBooll(), new Struct(Struct.Bool));
		constBool.setAdr(ConstBool.getBooll()?1:0);
		Code.load(constBool);
	}
	
	/*
	@Override
	public void visit(ReturnNoExpr ReturnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Assignment Assignment) {
		Code.store(Assignment.getDesignator().obj);
	}
	
	@Override
	public void visit(Const Const) {
		Code.load(new Obj(Obj.Con, "$", Const.struct, Const.getN1(), 0));
	}
	
	@Override
	public void visit(Designator Designator) {
		SyntaxNode parent = Designator.getParent();
		if (Assignment.class != parent.getClass() && FuncCall.class != parent.getClass()) {
			Code.load(Designator.obj);
		}
	}
	*/
}
