package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;

@Embeddable
public class HistoricoTratamento implements Serializable {
    
    public HistoricoTratamento(){}
    
    public HistoricoTratamento(Date data, String descricao){
        this.data = data;
        this.descricao = descricao;
    }
    
    private Date data;
    private String descricao;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.data != null ? this.data.hashCode() : 0);
        hash = 37 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
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
    
    
}
