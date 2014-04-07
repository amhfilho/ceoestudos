<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<p></p>
<c:if test="${empty tratamentos}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhum tratamento encontrado
    </div>
</c:if>

<c:if test="${not empty tratamentos}">
    <table class="table table-striped">
        <thead>
        <th>Turma</th>
        <th>Paciente</th>
        <th>Valor</th>            
        <th></th>
    </thead>
    <c:forEach items="${tratamentos}" var="tratamento">
        <tr>
            <td>
                <c:if test="${empty tratamento.turma}">Nenhuma turma associada</c:if>
                <c:if test="${not empty tratamento.turma}">${tratamento.turma}</c:if>
            </td>
            <td>${tratamento.paciente.nome}</td>
            <td><fmt:formatNumber value="${tratamento.valorBruto}" type="number"
                              minFractionDigits="2" maxFractionDigits="2"/>
             </td>
            <td>
                <button type="button" class="btn btn-default btn-xs" 
                        onclick="document.location = 'editarTratamento.html?idTratamento=${tratamento.id}'">
                    <span class="glyphicon glyphicon-pencil"></span>  Detalhes</a>
                </button>
            </td>
        </tr>
    </c:forEach>

</table>
</c:if>
<button type="button" class="btn btn-primary" onclick="document.location = 'novoTratamento.html'">
    <span class="glyphicon glyphicon-plus"></span>  Adicionar Tratamento
</button>
