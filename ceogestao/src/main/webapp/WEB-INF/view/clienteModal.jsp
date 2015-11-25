<script type="text/javascript">
    $(function() {

        $("#procurarClienteBtn").click(function() {

            var a = $("input[name=procurarClienteText]").val();
            if (a !== "") {

                $.get("clientes.html", {pesquisa: a, resultado: "clienteModalResultado"}, function(resposta) {
                    // selecionando o elemento html através da 
                    // ID e alterando o HTML dele 
                    $("#tabelaBuscaCliente").html(resposta);

                });
            }
        });
    });
</script>
<!-- Modal -->
<div class="modal fade" id="clienteModal" tabindex="-1" role="dialog" aria-labelledby="clienteModallLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="clienteModalLabel">Procure por um aluno ou paciente</h4>
                <input type="text" name="procurarClienteText" id="procurarClienteText" size="50" placeholder="Procure pelo nome do aluno ou paciente"/>
                <button class="btn btn-default btn-xs" type="button" id="procurarClienteBtn" >Procurar</button>
            </div>
            <div class="modal-body">               
                <div id="tabelaBuscaCliente">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>

            </div>
        </div>
    </div>
</div>
