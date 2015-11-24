package br.com.ceoestudos.ceogestao.model;

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
public class Parcela implements Serializable , Comparable<Parcela> {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date vencimento;
    
        
    @Digits(integer = 8, fraction = 2)
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal valor = BigDecimal.ZERO;
    
    private String obs;
    
    public static final String PAGA = "PAGA";
    public static final String NAO_PAGA = "NAO_PAGA";
    
    public Parcela()   {  }
    
    public Parcela(Date vencimento, BigDecimal valor) {
        this.vencimento = vencimento;
        this.valor = valor;
    }
    
    public Parcela(Long id){
    	this.id = id;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parcela other = (Parcela) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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


	@Override
	public int compareTo(Parcela o) {
		if(this.getVencimento().before(o.getVencimento())){
			return -1;
		}
		else if(this.getVencimento().after(o.getVencimento())){
			return 1;
		}
		return 0;
	}
    
    
    
}
