package br.com.ceoestudos.ceogestao.model.builder;

import br.com.ceoestudos.ceogestao.model.Parcela;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author amhfilho
 */
public class ParcelaBuilder {
    
    private Parcela parcela;
    
    public ParcelaBuilder(){
        parcela = new Parcela();
    }
    
    public ParcelaBuilder id(Long id){
        parcela.setId(id);
        return this;
    }
    
    public ParcelaBuilder vencimento(Date vencimento){
        parcela.setVencimento(vencimento);
        return this;
    }
    
    public ParcelaBuilder pagamento(Date pagamento){
        parcela.setPagamento(pagamento);
        return this;
    }
    
    public ParcelaBuilder valor(BigDecimal valor){
        parcela.setValor(valor);
        return this;
    }
    
    public ParcelaBuilder obs(String obs){
        parcela.setObs(obs);
        return this;
    }
    
    public Parcela build(){
        return parcela;
    }
    
}
