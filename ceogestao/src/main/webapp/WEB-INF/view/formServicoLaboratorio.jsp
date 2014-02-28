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
    function configurarModalPessoa(idFieldName, nomeFieldName) {
        document.getElementById("idFieldName").value = idFieldName;
        document.getElementById("nomeFieldName").value = nomeFieldName;
        $('#myModal').modal('show');
    }
    function configurarModalProcedimento(idFieldName, nomeFieldName) {
        document.getElementById("idFieldName").value = idFieldName;
        document.getElementById("nomeFieldName").value = nomeFieldName;
        $('#myModal').modal('show');
    }
    $(document).ready(function(e) {
        $('#envio').datetimepicker({
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
</script>
<br>
<small>Campos obrigatórios são marcados com <strong>*</strong></small>
<br>
<form:form class="form-horizontal" role="form" method="POST" action="salvarServicoLaboratorio.html"  
           modelAttribute="servicoLaboratorio" name="servicoForm">

    <jsp:include page="pessoaModal.jsp" />
    <form:errors path="*">
        <div class="alert alert-danger"><form:errors path="*"/></div>
    </form:errors>

    <form:hidden path="id" id="id" />
    <input type="hidden" name="idFieldName" id="idPessoaFieldName"/>
    <input type="hidden" name="nomeFieldName" id="nomePessoaFieldName"/>

    <div class="form-group">
        <div class="row">
            <label for="nomePaciente" class="col-sm-2 control-label">Paciente*</label>
            <div class="col-lg-6">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Clique em Adicionar para inserir um paciente" id="nomePaciente" disabled>
                    <input type="hidden" id="idPaciente" name="idPaciente" value=""/>
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" 
                                onclick="configurarModalPessoa('idPaciente', 'nomePaciente')">
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
                    <input type="text" class="form-control" placeholder="Clique em Adicionar para inserir um profissional" id="nomeProfissional" disabled>
                    <input type="hidden" id="idProfissional" name="idProfissional" value="" />
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" data-toggle="modal" data-target="#myModal"
                                onclick="configurarModalPessoa('idProfissional', 'nomeProfissional')">
                            Adicionar
                        </button>
                    </span>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
        </div>
    </div>

    <div class="form-group">
        <label for="envio" class="col-sm-2 control-label">Data de envio</label>
        <div class="col-lg-2">
            <form:input path="envio" cssClass="form-control" id="envio"/>
        </div>
    </div>

    <div class="form-group">
        <label for="entrega" class="col-sm-2 control-label">Data de entrega</label>
        <div class="col-lg-2">
            <form:input path="entrega" cssClass="form-control" id="entrega"/>
        </div>
    </div>
        
    <div class="form-group">
        <div class="row">
            <label for="nomeProcedimento" class="col-sm-2 control-label">Procedimento*</label>
            <div class="col-lg-6">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Clique em Adicionar para inserir um procedimento" 
                           id="nomeProcedimento" disabled>
                    <input type="hidden" id="idProcedimento" name="idProcedimento" value="" />
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" data-toggle="modal" data-target="#myModal"
                                onclick="configurarModalPessoa('idProfissional', 'nomeProfissional')">
                            Adicionar
                        </button>
                    </span>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
        </div>
    </div>    

</form:form>

