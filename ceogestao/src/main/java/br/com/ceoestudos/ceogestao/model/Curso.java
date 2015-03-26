/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 *
 * @author amhfilho
 */
@Entity
public class Curso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome do curso não pode exceder 255 caracteres")
    private String nome;
    @Size (max=3000, message = "Descrição não pode exceder 3000 caracteres")
    private String descricao;
    @NotNull(message = "Carga horária é obrigatória")
    @Min(value = 1, message = "Valor mínimo da carga horária deve ser 1")
    private Integer cargaHoraria;
    @Min(value = 0, message = "Investimento deve ser maior ou igual a zero")
    @Digits(integer = 8, fraction = 2)
    @NumberFormat(style = Style.NUMBER)
    private BigDecimal investimento = new BigDecimal(0);
    @Size(max = 3000, message = "Os pré-requisitos não podem exceder 3000 caracteres")
    private String preRequisitos;
    @Size(max = 255, message = "O código do curso não pode exceder 255 caracteres")
    private String codigo;
    
    @OneToMany(mappedBy = "curso",fetch = FetchType.EAGER)
    private Set<Turma> turmas;

    public int getTurmasNaoConfirmadas(){
        return getTurmasPorSituacao(SituacaoTurma.NAO_CONFIRMADA).size();
        
    }
    
    public int getTurmasCanceladas(){
        return getTurmasPorSituacao(SituacaoTurma.CANCELADA).size();
        
    }
    
    public int getTurmasConfirmadas(){
        return getTurmasPorSituacao(SituacaoTurma.CONFIRMADA).size();
    }
    
    public int getTurmasEmAndamento(){
        return getTurmasPorSituacao(SituacaoTurma.EM_ANDAMENTO).size();
    }
    
    /**
     * Filtro de turmas por situacao
     * @param situacao
     * @return 
     */
    public Set<Turma> getTurmasPorSituacao(SituacaoTurma situacao){
        Set<Turma> turmasPorSituacao = new HashSet<Turma>();
        for(Turma turma:turmas){
            if(turma.getSituacao()!=null && turma.getSituacao().equals(situacao)){
                turmasPorSituacao.add(turma);
            }
        }
        return turmasPorSituacao;
    }
    
    public Set<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }

    @Override
    public String toString() {
        return "Curso{" + "id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", cargaHoraria=" + cargaHoraria + ", investimento=" + investimento + ", preRequisitos=" + preRequisitos + ", codigo=" + codigo + '}';
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
        final Curso other = (Curso) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public BigDecimal getInvestimento() {
        return investimento;
    }

    public void setInvestimento(BigDecimal investimento) {
        this.investimento = investimento;
    }

    public String getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(String preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
