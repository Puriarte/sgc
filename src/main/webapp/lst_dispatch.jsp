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
 <meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
    <link rel="icon" href="images/icon.png">

	<title>G.C.P.</title>

  	<!-- Bootstrap core CSS -->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<!-- Custom styles for this template -->
	<link href="css/dashboard.css?var=<%=com.jcabi.manifests.Manifests.read("App-Version")%>" rel="stylesheet">
	
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

	<script src="js/autocomplete.js?var=<%=com.jcabi.manifests.Manifests.read("App-Version")%>"></script>

	<script
		src="js/dispatchGrid.js?var=<%=com.jcabi.manifests.Manifests.read("App-Version")%>"
		type="text/javascript"></script>

	<style>
		.ui-jqgrid tr.jqgrow td {white-space: normal}
		.nav-sidebar{margin-bottom: 5px;}
		.btn{padding: 3px 6px}
	</style>
	
	</head>
<body>
	<jsp:include page="menu.jsp" />

	<html:form action="/lstDispatch.do" method="post"
		styleId="frmLstDispatch" style="margin: 0px 0px 0px 0px;">
		<bean:define id="stFechaInicio" name="frmLstDispatch"
			property="fechaDesde" />
		<bean:define id="stFechaFin" name="frmLstDispatch"
			property="fechaHasta" />
		<html:hidden property="accion" styleId="accion" />


		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
<!--  
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
-->
					<ul class="nav nav-sidebar">
						<li><label class="control-label">Estado</label></li>
						<li><select class="form-control" name="dispatchStatus"
							id="dispatchStatus">
								<option value="">Todos</option>
								<logic:iterate name="frmLstDispatch"
									property="colDispatchStatus" id="item" indexId="idx">
									<logic:equal name="frmLstDispatch" property="dispatchStatus"
										value="${item.id}">
										<option selected="selected" value="${item.id}">${item.name}</option>
									</logic:equal>
									<logic:notEqual name="frmLstDispatch" property="dispatchStatus"
										value="${item.id}">
										<option value="${item.id}">${item.name}</option>
									</logic:notEqual>
								</logic:iterate>
						</select></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_actualizar" class="btn btn-primary">Refrescar</button></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_edit" class="btn btn-primary">Modificar</button></li>
					</ul>
<!-- 					<ul class="nav nav-sidebar">
						<li><button id="export" class="btn btn-primary">Exportar a Excel</button></li>
					</ul> -->
				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h2 class="sub-header">Gesti&oacute;n de Convocatorias</h2>

					<div class="table-responsive">
						<div id="idsValesSel"></div>
						<div id="allIdsdiv"></div>

						<div id="dialogo_resultados" title="Resultado" style="display: none;"></div>

						<div id="dialogo_ingresar_dispatch" title="Modificar Convocatoria"
							style="display: none;">
							<table width="100%" cellpadding="0" border="0" cellspacing="2">
								<tr>
									<td width="30%" style="padding-left: 10px; font-weight: bold;"
										align="left" valign="top" bgcolor="#E2F1F1">Destinatarios
										del mensaje</td>
									<td width="70%" style="padding-left: 10px; font-weight: bold;"
										align="left" valign="top" bgcolor="#FFFFFF"><input
										type="hidden" name="nroDestino" size="40" value=""
										id="nroDestino"> <textarea id="nroDestinoDesc"
											name="nroDestinoDesc" rows="3" cols="60"></textarea></td>
								</tr>
								<tr>
									<td width="30%" style="padding-left: 10px; font-weight: bold;"
										align="left" valign="top" bgcolor="#E2F1F1">Nombre</td>
									<td width="70%" style="padding-left: 10px; font-weight: bold;"
										align="left" valign="top" bgcolor="#FFFFFF"><input
										name="name" id="name" width="190" /></td>
								</tr>
								<tr>
									<td width="30%" style="padding-left: 10px; font-weight: bold;"
										align="left" valign="top" bgcolor="#E2F1F1">Mensaje</td>
									<td width="70%" style="padding-left: 10px; font-weight: bold;"
										align="left" valign="top" bgcolor="#FFFFFF"><textarea
											name="detalleIn" cols="60" rows="4" id="detalleIn"></textarea>
									</td>
								</tr>
							</table>
						</div>

						<table align="center" id="gridArticulos"></table>
						<div id="pagerArticulos"></div>
					</div>
				</div>
			</div>
		</div>
				
	</html:form>
</body>
</html>