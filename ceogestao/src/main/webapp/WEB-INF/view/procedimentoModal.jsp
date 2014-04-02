<script type="text/javascript">
    $(function() {

        $("#procurarProcedimentoBtn").click(function() {

            var a = $("input[name=procurarProcedimentoText]").val();
            if (a !== "") {

                $.get("procedimentos.html", {pesquisa: a, resultado: "procedimentoModalResultado"}, function(resposta) {
                    // selecionando o elemento html através da 
                    // ID e alterando o HTML dele 
                    $("#tabelaBuscaProcedimento").html(resposta);
                });
            }
        });
    });
</script>
<!-- Modal -->
<div class="modal fade" id="procedimentoModal" tabindex="-1" role="dialog" aria-labelledby="procedimentoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="procedimentoModalLabel">Selecione um procedimento</h4>
            </div>
            <div class="modal-body">

                <input type="text" name="procurarProcedimentoText" id="procurarAlunoText" size="50" placeholder="Procure pelo nome do procedimento"/>
                <button class="btn btn-default btn-xs" type="button" id="procurarProcedimentoBtn" >Procurar</button>
                <div >
                    <table id="tabelaBuscaProcedimento" >

                    </table>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>

            </div>
        </div>
    </div>
</div>
