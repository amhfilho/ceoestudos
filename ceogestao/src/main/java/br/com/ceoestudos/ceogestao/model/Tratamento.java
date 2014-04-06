package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Tratamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Turma turma;
    
    @ManyToOne
    @NotNull(message = "Deve haver um paciente associado ao tratamento")
    private Pessoa paciente;

    private String obs;

    @ElementCollection(targetClass = TratamentoDente.class, fetch = FetchType.EAGER)
    private List<TratamentoDente> dentes;
    
    @Min(value = 0, message = "Valor deve ser maior ou igual a zero")
    @Digits(integer = 8, fraction = 2)
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal valor = new BigDecimal(0);
    
    @Override
    public String toString() {
        return "Tratamento{" + "id=" + id + ", turma=" + turma + ", paciente=" + paciente + ", obs=" + obs + ", dentes=" + printDentes() + '}';
    }
    
    public BigDecimal getValorBruto(){
        BigDecimal valor = new BigDecimal(0);
        if(dentes!=null){
            for(TratamentoDente td:dentes){
                valor = valor.add(td.getValor());
            }
        }
        
        return valor;
    }
    
    public void addTratamentoDente(Integer dente, int qtd, Procedimento procedimento) {
        if (dentes == null) {
            dentes = new ArrayList<TratamentoDente>();
        }
        TratamentoDente td = new TratamentoDente(dente, procedimento, qtd);
        if (dentes.contains(td)) {
            dentes.remove(td);
        }
        dentes.add(td);
    }
    
    public void removeTratamentoDente(Integer dente,Long idProcedimento) {
        if (dentes != null) {
            TratamentoDente td = new TratamentoDente();
            td.setDente(dente);
            Procedimento p = new Procedimento();
            p.setId(idProcedimento);
            td.setProcedimento(p);
            dentes.remove(td);
            
        }
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

    public List<TratamentoDente> getDentes() {
        return dentes;
    }

    public void setDentes(List<TratamentoDente> dentes) {
        this.dentes = dentes;
    }

    public Pessoa getPaciente() {
        return paciente;
    }

    public void setPaciente(Pessoa paciente) {
        this.paciente = paciente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    

}
