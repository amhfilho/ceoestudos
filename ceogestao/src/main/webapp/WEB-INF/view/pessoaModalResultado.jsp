<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:if test="${empty pessoas}">
    <td colspan="2">Nenhuma pessoa encontrada</td>
</c:if>
    
<c:forEach items="${pessoas}" var="pessoa">
    
    <div><a href="javascript:adicionarPessoa(${pessoa.identificador},'${pessoa.nome}')">${pessoa.nome}<br></div>
    
</c:forEach>
    
<input type="hidden" id="pessoaId" name="pessoaId" value=""/>
