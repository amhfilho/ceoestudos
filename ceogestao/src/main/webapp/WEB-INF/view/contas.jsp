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
    <div class="form-group">
        <label for="nome" class="col-sm-1 control-label">Nome</label>
        <div class="col-sm-3">
            <input type="text" class="form-control input-sm" id="nome" placeholder="Nome" name="nome">
        </div>
        <label for="cpf" class="col-sm-1 control-label">CPF</label>
        <div class="col-sm-2">
            <input type="cpf" class="form-control input-sm" id="cpf" placeholder="CPF" name="cpf">
        </div>
        
        <div class="col-sm-3">
            <select class="form-control" name="idTurma" id="turma">
                <option value="">Qualquer turma</option>
                <c:forEach items="${todasAsTurmas}" var="turma">
                    <option value="${turma.id}" <c:if test="${turma.id == idTurmaPesquisada}"> selected </c:if>>${turma}</option>
                </c:forEach>
            </select>
        </div>
        
        <div class="col-sm-1">
            <button type="submit" class="btn btn-primary">Pesquisar</button>
        </div>

    </div>
    <div class="form-group">
        <div class="checkbox">
            <label>
                <input type="checkbox" name="pagasCanceladas" 
                  <c:if test="${not empty pesquisarPagasCanceladas}">checked</c:if> id="pagasCanceladas"> Exibir contas pagas e canceladas
            </label>
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
        <th>Valor Total</th>
        <th>Valor Não Pago</th>
        <th>Num. de Parcelas</th>
        <th>Descricao</th>
        <th>Situação</th>
        <th></th>
        
    </thead>
    <c:forEach items="${contas}" var="conta">
        <tr>
            <td>${conta.turma}</td>
            <td>${conta.cliente.nome}</td>
            
            <td><fmt:formatNumber value="${conta.getValor('TOTAL')}" type="number"
                              minFractionDigits="2" maxFractionDigits="2"/>
            </td>
            <td><fmt:formatNumber value="${conta.getValor('NAO_PAGA')}" type="number"
                              minFractionDigits="2" maxFractionDigits="2"/>
            </td>
            <td>
                <c:if test="${not empty conta.parcelas}">
                    ${fn:length(conta.parcelas)}
                </c:if>
            </td>
            <td>${conta.descricao}</td>
            <td>${conta.situacao}</td>
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