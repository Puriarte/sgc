var primeraVez=true;

jQuery(document).ready(function(){

	//--- Mascaras ---------------------------------------------------------
	$.mask.masks = $.extend($.mask.masks,{
		importe:{ mask : '99.999999', type : 'reverse' },
		fecha:{ mask : '39-19-9999'  }
	});
	//--- Fin Mascaras ---------------------------------------------------------

	jQuery("#gridRecibo").jqGrid({
	   	url:'/deudores/deudores/lstRecibo',
	   	postData: {
	   		IdDoc: function() { return $("#IdDoc").val(); },
			primeraVez:primeraVez
	   	},
	   	mtype: 'POST',
		datatype: "json",
	   	colNames:['NRO.CLI','RECIBO','DOCU', 'NRO DOCU','VTO','PAGO','BONIF','F.MOV'],
	   	colModel:[
  			{name:"NRO.CLI",index:"1", key: true, jsonmap:"NroCli", align:"center",  sortable:false},
  			{name:"RECIBO",index:"2", key: true, jsonmap:"Recibo", align:"center",  sortable:false},
  			{name:"DOCU",index:"3", key: true, jsonmap:"Docu", align:"center",  sortable:false},
			{name:"NRO.DOCU",index:"4", key: true, jsonmap:"NroDocu", align:"center", fixed:true,resizable:false, sortable:false},
			{name:"VTO",index:"5", key: true, jsonmap:"Vto", align:"center", fixed:true,resizable:false, sortable:false},
			{name:"PAGO",index:"6", jsonmap:"Pago", align:"center", fixed:true, resizable:false, sortable:false},
			{name:"BONIF",index:"7", jsonmap:"Bonif", align:"center", fixed:true, resizable:false, sortable:false},
			{name:"F.MOV",index:"8", jsonmap:"FMov", align:"center", fixed:true, resizable:false, sortable:false}
	   	],
		multiselect: false,
		caption: null,
	   	rowNum:50,
		height:$(window).height() * 0.80,
		width: $(window).width() * 0.98,
		pager: "pagerRecibo",
		gridview: true,
		viewrecords: true,
		jsonReader: { repeatitems : false, root:"rows" },
		loadComplete: function(data) {
			$("#idsReciboSel").val(null);
		}
	}).navGrid('#pagerRecibo',{edit:false,add:false,del:false});

	//--- FIN - Filtros -----------------------------------------------------

});