(function (global, $, Rx) {

	// Buscar mensajes nuevos 
  function searchWikipedia (term) {
    return $.ajax({
      url: 'notificaciones',
//      url: 'data.json',
      dataType: 'json'
    }).promise();
  }

    
  function main() {
    var $input = $('#textInput'),
        $results = $('#results');
			
	const myInterval = Rx.Observable.interval(5000);		

	var searcher = myInterval.flatMapLatest(searchWikipedia);
	
    searcher.subscribe(
      function (data) {
    	  data.forEach( function (v) { 
    		  $.notify({
      				title: '<strong>Nuevo SMS</strong>' ,
      				message: v.recipient + ' - ' +  v.text
    		  });
    	  });
      },
      function (error) {
        $results
          .empty()
          .append($('<li>'))
          .text('Error:' + error);
      });

  }
  $(main);

}(window, jQuery, Rx));