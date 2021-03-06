var contArtSel= "";
var primeraVez=true;
var documento_cdo=1;
var documento_fac=2;
var documento_nc=3;
var documento_fav=11;
var documento_pag=21;
var urlReload = "lstDispatch";
var urlReloadSubQuery = "lstAssignment";

var formName = "#frmLstDispatch";
var counter = 0;

var dispatchLocalJSON;

Number.prototype.format = function(){
   return this.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
};

jQuery(document).ready(function(){

	//--- Botonoes ---------------------------------------------------------
	$(function(){
	});
	//--- Fin Botonoes ---------------------------------------------------------

	//--- Mascaras ---------------------------------------------------------
	/*$.mask.masks = $.extend($.mask.masks,{
		importe:{ mask : '99.999999', type : 'reverse' },
		fecha:{ mask : '19-39-9999'  }
	});
	*/
	//--- Fin Mascaras ---------------------------------------------------------

	//--- Inputs ---------------------------------------------------------
 /* 	$(function(){
    	$("input:text").setMask();
  	});
  	//--- Fin Inputs ---------------------------------------------------------
  //--- Validacion Formulario -----------------------------------------------------
/*	$.validator.addMethod(
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
*/

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
				dispatchStatus: function() {
					return $("#dispatchStatus").val(); 
					},
				priority: function() { return $("#priority").val(); },
			primeraVez:primeraVez
	   	},
	   	loadonce:false,
	   	mtype: 'GET',
	   	datatype: "local", // se usa local para que no cargue registros en el primer acceso a la grilla
	   	colNames:['POS','ID', 'CODIGO', 'LUGAR','FECHA EVENTO','HASTA','NRO.DOCUMENTO','TEXTO','ESTADO'],
	   	colModel:[
   			{name:"POS"			,index:"1", key: false, jsonmap:"Pos", 			align:"center", hidden:true, width:10, sortable:false},
   			{name:'ID'			,index:'2',	key: true, 	jsonmap:"Id", 			width:55,		editable:true,editoptions:{readonly:true,size:10},hidden:true},
			{name:"CODIGO"		,index:"3", key: false, jsonmap:"DispatchCode",	align:"left", 	fixed:true,  width:50 ,resizable:false, sortable:true,hidden:false},
			{name:"LUGAR"		,index:"4", key: false, jsonmap:"Place",		align:"left", 	fixed:true,  width:250 ,resizable:false, sortable:true,hidden:false},
			{name:"FECHA EVENTO",index:"5", key: false, jsonmap:"FechaEnvio",	align:"center", fixed:true, resizable:false,  width:80  ,sortable:true,hidden:false},
			{name:"HASTA"		,index:"6", key: false, jsonmap:"FechaHasta",	align:"center", fixed:true, resizable:false,  width:80  ,sortable:true,hidden:false},
			{name:"NRO.DOCUMENTO",index:"7",key: false, jsonmap:"Texto", 		align:"left", 	fixed:true,  width:160 ,resizable:false, sortable:true,hidden:true},
			{name:"TEXTO"		,index:"8", key: false, jsonmap:"Name", 		align:"center", editable: true,  fixed:true, width:350, resizable:false, sortable:true,hidden:false},
			{name:"ESTADO"		,index:"9", key: false, jsonmap:"DispatchStatus",align:"center",  editable: true,  fixed:true, width:60, resizable:false, sortable:true,hidden:false},
			],
	   	rowNum:60,
	   	scrollOffset:50,
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
		editurl:"updateDispatch.do",
		jsonReader: { repeatitems : false, root:"rows" },
		loadComplete: function(data) {
			dispatchLocalJSON = data.rows; // save original JSON data
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
		},
		subGrid: true,
        subGridRowExpanded: function (subgridId, rowid) {
            var subgridTableId = subgridId + "_t";
            var empList;

            dispatchLocalJSON.forEach(function(entry) {
                if (entry.Id==rowid) empList  = entry.Assignments;
            });
            
            $("#" + subgridId).html("<table id='" + subgridTableId + "'></table>");
            $("#" + subgridTableId).jqGrid({
                datatype: "local",
                data: empList,
                colNames: ["Id", "NUMERO", "NOMBRE","CATEGORIA","ESTADO","FECHA ENVIADO"],
                colModel: [
                  {name: "Id",  			hidden:true,  width: 10, key: true},
                  {name: "Movil", 			hidden:false, width: 80},
                  {name: "Person", 			hidden:false, width: 160},
                  {name: "PersonCategory", 	hidden:false, width: 160},
                  {name: "Status", 			hidden:false, width: 80},
                  {name: "AssignmentDate", 	hidden:false, width: 120}
                ],
                height: "100%",
                rowNum: 100,
                sortname: "Person",
                idPrefix: "s_" + rowid + "_", 
                loadComplete: function(data) {
                	var myGrid = jQuery("#" +subgridTableId);
                	var ids = myGrid.jqGrid('getDataIDs');
                	for (var i = 0; i < ids.length; i++) {
                	    var rowid=ids[i];
                	    if (myGrid.jqGrid('getCell',rowid,'Status') == "Convocado"){
                            $("#" + subgridTableId).setCell(rowid,"Movil","",{"background-color":"#A9BCF5"});
                            $("#" + subgridTableId).setCell(rowid,"Person","",{"background-color":"#A9BCF5"});
                            $("#" + subgridTableId).setCell(rowid,"PersonCategory","",{"background-color":"#A9BCF5"});
                            $("#" + subgridTableId).setCell(rowid,"Status","",{"background-color":"#A9BCF5"});
                            $("#" + subgridTableId).setCell(rowid,"AssignmentDate","",{"background-color":"#A9BCF5"});
                	    }
                	    if (myGrid.jqGrid('getCell',rowid,'Status') ==  "Aceptado"){
                            $("#" + subgridTableId).setCell(rowid,"Movil","",{"background-color":"#CEF6E3"});
                            $("#" + subgridTableId).setCell(rowid,"Person","",{"background-color":"#CEF6E3"});
                            $("#" + subgridTableId).setCell(rowid,"PersonCategory","",{"background-color":"#CEF6E3"});
                            $("#" + subgridTableId).setCell(rowid,"Status","",{"background-color":"#CEF6E3"});
                            $("#" + subgridTableId).setCell(rowid,"AssignmentDate","",{"background-color":"#CEF6E3"});
                        }
                	    if (myGrid.jqGrid('getCell',rowid,'Status') ==  "Rechazado"){
                            $("#" + subgridTableId).setCell(rowid,"Movil","",{"background-color":"#FA5858"});
                            $("#" + subgridTableId).setCell(rowid,"Person","",{"background-color":"#FA5858"});
                            $("#" + subgridTableId).setCell(rowid,"PersonCategory","",{"background-color":"#FA5858"});
                            $("#" + subgridTableId).setCell(rowid,"Status","",{"background-color":"#FA5858"});
                            $("#" + subgridTableId).setCell(rowid,"AssignmentDate","",{"background-color":"#FA5858"});
                        }
                	}
        		}
            });
        }
	}).navGrid('#pagerArticulos',{edit:false,add:false,del:false,search: false});
/*	.navButtonAdd('#pagerArticulos', {
        caption: "",
        buttonicon: "ui-icon-pencil",
        onClickButton: function () {
        	try{
    			ingresarListaSMS();
    		}catch(Exception){
    			alert(Exception.message);
    		}
        },
        position: "last"
    });*/;
    
    $("#export").click(function(){ 
    	var id= jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
    	var len=jQuery("#gridArticulos_" + id + "_t").jqGrid('getDataIDs').length;
    	
    	if (len>0){
        	$("#gridArticulos_" + id + "_t").jqGrid("exportToExcel",{
    			includeLabels : true,
    			includeGroupHeader : true,
    			includeFooter: true,
    			fileName : "convocatoria.xlsx",
    			maxlength : 40 // maxlength for visible string data
    		});
    	}else{
    		alert("Se debe seleccionar y expandir una convocatoria.");
    	}
    	
    	/*
    	$("#gridArticulos").jqGrid("exportToExcel",{
			includeLabels : true,
			includeGroupHeader : true,
			includeFooter: true,
			fileName : "jqGridExport.xlsx",
			maxlength : 40 // maxlength for visible string data 
		});*/
		return false;
	});
	
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
				ingresarListaSMS();
			}else alert("Seleccione una convocator");
			
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
					 $('#btnEnviar').click();
				}else{
					$.ajax({
						url: "modifyCategoryDispatch.do",
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
		title: "Modificar Convocatoria",
		modal: true,
		resizable: true,
		height:530,
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
		innerDiv.childNodes[1].childNodes[1].required=true;	

		innerDiv.childNodes[3].childNodes[1].id="personCategory_"+counter;
		innerDiv.childNodes[3].childNodes[1].name="personCategory_"+counter;

		innerDiv.childNodes[5].childNodes[1].id="assignmentStatus_"+counter;
		innerDiv.childNodes[5].childNodes[1].name="assignmentStatus_"+counter;
	
		innerDiv.childNodes[7].id="assignment_"+counter;
		innerDiv.childNodes[7].name="assignment_"+counter;
		innerDiv.childNodes[7].value=0;

		document.getElementById("assignmentRowContainer").appendChild(innerDiv);

	}catch(E){
		
	}
}