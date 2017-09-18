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

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.min.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->	
</head>

 <body >
	<div align="center" class="ui-widget">

<!-- 
		<form-property name="NOMBRE" type="java.lang.String" />
		<form-property name="SOBRENOMBRE" type="java.lang.String" />
		<form-property name="CATEGORIA" type="java.lang.String" />
		<form-property name="ORDEN PRELACION" type="java.lang.Integer" />
		<form-property name="NUMERO" type="java.lang.String" />
		<form-property name="NRO DOCUMENTO" type="java.lang.String" />
		<form-property name="IMG" type="org.apache.struts.upload.FormFile" />
		<form-property name="RNDNAME" type="java.lang.String" />
		<form-property name="FOTO" type="java.lang.String" />
		<form-property name="CATEGORIAS" type="java.util.ArrayList" />
 -->

	<html:form action="/updatePerson.do" method="post" styleId="frmAdmPereson" style="margin: 0px 0px 0px 0px;">
	<html:hidden  property="accion" styleId="accion"/>
	<html:hidden property="ID" />

<fieldset>

<!-- Prepended text-->
<!-- Text input-->
<div class="row">
<div class="form-group">
  <div class="col-md-4">
	  <label class="col-md-4 control-label" for="NUMERO">NUMERO</label>  
  </div>
  <div class="col-md-4">
	  <input id="NUMERO" name="NUMERO" type="text" placeholder="" class="form-control input-md">
  </div>
</div>
</div>

<!-- Text input-->
<div class="row">
<div class="form-group">
  <label class="col-md-4 control-label" for="NRO DOCUMENTO">NRO DOCUMENTO</label>  
  <div class="col-md-4">
  <input id="NRO DOCUMENTO" name="NRO DOCUMENTO" type="text" placeholder="" class="form-control input-md">
    
  </div>
</div>
</div>

<!-- Text input-->
<div class="row">
	<div class="form-group">
	  <label class="col-md-4 control-label" for="NOMBRE">NOMBRE</label>  
	  <div class="col-md-4">
		  <input id="NOMBRE" name="NOMBRE" type="text" placeholder="" class="form-control input-md">
	  </div>
	</div>
</div>

<!-- Text input-->
<div class="row">
<div class="form-group">
  <label class="col-md-4 control-label" for="SOBRENOMBRE">SOBRENOMBRE</label>  
  <div class="col-md-4">
  <input id="SOBRENOMBRE" name="SOBRENOMBRE" type="text" placeholder="" class="form-control input-md">
    
  </div>
</div>
</div>

<!-- Multiple Checkboxes (inline) -->
<div class="row">
<div class="form-group">
  <label class="col-md-4 control-label" for="CATEGORIAS">CATEGORIAS</label>
  <div class="col-md-4">
    <label class="checkbox-inline" for="CATEGORIAS-0">
      <input type="checkbox" name="CATEGORIAS" id="CATEGORIAS-0" value="1">
      1
    </label>
    <label class="checkbox-inline" for="CATEGORIAS-1">
      <input type="checkbox" name="CATEGORIAS" id="CATEGORIAS-1" value="2">
      2
    </label>
    <label class="checkbox-inline" for="CATEGORIAS-2">
      <input type="checkbox" name="CATEGORIAS" id="CATEGORIAS-2" value="3">
      3
    </label>
    <label class="checkbox-inline" for="CATEGORIAS-3">
      <input type="checkbox" name="CATEGORIAS" id="CATEGORIAS-3" value="4">
      4
    </label>
  </div>
</div>
</div>

<!-- Select Basic -->
<div class="row">
<div class="form-group">
  <label class="col-md-4 control-label" for="CATEGORIA PREFERIDA">CATEGORIA PREFERIDA</label>
  <div class="col-md-4">
    <select id="CATEGORIA PREFERIDA" name="CATEGORIA PREFERIDA" class="form-control">
      <option value="1">Option one</option>
      <option value="2">Option two</option>
    </select>
  </div>
</div>
</div>

<!-- Text input-->
<div class="row">
<div class="form-group">
  <label class="col-md-4 control-label" for="ORDEN PRELACION">ORDEN PRELACION</label>  
  <div class="col-md-4">
  <input id="ORDEN PRELACION" name="ORDEN PRELACION" type="text" placeholder="" class="form-control input-md">
    
  </div>
</div>
</div>

<!-- Button -->
<div class="row">
<div class="form-group">
  <label class="col-md-4 control-label" for="OK"></label>
  <div class="col-md-4">
    <button id="OK" name="OK" class="btn btn-primary">OK</button>
  </div>
</div>
</div>

</fieldset>
	

	</html:form>
</div>
</body>
</html>