
<script type="text/javascript">
	function validSalvarPagamento(){
		var vencimento = document.getElementById('dataPagamento').value;
		var valor = document.getElementById('valorPagamento').value;
		var forma = document.getElementById('formaPagamento').value;
		return ((vencimento!="") && (valor!="") && (forma!=""));
	}
	
	function enableBotaoSalvar(){
		document.getElementById('salvarPagamentoBtn').disabled = !validSalvarPagamento();
	}
</script>
<!-- Modal -->
<div class="modal fade" id="pagamentoModal" tabindex="-1" role="dialog" aria-labelledby="pagamentoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="pagamentoModalLabel">Pagamento</h4>
            </div>
            <div class="modal-body">
            	<input type="hidden" name="idPagamento" id="idPagamento" />
                <label for="dataPagamento">Data:</label>
                <input type="text" name="dataPagamento" id="dataPagamento"  class="form-control input-sm" onblur="enableBotaoSalvar()"/>
                <label for="valorPagamento">Valor:</label>
                <input type="text" name="valorPagamento" id="valorPagamento"  class="form-control input-sm" onblur="enableBotaoSalvar()"/>
                <label for="formaPagamento">Forma de Pagamento:</label>
                <select id="formaPagamento" name="formaPagamento" class="form-control input-sm" onblur="enableBotaoSalvar()">                   
                   <option value="DINHEIRO">DINHEIRO</option>
                   <option value="CHEQUE">CHEQUE</option>
                   <option value="BOLETO">BOLETO</option>   
                </select>
                <label for="obsParcela">Banco</label>
                <input type="text" name="banco" id="banco"  class="form-control input-sm"/>
                <label for="obsParcela">Cheque núm</label>
                <input type="text" name="numCheque" id="numCheque"  class="form-control input-sm"/>
                <label for="obsParcela">Obs</label>
                <input type="text" name="obsPagamento" id="obsPagamento"  class="form-control input-sm"/>
            </div>
            <div class="modal-footer">
            	<button type="button" class="btn btn-primary" 
            			id="salvarPagamentoBtn" 
            			data-dismiss="modal" 
            			onclick="salvarPagamento()"
            			disabled >Salvar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>

            </div>
            
        </div>
    </div>
</div>
