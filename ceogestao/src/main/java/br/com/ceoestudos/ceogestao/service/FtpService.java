package br.com.ceoestudos.ceogestao.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import br.com.ceoestudos.ceogestao.model.BackupFile;

public interface FtpService {
	
	List<BackupFile> listAvailableBackupFiles() throws IOException ;
	
	void retrieveFile(String fullPath, OutputStream outputStream) throws IOException;
	
	String getPath();

}
