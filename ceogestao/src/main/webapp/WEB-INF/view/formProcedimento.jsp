<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<form:form class="form-horizontal" role="form" method="POST" action="salvarProcedimento.html"  
           modelAttribute="procedimento">
    <form:errors path="*">
        <div class="alert alert-danger"><form:errors path="*"/></div>
    </form:errors>

    <small>Campos obrigatórios são marcados com <strong>*</strong></small>
    <br>

    <form:hidden path="id" id="id" name="id" />

    <div class="form-group">
        <label for="nome" class="col-sm-2 control-label">Nome*</label>
        <div class="col-sm-10">
            <form:input cssClass="form-control input-sm" id="nome" placeholder="Nome do procedimento" path="nome" />
        </div>
    </div>

    <div class="form-group">
        <label for="tipo" class="col-sm-2 control-label">Tipo</label>
        <div class="col-lg-4">
            <form:select items="${tiposProcedimentos}" path="tipo" cssClass="form-control input-sm" id="tipo"/>
        </div>
        
        <div class="col-lg-4">
            <input type="text" class="form-control input-sm" id="textTipo" name="textTipo" placeholder="Insira um novo tipo"/>
        </div>
    </div>
    

    <div class="form-group">
        <label for="preco" class="col-sm-2 control-label">Valor</label>
        <div class="col-sm-10">
            <form:input path="preco" cssClass="form-control input-sm" id="preco" placeholder="Apenas números"/>
        </div>
    </div>

    <div class="form-group">
        <label for="obs" class="col-sm-2 control-label">Observações</label>
        <div class="col-sm-10">
            <form:textarea rows="3" path="obs" cssClass="form-control input-sm" id="obs" placeholder="Texto livre" />
        </div>
    </div> 

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Salvar</button>
            <button type="button" class="btn btn-default" onclick="location.href = 'procedimentos.html'">Voltar</button>
            <c:if test="${not empty procedimento.id}">
                <button type="button" class="btn btn-default" 
                        onclick="if (confirm('Deseja realmente excluir o procedimento ? ')) {
                                    location.href = 'excluirProcedimento.html?id=${procedimento.id}';
                                }">

                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
            </c:if>
        </div>
    </div>

</form:form>