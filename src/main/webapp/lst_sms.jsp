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
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="icon" href="http://getbootstrap.com/favicon.ico">
	<title>G.C.P.</title>
	
	<!-- Bootstrap core CSS -->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Custom styles for this template -->
	<link href="css/dashboard.css" rel="stylesheet">
	
	<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
	<!--[if lt IE 9]><script src="http://getbootstrap.com/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
	<script src="js/ie-emulation-modes-warning.js"></script>
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	
	<link href="css/jquery/jquery-ui-1.8.13.custom.css" rel="stylesheet"
		type="text/css" />
	<link href="css/jquery/ui.jqgrid.css" rel="stylesheet" type="text/css" />
	
	<script src="js/ajax/jquery-1.6.1.min.js" type="text/javascript"></script>
	<script src="js/ajax/jquery-ui-1.8.13.custom.min.js"
		type="text/javascript"></script>
	<script src="js/ajax/i18n/grid.locale-es.js" type="text/javascript"></script>
	<script src="js/ajax/jquery.jqGrid.min.js" type="text/javascript"></script>
	
	<script src="js/utils/date-es-UY.js" type="text/javascript"></script>
	<style>
	.ui-jqgrid tr.jqgrow td {
		white-space: normal
	}
	</style>
	
	<script
		src="js/implGrid.js?var=<%=com.jcabi.manifests.Manifests.read("App-Version")%>"
		type="text/javascript"></script>

</head>

<body>
	<jsp:include page="menu.jsp" />

	<html:form action="/lstSMS.do" method="post" styleId="frmLstSMS"
		style="margin: 0px 0px 0px 0px;">
		<bean:define id="stFechaInicio" name="frmLstSMS" property="fechaDesde" />
		<bean:define id="stFechaFin" name="frmLstSMS" property="fechaHasta" />

		<html:hidden property="accion" styleId="accion" />
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
					<ul class="nav nav-sidebar">
						<li class="active"><label class="control-label">Desde</label></li>
						<li><input id="fechaDesde" name="fechaDesde" type="date"
							value="${stFechaInicio}" placeholder="Fecha de alta desde"
							class="form-control" required /></li>
						<li><label class="control-label">Hasta</label></li>
						<li><input id="fechaHasta" name="fechaHasta" type="date"
							value="${stFechaFin}" placeholder="Fecha de alta hasta"
							class="form-control" required /></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><label class="control-label">Estado</label></li>
						<li><select class="form-control" name="estado" id="estado">
								<option value="0">Todos</option>
								<option value="1">Recibido</option>
								<option value="4">Pendiente</option>
								<option value="5">Enviado</option>
								<option value="6">Registro Fallido</option>
						</select></li>
						<li><label class="control-label">Convocatoria</label></li>
						<li><select class="form-control" name="convocatoria"
							id="convocatoria">
								<option value="">Seleccione</option>
								<logic:iterate name="frmLstSMS" property="convocatorias"
									id="item" indexId="idx">
									<option value="${item.id}">${item.code} - ${item.name}</option>
								</logic:iterate>
						</select></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_actualizar" class="btn btn-primary">Refrescar</button></li>
					</ul>
				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h2 class="sub-header">Consulta de SMS</h2>

						<div class="table-responsive">
							<div id="idsValesSel"></div>
							<div id="allIdsdiv"></div>

							<div id="dialogo_resultados" title="Resultado"
								style="display: none;"></div>
								<table align="center" id="gridArticulos"></table>
								<div id="pagerArticulos"></div>

						</div>
				</div>
			</div>
		</div>
	</html:form>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
	<script src="http://getbootstrap.com/assets/js/vendor/holder.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script
		src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
