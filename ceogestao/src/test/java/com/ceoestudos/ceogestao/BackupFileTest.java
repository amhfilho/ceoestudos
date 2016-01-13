package com.ceoestudos.ceogestao;

import java.util.Calendar;

import org.apache.commons.net.ftp.FTPFile;
import org.junit.Assert;
import org.junit.Test;

import br.com.ceoestudos.ceogestao.model.BackupFile;

public class BackupFileTest {

	@Test
	public void shouldReturnJan132016FromFileName(){
		FTPFile file = new FTPFile();
		file.setName("odontosys-2016-01-13_20h00m.sql.bz2");
		
		BackupFile backupFile = new BackupFile(file);
		Calendar date = backupFile.getDateFromName();
		
		Assert.assertEquals(13,date.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(Calendar.JANUARY,date.get(Calendar.MONTH));
		Assert.assertEquals(2016,date.get(Calendar.YEAR));
	}
}
