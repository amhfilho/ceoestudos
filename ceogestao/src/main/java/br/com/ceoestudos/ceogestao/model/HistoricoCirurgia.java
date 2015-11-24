package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="cirurgia_historico")
public class HistoricoCirurgia implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="data")
    @Temporal(TemporalType.DATE)
    private Date dataHistorico;
    
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name="cirurgia_id")
    private Cirurgia cirurgia;

    public HistoricoCirurgia(Date dataHistorico, String descricao, Cirurgia cirurgia) {
        this.dataHistorico = dataHistorico;
        this.descricao = descricao;
        this.cirurgia = cirurgia;
    }

    protected HistoricoCirurgia() {
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final HistoricoCirurgia other = (HistoricoCirurgia) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Cirurgia getCirurgia() {
        return cirurgia;
    }

    public void setCirurgia(Cirurgia cirurgia) {
        this.cirurgia = cirurgia;
    }
    
    
}
