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
    $(document).ready(function() {
        $('#pessoasTable').dataTable({
            "paging":   false,
            "info":     false,
            "bFilter":  false
            
            
        });
    } );
    function pesquisarPessoas(tipo){
        acao = tipo+"s.html";
        form = document.getElementById('pesquisaForm');
        form.action = acao;
        form.submit();
    }
    
    function novaPessoa(tipo){
        acao = "novo"+tipo+".html";
        document.location = acao;
    }
</script>


<form id="pesquisaForm">
    <div class="row">
        <div class="col-lg-6">
            <div class="input-group">
                <input type="text" class="form-control" name="pesquisa" id="pesquisa"
                       placeholder="Pesquisar por nome">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onclick="pesquisarPessoas('${tipoPessoa}')">
                        <span class="glyphicon glyphicon-search"></span> Pesquisar
                    </button>
                </span>
            </div>
        </div>
    </div>
</form>


<p></p>
<c:if test="${empty pessoas}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhum ${tipoPessoa} encontrado
    </div>
</c:if>
<c:if test="${not empty pessoas}">  
    <div class="alert alert-info alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Foram encontrados <strong>${fn:length(pessoas)}</strong> ${tipoPessoa}<c:if test="${fn:length(pessoas)>1}">s</c:if>
    </div>

    <table class="table table-condensed table-bordered" style="font-size: 11px" id="pessoasTable">
        
        <thead>
        <th>Nome</th>
        <th>Data de inclusão</th>    
        <c:if test="${tipoPessoa == 'interessado'}">
            <th>Curso de interesse</th>
            <th>Contato efetuado</th>
        </c:if>      
        <c:if test="${tipoPessoa == 'aluno'}">
            <th>Turma</th>
        </c:if>
        <th>Cidade - Estado</th>
        <th>Telefone Celular</th>
        <th>Telefone Residencial</th>
        <th>Telefone Comercial</th>
        
        <th></th>
    </thead>
<tbody>
    <c:forEach items="${pessoas}" var="pessoa">
        <tr
            <c:if test="${tipoPessoa == 'interessado' and not empty pessoa.contato}">
                class="success"
            </c:if>
        >
            <td>${pessoa.nome}</td>
            <td>
                <span style="display: none"><fmt:formatDate pattern="yyyy/MM/dd" value="${pessoa.dataInclusao}"/> </span>
                <fmt:formatDate pattern="dd/MM/yyyy" value="${pessoa.dataInclusao}"/>
                
            </td>   
            <c:if test="${tipoPessoa == 'interessado' }">
                <td>${pessoa.cursoInteresse.nome}</td>
                <td>${pessoa.contato}</td>
            </c:if>
            <c:if test="${tipoPessoa == 'aluno'}">
                <td>${pessoa.nomeTurmas}</td>
            </c:if>
            <td>${pessoa.cidade} - ${pessoa.estado}</td>
            <td>${pessoa.telefoneCelular}</td>
            <td>${pessoa.telefoneResidencial}</td>
            <td>${pessoa.telefoneComercial}</td>

            <td>
                <button type="button" class="btn btn-default btn-xs" onclick="document.location = 'editarPessoa.html?id=${pessoa.identificador}'">
                        <span class="glyphicon glyphicon-pencil"></span>  Detalhes</a>
                </button>
            </td>
        </tr>
    </c:forEach>
</tbody>
</table>
</c:if>

    <button type="button" class="btn btn-primary" onclick="novaPessoa('${tipoPessoa}')">
        <span class="glyphicon glyphicon-plus"></span>  Adicionar ${tipoPessoa}
    </button>
