package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author amhfilho
 */
@Entity
public class ServicoLaboratorio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255, message = "O nome do laboratório não pode exceder 255 caracteres")
    private String laboratorio;

    @ManyToOne
    @NotNull(message = "Deve haver um paciente associado a este serviço")
    private Pessoa paciente;

    @ManyToOne
    @NotNull(message = "Deve haver um profissional associado a este serviço")
    private Pessoa profissional;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Deve haver uma data de envio associada a este serviço")
    private Date envio;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date entrega;

    @ManyToOne
    @NotNull(message = "Deve haver um procedimento associado a este serviço")
    private Procedimento procedimento;

    @Column(name="previsao")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Deve haver uma previsão de entrega associada a este serviço")
    private Date previsao;
    
    @Column(name="recebedor")
    private String recebedor;
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ServicoLaboratorio other = (ServicoLaboratorio) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    

    public String getRecebedor() {
		return recebedor;
	}

	public void setRecebedor(String recebedor) {
		this.recebedor = recebedor;
	}

	public Date getPrevisao() {
		return previsao;
	}

	public void setPrevisao(Date previsao) {
		this.previsao = previsao;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Pessoa getPaciente() {
        return paciente;
    }

    public void setPaciente(Pessoa paciente) {
        this.paciente = paciente;
    }

    public Date getEnvio() {
        return envio;
    }

    public void setEnvio(Date envio) {
        this.envio = envio;
    }

    public Date getEntrega() {
        return entrega;
    }

    public void setEntrega(Date entrega) {
        this.entrega = entrega;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Pessoa getProfissional() {
        return profissional;
    }

    public void setProfissional(Pessoa profissional) {
        this.profissional = profissional;
    }

}
