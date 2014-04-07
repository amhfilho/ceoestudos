<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script>
    $(function() {

        $("#procurarAlunoBtn").click(function() {

            var a = $("input[name=procurarAlunoText]").val();
            if (a !== "") {

                $.post("procurarAluno.html", {'nome': a}, function(resposta) {
                    // selecionando o elemento html através da 
                    // ID e alterando o HTML dele 
                    $("#tabelaBuscaAlunos").html(resposta);

                });
            }
        });
    });

    function salvarTurma() {
        if (document.turmaForm.dataInicio.value === "" | document.turmaForm.dataFim.value === "" |
                document.turmaForm.horaInicio === "" | document.turmaForm.horaFim === "") {

            alert('As datas e os horários da turma devem ser preenchidos');
            return;
        }

//        if(document.formTurma.radioEmAndamento.checked ==="checked") {
//            
//            //verificar se data de inicio é igual ou anteriorà data de hoje
//            var dataInicio
//            
//        }

        var id = document.getElementById("id").value;

        if (id === null | id === "") {
            document.turmaForm.action = "adicionarTurma.html";
        } else {
            document.turmaForm.action = "atualizarTurma.html";
        }
        document.turmaForm.submit();
    }

    function excluirAluno(id) {
        document.turmaForm.action = "excluirAluno.html";
        document.getElementById("excluirAlunoId").value = id;
        document.turmaForm.submit();
    }

    $(document).ready(function(e) {
        $('#dataInicio').datetimepicker({
            lang: 'pt',
            onShow: function(ct) {
                this.setOptions({
                    maxDate: $('#dataFim').val() ? $('#dataFim').val() : false
                })
            },
            i18n: {
                pt: {
                    months: [
                        'Janeiro', 'Fevereiro', 'Março', 'Abril',
                        'Maio', 'Junho', 'Julho', 'Agosto',
                        'Setembro', 'Outubro', 'Novembro', 'Dezembro'
                    ],
                    dayOfWeek: [
                        "Dom", "Seg", "Ter", "Qua",
                        "Qui", "Sex", "Sab"
                    ]
                }
            },
            timepicker: false,
            allowBlank: true,
            format: 'd/m/Y'
        });
        $('#dataFim').datetimepicker({
            lang: 'pt',
            onShow: function(ct) {
                this.setOptions({
                    minDate: $('#dataInicio').val() ? $('#dataInicio').val() : false
                })
            },
            i18n: {
                pt: {
                    months: [
                        'Janeiro', 'Fevereiro', 'Março', 'Abril',
                        'Maio', 'Junho', 'Julho', 'Agosto',
                        'Setembro', 'Outubro', 'Novembro', 'Dezembro'
                    ],
                    dayOfWeek: [
                        "Dom", "Seg", "Ter", "Qua",
                        "Qui", "Sex", "Sab"
                    ]
                }
            },
            timepicker: false,
            allowBlank: true,
            format: 'd/m/Y'
        });

        $('#horaInicio').datetimepicker({
            datepicker: false,
            onShow: function(ct) {
                this.setOptions({
                    maxTime: $('#horaFim').val() ? $('#horaFim').val() : false
                })
            },
            format: 'H:i'
        });

        $('#horaFim').datetimepicker({
            datepicker: false,
            onShow: function(ct) {
                this.setOptions({
                    minTime: $('#horaInicio').val() ? $('#horaInicio').val() : false
                })
            },
            format: 'H:i'
        });
    });

</script>



<form:form class="form" role="form" method="POST" action="salvarTurma.html" id="turmaForm" 
           modelAttribute="turma" name="turmaForm">

    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Selecione um aluno</h4>
                </div>
                <div class="modal-body">

                    <input type="text" name="procurarAlunoText" id="procurarAlunoText" size="50" placeholder="Procure pelo nome do aluno"/>
                    <button class="btn btn-default btn-xs" type="button" id="procurarAlunoBtn" >Procurar</button>
                    <br>
                    <table class="table table-bordered" id="tabelaBuscaAlunos" style="width: 100%">

                    </table>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>

                </div>
            </div>
        </div>
    </div>


    <form:errors path="*">
        <div class="alert alert-danger">
            <strong>Problemas!</strong><br>
            <form:errors path="*"/></div>
        </form:errors>
        <form:hidden path="id" id="id" name="id" />
    <div class="form-group">
        <label for="curso" >Curso</label>
        <select id="curso" name="curso" class="form-control input-sm" >
            <c:forEach items="${todosOsCursos}" var="curso">
                <option value="${curso.id}" <c:if test="${turma.curso.id == curso.id}"> selected </c:if>>${curso.nome}</option>
            </c:forEach>
        </select>
        
        
    </div>

    <div class="form-group">
        <label>Datas e horários</label>
        <table class="table">
            <tr>
                <td>Data de início:</td>
                <td><form:input path="dataInicio" cssClass="form-control input-sm" id="dataInicio"/></td>
                <td>Horário de início:</td>
                <td><form:input path="horaInicio" cssClass="form-control input-sm" id="horaInicio"/></td>
            </tr>
            <tr>
                <td>Data de término:</td>
                <td><form:input path="dataFim" cssClass="form-control input-sm" id="dataFim" /></td>
                <td>Horário de término:</td>
                <td><form:input path="horaFim" cssClass="form-control input-sm" id="horaFim"/></td>
            </tr>
        </table>
    </div>



    <div class="form-group">
        <label for="situacao" >Situação: </label>
        <label class="radio-inline">
            <form:radiobutton id="radioNaoConfirmada" path="situacao"  value="NAO_CONFIRMADA"/> Não Confirmada
        </label>
        <label class="radio-inline">
            <form:radiobutton id="radioConfirmada" path="situacao" value="CONFIRMADA"/> Confirmada
        </label>
        <label class="radio-inline">
            <form:radiobutton id="radioEmAndamento" path="situacao" value="EM_ANDAMENTO"/> Em andamento
        </label>
        <label class="radio-inline">
            <form:radiobutton id="radioCancelada" path="situacao" value="CANCELADA"/> Cancelada
        </label>
    </div>




    <div class="form-group">
        <label for="diasDaSemana" >Dias da Semana: </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxSeg" path="diasDaSemana" value="SEG" /> Seg
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxTer" path="diasDaSemana" value="TER" /> Ter
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxQua" path="diasDaSemana" value="QUA" /> Qua
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxQui" path="diasDaSemana" value="QUI" /> Qui
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxSex" path="diasDaSemana" value="SEX" /> Sex
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxSab" path="diasDaSemana" value="SÁB" /> Sáb
        </label>
    </div>

    <div class="form-group">
        <label for="selectProfessor" >Professor</label>
        <c:if test="${not empty professores}">
            <form:select path="professor" id="selectProfessor" items="${professores}" cssClass="form-control input-sm" />
        </c:if>
        <c:if test="${empty professores}">
            <p class="form-control-static">Não há professores cadastrados no sistema.</p>
        </c:if>
    </div>   

    <div class="form-group">
        <label for="sala" >Sala</label>
        <form:input path="sala" cssClass="form-control input-sm" id="sala"/>
    </div>

    <div class="form-group">
        <label for="quorumMinimo" >Quorum mínimo</label>
        <form:input path="quorumMinimo" cssClass="form-control input-sm" id="quorumMinimo"/>
    </div>

    <div class="form-group">
        <label for="observacoes" >Observações</label>
        <form:textarea path="observacoes" cssClass="form-control input-sm" id="observacoes"/>
    </div>
    <div class="form-group">
        <label for="listaAlunos" >Alunos matriculados</label>

        <table class="table table-condensed" id="listaAlunos" style="font-size: 12px">
            <c:if test="${empty turma.alunos}">
                <tr><td colspan="2">Nenhum aluno matriculado</td></tr>
            </c:if>
            <c:if test="${not empty turma.alunos}">
                <c:forEach items="${turma.alunos}" var="aluno">
                    <tr>
                        <td>${aluno.nome}</td>
                        <td><a href="#" onclick="excluirAluno(${aluno.identificador})">Excluir</a></td>
                    </tr>
                </c:forEach>
                <input type="hidden" id="excluirAlunoId" name="excluirAlunoId" value=""/>
            </c:if>

            <tr>
                <td colspan="2">
                    <!-- Button trigger modal -->
                    <c:if test="${empty turma.id}">
                        É preciso salvar a turma primeiro para incluir alunos
                    </c:if>
                    <c:if test="${not empty turma.id}">
                        <button class="btn btn-default btn-xs" data-toggle="modal" data-target="#myModal">
                            Matricular aluno
                        </button>
                    </c:if>
                </td>
            </tr>
        </table>

    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="button" class="btn btn-primary" onclick="salvarTurma()">Salvar</button>
            <button type="button" class="btn btn-default" onclick="location.href = 'turmas.html'">Voltar</button>
            <c:if test="${not empty turma.id}">
                <button type="button" class="btn btn-default" 
                        onclick="if (confirm('Deseja realmente excluir a turma? ')) {
                                    location.href = 'excluirTurma.html?id=${turma.id}';

                                }">
                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
                <button type="button" class="btn btn-default" onclick="location.href = 'novaTurma.html'">Novo</button>
            </c:if>
        </div>
    </div>

</form:form>

