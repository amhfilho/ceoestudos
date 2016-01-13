package br.com.ceoestudos.ceogestao.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.ceoestudos.ceogestao.model.BackupFile;
import br.com.ceoestudos.ceogestao.service.FtpService;

@Controller
public class BackupController {
	
	@Autowired
	private FtpService ftpService;
	
	@RequestMapping(value="/backup", method=RequestMethod.GET)
	public String listaArquivosBackup(Model model) throws IOException{
		List<BackupFile> arquivos = ftpService.listAvailableBackupFiles();
		model.addAttribute("arquivos",arquivos);	
		return "listaBackup";
	}
	
	@RequestMapping(value="/backup/download", method=RequestMethod.POST)
	public void downloadFile(Model model, String fileName, HttpServletResponse response) throws IOException {
		String headerKey = "Content-Disposition";
     	String headerValue = String.format("attachment; filename=\"%s\"", fileName);
     	response.setHeader(headerKey, headerValue);
     	response.setContentType("application/octet-stream");
     	OutputStream outputStream = response.getOutputStream();
     	ftpService.retrieveFile(ftpService.getPath()+"/"+fileName, outputStream);
     	outputStream.close();
	}
		
	

}
