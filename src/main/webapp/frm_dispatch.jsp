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

	<!-- cdn for modernizr, if you haven't included it already -->
	<script src="http://cdn.jsdelivr.net/webshim/1.12.4/extras/modernizr-custom.js"></script>
	<!-- polyfiller file to detect and load polyfills -->
	<script src="http://cdn.jsdelivr.net/webshim/1.12.4/polyfiller.js"></script>
	<script>
	  webshims.setOptions('waitReady', false);
	  webshims.setOptions('forms-ext', {types: 'date'});
	  webshims.polyfill('forms forms-ext');
	</script>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.min.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->

	<style>
		.ui-jqgrid tr.jqgrow td {white-space: normal}
	</style>
</head>

 <body >
	<div align="center" class="ui-widget">

	<html:form action="/createCategoryDispatch.do" method="post" styleId="frmAdmDispatch" style="margin: 0px 0px 0px 0px;">
	<html:hidden  property="accion" styleId="accion"/>
	<html:hidden property="nroDestino" />
	<bean:define id="stPrefix" name="frmAdmDispatch" property="prefix"/>

	<div class="form-group"  id="dialogo_ingresar_sms" title="Enviar SMS">
	<div class="row">
			<div class="col-md-12">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col-md-1">
				<label class="control-label">Prefijo</label>
			</div>
			<div class="col-md-3">
				<input type="text" class="form-control" name="prefix" value="${stPrefix}" id="prefix" required >
			</div>
			<div class="col-md-1">
				<label class="control-label">Fecha</label>
			</div>
			<div class="col-md-3">
				<input type="date" class="form-control" name="eventDate"  value="" id="eventDate" required>
			</div>
			<div class="col-md-1">
				<label class="control-label">Hora</label>
			</div>
			<div class="col-md-3">
				<input type="time" class="form-control" name="eventHour"  value="" id="eventHour" required>
			</div>

		</div>
		<div class="row">
			<div class="col-md-12">&nbsp;</div>
		</div>


		<div class="row">
			<div class="col-md-1">
				<label class="control-label">Lugar</label>
			</div>
			<div class="col-md-11">
				<select class="form-control" name="place" id="place" required>
		          	<option value="">Seleccione</option>
				<logic:present name="frmAdmDispatch" property="places">
					<logic:iterate name="frmAdmDispatch" property="places" id="item" indexId="idx">
			        	<option value="${item.id}">${item.name}</option>
					</logic:iterate>
					</select>
				</logic:present>
			</div>
		</div>
		<div class="row">	
			<div class="col-md-5">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col-md-1">
				<label class="control-label">Destinatarios  </label>
			</div>
			<div class="col-md-11">
				<logic:present name="frmAdmDispatch" property="colPerson">
					<bean:define id="listaPerson" name="frmAdmDispatch" property="colPerson"/>
					<logic:iterate id="person" name="listaPerson" indexId="index">
	       			<div class="row">
						<div class="col-md-4">
							<label class="control-label">${person.person.name}</label>
						</div>
						<div class="col-md-6">
							<select class="form-control" name="personCategory_${person.person.id}" id="personCategory_${person.person.id}" required>
		                    	<option value="">Seleccione</option>
								<logic:iterate name="frmAdmDispatch" property="categories" id="item" indexId="idx">
								<c:if test="${person.person.category.id==item.id}">
		                    		<option value="${item.id}"  selected="selected" >${item.name}</option>
								</c:if>
								<c:if test="${person.person.category.id!=item.id}">
		                    		<option value="${item.id}">${item.name}</option>
								</c:if>
								</logic:iterate>
							</select>
						</div>
					</div>
					</logic:iterate>
				</logic:present>
			</div>
		</div>

		<div></div>
			<div class="col-md-2">
				<button class="btn btn-lg btn-primary btn-block" id="btnEnviar" type="submit" style="display:none;" >Enviar</button>
			</div>
	</div>

</html:form>
</div>
</body>
</html>