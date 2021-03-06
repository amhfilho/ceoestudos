<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script>
    $(document).ready(function(e) {
        $('#dataHistorico').datetimepicker({
            lang: 'pt',
            i18n: {
                pt: {
                    months: [
                        'Janeiro', 'Fevereiro', 'Mar�o', 'Abril',
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
        
        $('#historicoTable').dataTable({
            "paging":   false,
            "info":     false,
            "bFilter":  false             
        });
        
        
    });
    
    function excluirTratamento(id){
    	if(confirm('S� ser� poss�vel excluir o tratamento se n�o houver nenhum registro financeiro associado.Deseja continuar?')){
    		document.getElementById("idTratamento").value = id;
        	document.formTratamento.action = "excluirTratamento.html";
        	document.formTratamento.submit();
    	}
    }
   
       
    function configurarModalPaciente(action) {
        document.getElementById("adicionarPessoaAction").value = action;
        $('#pacienteModal').modal('show');
    }
    
    function configurarModalAluno(action) {
        document.getElementById("adicionarPessoaAction").value = action;
        $('#alunoModal').modal('show');
    }
    
    function adicionarPaciente(id,nome){
        document.getElementById("idPaciente").value = id;
        document.getElementById("nomePaciente").value = nome;
        $('#pacienteModal').modal('hide');
    }
    
    function adicionarAluno(id,nome){
        document.getElementById("idResponsavel").value = id;
        document.formTratamento.action = "adicionarResponsavel.html";
        document.formTratamento.submit();
        $('#alunoModal').modal('hide');
    }

    function configurarModalHistorico() {
        //document.getElementById("adicionarPessoaAction").value = action;
        $('#historicoModal').modal('show');
    }

    function configurarModalProcedimento(dente) {
        idPaciente = document.getElementById("idPaciente").value;
        document.getElementById("tipoProcedimento").value = "procedimentoDente";
        if (idPaciente === "") {
            alert('Deve haver um paciente associado a este tratamento');
        } else {
            $('#procedimentoModal').modal('show');
            document.getElementById("idDente").value = dente;
        }
    }
    
    function configurarModalProcedimentoAvulso() {
        idPaciente = document.getElementById("idPaciente").value;
        document.getElementById("tipoProcedimento").value = "procedimentoAvulso";
        if (idPaciente === "") {
            alert('Deve haver um paciente associado a este tratamento');
        } else {
            $('#procedimentoModal').modal('show');
        }
    }

    function adicionarProcedimento(id, qtd_id) {
        qtd = document.getElementById(qtd_id).value;
        document.getElementById("idProcedimento").value = id;
        document.getElementById("qtdProcedimento").value = qtd;
        tipoProcedimento = document.getElementById("tipoProcedimento").value;
        if(tipoProcedimento === 'procedimentoDente'){
            document.formTratamento.action = "adicionarProcedimento.html";
        } else {
            document.formTratamento.action = "adicionarProcedimentoAvulso.html";
        }
        document.formTratamento.submit();
        $('#procedimentoModal').modal('hide');
    }

    function removerProcedimento(idTratamentoDente) {
        if (confirm('Deseja remover o procedimento?')) {
            document.formTratamento.action = "removerProcedimento.html";
            document.getElementById("idTratamentoDente").value = idTratamentoDente;
            document.formTratamento.submit();
        }
    }

    function adicionarHistorico() {
        document.formTratamento.action = "adicionarHistorico.html";
        //document.getElementById("dataHistorico").value = data;
        //document.getElementById("descricao").value = descricao;
        document.formTratamento.submit();
    }

    function aplicarTaxa() {
        //taxa = document.getElementById("taxa").value;
        document.formTratamento.action = "aplicarTaxa.html";
        document.formTratamento.submit();
    }
    
    function removerResponsavel(idAluno){
        document.formTratamento.action = "removerResponsavel.html";
        document.getElementById("idResponsavel").value = idAluno;
        document.formTratamento.submit();
    }
    
    function removerHistorico(id){
        if(confirm('Deseja realmente remover esta linha de hist�rico?')){
            document.formTratamento.action = "removerTratamentoHistorico.html";
            document.getElementById("idTratamentoHistorico").value = id ;
            document.formTratamento.submit();
        }
    }
    
    function aprovarOrcamento(status){
        var action;
        if(status==='APROVADO'){
            action = "cancelar";
            document.formTratamento.action = "cancelarOrcamento.html";
        }
        else if(status==='NAO_APROVADO'){
            action="aprovar";
            document.formTratamento.action = "aprovarOrcamento.html";
        }
        if(confirm('Deseja realmente '+action+' o orcamento?')){
            document.formTratamento.submit();
        }
    }
</script>



<form:form class="form-horizontal" role="form" modelAttribute="tratamento" method="POST" action="salvarTratamento.html"
           name="formTratamento">
    <jsp:include page="pacienteModal.jsp" />
<jsp:include page="alunoModal.jsp" />
<jsp:include page="procedimentoModal.jsp" />
    <jsp:include page="historicoModal.jsp" />

    <form:errors path="*">
        <div class="alert alert-danger">
            <strong>Problemas!</strong><br>
            <form:errors path="*"/>
        </div>
    </form:errors>

    <input type="hidden" id="idDente" name="idDente"/>
    <input type="hidden" id="idProcedimento" name="idProcedimento"/>
    <input type="hidden" id="qtdProcedimento" name="qtdProcedimento"/>
    <input type="hidden" id="adicionarPessoaAction" name="adicionarPessoaAction" />
    <input type="hidden" id="idResponsavel" name="idResponsavel" />
    <input type="hidden" id="idTratamentoDente" name="idTratamentoDente" />
    <input type="hidden" id="tipoProcedimento" name="tipoProcedimento" />
    <input type="hidden" id="idTratamentoHistorico" name="idTratamentoHistorico" />
    <input type="hidden" id="idTratamento" name="idTratamento" />
    
    <form:hidden path="id" />
    <c:if test="${tratamento.status == 'NAO_APROVADO'}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <span class="sr-only">Warning:</span>
            Or�amento n�o aprovado
          </div>
    </c:if>
    <c:if test="${tratamento.status == 'APROVADO'}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
            <span class="sr-only">OK:</span>
            Or�amento aprovado
          </div>
    </c:if>
    <c:if test="${tratamento.status == 'CANCELADO'}">
        <div class="alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            <span class="sr-only">Error:</span>
            Or�amento cancelado
          </div>
    </c:if>
    

    <div class="form-group">
        <label for="turma" class="col-sm-2 control-label">Turma</label>
        <div class="col-sm-6">
            <select id="turma" name="turma" class="form-control input-sm">
                <option value="">Sem turma associada</option>
                <c:forEach items="${todasAsTurmas}" var="turma">
                    <option value="${turma.id}" <c:if test="${tratamento.turma.id == turma.id}"> selected </c:if>>${turma}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label for="nomePaciente" class="col-sm-2 control-label">Paciente*</label>
        <div class="col-sm-6">
            <div class="input-group">
                <input type="text" class="form-control" name="nomePaciente" id="nomePaciente"
                       placeholder="Pesquisar por nome" disabled="true" value="${tratamento.paciente.nome}">

                <form:hidden path="paciente" id="idPaciente"/>

                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onclick="configurarModalPaciente('paciente')">
                        <span class="glyphicon glyphicon-search"></span> 
                    </button>
                </span>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="">Alunos respons�veis pelo atendimento</label>
        <c:if test="${fn:length(tratamento.responsaveis) == 0}">
            <p>N�o h� aluno respons�vel por este tratamento</p>
        </c:if>
        <c:if test="${fn:length(tratamento.responsaveis) > 0}">
            <table class="table table-striped" id="responsaveis">
                <c:forEach items="${tratamento.responsaveis}" var="responsavel">
                    <tr>
                        <td>${responsavel.nome}</td>
                        <td>
                            <button type="button" class="btn btn-default btn-xs" 
                                    onclick="if (confirm('Deseja realmente excluir o aluno? ')) {
                                    location.href = 'javascript:removerResponsavel(${responsavel.identificador})';
                                }">
                            <span class="glyphicon glyphicon-trash"></span>  Excluir
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <p><button type="button" class="btn btn-default btn-xs" onclick="configurarModalAluno('responsavel')">Adicionar aluno</button></p>
    </div>
                
    <div class="form-group">
        <label for="" >Procedimentos avulsos</label>
        <c:if test="${fn:length(tratamento.procedimentosAvulsos) > 0}">
            <table class="table table-condensed table-bordered" id="responsaveis">
                <thead>
                    <th>Procedimento</th>
                    <th style="text-align: center">Qtd</th>
                    <th style="text-align: right">Valor unit�rio</th>
                    <th style="text-align: right">Sub-total</th>
                    <th style="text-align: center">Remover</th>
                </thead>
                <tbody>
                    <c:set var="sum" value="${0}"/>
                    <c:forEach items="${tratamento.procedimentosAvulsos}" var="avulso">
                        <tr>
                            <td>${avulso.procedimento.nome}</td>
                            <td style="text-align: center">${avulso.qtd}</td>
                            <td style="text-align: right">
                                <fmt:formatNumber value="${avulso.procedimento.preco}" 
                                                  type="number" groupingUsed='true' minFractionDigits="2" maxFractionDigits="2"/>
                            </td>
                            <td style="text-align: right">
                                <fmt:formatNumber value="${avulso.total}" 
                                                  type="number" groupingUsed='true' minFractionDigits="2" maxFractionDigits="2"/>
                            </td>
                            <td style="text-align: center">
                                <a href="javascript:removerProcedimentoAvulso(${avulso.id})">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </a>
                            </td>
                        </tr>
                        <c:set var="sum" value="${sum + avulso.total}"/> 
                    </c:forEach>
                        <tr>
                            <td style="text-align: right" colspan="3"><b>Total:</b></td>
                            <td style="text-align: right">
                                <fmt:formatNumber value="${sum}" 
                                                  type="number" groupingUsed='true' minFractionDigits="2" maxFractionDigits="2"/>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                </tbody>
            </table>
        </c:if>
        <button type="button" class="btn btn-default btn-xs" onclick="configurarModalProcedimentoAvulso()">Adicionar procedimento</button>
    </div>

    <p>Clique no dente para inserir um procedimento</p>

    <table class="table table-condensed">
        <tr>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('18')"><img src="${pageContext.request.contextPath}/resources/images/18.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('17')"><img src="${pageContext.request.contextPath}/resources/images/17.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('16')"><img src="${pageContext.request.contextPath}/resources/images/16.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('15')"><img src="${pageContext.request.contextPath}/resources/images/15.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('14')"><img src="${pageContext.request.contextPath}/resources/images/14.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('13')"><img src="${pageContext.request.contextPath}/resources/images/13.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('12')"><img src="${pageContext.request.contextPath}/resources/images/12.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('11')"><img src="${pageContext.request.contextPath}/resources/images/11.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('21')"><img src="${pageContext.request.contextPath}/resources/images/21.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('22')"><img src="${pageContext.request.contextPath}/resources/images/22.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('23')"><img src="${pageContext.request.contextPath}/resources/images/23.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('24')"><img src="${pageContext.request.contextPath}/resources/images/24.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('25')"><img src="${pageContext.request.contextPath}/resources/images/25.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('26')"><img src="${pageContext.request.contextPath}/resources/images/26.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('27')"><img src="${pageContext.request.contextPath}/resources/images/27.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('28')"><img src="${pageContext.request.contextPath}/resources/images/28.jpg"/></a></td>        
        </tr>
        <tr>
            <td style="text-align: center">18</td>
            <td style="text-align: center">17</td>
            <td style="text-align: center">16</td>
            <td style="text-align: center">15</td>
            <td style="text-align: center">14</td>
            <td style="text-align: center">13</td>
            <td style="text-align: center">12</td>
            <td style="text-align: center">11</td>
            <td style="text-align: center">21</td>
            <td style="text-align: center">22</td>
            <td style="text-align: center">23</td>
            <td style="text-align: center">24</td>
            <td style="text-align: center">25</td>
            <td style="text-align: center">26</td>
            <td style="text-align: center">27</td>
            <td style="text-align: center">28</td>
        </tr>
        <tr>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('48')"><img src="${pageContext.request.contextPath}/resources/images/48.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('47')"><img src="${pageContext.request.contextPath}/resources/images/47.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('46')"><img src="${pageContext.request.contextPath}/resources/images/46.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('45')"><img src="${pageContext.request.contextPath}/resources/images/45.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('44')"><img src="${pageContext.request.contextPath}/resources/images/44.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('43')"><img src="${pageContext.request.contextPath}/resources/images/43.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('42')"><img src="${pageContext.request.contextPath}/resources/images/42.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('41')"><img src="${pageContext.request.contextPath}/resources/images/41.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('31')"><img src="${pageContext.request.contextPath}/resources/images/31.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('32')"><img src="${pageContext.request.contextPath}/resources/images/32.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('33')"><img src="${pageContext.request.contextPath}/resources/images/33.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('34')"><img src="${pageContext.request.contextPath}/resources/images/34.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('35')"><img src="${pageContext.request.contextPath}/resources/images/35.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('36')"><img src="${pageContext.request.contextPath}/resources/images/36.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('37')"><img src="${pageContext.request.contextPath}/resources/images/37.jpg"/></a></td>
            <td style="text-align: center"><a href="javascript:configurarModalProcedimento('38')"><img src="${pageContext.request.contextPath}/resources/images/38.jpg"/></a></td>        
        </tr>
        <tr>
            <td style="text-align: center">48</td>
            <td style="text-align: center">47</td>
            <td style="text-align: center">46</td>
            <td style="text-align: center">45</td>
            <td style="text-align: center">44</td>
            <td style="text-align: center">43</td>
            <td style="text-align: center">42</td>
            <td style="text-align: center">41</td>
            <td style="text-align: center">31</td>
            <td style="text-align: center">32</td>
            <td style="text-align: center">33</td>
            <td style="text-align: center">34</td>
            <td style="text-align: center">35</td>
            <td style="text-align: center">36</td>
            <td style="text-align: center">37</td>
            <td style="text-align: center">38</td>
        </tr>
    </table>

    <c:if test="${fn:length(tratamento.dentes) > 0}">       
        <table class="table table-condensed table-bordered">

            <thead>
            <th>Dente</th>
            <th>Procedimento</th>
            <th style="text-align: center">Qtd</th>
            <th style="text-align: center">Valor unit�rio</th>
            <th style="text-align: center">Valor</th>
            <th style="text-align: center">Remover</th>
        </thead>
        <c:forEach items="${tratamento.dentes}" var="dente">
            <tr>
                <td>${dente.dente}</td>
                <td>${dente.procedimento.nome}</td>
                <td style="text-align: center">${dente.quantidade}</td>
                <td style="text-align: right">${dente.procedimento.preco}</td>
                <td style="text-align: right">
                    <fmt:formatNumber value="${dente.valor}" type="number"
                                      minFractionDigits="2" maxFractionDigits="2"/>
                </td>
                <td style="text-align: center">
                    <a href="javascript:removerProcedimento(${dente.id})">
                        <span class="glyphicon glyphicon-remove"></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
            
            <tr>
                <td colspan="4" style="text-align: right"><strong>Total</strong></td>
                <td style="text-align: right">
                    <fmt:formatNumber value="${tratamento.valorBruto}" type="number"
                                      minFractionDigits="2" maxFractionDigits="2"/>
                </td>
                <td></td>
            </tr>
        </table>
    </c:if>
        <table class="table table-condensed table-bordered" style="width: auto">
            <tr>
                <td style="text-align: right"><b>Total tratamento:</b></td>
                <td style="text-align: right"><fmt:formatNumber value="${tratamento.totalTratamento}" 
                                  type="number" minFractionDigits="2" maxFractionDigits="2" groupingUsed="true"/> 
                </td>
            </tr>
            <tr> 
                <td><form:input path="taxa" cssClass="form-control input-sm" id="taxa"/></td>
                <td style="text-align: right">
                    <button type="button" class="btn btn-default btn-xs" onclick="aplicarTaxa();">
                        Aplicar taxa %
                    </button>
                </td>
            </tr>
            <tr>
                <td style="text-align: right"><b>Taxa (%):</b></td>
                <td style="text-align: right">${tratamento.taxa}</td>
            </tr>
            <tr>
                <td style="text-align: right"><b>Total com taxa</b></td>
                <td style="text-align: right"><fmt:formatNumber value="${tratamento.valorComTaxa}" 
                                  type="number" minFractionDigits="2" maxFractionDigits="2" groupingUsed="true"/> 
                </td>
            </tr>
        </table>

<div class="form-group">
    <label for="obs" >Observa��es</label>
    <form:textarea path="obs" cssClass="form-control input-sm" id="obs"/>
</div>

<div class="form-group">
    <label for="historico" >Hist�rico de atendimento</label>
    <c:if test="${fn:length(tratamento.historico)==0}">
        <p>N�o h� hist�rico de atendimento</p>
    </c:if>
    <c:if test="${fn:length(tratamento.historico)>0}">
        <table class="table table-striped" id="historicoTable">
            <thead>
            <th>Data</th>
            <th>Descri��o</th>
            <th>Professor</th>
            <th>&nbsp;</th>
            </thead>
            <tbody>
            <c:forEach items="${tratamento.historico}" var="historico">
                <tr>
                    <td>
                        <span style="display: none"><fmt:formatDate pattern="yyyy/MM/dd" value="${historico.data}"/> </span>
                        <fmt:formatDate pattern="dd/MM/yyyy" value="${historico.data}"/>
                    </td>
                    <td>${historico.descricao}</td>
                    <td>${historico.professor.nome}</td>
                    <td style="text-align: center">
                        <button type="button" class="btn btn-default btn-xs" 
                                    onclick='removerHistorico(${historico.id})'>
                            <span class="glyphicon glyphicon-trash"></span>  Excluir
                         </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </c:if>
    <button type="button" class="btn btn-default btn-xs" 
            onclick="javascript:configurarModalHistorico();">Adicionar</button>
</div>

<div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-primary" >Salvar</button>
        <button type="button" class="btn btn-default" onclick="location.href = 'tratamentos.html'">Voltar</button>
        <c:if test="${not empty tratamento.id}">
            <button type="button" class="btn btn-default" 
                    onclick="excluirTratamento(${tratamento.id})">
                <span class="glyphicon glyphicon-trash"></span>  Excluir
            </button>
            <button type="button" class="btn btn-default" onclick="location.href = 'novoTratamento.html'">Novo</button>
            <c:if test="${tratamento.status == 'NAO_APROVADO'}">
                <button type="button" class="btn btn-success" 
                        onclick="aprovarOrcamento('${tratamento.status}')">Aprovar or�amento</button>
            </c:if>
            <c:if test="${tratamento.status == 'APROVADO' or tratamento.status == 'NAO_APROVADO'}">
                <button type="button" class="btn btn-danger" 
                        onclick="aprovarOrcamento('${tratamento.status}')">Cancelar or�amento</button>
            </c:if>
        </c:if>
    </div>
</div>

</form:form>