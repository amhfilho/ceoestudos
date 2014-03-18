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
        <td><a href="javascript:adicionarProcedimento(${procedimento.id}, '${procedimento.nome}')">${procedimento.nome}</a></td>
        <td>${procedimento.tipo}</td>
        <td>
            <div class="form-group">
                <label for="qtd" class="col-sm-2">Qtd:</label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" id="qtd">
                        <option selected="true">1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                </div>
            </div>
            

        </td>


    </tr>
</c:forEach>
<input type="hidden" id="procedimentoId" name="procedimentoId" value=""/>
