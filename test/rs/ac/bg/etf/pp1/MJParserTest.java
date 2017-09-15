package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.EntityCounters;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	static String[] TestExamples = {"program.mj",
									"Tests/syntaxErrors.mj",
									"Tests/syntaxAnalysis.mj",
									"tests/test01.mj",
									"tests/test02.mj",
									"tests/test03.mj",
									"tests/test04.mj",
									"tests/test05.mj",
									"tests/test06.mj",
									"tests/test07.mj",
									"tests/test08.mj",
									"tests/test09.mj",
									"tests/test10.mj"};
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        log.info("globalVariableCount = " + EntityCounters.getGlobalVariableCount());
	        log.info("globalConstVariableCount = " + EntityCounters.getGlobalConstVariableCount());
	        log.info("classVariableCount = " + EntityCounters.getClassVariableCount());
	        log.info("mainVariableCount = " + EntityCounters.getMainVariableCount());
	        log.info("mainFunctionCallsCount = " + EntityCounters.getMainFunctionCallsCount());
	        log.info("globalClassFunctionCount = " + EntityCounters.getGlobalClassFunctionCount());
	        log.info("staticClassFunctionCount = " + EntityCounters.getStaticClassFunctionCount());
	        log.info("globalFunctionCount = " + EntityCounters.getGlobalFunctionCount());
	        log.info("formalParamsCount = " + EntityCounters.getFormalParamsCount());
	        log.info("classCount = " + EntityCounters.getClassCount());
	        
	        Code.write(new FileOutputStream("test/program.obj"));
	        Tab.dump();
	        
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
