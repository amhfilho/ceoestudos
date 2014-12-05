package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 0, message = "Valor deve ser maior ou igual a zero")
    @Digits(integer = 8, fraction = 2)
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal valor = new BigDecimal(0);

    @Size(max = 255, message = "A descrição não pode conter mais que 255 caracteres")
    private String descricao;

    private SituacaoConta situacao;

    @ManyToOne
    @NotNull(message = "O Cliente deve ser informado")
    private Pessoa cliente;

    @ManyToOne
    private Turma turma;

    private PapelPessoa papel;

    private String tipoConta;

    private String formaPagamento;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Parcela> parcelas;

    public void addParcela(Parcela parcela) {
        if (parcelas == null) {
            parcelas = new HashSet<Parcela>();
        }
        parcelas.add(parcela);
    }

    public BigDecimal getValor(String tipo) {
        BigDecimal total = new BigDecimal(0);
        if (parcelas == null) {
            return total;
        }
        for (Parcela p : parcelas) {
            if ((tipo.equals(Parcela.PAGA) && p.getPagamento() != null)
                    || (tipo.equals(Parcela.NAO_PAGA) && p.getPagamento() == null)
                    || (tipo.equals("TOTAL"))) {

                total = total.add(p.getValor());
            }
        }
        return total;
    }
    
    public SituacaoConta getSituacao(){
        boolean paga=false;
        if(parcelas==null){
            return SituacaoConta.PENDENTE;
        } else {
            for (Parcela p: parcelas){
                if(p.getPagamento()!=null){
                    paga=true;
                }
                else if(p.getPagamento()==null && paga){
                    return SituacaoConta.PAGA_PARCIAL;
                }
            }
            if(paga){
                return SituacaoConta.PAGA;
            } else {
                return SituacaoConta.PENDENTE;
            }
        }
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
        return "Conta{" + "id=" + id + ", valor=" + valor + ", descricao=" + descricao + ", situacao=" + situacao + ", cliente=" + cliente + ", turma=" + turma + ", papel=" + papel + ", tipoConta=" + tipoConta + ", formaPagamento=" + formaPagamento + ", parcelas=" + parcelas + '}';
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

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
