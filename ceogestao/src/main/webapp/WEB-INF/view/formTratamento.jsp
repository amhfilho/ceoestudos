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
    function configurarModalPessoa() {
//        document.getElementById("idFieldName").value = idFieldName;
//        document.getElementById("nomeFieldName").value = nomeFieldName;
        $('#myModal').modal('show');
    }

    function adicionarPessoa(id, nome) {
//        var idFieldName = document.getElementById("idFieldName").value;
//        var nomeFieldName = document.getElementById("nomeFieldName").value;
        document.getElementById("idPaciente").value = id;
        document.getElementById("nomePaciente").value = nome;
        $('#myModal').modal('hide');
    }
</script>

<jsp:include page="pessoaModal.jsp" />

<form:form class="form-horizontal" role="form" modelAttribute="tratamento" method="POST" action="salvarTratamento.html">
    <form:errors path="*">
        <div class="alert alert-danger">
            <strong>Problemas!</strong><br>
            <form:errors path="*"/>
        </div>
    </form:errors>

    <div class="form-group">
        <label for="turma" class="col-sm-2 control-label">Turma</label>
        <div class="col-sm-6">
            <form:select path="turma" id="turma" cssClass="form-control">
                <form:option value="" label="Sem turma associada"/>
                <form:options items="${todasAsTurmas}"/>
            </form:select>
        </div>
    </div>

    <div class="form-group">
        <label for="nomePaciente" class="col-sm-2 control-label">Paciente*</label>
        <div class="col-sm-6">
            <div class="input-group">
                <input type="text" class="form-control" name="nomePaciente" id="nomePaciente"
                       placeholder="Pesquisar por nome">
                <input type="hidden" id="idPaciente" name="idPaciente" />
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onclick="configurarModalPessoa()">
                        <span class="glyphicon glyphicon-search"></span> 
                    </button>
                </span>
            </div>
        </div>
    </div>


    <table class="table">
        <tr>
            <td><img src="${pageContext.request.contextPath}/resources/images/18.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/17.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/16.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/15.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/14.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/13.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/12.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/11.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/21.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/22.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/23.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/24.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/25.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/26.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/27.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/28.jpg"/></td>        
        </tr>
        <tr>
            <td>
                <div class="checkbox"><label><input type="checkbox" value="">18</label></div>
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td><img src="${pageContext.request.contextPath}/resources/images/48.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/47.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/46.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/45.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/44.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/43.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/42.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/41.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/31.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/32.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/33.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/34.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/35.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/36.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/37.jpg"/></td>
            <td><img src="${pageContext.request.contextPath}/resources/images/38.jpg"/></td>        
        </tr>
    </table>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary" >Salvar</button>
            <button type="button" class="btn btn-default" onclick="location.href = '#'">Voltar</button>
            <c:if test="${not empty tratamento.id}">
                <button type="button" class="btn btn-default" 
                        onclick="if (confirm('Deseja realmente excluir o tratamento? ')) {
                                    location.href = '#';

                                }">
                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
                <button type="button" class="btn btn-default" onclick="location.href = 'novoTratamento.html'">Novo</button>
            </c:if>
        </div>
    </div>

</form:form>