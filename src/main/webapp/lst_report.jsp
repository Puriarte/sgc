<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt-rt.tld" prefix="fmt-rt"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
    <link rel="icon" href="images/icon.png">
	<title>G.C.P.</title>

  	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/dashboard.css?var=<%=com.jcabi.manifests.Manifests.read("App-Version")%>" rel="stylesheet">

    <script type="text/ecmascript" src="js/jquery-2.1.1.min.js"></script> 
    <script type="text/ecmascript" src="js/jquery.jqGrid-5.2.1.js"></script>
    <script type="text/ecmascript" src="js/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" language="javascript" src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
   
    <link rel="stylesheet" type="text/css" media="screen" href="css/jquery/ui.jqgrid-bootstrap.css" />

	<script src="js/report.js?var=<%= com.jcabi.manifests.Manifests.read("App-Version") %>1233" type="text/javascript"></script>

	<style>
		.ui-jqgrid tr.jqgrow td {	white-space: normal}
		.nav-sidebar{	margin-bottom: 5px;}
		.btn{	padding: 3px 6px}
	</style>

</head>
<body>
<jsp:include page="menu.jsp" />
	<html:form action="/lstReport.do" method="post"
		styleId="frmLstReport" style="margin: 0px 0px 0px 0px;">

		<bean:define id="report" name="frmLstReport" property="report"/>
		<bean:define id="fechaDesde" name="frmLstReport" property="fechaDesde"/>
		<bean:define id="fechaHasta" name="frmLstReport" property="fechaHasta"/>

		<html:hidden property="accion" styleId="accion" />
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
					<ul class="nav nav-sidebar">
					<c:if test="${report==1}">
						<ul class="nav nav-sidebar">
							<label class="control-label">Desde</label>
							<input type="date" class="form-control" name="fechaDesde" value="${fechaDesde}" id="fechaDesde">
						</ul>
						<ul class="nav nav-sidebar">
							<label class="control-label">Hasta</label>
							<input type="date" class="form-control" name="fechaHasta" value="${fechaHasta}" id="fechaHasta">
						</ul>
						<ul class="nav nav-sidebar">
							<li><button id="lk_actualizar" class="btn btn-primary">Refrescar</button></li>
						</ul>
						<ul class="nav nav-sidebar">
							<li><button id="export" class="btn btn-primary">Exportar a Excel</button></li>
						</ul>
					</c:if>
					</ul>
				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<c:if test="${report==0}">
						<h2 class="sub-header">Reportes</h2>
						<div class="table-responsive">
							<div id="idsValesSel"></div>
							<div id="allIdsdiv"></div>

							<div id="dialogo_resultados" title="Resultado" style="display: none;"></div>
							<ul class="list-group">
								<li class="list-group-item"><a href="lstReport.do?report=1">Convocatorias por persona</a></li>
							</ul>
						</div>
					</c:if>
					<c:if test="${report==1}">
						<div style="margin-left:20px">
							<ul class="list-group">CONVOCARORIAS POR PERSONA</ul>
						    <table id="jqGrid"></table>
						    <div id="jqGridPager"></div>
						</div>
					</c:if>
				</div>
			</div>
		</div>

	</html:form>


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
	<script src="js/holder.js"></script>
		
    
</body>
</html>