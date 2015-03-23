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
.ui-jqgrid tr.jqgrow td {
	white-space: normal
}
</style>
</head>

<body>
	<div align="center" class="ui-widget">

		<html:form action="/modifyCategoryDispatch.do" method="post"
			styleId="frmAdmDispatch" style="margin: 0px 0px 0px 0px;">
			<html:hidden property="accion" styleId="accion" />
			<html:hidden property="nroDestino" />
			<bean:define id="stPrefix" name="frmAdmDispatch" property="prefix" />
			<bean:define id="objDispatch" name="frmAdmDispatch"
				property="dispatch" />
			<bean:define id="stEventHour" name="frmAdmDispatch"
				property="eventHour" />
			<bean:define id="stEventDate" name="frmAdmDispatch"
				property="eventDate" />


			<div class="row" id="assignmentRowModel" style="display: none">
				<div class="col-md-4">
					<select class="form-control" name="personMovil_" id="personMovil_">
						<option value="">Seleccione</option>
						<logic:iterate name="frmAdmDispatch" property="personsMovil"
							id="item" indexId="idx">
							<option value="${item.id}">${item.person.name}</option>
						</logic:iterate>
					</select>
				</div>
				<div class="col-md-3">
					<select class="form-control" name="personCategory_"
						id="personCategory_">
						<option value="">Seleccione</option>
						<logic:iterate name="frmAdmDispatch" property="categories"
							id="item" indexId="idx">
							<option value="${item.id}">${item.name}</option>
						</logic:iterate>
					</select>
				</div>
				<div class="col-md-3">
					<select class="form-control" name="assignmentStatus_"
						id="assignmentStatus_">
						<option value="">Seleccione</option>
						<logic:iterate name="frmAdmDispatch" property="assignmentsStatus"
							id="item" indexId="idx">
							<option value="${item.id}">${item.name}</option>
						</logic:iterate>
					</select>
				</div>
				<input type="hidden" name="assignment_" id="assignment_" />

			</div>

			<div class="form-group" id="dialogo_ingresar_sms" title="Enviar SMS">
				<div class="row">
					<div class="col-md-12">&nbsp;</div>
				</div>
				<div class="row">
					<div class="col-md-2">
						<label class="control-label">Convocatoria</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="form-control"
							value="${objDispatch.name}" disabled="disabled"> <input
							type="hidden" class="form-control" name="dispatchId"
							id="dispatchId" value="${objDispatch.id}">
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">&nbsp;</div>
				</div>
				<div class="row">
					<div class="col-md-2">
						<label class="control-label">Prefijo</label>
					</div>
					<div class="col-md-1">
						<input type="text" class="form-control" value="ET"
							disabled="disabled"> <input type="hidden" value="ET"
							name="prefix" id="prefix" />
					</div>
					<div class="col-md-1">
						<label class="control-label">Fecha</label>
					</div>
					<div class="col-md-2">
						<input type="text" value="${stEventDate}" name="eventDate"
							id="eventDate" class="form-control" disabled="disabled" /> 
						<input type="hidden" value="${stEventDate}" name="eventDate"
							id="eventDate" />
					</div>
					<div class="col-md-1">
						<label class="control-label">Hora</label>
					</div>
					<div class="col-md-2">
						<input type="text" value="${stEventHour}" class="form-control"
							disabled="disabled" /> 
						<input type="hidden" value="${stEventHour}" name="eventHour" id="eventHour" />
					</div>
					<div class="col-md-1">
						<label class="control-label">Estado</label>
					</div>
					<div class="col-md-2">
						<select class="form-control" name="dispatchStatus" id="dispatchStatus" required>
		          			<option value="">Seleccione</option>
							<logic:present name="frmAdmDispatch" property="colDispatchStatus">
								<logic:iterate name="frmAdmDispatch" property="colDispatchStatus" id="item" indexId="idx">
							<c:if test="${objDispatch.dispatchStatus.id == item.id}">
						        	<option value="${item.id}" selected="selected">${item.name}</option>
						
							</c:if>
							<c:if test="${objDispatch.dispatchStatus.id != item.id}">
						        	<option value="${item.id}">${item.name}</option>

							</c:if>
	
								</logic:iterate>
							</logic:present>
						</select>
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
						<input type="text" value="${objDispatch.place.name}"
							class="form-control" disabled="disabled" /> <input type="hidden"
							value="${objDispatch.place.id}" name="place" id="place" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-5">&nbsp;</div>
				</div>
				<div class="row">
					<div class="col-md-2">
						<label class="control-label">Destinatarios </label>
					</div>
					<div class="col-md-10" id="assignmentRowContainer">
						<c:set var="counter" value="${0}" />
						
						<logic:present name="frmAdmDispatch" property="colDispatchStatus">
							<bean:define id="listDispatchStatus" name="frmAdmDispatch" property="colDispatchStatus" />
							<logic:iterate id="dispatchStatus" name="listDispatchStatus" indexId="index">

							</logic:iterate>

						</logic:present>
						
						<logic:present name="frmAdmDispatch" property="colAssignment">
							<bean:define id="listAssignment" name="frmAdmDispatch"	property="colAssignment" />
							<logic:iterate id="assignment" name="listAssignment" indexId="index">
								<c:set var="counter" value="${counter+1}" />
								<div class="row" id="assignmentRow${counter}">
									<div class="col-md-4">
										<label class="control-label">${assignment.personMovil.person.name}</label>
										<input type="hidden" value="${assignment.id}"
											id="assignment_${counter}"
											name="assignment_${counter}" /> <input
											type="hidden" value="${assignment.status.id}"
											name="assignmentStatus" id="assignmentStatus" />
									</div>
									<div class="col-md-3">
										<select class="form-control"
											name="personCategory_${counter}"
											id="personCategory_${counter}">
											<option value="">Seleccione</option>
											<logic:iterate name="frmAdmDispatch" property="categories"
												id="item" indexId="idx">
												<c:if test="${item.id==assignment.job.category.id}">
													<option selected="selected" value="${item.id}">${item.name}</option>
												</c:if>
												<c:if test="${item.id!=assignment.job.category.id}">
													<option value="${item.id}">${item.name}</option>
												</c:if>
											</logic:iterate>
										</select>
									</div>
									<div class="col-md-2">
										<select class="form-control"
											name="assignmentStatus_${counter}"
											id="assignmentStatus_${counter}">
											<option value="">Seleccione</option>
											<logic:iterate name="frmAdmDispatch"
												property="assignmentsStatus" id="item" indexId="idx">
												<c:if test="${item.id==assignment.status.id}">
													<option selected="selected" value="${item.id}">${item.name}</option>
												</c:if>
												<c:if test="${item.id!=assignment.status.id}">
													<option value="${item.id}">${item.name}</option>
												</c:if>
											</logic:iterate>
										</select>
									</div>
									<div class="col-md-1">
										<input type="checkbox" 
											class="form-control"
											name="forward_${counter}"
											id="forward_${counter}" />
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
						<div class="col-md-4">
							<button class="btn btn-lg btn-primary btn-block" id="btnEnviar"
								type="submit" style="display: none;">Enviar</button>
						</div>
					</div>
					<div class="col-md-3">
						<button class="btn btn-lg btn-primary btn-block"
							id="btnAddaAsignment" type="button"
							onclick="addAssignmentRow(this, ${counter});return false;">Agregar
							Destinatario</button>
					</div>
				</div>
				<div></div>
			</div>

		</html:form>
	</div>
</body>
</html>