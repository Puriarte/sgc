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
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  	<link rel="stylesheet" href="/resources/demos/style.css">
  	
  	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.0/themes/start/jquery-ui.css">


    <!-- This is the Javascript file of jqGrid -->
    <script type="text/ecmascript" src="js/jquery.jqGrid.min.js"></script>
    <!-- This is the localization file of the grid controlling messages, labels, etc.-->
    <!-- We support more than 40 localizations -->
    <script type="text/ecmascript" src="js/grid.locale-es.js"></script>
 
    <!-- The link to the CSS that the grid needs -->
    <link rel="stylesheet" type="text/css"  href="css/ui.jqgrid.css?a=<%= (int) (Math.random() * 100) %>" />
	
 	<script src="js/dist/rx.lite.compat.js"></script>
	<script src="js/bootstrap-notify-master/bootstrap-notify.min.js"></script>

	<script src="js/autocomplete.js?a=<%= (int) (Math.random() * 100) %>"></script>

	<script src="js/placeGrid.js?var=<%= com.jcabi.manifests.Manifests.read("App-Version") %><%= (int) (Math.random() * 100) %>" type="text/javascript"></script>
	
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

	<html:form action="/lstPlace.do" method="post" styleId="frmLstPlace" style="margin: 0px 0px 0px 0px;">
		<html:hidden property="accion" styleId="accion" />
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
					<ul class="nav nav-sidebar">
						<li><label class="control-label">Estado</label></li>
						<li><select class="form-control" name="placeStatus" id="placeStatus">
								<logic:iterate name="frmLstPlace" property="colPlaceStatus" id="item" indexId="idx">
									<logic:equal name="frmLstPlace" property="placeStatus" value="${item.key}">
										<option selected="selected" value="${item.key}">${item.value}</option>
									</logic:equal>
									<logic:notEqual name="frmLstPlace" property="placeStatus" value="${item.key}">
										<option value="${item.key}">${item.value}</option>
									</logic:notEqual>
								</logic:iterate>
						</select></li>
					</ul>
				
					<ul class="nav nav-sidebar">
						<li><button id="lk_actualizar" class="btn btn-primary">Refrescar</button></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_add_place" class="btn btn-primary">Agregar</button></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li><button id="lk_edit_place" class="btn btn-primary">Modificar</button></li>
					</ul>
				</div>

				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h2 class="sub-header">Gestión de Locales</h2>

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

</body>
</html>