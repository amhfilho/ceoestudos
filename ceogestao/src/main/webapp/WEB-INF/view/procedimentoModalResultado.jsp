<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:if test="${empty procedimentos}">
    <tr><td colspan="3">Nenhuma procedimento encontrado</td></tr>
</c:if>
<c:if test="${not empty procedimentos}">
    <tr><td colspan="3"><small>Escolha a quantidade e clique no nome do procedimento para adicionar</small></td></tr>
</c:if>
<c:forEach items="${procedimentos}" var="procedimento">
    <tr>
        <td><a href="javascript:adicionarProcedimento(${procedimento.id}, '${procedimento.nome}')">${procedimento.nome}</a></td>
        <td>${procedimento.tipo}</td>
        <td>
            <div class="form-group">             
                <div class="col-sm-4">
                    <select class="form-control input-sm" id="qtd">
                        <option selected="true">1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                        <option>6</option>
                        <option>7</option>
                        <option>8</option>
                        <option>9</option>
                    </select>
                </div>
            </div>
        </td>
    </tr>
</c:forEach>
<input type="hidden" id="procedimentoId" name="procedimentoId" value=""/>
