package br.com.ceoestudos.ceogestao.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.Util;

import br.com.ceoestudos.ceogestao.model.BackupFile;
import br.com.ceoestudos.ceogestao.service.FtpService;
import br.com.ceoestudos.ceogestao.service.RestoreService;

@Controller
public class BackupController {
	
	@Autowired
	private FtpService ftpService;
	
	private Logger LOG = Logger.getLogger(getClass().getName());
	
	@RequestMapping(value="backup", method=RequestMethod.GET)
	public String listaArquivosBackup(Model model) throws IOException{
		List<BackupFile> arquivos = ftpService.listAvailableBackupFiles();
		model.addAttribute("arquivos",arquivos);	
		return "listaBackup";
	}
	
	@RequestMapping(value="backup/download", method=RequestMethod.POST)
	public void downloadFile(Model model, String fileName, HttpServletResponse response) throws IOException {
		String headerKey = "Content-Disposition";
     	String headerValue = String.format("attachment; filename=\"%s\"", fileName);
     	response.setHeader(headerKey, headerValue);
     	response.setContentType("application/octet-stream");
     	OutputStream outputStream = response.getOutputStream();
     	ftpService.retrieveFile(ftpService.getPath()+"/"+fileName, outputStream);
     	outputStream.close();
	}
	
	@RequestMapping(value="backup/upload", method=RequestMethod.POST)
	public String uploadFile(@RequestParam("inputFile") MultipartFile file,Model model) throws IOException {
		File convFile = new File(file.getOriginalFilename());
        file.transferTo(convFile);

		Reader reader = new BufferedReader(new FileReader(convFile));

		try{
			RestoreService restoreService = new RestoreService(reader);
			restoreService.run();
			String log = restoreService.getLog();
			String errors = restoreService.getErrors();
			model.addAttribute("log",log);
			if(!errors.isEmpty()){
				model.addAttribute("ERROR_MESSAGE","Erros durante a restauração do backup");
				model.addAttribute("errors",errors);
			} else {
				model.addAttribute("SUCCESS_MESSAGE","Restauração do backup realizada com sucesso");
			}
			
			
		} catch (Exception e) {
			LOG.severe(Util.stackTraceToString(e));
			model.addAttribute("ERROR_MESSAGE","Ocorreu um erro na restauração do backup: "+e.getMessage());
			return "listaBackup";
		}
		return "resultadoRestauracao";
	}

}
