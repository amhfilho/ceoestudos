<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<form:form role="form" class="form-horizontal" action="procedimentos.html" id="formPesquisa" name="formPesquisa">

    <div class="form-group">
        <div class="col-lg-4">
            <input type="text" class="form-control input-sm" name="pesquisa" id="pesquisa"  placeholder="Pesquisar por nome"
                   value="${pesquisa}">
        </div>
        <div class="col-lg-2">
            <select name="filtroTipo" class="form-control input-sm" id="filtroTipo" >
                <option value="">Qualquer tipo</option>
                <c:forEach items="${tiposProcedimentos}" var="tipo">
                    <option value="${tipo}" <c:if test="${not empty filtroTipo && tipo == filtroTipo}"> selected</c:if>>
                        ${tipo}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-lg-2">
            <button class="btn btn-default" type="submit">
                <span class="glyphicon glyphicon-search"></span> Pesquisar
            </button>
        </div>
    </div>
</form:form>

<p></p>
<c:if test="${empty procedimentos}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhum procedimento encontrado
    </div>
</c:if>

<c:if test="${not empty procedimentos}">
    <table class="table table-striped" >
        <thead>
        <th>Nome</th>
        <th>Tipo</th>
        <th>Preço</th>            
        <th></th>
    </thead>
    <c:forEach items="${procedimentos}" var="p">
        <tr>
            <td>${p.nome}</td>
            <td>${p.tipo}</td>
            <td><fmt:formatNumber value="${p.preco}" type="number"
                              minFractionDigits="2" maxFractionDigits="2"/></td>

            <td>
                <button type="button" class="btn btn-default btn-xs" onclick="document.location = 'editarProcedimento.html?id=${p.id}'">
                    <span class="glyphicon glyphicon-pencil"></span>  Detalhes</a>
                </button>
            </td>
        </tr>
    </c:forEach>
</tr>
</table>
</c:if>

<form:form action="novoProcedimento.html" method="POST">
    <button type="submit" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus"></span>  Adicionar Procedimento
    </button>
</form:form>