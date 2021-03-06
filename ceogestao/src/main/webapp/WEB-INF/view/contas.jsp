<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script>
    $(document).ready(function() {
        $('#contasTable').dataTable({
            "paging":   false,
            "info":     false,
            "bFilter":  false
            
            
        });
    } );
</script>

<form class="form-horizontal" role="form" action="pesquisarContas.html">
    <div class="form-group form-group-sm">
        <label for="nome" class="col-sm-1 control-label">Nome</label>
        <div class="col-sm-2">
            <input type="text" class="form-control input-sm" id="nome" placeholder="Nome" name="nome" value="${nomePesquisa}">
        </div>
        <label for="cpf" class="col-sm-1 control-label">CPF</label>
        <div class="col-sm-2">
            <input type="cpf" class="form-control input-sm" id="cpf" placeholder="CPF" name="cpf" value="${cpfPesquisa}">
        </div>
        
        <div class="col-sm-3">
            <select class="form-control input-sm" name="idTurma" id="turma">
                <option value="">Qualquer turma</option>
                <c:forEach items="${todasAsTurmas}" var="turma">
                    <option value="${turma.id}" <c:if test="${turma.id == idTurmaPesquisada}"> selected </c:if>>${turma}</option>
                </c:forEach>
            </select>
        </div>
        
        <div class="col-sm-2">
            <select class="form-control input-sm" name="situacao" id="situacao">
                <option value="-1" <c:if test="${situacaoPesquisa == -1}"> selected </c:if>>Todas as contas</option>
                <option value="0" <c:if test="${situacaoPesquisa == 0}"> selected </c:if>>Somente contas quitadas</option>
                <option value="1" <c:if test="${situacaoPesquisa == 1}"> selected </c:if>>Somente contas com saldo devedor</option>
            </select>
        </div>
        
        <div class="col-sm-1">
            <button type="submit" class="btn btn-primary">Pesquisar</button>
        </div>

    </div>
    
</form>
<c:if test="${empty contas}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhuma conta encontrada
    </div>
</c:if>

<c:if test="${not empty contas}">
    <table class="table table-striped" id="contasTable">
        <thead>
        <th>Turma</th>
        <th>Cliente</th>
        <th>Valor orçamento</th>
        <th>Valor Total Parcelas</th>
        <th>Saldo devedor</th>
        <th>Num. de Parcelas</th>
        <th></th>
        
    </thead>
    <c:forEach items="${contas}" var="conta">
        <tr>
            <td>${conta.turma}</td>
            <td>${conta.cliente.nome}</td>
            <td><fmt:formatNumber value="${conta.valor}" type="number"
                              minFractionDigits="2" maxFractionDigits="2"/>
            </td>
            
            <td><fmt:formatNumber value="${conta.total}" type="number"
                              minFractionDigits="2" maxFractionDigits="2"/>
            </td>
            <td><fmt:formatNumber value="${conta.saldoDevedor}" type="number"
                              minFractionDigits="2" maxFractionDigits="2"/>
            </td>
            <td>
                <c:if test="${not empty conta.parcelas}">
                    ${fn:length(conta.parcelas)}
                </c:if>
            </td>
            
            
            <td>
                <button type="button" class="btn btn-default btn-xs" onclick="document.location = 'editarConta.html?id=${conta.id}'">
                   <span class="glyphicon glyphicon-pencil"></span>  Detalhes</a>
                </button>
            </td>
        </tr>
    </c:forEach>
</table>
</c:if>

<button type="button" class="btn btn-primary" onclick="document.location = 'novaConta.html'">
    <span class="glyphicon glyphicon-plus"></span>  Nova Conta
</button>