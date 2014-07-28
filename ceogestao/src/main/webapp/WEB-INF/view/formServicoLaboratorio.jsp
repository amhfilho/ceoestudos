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
    function adicionarPessoa(id, nome) {
        var idFieldName = document.getElementById("idFieldName").value;
        var nomeFieldName = document.getElementById("nomeFieldName").value;
        document.getElementById(idFieldName).value = id;
        document.getElementById(nomeFieldName).value = nome;
        $('#myModal').modal('hide');
    }
    function adicionarProcedimento(id, nome) {
        var idFieldName = document.getElementById("idFieldName").value;
        var nomeFieldName = document.getElementById("nomeFieldName").value;
        document.getElementById(idFieldName).value = id;
        document.getElementById(nomeFieldName).value = nome;
        $('#procedimentoModal').modal('hide');
    }
    function configurarModalPessoa(idFieldName, nomeFieldName) {
        document.getElementById("idFieldName").value = idFieldName;
        document.getElementById("nomeFieldName").value = nomeFieldName;
        $('#myModal').modal('show');
    }
    function configurarModalProcedimento(idFieldName, nomeFieldName) {
        document.getElementById("idFieldName").value = idFieldName;
        document.getElementById("nomeFieldName").value = nomeFieldName;
        $('#procedimentoModal').modal('show');
    }
    $(document).ready(function(e) {
        $('#envio').datetimepicker({
            lang: 'pt',
            onShow: function(ct) {
                this.setOptions({
                    maxDate: $('#entrega').val() ? $('#entrega').val() : false
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
        
        $('#entrega').datetimepicker({
            lang: 'pt',
            onShow: function(ct) {
                this.setOptions({
                    minDate: $('#envio').val() ? $('#envio').val() : false
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
    });
</script>
<div class="row">
    <small>Campos obrigatórios são marcados com <strong>*</strong></small>
</div>
<form:form cssClass="form-horizontal" role="form" method="POST" action="salvarServicoLaboratorio.html"  
           modelAttribute="servicoLaboratorio" name="servicoForm">

    
    <jsp:include page="pacienteModal.jsp" />
    <jsp:include page="alunoModal.jsp" />
    <jsp:include page="procedimentoModal.jsp"/>
    <form:errors path="*">
        <div class="alert alert-danger"><form:errors path="*"/></div>
    </form:errors>

    <form:hidden path="id" id="id" />
    <input type="hidden" name="idFieldName" id="idFieldName"/>
    <input type="hidden" name="nomeFieldName" id="nomeFieldName"/>

    <div class="form-group">
        <div class="row">
        <label for="laboratorio" class="col-sm-2 control-label">Laboratório</label>
        <div class="col-sm-10">
            <form:input path="laboratorio" cssClass="form-control" id="laboratorio"/>
        </div>
        </div>
    </div>
    
    <div class="form-group">
        <div class="row">
            <label for="nomePaciente" class="col-sm-2 control-label">Paciente*</label>
            <div class="col-lg-6">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Clique em Adicionar para inserir um paciente" 
                           id="nomePaciente" value="${servicoLaboratorio.paciente.nome}" disabled>
                    <form:hidden id="paciente" path="paciente" />
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" 
                                onclick="configurarModalPessoa('paciente', 'nomePaciente')">
                            Adicionar
                        </button>
                    </span>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <label for="nomeProfissional" class="col-sm-2 control-label">Dr.(a)*</label>
            <div class="col-lg-6">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Clique em Adicionar para inserir um profissional" 
                           id="nomeProfissional" value="${servicoLaboratorio.profissional.nome}" disabled>
                    <form:hidden id="profissional" path="profissional" />
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" 
                                onclick="configurarModalPessoa('profissional', 'nomeProfissional')">
                            Adicionar
                        </button>
                    </span>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
        </div>
    </div>

    <div class="form-group">
        <div class="row">
        <label for="envio" class="col-sm-2 control-label">Data de envio*</label>
        <div class="col-lg-2">
            <form:input path="envio" cssClass="form-control" id="envio"/>
        </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
        <label for="entrega" class="col-sm-2 control-label">Data de entrega</label>
        <div class="col-lg-2">
            <form:input path="entrega" cssClass="form-control" id="entrega"/>
        </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label for="nomeProcedimento" class="col-sm-2 control-label">Procedimento*</label>
            <div class="col-lg-6">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Clique em Adicionar para inserir um procedimento" 
                           id="nomeProcedimento" value="${servicoLaboratorio.procedimento.nome}" disabled>
                    <form:hidden id="procedimento" path="procedimento" />
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="configurarModalProcedimento('procedimento', 'nomeProcedimento')">
                            Adicionar
                        </button>
                    </span>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Salvar</button>
            <button type="button" class="btn btn-default" onclick="location.href = 'controleLaboratorio.html'">Voltar</button>
            <c:if test="${not empty servicoLaboratorio.id}">
                <button type="button" class="btn btn-default" 
                        onclick="if (confirm('Deseja realmente excluir o serviço ? ')) {
                                    location.href = 'excluirServicoLaboratorio.html?id=${servicoLaboratorio.id}';
                                }">

                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
                <button type="button" class="btn btn-default" onclick="location.href = 'novoServicoLaboratorio.html'">Novo</button>
            </c:if>
            
        </div>
    </div>

</form:form>

