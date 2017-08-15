$(document).ready(function () {
	console.log("JavaScript Run Test");
	
	$('tbody').on('click', 'tr td button.row_delete', function () {
        var id = $(this).parent().parent().data("id");
        
        $('#delete-id').val(id);
        $('#delete-element').modal('show');
    });

	$('tbody').on('click', 'tr td button.row_edit', function () {
		var id = $(this).parent().parent().data("id");
		
		 $.getJSON('element/' + id, function(obj) { 
			$('#edit-id').val(obj.id);
			$('#edit_name').val(obj.name);
			$('#edit_kiekis').val(obj.kiekis);
			$('#edit_spalva').val(obj.spalva);

		    $('#edit-element').modal('show');
		 }).fail(function() {
			alert('Edit Error')
		 });
    });
	
	$('.row_add').on('click', function () {
		$('#add-element').modal('show');
	});
	
	// Save edited row
	  $("#edit-form").on("submit", function(event) {
	    event.preventDefault();
	    var spalva_id = $('#edit_spalva option:selected').data("id");

	    var data_raw = $(this).serializeArray();
	    data_raw.splice(2, 1);

	    data_raw.push({ 'name': 'edit_spalva_id', 'value': spalva_id });

	    console.log(data_raw);
	    
	    $.ajax({
	    	type: "PUT",
	    	url: "element/" + $('#edit-id').val(),
	    	data: data_raw,
	    	success: function(obj) {
	    		console.log("put", obj);
		  		var tr = $('table tr[data-id=' + obj.id + ']');
		 		$('td:eq(0)', tr).html(obj.name);
		 		$('td:eq(1)', tr).html(obj.kiekis);
		 		$('td:eq(2)', tr).html(obj.spalva);
			    $('#edit-element').modal('hide');
			}
	    }).fail(function() {
	  		alert('Save Edit Error');
	    });
	  });
	  
	// Save add row
	  $("#add-form").on("submit", function(event) {
		  event.preventDefault();
		  var spalva_id = $('#add_spalva option:selected').data("id");
		  
		  var data_raw = $(this).serializeArray();
		  data_raw.splice(2, 1);
		  data_raw.push({ 'name': 'add_spalva_id', 'value': spalva_id });
		  
		  $.post("element", data_raw, function(obj) {
		      
			  $('table tbody').append('<tr data-id="'+ obj.id +'"><td>'+obj.name+'</td><td>'+obj.kiekis+'</td><td>'+obj.spalva+
			  '</td><td><button type="button" class="btn btn-default btn-xs row_edit"><span class="glyphicon glyphicon glyphicon-pencil"></span></button> <button type="button" class="btn btn-default btn-xs row_delete"><span class="glyphicon glyphicon-trash"></span></button></td></tr>');
			  
			  console.log(obj);
		      $('#add-element').modal('hide');
		  }).fail(function() {
			  alert('Add Error');
		  });
	  });
	  
	 //Delete row
	  $("#delete-form").on("submit", function(event) {
		  event.preventDefault();
		  
		  var id =  $('#delete-id').val();
		  
		  $.ajax({
			type: "DELETE",
			url: "element/" + id,
			success: function(obj) {
				console.log("delete", obj);

			    $('#delete-element').modal('hide'); 
				
		        $('table tr[data-id="' + id + '"]').fadeOut("fast", function(){
		        	$('table tr[data-id="' + id + '"]').remove();
		        });
			}
		  }).fail(function() {
			  alert('Delete Edit Error');
		  });
	  });
});