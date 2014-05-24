<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Modal -->
    <div class="modal fade bs-example-modal-lg" id="historicoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Histórico</h4>
                </div>
                <div class="modal-body">
                    <label for="dataHistorico">Data</label>
                    <input type="text" name="dataHistorico" id="dataHistorico" class="form-control"/><br>
                    <label for="dataHistorico">Descrição</label>
                    <input type="text" name="descricaoHistorico" id="descricaoHistorico" class="form-control"/>
                    <label for="idProfessor">Professor</label>
                    <select class="form-control" id="idProfessor" name="idProfessor">
                        <c:forEach items="${professores}" var="professor">
                            <option value="${professor.identificador}">${professor.nome}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" 
                            onclick="javascript:adicionarHistorico()">Adicionar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>