<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<form:errors path="curso.nome">
    <div class="alert alert-danger"><form:errors path="curso.nome"/></div>
</form:errors>

<form:errors path="curso.cargaHoraria">
    <div class="alert alert-danger"><form:errors path="curso.cargaHoraria"/></div>
</form:errors>

<form:errors path="curso.investimento">
    <div class="alert alert-danger"><form:errors path="curso.investimento"/></div>
</form:errors>

<small>Campos obrigat�rios s�o marcados com <strong>*</strong></small>
<br>
<form:form class="form-horizontal" role="form" method="POST" action="salvarCurso.html"  
           modelAttribute="curso">
    <form:hidden path="id" id="id" name="id" />

    <div class="form-group">
        <label for="codigo" class="col-sm-2 control-label">C�digo</label>
        <div class="col-sm-10">
            <form:input path="codigo" cssClass="form-control input-sm" id="codigo" placeholder="C�digo do curso" />
        </div>
    </div>

    <div class="form-group">
        <label for="nome" class="col-sm-2 control-label">Nome*</label>
        <div class="col-sm-10">
            <form:input path="nome" cssClass="form-control input-sm" id="nome" placeholder="Nome do curso" />
        </div>
    </div>

    <div class="form-group">
        <label for="cargaHoraria" class="col-sm-2 control-label">Carga hor�ria*</label>
        <div class="col-sm-10">
            <form:input path="cargaHoraria" cssClass="form-control input-sm" id="cargaHoraria" placeholder="Apenas n�meros" />
        </div>
    </div>

    <div class="form-group">
        <label for="investimento" class="col-sm-2 control-label">Investimento</label>
        <div class="col-sm-10">
            <form:input path="investimento" cssClass="form-control input-sm" id="investimento" placeholder="Apenas n�meros" />
        </div>
    </div>

    <div class="form-group">
        <label for="descricao" class="col-sm-2 control-label">Descri��o</label>
        <div class="col-sm-10">
            <form:textarea rows="3" path="descricao" cssClass="form-control input-sm" id="descricao" placeholder="Texto livre" />
        </div>
    </div>   

    <div class="form-group">
        <label for="preRequisitos" class="col-sm-2 control-label">Pr�-requisitos</label>
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
                        onclick="if (confirm('Deseja realmente excluir o curso? ')) {
                                location.href = 'excluirCurso.html?id=${curso.id}';
                            }">

                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
            </c:if>
        </div>
    </div>

</form:form>
