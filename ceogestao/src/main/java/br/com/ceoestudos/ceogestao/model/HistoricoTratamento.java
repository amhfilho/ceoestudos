package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.validation.constraints.Size;

@Entity
@Table(name="Tratamento_historico")
public class HistoricoTratamento implements Serializable, Comparable<HistoricoTratamento> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public HistoricoTratamento() {
    }

    public HistoricoTratamento(Date data, String descricao) {
        this.dataHistorico = data;
        this.descricao = descricao;
    }

    public HistoricoTratamento(Date data, String descricao, Pessoa professor) {
        this.dataHistorico = data;
        this.descricao = descricao;
        this.professor = professor;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="data")
    private Date dataHistorico;

    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
    private String descricao;

    @ManyToOne
    @JoinColumn(name="professor_identificador")
    private Pessoa professor;
    
    @ManyToOne
    @JoinColumn(name="Tratamento_id")
    private Tratamento tratamento;
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final HistoricoTratamento other = (HistoricoTratamento) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Tratamento getTratamento() {
        return tratamento;
    }

    public void setTratamento(Tratamento tratamento) {
        this.tratamento = tratamento;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return dataHistorico;
    }

    public void setData(Date data) {
        this.dataHistorico = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pessoa getProfessor() {
        return professor;
    }

    public void setProfessor(Pessoa professor) {
        this.professor = professor;
    }

    @Override
    public int compareTo(HistoricoTratamento param) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        Calendar thisDate = Calendar.getInstance();
        thisDate.setTime(dataHistorico);
        thisDate.set(Calendar.HOUR, 0);
        thisDate.set(Calendar.MINUTE, 0);
        thisDate.set(Calendar.SECOND, 0);
        thisDate.set(Calendar.MILLISECOND, 0);

        Calendar paramDate = Calendar.getInstance();
        paramDate.setTime(param.getData());
        paramDate.set(Calendar.HOUR, 0);
        paramDate.set(Calendar.MINUTE, 0);
        paramDate.set(Calendar.SECOND, 0);
        paramDate.set(Calendar.MILLISECOND, 0);
        
        if(thisDate.before(paramDate)){
            return BEFORE;
        } else if(thisDate.after(paramDate)){
            return AFTER;
        } else {
            return EQUAL;
        }
    }

}
