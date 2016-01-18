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
	function download(arquivo){
		document.getElementById('fileName').value = arquivo;
		document.forms.downloadForm.submit();
	}
	function restoreBackup(){
		if(document.getElementById('inputFile').value===''){
			alert('Nenhum arquivo foi submetido');
		} else {
			if(confirm('Deseja realmente restaurar o backup? Esta operação não poderá ser desfeita')){
				document.forms.downloadForm.action = "backup/upload.html";
				document.forms.downloadForm.enctype="multipart/form-data";
				document.forms.downloadForm.submit();
			}
		}
	}
	
	function enableButton(){
		document.getElementById('restoreButton').disabled = document.getElementById('inputFile').value==='';
	}
</script>

<c:if test="${fn:length(arquivos) == 0}">
<div class="panel panel-default">
  <div class="panel-body">
    <a href="backup.html">Recarregar</a>
  </div>
</div>
		
</c:if>

<form name="downloadForm" action="backup/download.html" method="POST">
	<input type="hidden" name="fileName" id="fileName" />
	<div class="form-group">
	    <label for="inputFile">Restaurar arquivo de backup</label>
	    <input type="file" id="inputFile" onchange="enableButton()" name="inputFile">
	    <p class="help-block">O arquivo de backup deve estar no mesmo formato em que foi feito o download.
	    <br/><b>ATENÇÃO:</b>Ao restaurar um arquivo de backup TODOS os dados serão substituídos, e não há como desfazer esta operação.
	    </p>
	  </div>
	  <button type="button" id="restoreButton" class="btn btn-default" onclick="restoreBackup()" disabled>Restaurar</button>	
</form>
<c:if test="${fn:length(arquivos) > 0}">
	<h3>Arquivos de backup disponíveis para download</h3>
	<p class="help-block">Os backups são gerados automaticamente pelo sistema todos os dias às 20 horas</p>

	<table class="table table-striped">
	
	<c:forEach items="${arquivos}" var="arquivo">
	<tr>
		<td>Backup do dia <fmt:formatDate pattern="dd/MM/yyyy" value="${arquivo.date.time}"/></td>
		<td>
			<button type="button" class="btn btn-default btn-xs" onclick="download('${arquivo.originalName}')">
                   <span class="glyphicon glyphicon-download"></span>  Download</a>
            </button>
		</td>
	</tr>
	</c:forEach>
	</table>	
</c:if>
