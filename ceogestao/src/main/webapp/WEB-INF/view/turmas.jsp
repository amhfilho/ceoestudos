<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script>
    $(document).ready(function() {
        $('#turmasTable').dataTable({
            "paging":   false,
            "info":     false,
            "bFilter":  false
            
        });
    } );
</script>
<c:if test="${empty turmas}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhuma turma encontrada
    </div>
</c:if>

<c:if test="${not empty turmas}">
    <table class="table table-striped" id="turmasTable">
        <thead>
        <th>Curso</th>
        <th>Inicio</th>
        <th>Dias da semana</th>
        <th>Alunos matriculados</th>
        <th>Situação</th>
        <th></th>
    </thead>
    <c:forEach items="${turmas}" var="turma">
        <tr>

            <td>${turma.curso.nome}</td>
            <td><span style="display: none"><fmt:formatDate pattern="yyyy/MM/dd" value="${turma.dataInicio}"/> </span>
                <fmt:formatDate pattern="dd/MM/yyyy" value="${turma.dataInicio}"/> 
            </td>
            <td>${turma.diasDaSemanaFormatados}</td>
            <td>${fn:length(turma.alunos)}</td>
            <td>${turma.situacao.nome}</td>
            <td>
                <button type="button" class="btn btn-default btn-xs" onclick="document.location = 'editarTurma.html?id=${turma.id}'">
                    <span class="glyphicon glyphicon-pencil"></span>  Detalhes</a>
                </button>
            </td>
        </tr>
    </c:forEach>
</table>
</c:if>

<c:if test="${empty todosOsCursos}">
    <small>É preciso ter ao menos um curso cadastrado para criar turmas. <a href="cursos.html">Ir para o cadastro de cursos</a></small>
</c:if>

<c:if test="${not empty todosOsCursos}">
    <form:form method="POST" action="novaTurma.html">
        <button type="submit" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus"></span>  Nova Turma
        </button>
    </form:form>
</c:if>