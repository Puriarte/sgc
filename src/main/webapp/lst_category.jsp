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
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="icon" href="images/icon.png">
	
	<title>G.C.P.</title>
	
	<!-- Bootstrap core CSS -->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Custom styles for this template -->
	<link href="css/navbar.css" rel="stylesheet">
	
	<link href="css/navbar-fixed-top.css" rel="stylesheet">
	
	<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
	<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
	<script src="js/ie-emulation-modes-warning.js"></script>
	
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	      <script src="js/html5shiv.min.js"></script>
	      <script src="js/respond.min.js"></script>
	    <![endif]-->
	
	<link href="css/jquery/jquery-ui-1.8.13.custom.css" rel="stylesheet"
		type="text/css" />
	
	<link href="css/jquery/ui.jqgrid.css" rel="stylesheet" type="text/css" />
	<link href="css/jquery/jquery.autocomplete.css" rel="stylesheet"
		type="text/css" />
	
	<script src="js/ajax/jquery-1.6.1.min.js" type="text/javascript"></script>
	<script src="js/ajax/i18n/grid.locale-es.js" type="text/javascript"></script>
	<script src="js/ajax/i18n/jquery.validate-es.js" type="text/javascript"></script>
	<script src="js/ajax/i18n/jquery.ui.datepicker-es.js"	type="text/javascript"></script>
	
	<script src="js/ajax/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="js/ajax/jquery-ui-1.8.13.custom.min.js"	type="text/javascript"></script>

	<script src="js/ajax/jquery.validate.min.js" type="text/javascript"></script>
	<script src="js/ajax/jquery.meio.mask.min.js" type="text/javascript"></script>
	<script src="js/categoryGrid.js?var=<%= com.jcabi.manifests.Manifests.read("App-Version") %>" type="text/javascript"></script>

</head>

<body>
	<jsp:include page="menu.jsp" />

	<div align="center" class="ui-widget">

		<html:form action="/lstCategory.do" method="post"
			styleId="frmLstCategory" style="margin: 0px 0px 0px 0px;">

			<html:hidden property="accion" styleId="accion" />
			<div id="idsValesSel"></div>
			<div id="allIdsdiv"></div>

			<div id="dialogo_resultados" title="Resultado" style="display: none;">
			</div>

			<div align="center">
				<fieldset>
					<div class="form-group">
						<div class="row">
							<div class="col-md-11"></div>
							<div class="col-md-1">
								<button id="lk_actualizar" class="btn btn-primary">Refrescar</button>
							</div>
						</div>
					</div>
				</fieldset>

				<table border="0" width="100%" cellpadding="3" cellspacing="0">
					<!-- GRILLA -->
					<tr class="ui-widget-content">
						<td width="100%" align="center">
							<table align="center" id="gridArticulos"></table>
							<div id="pagerArticulos"></div>
						</td>
					</tr>
				</table>
			</div>

			<!-- ERRORES -->
			<logic:messagesPresent property="error">
				<div class="ui-state-error ui-corner-all" align="left"
					style="padding: 10px;">
					<span class="ui-icon ui-icon-alert" style="float: left;"></span>
					<ol>
						<html:messages property="error" id="errMsg">
							<li><bean:write name="errMsg" /></li>
						</html:messages>
					</ol>
				</div>
			</logic:messagesPresent>
		</html:form>
	</div>
</body>
</html>


