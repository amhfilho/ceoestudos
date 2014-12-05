package br.com.ceoestudos.ceogestao.model;

import br.com.ceoestudos.ceogestao.util.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author amhfilho
 */
@Entity
public class Parcela implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date vencimento;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date pagamento;
    
    @Digits(integer = 8, fraction = 2)
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal valor;
    
    private String obs;
    
    public static final String PAGA = "PAGA";
    public static final String NAO_PAGA = "NAO_PAGA";
    
    public Parcela()   {  }
    
    public Parcela(Date vencimento, BigDecimal valor) {
        this.vencimento = vencimento;
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.vencimento != null ? this.vencimento.hashCode() : 0);
        hash = 79 * hash + (this.pagamento != null ? this.pagamento.hashCode() : 0);
        hash = 79 * hash + (this.valor != null ? this.valor.hashCode() : 0);
        hash = 79 * hash + (this.obs != null ? this.obs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        Util util = new Util();
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Parcela other = (Parcela) obj;
        if (!util.sameDate(vencimento, other.vencimento)) {
            return false;
        }
        if (!util.sameDate(pagamento, other.pagamento)) {
            return false;
        }
        if (this.valor != other.valor && (this.valor == null || !this.valor.equals(other.valor))) {
            return false;
        }
        if ((this.obs == null) ? (other.obs != null) : !this.obs.equals(other.obs)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Parcela{" + "id=" + id + ", vencimento=" + vencimento + ", pagamento=" + pagamento + ", valor=" + valor + ", obs=" + obs + '}';
    }
    
    

    /**
     * @return the vencimento
     */
    public Date getVencimento() {
        return vencimento;
    }

    /**
     * @param vencimento the vencimento to set
     */
    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    /**
     * @return the pagamento
     */
    public Date getPagamento() {
        return pagamento;
    }

    /**
     * @param pagamento the pagamento to set
     */
    public void setPagamento(Date pagamento) {
        this.pagamento = pagamento;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

    /**
     * @param obs the obs to set
     */
    public void setObs(String obs) {
        this.obs = obs;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the conta
     */
    public Conta getConta() {
        return conta;
    }

    /**
     * @param conta the conta to set
     */
    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
    
    
}
