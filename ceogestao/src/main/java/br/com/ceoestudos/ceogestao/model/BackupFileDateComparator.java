package br.com.ceoestudos.ceogestao.model;

import java.util.Comparator;

public class BackupFileDateComparator implements Comparator<BackupFile>{

	@Override
	public int compare(BackupFile o1, BackupFile o2) {		
		return o1.getDateFromName().compareTo(o2.getDateFromName());
	}

}
