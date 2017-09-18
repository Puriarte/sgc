<%@ page contentType="text/html; charset=UTF-8"%>
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
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="images/icon.png">

    <title>G.C.P.</title>

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

	<html:form action="/createDispatch.do" method="post" styleId="frmAdmDispatch" style="margin: 0px 0px 0px 0px;">
	<html:hidden  property="accion" styleId="accion"/>
	<html:hidden property="nroDestino" />
	<bean:define id="stPrefix" name="frmAdmDispatch" property="prefix"/>
	<bean:define id="stCode" name="frmAdmDispatch" property="code"/>
	<input type="hidden" class="form-control" name="name"  id="name"></textarea>

	<div class="control-group"  id="dialogo_ingresar_sms" title="Enviar SMS">
	<div class="row">
			<div class="col-md-12">&nbsp;</div>
		</div>
<!-- 		<div class="row">
			<div class="col-md-2">
				<label class="control-label">Nombre</label>
			</div>
			<div class="col-md-10">
				<input type="hidden" class="form-control" name="name"  id="name" required rows="3"></textarea>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">&nbsp;</div>
		</div>
 -->
		<div class="row">
			<div class="col-md-2">
				<label class="control-label">Mensaje</label>
			</div>
			<div class="col-md-10">
				<textarea  class="form-control" name="detalleIn"  value="" id="detalleIn" required rows="3"></textarea>
			</div>
		</div>
		<div class="row">
			<div class="col-md-5">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col-md-2">
				<label class="control-label">Destinatarios</label>
			</div>
			<div class="col-md-10">
				<logic:present name="frmAdmDispatch" property="colPerson">
					<bean:define id="listaPerson" name="frmAdmDispatch" property="colPerson"/>
					<logic:iterate id="person" name="listaPerson" indexId="index">
	       			<div class="row">
						<div class="col-md-12" align="left">
							<label class="control-label">${person.person.name} (${person.movil.number})</label>
						</div>
					</div>
					</logic:iterate>
				</logic:present>
			</div>
		</div>
		<div></div>
			<div class="col-md-2">
				<button class="btn btn-lg btn-primary btn-block" id="btnEnviar" type="submit" style="display:none;">Enviar</button>
			</div>
	</div>

</html:form>
</div>
</body>
</html>








