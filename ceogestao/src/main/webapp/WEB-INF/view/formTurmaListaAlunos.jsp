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

    <c:if test="${empty listaAlunosBusca}">
        <td colspan="2">Nenhum aluno encontrado</td>
    </c:if>
        <table class="table-striped" style="width: 100%">
    <c:forEach items="${listaAlunosBusca}" var="aluno">
        <tr>
            <td >${aluno.nome}</td>
            <td style="text-align: right">
                <button type="button" class="btn btn-success btn-xs" onclick="adicionarAluno(${aluno.identificador})">
                    Matricular
                </button>
            </td>
        
        </tr>
        <tr><td></td></tr>
    </c:forEach>
    </table>
    <input type="hidden" id="alunoId" name="alunoId" value=""/>
