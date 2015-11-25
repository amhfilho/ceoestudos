<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:if test="${empty cursos}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhum curso encontrado
    </div>
</c:if>
<fmt:setLocale value="pt_BR"/>
<c:if test="${not empty cursos}">
    <table class="table table-striped" >
        <tr>
        <thead>
        <th>Código</th>
        <th>Nome</th>
        <th>Carga horária</th>
        <th>Investimento</th>
        <th>Turmas Em Andamento</th>            
        <th>Turmas Nao Confirmadas</th>
        <th>Turmas Confirmadas</th>
        <th>Turmas Canceladas</th>
        <th></th>
    </thead>
    <c:forEach items="${cursos}" var="curso">
        <tr>
            <td>${curso.codigo}</td>
            <td>${curso.nome}</td>
            <td>${curso.cargaHoraria}</td>
            <td><fmt:formatNumber value="${curso.investimento}" type="number"
                              minFractionDigits="2" maxFractionDigits="2"/></td>
            <td>${curso.turmasEmAndamento}</td>
            <td>${curso.turmasNaoConfirmadas}</td>
            <td>${curso.turmasConfirmadas}</td>
            <td>${curso.turmasCanceladas}</td>
            <td>
                <button type="button" class="btn btn-default btn-xs" onclick="document.location = 'editarCurso.html?id=${curso.id}'">
                    <span class="glyphicon glyphicon-pencil"></span>  Detalhes</a>
                </button>
            </td>
        </tr>
    </c:forEach>
</tr>
</table>
</c:if>

    <button type="submit" class="btn btn-primary" onclick="document.location='novoCurso.html'">
        <span class="glyphicon glyphicon-plus"></span>  Adicionar Curso
    </button>
