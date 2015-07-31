
package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tratamento_dentes")
public class TratamentoDente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    public TratamentoDente(Integer dente, Procedimento procedimento, Integer quantidade) {
        this.dente = dente;
        this.procedimento = procedimento;
        this.quantidade = quantidade;
    }
    
    public TratamentoDente(){}
    
    public TratamentoDente(Long id){
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public BigDecimal getValor(){
        return  procedimento.getPreco().multiply(new BigDecimal(quantidade));
    }
    
    private Integer dente;
    
    @ManyToOne
    private Procedimento procedimento;
    
    @ManyToOne
    @JoinColumn(name="Tratamento_id")
    private Tratamento tratamento;
    
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

    public Long getId() {
        return id;
    }

    /**
     * @return the tratamento
     */
    public Tratamento getTratamento() {
        return tratamento;
    }

    /**
     * @param tratamento the tratamento to set
     */
    public void setTratamento(Tratamento tratamento) {
        this.tratamento = tratamento;
    }
}
