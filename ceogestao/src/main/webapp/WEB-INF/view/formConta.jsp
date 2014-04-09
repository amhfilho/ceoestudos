<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script>
    function adicionarPessoa(id, nome) {
        document.getElementById("idCliente").value = id;
        document.getElementById("nomeCliente").value = nome;
        $('#myModal').modal('hide');
    }

    function configurarModalPessoa() {
        $('#myModal').modal('show');
    }

    $(document).ready(function(e) {
        $('#vencimento').datetimepicker({
            lang: 'pt',
            i18n: {
                pt: {
                    months: [
                        'Janeiro', 'Fevereiro', 'Março', 'Abril',
                        'Maio', 'Junho', 'Julho', 'Agosto',
                        'Setembro', 'Outubro', 'Novembro', 'Dezembro'
                    ],
                    dayOfWeek: [
                        "Dom", "Seg", "Ter", "Qua",
                        "Qui", "Sex", "Sab"
                    ]
                }
            },
            timepicker: false,
            allowBlank: true,
            format: 'd/m/Y'
        });
    });
</script>
<jsp:include page="pessoaModal.jsp" />

<form:form cssClass="form-horizontal" role="form" method="POST" action="salvarConta.html" id="formConta" 
           modelAttribute="conta" >



    <form:errors path="*">
        <div class="alert alert-danger">
            <strong>Problemas!</strong><br>
            <form:errors path="*"/></div>
        </form:errors>

    <form:hidden path="id" id="id" name="id" />

    <div class="form-group">
        <div class="row">
            <label for="turma" class="col-sm-2 control-label">Turma</label>
            <div class="col-sm-6">
                <select id="turma" name="turma" class="form-control input-sm">
                    <option value="">Sem turma associada</option>
                    <c:forEach items="${todasAsTurmas}" var="turma">
                        <option value="${turma.id}" <c:if test="${conta.turma.id == turma.id}"> selected </c:if>>${turma}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label for="nomeCliente" class="col-sm-2 control-label">Cliente*</label>
            <div class="col-sm-6">
                <div class="input-group">
                    <input type="text" class="form-control" name="nomeCliente" id="nomeCliente"
                           placeholder="Pesquisar por nome" disabled="true" value="${conta.cliente.nome}">

                    <form:hidden path="cliente" id="idCliente"/>

                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="configurarModalPessoa()">
                            <span class="glyphicon glyphicon-search"></span> 
                        </button>
                    </span>
                </div>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label for="vencimento" class="col-sm-2 control-label">Vencimento</label>
            <div class="col-sm-2">
                <form:input path="vencimento" id="vencimento" cssClass="form-control input-sm"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label for="valor" class="col-sm-2 control-label">Valor</label>
            <div class="col-sm-2">
                <form:input path="valor" id="valor" cssClass="form-control input-sm"/>
            </div>

        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <label for="tipoConta" class="col-sm-2 control-label">Tipo</label>
            <div class="col-sm-2">
                <form:select path="tipoConta" id="tipoConta" cssClass="form-control input-sm">
                    <form:option value="A PAGAR">A Pagar</form:option>
                    <form:option value="A RECEBER">A Receber</form:option>
                </form:select>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <label for="situacao" class="col-sm-2 control-label">Situação</label>
            <div class="col-sm-2">
                <form:select path="situacao" id="situacao" cssClass="form-control input-sm">
                    <form:option value="PENDENTE">Pendente</form:option>
                    <form:option value="PAGA">Paga</form:option>
                    <form:option value="CANCELADA">Cancelada</form:option>
                </form:select>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <label for="descricao" class="col-sm-2 control-label">Descrição</label>
            <div class="col-sm-6">
                <form:input path="descricao" id="descricao" placeholder="" cssClass="form-control input-sm"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label for="formaPagamento" class="col-sm-2 control-label">Forma de Pagamento</label>
            <div class="col-sm-2">
                <form:input path="formaPagamento" id="formaPagamento" placeholder="" cssClass="form-control input-sm"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary" >Salvar</button>
            <button type="button" class="btn btn-default" onclick="location.href = 'contas.html'">Voltar</button>
            <c:if test="${not empty conta.id}">
                <button type="button" class="btn btn-default" 
                        onclick="if (confirm('Deseja realmente excluir a conta? ')) {
                                    location.href = 'excluirConta.html?id=${conta.id}';

                                }">
                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
                <button type="button" class="btn btn-default" onclick="location.href = 'novaConta.html'">Novo</button>
            </c:if>
        </div>
    </div>
</form:form>