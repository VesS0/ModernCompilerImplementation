package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class SemanticAnalyzer extends VisitorAdaptor {
	boolean errorDetected = false;
	int nVars;
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}


	@Override
	public void visit(ExprOrError ExprOrError) {
		// TODO Auto-generated method stub
		super.visit(ExprOrError);
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
	public void visit(OptFactorMulopList OptFactorMulopList) {
		// TODO Auto-generated method stub
		super.visit(OptFactorMulopList);
	}

	@Override
	public void visit(Var Var) {
		// TODO Auto-generated method stub
		super.visit(Var);
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
	public void visit(Designator Designator) {
		// TODO Auto-generated method stub
		super.visit(Designator);
	}

	@Override
	public void visit(OptTermAddopList OptTermAddopList) {
		// TODO Auto-generated method stub
		super.visit(OptTermAddopList);
	}

	@Override
	public void visit(ActualParamList ActualParamList) {
		// TODO Auto-generated method stub
		super.visit(ActualParamList);
	}

	@Override
	public void visit(OptArrayIndexer OptArrayIndexer) {
		// TODO Auto-generated method stub
		super.visit(OptArrayIndexer);
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
	public void visit(SemiCommaVarDecl SemiCommaVarDecl) {
		// TODO Auto-generated method stub
		super.visit(SemiCommaVarDecl);
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
	public void visit(OptMultipleActualParamList OptMultipleActualParamList) {
		// TODO Auto-generated method stub
		super.visit(OptMultipleActualParamList);
	}

	@Override
	public void visit(OptActualParamList OptActualParamList) {
		// TODO Auto-generated method stub
		super.visit(OptActualParamList);
	}

	@Override
	public void visit(ActualParam ActualParam) {
		// TODO Auto-generated method stub
		super.visit(ActualParam);
	}

	@Override
	public void visit(MultipleActualParams MultipleActualParams) {
		// TODO Auto-generated method stub
		super.visit(MultipleActualParams);
	}

	@Override
	public void visit(NoActualParams NoActualParams) {
		// TODO Auto-generated method stub
		super.visit(NoActualParams);
	}

	@Override
	public void visit(ActualParams ActualParams) {
		// TODO Auto-generated method stub
		super.visit(ActualParams);
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
	public void visit(MethodReturnType MethodReturnType) {
		// TODO Auto-generated method stub
		super.visit(MethodReturnType);
	}

	@Override
	public void visit(MethodTypeName MethodTypeName) {
		// TODO Auto-generated method stub
		super.visit(MethodTypeName);
	}

	@Override
	public void visit(MethodDecl MethodDecl) {
		// TODO Auto-generated method stub
		super.visit(MethodDecl);
	}

	@Override
	public void visit(Type Type) {
		// TODO Auto-generated method stub
		super.visit(Type);
	}

	@Override
	public void visit(NoBrack NoBrack) {
		// TODO Auto-generated method stub
		super.visit(NoBrack);
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
	public void visit(VarList VarList) {
		// TODO Auto-generated method stub
		super.visit(VarList);
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
		// TODO Auto-generated method stub
		super.visit(VarDecl);
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
		// TODO Auto-generated method stub
		super.visit(ArrayIndexer);
	}

	@Override
	public void visit(DesignatorSimple DesignatorSimple) {
		// TODO Auto-generated method stub
		super.visit(DesignatorSimple);
	}

	@Override
	public void visit(NoBrackExpr NoBrackExpr) {
		// TODO Auto-generated method stub
		super.visit(NoBrackExpr);
	}

	@Override
	public void visit(BrackExpr BrackExpr) {
		// TODO Auto-generated method stub
		super.visit(BrackExpr);
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
	public void visit(NoFactor NoFactor) {
		// TODO Auto-generated method stub
		super.visit(NoFactor);
	}

	@Override
	public void visit(FactorMulopList FactorMulopList) {
		// TODO Auto-generated method stub
		super.visit(FactorMulopList);
	}

	@Override
	public void visit(Term Term) {
		// TODO Auto-generated method stub
		super.visit(Term);
	}

	@Override
	public void visit(NoTerm NoTerm) {
		// TODO Auto-generated method stub
		super.visit(NoTerm);
	}

	@Override
	public void visit(TermAddopList TermAddopList) {
		// TODO Auto-generated method stub
		super.visit(TermAddopList);
	}

	@Override
	public void visit(FuncCall FuncCall) {
		// TODO Auto-generated method stub
		super.visit(FuncCall);
	}

	@Override
	public void visit(TermAddopListExprNoSub TermAddopListExprNoSub) {
		// TODO Auto-generated method stub
		super.visit(TermAddopListExprNoSub);
	}

	@Override
	public void visit(TermAddopListExpr TermAddopListExpr) {
		// TODO Auto-generated method stub
		super.visit(TermAddopListExpr);
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
	public void visit(ExprOrErrorDerived2 ExprOrErrorDerived2) {
		// TODO Auto-generated method stub
		super.visit(ExprOrErrorDerived2);
	}

	@Override
	public void visit(ExprOrErrorDerived1 ExprOrErrorDerived1) {
		// TODO Auto-generated method stub
		super.visit(ExprOrErrorDerived1);
	}

	@Override
	public void visit(ProcCall ProcCall) {
		// TODO Auto-generated method stub
		super.visit(ProcCall);
	}

	@Override
	public void visit(PostfixStmt PostfixStmt) {
		// TODO Auto-generated method stub
		super.visit(PostfixStmt);
	}

	@Override
	public void visit(AssignmentStmt AssignmentStmt) {
		// TODO Auto-generated method stub
		super.visit(AssignmentStmt);
	}

	@Override
	public void visit(NoCommaNumber NoCommaNumber) {
		// TODO Auto-generated method stub
		super.visit(NoCommaNumber);
	}

	@Override
	public void visit(CommaNumber CommaNumber) {
		// TODO Auto-generated method stub
		super.visit(CommaNumber);
	}

	@Override
	public void visit(ReturnExpr ReturnExpr) {
		// TODO Auto-generated method stub
		super.visit(ReturnExpr);
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
	public void visit() {
		// TODO Auto-generated method stub
		super.visit();
	}

}
