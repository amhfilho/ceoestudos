package com.ceoestudos.ceogestao;

import br.com.ceoestudos.ceogestao.model.Conta;
import br.com.ceoestudos.ceogestao.model.Parcela;
import br.com.ceoestudos.ceogestao.model.SituacaoConta;
import br.com.ceoestudos.ceogestao.model.builder.ParcelaBuilder;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;
import org.junit.Test;



/**
 *
 * @author amhfilho
 */
public class ContaTest {
    
    @Test
    public void deveRetornarPendenteParaParcelasNull(){
        Assert.assertEquals(SituacaoConta.PENDENTE, new Conta().getSituacao());
    }
    
    @Test
    public void deveRetornarPendenteParaParcelasVazia(){
        Conta conta = new Conta();
        Set<Parcela> parcelas = new HashSet<Parcela>();
        conta.setParcelas(parcelas);
        Assert.assertEquals(SituacaoConta.PENDENTE, conta.getSituacao());
    }
    
    @Test
    public void deveRetornarContaPagaTodasAsParcelasPagas(){
        Conta conta = new Conta();
        Set<Parcela> parcelas = new HashSet<Parcela>();
        Parcela p1 = new ParcelaBuilder().pagamento(new Date()).build();
        Parcela p2 = new ParcelaBuilder().pagamento(new Date()).build();
        Parcela p3 = new ParcelaBuilder().pagamento(new Date()).build();
        parcelas.add(p1);
        parcelas.add(p2);
        parcelas.add(p3);
        
        conta.setParcelas(parcelas);
        
        Assert.assertEquals(SituacaoConta.PAGA,conta.getSituacao());
    }
    
    @Test
    public void deveRetornarPendenteParaTodasAsParcelasNaoPagas(){
        Conta conta = new Conta();
        Set<Parcela> parcelas = new HashSet<Parcela>();
        Parcela p1 = new ParcelaBuilder().build();
        Parcela p2 = new ParcelaBuilder().build();
        Parcela p3 = new ParcelaBuilder().build();
        parcelas.add(p1);
        parcelas.add(p2);
        parcelas.add(p3);
        
        conta.setParcelas(parcelas);
        
        Assert.assertEquals(SituacaoConta.PENDENTE,conta.getSituacao());
    }
    
    @Test
    public void deveRetornarPagaParcialParaUmaParcelaPagaEDemaisNaoPagas(){
        Conta conta = new Conta();
        Set<Parcela> parcelas = new HashSet<Parcela>();
        Parcela p1 = new ParcelaBuilder().pagamento(new Date()).build();
        Parcela p2 = new ParcelaBuilder().build();
        Parcela p3 = new ParcelaBuilder().build();
        parcelas.add(p1);
        parcelas.add(p2);
        parcelas.add(p3);
        
        conta.setParcelas(parcelas);
        
        Assert.assertEquals(SituacaoConta.PAGA_PARCIAL,conta.getSituacao());
    }
}
