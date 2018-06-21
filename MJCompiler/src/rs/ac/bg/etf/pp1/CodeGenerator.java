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
	private boolean loadingArray = false;
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
	public void visit(Program Program)
	{
		// Debug Only:
		OptVarDeclList ovdl = Program.getOptVarDeclList();
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
	public void visit(NewType NewType)
	{
		// if our type is array
		if (NewType.getOptArray().struct != Tab.noType)
		{
			Code.put(Code.newarray); 
            Code.put(NewType.getType().struct == Tab.charType ? 0  : 1);  
		}
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
		//if (AssignmentStmt.getDesignator().obj.getKind() != Obj.Elem)
		{
			Code.store(AssignmentStmt.getDesignator().obj);
		}/* 
		else
		{			
			// At this point on stack are already address of object
			// index of element, and value of expression
			Code.put(Code.astore);
		}*/
	}
	
	@Override
	public void visit(PostfixStmt PostfixStmt)
	{
		// Pop the value from stack
		Code.put(Code.pop);
	}
	
	@Override
	public void visit(DesigName DesigName)
	{
		Designator desigParent = (Designator)DesigName.getParent();
		SyntaxNode parent = DesigName.getParent().getParent();
		boolean isAssignment = AssignmentStmt.class == parent.getClass();
		boolean isElementObjectKind = desigParent.obj.getKind() == Obj.Elem;
		boolean hasArrayAccessing = desigParent.getOptArray().struct != Tab.noType;
		boolean isFunctionCall = FuncCall.class == parent.getClass();
		boolean isReadStmt = ReadStmt.class == parent.getClass();
		
		if (!isFunctionCall && !isReadStmt &&
			(!isAssignment || (isElementObjectKind && hasArrayAccessing)))
		{
			Code.load(DesigName.obj);
		}
	}
	
	
	@Override
	public void visit(Designator Designator)
	{
		
		if (AssignmentStmt.class != Designator.getParent().getClass() && 
				Designator.obj.getKind() == Obj.Elem && 
				Designator.getOptArray().struct != Tab.noType)
		{
			// At this point both array address and index are on stack
			// if it is not assign operation, we should read value
			// from provided address (Array)
			Code.load(Designator.obj);
			
			// If parent operation is postfix operation, we should duplicate
			// array address and index in order to know where to store resulting
			// operation side effect
			if (Designator.getParent().getClass() == DecOperation.class ||
					Designator.getParent().getClass() == IncOperation.class)
			{
				/*
				// before stackBottom: Adr, Idx
				Code.put(Code.dup2);
				// after Adr, Idx, Adr, Idx
				// one used for reading, other one for writing
				 */
			}

		}
		/*
		SyntaxNode parent = Designator.getParent();
		if ( (AssignmentStmt.class != parent.getClass() || 
				(Designator.obj.getKind() == Obj.Elem && 
				Designator.getOptArray().struct != Tab.noType)) &&
				FuncCall.class != parent.getClass() &&
				ReadStmt.class != parent.getClass())
		{
			Code.load(Designator.obj);
		}*/
		/*
		// In semantic pass we save in Designator.obj element of array if
		// it is being indexed.
		if (Designator.obj.getKind() == Obj.Elem && 
				Designator.getOptArray().struct != Tab.noType)
		{
			// Now we will need address of this array and index in order
			// to read or write to it. At this point index is already on stack
			// so we are putting also address of an array and swapping their
			// places, as expected from astore instruction. We will get value
			// from expression later
			Obj array = Tab.find(Designator.getDesignatorName());
			if (array == null || array.getType().getKind() != Struct.Array)
			{
				report_error("Code Generation Error: Unexpected indexing of non array, this "+
							"should have been caught in Semantic Segmentation - some assumptions are broken now",null);
			}
			Code.put2(array.getAdr());
			
			// Reversing order of parameters on stack (Adr, Index instead of Index, Adr)
			// Code.put(Code.dup2);
			// Code.put(Code.pop);
		}*/
		
	}
	
	@Override
	public void visit(DecOperation DecOperation)
	{
		// Duplicate value on the stack.
		// Note: we should not load designator object again, because in case
		// that designator is array, we will be expecting two more arguments on stack
		// and will be calling aload which we want to avoid
		Code.put(Code.dup);
		Code.put(Code.const_m1);
		Code.put(Code.add);
		
		if (DecOperation.getDesignator().obj.getKind() == Obj.Elem)
		{
			// in case of array, we are having following situation
			// StackBottom: Adr, Idx, Val, DecVal
			// What we want is Val, Adr, Idx, DecVal
			// Operation dup_x2 from http://ir4pp1.etf.rs/Domaci/2017-2018/mikrojava_1718_jan.pdf
			// would be useful but I cannot find it =D
			// So we will do it manually:
			
			// We will store current values from stack
			int Adr, Idx, Val, DecVal;
			DecVal = Code.buf[Code.pc-1];
			Val = Code.buf[Code.pc-2];
			Idx = Code.buf[Code.pc-3];
			Adr = Code.buf[Code.pc-4];
			
			int Adrr, Idxx, Vall, DecVall;
			// Pop them all
			Adrr = Code.get2(Code.pc-1);
			Code.put(Code.pop);
			Idxx = Code.get2(Code.pc-1);
			Code.put(Code.pop);
			Vall = Code.get2(Code.pc-1);
			Code.put(Code.pop);
			DecVall = Code.get2(Code.pc-1);
			Code.put(Code.pop);
			
			// Load them the way we want.
			Code.loadConst(Val);
			Code.loadConst(Adr);
			Code.loadConst(Idx);
			Code.loadConst(DecVal);
			// Code.buf[Code.pc-1] = (byte) Idx;
			// Code.buf[Code.pc-2] = (byte) Adr;
			// Code.buf[Code.pc-3] = (byte) Val;
			
		}
		
		Code.store(DecOperation.getDesignator().obj);
	}
	
	@Override
	public void visit(IncOperation IncOperation)
	{
		// Duplicate value on the stack.
		// Note: we should not load designator object again, because in case
		// that designator is array, we will be expecting two more arguments on stack
		// and will be calling aload which we want to avoid
		Code.put(Code.dup);
		Code.put(Code.const_1);
		Code.put(Code.add);
		
		//if (IncOperation.getDesignator().obj.getKind() != Obj.Elem)
		{
			Code.store(IncOperation.getDesignator().obj);
		}/* else
		{			
			// At this point on stack are already address of object
			// index of element, and value of expression
			Code.put(Code.astore);
		}*/
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
		
		Code.put(Code.trap);
		Code.put(1);
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
		
		Code.put(Code.trap);
		Code.put(1);
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
}
