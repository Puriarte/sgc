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
	
	<style>
	 
	 .body{
	 	padding-top:10px;
	  	width:600px;
   		margin:0 auto;
   		background-color: #f5faff ;
	}
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

 <body style="padding-top:10px;background-color:#f5faff;">
	<html:form action="/addToCategoryDispatch.do" method="post" styleId="frmAdmDispatch">
	<html:hidden  property="accion" styleId="accion"/>
	<bean:define id="stPrefix" name="frmAdmDispatch" property="prefix" />
	<bean:define id="stCode" name="frmAdmDispatch" property="code" />

	<bean:define id="objDispatch" name="frmAdmDispatch"	property="dispatch" />
	<bean:define id="stEventHour" name="frmAdmDispatch"	property="eventHour" />
	<bean:define id="stEventEndHour" name="frmAdmDispatch"	property="eventEndHour" />
	<bean:define id="stEventDate" name="frmAdmDispatch"	property="eventDate" />
	<bean:define id="stEventDateAlt2" name="frmAdmDispatch"	property="eventDateAlt2" />
	<bean:define id="stEventDateAlt2" name="frmAdmDispatch"	property="eventDateAlt2" />
	<bean:define id="stNroDestino" name="frmAdmDispatch"	property="nroDestino" />

	<html:hidden property="nroDestino" value="${stNroDestino}" />
	<div class="row" id="categoryRowModel">
		<div class="table-responsive"> 
			<logic:empty name="frmAdmDispatch" property="dispatchId">
				<table class="table-condensed" id="personTable">
	            <tbody>
	                <tr>
		                <td><label class="control-label input-sm">Convocatoria</label></td>
		                <td>
		                	<select class="form-control input-sm" name="dispatchId" id="dispatchId" required>
				          	<option value="">Seleccione</option>
							<logic:present name="frmAdmDispatch" property="dispatches">
								<logic:iterate name="frmAdmDispatch" property="dispatches" id="item" indexId="idx">
						        	<option value="${item.id}">${item.code} - ${item.name}</option>
								</logic:iterate>
								</select>
							</logic:present>
						</td>
						<td>
							<button class="btn btn-sm btn-primary btn-block" id="btnEnviar" type="submit" >Continuar</button>
						</td>
	                </tr>
	             </tbody>
	             </table>
			</logic:empty>
			<logic:notEmpty name="frmAdmDispatch" property="dispatchId">
				<html:hidden property="dispatchId" />			
				<button class="btn btn-sm btn-primary btn-block" id="btnEnviar" type="submit"  style="display:none"></button>
				<table class="table-condensed" id="personTable" width="750px">
	            <tbody>
	                <tr>
	                	<td width="120px">
							<label class="control-label input-sm">Convocatoria</label>
						</td>
	                	<td width="80px">
							<input type="text" class="form-control input-sm" value="${stCode}" disabled="disabled"> 
						</td>
	                	<td >
							<input type="text" class="form-control input-sm" value="${objDispatch.name}" disabled="disabled"> 
						</td>
					</tr>
				</tbody>
				</table>
				<table class="table-condensed" id="personTable" width="750px">
	            <tbody>
	                <tr>
	                	<td width="120px">
							<label class="control-label input-sm">Fecha</label>
						</td>
	                	<td width="120px">
							<input type="text" value="${stEventDateAlt2}" name="eventDate"
								id="eventDate" class="form-control input-sm" disabled="disabled" /> 
						</td>
	                	<td width="120px">
							<input type="text" value="${stEventHour}" class="form-control input-sm"
							disabled="disabled" /> 
						</td>
	                	<td width="80px">
							<label class="control-label input-sm">Hasta</label>
						</td>
	                	<td width="120px">
							<input type="text" value="${stEventEndHour}" class="form-control input-sm" disabled="disabled" /> 
						</td>
	                	<td width="20px" align="right">
							<input type="checkbox" class="form-control input-sm" name="enviarSMS"  value="" id="enviarSMS" >
						</td>
	                	<td width="180px">ENVIAR AHORA
						</td>
					</tr>
				</tbody>
				</table>

				<table class="table-condensed" id="personTable" align="center" width="750px">
	            <tbody>
	                <tr>
	    	            <td colspan="2" align="center"><h5><b>Destinatarios</b></small></h5></td>
    	            </tr>
							<logic:present name="frmAdmDispatch" property="colPerson">
								<bean:define id="listaPerson" name="frmAdmDispatch" property="colPerson"/>
								<logic:iterate id="person" name="listaPerson" indexId="index">
								<tr>
									<td>
										<input type="hidden"  name="personId_${person.id}" id="personId_${person.id}" value="${person.id}" />
										<label class="control-label input-sm">${person.person.name}</label>
									</td>
									<td>
										<select class="form-control input-sm" name="personCategory_${person.id}" id="personCategory_${person.id}" required>
					                    	<option value="">Seleccione</option>
											<logic:iterate name="frmAdmDispatch" property="categories" id="item" indexId="idx">
											<c:if test="${person.person.preferedCategory.id==item.id}">
					                    		<option value="${item.id}"  selected="selected" >${item.name}</option>
											</c:if>
											<c:if test="${person.person.preferedCategory.id!=item.id}">
					                    		<option value="${item.id}">${item.name}</option>
											</c:if>
											</logic:iterate>
										</select>
									</td>
								</tr>
								</logic:iterate>
							</logic:present>
				</tbody>
				</table>
			</logic:notEmpty>

         </div>
    </div>

     

</html:form>
</body>
</html>