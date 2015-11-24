package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Conta implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int QUITADAS = 0;
	public static final int SALDO_DEVEDOR = 1;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@DecimalMin(value = "1.0", message = "Valor deve ser maior ou igual a 1,00")
    @Digits(integer = 8, fraction = 2)
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal valor = BigDecimal.ZERO;

    @Size(max = 255, message = "A descrição não pode conter mais que 255 caracteres")
    private String descricao;

    @ManyToOne
    @NotNull(message = "O Cliente deve ser informado")
    private Pessoa cliente;

    @ManyToOne
    private Turma turma;

    private String tipoConta;

    private String formaPagamento;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Parcela> parcelas;
    
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    private Set<Pagamento> pagamentos;
      
    public Set<Pagamento> getPagamentos(){
    	return this.pagamentos;
    }
    
    @Column(name="taxa_juros")
    private Double taxaJuros = 0D;
    
    public void addPagamento(Pagamento p){
    	if(pagamentos==null){
    		pagamentos = new HashSet<Pagamento>();
    	}
    	p.setConta(this);
    	pagamentos.add(p);
    }
    
    public void removePagamentos(Pagamento p){
    	if(pagamentos!=null){
    		pagamentos.remove(p);
    	}
    }

    public void addParcela(Parcela parcela) {
        if (parcelas == null) {
            parcelas = new TreeSet<Parcela>();
        }
        parcela.setConta(this);
        parcelas.add(parcela);
    }

    public BigDecimal getValor() {
        return this.valor;
    }
    
    public BigDecimal getTotal(){
    	BigDecimal total = BigDecimal.ZERO;
    	if(parcelas!=null){
    		for(Parcela p:parcelas){
    			total = total.add(p.getValor());
    		}
    	}
    	return total;
    }
    
    public BigDecimal getValorPago(){
    	BigDecimal total = BigDecimal.ZERO;
    	if(pagamentos!=null){
    		for(Pagamento p:pagamentos){
    			total = total.add(p.getValor());
    		}
    	}
    	return total;
    }
    
    public BigDecimal getSaldoDevedor(){
    	return getTotal().subtract(getValorPago());
    }
    
      
    public static Conta createContaFromTratamento(Tratamento tratamento){
    	Conta conta = new Conta();
    	conta.setCliente(tratamento.getPaciente());
    	conta.setDescricao("Orçamento aprovado");
    	conta.setTipoConta("A_RECEBER");
    	conta.setTurma(tratamento.getTurma());
    	conta.setValor(tratamento.getValorComTaxa());
    	conta.addParcela(new Parcela(new Date(),tratamento.getValorComTaxa()));
    	return conta;
    }
    
    public void aplicarTaxaJuros(int numParcelas, Double taxa, Calendar dataPrimeiraParcela){
    	this.taxaJuros = taxa;
    	BigDecimal valorParcela = new BigDecimal(String.valueOf(FinanceLib.pmt(taxa/100, numParcelas, getValor().doubleValue(), 0.0, false)));
    	valorParcela = valorParcela.setScale(2, RoundingMode.UP).abs();
    	cleanParcelas();
    	
    	for(int i =0; i < numParcelas; i++){
    		Parcela p = new Parcela();
    		p.setConta(this);
    		String strTaxa = new DecimalFormat("#.##").format(taxa);
    		p.setObs(String.format("Valor obtido a partir de aplicação de taxa de juros de %s",strTaxa));
    		p.setValor(valorParcela);
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(dataPrimeiraParcela.getTime());
    		cal.add(Calendar.MONTH, i);
    		p.setVencimento(cal.getTime());
    		addParcela(p);
    	}
    }

	private void cleanParcelas() {
		if(parcelas!=null){
    		parcelas.removeAll(parcelas);
    	}
	}
    
	public void removeParcela(Parcela p){
		BigDecimal subtract = getTotal().subtract(p.getValor());
		if(subtract.compareTo(BigDecimal.ZERO)==0){
    		throw new IllegalStateException("A soma das parcelas deve ser maior que 0");
    	}
		if(parcelas!=null){
			parcelas.remove(p);
			p.setConta(null);
		}
	}
	
    public void atualizarValor(){
    	setValor(this.getTotal());
    }
    
    public void atualizarValor(BigDecimal valor){
    	cleanParcelas();
    	this.valor = valor;
    	Parcela parcela = new Parcela();
    	parcela.setValor(valor);
    	parcela.setVencimento(new Date());
    	parcela.setObs("Parcela única");
    	addParcela(parcela);
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

	public void setPagamentos(Set<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public Double getTaxaJuros() {
		return taxaJuros;
	}
}
