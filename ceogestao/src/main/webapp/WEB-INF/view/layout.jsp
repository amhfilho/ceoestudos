<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ceo" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>CEO Estudos - Sistema de Gestão</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/resources/css/navbar-fixed-top.css" rel="stylesheet">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery-ui-1.10.4.custom.css" />
        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>  
        <script src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.4.custom.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.4.custom.min.js"></script>

    </head>
    <body>

        <!-- Fixed navbar -->
        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        <img class="img-rounded" src="${pageContext.request.contextPath}/resources/images/CEO_DR2.png" />
                    </a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="turmas.html">Turmas</a></li>
                        <li><a href="#about">Inscrições do Site</a></li>
                        <li><a href="#contact">Financeiro</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Cadastro <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="pessoas.html">Pessoas</a></li>
                                <li><a href="cursos.html">Cursos</a></li>
                                <li><a href="#">Procedimentos</a></li>
                                <li class="divider"></li>
                                <li class="dropdown-header">Nav header</li>
                                <li><a href="#">Separated link</a></li>
                                <li><a href="#">One more separated link</a></li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#">Default</a></li>
                        <li><a href="#">Static top</a></li>
                        <li class="active"><a href="#">Fixed top</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <div class="container">

            <!-- Main component for a primary marketing message or call to action -->
            <div >

                <div class="page-header">
                    <h1>
                        Sistema de Gestão
                        <small><tiles:insertAttribute name="title" /></small>

                    </h1>
                </div>
                <c:if test="${SUCCESS_MESSAGE != null}">
                    <div class="alert alert-success">${SUCCESS_MESSAGE}</div>
                </c:if>

                <c:if test="${ERROR_MESSAGE != null}">
                    <div class="alert alert-danger">${ERROR_MESSAGE}</div>
                </c:if>        
                <tiles:insertAttribute name="body" />

            </div>

        </div> <!-- /container -->


        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->

        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

    </body>
</html>
