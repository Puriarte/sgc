jQuery(document).ready(function(){

	//Para acutalizar la grilla
	$("#lk_actualizar").click(function(){
		try {
			primeraVez=false;
		    $("#jqGrid").setGridParam({datatype:'json', page:1}).trigger('reloadGrid');
		    return false;
		} catch(Exception){
		  alert(Exception.message);
		}
	});
		
	$("#jqGrid").jqGrid({
		url: 'lstGridReport?report=1',
		postData: {
			fechaDesde: function() {return $("#fechaDesde").val();},
  			fechaHasta: function() { return $("#fechaHasta").val(); },
  			reporte: "1"
		},
		styleUI : 'Bootstrap',
		datatype: "json",
		colModel: [
		           { label: 'Nombre', name: 'Name', key: true, width: 175 },
		           { label: 'Número', name: 'Phone', width: 150 , sorttype:'number'  },
		           { label: 'Convocatorias', name: 'Convoc', width: 110 , sorttype:'number'  },
		           { label: 'Aceptadas', name: 'Aceptada', width: 110 , sorttype:'number'  },
		           { label:'Rechazadas', name: 'Rechazada', width: 110 , sorttype:'number'  },
		           { label:'Canceladas', name: 'Cancelada', width: 110 , sorttype:'number'  },
		           { label:'Aceptación', name: 'Aceptacion', width: 110, formatter: 'currency', formatoptions: {  suffix: ' %' }, sorttype:'number'  }
          ],
          jsonReader: {
        	  repeatitems : false,
        	  id: "0"
          },
          loadonce: true,
          viewrecords: true,
          height: 400,
          rowNum: 10000
      });	
	  
	
	$("#export").on("click", function(){
		$("#jqGrid").jqGrid("exportToExcel",{
			includeLabels : true,
			includeGroupHeader : true,
			includeFooter: true,
			fileName : "jqGridExport.xlsx",
			maxlength : 40 // maxlength for visible string data 
		});
		return false;
	})
	
	jQuery("#jsonmap").jqGrid('jqGrid','#jqGridPager',{edit:false,add:false,del:false});
	
});
