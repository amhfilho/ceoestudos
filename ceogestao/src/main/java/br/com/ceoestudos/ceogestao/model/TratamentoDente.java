
package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class TratamentoDente implements Serializable {

    public TratamentoDente(Integer dente, Procedimento procedimento, Integer quantidade) {
        this.dente = dente;
        this.procedimento = procedimento;
        this.quantidade = quantidade;
    }
    
    public TratamentoDente(){}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.dente != null ? this.dente.hashCode() : 0);
        hash = 29 * hash + (this.procedimento != null ? this.procedimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TratamentoDente other = (TratamentoDente) obj;
        if (this.dente != other.dente && (this.dente == null || !this.dente.equals(other.dente))) {
            return false;
        }
        if (this.procedimento != other.procedimento && (this.procedimento == null || !this.procedimento.equals(other.procedimento))) {
            return false;
        }
        return true;
    }
    
    
    private Integer dente;
    
    @ManyToOne
    private Procedimento procedimento;
    
    private Integer quantidade = 1;

    public Integer getDente() {
        return dente;
    }

    public void setDente(Integer dente) {
        this.dente = dente;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    
    
    
    
}
