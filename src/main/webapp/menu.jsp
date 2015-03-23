<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<div style="height:10px"></div>
			<a class="brand" href="#"><img src="images/logo.png" width="60px" /></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="main.jsp">Inicio</a></li>
				<li class="dropdown"><a href="lstSMS.do?accion=cargar"
					class="dropdown-toggle">Consulta de SMS</a></li>
				<li class="dropdown"><a href="lstPerson.do?accion=cargar"
					class="dropdown-toggle">Consulta de Empleados</a></li>
				<li class="dropdown"><a href="lstDispatch.do?accion=cargar"
					class="dropdown-toggle">Gesti&oacute;n de Convocatorias</a></li>
				<li class="dropdown"><a href="lstCategory.do?accion=cargar"
					class="dropdown-toggle">Gesti&oacute;n de Categor&iacute;as</a></li>
				<li class="dropdown"><a href="lstPlace.do?accion=cargar"
					class="dropdown-toggle">Gesti&oacute;n de Locales</a></li>
				<li class="active"><a href="logout.do">Salir</a></li>
			</ul>
		</div>
	</div>
</nav>
