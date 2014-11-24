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

	<html:form action="/modifyCategoryDispatch.do" method="post" styleId="frmAdmDispatch" style="margin: 0px 0px 0px 0px;">
	<html:hidden  property="accion" styleId="accion"/>
	<html:hidden property="nroDestino" />
	<bean:define id="stPrefix" name="frmAdmDispatch" property="prefix"/>
	<bean:define id="objDispatch" name="frmAdmDispatch" property="dispatch"/>
	<bean:define id="stEventHour" name="frmAdmDispatch" property="eventHour"/>
	<bean:define id="stEventDate" name="frmAdmDispatch" property="eventDate"/>
	

						<div class="row" id="assignmentRowModel" style="display: none" >
							<div class="col-md-4">
							<select class="form-control" name="personMovil_" id="personMovil_"  required>
		                    	<option value="">Seleccione</option>
								<logic:iterate name="frmAdmDispatch" property="personsMovil" id="item" indexId="idx">
			                    		<option value="${item.id}">${item.person.name}</option>
								</logic:iterate>
							</select>
							</div>
							<div class="col-md-3">
							<select class="form-control" name="personCategory_" id="personCategory_">
					           	<option value="">Seleccione</option>
								<logic:iterate name="frmAdmDispatch" property="categories" id="item" indexId="idx">
			                    		<option value="${item.id}">${item.name}</option>
								</logic:iterate>
							</select>
							</div>
							<div class="col-md-3">
							<select class="form-control" name="assignmentStatus_" id="assignmentStatus_" >
		                    	<option value="">Seleccione</option>
								<logic:iterate name="frmAdmDispatch" property="assignmentsStatus" id="item" indexId="idx">
			                    		<option value="${item.id}">${item.name}</option>
								</logic:iterate>
							</select>
							</div>
					</div>
 
<!-- 
				<bean:write name="frmAdmDispatch" property="dispatch.scheduledDate"/>
<bean:write name="frmAdmDispatch" property="dispatch.name"/>
 -->	<div class="form-group"  id="dialogo_ingresar_sms" title="Enviar SMS">
	<div class="row">
			<div class="col-md-12">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col-md-2">
				<label class="control-label">Convocatoria</label>
			</div>
			<div class="col-md-10">
				<input type="text" class="form-control" value="${objDispatch.name}" disabled="disabled" >
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col-md-2">
				<label class="control-label">Prefijo</label>
			</div>
			<div class="col-md-2">
				<input type="text" class="form-control" name="prefix" value="${prefix}" id="prefix"  disabled="disabled" >
			</div>
			<div class="col-md-1">
				<label class="control-label">Fecha</label>
			</div>
			<div class="col-md-3">
     			<input type="date" value="${stEventDate}"  class="form-control"  disabled="disabled"/>
			</div>
			<div class="col-md-1">
				<label class="control-label">Hora</label>
			</div>
			<div class="col-md-3">
     			<input type="time" value="${stEventHour}"  class="form-control"  disabled="disabled"/>
			</div>

		</div>
		<div class="row">
			<div class="col-md-12">&nbsp;</div>
		</div>


		<div class="row">
			<div class="col-md-2">
				<label class="control-label">Lugar</label>
			</div>
			<div class="col-md-10">
				<input type="text" value="${objDispatch.place.name}"  class="form-control"  disabled="disabled"/>
			</div>
		</div>
		<div class="row">	
			<div class="col-md-5">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col-md-2">
				<label class="control-label">Destinatarios  </label>
			</div>
			<div class="col-md-10" id="assignmentRowContainer">
				<c:set var="counter" value="${0}"/>
				<logic:present name="frmAdmDispatch" property="colAssignment">
					<bean:define id="listAssignment" name="frmAdmDispatch" property="colAssignment"/>
					<logic:iterate id="assignment" name="listAssignment" indexId="index">
					<c:set var="counter" value="${counter+1}"/>
	       			<div class="row" id="assignmentRow${counter}">
						<div class="col-md-4">
							<label class="control-label">${assignment.personMovil.person.name}</label>
							<label class="control-label">${assignment.job.category.id}</label>
						</div>
						<div class="col-md-3">
							<select class="form-control" name="personCategory_${assignment.personMovil.person.id}" id="personCategory_${assignment.personMovil.person.id}"  required>
		                    	<option value="">Seleccione</option>
								<logic:iterate name="frmAdmDispatch" property="categories" id="item" indexId="idx">
									<c:if test="${item.id==assignment.job.category.id}">
			                    		<option selected="selected" value="${item.id}">${item.name}</option>
									</c:if>
									<c:if test="${item.id!=assignment.job.category.id}">
			                    		<option value="${item.id}">${item.name}</option>
									</c:if>
								</logic:iterate>
							</select>
						</div>
						<div class="col-md-3">
							<select class="form-control" name="assignmentStatus_${assignment.personMovil.person.id}" id="assignmentStatus_${assignment.personMovil.person.id}"  required>
		                    	<option value="">Seleccione</option>
								<logic:iterate name="frmAdmDispatch" property="assignmentsStatus" id="item" indexId="idx">
									<c:if test="${item.id==assignment.status.id}">
			                    		<option selected="selected" value="${item.id}">${item.name}</option>
									</c:if>
									<c:if test="${item.id!=assignment.status.id}">
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
		<div class="row">	
			<div class="col-md-5">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col-md-6">
			<div class="col-md-2">
				<button class="btn btn-lg btn-primary btn-block" id="btnEnviar" type="submit" onclick="{this.form.action='mostrarEscolaridadPDF';}" style="display:none;" >Enviar 1</button>
			</div>
			</div>
			<div class="col-md-3">
				<button class="btn btn-lg btn-primary btn-block" id="btnAddaAsignment" type="button"  onclick="addAssignmentRow(this, ${counter});return false;" >Agregar Destinatario</button>
			</div>
		</div>
		<div></div>
	</div>

</html:form>
</div>
</body>
</html>