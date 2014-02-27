<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<br>
<small>Campos obrigatórios são marcados com <strong>*</strong></small>
<br>
<form:form class="form-horizontal" role="form" method="POST" action="salvarServicoLaboratorio.html"  
           modelAttribute="servicoLaboratorio">
    <form:errors path="*">
        <div class="alert alert-danger"><form:errors path="*"/></div>
    </form:errors>

    <form:hidden path="id" id="id" name="id" />

    <div class="row">
        <label for="nomePaciente" class="col-sm-2 control-label">Paciente</label>
        <div class="col-lg-6">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Clique em Adicionar para inserir um paciente" id="nomePaciente" disabled>
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button">Adicionar</button>
                </span>
            </div><!-- /input-group -->
        </div><!-- /.col-lg-6 -->
    </div>

</form:form>

