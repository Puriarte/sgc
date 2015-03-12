var contArtSel= "";
var primeraVez=true;
var documento_cdo=1;
var documento_fac=2;
var documento_nc=3;
var documento_fav=11;
var documento_pag=21;
var urlReload = "lstPerson";
var formName = "#frmLstPerson";

Number.prototype.format = function(){
   return this.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
};

var editMessage = function(response,postdata){
	var json   = response.responseText;
    var result = JSON.parse(json);
    return [result.status,result.message,null];
}

var editOptions={top: 50, left: 100,
		width: 350,
		height:250,
		recreateForm:true,
		closeOnEscape: true,
		closeAfterAdd:true,
		closeAfterEdit:true,
		modal: true,
	    afterSubmit: editMessage,
};

var addOptions={top: 50, left: 100,
		width: 350,
		height:250,
		recreateForm:true,
		closeOnEscape: true,
		closeAfterAdd:true,
		closeAfterEdit:true,
		modal: true,
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
	   	colNames:['POS','ID',  'NUMERO','TIPO DOC.','NRO DOCUMENTO','NOMBRE','SOBRENOMBRE','CATEGORIA', 'ORDEN PRELACION' ],
	   	colModel:[
   			{name:"POS",			index:"1", key: false, jsonmap:"Pos", 		align:"center", 			width:10,  hidden:true, sortable:false},
   			{name:'ID',				index:'2', key: true,  jsonmap:"Id",									width:55,  editable:true, editoptions:{readonly:true,size:10},hidden:true},
	//S		{name:'FOTO', 			index:'3', width: 55, editable: true, edittype: 'image', editoptions: {src: ''}, formatter: function (cell, options) { return '<img src="./images/faces/flag_chica_' + options.rowId + '.jpg"/>'; }},
			{name:"NUMERO",			index:"4", key: false, jsonmap:"Numero", 	align:"center", fixed:true, width:80,  resizable:false, sortable:true,hidden:false, editable:true},
			{name:"TIPO DOC.",		index:"5", key: false, jsonmap:"FechaEnvio",align:"center", fixed:true, width:80,  sortable:true,resizable:false,  hidden:false},
			{name:"NRO DOCUMENTO",	index:"6", key: false, jsonmap:"Texto", 	align:"left", 	fixed:true, width:160, resizable:false, sortable:true,hidden:false, editable:true },
			{name:"NOMBRE",			index:"7", key: false, jsonmap:"Name", 		align:"center", fixed:true, width:150, resizable:false, sortable:true,hidden:false, editable: true },
			{name:"SOBRENOMBRE",	index:"8", key: false, jsonmap:"Nickname", 	align:"center", fixed:true, width:100, resizable:false, sortable:true,hidden:false, editable: true},
			{name:"CATEGORIA",		index:"9", key: false, jsonmap:"Category", 	edittype:"select", editoptions:{ dataUrl:'lstPersonCategory'}, editrules:{required:true}, width:90 , editable: true},
			{name:"ORDEN PRELACION",index:"10", key: false, jsonmap:"Priority", 	align:"center", fixed:true, resizable:false, width:140 ,sortable:true,hidden:false, editable: true},
	//		{name:'IMG', 			index:"11", align: 'left', editable: true, edittype: 'file', editoptions: { enctype: "multipart/form-data" }, search: false },
			],
		rowNum:60,
	   	scrollOffset:50,
		multiselect: false,
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
	}).navGrid('#pagerArticulos',{edit:true,add:true,del:false}, editOptions , addOptions);

	$("#bedata").click(function(){
		var gr = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
		if( gr != null ) jQuery("#gridArticulos").jqGrid('editGridRow',gr,{height:280,reloadAfterSubmit:true, closeAfterEdit: true});
		else alert("Seleccione una persona");
	});


	$("#escolaridad").click(function(){
		var gr = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
		if( gr != null )
			verEscolaridad(gr);
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


	//--- Ingresar Vale -----------------------------------------------------
	$("#btnIngresar").click(function(){
		try{
			ingresarListaSMS();
		}catch(Exception){
			alert(Exception.message);
		}
		return false;
	});

	//--- Ingresar Vale -----------------------------------------------------
	$("#btnIngresarMessage").click(function(){
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

