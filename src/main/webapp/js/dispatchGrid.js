var contArtSel= "";
var primeraVez=true;
var documento_cdo=1;
var documento_fac=2;
var documento_nc=3;
var documento_fav=11;
var documento_pag=21;
var urlReload = "lstDispatch";
var formName = "#frmLstDispatch";
var counter = 0;

Number.prototype.format = function(){
   return this.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
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
	   	colNames:['POS','ID','LUGAR','FECHA','NRO.DOCUMENTO','NOMBRE'],
	   	colModel:[
   			{name:"POS",index:"1", key: false, jsonmap:"Pos", align:"center", hidden:true, width:10, sortable:false},
   			{name:'ID',index:'2',key: true, jsonmap:"Id", width:55,editable:true,editoptions:{readonly:true,size:10},hidden:true},
			{name:"LUGAR",index:"3", key: false, jsonmap:"Place", align:"left", fixed:true,  width:250 ,resizable:false, sortable:true,hidden:false},
			{name:"FECHA",index:"4", key: false, jsonmap:"FechaEnvio", align:"center", fixed:true, resizable:false,  width:120  ,sortable:true,hidden:false},
			{name:"NRO.DOCUMENTO",index:"5", key: false, jsonmap:"Texto", align:"left", fixed:true,  width:160 ,resizable:false, sortable:true,hidden:true},
			{name:"NOMBRE",index:"6", editable: true,  key: false, jsonmap:"Name", align:"center", fixed:true, width:350, resizable:false, sortable:true,hidden:false},
			],
	   	rowNum:60,
	   	scrollOffset:50,
		caption: null,
		forceFit: true,
		height:$(window).height() * 0.60,
		width: $(window).width() * 0.98,
		pager: "pagerArticulos",
		gridview: true,
		viewrecords: true,
		multiselect: true,
        multiboxonly: true,
		footerrow: false,
		closeAfterEdit:true,
		editurl:"updateDispatch.do",
		jsonReader: { repeatitems : false, root:"rows" },
		loadComplete: function(data) {
		},
		onSelectRow: function(id){
			$("#idsValesSel").val(id);
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
	}).navGrid('#pagerArticulos',{edit:false,add:false,del:false});

	$("#bedata").click(function(){
		var gr = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
		if( gr != null ) jQuery("#gridArticulos").jqGrid('editGridRow',gr,{height:280,reloadAfterSubmit:true, closeAfterEdit: true});
		else alert("Seleccione una convocatoria");
	});


	$("#escolaridad").click(function(){
		var gr = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
		if( gr != null )
			verEscolaridad(gr);
		else alert("Seleccione una convocatoria");
	});


   

	//--- FIN - Filtros -----------------------------------------------------

	
	//--- Ingresar Vale -----------------------------------------------------
	$("#btnIngresar").click(function(){
		try{
			ingresarListaSMS();
		}catch(Exception){
			alert(Exception.message);
		}
		return false;
	});
	
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
	var url= "modifyCategoryDispatch.do?accion=load&nroDestino=" + s.toString();
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


function removeOptions(selectbox)
{
    var i;
    for(i=selectbox.options.length-1;i>=0;i--)
    {
        selectbox.remove(i);
    }
}


function addAssignmentRow(element, elementCounter){
	
	try{
		if (counter==0)	counter = elementCounter;
		counter = counter +1;
		
		var innerDiv = document.createElement('div');
		innerDiv.id="assignmentRow" + counter;
		innerDiv.style.display='inline';
		innerDiv.innerHTML=document.getElementById("assignmentRowModel").innerHTML;
		
		innerDiv.childNodes[1].childNodes[1].id="personMovil_"+counter;
		innerDiv.childNodes[1].childNodes[1].name="personMovil_"+counter;

		innerDiv.childNodes[3].childNodes[1].id="personCategory_"+counter;
		innerDiv.childNodes[3].childNodes[1].name="personCategory_"+counter;

		innerDiv.childNodes[5].childNodes[1].id="assignmentStatus_"+counter;
		innerDiv.childNodes[5].childNodes[1].name="assignmentStatus_"+counter;
		
		document.getElementById("assignmentRowContainer").appendChild(innerDiv);

	}catch(E){
		
	}
		
	
}
