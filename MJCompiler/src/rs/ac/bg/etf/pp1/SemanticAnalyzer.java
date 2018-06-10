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
		
		if (Designator.getOptArray().struct.getKind() == Struct.Array)
		{
			if (obj.getType().getKind() != Struct.Array)
			{
				report_error(
						"Semantic Error on line " + Designator.getLine() +
						" : Name " + Designator.getDesignatorName() +
						" is not an array ", Designator);
				Designator.obj = Tab.noObj;
				return;
			}
			
			Designator.obj = new Obj(obj.getKind(), obj.getName(), obj.getType().getElemType());
			return;
		}
		
		Designator.obj = obj;
	}
	
	@Override
	public void visit(FormalParamDecl FormalParamDecl) {
		if (FormalParamDecl.getOptArrayBrackets().bool)
		{
			Tab.insert(Obj.Var, FormalParamDecl.getParamName(), new Struct(Struct.Array, FormalParamDecl.getType().struct));
		} else
		{
			Tab.insert(Obj.Var, FormalParamDecl.getParamName(), FormalParamDecl.getType().struct);
		}
	}

	@Override
	public void visit(MethodTypeName MethodTypeName) {
		
        if(Tab.currentScope.findSymbol(MethodTypeName.getMethodName()) != null)
        {
              report_error("Semantic Error method named \"" + MethodTypeName.getMethodName() + "\" already exists", null);
              currentMethod = MethodTypeName.obj = null;
              return;
        }
        if (MethodTypeName.getOptArrayBrackets().bool)
        {
    		currentMethod = Tab.insert(Obj.Meth, MethodTypeName.getMethodName(), new Struct(Struct.Array, MethodTypeName.getType().struct));
        } else {
    		currentMethod = Tab.insert(Obj.Meth, MethodTypeName.getMethodName(), MethodTypeName.getType().struct);
        }

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
	public void visit(SimpleType SimpleType) {
		SimpleType.bool = false;
	}

	@Override
	public void visit(ArrayType ArrayType) {
		ArrayType.bool = true;
	}
	
	@Override
	public void visit(Vars Vars) {
		// Is it array? Vars.getOptArrayBrackets();
		// Is it const?
		if(Vars.getOptArrayBrackets().bool)
		{
			report_info("Array \""+ Vars.getVarName() + "\" declared on line " + Vars.getLine() +
					" of type " + StructKindToName(currentDeclTypeStruct.getKind()), Vars);
			// currentDeclTypeStruct = new Struct(Struct.Array, currentDeclTypeStruct); 
			Obj varNode = Tab.insert(Obj.Var, Vars.getVarName(), new Struct(Struct.Array, currentDeclTypeStruct));
		} else
		{
			report_info("Variable \""+ Vars.getVarName() + "\" declared on line " + Vars.getLine() +
					" of type " + StructKindToName(currentDeclTypeStruct.getKind()), Vars);
			Obj varNode = Tab.insert(Obj.Var, Vars.getVarName(), currentDeclTypeStruct);
		}
		// Obj varNode = Tab.insert(Obj.Var, Vars.getVarName(), currentDeclTypeStruct);
	}

	@Override
	public void visit(NoArrayIndexer NoArrayIndexer) {
		NoArrayIndexer.struct = new Struct(Struct.None);
	}

	@Override
	public void visit(ArrayIndexer ArrayIndexer) {
		if (ArrayIndexer.getExpr().struct.getKind() != Struct.Int)
		{
			report_error("Semantic Error on line " + ArrayIndexer.getLine() + " : arrays can only be indexed with integers, and you are trying to index with type "
					+ StructKindToName(ArrayIndexer.getExpr().struct.getKind()), null);
			ArrayIndexer.struct = Tab.noType;
			return;
		}
		
		ArrayIndexer.struct = new Struct(Struct.Array);;
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
		if (NewType.getOptArray().struct.getKind() != Struct.Array)
		{
			report_error("Semantic Error on line " + NewType.getLine() + " : operator NEW can only be used with array types (Type you have provided is "
					+ StructKindToName(NewType.getOptArray().struct.getKind()) +")", null);
			NewType.struct = Tab.noType;
			return;
		}
		NewType.struct = new Struct(Struct.Array, NewType.getType().struct);
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
		Struct LeftFromAssignType = AssignmentStmt.getDesignator().obj.getType();
		Struct RightFromAssignType = AssignmentStmt.getExprOrError().struct;
		
		if (AssignmentStmt.getDesignator().getOptArray().struct.getKind() == Struct.Array)
		{
			// LeftFromAssignType = LeftFromAssignType.getElemType();
		}
		
		// For some reason if two expressions are of same type (Bool), it still returns false for assignable
		if ( RightFromAssignType.getKind() != LeftFromAssignType.getKind() && !RightFromAssignType.assignableTo(LeftFromAssignType))
		{
			report_error("Semantic Error on line " + AssignmentStmt.getLine() +" : incompatible types in assignment ("+
					StructKindToName(RightFromAssignType.getKind()) + " is not assignable to "+
					StructKindToName(LeftFromAssignType.getKind()) + ")", AssignmentStmt);
		}
	}

	@Override
	public void visit(NoCommaNumber NoCommaNumber) {
		NoCommaNumber.struct = Tab.noType;
	}

	@Override
	public void visit(CommaNumber CommaNumber) {
		if (CommaNumber.getExpr().struct.getKind() != Struct.Int)
		{
			report_error("Semantic Error on line " + CommaNumber.getLine() +" : second parameter in print needs to be of type int ("+
					"you have used expression which is returning type"+ StructKindToName(CommaNumber.getExpr().struct.getKind()) +")", null);
			CommaNumber.struct = Tab.noType;
			return;
		}
		CommaNumber.struct = Tab.intType;
	}

	@Override
	public void visit(ReturnExpr ReturnExpr) {
		returnFound = true;
		Struct DefinedReturnType = currentMethod.getType();
		Struct ActualReturningType = ReturnExpr.getExpr().struct;
		
		if (ActualReturningType.getKind() == Struct.Array)
		{
			// ActualReturningType = ActualReturningType.getElemType();
		}
		
		// First check is needed because of Bool type.
		if (DefinedReturnType.getKind() != ActualReturningType.getKind() && !DefinedReturnType.compatibleWith(ActualReturningType) ) {
			
			report_error("Semantic Error on line " + ReturnExpr.getLine() +
					" : Type of expression in return statement is incompatible with return value type of method "
					+ currentMethod.getName() +" (Return expression of type " + StructKindToName(ReturnExpr.getExpr().struct.getKind()) +
					", current method needs type "+ StructKindToName(currentMethod.getType().getKind()) + ")", ReturnExpr);
			return;
		}
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
}
