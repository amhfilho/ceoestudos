<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:if test="${fn:length(pessoas)==0}">
    <p><small>Nenhum aluno encontrado</small></p>
</c:if>
<c:if test="${fn:length(pessoas)>0}">
<table class="table-striped" style="width: 100%">
<c:forEach items="${pessoas}" var="pessoa">
    <tr><td><a href="javascript:adicionarAluno(${pessoa.identificador},'${pessoa.nome}')">${pessoa.nome}</td></tr>
    <tr><td></td></tr>
</c:forEach>
</table>
</c:if>
<input type="hidden" id="pessoaId" name="pessoaId" value=""/>
