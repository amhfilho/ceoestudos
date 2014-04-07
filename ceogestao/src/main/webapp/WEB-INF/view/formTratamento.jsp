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
    function configurarModalPessoa(action) {
        document.getElementById("adicionarPessoaAction").value = action;
        $('#myModal').modal('show');
    }

    function adicionarPessoa(id, nome) {
        if(document.getElementById("adicionarPessoaAction").value === "paciente"){
            document.getElementById("idPaciente").value = id;
            document.getElementById("nomePaciente").value = nome;
        }
        else {
            document.getElementById("idResponsavel").value = id;
            document.formTratamento.action = "adicionarResponsavel.html";
            document.formTratamento.submit();
            
            //if (id !== "") {

//                $.get("adicionarResponsavel.html", {pesquisa: id}, function(resposta) {
//                    $("#responsaveis").html(resposta);
//                });
//            }
//            document.location = "adicionarResponsavel.html?id="+id+"&nome="+nome;
//            
        }
        $('#myModal').modal('hide');
    }

    function configurarModalProcedimento(dente) {
        idPaciente = document.getElementById("idPaciente").value;
        if (idPaciente === "") {
            alert('Deve haver um paciente associado a este tratamento');
        } else {
            $('#procedimentoModal').modal('show');
            document.getElementById("idDente").value = dente;
        }
    }

    function adicionarProcedimento(id, qtd_id) {
        qtd = document.getElementById(qtd_id).value;
        document.getElementById("idProcedimento").value = id;
        document.getElementById("qtdProcedimento").value = qtd;
        document.formTratamento.action = "adicionarProcedimento.html";
        document.formTratamento.submit();
    }

    function removerProcedimento(idDente, idProcedimento) {
        if (confirm('Deseja remover o procedimento?')) {
            document.formTratamento.action = "removerProcedimento.html";
            document.getElementById("idDente").value = idDente;
            document.getElementById("idProcedimento").value = idProcedimento;
            document.formTratamento.submit();
        }
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
    <input type="hidden" id="qtdProcedimento" name="qtdProcedimento"/>
    <input type="hidden" id="adicionarPessoaAction" name="adicionarPessoaAction" />
    <input type="hidden" id="idResponsavel" name="idResponsavel" />
    <form:hidden path="id" />

    <div class="form-group">
        <label for="turma" class="col-sm-2 control-label">Turma</label>
        <div class="col-sm-6">
            <select id="turma" name="turma" class="form-control">
                <option value="">Sem turma associada</option>
                <c:forEach items="${todasAsTurmas}" var="turma">
                    <option value="${turma.id}" <c:if test="${tratamento.turma.id == turma.id}"> selected </c:if>>${turma}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label for="nomePaciente" class="col-sm-2 control-label">Paciente*</label>
        <div class="col-sm-6">
            <div class="input-group">
                <input type="text" class="form-control" name="nomePaciente" id="nomePaciente"
                       placeholder="Pesquisar por nome" disabled="true" value="${tratamento.paciente.nome}">

                <form:hidden path="paciente" id="idPaciente"/>

                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onclick="configurarModalPessoa('paciente')">
                        <span class="glyphicon glyphicon-search"></span> 
                    </button>
                </span>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="">Responsáveis pelo atendimento</label>
        <c:if test="${not empty tratamento.responsaveis}">
            <table class="table table-striped" id="responsaveis">
                <c:forEach items="${tratamento.responsaveis}" var="responsavel">
                    <tr>
                        <td>${responsavel.nome}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <button type="button" class="btn btn-default btn-xs" onclick="configurarModalPessoa('responsavel')">Adicionar</button>
    </div>

    <p>Clique no dente para inserir um procedimento</p>

    <table class="table table-condensed">
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

    <c:if test="${fn:length(tratamento.dentes) > 0}">       
        <table class="table table-condensed table-bordered">

            <thead>
            <th>Dente</th>
            <th>Procedimento</th>
            <th style="text-align: center">Qtd</th>
            <th style="text-align: center">Valor unitário</th>
            <th style="text-align: center">Valor</th>
            <th style="text-align: center">Remover</th>
        </thead>
        <c:forEach items="${tratamento.dentes}" var="dente">
            <tr>
                <td>${dente.dente}</td>
                <td>${dente.procedimento.nome}</td>
                <td style="text-align: center">${dente.quantidade}</td>
                <td style="text-align: right">${dente.procedimento.preco}</td>
                <td style="text-align: right">
                    <fmt:formatNumber value="${dente.valor}" type="number"
                                      minFractionDigits="2" maxFractionDigits="2"/>
                </td>
                <td style="text-align: center">
                    <a href="javascript:removerProcedimento(${dente.dente},${dente.procedimento.id})">
                        <span class="glyphicon glyphicon-remove"></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" style="text-align: right"><strong>Total</strong></td>
            <td style="text-align: right">
                <fmt:formatNumber value="${tratamento.valorBruto}" type="number"
                                  minFractionDigits="2" maxFractionDigits="2"/>
            </td>
            <td></td>
        </tr>
    </table>
</c:if>

<div class="form-group">
    <label for="obs" >Observações</label>
    <form:textarea path="obs" cssClass="form-control input-sm" id="obs"/>
</div>

<div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-primary" >Salvar</button>
        <button type="button" class="btn btn-default" onclick="location.href = 'tratamentos.html'">Voltar</button>
        <c:if test="${not empty tratamento.id}">
            <button type="button" class="btn btn-default" 
                    onclick="if (confirm('Deseja realmente excluir o tratamento? ')) {
                                location.href = 'excluirTratamento.html?id=${tratamento.id}';

                            }">
                <span class="glyphicon glyphicon-trash"></span>  Excluir
            </button>
            <button type="button" class="btn btn-default" onclick="location.href = 'novoTratamento.html'">Novo</button>
        </c:if>
    </div>
</div>

</form:form>