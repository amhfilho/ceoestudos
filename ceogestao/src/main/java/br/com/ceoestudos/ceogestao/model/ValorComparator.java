package br.com.ceoestudos.ceogestao.model;

import java.util.Comparator;

public class ValorComparator implements Comparator<Pessoa>{

	@Override
	public int compare(Pessoa o1, Pessoa o2) {
		
		return o1.getValorTotalContas().compareTo(o2.getValorTotalContas());
	}

}
