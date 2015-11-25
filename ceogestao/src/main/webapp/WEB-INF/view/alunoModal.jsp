<script type="text/javascript">
    $(function() {

        $("#procurarAlunoBtn").click(function() {

            var a = $("input[name=procurarAlunoText]").val();
            if (a !== "") {

                $.get("alunos.html", {pesquisa: a, resultado: "alunoModalResultado"}, function(resposta) {
                    // selecionando o elemento html através da 
                    // ID e alterando o HTML dele 
                    $("#tabelaBuscaAluno").html(resposta);

                });
            }
        });
    });
</script>
<!-- Modal -->
<div class="modal fade" id="alunoModal" tabindex="-1" role="dialog" aria-labelledby="alunoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="alunoModalLabel">Selecione um aluno</h4>
                <input type="text" name="procurarAlunoText" id="procurarAlunoText" size="50" placeholder="Procure pelo nome do aluno"/>
                <button class="btn btn-default btn-xs" type="button" id="procurarAlunoBtn" >Procurar</button>
            </div>
            <div class="modal-body">               
                <div id="tabelaBuscaAluno">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>

            </div>
        </div>
    </div>
</div>
