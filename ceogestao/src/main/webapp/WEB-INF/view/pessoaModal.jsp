<script type="text/javascript">
    $(function() {

        $("#procurarPessoaBtn").click(function() {

            var a = $("input[name=procurarPessoaText]").val();
            if (a !== "") {

                $.get("pessoas.html", {pesquisa: a, resultado: "pessoaModalResultado"}, function(resposta) {
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
                <h4 class="modal-title" id="myModalLabel">Selecione uma pessoa</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="procurarPessoaText">Nome</label>
                        <div class="col-sm-10">
                        <input type="text" class="form-control" name="procurarPessoaText" id="procurarAlunoText"  
                               placeholder="Procure pelo nome">
                        </div>
                    </div>
                    
                    <button class="btn btn-default btn-xs" type="button" id="procurarPessoaBtn" >Procurar</button>
                </form>      
                <div class="container">
                    <table class="table table-hover" id="tabelaBuscaPessoa">
                        
                    </table>
                    
                    
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>

            </div>
        </div>
    </div>
</div>
