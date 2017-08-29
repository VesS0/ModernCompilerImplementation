package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4JUtils;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
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
	        
	        log.info("globalVariableCount = " + p.globalVariableCount);
	        log.info("globalConstVariableCount = " + p.globalConstVariableCount);
	        log.info("classVariableCount = " + p.classVariableCount);
	        log.info("mainVariableCount = " + p.mainVariableCount);
	        log.info("mainFunctionCallsCount = " + p.mainFunctionCallsCount);
	        log.info("globalClassFunctionCount = " + p.globalClassFunctionCount);
	        log.info("staticClassFunctionCount = " + p.staticClassFunctionCount);
	        log.info("globalFunctionCount = " + p.globalFunctionCount);
	        log.info("formalParamsCount = " + p.formalParamsCount);
	        log.info("classCount = " + p.classCount);
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
