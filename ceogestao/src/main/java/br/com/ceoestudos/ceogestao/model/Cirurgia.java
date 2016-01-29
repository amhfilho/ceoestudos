package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Afilho
 */
@Entity
public class Cirurgia implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    @OneToMany(mappedBy = "cirurgia")
    private List<HistoricoCirurgia> historico;
    
    public void adicionarHistorico(Date data, String descricao){
        if(historico==null){
            historico = new ArrayList<HistoricoCirurgia>();
        }
        historico.add(new HistoricoCirurgia(data, descricao, this));
    }
    
    public void removerHistorico(Date data, String descricao, Pessoa professor){
        if(historico!=null){
            HistoricoCirurgia ht = new HistoricoCirurgia(data, descricao, this);
            historico.remove(ht);
        }
    }
    
    public Date getDataPrimeiroProcedimento(){
        if(historico!=null && historico.size() > 0){
            historico.get(0);
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

    public List<HistoricoCirurgia> getHistorico() {
        return historico;
    }

    public void setHistorico(List<HistoricoCirurgia> historico) {
        this.historico = historico;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    
    
}
