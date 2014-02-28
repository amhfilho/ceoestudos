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
        <td colspan="2">Nenhuma procedimento encontrado</td>
    </c:if>
    <c:forEach items="${procedimentos}" var="procedimento">
        <tr>
            <td>${procedimento.nome}</td>
            <td>${procedimento.tipo}</td>
            <td style="text-align: center">
                <button type="button" class="btn btn-success btn-xs" 
                        onclick="adicionarProcedimento(${procedimento.id},'${procedimento.nome}')">
                    Confirmar
                </button>
            </td>
        
        </tr>
    </c:forEach>
    <input type="hidden" id="procedimentoId" name="procedimentoId" value=""/>
