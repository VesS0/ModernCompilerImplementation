package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

/*
	O = Basic,
	A = First level of complexity (20pts),
	B = Second level of complexity (30pts),
	C = Second level of complexity (40pts)
*/

" " 	{ } /* O */
"\b" 	{ } /* O */
"\t" 	{ } /* O */
"\r\n" 	{ } /* O */
"\f" 	{ } /* O */

"//" 		     { yybegin(COMMENT);	} /* O */
<COMMENT> .      { yybegin(COMMENT);	} /* O */
<COMMENT> "\r\n" { yybegin(YYINITIAL);	} /* O */

"break" 	{ return new_symbol(sym.BREAK, yytext()); }
"class" 	{ return new_symbol(sym.CLASS, yytext()); }
"const" 	{ return new_symbol(sym.CONST, yytext()); }
"continue" 	{ return new_symbol(sym.CONTINUE, yytext()); }
"else"	 	{ return new_symbol(sym.ELSE, yytext()); }
"extends" 	{ return new_symbol(sym.EXTENDS, yytext()); }
"for"	 	{ return new_symbol(sym.FOR, yytext()); }
"if"	 	{ return new_symbol(sym.IF, yytext()); }
"new"	 	{ return new_symbol(sym.NEW, yytext());				} /* A */
"print" 	{ return new_symbol(sym.PRINT, yytext());			} /* A */
"program"   { return new_symbol(sym.PROG, yytext()); }
"read"	 	{ return new_symbol(sym.READ, yytext());			} /* A */
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"static" 	{ return new_symbol(sym.STATIC, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }

"(" 		{ return new_symbol(sym.LPAREN, yytext());			} /* A */
")" 		{ return new_symbol(sym.RPAREN, yytext());			} /* A */
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"["		 	{ return new_symbol(sym.LBRACK, yytext());			} /* A */
"]" 		{ return new_symbol(sym.RBRACK, yytext());			} /* A */

"+" 		{ return new_symbol(sym.ADD, yytext());				} /* A */
"-" 		{ return new_symbol(sym.SUB, yytext());				} /* A */
"*" 		{ return new_symbol(sym.MUL, yytext());				} /* A */
"/" 		{ return new_symbol(sym.DIV, yytext());				} /* A */
"%" 		{ return new_symbol(sym.MOD, yytext());				} /* A */

"=" 		{ return new_symbol(sym.ASSIGN, yytext());			} /* A */
"+=" 		{ return new_symbol(sym.ASSIGN_ADD, yytext()); }
"-=" 		{ return new_symbol(sym.ASSIGN_SUB, yytext()); }
"*=" 		{ return new_symbol(sym.ASSIGN_MUL, yytext()); }
"/=" 		{ return new_symbol(sym.ASSIGN_DIV, yytext()); }
"%=" 		{ return new_symbol(sym.ASSIGN_MOD, yytext()); }

"==" 		{ return new_symbol(sym.EQUAL, yytext()); }
"!=" 		{ return new_symbol(sym.NOT_EQUAL, yytext()); }
">=" 		{ return new_symbol(sym.GREATER_EQUAL, yytext()); }
"<=" 		{ return new_symbol(sym.LESS_EQUAL, yytext()); }
">" 		{ return new_symbol(sym.GREATER, yytext()); }
"<" 		{ return new_symbol(sym.LESS, yytext()); }

"--" 		{ return new_symbol(sym.DEC, yytext());				} /* A (post-decrement) */
"++" 		{ return new_symbol(sym.INC, yytext());				} /* A (post-increment) */
"&&" 		{ return new_symbol(sym.AND, yytext()); }
"||" 		{ return new_symbol(sym.OR, yytext()); }

";" 		{ return new_symbol(sym.SEMI, yytext());			} /* A */
"," 		{ return new_symbol(sym.COMMA, yytext());			} /* A */
"." 		{ return new_symbol(sym.DOT, yytext()); }

"#"         { return new_symbol(sym.HASHTAG, yytext()); }
"->"        { return new_symbol(sym.ARROW, yytext()); }
":"         { return new_symbol(sym.DOTDOT, yytext()); }
"**"        { return new_symbol(sym.STARSTAR, yytext()); }

"true" 		{ return new_symbol(sym.BOOL, new Boolean(yytext())); }
"false" 	{ return new_symbol(sym.BOOL, new Boolean(yytext())); }
"'"."'" 	{ return new_symbol(sym.CHAR, new Character (yytext().charAt(1))); }
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext()));}  /* O */
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }  /* O */

. { System.err.println("Leksicka greska ("+yytext()+") na liniji "+(yyline+1) + " i koloni " +(yycolumn+1)); }  /* O */