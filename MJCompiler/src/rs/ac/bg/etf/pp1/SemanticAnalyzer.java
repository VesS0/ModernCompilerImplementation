package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {
	boolean errorDetected = false;
	Obj currentMethod = null;
	Struct currentVarDeclTypeStruct = null;
	boolean returnFound = false;
	int nVars;
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		// if (line != 0)
			//msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		//if (line != 0)
			// msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean isErrorDetected() {
		return errorDetected;
	}
	
	@Override
	public void visit(ProgramName ProgramName) {
		ProgramName.obj = Tab.insert(Obj.Prog, ProgramName.getPName(), Tab.noType);
		Tab.openScope(); 
	}

	@Override
	public void visit(Program Program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(Program.getProgramName().obj);
		Tab.closeScope();
	}

	@Override
	public void visit(ExprOrError ExprOrError) {
		// ExprOrError.struct = ExprOrError.getExpr();
	}

	@Override
	public void visit(OptFormalParamList OptFormalParamList) {
		// TODO Auto-generated method stub
		super.visit(OptFormalParamList);
	}

	@Override
	public void visit(Mulop Mulop) {
		// TODO Auto-generated method stub
		super.visit(Mulop);
	}

	@Override
	public void visit(OptConst OptConst) {
		// TODO Auto-generated method stub
		super.visit(OptConst);
	}

	@Override
	public void visit(OptArrayBrackets OptArrayBrackets) {
		// TODO Auto-generated method stub
		super.visit(OptArrayBrackets);
	}

	@Override
	public void visit(OptArray OptArray) {
		// TODO Auto-generated method stub
		super.visit(OptArray);
	}

	@Override
	public void visit(OptArgumentParamList OptArgumentParamList) {
		// TODO Auto-generated method stub
		super.visit(OptArgumentParamList);
	}

	@Override
	public void visit(SemiCommaVarList SemiCommaVarList) {
		// TODO Auto-generated method stub
		super.visit(SemiCommaVarList);
	}

	@Override
	public void visit(Addop Addop) {
		// TODO Auto-generated method stub
		super.visit(Addop);
	}

	@Override
	public void visit(OptVarDeclList OptVarDeclList) {
		// TODO Auto-generated method stub
		super.visit(OptVarDeclList);
	}

	@Override
	public void visit(OptStatementList OptStatementList) {
		// TODO Auto-generated method stub
		super.visit(OptStatementList);
	}

	@Override
	public void visit(OptBrackExpr OptBrackExpr) {
		// TODO Auto-generated method stub
		super.visit(OptBrackExpr);
	}

	@Override
	public void visit(Factor Factor) {
		// TODO Auto-generated method stub
		super.visit(Factor);
	}

	@Override
	public void visit(VarList VarList) {
		// TODO Auto-generated method stub
		super.visit(VarList);
	}

	@Override
	public void visit(TermAddopList TermAddopList) {
		// TODO Auto-generated method stub
		super.visit(TermAddopList);
	}

	@Override
	public void visit(Designator Designator) {
		Obj obj = Tab.find(Designator.getDesignatorName());

		if (Tab.noObj == obj)
		{
			report_error(
					"Semantic Error on line " + Designator.getLine() +
					" : Name \"" + Designator.getDesignatorName() +
					"\" is not declared! ", Designator);
			Designator.obj = Tab.noObj;
			return;
		} 
		/*else if (Struct.Array == obj.getType().getKind() && !Designator.getOptArray().struct.getType().getKind())
		{
			report_error(
					"Semantic Error on line " + Designator.getLine() +
					" : Name " + Designator.getDesignatorName() +
					" is an array ", Designator);
			return;
		}*/
		Designator.obj = obj;
	}

	@Override
	public void visit(ArgumentParamList ArgumentParamList) {
		// TODO Auto-generated method stub
		super.visit(ArgumentParamList);
	}

	@Override
	public void visit(OptCommaNumber OptCommaNumber) {
		// TODO Auto-generated method stub
		super.visit(OptCommaNumber);
	}

	@Override
	public void visit(FormalParam FormalParam) {
		// TODO Auto-generated method stub
		super.visit(FormalParam);
	}

	@Override
	public void visit(FormalParamList FormalParamList) {
		// TODO Auto-generated method stub
		super.visit(FormalParamList);
	}

	@Override
	public void visit(Expr Expr) {
		// TODO Auto-generated method stub
		super.visit(Expr);
	}

	@Override
	public void visit(DesignatorStatement DesignatorStatement) {
		// TODO Auto-generated method stub
		super.visit(DesignatorStatement);
	}

	@Override
	public void visit(Const Const) {
		// TODO Auto-generated method stub
		super.visit(Const);
	}

	@Override
	public void visit(FactorMulopList FactorMulopList) {
		// TODO Auto-generated method stub
		super.visit(FactorMulopList);
	}

	@Override
	public void visit(OptMethodDeclList OptMethodDeclList) {
		// TODO Auto-generated method stub
		super.visit(OptMethodDeclList);
	}

	@Override
	public void visit(Statement Statement) {
		// TODO Auto-generated method stub
		super.visit(Statement);
	}

	@Override
	public void visit(OptPostfixOperation OptPostfixOperation) {
		// TODO Auto-generated method stub
		super.visit(OptPostfixOperation);
	}

	@Override
	public void visit(ArgumentParam ArgumentParam) {
		// TODO Auto-generated method stub
		super.visit(ArgumentParam);
	}

	@Override
	public void visit(MultipleArgumentParams MultipleArgumentParams) {
		// TODO Auto-generated method stub
		super.visit(MultipleArgumentParams);
	}

	@Override
	public void visit(NoArgumentParams NoArgumentParams) {
		// TODO Auto-generated method stub
		super.visit(NoArgumentParams);
	}

	@Override
	public void visit(ArgumentParams ArgumentParams) {
		// TODO Auto-generated method stub
		super.visit(ArgumentParams);
	}

	@Override
	public void visit(NoStatement NoStatement) {
		// TODO Auto-generated method stub
		super.visit(NoStatement);
	}

	@Override
	public void visit(StatementList StatementList) {
		// TODO Auto-generated method stub
		super.visit(StatementList);
	}

	@Override
	public void visit(FormalParamDecl FormalParamDecl) {
		// TODO Auto-generated method stub
		super.visit(FormalParamDecl);
	}

	@Override
	public void visit(SingleFormalParamDecl SingleFormalParamDecl) {
		// TODO Auto-generated method stub
		super.visit(SingleFormalParamDecl);
	}

	@Override
	public void visit(FormalParamDeclList FormalParamDeclList) {
		// TODO Auto-generated method stub
		super.visit(FormalParamDeclList);
	}

	@Override
	public void visit(NoFormalParam NoFormalParam) {
		// TODO Auto-generated method stub
		super.visit(NoFormalParam);
	}

	@Override
	public void visit(FormalParamListOpt FormalParamListOpt) {
		// TODO Auto-generated method stub
		super.visit(FormalParamListOpt);
	}

	@Override
	public void visit(MethodTypeName MethodTypeName) {
		currentMethod = Tab.insert(Obj.Meth, MethodTypeName.getMethodName(), MethodTypeName.getType().struct);

		MethodTypeName.obj  = currentMethod;
		Tab.openScope();
		report_info("Processing function " + MethodTypeName.getMethodName(), MethodTypeName);
	}

	@Override
	public void visit(MethodDecl MethodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semantic Error on line " + MethodDecl.getLine() + ": function \"" + currentMethod.getName() + "\" doesn't have return expression!", MethodDecl);
			return;
		}
		
		assert(MethodDecl.getMethodTypeName().obj == currentMethod)
			: "currentMethod should be same as object in MethodTypeName";
		
		Tab.chainLocalSymbols(MethodDecl.getMethodTypeName().obj);
		Tab.closeScope();
		
		returnFound = false;
		currentMethod = null;
	}

	@Override
	public void visit(NoMethodDecl NoMethodDecl) {
		// TODO Auto-generated method stub
		super.visit(NoMethodDecl);
	}

	@Override
	public void visit(MethodDeclList MethodDeclList) {
		// TODO Auto-generated method stub
		super.visit(MethodDeclList);
	}

	@Override
	public void visit(Type Type) {
		Type.struct = Tab.noType;
		Obj typeNode = Tab.find(Type.getTypeName());
		
		if (typeNode == Tab.noObj) {
			report_error("Semnatic Error on line "+ Type.getLine() + " : Type \"" + Type.getTypeName() + "\" not found in symbol table ", Type);
			return;
		}
		
		if (Obj.Type != typeNode.getKind()) {
			report_error("Semantic Error on line "+ Type.getLine() + " : Name \""+ Type.getTypeName() + "\" does not represent type", Type);
			return;
		}
		
		Type.struct = typeNode.getType();
	}

	@Override
	public void visit(SimpleType SimpleType) {
		// TODO Auto-generated method stub
		super.visit(SimpleType);
	}

	@Override
	public void visit(ArrayType ArrayType) {
		// TODO Auto-generated method stub
		super.visit(ArrayType);
	}

	@Override
	public void visit(CommaVarDecl CommaVarDecl) {
		// TODO Auto-generated method stub
		super.visit(CommaVarDecl);
	}

	@Override
	public void visit(Semi Semi) {
		// TODO Auto-generated method stub
		super.visit(Semi);
	}

	@Override
	public void visit(ParamDefinitionError ParamDefinitionError) {
		// TODO Auto-generated method stub
		super.visit(ParamDefinitionError);
	}

	@Override
	public void visit(Vars Vars) {
		// Is it array? Vars.getOptArrayBrackets();
		// Is it const?
		
		report_info("Variable "+ Vars.getVarName() + " declared on line " + Vars.getLine(), Vars);
		Obj varNode = Tab.insert(Obj.Var, Vars.getVarName(), currentVarDeclTypeStruct);
	}

	@Override
	public void visit(NotConstType NotConstType) {
		// TODO Auto-generated method stub
		super.visit(NotConstType);
	}

	@Override
	public void visit(ConstType ConstType) {
		// TODO Auto-generated method stub
		super.visit(ConstType);
	}

	@Override
	public void visit(VarDecl VarDecl) {
		// Is it const? VarDecl.getOptConst();
		// Get list here and insert all at this point? VarDecl.getVarList();
		currentVarDeclTypeStruct = VarDecl.getType().struct;
	}

	@Override
	public void visit(NoVarDecl NoVarDecl) {
		// TODO Auto-generated method stub
		super.visit(NoVarDecl);
	}

	@Override
	public void visit(VarDeclList VarDeclList) {
		// TODO Auto-generated method stub
		super.visit(VarDeclList);
	}

	@Override
	public void visit(MulopMod MulopMod) {
		// TODO Auto-generated method stub
		super.visit(MulopMod);
	}

	@Override
	public void visit(MulopDiv MulopDiv) {
		// TODO Auto-generated method stub
		super.visit(MulopDiv);
	}

	@Override
	public void visit(MulopMul MulopMul) {
		// TODO Auto-generated method stub
		super.visit(MulopMul);
	}

	@Override
	public void visit(AddopSub AddopSub) {
		// TODO Auto-generated method stub
		super.visit(AddopSub);
	}

	@Override
	public void visit(AddopAdd AddopAdd) {
		// TODO Auto-generated method stub
		super.visit(AddopAdd);
	}

	@Override
	public void visit(NoArrayIndexer NoArrayIndexer) {
		// TODO Auto-generated method stub
		super.visit(NoArrayIndexer);
	}

	@Override
	public void visit(ArrayIndexer ArrayIndexer) {
		ArrayIndexer.struct = ArrayIndexer.getExpr().struct;
	}

	@Override
	public void visit(ConstBool ConstBool) {
		// TODO Auto-generated method stub
		super.visit(ConstBool);
	}

	@Override
	public void visit(ConstChar ConstChar) {
		// TODO Auto-generated method stub
		super.visit(ConstChar);
	}

	@Override
	public void visit(ConstNum ConstNum) {
		// TODO Auto-generated method stub
		super.visit(ConstNum);
	}

	@Override
	public void visit(DefinedVariable DefinedVariable) {
		// TODO Auto-generated method stub
		super.visit(DefinedVariable);
	}

	@Override
	public void visit(NewType NewType) {
		// TODO Auto-generated method stub
		super.visit(NewType);
	}

	@Override
	public void visit(ParenExpr ParenExpr) {
		// TODO Auto-generated method stub
		super.visit(ParenExpr);
	}

	@Override
	public void visit(ConstVar ConstVar) {
		// TODO Auto-generated method stub
		super.visit(ConstVar);
	}

	@Override
	public void visit(SingleFactor SingleFactor) {
		SingleFactor.struct = SingleFactor.getFactor().struct;
	}

	@Override
	public void visit(FactorMulops FactorMulops) {
		Struct factor = FactorMulops.getFactor().struct;
		Struct factorMulList = FactorMulops.getFactorMulopList().struct;
		if (factor.equals(factorMulList) && factor == Tab.intType)
		{
			FactorMulops.struct = factor;
			return;
		}
		
		FactorMulops.struct = Tab.noType;
		report_error("Semantic Error on line "+ FactorMulops.getLine() +
				" : incompatible types in multiplication expression ( " +factor + " and mul " + factorMulList + " )"
				, FactorMulops);
	}

	@Override
	public void visit(Term Term) {
		Term.struct = Term.getFactorMulopList().struct;
	}

	@Override
	public void visit(SingleTerm SingleTerm) {
		SingleTerm.struct = SingleTerm.getTerm().struct;
	}

	@Override
	public void visit(TermAddops TermAddops) {
		Struct term = TermAddops.getTerm().struct;
		Struct termSumList = TermAddops.getTermAddopList().struct;
		
		if (term.equals(termSumList) && term == Tab.intType)
		{
			TermAddops.struct = term;
			return;
		}
		
		TermAddops.struct = Tab.noType;
		report_error("Semantic Error on line "+ TermAddops.getLine() +
				" : incompatible types in addition expression ( " +term + " and sum " + termSumList + " )", TermAddops);
	}

	@Override
	public void visit(FuncCall FuncCall) {
		Obj function = FuncCall.getDesignator().obj;
		if (Obj.Meth != function.getKind())
		{
			report_error("Semantic Error on line " + FuncCall.getLine() +
					" : \"" + function.getName() + "\" is not a function! ( It is of kind "+ function.getKind(), FuncCall);
			FuncCall.struct = Tab.noType;
			return;
		}
		
		report_info("Function " + function.getName() + " call was found on line " + FuncCall.getLine(), FuncCall);
		FuncCall.struct = function.getType();
	}

	@Override
	public void visit(TermAddopListExpr TermAddopListExpr) {
		// TODO Auto-generated method stub
		super.visit(TermAddopListExpr);
	}

	@Override
	public void visit(TermAddopListExprSub TermAddopListExprSub) {
		// TODO Auto-generated method stub
		super.visit(TermAddopListExprSub);
	}

	@Override
	public void visit(NoPosfixOperation NoPosfixOperation) {
		// TODO Auto-generated method stub
		super.visit(NoPosfixOperation);
	}

	@Override
	public void visit(IncOperation IncOperation) {
		// TODO Auto-generated method stub
		super.visit(IncOperation);
	}

	@Override
	public void visit(DecOperation DecOperation) {
		// TODO Auto-generated method stub
		super.visit(DecOperation);
	}

	@Override
	public void visit(PostfixStmt PostfixStmt) {
		// TODO Auto-generated method stub
		super.visit(PostfixStmt);
	}

	@Override
	public void visit(AssignmentStmt AssignmentStmt) {
		if (!AssignmentStmt.getExprOrError().struct.assignableTo(AssignmentStmt.getDesignator().obj.getType()))
		{
			report_error("Semantic Error on line " + AssignmentStmt.getLine() +" : incompatible types in assignment ("+
					AssignmentStmt.getExprOrError().struct + " is not assignable to " + AssignmentStmt.getDesignator().obj.getType(), AssignmentStmt);
		}
	}

	@Override
	public void visit(NoCommaNumber NoCommaNumber) {
		// TODO Auto-generated method stub
		super.visit(NoCommaNumber);
	}

	@Override
	public void visit(CommaNumber CommaNumber) {
		if (CommaNumber.getNum() <= 0)
		{
			CommaNumber.struct = Tab.noType;
			return;
		}
		
		CommaNumber.struct = Tab.intType;
	}

	@Override
	public void visit(ReturnExpr ReturnExpr) {
		returnFound = true;
		if (!currentMethod.getType().compatibleWith(ReturnExpr.getExpr().struct) ) {
			report_error("Semantic Error on line " + ReturnExpr.getLine() + " : " + " Type of expression in return statement is incompatible with return value type of method " + currentMethod.getName() +
						"( Return expression of type " + ReturnExpr.getExpr().struct.getKind() + ", current method needs type "+ currentMethod.getType().getKind(), ReturnExpr);
		}
	}

	@Override
	public void visit(ReturnVoid ReturnVoid) {
		// TODO Auto-generated method stub
		super.visit(ReturnVoid);
	}

	@Override
	public void visit(PrintStmt PrintStmt) {
		// TODO Auto-generated method stub
		super.visit(PrintStmt);
	}

	@Override
	public void visit(ReadStmt ReadStmt) {
		// TODO Auto-generated method stub
		super.visit(ReadStmt);
	}

	@Override
	public void visit(DesignatorStmt DesignatorStmt) {
		// TODO Auto-generated method stub
		super.visit(DesignatorStmt);
	}

	@Override
	public void visit(ExprFuncCall ExprFuncCall) {
		// TODO Auto-generated method stub
		super.visit(ExprFuncCall);
	}

	@Override
	public void visit(Err Err) {
		// TODO Auto-generated method stub
		super.visit(Err);
	}

	@Override
	public void visit(Expression Expression) {
		Expression.struct = Expression.getExpr().struct;
	}

	@Override
	public void visit(StmtFuncCall StmtFuncCall) {
		// TODO Auto-generated method stub
		super.visit(StmtFuncCall);
	}

	@Override
	public void visit() {
		// TODO Auto-generated method stub
		super.visit();
	}

}
