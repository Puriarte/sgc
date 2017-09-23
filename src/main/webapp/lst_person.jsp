<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt-rt.tld" prefix="fmt-rt"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//ES" "http://www.w3.org/TR/xhtml2/DTD/xhtml1-strict.dtd">
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

<script src="js/ajax/jquery.validate.min.js" type="text/javascript"></script>
<script src="js/ajax/jquery.meio.mask.min.js" type="text/javascript"></script>

<script src="js/ajax/jquery.ajaxfileupload.js" type="text/javascript"></script>

<script
	src="js/personGrid.js?var=<%=com.jcabi.manifests.Manifests.read("App-Version")%>"
	type="text/javascript"></script>
</head>


<body>

	<jsp:include page="menu.jsp" />
	<html:form action="/lstPerson.do" method="post" styleId="frmLstPerson"
		style="margin: 0px 0px 0px 0px;">
		<bean:define id="stFechaInicio" name="frmLstPerson"
			property="fechaDesde" />
		<bean:define id="stFechaFin" name="frmLstPerson" property="fechaHasta" />

		<html:hidden property="accion" styleId="accion" />
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
					<ul class="nav nav-sidebar">
						<li class="active">
							<div class="row">
								<div class="col-md-8"><label class="control-label">Categor&iacute;a</label></div>
								<div class="col-md-4"><a href="#" onclick="desmarcarSelect('categories')">Borrar</a></div>
							</div>
						</li>
						<li><select class="form-control" name="category" multiple="multiple"
							id="category" required>
								<logic:iterate name="frmLstPerson" property="categories"
									id="item" indexId="idx">
									<option value="${item.id}">${item.name}</option>
								</logic:iterate>
						</select></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li class="active">
							<div class="row">
								<div class="col-md-8"><label class="control-label">Orden&nbsp;Prelaci&oacute;n</label></div>
								<div class="col-md-4"><a href="#" onclick="desmarcarSelect('priority')">Borrar</a></div>
							</div>
						</li>
						<li><select class="form-control" name="priority"
							id="priority" multiple="multiple" style="height: 80px">
								<option value="0">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
						</select></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_actualizar" class="btn btn-primary">Refrescar</button></li>
					</ul>

				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h2 class="sub-header">Consulta de Empleados</h2>

					<div class="table-responsive">
						<div id="idsValesSel"></div>
						<div id="allIdsdiv"></div>

						<div id="dialogo_resultados" title="Resultado"
							style="display: none;"></div>

						<div class="form-group" id="dialogo_ingresar_sms"
							title="Enviar SMS" style="display: none;">
							<div class="row">
								<div class="col-md-12">&nbsp;</div>
							</div>

							<div class="row">
								<div class="col-md-2">
									<label class="control-label">Prefijo</label>
								</div>
								<div class="col-md-2">
									<input type="text" class="form-control" name="prefix" value=""
										id="prefix">
								</div>
								<div class="col-md-2">
									<label class="control-label">Codigo</label>
								</div>
								<div class="col-md-2">
									<input type="text" class="form-control" name="code" value=""
										id="code">
								</div>
								<div class="col-md-1">
									<label class="control-label">Fecha</label>
								</div>
								<div class="col-md-3">
									<input type="date" class="form-control" name="eventDate"
										value="" id="eventDate">
								</div>
								<div class="col-md-1">
									<label class="control-label">Hora</label>
								</div>
								<div class="col-md-3">
									<input type="time" class="form-control" name="eventHour"
										value="" id="eventHour">
								</div>

							</div>
							<div class="row">
								<div class="col-md-12">&nbsp;</div>
							</div>


							<div class="row">
								<div class="col-md-2">
									<label class="control-label">Lugar</label>
								</div>
								<div class="col-md-4">
									<input type="text" class="form-control" name="place" value=""
										id="place">
								</div>


							</div>
							<div class="row">
								<div class="col-md-5">&nbsp;</div>
							</div>
							<div class="row">
								<div class="col-md-2">
									<label class="control-label">Destinatarios</label>
								</div>
								<div class="col-md-3">
									<input type="hidden" name="nroDestino" value="" id="nroDestino">
									<select class="form-control" name="nroDestinoDesc"
										id="nroDestinoDesc" style="width: 400px;" size="10" required>
									</select>
								</div>
							</div>

						</div>
					</div>

					<table align="center" id="gridArticulos"></table>
					<div id="pagerArticulos"></div>

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


