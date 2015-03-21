<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt-rt.tld" prefix="fmt-rt" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
	 <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="images/icon.png">

    <title>G.C.P.</title>

    <!-- Le styles -->
    <link href="http://getbootstrap.com/2.3.2/assets/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }

      @media (max-width: 980px) {
        /* Enable use of floated navbar text */
        .navbar-text.pull-right {
          float: none;
          padding-left: 5px;
          padding-right: 5px;
        }
      }
    </style>
    <link href="http://getbootstrap.com/2.3.2/assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.min.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->

    <link href="css/jquery/jquery-ui-1.8.13.custom.css" rel="stylesheet" type="text/css" />
	<link href="css/jquery/ui.jqgrid.css" rel="stylesheet" type="text/css" />

   	<script src="js/ajax/jquery-1.6.1.min.js"  type="text/javascript"></script>
	<script src="js/ajax/jquery-ui-1.8.13.custom.min.js" type="text/javascript"></script>
	<script src="js/ajax/i18n/grid.locale-es.js"  type="text/javascript"></script>
	<script src="js/ajax/jquery.jqGrid.min.js" type="text/javascript"></script>

	<script src="js/utils/date-es-UY.js"  type="text/javascript"></script>
	<style>
		.ui-jqgrid tr.jqgrow td {white-space: normal}
	</style>

	<script src="js/implGrid.js?var=<%= com.jcabi.manifests.Manifests.read("App-Version") %>" type="text/javascript"></script>

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="http://getbootstrap.com/2.3.2/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="http://getbootstrap.com/2.3.2/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="http://getbootstrap.com/2.3.2/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="http://getbootstrap.com/2.3.2/assets/ico/apple-touch-icon-57-precomposed.png">
	<link rel="shortcut icon" href="http://getbootstrap.com/2.3.2/assets/ico/favicon.png">
  </head>

  <body cz-shortcut-listen="true">
	<jsp:include page="menu.jsp" />
	
	
	<html:form action="/lstSMS.do" method="post" styleId="frmLstSMS" style="margin: 0px 0px 0px 0px;">
	<bean:define id="stFechaInicio" name="frmLstSMS" property="fechaDesde"/>
	<bean:define id="stFechaFin" name="frmLstSMS" property="fechaHasta"/>

	<html:hidden property="accion" styleId="accion"/>

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">
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
	
			</li>
            </ul>
          </div><!--/.well -->
        </div><!--/span-->
        <div class="span9">
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
	
<table border="0" width="100%" cellpadding="3" cellspacing="0">


<!-- GRILLA -->
<tr class="ui-widget-content">
	<td width="100%" align="center">
---
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
			
        </div><!--/span-->
      </div><!--/row-->

      <hr>

      <footer>
        <p>© Company 2013</p>
      </footer>

    </div><!--/.fluid-container-->


		
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

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="./Bootstrap, from Twitter_files/jquery.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-transition.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-alert.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-modal.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-dropdown.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-scrollspy.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-tab.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-tooltip.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-popover.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-button.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-collapse.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-carousel.js"></script>
    <script src="./Bootstrap, from Twitter_files/bootstrap-typeahead.js"></script>

  

</body></html>