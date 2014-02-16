<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- Modal -->

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Selecione um aluno</h4>
            </div>
            <div class="modal-body">
                <form:form class="form" role="form" method="POST" action="adicionarAluno.html"  
                           modelAttribute="aluno">

                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>



<form:form class="form" role="form" method="POST" action="salvarTurma.html"  
           modelAttribute="turma">
    <form:errors path="*">
        <div class="alert alert-danger"><form:errors path="*"/></div>
    </form:errors>
    <form:hidden path="id" id="id" name="id" />
    <div class="form-group">
        <label for="curso" >Curso</label>
        <form:select path="curso" items="${mapCursos}" cssClass="form-control input-sm" id="curso"/>
    </div>

    <div class="form-group">
        <label for="situacao" >Situação</label>
        <form:select path="situacao" items="${situacoesTurma}" cssClass="form-control input-sm" id="situacao"/>
    </div>


    <div class="form-group">
        <label for="diasDaSemana" >Dias da Semana: </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxSeg" path="diasDaSemana" value="seg" /> Seg
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxTer" path="diasDaSemana" value="ter" /> Ter
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxQua" path="diasDaSemana" value="qua" /> Qua
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxQui" path="diasDaSemana" value="qui" /> Qui
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxSex" path="diasDaSemana" value="sex" /> Sex
        </label>
        <label class="checkbox-inline">
            <form:checkbox id="checkboxSab" path="diasDaSemana" value="sab" /> Sáb
        </label>
    </div>

    <div class="form-group">
        <label for="sala" >Sala</label>
        <form:input path="sala" cssClass="form-control input-sm" id="sala"/>
    </div>

    <div class="form-group">
        <label for="quorumMinimo" >Quorum mínimo</label>
        <form:input path="quorumMinimo" cssClass="form-control input-sm" id="quorumMinimo"/>
    </div>

    <div class="form-group">
        <label for="observacoes" >Observações</label>
        <form:textarea path="observacoes" cssClass="form-control input-sm" id="observacoes"/>
    </div>
    <div class="form-group">
        <label for="listaAlunos" >Alunos matriculados</label>
        
        <table class="table table-condensed" id="listaAlunos">
            <c:if test="${empty turma.alunos}">
                <tr><td colspan="2">Nenhum aluno matriculado</td></tr>
            </c:if>
            <c:if test="${not empty turma.alunos}">
                <c:forEach items="${turma.alunos}" var="aluno">
                    <tr>
                        <td>${aluno.nome}</td><td>Excluir</td>
                    </tr>
                </c:forEach>
            </c:if>
            
            <tr>
                <td colspan="2">
                    <!-- Button trigger modal -->
                    <button class="btn btn-default btn-xs" data-toggle="modal" data-target="#myModal">
                        Adicionar
                    </button>
                </td>
            </tr>
        </table>

    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Salvar</button>
            <button type="button" class="btn btn-default" onclick="location.href = 'turmas.html'">Voltar</button>
            <button type="button" class="btn btn-default" 
                    onclick="if (confirm('Deseja realmente excluir a turma? ')) {
                                location.href = 'excluirTurma.html?id=${turma.id}';
                            }">
                <span class="glyphicon glyphicon-trash"></span>  Excluir
            </button>
        </div>
    </div>

</form:form>

