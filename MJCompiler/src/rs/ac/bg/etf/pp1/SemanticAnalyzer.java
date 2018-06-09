package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {
	boolean errorDetected = false;
	Obj currentMethod = null;
	Struct currentDeclTypeStruct = null;
	boolean returnFound = false;
	boolean postfixOperationPresent = false;
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
		
	private static String StructKindToName(int kind)
	{
		switch (kind)
		{
			case Struct.None: return 	"Struct_None";
			case Struct.Int: return 	"Struct_Int";
			case Struct.Char: return 	"Struct_Char";
			case Struct.Array: return 	"Struct_Array";
			case Struct.Class: return 	"Struct_Class";
			case Struct.Bool: return 	"Struct_Bool";
		}
		assert(false) : "We should always have some of existing Kinds sent in StructKindToName function";
		return "NoSuchKind";
	}
	
	private static String ObjTypeToName(int type)
	{
		switch (type)
		{
			case Obj.Con: return 		"Obj_Con";
			case Obj.Elem: return 		"Obj_Elem";
			case Obj.Fld: return 		"Obj_Fld";
			case Obj.Meth: return 		"Obj_Meth";
			case Obj.NO_VALUE: return 	"Obj_NOVALUE";
			case Obj.Prog: return 		"Obj_Prog";
			case Obj.Type: return 		"Obj_Type";
			case Obj.Var: return 		"Obj_Var";
		}
		
		assert(false) : "We should always have some of existing Types sent in ObjTypeToName function";
		return "NoSuchType";
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
		Tab.insert(Obj.Var, FormalParamDecl.getParamName(), FormalParamDecl.getType().struct);
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
		
        if(Tab.currentScope.findSymbol(MethodTypeName.getMethodName()) != null)
        {
              report_error("Semantic Error method named \"" + MethodTypeName.getMethodName() + "\" already exists", null);
              currentMethod = MethodTypeName.obj = null;
              return;
        }
        
		currentMethod = Tab.insert(Obj.Meth, MethodTypeName.getMethodName(), MethodTypeName.getType().struct);

		MethodTypeName.obj  = currentMethod;
		Tab.openScope();
		report_info("Processing function " + MethodTypeName.getMethodName(), MethodTypeName);
	}

	@Override
	public void visit(MethodDecl MethodDecl) {
		if (currentMethod == null)
		{
			Tab.closeScope();
			return;
		}
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
		super.visit(Type);
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
		
		report_info("Variable \""+ Vars.getVarName() + "\" declared on line " + Vars.getLine(), Vars);
		Obj varNode = Tab.insert(Obj.Var, Vars.getVarName(), currentDeclTypeStruct);
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
		ArrayIndexer.struct = ArrayIndexer.getExpr().struct;
	}

	@Override
	public void visit(ConstBool ConstBool) {
		ConstBool.struct = new Struct(Struct.Bool);
	}

	@Override
	public void visit(ConstChar ConstChar) {
		ConstChar.struct = Tab.charType;
	}

	@Override
	public void visit(ConstNum ConstNum) {
		ConstNum.struct = Tab.intType;
	}

	@Override
	public void visit(DefVarWithOptPostfixOp DefVarWithOptPostfixOp) {
		DefVarWithOptPostfixOp.struct = DefVarWithOptPostfixOp.getDesignator().obj.getType();
		
		if (postfixOperationPresent)
		{
			if ( (DefVarWithOptPostfixOp.getDesignator().obj.getKind() != Obj.Var &&
					DefVarWithOptPostfixOp.getDesignator().obj.getKind() != Obj.Elem)
					|| DefVarWithOptPostfixOp.getDesignator().obj.getType() != Tab.intType)
			{
				report_error("Semantic Error on line " + DefVarWithOptPostfixOp.getLine() + " : Postfix operation cannot be used on object of kind " +
						ObjTypeToName(DefVarWithOptPostfixOp.getDesignator().obj.getKind()) + " and type "+ StructKindToName(DefVarWithOptPostfixOp.getDesignator().obj.getType().getKind()) , null);
			}
			postfixOperationPresent = false;
		}
	}

	@Override
	public void visit(NewType NewType) {
		NewType.struct = NewType.getType().struct;
		// is array type? NewType.getOptArray();
	}

	@Override
	public void visit(ParenExpr ParenExpr) {
		ParenExpr.struct = ParenExpr.getExpr().struct;
	}

	@Override
	public void visit(ConstVar ConstVar) {
		ConstVar.struct = ConstVar.getConst().struct;
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
				" : incompatible types in multiplication expression ( " + StructKindToName(factor.getKind()) + " and mul " + StructKindToName(factorMulList.getKind()) + " )"
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
				" : incompatible types in addition expression ( " +StructKindToName(term.getKind()) + " and sum " + StructKindToName(termSumList.getKind()) + " )", TermAddops);
	}

	@Override
	public void visit(FuncCall FuncCall) {
		Obj function = FuncCall.getDesignator().obj;
		if (Obj.Meth != function.getKind())
		{
			report_error("Semantic Error on line " + FuncCall.getLine() +
					" : \"" + function.getName() + "\" is not a function! (It is of kind "+ StructKindToName(function.getKind()) + ")", FuncCall);
			FuncCall.struct = Tab.noType;
			return;
		}
		
		report_info("Function " + function.getName() + " call was found on line " + FuncCall.getLine(), FuncCall);
		FuncCall.struct = function.getType();
	}

	@Override
	public void visit(TermAddopListExpr TermAddopListExpr) {
		TermAddopListExpr.struct = TermAddopListExpr.getTermAddopList().struct;
	}

	@Override
	public void visit(TermAddopListExprSub TermAddopListExprSub) {
		TermAddopListExprSub.struct = TermAddopListExprSub.getTermAddopList().struct;
	}

	@Override
	public void visit(NoPosfixOperation NoPosfixOperation) {
		postfixOperationPresent = false;
		// TODO Auto-generated method stub
		super.visit(NoPosfixOperation);
	}

	@Override
	public void visit(IncOperation IncOperation) {
		postfixOperationPresent = true;
		// TODO Auto-generated method stub
		super.visit(IncOperation);
	}

	@Override
	public void visit(DecOperation DecOperation) {
		postfixOperationPresent = true;
		// TODO Auto-generated method stub
		super.visit(DecOperation);
	}

	@Override
	public void visit(PostfixStmt PostfixStmt) {
		if (postfixOperationPresent)
		{
			if ( (PostfixStmt.getDesignator().obj.getKind() != Obj.Var &&
					PostfixStmt.getDesignator().obj.getKind() != Obj.Elem)
					|| PostfixStmt.getDesignator().obj.getType() != Tab.intType)
			{
				report_error("Semantic Error on line " + PostfixStmt.getLine() + " : Postfix operation cannot be used on object of kind " +
						ObjTypeToName(PostfixStmt.getDesignator().obj.getKind()) + " and type "+ StructKindToName(PostfixStmt.getDesignator().obj.getType().getKind()) , null);
			}
			postfixOperationPresent = false;
		}
		
	}

	@Override
	public void visit(AssignmentStmt AssignmentStmt) {
		// For some reason if two expressions are of same type (Bool), it still returns false for assignable
		if (AssignmentStmt.getExprOrError().struct.getKind() != AssignmentStmt.getDesignator().obj.getType().getKind() &&
				!AssignmentStmt.getExprOrError().struct.assignableTo(AssignmentStmt.getDesignator().obj.getType()))
		{
			report_error("Semantic Error on line " + AssignmentStmt.getLine() +" : incompatible types in assignment ("+
					StructKindToName(AssignmentStmt.getExprOrError().struct.getKind()) + " is not assignable to " + StructKindToName(AssignmentStmt.getDesignator().obj.getType().getKind()) + " )", AssignmentStmt);
		}
	}

	@Override
	public void visit(NoCommaNumber NoCommaNumber) {
		NoCommaNumber.struct = Tab.noType;
	}

	@Override
	public void visit(CommaNumber CommaNumber) {
		CommaNumber.struct = Tab.intType;
	}

	@Override
	public void visit(ReturnExpr ReturnExpr) {
		returnFound = true;
		if (currentMethod.getType().getKind() != ReturnExpr.getExpr().struct.getKind() &&
				!currentMethod.getType().compatibleWith(ReturnExpr.getExpr().struct) ) {
			report_error("Semantic Error on line " + ReturnExpr.getLine() + " : " + " Type of expression in return statement is incompatible with return value type of method " + currentMethod.getName() +
						"( Return expression of type " + StructKindToName(ReturnExpr.getExpr().struct.getKind()) + ", current method needs type "+ StructKindToName(currentMethod.getType().getKind()) + ")", ReturnExpr);
		}
		
	}

	@Override
	public void visit(ReturnVoid ReturnVoid) {
		// TODO Auto-generated method stub
		super.visit(ReturnVoid);
	}

	@Override
	public void visit(PrintStmt PrintStmt) {
		if (Tab.noType == PrintStmt.getOptCommaNumber().struct)
		{
			// there is no " , number " in this statement;
		} 
		/* Following code should be unneeded since in CommaNumber we are recognizing
		 * NUMBER which is of type INT (Lexical Analysis should fail here!)
		else if (Tab.intType != PrintStmt.getOptCommaNumber().struct)
		{
			report_error("Semantic Error on line " + PrintStmt.getLine() + 
					" : After expression in print, only supported type is int (Type you have provided is " +
					KindToName(PrintStmt.getOptCommaNumber().struct.getKind()) + ")", null);
		}*/ 
		
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
		ExprFuncCall.struct = ExprFuncCall.getFuncCall().struct;
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
	public void visit(VoidType VoidType) {
		VoidType.struct = Tab.noType;
	}

	@Override
	public void visit(NotVoidType NotVoidType) {
		NotVoidType.struct = Tab.noType;
		Obj typeNode = Tab.find(NotVoidType.getTypeName());
		
		if (typeNode == Tab.noObj) {
			report_error("Semnatic Error on line "+ NotVoidType.getLine() + " : Type \"" + NotVoidType.getTypeName() + "\" not found in symbol table ", NotVoidType);
			return;
		}
		
		if (Obj.Type != typeNode.getKind()) {
			report_error("Semantic Error on line "+ NotVoidType.getLine() + " : Name \""+ NotVoidType.getTypeName() + "\" does not represent type", NotVoidType);
			return;
		}
		
		NotVoidType.struct = typeNode.getType();
	}

	@Override
	public void visit(VarType VarType) {
		// Is it const? VarDecl.getOptConst();
		// Get list here and insert all at this point? VarDecl.getVarList();
		currentDeclTypeStruct = VarType.getType().struct;
		report_info("Type Changed! " + StructKindToName(currentDeclTypeStruct.getKind()),null);
	}

	@Override
	public void visit() {
		// TODO Auto-generated method stub
		super.visit();
	}

}
