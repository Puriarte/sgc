var contArtSel= "";
var primeraVez=true;
var documento_cdo=1;
var documento_fac=2;
var documento_nc=3;
var documento_fav=11;
var documento_pag=21;
var urlReload = "lstGridPlace";
var formName = "#frmLstPerson";

Number.prototype.format = function(){
   return this.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
};

var editOptions={
		top: 140, left: 250,
		width: 500,
		height:200,
		recreateForm:true,
		closeOnEscape: true,
		closeAfterAdd:true,
		closeAfterEdit:true,
		modal: true};

var addOptions={
		top: 140, left: 250,
		width: 500,
		height:200,
		recreateForm:true,
		closeOnEscape: true,
		closeAfterAdd:true,
		closeAfterEdit:true,
		modal: true};

jQuery(document).ready(function(){

	jQuery("#gridArticulos").jqGrid({
	   	url:urlReload,
	   	postData: {
			primeraVez:primeraVez
	   	},
	   	loadonce:false,
	   	mtype: 'GET',
	   	datatype: "local", // se usa local para que no cargue registros en el primer acceso a la grilla
	   	colNames:['POS','ID','NOMBRE','DIRECCION', 'TELEFONO'],
	   	colModel:[
   			{name:"POS",index:"1", key: false, jsonmap:"Pos", align:"center", hidden:true, width:10, sortable:false},
   			{name:'ID',index:'2',key: true, jsonmap:"Id", width:55,editable:true,editoptions:{readonly:true,size:10},hidden:true},
			{name:"NOMBRE",index:"3", editable: true,  key: false, jsonmap:"Name", align:"center", fixed:true, width:250, resizable:false, sortable:true,hidden:false},
			{name:"DIRECCION",index:"4", editable: true,  key: false, jsonmap:"Address", align:"center", fixed:true, width:250, resizable:false, sortable:true,hidden:false},
			{name:"TELEFONO",index:"5", editable: true,  key: false, jsonmap:"Phone", align:"center", fixed:true, width:150, resizable:false, sortable:true,hidden:false},
			],
	   	rowNum:60,
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
		editurl:"updatePlace.do",
		jsonReader: { repeatitems : false, root:"rows" },
		onSelectRow: function(id){
			$("#idsValesSel").val(id);
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
	}).navGrid('#pagerArticulos',{edit:true,add:true,del:false, search: false}, editOptions, addOptions);

	
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
});