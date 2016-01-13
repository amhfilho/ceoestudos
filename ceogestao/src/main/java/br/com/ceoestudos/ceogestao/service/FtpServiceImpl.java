package br.com.ceoestudos.ceogestao.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import br.com.ceoestudos.ceogestao.model.BackupFile;
import br.com.ceoestudos.ceogestao.model.BackupFileDateComparator;

@Service
public class FtpServiceImpl implements FtpService {
	final String SERVER = "191.252.18.38";
    final int PORT = 21;
    final String USER = "jelastic-ftp";
    final String PASS = "8RG3avzHbR";
    final String PATH = "/backup";
	
    private FTPClient ftpClient;

	@Override
	public List<BackupFile> listAvailableBackupFiles() throws IOException {
		createFTPClient();
        
        FTPFile[] files = ftpClient.listFiles(PATH);
        
        List<BackupFile> listAvailableBackupFiles = new ArrayList<>();
        for (FTPFile file:files){
        	BackupFile bFile = new BackupFile(file);
        	if(bFile.getDate().after(getCalendarOf(2016,Calendar.JANUARY,1))){
        		listAvailableBackupFiles.add(bFile);
        	}
        }
        
        Collections.sort(listAvailableBackupFiles,Collections.reverseOrder(new BackupFileDateComparator()));

		return listAvailableBackupFiles;
	}

	@Override
	public void retrieveFile(String fullPath, OutputStream outputStream) throws IOException {
		createFTPClient();
		
		ftpClient.retrieveFile(fullPath, outputStream);
	}
	

	private void createFTPClient() throws SocketException, IOException {
		ftpClient = new FTPClient();	 
        ftpClient.connect(SERVER, PORT);
        ftpClient.login(USER, PASS);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	}

	@Override
	public String getPath() {		
		return PATH;
	}
	
	private Calendar getCalendarOf(int year, int month, int day){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		return cal;
		
	}

}
