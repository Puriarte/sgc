<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//ES" "http://www.w3.org/TR/xhtml2/DTD/xhtml1-strict.dtd">
<html lang="es">
	<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="images/icon.png">

    <title>GCP</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.min.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
  </head>

  <body cz-shortcut-listen="true">

    <div class="container">

	<html:form action="/login.do" method="post" styleClass="form-signin" >
		<img src="images/sms.png" >
        <h2></h2>
        <input type="text" class="form-control" name="nombre" id="nombre" placeholder="Usuario" required="" autofocus="">
        <input type="password" class="form-control" name="password" id="password" placeholder="Clave" required="">
        <label class="checkbox"><input type="checkbox" value="remember-me">Recordar</label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
		<logic:messagesPresent property="error">
					<div class="ui-state-error" align="left" style="margin:5px;">
						<span class="ui-icon ui-icon-alert" style="float:left;"></span>
						<ol>
						</ol>
					</div>
		</logic:messagesPresent>

      </html:form>
	</div> <!-- /container -->

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/ie10-viewport-bug-workaround.js"></script>


</body></html>