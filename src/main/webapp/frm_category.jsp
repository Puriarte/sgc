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
    	<script src="http://cdn.jsdelivr.net/webshim/1.12.4/polyfiller.js"></script>
	<script>
	  webshims.setOptions('waitReady', false);
	  webshims.setOptions('forms-ext', {types: 'date'});
	  webshims.polyfill('forms forms-ext');
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



 <body >

 <div align="center" class="container" >
	<html:form action="/updateCategory.do" method="post" styleId="frmAdmCategory" style="margin: 0px 0px 0px 0px;" enctype="multipart/form-data">
	<html:hidden  property="accion" styleId="accion"/>
	<html:hidden property="ID" />
	<bean:define id="stNombre" name="frmAdmCategory" property="NOMBRE" />

	<div class="row" id="categoryRowModel">
		<div class="table-responsive"> 
			<table class="table-condensed" id="personTable">
            <tbody>
                <tr>
                    <td><label class="col-md-2 control-label input-sm" for="NOMBRE">Nombre</label></td>
                    <td colspan="3"><input id="NOMBRE" name="NOMBRE" type="text" placeholder="" class="form-control input-md" value="${stNombre}"></td>
                </tr>
                
				<logic:notEmpty name="frmAdmCategory" property="ID" >
                <tr>
                    <td><label class="col-md-2 control-label input-sm">Inactivo</label></td>
                    <td colspan="3"><html:checkbox property="DELETED" value="ON" ></html:checkbox></td>
                </tr>
				</logic:notEmpty>
                
            </tbody>
        </table>
		</div>
	</div>			

	<button  id="btnEnviar" style="display:none;" type="submit" >Guardar</button>

	</html:form>
</div>
</body>
</html>