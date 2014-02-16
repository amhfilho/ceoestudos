<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:if test="${empty turmas}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhuma turma encontrada
    </div>
</c:if>

<c:if test="${not empty turmas}">
    <table class="table table-striped" style="font-size: 11px">
        <thead>
            <th>Curso</th>
            <th>Inicio</th>
            <th>Fim</th>
            <th>Alunos matriculados</th>
            <th>Situação</th>
            <th></th>
        </thead>
        <c:forEach items="${turmas}" var="turma">
            <tr>
                <td><a href="editarTurma.html?id=${turma.id}">Atualizar</a></td>
                <td>${turma.diasDaSemana}</td>
                <td>${turma.situacao}</td>
                <td>${turma.situacao}</td>
                <td>${turma.situacao}</td>
                <td></td>
            </tr>
        </c:forEach>
</table>
</c:if>
<form:form method="POST" action="novaTurma.html">
    <button type="submit" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus"></span>  Nova Turma
    </button>
</form:form>