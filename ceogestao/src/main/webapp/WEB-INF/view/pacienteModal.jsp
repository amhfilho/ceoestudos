<script type="text/javascript">
    $(function() {

        $("#procurarPacienteBtn").click(function() {

            var a = $("input[name=procurarPacienteText]").val();
            if (a !== "") {

                $.get("pacientes.html", {pesquisa: a, resultado: "pacienteModalResultado"}, function(resposta) {
                    // selecionando o elemento html através da 
                    // ID e alterando o HTML dele 
                    $("#tabelaBuscaPaciente").html(resposta);

                });
            }
        });
    });
</script>
<!-- Modal -->
<div class="modal fade" id="pacienteModal" tabindex="-1" role="dialog" aria-labelledby="pacienteModallLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="pacienteModalLabel">Selecione um paciente</h4>
                <input type="text" name="procurarPacienteText" id="procurarPacienteText" size="50" placeholder="Procure pelo nome do paciente"/>
                <button class="btn btn-default btn-xs" type="button" id="procurarPacienteBtn" >Procurar</button>
            </div>
            <div class="modal-body">               
                <div id="tabelaBuscaPaciente">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>

            </div>
        </div>
    </div>
</div>
