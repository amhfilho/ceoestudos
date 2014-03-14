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
    
    function configurarModalProcedimento(dente){
        $('#procedimentoModal').modal('show');
        document.getElementById("idDente").value = dente;
    }
    
    function adicionarProcedimento(id,nome){
        alert(id+","+nome);
        document.getElementById("idProcedimento").value = id;
        document.formTratamento.action = "adicionarProcedimento.html";
        document.formTratamento.submit();
    }
</script>

<jsp:include page="pessoaModal.jsp" />
<jsp:include page="procedimentoModal.jsp" />

<form:form class="form-horizontal" role="form" modelAttribute="tratamento" method="POST" action="salvarTratamento.html"
           name="formTratamento">
    
    <form:errors path="*">
        <div class="alert alert-danger">
            <strong>Problemas!</strong><br>
            <form:errors path="*"/>
        </div>
    </form:errors>

    <input type="hidden" id="idDente" name="idDente"/>
    <input type="hidden" id="idProcedimento" name="idProcedimento"/>

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
                       placeholder="Pesquisar por nome" disabled="true">
                <input type="hidden" id="idPaciente" name="idPaciente" />
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onclick="configurarModalPessoa()">
                        <span class="glyphicon glyphicon-search"></span> 
                    </button>
                </span>
            </div>
        </div>
    </div>
    <c:if test="${empty tratamento.dentes}">
        <p>Clique no dente para inserir um procedimento</p>
    </c:if>
    <table class="table">
        <tr>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('18')"><img src="${pageContext.request.contextPath}/resources/images/18.jpg"/></a></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/17.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/16.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/15.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/14.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/13.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/12.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/11.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/21.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/22.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/23.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/24.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/25.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/26.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/27.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/28.jpg"/></td>        
        </tr>
        <tr>
            <td style="text-align: center">18</td>
            <td style="text-align: center">17</td>
            <td style="text-align: center">16</td>
            <td style="text-align: center">15</td>
            <td style="text-align: center">14</td>
            <td style="text-align: center">13</td>
            <td style="text-align: center">12</td>
            <td style="text-align: center">11</td>
            <td style="text-align: center">21</td>
            <td style="text-align: center">22</td>
            <td style="text-align: center">23</td>
            <td style="text-align: center">24</td>
            <td style="text-align: center">25</td>
            <td style="text-align: center">26</td>
            <td style="text-align: center">27</td>
            <td style="text-align: center">28</td>
        </tr>
        <tr>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/48.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/47.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/46.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/45.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/44.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/43.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/42.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/41.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/31.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/32.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/33.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/34.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/35.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/36.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/37.jpg"/></td>
            <td style="text-align: center"><img src="${pageContext.request.contextPath}/resources/images/38.jpg"/></td>        
        </tr>
        <tr>
            <td style="text-align: center">48</td>
            <td style="text-align: center">47</td>
            <td style="text-align: center">46</td>
            <td style="text-align: center">45</td>
            <td style="text-align: center">44</td>
            <td style="text-align: center">43</td>
            <td style="text-align: center">42</td>
            <td style="text-align: center">41</td>
            <td style="text-align: center">31</td>
            <td style="text-align: center">32</td>
            <td style="text-align: center">33</td>
            <td style="text-align: center">34</td>
            <td style="text-align: center">35</td>
            <td style="text-align: center">36</td>
            <td style="text-align: center">37</td>
            <td style="text-align: center">38</td>
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