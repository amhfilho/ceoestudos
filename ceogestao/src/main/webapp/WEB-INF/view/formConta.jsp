<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script>
    function adicionarCliente(id, nome) {
        document.getElementById("idCliente").value = id;
        document.getElementById("nomeCliente").value = nome;
        $('#clienteModal').modal('hide');
    }

    function configurarModalCliente() {
        $('#clienteModal').modal('show');
    }
    
    function configurarModalParcela() {
        if(document.forms.formConta.id.value === null || document.forms.formConta.id.value === ""){
            alert ('Salvar conta primeiro');
        } else {
            $('#parcelaModal').modal('show');
        }
    }
    
    function editarParcela(id,vencimento,pagamento,valor,obs){
        
        document.getElementById("idParcela").value=id;
        document.getElementById("vencimentoParcela").value=vencimento;
        document.getElementById("pagamentoParcela").value=pagamento;
        document.getElementById("valorParcela").value=valor;
        document.getElementById("obsParcela").value=obs;
        $('#parcelaModal').modal('show');
    }
    
    function salvarParcela(){
        
        document.forms.formConta.action = "salvarParcela.html";
        document.forms.formConta.submit();
    }
    
    function excluirParcela(id){
        if (confirm('Deseja realmente excluir a parcela? ')) {
            document.forms.formConta.action = "excluirParcela.html";
            document.getElementById("idParcelaExcluir").value=id;
            document.forms.formConta.submit();
        }
    }

    $(document).ready(function(e) {
        $('#vencimentoParcela').datetimepicker({
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
        
        $('#pagamentoParcela').datetimepicker({
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
        
        $('#valorParcela').mask("#.##0,00", {reverse: true});
    });
</script>
<jsp:include page="clienteModal.jsp" />


<form:form cssClass="form-horizontal" role="form" method="POST" action="salvarConta.html" id="formConta" 
           modelAttribute="conta" >
    
    <jsp:include page="parcelaModal.jsp" />
    
    <input type="hidden" id="idParcelaExcluir" name="idParcelaExcluir" />
    
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
            <label for="nomeCliente" class="col-sm-2 control-label">Cliente (Aluno ou Paciente)*</label>
            <div class="col-sm-6">
                <div class="input-group">
                    <input type="text" class="form-control" name="nomeCliente" id="nomeCliente"
                           placeholder="Pesquisar por nome" disabled="true" value="${conta.cliente.nome}">

                    <form:hidden path="cliente" id="idCliente"/>

                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="configurarModalCliente()">
                            <span class="glyphicon glyphicon-search"></span> 
                        </button>
                    </span>
                </div>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label for="situacao" class="col-sm-2 control-label">Situação</label>
            <div class="col-sm-6" id="situacao">
                ${conta.situacao}
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label for="valor" class="col-sm-2 control-label">Valor total</label>
            <div class="col-sm-2" id="valor">
                <fmt:formatNumber value="${conta.getValor('TOTAL')}" type="number"  minFractionDigits="2" maxFractionDigits="2"/>
            </div>
        </div>
    </div>
            
   <div class="form-group">
        <div class="row">
            <label for="valorPago" class="col-sm-2 control-label">Valor pago</label>
            <div class="col-sm-2" id="valorPago">
                <fmt:formatNumber value="${conta.getValor('PAGA')}" type="number"  minFractionDigits="2" maxFractionDigits="2"/>
            </div>
        </div>
    </div>
          
    <div class="form-group">
        <div class="row">
            <label for="parcelas" class="col-sm-2 control-label">Parcelas</label>
            <div class="col-sm-6">
                <c:if test="${not empty conta.parcelas}">
                    <table class="table table-bordered" style="font-size: 11px" id="parcelas">
                        <thead><th>Valor</th><th>Vencimento</th><th>Data Pagto</th><th>Forma pagto</th><th></th></thead>
                        <tbody>    
                            <c:forEach items="${conta.parcelas}" var="parcela">
                                <tr>
                                    <td>
                                        <span style="text-align: right">
                                            <fmt:formatNumber value="${parcela.valor}" type="number"  minFractionDigits="2" maxFractionDigits="2"/>
                                        </span>
                                    </td>
                                    <td><span style="text-align: center"><fmt:formatDate pattern="dd/MM/yyyy" value="${parcela.vencimento}"/></span></td>
                                    <td><span style="text-align: center"><fmt:formatDate pattern="dd/MM/yyyy" value="${parcela.pagamento}"/></span></td>
                                    <td>${parcela.obs}</td>
                                    <td><span style="text-align: center">
                                        <c:if test="${not empty parcela.id}">
                                            <button type="button" class="btn btn-default btn-xs" 
                                                    onclick="editarParcela('${parcela.id}',
                                                                           '<fmt:formatDate pattern="dd/MM/yyyy" value="${parcela.vencimento}"/>',
                                                                           '<fmt:formatDate pattern="dd/MM/yyyy" value="${parcela.pagamento}"/>',
                                                                           '<fmt:formatNumber value="${parcela.valor}" type="number"  minFractionDigits="2" maxFractionDigits="2"/>',
                                                                           '${parcela.obs}')">
                                                <span class="glyphicon glyphicon-pencil"></span>  Detalhes</a>
                                            </button>
                                        </c:if>
                                        <button type="button" class="btn btn-default btn-xs" onclick="excluirParcela(${parcela.id})">
                                                <span class="glyphicon glyphicon-trash"></span>  Excluir</a>
                                        </button>
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty conta.parcelas}">
                    <small>Atualmente o valor desta conta é 0 (zero). Você deve inserir ao menos uma parcela</small>
                </c:if>
                <button type="button" class="btn btn-default btn-xs" onclick="configurarModalParcela()">Adicionar</button>
            </div>
        </div>
    </div>
            
            <div class="form-group">
        <div class="row">
            <label for="valorNaoPago" class="col-sm-2 control-label">Valor não pago</label>
            <div class="col-sm-2" id="valorNaoPago">
                <fmt:formatNumber value="${conta.getValor('NAO_PAGA')}" type="number"  minFractionDigits="2" maxFractionDigits="2"/>
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
            <label for="descricao" class="col-sm-2 control-label">Observações</label>
            <div class="col-sm-6">
                <form:input path="descricao" id="descricao" placeholder="" cssClass="form-control input-sm"/>
            </div>
        </div>
    </div>

    
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary" >Salvar</button>
            <button type="button" class="btn btn-default" onclick="location.href = 'pesquisarContas.html'">Voltar</button>
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