<!-- Modal -->
<div class="modal fade" id="parcelaModal" tabindex="-1" role="dialog" aria-labelledby="parcelaModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="parcelaModalLabel">Parcela</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" name="idParcela" id="idParcela" value="${parcela.id}"/>
                <label for="vencimentoParcela">Vencimento:</label>
                <input type="text" name="vencimentoParcela" id="vencimentoParcela" value="${}" class="form-control input-sm"/>
                <label for="vencimentoParcela">Data do pagamento:</label>
                <input type="text" name="pagamentoParcela" id="pagamentoParcela" class="form-control input-sm"/>
                <label for="valorParcela">Valor:</label>
                <input type="text" name="valorParcela" id="valorParcela" class="form-control input-sm"/>
                <label for="obsParcela">Observações:</label>
                <input type="text" name="obsParcela" id="obsParcela" class="form-control input-sm"/>
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="salvarParcelaBtn" data-dismiss="modal" onclick="salvarParcela()" >Salvar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>

            </div>
        </div>
    </div>
</div>
