<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt-rt.tld" prefix="fmt-rt" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//ES" "http://www.w3.org/TR/xhtml2/DTD/xhtml1-strict.dtd">
<html lang="es">
<head>
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

    <link href="css/jquery/jquery-ui-1.8.13.custom.css" rel="stylesheet" type="text/css" />

   	<script src="js/ajax/jquery-1.6.1.min.js"  type="text/javascript"></script>
	<script src="js/ajax/jquery.bgiframe.min.js"  type="text/javascript"></script>
	<script src="js/ajax/jquery-ui-1.8.13.custom.min.js" type="text/javascript"></script>

	<script src="js/ajax/jquery.positionBy.js"  type="text/javascript"></script>

	<link href="css/jquery/ui.jqgrid.css" rel="stylesheet" type="text/css" />
	<link href="css/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

	<script src="js/ajax/jquery-1.6.1.min.js"  type="text/javascript"></script>
	<script src="js/ajax/jquery.bgiframe.min.js"  type="text/javascript"></script>

	<script src="js/ajax/i18n/grid.locale-es.js"  type="text/javascript"></script>
	<script src="js/ajax/i18n/jquery.validate-es.js"  type="text/javascript"></script>
	<script src="js/ajax/i18n/jquery.ui.datepicker-es.js"  type="text/javascript"></script>

	<script src="js/ajax/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="js/ajax/jquery-ui-1.8.13.custom.min.js" type="text/javascript"></script>

	<script src="js/ajax/jquery.validate.min.js"  type="text/javascript"></script>
	<script src="js/ajax/jquery.autocomplete.min.js"  type="text/javascript"></script>
	<script src="js/ajax/jquery.meio.mask.min.js"  type="text/javascript"></script>

	<script src="js/ajax/jquery.positionBy.js"  type="text/javascript"></script>
	<script src="js/ajax/jquery.jdMenu.js"  type="text/javascript"></script>

	<script src="js/utils/date-es-UY.js"  type="text/javascript"></script>
	<style>
		.ui-jqgrid tr.jqgrow td {white-space: normal}
	</style>

	<script src="js/implGrid.js?var=<%= com.jcabi.manifests.Manifests.read("App-Version") %>" type="text/javascript"></script>

  </head>

 <body >
	<jsp:include page="menu.jsp" />

	<div align="center" class="ui-widget">

	<html:form action="/lstSMS.do" method="post" styleId="frmLstSMS" style="margin: 0px 0px 0px 0px;">
	<bean:define id="stFechaInicio" name="frmLstSMS" property="fechaDesde"/>
	<bean:define id="stFechaFin" name="frmLstSMS" property="fechaHasta"/>

	<html:hidden property="accion" styleId="accion"/>
	<div id="idsValesSel"></div>
	<div id="allIdsdiv"></div>

	<div id="dialogo_resultados" title="Resultado" style="display: none;">
	</div>


<div id="dialogo_ingresar_sms" title="Enviar SMS" style="display: none;">
	<table width="100%" cellpadding="0" border="0" cellspacing="2">
	<tr>
		<td width="30%"  style="padding-left: 10px;font-weight: bold;" align="left" valign="top" bgcolor="#E2F1F1">
			Numero
		</td>
		<td width="70%"  style="padding-left: 10px;font-weight: bold;" align="left" valign="top" bgcolor="#FFFFFF">
			<input type="text" name="nroDestino" size="40" value="" id="nroDestino">
		</td>
	</tr>
	<tr>
		<td width="30%"  style="padding-left: 10px;font-weight: bold;" align="left" valign="top" bgcolor="#E2F1F1">
			Mensaje
		</td>
		<td width="70%"  style="padding-left: 10px;font-weight: bold;" align="left" valign="top" bgcolor="#FFFFFF">
			<textarea name="detalleIn" cols="60" rows="4" id="detalleIn"></textarea>
		</td>
	</tr>
	</table>

</div>

<div align="center">
	<fieldset>
		<div class="form-group">
			<div class="row">
		        <div class="col-md-1"><label class="control-label">Desde</label></div>
		        <div class="col-md-2"><input id="fechaDesde" name="fechaDesde" type="date" value="${stFechaInicio}" placeholder="Fecha de alta desde" class="form-control"  required/></div>
				<div class="col-md-1">
		               	<label class="control-label">hasta</label>
				</div>
				<div class="col-md-2">
		        	<input id="fechaHasta" name="fechaHasta" type="date" value="${stFechaFin}" placeholder="Fecha de alta hasta" class="form-control"  required/>
				</div>
				<div class="col-md-1">
		               	<label class="control-label">Estado</label>
				</div>
				<div class="col-md-1">
					<select class="form-control" name="estado" id="estado" required>
                    	<option value="0">Todos</option>
                    	<option value="1">Recibido</option>
                    	<option value="4">Pendiente</option>
                    	<option value="5">Enviado</option>
                    	<option value="6">Registro Fallido</option>
                	</select>

				</div>
				<div class="col-md-1">
	               	<label class="control-label">Convocatoria</label>
				</div>

				<div class="col-md-2">
					<select class="form-control" name="convocatoria" id="convocatoria" required>
                    	<option value="">Seleccione</option>
						<logic:iterate name="frmLstSMS" property="convocatorias" id="item" indexId="idx">
                    		<option value="${item.id}">${item.name}</option>
						</logic:iterate>
					</select>
				</div>
				<div class="col-md-1">
					<button id="lk_actualizar"  class="btn btn-primary">Refrescar</button>
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

<!-- BOTONES -->
<tr class="ui-widget-content-btn">
	<td width="100%">
<!--  	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr height="30"  valign="middle">
			<td align="center">
				<input type="button" name="bcAlta" value="Enviar SMS" id="btnIngresar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button">
			</td>
		</tr>
		</table>
-->	</td>
</tr>
</table>

</div>

<!-- ERRORES -->
<logic:messagesPresent property="error">
<div class="ui-state-error ui-corner-all" align="left" style="padding: 10px;">
	<span class="ui-icon ui-icon-alert" style="float:left;"></span>
	<ol>
		<html:messages property="error" id="errMsg" >
			<li><bean:write name="errMsg"/></li>
		</html:messages>
	</ol>
</div>
</logic:messagesPresent>


</html:form>
</div>
</body>
</html>


