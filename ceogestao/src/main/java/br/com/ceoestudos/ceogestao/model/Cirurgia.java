package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
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
    
    @NotNull
    @ManyToOne
    private Pessoa paciente;
    
    private String exameRadiografico;
    
    private String planejamento;
    
    private String antibiotico;
    
    private String antiinflamatorio;
    
    private String ansiolitico;
    
    @ElementCollection(targetClass = HistoricoTratamento.class, fetch = FetchType.EAGER)
    private Set<HistoricoTratamento> historico;

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
    
    
}
