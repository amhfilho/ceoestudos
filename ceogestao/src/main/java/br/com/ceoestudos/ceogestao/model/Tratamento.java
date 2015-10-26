package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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

    @OneToMany(mappedBy = "tratamento", cascade = {CascadeType.ALL})
    private Set<TratamentoDente> dentes;
    
//    @Min(value = 0, message = "Valor deve ser maior ou igual a zero")
//    @Digits(integer = 8, fraction = 2)
//    @NumberFormat(style = NumberFormat.Style.NUMBER)
//    private BigDecimal valor = BigDecimal.ZERO;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "TRATAMENTO_RESPONSAVEL",
            joinColumns = @JoinColumn(name = "TRATAMENTO_ID"),
            inverseJoinColumns = @JoinColumn(name = "RESPONSAVEL_ID"))
    private Set<Pessoa> responsaveis;
    
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "Tratamento_id")
    @OrderBy("dataHistorico DESC")
    private Set<HistoricoTratamento> historico;
    
    @Min(value = 0, message = "Taxa deve ser maior ou igual a zero")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Double taxa = 0d;
    
    @OneToMany(mappedBy = "tratamento", cascade = {CascadeType.ALL})
    private Set<ProcedimentoAvulso> procedimentosAvulsos;
    
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private StatusTratamento status = StatusTratamento.NAO_APROVADO;
    
    public BigDecimal getTotalTratamento(){
        BigDecimal total = BigDecimal.ZERO;
        if(procedimentosAvulsos!=null){
            for(ProcedimentoAvulso avulso:procedimentosAvulsos){
                total = total.add(avulso.getTotal());
            }
        }
        total = total.add(getValorBruto());
        return total;
    }
    
    
    public void addProcedimentoAvulso(Procedimento procedimento,Integer qtd){
        if(procedimentosAvulsos==null){
            procedimentosAvulsos = new HashSet<ProcedimentoAvulso>();
        }
        ProcedimentoAvulso avulso = getProcedimentoAvulsoPorProcedimento(procedimento);
        if(avulso == null){
            avulso = new ProcedimentoAvulso();
            avulso.setProcedimento(procedimento);
            avulso.setQtd(qtd);
            avulso.setTratamento(this);
            procedimentosAvulsos.add(avulso);
        } else {
            avulso.setQtd(avulso.getQtd()+qtd);
        }       
    }
    
    public void removeProcedimentoAvulso(ProcedimentoAvulso procedimentoAvulso){
        if(procedimentosAvulsos!=null){
            procedimentosAvulsos.remove(procedimentoAvulso);
        }
    }
    
    public ProcedimentoAvulso getProcedimentoAvulsoPorProcedimento(Procedimento p){
        if(getProcedimentosAvulsos()!=null){
            for(ProcedimentoAvulso avulso:getProcedimentosAvulsos()){
                if(avulso.getProcedimento().equals(p)){
                    return avulso;
                }
            }
        }
        return null;
    }

    public Set<ProcedimentoAvulso> getProcedimentosAvulsos() {
        return procedimentosAvulsos;
    }

    public void setProcedimentosAvulsos(Set<ProcedimentoAvulso> procedimentosAvulsos) {
        this.procedimentosAvulsos = procedimentosAvulsos;
    }

    public Double getTaxa() {
        if(taxa==null){
            taxa = 0.0;
        }
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }
    
    public void adicionarHistorico(HistoricoTratamento ht){
        if(historico==null){
            historico = new HashSet<HistoricoTratamento>();
        }
        ht.setTratamento(this);
        historico.add(ht);
    }
    
    public void removerHistorico(Date data,String descricao){
        if(historico!=null){
            historico.remove(new HistoricoTratamento(data, descricao));
        }
    }
    
    
    
    @Override
    public String toString() {
        return "Tratamento{" + "id=" + id + ", turma=" + turma + ", paciente=" + paciente + ", obs=" + obs + ", dentes=" + printDentes() + '}';
    }
    
    public BigDecimal getValorBruto(){
        BigDecimal valor = BigDecimal.ZERO;
        if(getDentes()!=null){
            for(TratamentoDente td:getDentes()){
                valor = valor.add(td.getValor());
            }
        }
        
        return valor;
    }
    
    public BigDecimal getValorComTaxa(){
        double fator = 1 + getTaxa()/100;
        return getTotalTratamento().multiply(new BigDecimal(fator));
        
    }
    
    public void addTratamentoDente(Integer dente, int qtd, Procedimento procedimento) {
        if (getDentes() == null) {
            setDentes(new HashSet<TratamentoDente>());
        }
        TratamentoDente td = new TratamentoDente(dente, procedimento, qtd);
        td.setTratamento(this);
        if (getDentes().contains(td)) {
            getDentes().remove(td);
        }
        getDentes().add(td);
    }
    
    public void removeTratamentoDente(Integer dente,Long idProcedimento) {
        if (getDentes() != null) {
            TratamentoDente td = new TratamentoDente();
            td.setDente(dente);
            Procedimento p = new Procedimento();
            p.setId(idProcedimento);
            td.setProcedimento(p);
            getDentes().remove(td);
            td.setTratamento(null);
        }
    }
    
    public void removerTratamentoDente(TratamentoDente td){
        if(getDentes()!=null){
            getDentes().remove(td);
            td.setTratamento(null);
        }
    }
    
    public void addResponsavel(Pessoa p){
        if(responsaveis==null){
            responsaveis = new HashSet<Pessoa>();
        }
        responsaveis.add(p);
    }
    
    public void removeResponsavel(Pessoa p){
        if(responsaveis!=null){
            responsaveis.remove(p);
        }
        
    }
    
    public void removeTratamento(TratamentoDente t) {
        if (getDentes() != null) {
            getDentes().remove(t);
        }
    }

    public List<TratamentoDente> getTratamentosPorDente(Integer dente) {
        List<TratamentoDente> lista = new ArrayList<TratamentoDente>();
        for (TratamentoDente td : getDentes()) {
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
        if(getDentes()!=null){
            retorno+="\t";
            for(TratamentoDente td:getDentes()){
                retorno+=td.getDente()+","+td.getProcedimento().getNome()+","+td.getQuantidade()+"\n";
            }
        }
        return retorno;
    }

    public Set<Pessoa> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(Set<Pessoa> responsaveis) {
        this.responsaveis = responsaveis;
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

    public Set<TratamentoDente> getDentes() {
        return dentes;
    }

    public void setDentes(Set<TratamentoDente> dentes) {
        this.dentes = dentes;
    }

    public Pessoa getPaciente() {
        return paciente;
    }

    public void setPaciente(Pessoa paciente) {
        this.paciente = paciente;
    }

    public StatusTratamento getStatus() {
        return status;
    }

    public void setStatus(StatusTratamento status) {
        this.status = status;
    }



    public Set<HistoricoTratamento> getHistorico() {
        return historico;
    }

    public void setHistorico(Set<HistoricoTratamento> historico) {
        this.historico = historico;
    }

}
