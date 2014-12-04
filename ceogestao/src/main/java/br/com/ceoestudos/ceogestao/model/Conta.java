package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Conta implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    //@NotNull(message = "Data de vencimento deve ser informada")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date vencimento;
    
    @Min(value = 0, message = "Valor deve ser maior ou igual a zero")
    @Digits(integer = 8, fraction = 2)
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal valor = new BigDecimal(0);
    
    @Size(max=255, message="A descrição não pode conter mais que 255 caracteres")
    private String descricao;
    
    private SituacaoConta situacao;
    
    @ManyToOne
    @NotNull(message="O Cliente deve ser informado")
    private Pessoa cliente;
    
    @ManyToOne
    private Turma turma;
    
    private PapelPessoa papel;
    
    private String tipoConta;
    
    private String formaPagamento;
    
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@NotEmpty (message = "Deve haver no mínimo uma parcela")
    private Set<Parcela> parcelas;

    public void addParcela(Parcela parcela){
        if(parcelas==null){
            parcelas = new HashSet<Parcela>();
        }
        parcelas.add(parcela);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Conta other = (Conta) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Conta{" + "id=" + id + ", vencimento=" + vencimento + ", valor=" + valor + ", descricao=" + descricao + ", situacao=" + situacao + ", cliente=" + cliente + ", turma=" + turma + ", papel=" + papel + ", tipoConta=" + tipoConta + ", formaPagamento=" + formaPagamento + ", parcelas=" + parcelas + '}';
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public BigDecimal getValor() {
        BigDecimal total = new BigDecimal(0);
        if(parcelas==null){
            return total;
        }
        for (Parcela p:parcelas){
            total = total.add(p.getValor());
        }
        return total;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public SituacaoConta getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoConta situacao) {
        this.situacao = situacao;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public PapelPessoa getPapel() {
        return papel;
    }

    public void setPapel(PapelPessoa papel) {
        this.papel = papel;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /**
     * @return the parcelas
     */
    public Set<Parcela> getParcelas() {
        return parcelas;
    }

    /**
     * @param parcelas the parcelas to set
     */
    public void setParcelas(Set<Parcela> parcelas) {
        this.parcelas = parcelas;
    }
    
    
    
}
