<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script>
	function pesquisarContasPorAluno() {
		var filtro = $('input[name="inlineRadio1"]:checked').val();
		var orderBy = document.getElementById('orderBy').value;
		document.location = 'contasPorAluno.html?filtro=' + filtro + '&orderBy='+orderBy;
	}
</script>

<form class="form-inline">
<label class="radio-inline"> <input type="radio"
	name="inlineRadio1" id="inlineRadio1" value="TODOS"
	<c:if test="${empty filtro  or filtro eq 'TODOS' }"> checked </c:if>>
	 Todos
</label>
<label class="radio-inline"> <input type="radio"
	name="inlineRadio1" id="inlineRadio2" value="VENCIDOS"
	 <c:if test="${filtro eq 'VENCIDOS'}"> checked </c:if>> 
	Alunos	com parcelas em aberto
</label>

<label class="radio-inline"> <input type="radio"
	name="inlineRadio1" id="inlineRadio3" value="EM_DIA"
	<c:if test="${filtro eq 'EM_DIA'}"> checked </c:if>> 
	Alunos com parcelas quitadas
</label>
<div class="col-sm-3">
    <label for="orderBy">Ordenar por:</label>
    <select class="form-control input-sm" id="orderBy" name="orderBy">
	  <option value="ORDER_ALUNO" <c:if test="${empty orderBy or orderBy eq 'ORDER_ALUNO'}"> selected </c:if> >Aluno</option>
	  <option value="ORDER_CURSO" <c:if test="${orderBy eq 'ORDER_CURSO'}"> selected </c:if> >Curso</option>
	  <option value="ORDER_MAIOR_VALOR" <c:if test="${orderBy eq 'ORDER_MAIOR_VALOR'}"> selected </c:if> >Maior valor</option>
	  <option value="ORDER_MENOR_VALOR" <c:if test="${orderBy eq 'ORDER_MENOR_ALUNO'}"> selected </c:if> >Menor valor</option>
	</select>
 </div>


<label class="radio-inline">
	<button type="button" name="contasPorAlunoBtn" id="contasPorAlunoBtn"
		class="btn btn-primary" onclick="pesquisarContasPorAluno()">
		Pesquisar</button>
</label>

<button type="button" class="btn btn-default no-print" 
        onclick="window.print();">
        <span class="glyphicon glyphicon-print"></span>  Imprimir</a>
</button>
</form>
<p>&nbsp;</p>

<c:if test="${empty alunos}">
	<div class="alert alert-warning alert-dismissable">
		<button type="button" class="close" data-dismiss="alert"
			aria-hidden="true">&times;</button>
		Nenhum resultado encontrado
	</div>
</c:if>

<c:if test="${fn:length(alunos)>0}">
	<table class="table table-condensed" style="width: auto;">
		<thead>
			<tr>
				<th>Nome</th>
				<th>CPF</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${alunos}" var="aluno">
				<tr>
					<td>${aluno.nome}</td>
					<td colspan="3"><c:if test="${not empty aluno.cpf }">
						${aluno.cpf}
					</c:if> <c:if test="${empty aluno.cpf }">
							<i>[Não cadastrado]</i>
						</c:if></td>
				</tr>
				<c:forEach items="${aluno.contas}" var="conta">
					
					<tr>
						<td><small>
							<c:if test="${empty conta.turma}"><i>[ Sem curso associado ]</i> </c:if> 
							${conta.turma.curso.nome}
						</small></td>
						<td><small><b>Valor total:</b> ${conta.total}</small></td>
						<td><small><b>Valor pago:</b> ${conta.valorPago}</small></td>
						<td><small><b>Valor devido:</b>
								${conta.saldoDevedor}</small></td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
</c:if>