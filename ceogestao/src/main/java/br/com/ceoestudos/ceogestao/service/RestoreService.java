package br.com.ceoestudos.ceogestao.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.ibatis.jdbc.ScriptRunner;

import br.com.ceoestudos.ceogestao.util.JdbcConnectionProvider;

public class RestoreService {
	
	private ScriptRunner scriptRunner;
	private Reader reader;
	private StringWriter logStringWriter ;
	private PrintWriter logPrintWriter ;
	private StringWriter errorStringWriter ;
	private PrintWriter errorPrintWriter ;
	
	
	public RestoreService(Reader reader) throws ClassNotFoundException, SQLException{
		Connection con = JdbcConnectionProvider.getConnection();
		this.scriptRunner = new ScriptRunner(con);
		this.reader = reader;
		logStringWriter = new StringWriter();
		logPrintWriter = new PrintWriter(logStringWriter);
		errorStringWriter = new StringWriter();
		errorPrintWriter = new PrintWriter(errorStringWriter);
		
		scriptRunner.setLogWriter(logPrintWriter);
		scriptRunner.setErrorLogWriter(errorPrintWriter);
	}
	
	public static BufferedReader getBufferedReaderForCompressedFile(File fileIn) throws FileNotFoundException, CompressorException {
	    FileInputStream fin = new FileInputStream(fileIn);
	    BufferedInputStream bis = new BufferedInputStream(fin);
	    CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
	    BufferedReader br2 = new BufferedReader(new InputStreamReader(input));
	    return br2;
	}
	
	public void run(){
		scriptRunner.runScript(reader);
	}
	
	public String getErrors(){
		return errorStringWriter.toString();
	}
	
	public String getLog(){
		return logStringWriter.toString();
	}

}
