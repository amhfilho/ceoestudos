package com.ceoestudos.ceogestao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import br.com.ceoestudos.ceogestao.model.Conta;
import br.com.ceoestudos.ceogestao.model.Parcela;

/**
 *
 * @author amhfilho
 */
public class ContaTest {
    
	@Test
    public void deveCriar3ParcelasComJurosOrdenadasPorData(){
    	Conta conta = new Conta();
    	conta.setValor(new BigDecimal("1000"));
    	Calendar initialDate = Calendar.getInstance();
    	conta.aplicarTaxaJuros(3, 1.5D, initialDate);
    	Set<Parcela> parcelas = conta.getParcelas();
    	int count=0;
    	for(Parcela p:parcelas){    		
    		Calendar c = Calendar.getInstance();
    		c.setTime(p.getVencimento());
    		int dia = c.get(Calendar.DAY_OF_MONTH);
    		int mes = c.get(Calendar.MONTH);
    		int ano = c.get(Calendar.YEAR);
    		Calendar c2 = Calendar.getInstance();
    		c2.setTime(initialDate.getTime());
    		c2.add(Calendar.MONTH, count);
    		Assert.assertEquals(c2.get(Calendar.DAY_OF_MONTH), dia);
    		Assert.assertEquals(c2.get(Calendar.MONTH), mes);
    		Assert.assertEquals(c2.get(Calendar.YEAR), ano);
    		Assert.assertEquals(new BigDecimal("343.39"), p.getValor());
    		count++;
    	}
    	
    }
	
	    
    @Test
    public void deveRetornar4ParcelasOrdenadasPorData(){
    	Conta conta = new Conta();
    	Parcela p1 = new Parcela();
    	Calendar c1 = Calendar.getInstance();
    	c1.set(2015, Calendar.NOVEMBER,8);
    	p1.setVencimento(c1.getTime());
    	
    	Parcela p2 = new Parcela();
    	c1.set(2015, Calendar.NOVEMBER, 9);
    	p2.setVencimento(c1.getTime());
    	
    	Parcela p3 = new Parcela();
    	c1.set(2014, Calendar.JANUARY, 21);
    	p3.setVencimento(c1.getTime());
    	
    	Parcela p4 = new Parcela();
    	c1.set(2013, Calendar.DECEMBER, 30);
    	p4.setVencimento(c1.getTime());
    	
    	
    	TreeSet<Parcela> parcelas = new TreeSet<Parcela>();
    	parcelas.add(p2);
    	parcelas.add(p1);
    	parcelas.add(p3);
    	parcelas.add(p4);
    	
    	conta.setParcelas(parcelas);
    	
    	int count = 0;
    	for(Parcela p: conta.getParcelas()){
    		if(count == 0){
    			Calendar c = Calendar.getInstance();
    			c.setTime(p.getVencimento());
    			Assert.assertTrue(c.get(Calendar.DAY_OF_MONTH)==30 && c.get(Calendar.MONTH)==Calendar.DECEMBER && c.get(Calendar.YEAR)==2013);
    		}
    		if(count == 1){
    			Calendar c = Calendar.getInstance();
    			c.setTime(p.getVencimento());
    			Assert.assertTrue(c.get(Calendar.DAY_OF_MONTH)==21 && c.get(Calendar.MONTH)==Calendar.JANUARY && c.get(Calendar.YEAR)==2014);
    		}
    		if(count ==2){
    			Calendar c = Calendar.getInstance();
    			c.setTime(p.getVencimento());
    			Assert.assertTrue(c.get(Calendar.DAY_OF_MONTH)==8 && c.get(Calendar.MONTH)==Calendar.NOVEMBER && c.get(Calendar.YEAR)==2015);
    		}
    		if(count==3){
    			Calendar c = Calendar.getInstance();
    			c.setTime(p.getVencimento());
    			Assert.assertTrue(c.get(Calendar.DAY_OF_MONTH)==9 && c.get(Calendar.MONTH)==Calendar.NOVEMBER&& c.get(Calendar.YEAR)==2015);
    		}
    		count++;
    	}
    }
    
      
    
    
}
