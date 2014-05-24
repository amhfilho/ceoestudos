package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Embeddable
public class HistoricoTratamento implements Serializable {
    
    public HistoricoTratamento(){}
    
    public HistoricoTratamento(Date data, String descricao){
        this.data = data;
        this.descricao = descricao;
    }

    public HistoricoTratamento(Date data, String descricao, Pessoa professor) {
        this.data = data;
        this.descricao = descricao;
        this.professor = professor;
    }
    
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
    private String descricao;
    
    @ManyToOne
    private Pessoa professor;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.data != null ? this.data.hashCode() : 0);
        hash = 73 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        hash = 73 * hash + (this.professor != null ? this.professor.hashCode() : 0);
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
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        if (this.professor != other.professor && (this.professor == null || !this.professor.equals(other.professor))) {
            return false;
        }
        return true;
    }

    

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
    
    
    
    
}
