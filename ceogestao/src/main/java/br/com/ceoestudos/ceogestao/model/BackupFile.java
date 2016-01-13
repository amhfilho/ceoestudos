package br.com.ceoestudos.ceogestao.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ftp.FTPFile;

public class BackupFile {
	private static final String FILE_NAME_DATE_FORMAT = "yyyy-MM-dd";
	private FTPFile ftpFile ;
	
	public BackupFile(FTPFile ftpFile){
		this.ftpFile = ftpFile;
	}
	
	public String getOriginalName(){
		return ftpFile.getName();
	}
	
	public Calendar getDateFromName(){
		String strDate = getOriginalName().substring(10, 20);
		try {
			Date date = new SimpleDateFormat(FILE_NAME_DATE_FORMAT).parse(strDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} catch (ParseException e) {
			return null;
		}
		
	}
	
	public Calendar getDate(){
		return ftpFile.getTimestamp();
	}

}
