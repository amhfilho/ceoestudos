<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>



<form:form role="form" action="pessoas.html">

    <div class="row">
        <div class="col-lg-6">
            <div class="input-group">
                <input type="text" class="form-control" name="pesquisa" id="pesquisa"
                       placeholder="Pesquisar por nome">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button">
                        <span class="glyphicon glyphicon-search"></span> Pesquisar
                    </button>
                </span>
            </div>
        </div>
    </div>

</form:form>

<p></p>
<c:if test="${empty pessoas}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Nenhuma pessoa encontrada
    </div>
</c:if>
<c:if test="${not empty pessoas}">  
    <div class="alert alert-info alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Foram encontradas <strong>${fn:length(pessoas)}</strong> pessoas
    </div>

    <table class="table table-striped" style="font-size: 11px">
        <tr>
        <thead>

        <th>Nome</th>
        <th>RG</th>
        <th>CPF</th>
        <th>Endereço</th>

        <th>Cidade</th>

        <th>Telefone Celular</th>
        <th>Telefone Residencial</th>
        <th>Telefone Comercial</th>
        <th>E-mail</th>
        <th>CRO</th>
        <th></th>
    </thead>
</tr>
<c:forEach items="${pessoas}" var="pessoa">
    <tr>

        <td>${pessoa.nome}</td>
        <td>${pessoa.rg}</td>
        <td>${pessoa.cpf}</td>
        <td>${pessoa.endereco},${pessoa.numero}</td>

        <td>${pessoa.cidade} - ${pessoa.estado}</td>

        <td>${pessoa.telefoneCelular}</td>
        <td>${pessoa.telefoneResidencial}</td>
        <td>${pessoa.telefoneComercial}</td>
        <td>${pessoa.email}</td>
        <td>${pessoa.cro} - ${pessoa.ufCro}</td>
        <td><a href="editarPessoa.html?id=${pessoa.identificador}"><span class="glyphicon glyphicon-pencil"></span>  Detalhes</a></td>
    </tr>
</c:forEach>
</table>
</c:if>
<form:form action="novaPessoa.html" method="POST">
    <button type="submit" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus"></span>  Adicionar Pessoa
    </button>
</form:form>