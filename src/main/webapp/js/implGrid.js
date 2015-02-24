var contArtSel= "";
var primeraVez=true;
var documento_cdo=1;
var documento_fac=2;
var documento_nc=3;
var documento_fav=11;
var documento_pag=21;

Number.prototype.format = function(){
   return this.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
};

var deleteOptions={
			url: 'deleteSMS.do',
			mtype:"POST",
			reloadAfterSubmit:true,
			serializeDelData: function (postdata) {
			      var rowdata = jQuery('#gridArticulos').getRowData(postdata.id);
			      // append postdata with any information 
			      return {id: postdata.id, oper: postdata.oper,  smsid: rowdata.ID};
			 }
};


var editMessage = function(response,postdata){
	var json   = response.responseText;
    var result = JSON.parse(json);
    return [result.status,result.message,null];
}

var editOptions={top: 50, left: "100",
		width: 300,
		height:300,
		recreateForm:true,
		closeOnEscape: true,
		closeAfterAdd:true,
		closeAfterEdit:true,
		modal: true,
	    beforeShowForm: function ($form) {
	    	$("#NUMERO")
            .prop("disabled", true)
            .addClass("ui-state-disabled")
            .closest(".DataTD")
            .prev(".CaptionTD")
            .prop("disabled", true)
            .addClass("ui-state-disabled")

	    },
	    afterSubmit: editMessage,
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

	var validator = $("#frmLstSMS").validate({
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
	   	url:'lstSMS',
	   	postData: {
			fechaDesde: function() {return $("#fechaDesde").val();},
			fechaHasta: function() { return $("#fechaHasta").val();},
			estado: function() {return $("#estado").val();},
			convocatoria: function() {return $("#convocatoria").val();},
			primeraVez:primeraVez
	   	},
	   	loadonce:false,
	   	mtype: 'GET',
	   	datatype: "local", // se usa local para que no cargue registros en el primer acceso a la grilla
	   	colNames:['POS','ID','CLIENTE','IDDOCUMENTO', 'Fecha Creado','Numero','Nombre','Texto','Fecha Envio','Dir','Estado', 'Convocatoria'],
	   	colModel:[
   			{name:"POS"			,index:"1", key: true, 	jsonmap:"Pos", 		 align:"center", hidden:true,  width:10,  editable:false, sortable:false},
  			{name:"ID"			,index:"2", key: true, 	jsonmap:"Id", 		 align:"center", hidden:true,  width:100, editable:true, editoptions:{readonly:true,size:10}},
  			{name:"CLIENTE"		,index:"3", key: true, 	jsonmap:"Cliente",	 align:"center", hidden:true,  width:400, editable:false, sortable:true},
  			{name:"IDDOCUMENTO"	,index:"4", key: true, 	jsonmap:"IdDoc",	 align:"center", hidden:true,  width:10},
			{name:"FECHA"		,index:"5", key: true, 	jsonmap:"Fecha",	 align:"center", hidden:false, width:110, editable:false, fixed:true, resizable:false, sortable:true},
			{name:"NUMERO"		,index:"7", 			jsonmap:"Numero",	 align:"center", hidden:false, width:120, editable:false, fixed:true, resizable:false, sortable:true},
			{name:"NOMBRE"		,index:"8", 			jsonmap:"Nombre",	 align:"left", 	 hidden:false, width:140, editable:false, fixed:true, resizable:false, sortable:true},
			{name:"TEXTO"		,index:"9", 			jsonmap:"Texto", 	 align:"left", 	 hidden:false, width:380, editable:false, fixed:true, resizable:false, sortable:true},
			{name:"FECHA ENVIO"	,index:"10", 			jsonmap:"FechaEnvio",align:"center", hidden:false, width:110, editable:false, fixed:true, resizable:false, sortable:true},
			{name:"DIR"			,index:"11",  			jsonmap:"Action", 	 align:"center", hidden:false, width:50,  editable:false, fixed:true, resizable:false, sortable:true,
				formatter: function(cellvalue, options, rowObject) {
					return cellvalue == "ENTRANTE" ? "<span class='glyphicon glyphicon-arrow-down'></span>" :
						cellvalue == "SALIENTE" ? "<span class='glyphicon glyphicon-arrow-up'></span>" :
					cellvalue;
					}
			},
			{name:"ESTADO",		index:"12", 	jsonmap:"Saldo", 	align:"center"	, fixed:true, editable:false, resizable:false, width:80 ,sortable:true,hidden:false},
			{name:"CONVOCATORIA",index:"13", 	jsonmap:"Dispatch", align:"left"	, fixed:true, edittype:"select", editoptions:{ dataUrl:'selectDispatch'}, editrules:{required:true}, editable:true, resizable:false, width:310 ,sortable:true,hidden:false},	
			],
	   	rowNum:60,
	   	scrollOffset:50,
		multiselect: true,
		caption: null,
		forceFit: true,
		height:$(window).height() * 0.60,
		width: $(window).width() * 0.98,
		pager: "pagerArticulos",
		gridview: true,
		viewrecords: true,
		footerrow: false,
		closeAfterEdit:true,
		editurl:"updateSMS.do",
		jsonReader: { repeatitems : false, root:"rows" },
		loadComplete: function(data) {
		},
//		beforeSelectRow: function(id){
//			jQuery("#gridArticulos").setSelection (id, true);
//			return false;
//		},
		onSelectRow: function(id){
			$("#idsValesSel").val(id);
		},

		ondblClickRow: function(id){
			jQuery("#gridArticulos").jqGrid('setSelection',id,false);
			$('#btnVerFactura').trigger('click');
		},
		ajaxDelOptions: { contentType: 'application/json; charset=utf-8' },
			serializeDelData: function (postData) {
		        return JSON.stringify(postData);
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
	}).navGrid('#pagerArticulos',{edit:true,add:false,del:true}, null, null, deleteOptions);


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


	//--- Ver Factura----------------------------------------------
	$("#btnVerFactura").click(function(){

		try{
			var id = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
			if (id)	{
				var ret = jQuery("#gridArticulos").jqGrid('getRowData',id);
				verFactura(ret.IDDOCUMENTO + "_" + ret.NUMERO);
			}
		}catch(Exception){
			alert(Exception.message);
		}
		return false;
	});
});


function ingresarVale() {

	try {
		$("#dialogo_ingresar_sms").dialog({
			resizable: false,
			height:260,
			width:600,
			modal: true,
			open: function(event, ui){
				$('body').css('overflow','hidden');
				$('.ui-widget-overlay').css('width','100%');
			},
			close: function(event, ui) {
				$('body').css('overflow','auto');

				//Receteo los valores del formulario y las validaciones
				$("#nroDestino").val(null);
				$("#detalleIn").val(null);
				$("#frmLstSMS").validate().resetForm();

			},
			buttons: {
				"Aceptar": {
					class: 'btn btn-lg btn-primary btn-block',
					click: function() {
					if($("#frmLstSMS").validate().form()) {
						$.ajax({
						url: "ingresarSMS.do",
						data: $("#frmLstSMS").serialize(),
						type: $("#frmLstSMS").attr("method"),
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
									}
								}
							});
							$("#dialogo_resultados").dialog("open");

							if ( data.indexOf("div-error") == -1 ) {
								$("#dialogo_ingresar_sms").dialog("close");
								$('#gridVales').trigger("reloadGrid");

								//quito el scroll nuevamente para el dialogo que se sigue viendo
								$('body').css('overflow','hidden');
								$('.ui-widget-overlay').css('width','100%');
							}

							return data;
						},
						error: function(jqXHR, textStatus, errorThrown){
							alert("error = " + textStatus);
						}

						});
					}
					}
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
			});

			$("#dialogo_ingresar_sms").parent().appendTo($("#frmLstSMS"));
			$("#dialogo_ingresar_sms").dialog("open");

	}catch(Exception){
		alert(Exception.message);
	}
	return false;
}
