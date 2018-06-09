package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	// public static final int STATIC = 6;
	public static final int Bool = 5;
	public static final Struct boolType = new Struct(Struct.Bool);
	
	public static void main(String[] args) throws Exception {
		Logger log = Logger.getLogger(Compiler.class);

		String objectFilePath[], sourceFilePath[];
		int numberOfFilesToParse = 1, SyntaxOnlyFiles = 0, SemanticOnlyFiles = 0;
		
		if (/*args.length < 2*/ true) {
			log.error("Not enough arguments supplied! Usage: MJParser <source-file> <obj-file> \nUsing test files instead");
			sourceFilePath = new String[] {
					"test/syntaxTests/SyntaxTestBasic", "test/syntaxTests/SyntaxTestError" ,
					"test/semanticAnalysisTests/SemanticAnalysisTestBasic", "test/semanticAnalysisTests/SemanticAnalysisTestError",
					"test/semanticAnalysisTests/SemanticAnalysisArrayBasic", "test/semanticAnalysisTests/SemanticAnalysisArrayError"
			};
			
			SyntaxOnlyFiles = 2;
			SemanticOnlyFiles = 2 + SyntaxOnlyFiles;
			
			numberOfFilesToParse = sourceFilePath.length;
			objectFilePath = new String[numberOfFilesToParse];
			

			// objectFilePath[0] = "test/SemanticAnalysisTestBasic.obj";
		}
		else
		{
			objectFilePath = new String[numberOfFilesToParse];
			sourceFilePath = new String[numberOfFilesToParse];
			
			sourceFilePath[0] = args[0];
			objectFilePath[0] = args[1];
		}
		
		for (int currentParsingFileIndex = 0; currentParsingFileIndex < numberOfFilesToParse; currentParsingFileIndex++)
		{
			File sourceCode= new File(sourceFilePath[currentParsingFileIndex]);
			if (!sourceCode.exists()) {
				log.error("Source file [" + sourceCode.getAbsolutePath() + "] not found!");
				return;
			}
				
			log.info(" ******************************** /nCompiling source file: " + sourceCode.getAbsolutePath());
			
			try (BufferedReader br = new BufferedReader(new FileReader(sourceCode))) {
				Yylex lexer = new Yylex(br);
				MJParser p = new MJParser(lexer);
		        Symbol s = p.parse();  //pocetak parsiranja
		        
		        SyntaxNode prog = (SyntaxNode)(s.value);
		        
		        if (p.isSyntaxErrorDetected)
		        {
		        	log.info(" ******************************** /nSyntax Error Detected - further parsing stopped on file: "+ sourceFilePath[currentParsingFileIndex]);
		        	if (currentParsingFileIndex%2 ==0){
		        		throw new Exception("TEST ASSUMPTION FAILED");
		        	}
		        	continue;
		        } else
		        {
		        	log.info(" ******************************** /nSyntax Analysis successfully finished on file: "+ sourceFilePath[currentParsingFileIndex]);
		        }
		        
		        log.info("Prog = " + prog.toString());
		        log.info("Symb = " + s.toString());
		        
		        if (currentParsingFileIndex < SyntaxOnlyFiles)
		        {
		        	continue;
		        }
		        
				Tab.init(); // Universe scope
				Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
				
				SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
				prog.traverseBottomUp(semanticAnalyzer);
				
				Tab.dump();
				
				if(semanticAnalyzer.isErrorDetected()) {
			        log.info(" ******************************** /nSemantic Error Detected - further parsing stopped on file: "+ sourceFilePath[currentParsingFileIndex]);
		        	if (currentParsingFileIndex%2 ==0){
		        		throw new Exception("TEST ASSUMPTION FAILED");
		        	}
			        continue;
				} else {
			        log.info(" ******************************** /nSemantic Analysis successfully finished on file: "+ sourceFilePath[currentParsingFileIndex]);
				}
				
		        if (currentParsingFileIndex < SemanticOnlyFiles)
		        {
		        	continue;
		        }
		        
				/*
		        File objFile = new File(objectFilePath[currentParsingFileIndex]);
		        log.info("Generating bytecode file: " + objFile.getAbsolutePath());
		        if (objFile.exists())
		        		objFile.delete();
		        	
		        // Code generation...
		        CodeGenerator codeGenerator = new CodeGenerator();
		        prog.traverseBottomUp(codeGenerator);
		        Code.dataSize = semanticAnalyzer.nVars;
		        Code.mainPc = codeGenerator.getMainPc();
		        Code.write(new FileOutputStream(objFile));
		        log.info("Parsing successfully finished");
		        */
			}
		}
	}
}
