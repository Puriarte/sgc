var contArtSel= "";
var primeraVez=true;
var documento_cdo=1;
var documento_fac=2;
var documento_nc=3;
var documento_fav=11;
var documento_pag=21;
var urlReload = "lstGridPersonCategory";

//var urlReload = "categorias.json";
var formName = "#frmLstPerson";

Number.prototype.format = function(){
   return this.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
};

jQuery(document).ready(function(){

	//--- FIN Validacion Formulario -----------------------------------------------------
	jQuery("#gridArticulos").jqGrid({
	   	url:urlReload,
	   	postData: {
	   		categoryStatus: function() { return $("#categoryStatus").val(); },
			primeraVez:primeraVez
	   	},
	   	loadonce:false,
	   	mtype: 'GET',
	   	datatype: "local", // se usa local para que no cargue registros en el primer acceso a la grilla
	   	colNames:['POS','ID','NOMBRE','HABILITADA'],
	   	colModel:[
   			{name:"POS",index:"1", key: false, jsonmap:"Pos", align:"center", hidden:true, width:10, sortable:false},
   			{name:'ID',index:'2',key: true, jsonmap:"Id", width:55,editable:true,editoptions:{readonly:true,size:10},hidden:true},
			{name:"NOMBRE",index:"3", editable: true,  key: false, jsonmap:"Name", align:"center", fixed:true, width:150, resizable:false, sortable:true,hidden:false},
			{name:"HABILITADA",index:"5", editable: true,  key: false, jsonmap:"Deleted", align:"center", fixed:true, width:150, resizable:false, sortable:true,hidden:false, formatter:'select', formatoptions : {value:"true:No;false:Si"}},
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
		editurl:"updateCategory.do",
		autoencode:true,
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
	}).navGrid('#pagerArticulos',{edit:false,add:false,del:false, search: false});

	$("#bedata").click(function(){
		var gr = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
		if( gr != null ) jQuery("#gridArticulos").jqGrid('editGridRow',gr,{height:280,reloadAfterSubmit:true, closeAfterEdit: true});
		else alert("Seleccione una categor�a");
	});


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
	
	$("#lk_add_category").click(function(){
		try{
	    	addCategory();
		}catch(Exception){
			alert(Exception.message);
		}
		return false;
	});
	
	//Para acutalizar la grilla
	$("#lk_edit_category").click(function(){
		try{
			var id = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
			if (id)	{
				modificarCategory();
			}else alert("Seleccione una categoria");
			
		}catch(Exception){
			alert(Exception.message);
		}
		return false;
	});
	
	function modificarCategory(){
		try{
			$("#accion").val("cargar");
			var personId = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
			var cerrarBtns = {
	                "Aceptar": function () {
						$("#lk_actualizar").click();
	                    $(this).dialog("close");
	                }
	            };
			
			refDialogIframe = $('<iframe id="ifModCliente" frameborder="0" marginwidth="0px" marginheight="0px" style="overflow-y:hidden; overflow-x:scroll;" src="updateCategory.do?accion=load&ID=' + personId + '"/>').dialog({
				resizable: true,
				width:420,
				height:160,
				modal: true,
				title: "Modificar Categoria",
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
						refDialogIframe.dialog("option",'buttons',cerrarBtns);
						$( this ).height( 140 ).css({cursor: "auto"});
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
					}
				}
			}).width(400).height(140);
		}catch(Exception){
			alert(Exception.message);
		}
		return false;
	}

	
	function addCategory(){
		try{
			$("#accion").val("cargar");
			var personId = jQuery("#gridArticulos").jqGrid('getGridParam','selrow');
			var cerrarBtns = {
	                "Aceptar": function () {
						$("#lk_actualizar").click();
	                    $(this).dialog("close");
	                }
	            };
			refDialogIframe = $('<iframe id="ifModCliente" frameborder="0" marginwidth="0px" marginheight="0px" src="updateCategory.do?accion=load&ID=' + '"/>').dialog({
				resizable: true,
				width:420,
				height:160,
				modal: true,
				title: "Agregar Categoria",
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
						refDialogIframe.dialog("option",'buttons',cerrarBtns);
						$( this ).height( 140 ).css({cursor: "auto"});
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
					}
				}
			}).width(400).height(140);
		}catch(Exception){
			alert(Exception.message);
		}
		return false;
	}
});

function removeOptions(selectbox)
{
    var i;
    for(i=selectbox.options.length-1;i>=0;i--)
    {
        selectbox.remove(i);
    }
}