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
    function adicionarAluno(id){
        document.turmaForm.action = "adicionarAluno.html";
        document.getElementById("alunoId").value = id;
        document.turmaForm.submit();
    }
</script>
    <c:forEach items="${listaAlunosBusca}" var="aluno">
        <tr>
            <td>${aluno.nome}</td><td><a href="#" onclick="adicionarAluno(${aluno.identificador})">Adicionar</a></td>
        
        </tr>
    </c:forEach>
    <input type="hidden" id="alunoId" name="alunoId" value=""/>
