package br.com.ceoestudos.ceogestao.model;

import br.com.ceoestudos.ceogestao.util.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author amhfilho
 */
@Entity
public class Turma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message="Deve haver um curso associado a esta turma")
    @ManyToOne
    private Curso curso;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    
    /**
     * Duração da aula em minutos
     */
    @Min(value=1, message="O valor mínimo de duração da aula é 1 minuto")
    private Integer minutosAula;

    @Min(value=0, message="O Quorum mínimo não pode ter um valor negativo")
    private Integer quorumMinimo;

    private String sala;

    private String observacoes;

    private SituacaoTurma situacao = SituacaoTurma.NAO_CONFIRMADA;

//    @ManyToOne(cascade = CascadeType.ALL)
//    private Pessoa professor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TURMA_ALUNO",
            joinColumns = @JoinColumn(name = "TURMA_ID"),
            inverseJoinColumns = @JoinColumn(name = "ALUNO_ID"))
    private Set<Pessoa> alunos;
    
    @Size(min=1,message="Deve haver ao menos um dia da semana associada à turma")
    private String[] diasDaSemana;

    @Override
    public String toString() {
        Util util = new Util();
        return "Turma{" + "id=" + id + ", curso=" + curso + ", dataInicio=" + dataInicio + ", minutosAula=" + minutosAula 
                + ", quorumMinimo=" + quorumMinimo + ", sala=" + sala + ", observacoes=" + observacoes + ", situacao=" 
                + situacao + ", alunos=" + util.toString(alunos) + ", diasDaSemana=" + util.arrayToString(diasDaSemana) + '}';
    }
   

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Turma other = (Turma) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Integer getMinutosAula() {
        return minutosAula;
    }

    public void setMinutosAula(Integer minutosAula) {
        this.minutosAula = minutosAula;
    }
     
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
   }

    public Integer getQuorumMinimo() {
        return quorumMinimo;
    }

    public void setQuorumMinimo(Integer quorumMinimo) {
        this.quorumMinimo = quorumMinimo;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public SituacaoTurma getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoTurma situacao) {
        this.situacao = situacao;
    }

//    public Pessoa getProfessor() {
//        return professor;
//    }
//
//    public void setProfessor(Pessoa professor) {
//        this.professor = professor;
//    }

    public Set<Pessoa> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<Pessoa> alunos) {
        this.alunos = alunos;
    }

    public String[] getDiasDaSemana() {
        return diasDaSemana;
    }

    public void setDiasDaSemana(String[] diasDaSemana) {
        this.diasDaSemana = diasDaSemana;
    }

    
}
