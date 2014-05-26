package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Afilho
 */
@Entity
public class Cirurgia implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull(message="Deve haver um paciente associado à cirurgia")
    @ManyToOne
    private Pessoa paciente;
    
    @ManyToOne
    private Turma turma;
    
    private String exameRadiografico;
    
    private String planejamento;
    
    private String antibiotico;
    
    private String antiinflamatorio;
    
    private String ansiolitico;
    
    @ElementCollection(targetClass = HistoricoTratamento.class, fetch = FetchType.EAGER)
    private Set<HistoricoTratamento> historico;
    
    public void adicionarHistorico(Date data, String descricao, Pessoa professor){
        if(historico==null){
            historico = new LinkedHashSet<HistoricoTratamento>();
        }
        historico.add(new HistoricoTratamento(data, descricao, professor));
    }
    
    public void removerHistorico(Date data, String descricao, Pessoa professor){
        if(historico!=null){
            HistoricoTratamento ht = new HistoricoTratamento(data, descricao, professor);
            historico.remove(ht);
        }
    }
    
    public Date getDataPrimeiroProcedimento(){
        if(historico!=null && historico.size() > 0){
            Iterator i = historico.iterator();
            return ((HistoricoTratamento)i.next()).getData();
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPaciente() {
        return paciente;
    }

    public void setPaciente(Pessoa paciente) {
        this.paciente = paciente;
    }

    public String getExameRadiografico() {
        return exameRadiografico;
    }

    public void setExameRadiografico(String exameRadiografico) {
        this.exameRadiografico = exameRadiografico;
    }

    public String getPlanejamento() {
        return planejamento;
    }

    public void setPlanejamento(String planejamento) {
        this.planejamento = planejamento;
    }

    public String getAntibiotico() {
        return antibiotico;
    }

    public void setAntibiotico(String antibiotico) {
        this.antibiotico = antibiotico;
    }

    public String getAntiinflamatorio() {
        return antiinflamatorio;
    }

    public void setAntiinflamatorio(String antiinflamatorio) {
        this.antiinflamatorio = antiinflamatorio;
    }

    public String getAnsiolitico() {
        return ansiolitico;
    }

    public void setAnsiolitico(String ansiolitico) {
        this.ansiolitico = ansiolitico;
    }

    public Set<HistoricoTratamento> getHistorico() {
        return historico;
    }

    public void setHistorico(Set<HistoricoTratamento> historico) {
        this.historico = historico;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    
    
}
