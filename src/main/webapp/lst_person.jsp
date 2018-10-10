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
	<meta name="description" content="">
	<meta name="author" content="">
    <link rel="icon" href="images/icon.png">
	<title>G.C.P.</title>
	
		<!-- Bootstrap core CSS -->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<!-- Custom styles for this template -->
	<link href="css/dashboard.css" rel="stylesheet">
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  	
  	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.0/themes/start/jquery-ui.css">


    <!-- This is the Javascript file of jqGrid -->
    <script type="text/ecmascript" src="js/jquery.jqGrid.min.js"></script>
    <!-- This is the localization file of the grid controlling messages, labels, etc.-->
    <!-- We support more than 40 localizations -->
    <script type="text/ecmascript" src="js/grid.locale-es.js"></script>
 
    <!-- The link to the CSS that the grid needs -->
    <link rel="stylesheet" type="text/css"  href="css/ui.jqgrid.css" />
	
	<script src="js/dist/rx.lite.compat.js"></script>
	<script src="js/bootstrap-notify-master/bootstrap-notify.min.js"></script>

	<script src="js/autocomplete.js?a=<%= (int) (Math.random() * 100) %>"></script>

	<script
		src="js/personGrid.js?var=<%=com.jcabi.manifests.Manifests.read("App-Version")%><%= (int) (Math.random() * 100) %>"
		type="text/javascript"></script>

	<script src="js/ajax/jquery.ajaxfileupload.js" type="text/javascript"></script>
	
	<style>
	.ui-jqgrid tr.jqgrow td {
		white-space: normal
	}
	.nav-sidebar{
		margin-bottom: 5px;
	}
	.btn{
		padding: 3px 6px
	}
	</style>
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
								<div class="col-md-4"><a href="#" onclick="desmarcarSelect('category')">Borrar</a></div>
							</div>
						</li>
						<li><select class="form-control" name="category" multiple="multiple" id="category" required>
								<logic:iterate name="frmLstPerson" property="categories"
									id="item" indexId="idx">
									<option value="${item.id}">${item.name}</option>
								</logic:iterate>
						</select></li>
					</ul>
<!-- 					<ul class="nav nav-sidebar">
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
 -->
					<ul class="nav nav-sidebar">
						<li><button id="lk_actualizar" class="btn btn-primary">Refrescar</button></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_add" class="btn btn-primary">Agregar</button></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_edit" class="btn btn-primary">Modificar</button></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_message" class="btn btn-primary">Crear Mensaje</button></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_dispatch" class="btn btn-primary">Nueva Convocatoria</button></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_addDispatch" class="btn btn-primary">Agregar a Convocatoria</button></li>
					</ul>
<!-- 					<ul class="nav nav-sidebar">
						<li><button id="lk_report" class="btn btn-primary">Informes</button></li>
					</ul>
 -->
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

</body>
</html>


