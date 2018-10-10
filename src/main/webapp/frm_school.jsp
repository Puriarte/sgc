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

		<html:form action="/school.do" method="post" styleId="frmSchool"
			style="margin: 0px 0px 0px 0px;">
			<bean:define id="stFechaInicio" name="frmSchool"
				property="fechaDesde" />
			<bean:define id="stFechaFin" name="frmSchool" property="fechaHasta" />
			<html:hidden property="accion" styleId="accion" />
			<html:hidden property="idPerson" />

			<div align="center" class="ui-widget">
				<div class="form-group">
					<label class="col-lg-3 control-label input-sm">Seleccione
						el rango de fechas a considerar para la esoclaridad</label>
					<div class="col-lg-7">
						<div class="form-inline">
							<div class="form-group ">
								<div class="col-lg-3">
									<label class="sronly" for="fechaDesde">Desde</label> <input
										type="date" id="fechaDesde" name="fechaDesde"
										value="${stFechaInicio}" class="form-control input-sm"
										style="width: 140px" placeholder="" required maxlength="2"
										data-validation-required-message="Fecha requerida">
								</div>
							</div>

							<div class="form-group">
								<div class="col-lg-3">
									<label class="sronly" for="fechaHasta">Hasta</label> <input
										type="date" id="fechaHasta" name="fechaHasta"
										value="${stFechaFin}" class="form-control input-sm"
										style="width: 140px" placeholder="" required maxlength="4"
										data-validation-required-message="Fecha requerida">
								</div>
							</div>
						</div>
						<p class="help-block">
							<br />
							<button id="lk_actualizar" class="btn btn-primary"	onclick="{this.form.action='mostrarEscolaridadPDF';}">Ver Escolaridad</button>
							<button id="lk_actualizar2" class="btn btn-primary"	onclick="{this.form.action='mostrarConvocatoriasPDF';}">Ver Convocatorias</button>
						</p>
					</div>
				</div>
			</div>
		</html:form>
	</div>
</body>
</html>