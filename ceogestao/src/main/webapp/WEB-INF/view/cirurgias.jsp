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
    $(document).ready(function() {
        $('#cirurgiasTable').dataTable({
            "paging":   false,
            "info":     false,
            "bFilter":  false
        });
    } );
</script>
<p></p>

<c:if test="${empty cirurgias}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhuma cirurgia encontrada
    </div>
</c:if>

<c:if test="${not empty cirurgias}">
    <table class="table table-striped" id="cirurgiasTable">
        <thead>
        <th>Turma</th>
        <th>Paciente</th>
        <th>Data do primeiro procedimento</th>
        <th></th>
    </thead>
    <c:forEach items="${cirurgias}" var="cirurgia">
        <tr>
            <td>${cirurgia.turma}</td>
            <td>${cirurgia.paciente.nome}</td>
            <td>
                <span style="display: none"><fmt:formatDate pattern="yyyy/MM/dd" value="${cirurgia.dataPrimeiroProcedimento}"/> </span>
                <fmt:formatDate pattern="dd/MM/yyyy" value="${cirurgia.dataPrimeiroProcedimento}"/>
            <td>
                <button type="button" class="btn btn-default btn-xs" onclick="document.location = 'editarCirurgia.html?id=${cirurgia.id}'">
                    <span class="glyphicon glyphicon-pencil"></span>  Detalhes</a>
                </button>
            </td>
        </tr>
    </c:forEach>
</table>
</c:if>

<button type="submit" class="btn btn-primary" onclick="document.location = 'novaCirurgia.html'">
    <span class="glyphicon glyphicon-plus"></span>  Adicionar Cirurgia
</button>