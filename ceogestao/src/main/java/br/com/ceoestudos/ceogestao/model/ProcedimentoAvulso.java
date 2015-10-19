package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name="procedimento_avulso")
public class ProcedimentoAvulso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tratamento_id")
    private Tratamento tratamento;
    
    @ManyToOne
    @JoinColumn(name = "procedimento_id")
    private Procedimento procedimento;
    
    @Column(name="qtd")
    private Integer qtd;
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ProcedimentoAvulso other = (ProcedimentoAvulso) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public BigDecimal getTotal(){
        BigDecimal total = BigDecimal.ZERO;
        if(procedimento!=null){
            total = procedimento.getPreco();
            total = total.multiply(new BigDecimal(qtd));
        }
        return total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    /**
     * @return the procedimento
     */
    public Procedimento getProcedimento() {
        return procedimento;
    }

    /**
     * @param procedimento the procedimento to set
     */
    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    /**
     * @return the qtd
     */
    public Integer getQtd() {
        return qtd;
    }

    /**
     * @param qtd the qtd to set
     */
    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }
    
}
