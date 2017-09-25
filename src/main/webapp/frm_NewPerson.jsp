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
	<script
	src="js/personGrid.js?var=<%=com.jcabi.manifests.Manifests.read("App-Version")%>"
	type="text/javascript"></script>
</head>

 <body >

 <div align="center" class="container" >
	<html:form action="/updatePerson.do" method="post" styleId="frmAdmPereson" style="margin: 0px 0px 0px 0px;" enctype="multipart/form-data">
	<html:hidden  property="accion" styleId="accion"/>
	<html:hidden property="ID" />
	<bean:define id="stNumero" name="frmAdmPereson" property="NUMERO" />
	<bean:define id="stNombre" name="frmAdmPereson" property="NOMBRE" />
	<bean:define id="stSobreNombre" name="frmAdmPereson" property="SOBRENOMBRE" />
	<bean:define id="stCategoria" name="frmAdmPereson" property="CATEGORIA" />
	<bean:define id="stOrdenPrelacion" name="frmAdmPereson" property="ORDEN PRELACION" />
	<bean:define id="stId" name="frmAdmPereson" property="ID" />
	<bean:define id="stNroDocumento" name="frmAdmPereson" property="NRO DOCUMENTO" />
	<bean:define id="stRndName" name="frmAdmPereson" property="RNDNAME" />
	<bean:define id="stFoto" name="frmAdmPereson" property="FOTO" />
	<bean:define id="colCategorias" name="frmAdmPereson" property="CATEGORIAS" />
	<bean:define id="stCategoriaPreferida" name="frmAdmPereson" property="CATEGORIA PREFERIDA" />

	<div class="row" id="categoryRowModel">
		<div class="table-responsive"> 
			<table class="table table-bordered" id="personTable">
            <tbody>
                <tr >
                	<td rowspan="3"><img width="120px" height="120px" /></td>
                    <td><label class="col-md-2 control-label input-sm" for="NUMERO">Numero</label>  </td>
                    <td><input id="NUMERO" name="NUMERO" type="text" placeholder="" class="form-control input-md" value="${stNumero}"></td>
                    <td><label class="col-md-2 control-label input-sm" for="NRO DOCUMENTO">Documento</label>  </td>
                    <td><input id="NRO DOCUMENTO" name="NRO DOCUMENTO" type="text" placeholder="" class="form-control input-md" value="${stNroDocumento}"></td>
                </tr>
                <tr>
                    <td><label class="col-md-2 control-label input-sm" for="NOMBRE">Nombre</label></td>
                    <td colspan="3"><input id="NOMBRE" name="NOMBRE" type="text" placeholder="" class="form-control input-md" value="${stNombre}"></td>
                </tr>
                <tr>
                    <td><label class="col-md-2 control-label input-sm" for="NOMBRE">Sobrenombre</label></td>
                    <td colspan="3"><input id="SOBRENOMBRE" name="SOBRENOMBRE" type="text" placeholder="" class="form-control input-md" value="${stSobreNombre}"></td>
                </tr>
                <tr id="categoryModel" >
                	<td><input type="file" enctype="multipart/form-data" style="width:200px; display:none;"  id="IMG" name="IMG" class="FormElement" />
                	<input type="button" value="Select an Image" onclick="$('#IMG').click();" /></td>
                    <td><label class="col-md-2 control-label input-sm" for="NUMERO">Categor&iacute;a Preferida</label>  </td>
                    <td>
	                    <select class="form-control input-sm" name="CATEGORIA PREFERIDA" id="CATEGORIA PREFERIDA">
							<option value="">Seleccione</option>
							<logic:iterate name="frmAdmPereson" property="COLCATEGORIAS" id="item" indexId="idx">
								<c:if test="${stCategoriaPreferida == item.id}">
									<option value="${item.id}" selected="selected">${item.name}</option>
								</c:if>	
								<c:if test="${stCategoriaPreferida != item.id}">
									<option value="${item.id}">${item.name}</option>
								</c:if>	
							</logic:iterate>
						</select>
                    </td>                    
                    <td><label class="col-md-2 control-label input-sm" for="ORDEN PRELACION">Orden Prelaci&oacute;n</label>  </td>
                    <td><input id="ORDEN PRELACION" name="ORDEN PRELACION" type="text" placeholder="" class="form-control input-md" value="${stOrdenPrelacion}"></td>
                </tr>
                <c:set var="counter" value="${0}" />
				<logic:iterate name="frmAdmPereson" property="CATEGORIAS" id="personCategory" indexId="idx">
				<c:set var="counter" value="${counter+1}" />
				<tr >
                    <td><label class="col-md-2 control-label input-sm" for="NUMERO">Otras Categorias</label>  </td>
                    <td><label class="col-md-2 control-label input-sm" for="NUMERO"></label><button  class="btn btn-sm btn-primary btn-block" id="btnborrar"
							type="button" onclick="deleteCategoryRow(this);return false;" >Borrar</button></td>
                    <td>
	                    <select class="form-control input-sm" name="CATEGORIA_${counter}" id="CATEGORIA_${counter}">
							<option value="">Seleccione</option>
							<logic:iterate  name="frmAdmPereson" property="COLCATEGORIAS" id="item" indexId="idx">
								<c:if test="${personCategory.personCategory.id == item.id}">
									<option value="${item.id}" selected="selected">${item.name}</option>
								</c:if>	
								<c:if test="${personCategory.personCategory.id != item.id}">
									<option value="${item.id}">${item.name}</option>
								</c:if>	
							</logic:iterate>
						</select>
                    </td>                    
                    <td><label class="col-md-2 control-label input-sm" for="ORDEN PRELACION">Orden Prelaci&oacute;n</label>  </td>
                    <td><input id="ORDEN PRELACION_${counter}" name="ORDEN PRELACION_${counter}" type="text" placeholder="" class="form-control input-md" value="${stOrdenPrelacion}"></td>
                </tr>
				</logic:iterate>
	
            </tbody>
        </table>
		</div>
	</div>			

	<table class="table table-bordered">
	<tr>
		<td>
					<button class="btn btn-sm btn-primary btn-block"
							id="btnAddaAsignment" type="button"
							onclick="addCategoryRow(this, ${counter});return false;">Agregar
							Categor&iacute;a</button>
		</td>
		<td>
				<button  class="btn btn-sm btn-primary btn-block" id="btnEnviar"
							type="submit" >Guardar</button>
		</td>
		<td>
				<button  class="btn btn-sm btn-primary btn-block" id="btnCancel"
							type="button" onclick="this.window.close();" >Cancelar</button>
		</td>
	</tr>
	</table>

	</html:form>
</div>
</body>
</html>