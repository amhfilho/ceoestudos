<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:if test="${fn:length(procedimentos)==0}">
    <p><small>Nenhum procedimento encontrado<small></p>
</c:if>
<c:if test="${fn:length(procedimentos)>0}">
<table class="table-striped" style="width: 100%">
<c:forEach items="${procedimentos}" var="procedimento">
    <tr>
        <td><small><a href="javascript:adicionarProcedimento(${procedimento.id},'${procedimento.id}_qtd','${procedimento.nome}')">
            ${procedimento.nome}
        </a></small></td>
        <td><small>${procedimento.tipo}</small></td>
        <td>
            <div class="form-group">             
                <div class="col-sm-4">
                    <select id="${procedimento.id}_qtd">
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
    <tr><td></td></tr>
</c:forEach>
</table>
</c:if>
<input type="hidden" id="procedimentoId" name="procedimentoId" value=""/>
