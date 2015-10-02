package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;

@SuppressWarnings("serial")
@Embeddable
@Table(name="tratamento_historico")
public class HistoricoTratamento implements Serializable, Comparable<HistoricoTratamento> {

    public HistoricoTratamento() {
    }

    public HistoricoTratamento(Date data, String descricao) {
        this.data = data;
        this.descricao = descricao;
    }

    public HistoricoTratamento(Date data, String descricao, Pessoa professor) {
        this.data = data;
        this.descricao = descricao;
        this.professor = professor;
    }

    @Temporal(TemporalType.DATE)
    private DateTime data;

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

    @Override
    public int compareTo(HistoricoTratamento param) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        Calendar thisDate = Calendar.getInstance();
        thisDate.setTime(data);
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
