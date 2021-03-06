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
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="icon" href="images/icon.png">
	
	<title>G.C.P.</title>
	
	
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	      <script src="js/html5shiv.min.js"></script>
	      <script src="js/respond.min.js"></script>
	    <![endif]-->
	
	<script type="text/javascript">
	
	
	$('#select-all').click(function(event) {   
	    if(this.checked) {
	        // Iterate each checkbox
	        $(':checkbox').each(function() {
	            this.checked = true;                        
	        });
	    } else {
	        $(':checkbox').each(function() {
	            this.checked = false;                       
	        });
	    }
	});
	
	</script>
	
	<style>
	 
	.row {
	  margin-right: -10px;
	}
	.container {
	 	width: 100%; 
	} 
	
	.bs-example{
    	margin: 20px;
    }
    
    .form-control{
    	height: 26px;
    }
    
    .input-sm{
    	height: 26px;
	}    

	</style>
</head>

<body>
	<div align="center" class="container" >

		<html:form action="/modifyCategoryDispatch.do" method="post"
			styleId="frmAdmDispatch" style="margin: 0px 0px 0px 0px;">
			<html:hidden property="accion" styleId="accion" />
			<html:hidden property="nroDestino" />
			<bean:define id="stPrefix" name="frmAdmDispatch" property="prefix" />
			<bean:define id="stCode" name="frmAdmDispatch" property="code" />
			<bean:define id="objDispatch" name="frmAdmDispatch"	property="dispatch" />
			<bean:define id="stEventHour" name="frmAdmDispatch"	property="eventHour" />
			<bean:define id="stEventEndHour" name="frmAdmDispatch"	property="eventEndHour" />
			<bean:define id="stEventDate" name="frmAdmDispatch"	property="eventDate" />
			<bean:define id="stEventDateAlt2" name="frmAdmDispatch"	property="eventDateAlt2" />
			<logic:notEmpty name="frmAdmDispatch" property="attribute3"  > 
				<bean:define id="stAttribute3" name="frmAdmDispatch"	property="attribute3" />
			</logic:notEmpty>	

			<input type="hidden" value="${stPrefix}" name="prefix" id="prefix" />
			<input type="hidden" value="${stCode}" name="code" id="code" />
			
		
			<div class="row" id="assignmentRowModel" style="display: none">
				<div class="col-md-4">
					<select class="form-control input-sm" name="personMovil_" id="personMovil_">
						<option value="">Seleccione</option>
						<logic:iterate name="frmAdmDispatch" property="personsMovil"
							id="item" indexId="idx">
							<option value="${item.id}">${item.person.name}</option>
						</logic:iterate>
					</select>
				</div>
				<div class="col-md-3">
					<select class="form-control input-sm" name="personCategory_"
						id="personCategory_">
						<option value="">Seleccione</option>
						<logic:iterate name="frmAdmDispatch" property="categories"
							id="item" indexId="idx">
							<option value="${item.id}">${item.name}</option>
						</logic:iterate>
					</select>
				</div>
				<div class="col-md-3">
					<select class="form-control input-sm" name="assignmentStatus_"
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

			<div class="form-group" id="dialogo_ingresar_sms" title="Modificar Convocatoria">
				<div class="col-md-12">&nbsp;</div>

				<div class="row">
					<div class="col-md-2">
						<label class="control-label input-sm">Convocatoria</label>
					</div>
					<div class="col-md-2">
						<input type="text" class="form-control input-sm" value="${stCode}" disabled="disabled"> 
					</div>
					<div class="col-md-8">
						<input type="text" class="form-control input-sm" value="${objDispatch.name}" disabled="disabled"> 
						<input type="hidden" name="dispatchId" id="dispatchId" value="${objDispatch.id}" >
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">&nbsp;</div>
				</div>
				<div class="row">
					<div class="col-md-1">
						<label class="control-label input-sm">Fecha</label>
					</div>
					<div class="col-md-2">
						<input type="text" value="${stEventDateAlt2}" name="eventDate"
							id="eventDate" class="form-control input-sm" disabled="disabled" /> 
						<input type="hidden" value="${stEventDate}" name="eventDate"
							id="eventDate" />
					</div>
					<div class="col-md-2">
						<input type="text" value="${stEventHour}" class="form-control input-sm"
							disabled="disabled" /> 
						<input type="hidden" value="${stEventHour}" name="eventHour" id="eventHour" />
					</div>
					<div class="col-md-1">
						<label class="control-label input-sm">Hasta</label>
					</div>
					<div class="col-md-2">
						<input type="text" value="${stEventEndHour}" class="form-control input-sm"
							disabled="disabled" /> 
						<input type="hidden" value="${stEventEndHour}" name="eventEndHour" id="eventEndHour" />
					</div>
					

					<logic:notEmpty name="frmAdmDispatch" property="attribute1"  > 
						<div class="row">	
						</div>
						<div class="row">	
							<div class="col-md-1">
								<label class="control-label input-sm">Paciente</label>
							</div>
							<div class="col-md-5">
								<input type="text" class="form-control input-sm" name="attribute1"  value="" id="attribute1">
							</div>
							<div class="col-md-1">
								<label class="control-label input-sm">Sala</label>
							</div>
							<div class="col-md-5">
								<input type="text" class="form-control input-sm" name="attribute2"  value="" id="attribute2">
							</div>
						</div>
					</logic:notEmpty>
			
					<div class="col-md-1">
						<label class="control-label input-sm">Estado</label>
					</div>
					<div class="col-md-3">
						<select class="form-control input-sm" name="dispatchStatus" id="dispatchStatus" required>
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
					<logic:notEmpty name="frmAdmDispatch" property="attribute3"  > 
						<div class="row">	
					<div class="col-md-12">&nbsp;</div>
						</div>
						<div class="row">	
							<div class="col-md-2">
								<label class="control-label input-sm">Mensaje</label>
							</div>
							<div class="col-md-10">
								<input type="text" class="form-control input-sm" name="attribute3" disabled="disabled"  value="${stAttribute3}" id="attribute3">
							</div>
						</div>
					</logic:notEmpty>
				<div class="row">
					<div class="col-md-12">&nbsp;</div>
				</div>

				<div class="row">
					<div class="col-md-1">
						<label class="control-label input-sm">Lugar</label>
					</div>
					<div class="col-md-11">
						<input type="text" value="${objDispatch.place.name}"
							class="form-control input-sm" disabled="disabled" /> <input type="hidden"
							value="${objDispatch.place.id}" name="place" id="place" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-2">
					<h6><i class="icon-plus-sign-alt"></i>Destinatarios</h6>
					</div>
					<div class="col-md-5">
					</div>
					<div class="col-md-2">
					</div>
					<div class="col-md-2" style="text-align: right"><label class="control-label input-sm">Enviar&nbsp;&nbsp;</label>
					</div>
					<div class="col-md-1" style="text-align: right"><input type="checkbox" name="select-all" id="select-all" /></div>
				</div>

				<div class="row" id="assignmentRowContainer">
					<c:set var="counter" value="${0}" />
					<logic:present name="frmAdmDispatch" property="colDispatchStatus">
						<bean:define id="listDispatchStatus" name="frmAdmDispatch" property="colDispatchStatus" />
						<logic:iterate id="dispatchStatus" name="listDispatchStatus" indexId="index">
						</logic:iterate>
					</logic:present>
						
					<logic:present name="frmAdmDispatch" property="colAssignment">
						<bean:define id="listAssignment" name="frmAdmDispatch"	property="colAssignment" />
						<logic:iterate id="assignment" name="listAssignment" indexId="index">
						<c:choose>
					    <c:when test="${assignment.status.id == 2}">
					      <c:set var="bgcolor" value="${'#A9BCF5'}" />
					    </c:when>
					    <c:when test="${assignment.status.id ==5}">
					        <c:set var="bgcolor" value="${'#CEF6E3'}" />
					    </c:when>
					    <c:when test="${assignment.status.id ==6}">
					        <c:set var="bgcolor" value="${'#FA5858'}" />
					    </c:when>
					    <c:otherwise>
					       <c:set var="bgcolor" value="${'white'}" />
					    </c:otherwise>
					</c:choose>
							<c:set var="counter" value="${counter+1}" />
							<div class="row" style="background-color: ${bgcolor}" id="assignmentRow${counter}">
								<div class="col-md-4">
									<label class="control-label input-sm">${assignment.personMovil.person.name}</label>
									<input type="hidden" value="${assignment.id}"
										id="assignment_${counter}"
										name="assignment_${counter}" /> <input
										type="hidden" value="${assignment.status.id}"
										name="assignmentStatus" id="assignmentStatus" />
								</div>
								<div class="col-md-3">
									<select class="form-control input-sm"
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
								<div class="col-md-3">
									<select class="form-control input-sm"
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
									</div>
									<div class="checkbox col-md-1">
										<c:if test="${assignment.status.id==1}">
											<input type="checkbox" 
												name="forward_${counter}"
												id="forward_${counter}"  checked="checked" />
										</c:if>
										<c:if test="${assignment.status.id!=1}">
											<input type="checkbox" 
												name="forward_${counter}"
												id="forward_${counter}"  />
										</c:if>
									</div>
								</div>
							</logic:iterate>
						</logic:present>
				</div>
				<div class="row">
					<div class="col-md-12">&nbsp;</div>
				</div>
				<div class="row">
					<div class="col-md-9">
						<button class="btn btn-lg btn-primary btn-block" id="btnEnviar"
							type="submit" style="display: none;">Enviar</button>
					</div>
					<div class="col-md-3">
						<button class="btn btn-sm btn-primary btn-block"
							id="btnAddaAsignment" type="button"
							onclick="addAssignmentRow(this, ${counter});return false;" style="display: none;">Agregar
							Destinatario</button>
					</div>
				</div>

			</div>

		</html:form>
	</div>
</body>
</html>