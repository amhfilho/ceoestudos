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
    
    function editarParcela(id,vencimento,valor,obs){
        
        document.getElementById("idParcela").value=id;
        document.getElementById("vencimentoParcela").value=vencimento;
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
        
        $('#dataPagamento').datetimepicker({
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
        
        $('#dtPrimeiraParcela').datetimepicker({
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
        
        $('#valor').mask("#.##0,00", {reverse: true});
        
        
        $('#parcelas').dataTable({
            "paging":   false,
            "info":     false,
            "bFilter":  false      
        });
        
        $('#pagamentos').dataTable({
            "paging":   false,
            "info":     false,
            "bFilter":  false      
        });
    });
    
    function adicionarPagamento(){
    	document.getElementById('idPagamento').value = "";
    	document.getElementById('dataPagamento').value = "";
    	document.getElementById('valorPagamento').value = "";
    	document.getElementById('formaPagamento').value = "";
    	document.getElementById('numCheque').value = "";
    	document.getElementById('banco').value = ""; 
    	document.getElementById('obsPagamento').value = ""; 
    	$('#pagamentoModal').modal('show');  	
    }
    
    function salvarPagamento(){
    	document.formConta.action = "salvarPagamento.html";  	
    	document.formConta.submit();
    }
    
    function aplicarTaxaJuros(){
    	if(valorValido()){
    	  
    	  document.formConta.action = "aplicarTaxaJuros.html";  	
    	  document.formConta.submit();
    	
    	}	
    }
    
    function valorValido(){
    	return parseFloat(document.getElementById('valor').value) > 0;
    }
    
    function editarPagamento(id,data,valor,forma,cheque, banco,obs){
    	document.getElementById('idPagamento').value = id;
    	document.getElementById('dataPagamento').value = data;
    	document.getElementById('valorPagamento').value = valor;
    	document.getElementById('formaPagamento').value = forma;
    	document.getElementById('numCheque').value = cheque;
    	document.getElementById('banco').value = banco;
    	document.getElementById('obsPagamento').value = obs; 
    	$('#pagamentoModal').modal('show');	
    }
    
    function excluirPagamento(id){
    	if(confirm('Deseja realmente excluir este pagamento?')){
    		document.formConta.action = "excluirPagamento.html";
    		document.getElementById('idPagamento').value = id;
    		document.formConta.submit();
    		$('#pagamentoModal').modal('hide');	
    	}
    }
    
    function excluirConta(){
    	if(confirm('Deseja realmente excluir esta conta? Todas as parcelas e pagamentos serão excluídos')){
    		document.formConta.action = "excluirConta.html";
    		
    		document.formConta.submit();
    	}
    }
</script>
<jsp:include page="clienteModal.jsp" />


<form:form cssClass="form-horizontal" role="form" method="POST" action="salvarConta.html" id="formConta" 
           modelAttribute="conta" name="formConta">
    <jsp:include page="pagamentoModal.jsp" />
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
            <label for="valorOrcamento" class="col-sm-2 control-label">Valor da conta/orçamento</label>
            <div class="col-sm-2" id="valorDiv">
            <input type="text" class="form-control input-sm col-sm-1" style="text-align:right" id="valor" name="valor" 
                value=<c:if test="${not empty(conta.valor)}"><fmt:formatNumber value="${conta.valor}" type="number"  minFractionDigits="2" maxFractionDigits="2"/></c:if>
                 />
            </div>
        </div>
    </div>
            
   <div class="form-group">
        <div class="row">
            <label for="valorPago" class="col-sm-2 control-label">Valor pago</label>
            <div class="col-sm-2" id="valorPago">
                <fmt:formatNumber value="${conta.valorPago}" type="number"  minFractionDigits="2" maxFractionDigits="2"/>
            </div>
        </div>
    </div>
    
    <div class="form-group">
        <div class="row">
            <label for="saldoDevedor" class="col-sm-2 control-label">Saldo devedor</label>
            <div class="col-sm-2" id="saldoDevedor">
                <fmt:formatNumber value="${conta.saldoDevedor}" type="number"  minFractionDigits="2" maxFractionDigits="2"/>
            </div>
        </div>
    </div>
    
    <c:if test="${conta.valor > 0 or fn:length(conta.parcelas) > 0 }">
    <div class="form-group">
        <div class="row">
        	<label for="opcoesParcelamento" class="col-sm-2 control-label">Parcelamento</label>
            <div class="col-sm-6" id="opcoesParcelamento">
            	<table>
            		<tr>
            		    <td style="text-align: left">Parcelas</td>
            			<td><input type="number" class="form-control input-sm col-sm-1" 
            					style="text-align: center; width: 50px;" id="numParcelas" name="numParcelas"
            					value="${fn:length(conta.parcelas)}"/>
            			</td>
            			
            			<td>Taxa de juros</td>
            			<td><input type="text" class="form-control input-sm col-sm-1" 
            					style="text-align: center; width: 50px" id="taxaJuros" name="taxaJuros"
            					value=<fmt:formatNumber value='${conta.taxaJuros}' type="number"  minFractionDigits="2" maxFractionDigits="2"/>
            					/>
            			</td>
            			<td>Data da primeira parcela</td>
            			<td>
            			<input type="text" class="form-control input-sm col-sm-1" 
            					style="text-align: center" id="dtPrimeiraParcela" name="dtPrimeiraParcela"
            					value=""/>
            			</td>
            			<td>
            				<button type="button" class="btn btn-default btn-xs" onclick="aplicarTaxaJuros()">
                            	<span class="glyphicon glyphicon-ok"></span>  Aplicar
                            </button>
            			</td>
            			
            		</tr>
            	</table>
            </div>
        </div>
    </div>
   
    
          
    <div class="form-group">
        <div class="row">
            <label for="parcelas" class="col-sm-2 control-label">Parcelas</label>
            <div class="col-sm-6">
                <c:if test="${fn:length(conta.parcelas) > 0}">
                    <table class="table table-bordered" style="font-size: 11px" id="parcelas">
                        <thead><th>Valor</th><th>Vencimento</th><th>Obs</th><th></th></thead>
                        <tbody>    
                            <c:forEach items="${conta.parcelas}" var="parcela">
                                <tr>
                                    <td style="text-align: right">
                                        <fmt:formatNumber value="${parcela.valor}" type="number"  minFractionDigits="2" maxFractionDigits="2"/>
                                    </td>
                                    <td style="text-align: center"><fmt:formatDate pattern="dd/MM/yyyy" value="${parcela.vencimento}"/></td>
                                    
                                    <td>${parcela.obs}</td>
                                    <td style="text-align: center">
                                        <c:if test="${not empty parcela.id}">
                                            <button type="button" class="btn btn-default btn-xs" 
                                                    onclick="editarParcela('${parcela.id}',
                                                                           '<fmt:formatDate pattern="dd/MM/yyyy" value="${parcela.vencimento}"/>',
                                                                           '<fmt:formatNumber value="${parcela.valor}" type="number"  
                                                                           				minFractionDigits="2" maxFractionDigits="2"/>',
                                                                           '${parcela.obs}')">
                                                <span class="glyphicon glyphicon-pencil"></span>  Detalhes
                                            </button>
                                        </c:if>
                                        <button type="button" class="btn btn-default btn-xs" onclick="excluirParcela(${parcela.id})">
                                                <span class="glyphicon glyphicon-trash"></span>  Excluir
                                        </button>
                                       
                                    </td>
                                </tr>
                            </c:forEach>
                            
                        </tbody>
                    </table>
                    <table class="table table-bordered">
                         <tr>
                            	<td colspan="4" style="text-align:right">Total</td>
                            	<td style="text-align:right"><fmt:formatNumber value="${conta.total}" type="number"  minFractionDigits="2" maxFractionDigits="2"/></td>
                         </tr>
                    </table>
                </c:if>
                <c:if test="${empty conta.parcelas}">
                    <small>Atualmente o valor desta conta é 0 (zero). Você deve inserir ao menos uma parcela</small>
                </c:if>
                <button type="button" class="btn btn-default btn-xs" onclick="configurarModalParcela()">Adicionar</button>
            </div>
        </div>
    </div>
    </c:if>
    
    <div class="form-group">
        <div class="row">
            <label for="parcelas" class="col-sm-2 control-label">Pagamentos</label>
            <div class="col-sm-8">
            	<c:if test="${fn:length(conta.pagamentos) ==0 }">
            		<p>Não há pagamentos realizados</p>
            	</c:if>
            	<c:if test="${fn:length(conta.pagamentos) >0 }">
	            <table class="table table-bordered" style="font-size: 11px" id="pagamentos">
	            <thead>
	            	<th style="text-align:right">Valor</th>
	            	<th style="text-align:center">Data</th>
	            	<th>Forma pagto</th>
	            	<th>Núm cheque</th>
	            	<th>Banco</th>
	            	<th></th>
	            </thead>
	            <tbody>
	            <c:forEach items="${conta.pagamentos}" var="pagamento">
	            	<tr>
	            		<td style="text-align:right"><fmt:formatNumber value="${pagamento.valor }" 
	            			type="number"  minFractionDigits="2" maxFractionDigits="2"/>
	            		</td>
	            		<td style="text-align:center"><fmt:formatDate pattern="dd/MM/yyyy" value="${pagamento.dataPagamento.time}"/></td>
	            		<td style="text-align:left">${pagamento.formaPagamento}</td>
	            		<td style="text-align:left">${pagamento.numeroCheque}</td>
	            		<td style="text-align:left">${pagamento.banco}</td>
	            		<td style="text-align:center">
	            			<button type="button" class="btn btn-default btn-xs" 
	            				onclick="editarPagamento(${pagamento.id},
	            				                         '<fmt:formatDate pattern="dd/MM/yyyy" value="${pagamento.dataPagamento.time}"/>',
	            				                         '<fmt:formatNumber value="${pagamento.valor }" type="number"  minFractionDigits="2" maxFractionDigits="2"/>',
	            				                         '${pagamento.formaPagamento}',
	            				                         '${pagamento.numeroCheque}',
	            				                         '${pagamento.banco}',
	            				                         '${pagamento.obs}')">
	            			
                                 <span class="glyphicon glyphicon-pencil"></span>  Detalhes
                            </button>
	            			<button type="button" class="btn btn-default btn-xs" onclick="excluirPagamento(${pagamento.id})">
                                 <span class="glyphicon glyphicon-trash"></span>  Excluir
                            </button>
	            		</td>
	            	</tr>
	            	
	            </c:forEach>
	            </tbody>
	            
	            </table>
            </c:if>
            <button type="button" class="btn btn-default btn-xs" 
	            	      onclick="adicionarPagamento()" >Adicionar</button>
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
                        onclick="excluirConta()">
                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
                <button type="button" class="btn btn-default" onclick="location.href = 'novaConta.html'">Novo</button>
            </c:if>
        </div>
    </div>
</form:form>