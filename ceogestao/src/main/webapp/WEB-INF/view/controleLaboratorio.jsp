<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<p></p>
<c:if test="${empty servicosLaboratorio}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhum serviço de laboratório encontrado
    </div>
</c:if>

<c:if test="${not empty servicosLaboratorio}">
    <table class="table table-striped" >
        <thead>
        <th>Nome</th>
        <th>Envio</th>
        <th>Entrega</th>            
        <th>Dr(a)</th>
        <th>Procedimento</th>
        <th>Laboratório</th>
        <th></th>
    </thead>
    <c:forEach items="${servicosLaboratorio}" var="s">
        <tr>
            <td>${s.pessoa.nome}</td>
            <td>${s.envio}</td>
            <td>${s.entrega}</td>
            <td>${s.profissional.nome}</td>
            <td>${s.procedimento.nome}</td>
            <td>${s.laboratorio}</td>
            
            <td>
                <button type="button" class="btn btn-default btn-xs" onclick="document.location = '#'">
                    <span class="glyphicon glyphicon-pencil"></span>  Detalhes</a>
                </button>
            </td>
        </tr>
    </c:forEach>
</tr>
</table>
</c:if>

<form:form action="novoServicoLaboratorio.html" method="POST">
    <button type="submit" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus"></span>  Adicionar Serviço
    </button>
</form:form>
