<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script	type="text/javascript">
    $(document).ready(function(e) {
        $('#dataHistorico').datetimepicker({
            lang: 'pt',
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
    });
    
    function configurarModalPaciente() {
        //document.getElementById("adicionarPessoaAction").value = action;
        $('#pacienteModal').modal('show');
    }
    function adicionarPaciente(id, nome) {
        document.getElementById("idPaciente").value = id;
        document.getElementById("nomePaciente").value = nome;
        $('#pacienteModal').modal('hide');
    }
    function configurarModalHistorico() {
        if (document.getElementById("idPaciente").value === "") {
            alert('Você deve primeiro inserir um paciente');
        } else {
            $('#historicoModal').modal('show');
        }
    }
    function adicionarHistorico() {
        document.formCirurgia.action = "atualizarHistoricoCirurgia.html";
        document.formCirurgia.submit();
    }

    function removerHistorico(data, descricao, idProfessor) {
        if (confirm('Deseja realmente excluir esta linha do histórico?')) {
            document.formCirurgia.action = "atualizarHistoricoCirurgia.html";
            document.getElementById("removerHistorico").value = "true";
            document.getElementById("dataHistorico").value = data;
            document.getElementById("descricaoHistorico").value = descricao;
            document.getElementById("idProfessor").value = idProfessor;
            document.formCirurgia.submit();
        }
    }
</script>
<jsp:include page="pacienteModal.jsp" />
<form:form class="form-horizontal" role="form" method="POST" action="salvarCirurgia.html"  
           modelAttribute="cirurgia" id="formCirurgia" name="formCirurgia">

    <jsp:include page="historicoModal.jsp" />

    <form:errors path="*">
        <div class="alert alert-danger"><form:errors path="*"/></div>
    </form:errors>
    <small>Campos obrigatórios são marcados com <strong>*</strong></small>
    <br>

    <form:hidden path="id" id="id"/>

    <div class="form-group">
        <label for="turma" class="col-sm-2 control-label">Turma</label>
        <div class="col-sm-6">
            <select id="turma" name="turma" class="form-control">
                <option value="">Sem turma associada</option>
                <c:forEach items="${todasAsTurmas}" var="turma">
                    <option value="${turma.id}" <c:if test="${cirurgia.turma.id == turma.id}"> selected </c:if>>${turma}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    
    <div class="form-group">
        <label for="nomePaciente" class="col-sm-2 control-label">Paciente*</label>
        <div class="col-sm-6">
            <div class="input-group">
                <input type="text" class="form-control" name="nomePaciente" id="nomePaciente"
                       placeholder="Pesquisar por nome" disabled="true" value="${cirurgia.paciente.nome}">

                <form:hidden path="paciente" id="idPaciente"/>

                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onclick="configurarModalPaciente()">
                        <span class="glyphicon glyphicon-search"></span> 
                    </button>
                </span>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="exameRadiografico" class="col-sm-2 control-label">Exame Radiográfico</label>
        <div class="col-sm-10">
            <form:input path="exameRadiografico" cssClass="form-control input-sm" id="exameRadiografico" placeholder="Exame Radiográfico" />
        </div>
    </div>

    <div class="form-group">
        <label for="planejamento" class="col-sm-2 control-label">Planejamento</label>
        <div class="col-sm-10">
            <form:input path="planejamento" cssClass="form-control input-sm" id="planejamento" placeholder="Planejamento" />
        </div>
    </div>

    <div class="form-group">
        <label for="antibiotico" class="col-sm-2 control-label">Antibiótico</label>
        <div class="col-sm-10">
            <form:input path="antibiotico" cssClass="form-control input-sm" id="antibiotico" placeholder="Antibiótico" />
        </div>
    </div>

    <div class="form-group">
        <label for="ansiolitico" class="col-sm-2 control-label">Ansiolítico</label>
        <div class="col-sm-10">
            <form:input path="ansiolitico" cssClass="form-control input-sm" id="ansiolitico" placeholder="Ansiolítico" />
        </div>
    </div>

    <div class="form-group">
        <label for="historico" >Histórico de atendimento</label>
        <c:if test="${empty cirurgia.historico}">
            <p>Não há histórico de atendimento</p>
        </c:if>
        <c:if test="${not empty cirurgia.historico}">
            <table class="table table-striped">
                <c:forEach items="${cirurgia.historico}" var="historico">
                    <tr>
                        <td><fmt:formatDate dateStyle="medium" value="${historico.data}"/></td>
                        <td>${historico.descricao}</td>
                        <td>${historico.professor.nome}</td>
                        <td style="text-align: center">
                            <a href="javascript:removerHistorico('<fmt:formatDate pattern="dd/MM/yyyy" value="${historico.data}"/>',
                               '${historico.descricao}',
                               '${historico.professor.identificador}')">
                                <span class="glyphicon glyphicon-remove"></span>
                                Remover
                            </a>
                            
                        </td>
                    </tr>
                </c:forEach>           
            </table>

        </c:if>
        <input type="hidden" name="removerHistorico" id="removerHistorico"/>
        <button type="button" class="btn btn-default btn-xs" 
                onclick="javascript:configurarModalHistorico();">Adicionar</button>
    </div>


    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Salvar</button>
            <button type="button" class="btn btn-default" onclick="location.href = 'cirurgias.html'">Voltar</button>
            <c:if test="${not empty cirurgia.id}">
                <button type="button" class="btn btn-default" 
                        onclick="if (confirm('Deseja realmente excluir a cirurgia? ')) {
                                    location.href = 'excluirCirurgia.html?id=${cirurgia.id}';
                                }">
                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
                <button type="button" class="btn btn-default" onclick="location.href = 'novaCirurgia.html'">Novo</button>
            </c:if>
        </div>
     </div>
    </form:form>
