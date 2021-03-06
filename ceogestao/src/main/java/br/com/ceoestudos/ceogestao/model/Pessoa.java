package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author amhfilho
 */
@Entity
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 6884596062394504011L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;
    @NotNull(message="Nome é obrigatório")
    private String nome;
    @Email(message = "Formato de e-mail inválido")
    private String email;
    private String endereco;
    private String numero;
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;
    private String rg;
    //@NotNull(message="CPF é obrigatório")
    
    private String cpf;
    private String telefoneCelular;
    private String telefoneResidencial;
    private String telefoneComercial;
    private String cro;
    private String ufCro;
    private String complemento;
    private Sexo sexo;
    private boolean professor;
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipo;
        
    @ManyToMany(mappedBy = "alunos")
    private Set<Turma> turmas;
    
    @OneToMany(mappedBy = "cliente")
    private Set<Conta> contas;
    
    public Set<Conta> getContas(){
    	return this.contas;
    }
    
    public BigDecimal getValorTotalContas(){
    	BigDecimal total = BigDecimal.ZERO;
    	if(contas!=null){
    		for(Conta c:contas){
    			total = total.add(c.getTotal());
    		}
    	}
    	return total;
    	
    }
    
    public String getNomeTurmas(){
        if(turmas!=null && turmas.size() > 0){
            String retorno = "";
            for (Turma t:turmas){
                retorno+=t.getCurso().getNome()+", ";
            }
            return retorno;
        }
        return "Nenhum curso associado";
    }
    
    public BigDecimal getSaldoDevedor(){
    	return getValorTotalContas().subtract(getValorPago());
    }
    
    public BigDecimal getValorPago(){
    	BigDecimal total = BigDecimal.ZERO;
    	if(contas!=null){
    		for(Conta c:contas){
    			total = total.add(c.getValorPago());
    		}
    	}
    	return total;
    }
    
    
    @OneToOne
    private Curso cursoInteresse;
    
    private String contato;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataInclusao;

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.identificador != null ? this.identificador.hashCode() : 0);
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
        final Pessoa other = (Pessoa) obj;
        if (this.identificador != other.identificador && (this.identificador == null || !this.identificador.equals(other.identificador))) {
            return false;
        }
        return true;
    }
    
    public Pessoa(){
        dataInclusao = new Date();
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + identificador + 
                ", nome=" + nome + ", email=" + email + ", endereco=" + endereco + 
                ", numero=" + numero + ", cep=" + cep + ", cidade=" + cidade + 
                ", estado=" + estado + ", dataNascimento=" + dataNascimento + 
                ", rg=" + rg + ", cpf=" + cpf + ", telefoneCelular=" + telefoneCelular + 
                ", telefoneResidencial=" + telefoneResidencial + 
                ", telefoneComercial=" + telefoneComercial + 
                ", cro=" + cro + ", ufCro=" + ufCro + '}';
    }
    
    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    

    /**
     * @return the id
     */
    public Long getIdentificador() {
        return identificador;
    }
    
    public void setIdentificador(Long identificador){
        this.identificador = identificador;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the dataNascimento
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the rg
     */
    public String getRg() {
        return rg;
    }

    /**
     * @param rg the rg to set
     */
    public void setRg(String rg) {
        this.rg = rg;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the telefoneCelular
     */
    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    /**
     * @param telefoneCelular the telefoneCelular to set
     */
    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    /**
     * @return the telefoneResidencial
     */
    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    /**
     * @param telefoneResidencial the telefoneResidencial to set
     */
    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    /**
     * @return the telefoneComercial
     */
    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    /**
     * @param telefoneComercial the telefoneComercial to set
     */
    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public String getUfCro() {
        return ufCro;
    }

    public void setUfCro(String ufCro) {
        this.ufCro = ufCro;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public boolean isProfessor() {
        return professor;
    }

    public void setProfessor(boolean professor) {
        this.professor = professor;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the cursoInteresse
     */
    public Curso getCursoInteresse() {
        return cursoInteresse;
    }

    /**
     * @param cursoInteresse the cursoInteresse to set
     */
    public void setCursoInteresse(Curso cursoInteresse) {
        this.cursoInteresse = cursoInteresse;
    }

    /**
     * @return the contato
     */
    public String getContato() {
        return contato;
    }

    /**
     * @param contato the contato to set
     */
    public void setContato(String contato) {
        this.contato = contato;
    }

    /**
     * @return the dataInclusao
     */
    public Date getDataInclusao() {
        return dataInclusao;
    }

    /**
     * @param dataInclusao the dataInclusao to set
     */
    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }
    
}
