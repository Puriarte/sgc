var contArtSel= "";
var primeraVez=true;
var documento_cdo=1;
var documento_fac=2;
var documento_nc=3;
var documento_fav=11;
var documento_pag=21;
var counter = 0;
var urlReload = "lstPerson";
var formName = "#frmLstPerson";
var rndNAme= "";
var selId = "";

Number.prototype.format = function(){
   return this.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
};

var editMessage = function(response,postdata){
	var json   = response.responseText;
    var result = JSON.parse(json);
    return [result.status,result.message,null];
}


var editOptions={
		top: 120, left: 250,
		width: 800,
		height:460,
		recreateForm:true,
		closeOnEscape: true,
		closeAfterAdd:true,
		closeAfterEdit:true,
		modal: true,
		beforeInitData: function () {
			var cm = jQuery("#gridArticulos").jqGrid('getColProp','FOTO');
			var selRowId = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');	
//            cm.editoptions.src = './uploads/flag_mediana_' + $('#gridArticulos').getCell(selRowId, 'RNDNAME') + '.jpg';
        },
        onInitializeForm : function(formid){
        	$(formid).attr('method','POST');
            $(formid).attr('action','""');
            $(formid).attr('enctype','multipart/form-data');
               	rndNAme=  Math.random().toString(36).substring(7);
               	selId = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');

            $('#IMG').ajaxfileupload({
            	'action': 'updatePersonImage.do',
                'params': {
                  'ID': selId,
                  'RNDNAME' :  rndNAme
                },
                'onComplete': function(response) {
                  	document.getElementById("FOTO").src="./uploads/flag_mediana_"  + selId + rndNAme + ".jpg";
                  	document.getElementById("RNDNAME").value= selId + rndNAme ;
                    alert("Se ha cargado la foto.");
                }
              });           
        }, 
	    beforeShowForm: function ($form) {
	    	$("#RNDNAME")
            .prop("disabled", true)
            .addClass("ui-state-disabled")
            .closest(".DataTD")
            .prev(".CaptionTD")
            .prop("disabled", true)
            .addClass("ui-state-disabled")

	    },
	    afterSubmit: editMessage,
};

var addOptions={
		top: 180, left: 250,
		width: 800,
		height:400,
		recreateForm:true,
		closeOnEscape: true,
		closeAfterAdd:true,
		closeAfterEdit:true,
		modal: true,
		beforeInitData: function () {
			var cm = jQuery("#gridArticulos").jqGrid('getColProp','FOTO');
			var selRowId = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
            cm.editoptions.src = './uploads/persona.jpg';
        },
        onInitializeForm : function(formid){
            $(formid).attr('method','POST');
            $(formid).attr('action','""');
            $(formid).attr('enctype','multipart/form-data');
            
            $('#IMG').ajaxfileupload({
            	'action': 'updatePersonImage.do',
                'params': {
                  'ID': jQuery("#gridArticulos").jqGrid('getGridParam','selrow')
                },
                'onComplete': function(response) {
                	document.getElementById("FOTO").src="./uploads/flag_mediana_" + selId + rndNAme + ".jpg"
                  alert("Se ha cargado la foto.");
                }
              });
           
        }, 
		afterSubmit: editMessage
};


jQuery(document).ready(function(){

	//--- Botonoes ---------------------------------------------------------
	$(function(){
	});
	//--- Fin Botonoes ---------------------------------------------------------

	//--- Mascaras ---------------------------------------------------------
	$.mask.masks = $.extend($.mask.masks,{
		importe:{ mask : '99.999999', type : 'reverse' },
		fecha:{ mask : '19-39-9999'  }
	});
	//--- Fin Mascaras ---------------------------------------------------------

	//--- Inputs ---------------------------------------------------------
  	$(function(){
    	$("input:text").setMask();
  	});
  	//--- Fin Inputs ---------------------------------------------------------
  //--- Validacion Formulario -----------------------------------------------------
	$.validator.addMethod(
			"dateUY",
			function (value, element) {
				return Date.parseExact(value, "dd-MM-yyyy");
			},
			"Ingrese una fecha en el formato dd-mm-yyyy"
	);

	var validator = $(formName).validate({
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		ignore: ":hidden",
		wrapper: "li",
		errorClass: "ui-state-error-text",
		errorPlacement: function(error, element) {
			error.appendTo(element.parent());
		},
		rules: {
		},
		messages: {
		}
	});

	//--- FIN Validacion Formulario -----------------------------------------------------
	jQuery("#gridArticulos").jqGrid({
	   	url:urlReload,
	   	postData: {
			fechaDesde: function() {
				return $("#fechaDesde").val();
				},
				fechaHasta: function() { return $("#fechaHasta").val(); },
				estado: function() { return $("#estado").val(); },
				category: function() { return $("#category").val(); },
				priority: function() { return $("#priority").val(); },
			primeraVez:primeraVez
	   	},
	   	loadonce:false,
	   	mtype: 'GET',
	   	datatype: "local", // se usa local para que no cargue registros en el primer acceso a la grilla
	 	colNames:['POS','ID', 'FOTO' ,'NUMERO','TIPO DOC.','NRO DOCUMENTO','NOMBRE','SOBRENOMBRE', 'CATEGORIA PREFERIDA', 'ORDEN PRELACION', 'OTRAS CATEGORIAS', 'IMG', 'RNDNAME'],
	   	colModel:[
   			{name:"POS",			index:"1", key: false, jsonmap:"Pos", 		align:"center", 			width:10, hidden:true, sortable:false},
   			{name:'ID',				index:'2', key: true,  jsonmap:"Id",									width:55, hidden:true},
   			{name:'FOTO', 			index:'3', key: false, jsonmap:"Picture", 	width: 15, 	align:"center", formatter: function (cell, options) { 
   				return '<img width="25px" src="./uploads/flag_chica_' +  cell + '.jpg"/>'; 
   				}},
   			{name:"NUMERO",			index:"4", key: false, jsonmap:"Numero", 	align:"center", fixed:true, width:80,  resizable:false, sortable:true, sorttype:'number' , hidden:false},
			{name:"TIPO DOC.",		index:"5", key: false, jsonmap:"FechaEnvio",align:"center", fixed:true, width:80,  sortable:true, resizable:false,  hidden:false},
			{name:"NRO DOCUMENTO",	index:"6", key: false, jsonmap:"Texto", 	align:"left", 	fixed:true, width:120, resizable:false, sortable:true, sorttype:'number', hidden:false },
			{name:"NOMBRE",			index:"7", key: false, jsonmap:"Name", 		align:"center", fixed:true, width:150, resizable:false, sortable:true,hidden:false },
			{name:"SOBRENOMBRE",	index:"8", key: false, jsonmap:"Nickname", 	align:"center", fixed:true, width:100, resizable:false, sortable:true,hidden:false},
			{name:"CATEGORIA PREFERIDA",index:"10", key: false, jsonmap:"PreferedCategory", width:90},
			{name:"ORDEN PRELACION",index:"11",key: false, jsonmap:"Priority", 	align:"center", fixed:true, resizable:false, width:140 ,sortable:true,hidden:false},
			{name:"OTRAS CATEGORIAS",		index:"9", key: false, jsonmap:"Category", width:90},
			{name:'IMG', 			index:"12", align: 'left', width:1,  search: false }, 
			{name:'RNDNAME', 		index:"13", align: 'left', jsonmap:"Picture",  hidden:true, width:0}, 
		],
		rowNum:300,
	   	scrollOffset:50,
		multiselect: false,
		caption: null,
		forceFit: true,
		height:$(window).height() * 0.70,
		width: $(window).width() * 0.81,
		pager: "pagerArticulos",
		gridview: true,
		viewrecords: true,
		multiselect: true,
        multiboxonly: true,
		footerrow: false,
		closeAfterEdit:true,
		editurl:"updatePerson.do",
		jsonReader: { repeatitems : false, root:"rows" },
		loadComplete: function(data) {
		},
		beforeSelectRow: function(id){
			jQuery("#gridArticulos").setSelection (id, true);
			return false;
//			$("#idsValesSel").val(id);
		},
		ondblClickRow: function(id){
			jQuery("#gridArticulos").jqGrid('setSelection',id,false);
			$('#btnVerFactura').trigger('click');
		},
		ajaxGridOptions: {dataFilter:function(data,dataType){
			var msg = eval('(' + data + ')');
			if ((msg.error != undefined) &&  (msg.error.length>0)){
				hmtlError = "";
				for(var i=0;i<msg.error.length;i++){
					var obj = msg.error[i];
					hmtlError += obj["errtext"]+ "<br/>";
				}
		    	$("#dialogo_resultados").html(hmtlError);
				$("#dialogo_resultados").dialog({
					resizable: false,
					height:150,
					modal: true,
					open: function(event, ui){
						$('body').css('overflow','hidden');
						$('.ui-widget-overlay').css('width','100%');
					},
					close: function(event, ui) {
						$('body').css('overflow','auto');
					},
					buttons: {
						"Aceptar": function() {
							$( this ).dialog( "close" );
						}
					}
				});
				$("#dialogo_resultados").dialog("open");
				return "";
			}else {
				return data;
			}
			}
		}
	}).navGrid('#pagerArticulos',{edit:false,add:false,del:false,search: false}, editOptions , addOptions);
/*	.navButtonAdd("#pagerArticulos", {
		    caption: "Agregar",
		    buttonicon: "ui-icon-disk",
		    onClickButton: function () {
		    	agregarCliente();
		    },
		    position: "last"
		})
	.navButtonAdd("#pagerArticulos", {
		    caption: "Modificar",
		    buttonicon: "ui-icon-disk",
		    onClickButton: function () {
				var id = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
				if (id)	{
			    	modificarCliente();
				}else alert("Seleccione una persona");
		    },
		    position: "last"
		})
	.navButtonAdd('#pagerArticulos', {
        caption: "Crear mensaje",
        buttonicon: "ui-icon-mail-open",
        onClickButton: function () {
        	try{
    			ingresarMensajeSMS();
    		}catch(Exception){
    			alert(Exception.message);
    		}
        },
        position: "last"
    }).navButtonAdd('#pagerArticulos', {
        caption: "Crear convocatoria",
        buttonicon: "ui-icon-comment",
        onClickButton: function () {
        	try{
    			ingresarListaSMS();
    		}catch(Exception){
    			alert(Exception.message);
    		}
        },
        position: "last"
    }).navButtonAdd('#pagerArticulos', {
        caption: "Informes",
        buttonicon: "ui-icon-disk",
        onClickButton: function () {
        	var gr = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
    		if( gr != null )
    			verEscolaridad(gr);
    		else alert("Seleccione una persona");
        },
        position: "last"
    });
*/
	$("#bedata").click(function(){
		var gr = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
		if( gr != null ) jQuery("#gridArticulos").jqGrid('editGridRow',gr,{height:280,reloadAfterSubmit:true, closeAfterEdit: true});
		else alert("Seleccione una persona");
	});


	function verEscolaridad(gr){
		try{

			var x=800;
			var y=600;
			var x1=(screen.width - x) /2;
			var y1=(screen.height - y) /2;

			var params="'location=0,toolbar=0,directories=0,status=0,menubar=0,scrollbars=1,copyhistory=0,resizable=0," +
			"position: absolute, top=" + y1 + ", left=" + x1 + ", height=" + y + ", width=" + x + "'"

			var win = window.open("school.do?idPerson=" + gr,"modVer",params);


//			var win = window.open("mostrarEscolaridadPDF?idPerson=" + gr,"modVer",params);

		}catch(Exception){
			alert(Exception.message);
		}
		return false;
	}


    function parseXMLAutocomplete(xml) {
        var results = [];
        $(xml).find('item').each(function() {
            var text = $.trim($(this).find('text').text());
            var value = $.trim($(this).find('value').text());
            results[results.length] = { 'data': { text: text, value: value },
                'result': text, 'value': value
            };
        });
        return results;
    };

    function formatItemAutocomplete(data) {
        return data.text;
    };

    function formatResultAutocomplete(data) {
		return data.value;
    };

	//--- FIN - Filtros -----------------------------------------------------

	//Para acutalizar la grilla
	$("#lk_actualizar").click(function(){
		try {
			primeraVez=false;
		    $("#gridArticulos").setGridParam({datatype:'json', page:1}).trigger('reloadGrid');
		    return false;
		} catch(Exception){
		  alert(Exception.message);
		}
	});


	//Para acutalizar la grilla
	$("#lk_edit").click(function(){
		try{
			var id = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
			if (id)	{
		    	modificarCliente();
			}else alert("Seleccione una persona");
			
		}catch(Exception){
			alert(Exception.message);
		}
		return false;

	});
	//Para acutalizar la grilla
	$("#lk_add").click(function(){
		try{
	    	agregarCliente();
		}catch(Exception){
			alert(Exception.message);
		}
		return false;
	});

	//Crear Convocatoria
	$("#lk_dispatch").click(function(){
		try{
			ingresarListaSMS();
		}catch(Exception){
			alert(Exception.message);
		}

		return false;
	});
		
	//Para acutalizar la grilla
	$("#lk_addDispatch").click(function(){
		try{
			agregarADispatch();
		}catch(Exception){
			alert(Exception.message);
		}

		return false;
	});

	//Para acutalizar la grilla
	$("#lk_report").click(function(){
		var gr = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
		if( gr != null )
			verEscolaridad(gr);
		else alert("Seleccione una persona");

		return false;
	});

	//Para acutalizar la grilla
	$("#lk_message").click(function(){
		try{
			ingresarMensajeSMS();
		}catch(Exception){
			alert(Exception.message);
		}
		return false;

	});
	
});


function ingresarMensajeSMS(){
	var s;
	s = jQuery("#gridArticulos").jqGrid('getGridParam','selarrrow');
	var url= "createDispatch.do?accion=load&nroDestino=" + s.toString();

	$("#nroDestino").val(s);
	var dialog = $('<div style="display:none" class="loading"><iframe> </iframe></div>').appendTo('body');

	// open the dialog
	dialog.dialog({
		// add a close listener to prevent adding multiple divs to the document
		close: function(event, ui) {
			// remove div with all data and events
			dialog.remove();
		},
		buttons: {
			"Aceptar": function() {
				var isValidForm = $('#frmAdmDispatch')[0].checkValidity();
				if (!isValidForm) {
					document.getElementById("btnEnviar").click();
				}else{
					$.ajax({
						url: "createDispatch.do",
						data: $("#frmAdmDispatch").serialize(),
						type: $("#frmAdmDispatch").attr("method"),
						dataType: "html",
						dataFilter:function(data,dataType){
							$("#dialogo_resultados").html(data);
							$("#dialogo_resultados").dialog({
								resizable: false,
								width:400,
								height:240,
								modal: true,
								open: function(event, ui){
									$('body').css('overflow','hidden');
									$('.ui-widget-overlay').css('width','100%');
								},
								close: function(event, ui) {
									$('body').css('overflow','auto');
								},
								buttons: {
									"Aceptar": function() {
										$( this ).dialog( "close" );
										try{
											dialog.remove();
										}catch(e){}
									}
								}
							});
							$("#dialogo_resultados").dialog("open");
							return data;
						},
						error: function(jqXHR, textStatus, errorThrown){
							alert("error = " + textStatus);
						}
					});
				}
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
			}
		},
		modal: true,
		resizable: true,
		height:550,
		width:700
	});
	// load remote content
	dialog.load(
			url,
			{}, // omit this param object to issue a GET request instead a POST request, otherwise you may provide post parameters within the object
			function (responseText, textStatus, XMLHttpRequest) {
				// remove the loading class
				dialog.removeClass('loading');
			}
	);

	return false;
}


function ingresarListaSMS(){
	var s;
	s = jQuery("#gridArticulos").jqGrid('getGridParam','selarrrow');
	var url= "createCategoryDispatch.do?accion=load&nroDestino=" + s.toString();
	$("#nroDestino").val(s);
	var dialog = $('<div style="display:none" class="loading"><iframe> </iframe></div>').appendTo('body');

	// open the dialog
	dialog.dialog({
		// add a close listener to prevent adding multiple divs to the document
		close: function(event, ui) {
			// remove div with all data and events
			dialog.remove();
		},
		buttons: {
			"Aceptar": function() {
				var isValidForm = $('#frmAdmDispatch')[0].checkValidity();
				if (!isValidForm) {
					document.getElementById("btnEnviar").click();
				}else{
					$.ajax({
						url: "createCategoryDispatch.do",
						data: $("#frmAdmDispatch").serialize(),
						type: $("#frmAdmDispatch").attr("method"),
						dataType: "html",
						dataFilter:function(data,dataType){
							$("#dialogo_resultados").html(data);
							$("#dialogo_resultados").dialog({
								resizable: false,
								width:400,
								height:240,
								modal: true,
								open: function(event, ui){
									$('body').css('overflow','hidden');
									$('.ui-widget-overlay').css('width','100%');
								},
								close: function(event, ui) {
									$('body').css('overflow','auto');
								},
								buttons: {
									"Aceptar": function() {
										$( this ).dialog( "close" );
										try{
											dialog.remove();
										}catch(e){}
									}
								}
							});
							$("#dialogo_resultados").dialog("open");
							return data;
						},
						error: function(jqXHR, textStatus, errorThrown){
							alert("error = " + textStatus);
						}
					});
				}
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
			}
		},
		title: "Agregar Personas a Convocatoria",
		modal: true,
		resizable: true,
		height:550,
		width:850
	});
	// load remote content
	dialog.load(
			url,
			{}, // omit this param object to issue a GET request instead a POST request, otherwise you may provide post parameters within the object
			function (responseText, textStatus, XMLHttpRequest) {
				// remove the loading class
				dialog.removeClass('loading');
			}
	);

	return false;
}


function agregarADispatch(gr){
	try{
		$("#accion").val("cargar");
		var personId = jQuery("#gridArticulos").jqGrid('getGridParam','selarrrow');
		refDialogIframe = $('<iframe id="ifModCliente" frameborder="0" marginwidth="20px" marginheight="20px" width="100%" src="addToCategoryDispatch.do?accion=load&nroDestino=' + personId + '"/>').dialog({
			resizable: true,
			width:875,
			height:500,
			modal: true,
			title: "Agregar Personas a Convocatoria",
			open: function(event, ui){
				$('body').css('overflow','hidden');
				$('.ui-widget-overlay').css('width','100%');
			},
			close: function(event, ui) {
				$('body').css('overflow','auto');
			},
			buttons: {
				"Aceptar": function() {
					refDialogIframe["0"].contentDocument.getElementById("btnEnviar").click();
/*					$( this ).dialog( "close" );
					$("#lk_actualizar").click();
	*/			},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
		}).width(875).height(500);
	}catch(Exception){
		alert(Exception.message);
	}
	return false;
}


function removeOptions(selectbox)
{
    var i;
    for(i=selectbox.options.length-1;i>=0;i--)
    {
        selectbox.remove(i);
    }
}


function modificarCliente(){
	try{
		$("#accion").val("cargar");
		var personId = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
		refDialogIframe = $('<iframe id="ifModCliente" frameborder="0" marginwidth="0px" marginheight="0px" style="overflow-y:hidden; overflow-x:scroll;" src="updatePerson.do?accion=load&ID=' + personId + '"/>').dialog({
			resizable: true,
			width:775,
			height:500,
			modal: true,
			title: "Modificar Persona",
			open: function(event, ui){
				$('body').css('overflow','hidden');
				$('.ui-widget-overlay').css('width','100%');
			},
			close: function(event, ui) {
				$('body').css('overflow','auto');
			},
			buttons: {
				"Aceptar": function() {
					refDialogIframe["0"].contentDocument.getElementById("btnEnviar").click();
				},
				"Agregar Categoria": function() {
					refDialogIframe["0"].contentDocument.getElementById("btnAddaAsignment").click();
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
		}).width(775).height(500);
	}catch(Exception){
		alert(Exception.message);
	}
	return false;
}


function agregarCliente(){
	try{
		$("#accion").val("cargar");
		var personId = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
		refDialogIframe = $('<iframe id="ifModCliente" frameborder="0" marginwidth="0px" marginheight="0px" src="updatePerson.do?accion=load&ID=' + '"/>').dialog({
			resizable: true,
			width:775,
			height:500,
			modal: true,
			title: "Agregar Empleado",
			open: function(event, ui){
				$('body').css('overflow','hidden');
				$('.ui-widget-overlay').css('width','100%');
			},
			close: function(event, ui) {
				$('body').css('overflow','auto');
			},
			buttons: {
				"Aceptar": function() {
					refDialogIframe["0"].contentDocument.getElementById("btnEnviar").click();
					$( this ).dialog( "close" );
					$("#lk_actualizar").click();
				},
				"Agregar Categoria": function() {
					refDialogIframe["0"].contentDocument.getElementById("btnAddaAsignment").click();
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
		}).width(775).height(500);
	}catch(Exception){
		alert(Exception.message);
	}
	return false;
}


function desmarcarSelect(selectName){

	var selectbox = document.getElementById(selectName);
	if (!hasOptions(selectbox)) { return; }
	for (var i=0; i<selectbox.options.length; i++) {
		selectbox.options[i].selected = false;
	}
}

function hasOptions(obj) {
	if (obj!=null && obj.options!=null) { return true; }
	return false;
	}


function addCategoryRow(element, elementCounter){
	
	
	try{
		if (counter==0)	counter = elementCounter;
		counter = counter +1;
		
		var tr = document.createElement('tr');

		var innerTr= document.createElement('tr');
		innerTr.innerHTML=document.getElementById("categoryModel").innerHTML;

		innerTr.childNodes[1].innerHTML="";
		innerTr.childNodes[2].innerHTML="";
		innerTr.childNodes[3].innerHTML="<button  class='btn btn-sm btn-primary btn-block' id='btnborrar' type='button' onclick='deleteCategoryRow(this);return false;'>Borrar</button>";
		innerTr.childNodes[5].childNodes[1].id ="CATEGORIA_" + counter;
		innerTr.childNodes[5].childNodes[1].name ="CATEGORIA_" + counter;

		innerTr.childNodes[9].childNodes[0].id ="ORDEN PRELACION_" + counter;
		innerTr.childNodes[9].childNodes[0].name ="ORDEN PRELACION_" + counter;

		document.getElementById("personTable").appendChild(innerTr);

	}catch(E){
	}
}

function deleteCategoryRow(element){
	try{
		element.parentElement.parentElement.remove();
	}catch(E){
	}
}


function continueAddToDispatch(i){
	try{
		ev.preventDefault();
		jQuery("#frmAdmDispatch").submit();
		return false;
	}catch(E){
	}
}
