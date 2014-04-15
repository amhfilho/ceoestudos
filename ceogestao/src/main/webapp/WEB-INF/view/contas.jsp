<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


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
        
        <div class="col-sm-1">
            <button type="submit" class="btn btn-primary">Pesquisar</button>
        </div>

    </div>
    <div class="form-group">
        <div class="checkbox">
            <label>
                <input type="checkbox" name="pagasCanceladas" value="true" id="pagasCanceladas"> Exibir contas pagas e canceladas
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
    <table class="table table-striped">
        <thead>
        <th>Turma</th>
        <th>Pessoa</th>
        <th>Papel</th>
        <th>Data</th>
        <th>Valor</th>
        <th>Descricao</th>
        <th>Forma de Pagamento</th>
        <th>Situação</th>
        <th></th>
        
    </thead>
    <c:forEach items="${contas}" var="conta">
        <tr>
            <td>${conta.turma}</td>
            <td>${conta.cliente.nome}</td>
            <td>${conta.papel}</td>
            <td><fmt:formatDate dateStyle="medium" value="${conta.vencimento}"/></td>
            <td><fmt:formatNumber value="${conta.valor}" type="number"
                              minFractionDigits="2" maxFractionDigits="2"/></td>
            <td>${conta.descricao}</td>
            <td>${conta.formaPagamento}</td>
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