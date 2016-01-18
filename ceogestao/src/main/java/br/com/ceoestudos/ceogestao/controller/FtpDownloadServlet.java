package br.com.ceoestudos.ceogestao.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * Servlet implementation class FtpDownloadServlet
 */
@WebServlet("/testebackup")
public class FtpDownloadServlet extends HttpServlet {
	final String SERVER = "191.252.18.38";
    final int PORT = 21;
    final String USER = "jelastic-ftp";
    final String PASS = "8RG3avzHbR";
    
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FtpDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        FTPClient ftpClient = new FTPClient();
        
 
        ftpClient.connect(SERVER, PORT);
        ftpClient.login(USER, PASS);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
               

        String dir = "/backup/";
        String fileName = "odontosys-2016-01-12_20h00m.sql.bz2";
        String fullPath = dir+fileName;

        OutputStream outputStream1 = response.getOutputStream();

     	String headerKey = "Content-Disposition";
     	String headerValue = String.format("attachment; filename=\"%s\"", fileName);
     	response.setHeader(headerKey, headerValue);
     	response.setContentType("application/octet-stream");

        boolean success = ftpClient.retrieveFile(fullPath, outputStream1);
        
        outputStream1.close();
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
