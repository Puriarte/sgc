<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt-rt.tld" prefix="fmt-rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<logic:messagesPresent property="error">
<div id="div-error" class="ui-state-error ui-corner-all" align="left" style="padding: 10px;">
	<span class="ui-icon ui-icon-alert" style="float:left;"></span>
	<ol>
		<html:messages property="error" id="errMsg" >
			<li><bean:write name="errMsg"/></li>
		</html:messages>
	</ol>
</div>
</logic:messagesPresent>
<logic:messagesPresent message="true">
	<html:messages id="msg" property="msg" message="true">
		--- SISIS
  	</html:messages>
</logic:messagesPresent>


</body>
</html>