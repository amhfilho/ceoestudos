<script type="text/javascript">
    $(function() {

        $("#procurarProcedimentoBtn").click(function() {

            var a = $("input[name=procurarPessoaText]").val();
            if (a !== "") {

                $.get("procedimentos.html", {pesquisa: a,resultado: "procedimentoModalResultado"}, function(resposta) {
                    // selecionando o elemento html através da 
                    // ID e alterando o HTML dele 
                    $("#tabelaBuscaPessoa").html(resposta);

                });
            }
        });
    });
</script>
<!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Selecione uma procedimento</h4>
                </div>
                <div class="modal-body">

                    <input type="text" name="procurarPessoaText" id="procurarAlunoText" size="50" placeholder="Procure pelo nome"/>
                    <button class="btn btn-default btn-xs" type="button" id="procurarPessoaBtn" >Procurar</button>
                    <br>
                    <table class="table table-striped" id="tabelaBuscaPessoa" >

                    </table>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>

                </div>
            </div>
        </div>
    </div>
