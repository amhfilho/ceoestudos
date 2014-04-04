package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Tratamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Turma turma;
    
    @ManyToOne
    private Pessoa paciente;

    private String obs;

    @ElementCollection(targetClass = TratamentoDente.class, fetch = FetchType.EAGER)
    private Collection<TratamentoDente> dentes;

    @Override
    public String toString() {
        return "Tratamento{" + "id=" + id + ", turma=" + turma + ", paciente=" + paciente + ", obs=" + obs + ", dentes=" + printDentes() + '}';
    }
    
    public void addTratamento(Integer dente, int qtd, Procedimento procedimento) {
        if (dentes == null) {
            dentes = new ArrayList<TratamentoDente>();
        }
        TratamentoDente td = new TratamentoDente(dente, procedimento, qtd);
        if (dentes.contains(td)) {
            dentes.remove(td);
        }
        dentes.add(td);
    }

    public void removeTratamento(TratamentoDente t) {
        if (dentes != null) {
            dentes.remove(t);
        }
    }

    public List<TratamentoDente> getTratamentosPorDente(Integer dente) {
        List<TratamentoDente> lista = new ArrayList<TratamentoDente>();
        for (TratamentoDente td : dentes) {
            if (td.getDente() == dente) {
                lista.add(td);
            }
        }
        return lista;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Tratamento other = (Tratamento) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    private String printDentes(){
        String retorno = "";
        if(dentes!=null){
            retorno+="\t";
            for(TratamentoDente td:dentes){
                retorno+=td.getDente()+","+td.getProcedimento().getNome()+","+td.getQuantidade()+"\n";
            }
        }
        return retorno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Collection<TratamentoDente> getDentes() {
        return dentes;
    }

    public void setDentes(Collection<TratamentoDente> dentes) {
        this.dentes = dentes;
    }

    public Pessoa getPaciente() {
        return paciente;
    }

    public void setPaciente(Pessoa paciente) {
        this.paciente = paciente;
    }
    
    

}
