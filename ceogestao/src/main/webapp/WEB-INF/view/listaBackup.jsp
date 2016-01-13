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
	alert(arquivo);
		document.getElementById('fileName').value = arquivo;
		document.forms.downloadForm.submit();
	}
</script>

<h3>Lista de arquivos de Backup</h3>

<c:if test="${fn:length(arquivos) > 0}">
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
	<form name="downloadForm" action="backup/download.html" method="POST">
		<input type="hidden" name="fileName" id="fileName" />
	</form>
</c:if>