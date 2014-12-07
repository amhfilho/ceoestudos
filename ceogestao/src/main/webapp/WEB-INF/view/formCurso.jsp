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
    function excluirCurso(){
        if(confirm("Deseja realmente excluir o curso?")){
            document.formCurso.action = "excluirCurso.html";
            document.formCurso.submit();
        }
    }
    $(document).ready(function(e) {
        $('#investimento').mask("#.##0,00", {reverse: true});
    });
</script>

<form:form class="form-horizontal" role="form" method="POST" action="salvarCurso.html"  
           modelAttribute="curso" name="formCurso" id="formCurso">
    <form:errors path="*">
        <div class="alert alert-danger">
            <strong>Problemas!</strong><br>
            <form:errors path="*"/></div>
    </form:errors>
        
    <small>Campos obrigatórios são marcados com <strong>*</strong></small>
    <br>
    <form:hidden path="id" id="id" name="id" />

    <div class="form-group">
        <label for="codigo" class="col-sm-2 control-label">Código</label>
        <div class="col-sm-10">
            <form:input path="codigo" cssClass="form-control input-sm" id="codigo" placeholder="Código do curso" />
        </div>
    </div>

    <div class="form-group">
        <label for="nome" class="col-sm-2 control-label">Nome*</label>
        <div class="col-sm-10">
            <form:input path="nome" cssClass="form-control input-sm" id="nome" placeholder="Nome do curso" />
        </div>
    </div>

    <div class="form-group">
        <label for="cargaHoraria" class="col-sm-2 control-label">Carga horária*</label>
        <div class="col-sm-10">
            <form:input path="cargaHoraria" cssClass="form-control input-sm" id="cargaHoraria" placeholder="Apenas números" />
        </div>
    </div>

    <div class="form-group">
        <label for="investimento" class="col-sm-2 control-label">Investimento</label>
        <div class="col-sm-10">
            <form:input path="investimento" cssClass="form-control input-sm" id="investimento" placeholder="Apenas números" />
        </div>
    </div>

    <div class="form-group">
        <label for="descricao" class="col-sm-2 control-label">Descrição</label>
        <div class="col-sm-10">
            <form:textarea rows="3" path="descricao" cssClass="form-control input-sm" id="descricao" placeholder="Texto livre" />
        </div>
    </div>   

    <div class="form-group">
        <label for="preRequisitos" class="col-sm-2 control-label">Pré-requisitos</label>
        <div class="col-sm-10">
            <form:textarea rows="3" path="preRequisitos" cssClass="form-control input-sm" id="preRequisitos" placeholder="Texto livre" />
        </div>
    </div> 

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Salvar</button>
            <button type="button" class="btn btn-default" onclick="location.href = 'cursos.html'">Voltar</button>
            <c:if test="${not empty curso.id}">
                <button type="button" class="btn btn-default" 
                        onclick="javascript:excluirCurso();">

                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
                <button type="button" class="btn btn-default" onclick="location.href = 'novoCurso.html'">Novo</button>
            </c:if>
        </div>
    </div>

</form:form>
