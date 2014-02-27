package br.com.ceoestudos.ceogestao.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Weeks;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author amhfilho
 */
@Entity
public class Turma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Deve haver um curso associado a esta turma")
    @ManyToOne
    private Curso curso;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Data de inicio deve ser informada")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Data de término deve ser informada")
    private Date dataFim;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "Hora de inicio deve ser informada")
    private Date horaInicio;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "Data de término deve ser informada")
    private Date horaFim;

    @Min(value = 0, message = "O Quorum mínimo não pode ter um valor negativo")
    private Integer quorumMinimo;

    private String sala;

    private String observacoes;

    private SituacaoTurma situacao = SituacaoTurma.NAO_CONFIRMADA;

//    @ManyToOne(cascade = CascadeType.ALL)
//    private Pessoa professor;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TURMA_ALUNO",
            joinColumns = @JoinColumn(name = "TURMA_ID"),
            inverseJoinColumns = @JoinColumn(name = "ALUNO_ID"))
    private Set<Pessoa> alunos;

    @ManyToOne
    private Pessoa professor;

    @Size(min = 1, message = "Deve haver ao menos um dia da semana associada à turma")
    private String[] diasDaSemana;

    @Override
    public String toString() {
        return "Turma{" + "id=" + id + ", curso=" + curso + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", horaInicio=" + horaInicio + ", horaFim=" + horaFim + ", quorumMinimo=" + quorumMinimo + ", sala=" + sala + ", observacoes=" + observacoes + ", situacao=" + situacao + '}';
    }

    public String getDiasDaSemanaFormatados() {
        String retorno = "";
        if (diasDaSemana != null) {
            for (String dia : diasDaSemana) {
                retorno += dia + ",";
            }
            retorno = retorno.substring(0, retorno.length() - 1);
        }
        return retorno;
    }

    public void adicionarAluno(Pessoa aluno) {
        if (getAlunos() == null) {
            setAlunos(new HashSet<Pessoa>());
        }
        getAlunos().add(aluno);
    }

    public void removerAluno(Pessoa aluno) {
        if (getAlunos() != null) {
            getAlunos().remove(aluno);
        }
    }

    public Calendar getCalendarInicioAulas() {
        return criarCalendarComDataEHorario(dataInicio, horaInicio);
    }

    public Calendar getCalendarFimAulas() {
        return criarCalendarComDataEHorario(dataFim, horaFim);
    }

    public int getMinutosAula() {
        DateTime dateTimeInicio = new DateTime(getCalendarInicioAulas());
        int horaTermino = getCalendarFimAulas().get(Calendar.HOUR_OF_DAY);
        int minutoTermino = getCalendarFimAulas().get(Calendar.MINUTE);
        DateTime dateTimeFim = new DateTime(getCalendarInicioAulas())
                .withHourOfDay(horaTermino)
                .withMinuteOfHour(minutoTermino);
        return Minutes.minutesBetween(dateTimeInicio, dateTimeFim).getMinutes();
    }
    
    public int getNumeroDeAulasTurma(){
        int aulasPorSemana = getDiasDaSemana().length;
        DateTime dateTimeInicio = new DateTime(getCalendarInicioAulas());
        DateTime dateTimeFim = new DateTime(getCalendarFimAulas());
        int semanas =  Weeks.weeksBetween(dateTimeInicio, dateTimeFim).getWeeks();
        return semanas * aulasPorSemana;
    }

    public double getCargaHorariaTurma() {
        Calendar inicio = getCalendarInicioAulas();
        Calendar fim = getCalendarFimAulas();
        //inicio.
        return 0;
    }

    private Calendar criarCalendarComDataEHorario(Date data, Date horario) {
        //Criando um objeto Calendar com data de inicio
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        //Criando um objeto Calendar com o horário de inicio
        Calendar horaCal = Calendar.getInstance();
        horaCal.setTime(horario);
        //Setando as informações de horário no objeto inicio
        int horas = horaCal.get(Calendar.HOUR_OF_DAY);
        int minutos = horaCal.get(Calendar.MINUTE);
        calendar.set(Calendar.HOUR_OF_DAY, horas);
        calendar.set(Calendar.MINUTE, minutos);
        return calendar;
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

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public Pessoa getProfessor() {
        return professor;
    }

    public void setProfessor(Pessoa professor) {
        this.professor = professor;
    }

}
