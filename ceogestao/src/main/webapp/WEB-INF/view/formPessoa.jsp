<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">
    function enableUfCro() {
        var cro = document.getElementById("cro").value;
        document.getElementById("ufCro").disabled = cro === "";
    }

</script>

<form:form class="form-horizontal" role="form" method="POST" action="salvarPessoa.html"  
           modelAttribute="pessoa" >
    <form:errors path="*">
        <div class="alert alert-danger"><form:errors path="*"/></div>
    </form:errors>
    <small>Campos obrigatórios são marcados com <strong>*</strong></small>
    <br>
    <form:hidden path="identificador" id="identificador" name="identificador" />
    <div class="row">
        <label for="nome" class="col-sm-2 control-label">Nome*</label>
        <div class="col-sm-10">
            <form:input cssClass="form-control input-sm" id="nome" placeholder="Nome completo" path="nome" />
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="checkbox">
                <label>
                    <form:checkbox id="checkboxProfessor" path="professor" /> Professor
                </label>
            </div>
        </div>
    </div>


    <div class="form-group">
        <label for="cpf" class="col-sm-2 control-label">CPF*</label>
        <div class="col-sm-10">
            <form:input path="cpf" cssClass="form-control input-sm" id="cpf" placeholder="Sem pontos e traços" />
        </div>
    </div>


    <div class="form-group">
        <label for="rg" class="col-sm-2 control-label">RG</label>
        <div class="col-sm-10">
            <form:input cssClass="form-control input-sm" id="rg" placeholder="RG" path="rg" />
        </div>
    </div>


    <div class="form-group">
        <label for="email" class="col-sm-2 control-label">Email*</label>
        <div class="col-sm-10">
            <form:input cssClass="form-control input-sm" id="email" placeholder="Email" path="email"/>
        </div>
    </div>
    <script type="text/javascript">
        /*
         $(document).ready(function(e) {
         $('#dataNascimento').datetimepicker({
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
         
         
         });
         */
    </script> 
    <div class="form-group">
        <label for="dataNascimento" class="col-sm-2 control-label">Data de Nascimento</label>
        <div class="col-sm-2">
            <form:input cssClass="form-control input-sm" id="dataNascimento" placeholder="Ex: 21/01/2004" path="dataNascimento" />    
        </div>
        <span class="help-block">Data deve estar no formato dia/mês/ano com 4 dígitos</span>
    </div>         

    <div class="form-group">
        <label for="sexo" class="col-sm-2 control-label">Sexo</label>
        <div class="col-sm-2">
            <select class="form-control input-sm" name="sexo" id="sexo">
                <option value="MASCULINO">Masculino</option>
                <option value="FEMININO">Feminino</option>
            </select>
        </div>
    </div>


    <div class="form-group">
        <label for="endereco" class="col-sm-2 control-label">Endereco</label>
        <div class="col-sm-10">
            <input type="text" class="form-control input-sm" id="endereco" placeholder="Rua, avenida, travessa, praça, etc" 
                   name="endereco">
        </div>
    </div>

    <div class="form-group">
        <label for="numero" class="col-sm-2 control-label">Número</label>
        <div class="col-sm-10">
            <input type="text" class="form-control input-sm" id="numero" placeholder="Número" 
                   name="numero">
        </div>
    </div>

    <div class="form-group">
        <label for="complemento" class="col-sm-2 control-label">Complemento</label>
        <div class="col-sm-10">
            <input type="text" class="form-control input-sm" id="complemento" placeholder="Apartamento, bloco, casa, etc" 
                   name="complemento">
        </div>
    </div>

    <div class="form-group">
        <label for="bairro" class="col-sm-2 control-label">Bairro</label>
        <div class="col-sm-10">
            <input type="text" class="form-control input-sm" id="bairro" placeholder="Bairro" 
                   name="bairro">
        </div>
    </div>

    <div class="form-group">
        <label for="cep" class="col-sm-2 control-label">CEP</label>
        <div class="col-sm-10">
            <input type="text" class="form-control input-sm" id="cep" placeholder="CEP" 
                   name="cep">
        </div>
    </div>

    <div class="form-group">
        <label for="cidade" class="col-sm-2 control-label">Cidade</label>
        <div class="col-sm-10">
            <input type="text" class="form-control input-sm" id="cidade" placeholder="Cidade" 
                   name="cidade">
        </div>
    </div>

    <div class="form-group">
        <label for="estado" class="col-sm-2 control-label">Estado</label>
        <div class="col-sm-2">
            <select class="form-control input-sm" name="estado" id="estado">
                <option value="AC" <c:if test="${pessoa.estado == 'AC'}"> selected</c:if> >Acre</option>
                <option value="AL" <c:if test="${pessoa.estado == 'AC'}"> selected</c:if> >Alagoas</option>
                <option value="AM" <c:if test="${pessoa.estado == 'AM'}"> selected</c:if> >Amazonas</option>
                <option value="AP" <c:if test="${pessoa.estado == 'AP'}"> selected</c:if> >Amapá</option>
                <option value="BA" <c:if test="${pessoa.estado == 'BA'}"> selected</c:if> >Bahia</option>
                <option value="CE" <c:if test="${pessoa.estado == 'CE'}"> selected</c:if> >Ceará</option>
                <option value="DF" <c:if test="${pessoa.estado == 'DF'}"> selected</c:if> >Distrito Federal</option>
                <option value="ES" <c:if test="${pessoa.estado == 'ES'}"> selected</c:if> >Espirito Santo</option>
                <option value="GO" <c:if test="${pessoa.estado == 'GO'}"> selected</c:if> >Goiás</option>
                <option value="MA" <c:if test="${pessoa.estado == 'MA'}"> selected</c:if> >Maranhão</option>
                <option value="MG" <c:if test="${pessoa.estado == 'MG'}"> selected</c:if> >Minas Gerais</option>
                <option value="MS" <c:if test="${pessoa.estado == 'MS'}"> selected</c:if> >Mato Grosso do Sul</option>
                <option value="MT" <c:if test="${pessoa.estado == 'MT'}"> selected</c:if> >Mato Grosso</option>
                <option value="PA" <c:if test="${pessoa.estado == 'PA'}"> selected</c:if> >Pará</option>
                <option value="PB" <c:if test="${pessoa.estado == 'PB'}"> selected</c:if> >Paraíba</option>
                <option value="PE" <c:if test="${pessoa.estado == 'PE'}"> selected</c:if> >Pernambuco</option>
                <option value="PI" <c:if test="${pessoa.estado == 'PI'}"> selected</c:if> >Piauí</option>
                <option value="PR" <c:if test="${pessoa.estado == 'PR'}"> selected</c:if> >Paraná</option>
                <option value="RJ" <c:if test="${pessoa.estado == 'RJ'}"> selected</c:if> >Rio de Janeiro</option>
                <option value="RN" <c:if test="${pessoa.estado == 'RN'}"> selected</c:if> >Rio Grande do Norte</option>
                <option value="RO" <c:if test="${pessoa.estado == 'RO'}"> selected</c:if> >Rondônia</option>
                <option value="RR" <c:if test="${pessoa.estado == 'RR'}"> selected</c:if> >Roraima</option>
                <option value="RS" <c:if test="${pessoa.estado == 'RS'}"> selected</c:if> >Rio Grande do Sul</option>
                <option value="SC" <c:if test="${pessoa.estado == 'SC'}"> selected</c:if> >Santa Catarina</option>
                <option value="SE" <c:if test="${pessoa.estado == 'SE'}"> selected</c:if> >Sergipe</option>
                <option value="SP" <c:if test="${pessoa.estado == 'SP'}"> selected</c:if> >São Paulo</option>
                <option value="TO" <c:if test="${pessoa.estado == 'TO'}"> selected</c:if> >Tocantins</option>

                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="telefoneResidencial" class="col-sm-2 control-label">Tel. Residencial</label>
            <div class="col-sm-10">
                <input type="text" class="form-control input-sm" id="telefoneResidencial" placeholder="Telefone com DDD" 
                       name="telefoneResidencial">
            </div>
        </div>

        <div class="form-group">
            <label for="telefoneCelular" class="col-sm-2 control-label">Tel. Celular</label>
            <div class="col-sm-10">
                <input type="text" class="form-control input-sm" id="telefoneCelular" placeholder="Telefone com DDD" 
                       name="telefoneCelular">
            </div>
        </div>

        <div class="form-group">
            <label for="telefoneComercial" class="col-sm-2 control-label">Tel. Comercial</label>
            <div class="col-sm-10">
                <input type="text" class="form-control input-sm" id="telefoneComercial" placeholder="Telefone com DDD" 
                       name="telefoneComercial">
            </div>
        </div>

        <div class="form-group">
            <label for="cro" class="col-sm-2 control-label">CRO</label>
            <div class="col-sm-2">
                <input type="text" class="form-control input-sm" id="cro" placeholder="CRO" 
                       name="cro" onchange="enableUfCro();" onkeypress="this.onchange();" 
                       onpaste="this.onchange();" oninput="this.onchange();">
            </div>
            <label for="ufCro" class="col-sm-2 control-label">U.F. do CRO</label>
            <div class="col-sm-2">
                <select class="form-control input-sm" name="ufCro" id="ufCro" disabled="true">
                    <option value="" selected >Selecione</option>
                    <option value="AC" <c:if test="${pessoa.ufCro == 'AC'}"> selected</c:if> >Acre</option>
                <option value="AL" <c:if test="${pessoa.ufCro == 'AC'}"> selected</c:if> >Alagoas</option>
                <option value="AM" <c:if test="${pessoa.ufCro == 'AM'}"> selected</c:if> >Amazonas</option>
                <option value="AP" <c:if test="${pessoa.ufCro == 'AP'}"> selected</c:if> >Amapá</option>
                <option value="BA" <c:if test="${pessoa.ufCro == 'BA'}"> selected</c:if> >Bahia</option>
                <option value="CE" <c:if test="${pessoa.ufCro == 'CE'}"> selected</c:if> >Ceará</option>
                <option value="DF" <c:if test="${pessoa.ufCro == 'DF'}"> selected</c:if> >Distrito Federal</option>
                <option value="ES" <c:if test="${pessoa.ufCro == 'ES'}"> selected</c:if> >Espirito Santo</option>
                <option value="GO" <c:if test="${pessoa.ufCro == 'GO'}"> selected</c:if> >Goiás</option>
                <option value="MA" <c:if test="${pessoa.ufCro == 'MA'}"> selected</c:if> >Maranhão</option>
                <option value="MG" <c:if test="${pessoa.ufCro == 'MG'}"> selected</c:if> >Minas Gerais</option>
                <option value="MS" <c:if test="${pessoa.ufCro == 'MS'}"> selected</c:if> >Mato Grosso do Sul</option>
                <option value="MT" <c:if test="${pessoa.ufCro == 'MT'}"> selected</c:if> >Mato Grosso</option>
                <option value="PA" <c:if test="${pessoa.ufCro == 'PA'}"> selected</c:if> >Pará</option>
                <option value="PB" <c:if test="${pessoa.ufCro == 'PB'}"> selected</c:if> >Paraíba</option>
                <option value="PE" <c:if test="${pessoa.ufCro == 'PE'}"> selected</c:if> >Pernambuco</option>
                <option value="PI" <c:if test="${pessoa.ufCro == 'PI'}"> selected</c:if> >Piauí</option>
                <option value="PR" <c:if test="${pessoa.ufCro == 'PR'}"> selected</c:if> >Paraná</option>
                <option value="RJ" <c:if test="${pessoa.ufCro == 'RJ'}"> selected</c:if>> Rio de Janeiro</option>
                <option value="RN" <c:if test="${pessoa.ufCro == 'RN'}"> selected</c:if> >Rio Grande do Norte</option>
                <option value="RO" <c:if test="${pessoa.ufCro == 'RO'}"> selected</c:if> >Rondônia</option>
                <option value="RR" <c:if test="${pessoa.ufCro == 'RR'}"> selected</c:if> >Roraima</option>
                <option value="RS" <c:if test="${pessoa.ufCro == 'RS'}"> selected</c:if> >Rio Grande do Sul</option>
                <option value="SC" <c:if test="${pessoa.ufCro == 'SC'}"> selected</c:if> >Santa Catarina</option>
                <option value="SE" <c:if test="${pessoa.ufCro == 'SE'}"> selected</c:if> >Sergipe</option>
                <option value="SP" <c:if test="${pessoa.ufCro == 'SP'}"> selected</c:if> >São Paulo</option>
                <option value="TO" <c:if test="${pessoa.ufCro == 'TO'}"> selected</c:if> >Tocantins</option>
                </select>
            </div>
        </div>





        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Salvar</button>
                <button type="button" class="btn btn-default" onclick="location.href = 'pessoas.html'">Voltar</button>
            <c:if test="${not empty pessoa.identificador}">
                <button type="button" class="btn btn-default" 
                        onclick="if (confirm('Deseja realmente excluir a pessoa? ')) {
                                    location.href = 'excluirPessoa.html?id=${pessoa.identificador}';
                                }">
                    <span class="glyphicon glyphicon-trash"></span>  Excluir
                </button>
                <button type="button" class="btn btn-default" onclick="location.href = 'novaPessoa.html'">Novo</button>
            </c:if>
        </div>
    </div>


</form:form>


