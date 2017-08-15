<%@page import="lt.rg.example.controler.Spalva"%>
<%@page import="lt.rg.example.controler.Element"%>
<%@page import="java.util.LinkedList"%>
<%@page import="lt.rg.example.controler.DataJSP"%> 
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="text/html; charset=UTF-8; IE=edge">
<meta name="viewport" content="width = device-width, initial-scale = 1">
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>REST Example Client</title>
</head>
<body>
<div class="container">
<div class="row my-padd">
	<div class="col-lg-12">
		<h3>REST Example Client</h3>
		<div class="pull-right">
			<button type="button" class="btn btn-default btn-xs row_add"><span class="glyphicon glyphicon-edit"></span></button>
		</div>
	</div>
</div>
<div class="row top12">
	<div class="col-lg-12">
		<table class="table table-bordered table-hover">
			<thead>
			<tr>
		    	<th>Name</th><th>Kiekis</th><th>Spalva</th><th>Edit</th>
		 	</tr>
		 	</thead>
		 	<tbody>
			<%
			DataJSP data = new DataJSP();
			LinkedList<Element> list = data.getTable();
			LinkedList<Spalva> spalList = data.getSpalvaTable();
			
			for(int i = 0; i < list.size(); i++){
			%>
			<tr data-id="<%= list.get(i).getId() %>">
				<td><%= list.get(i).getName() %></td>
				<td><%= list.get(i).getKiekis() %></td>
				<td><%= list.get(i).getSpalva() %></td>
				<td>
					<button type="button" class="btn btn-default btn-xs row_edit"><span class="glyphicon glyphicon glyphicon-pencil"></span></button>
					<button type="button" class="btn btn-default btn-xs row_delete"><span class="glyphicon glyphicon-trash"></span></button>
				</td>
			</tr>
			<% } %>
			</tbody>
		</table>
	</div>
</div>
  
<!-- Edit -->
<div class="modal fade" id="edit-element" tabindex="-1" role="dialog" aria-labelledby="edit-element-label">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form class="form-horizontal" id="edit-form">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="edit-element-label">Edit</h4>
  		</div>
  		<div class="modal-body">
			<input type="hidden" id="edit-id" value="" class="hidden">
		  	<div class="form-group">
		    	<label for="edit_name" class="col-sm-2 control-label">Name</label>
		    	<div class="col-sm-10">
		      		<input type="text" class="form-control" id="edit_name" name="edit_name" placeholder="Name" required>
		    	</div>
		  	</div>
		  	<div class="form-group">
		    	<label for="edit_kiekis" class="col-sm-2 control-label">Kiekis</label>
		    	<div class="col-sm-10">
		      		<input type="number" class="form-control" id="edit_kiekis" name="edit_kiekis" placeholder="Kiekis" required>
		    	</div>
		  	</div>
            <div class="form-group">
				<label for="edit_spalva" class="col-sm-2 control-label">Spalva</label>
					<div class="col-sm-10">
                  		<select class="form-control" id="edit_spalva" name="edit_spalva">
							<% 
							for(int i = 0; i < spalList.size(); i++){ %>
								<option data-id="<%= spalList.get(i).getId() %>"><%= spalList.get(i).getName() %></option>
							<% } %>
                 		</select>
				    </div>
			</div>
		</div>
		<div class="modal-footer">
			 <button type="submit" class="btn btn-primary">Save</button>
		</div>
	  </form>
    </div>
  </div>
</div>

<!-- Add -->
<div class="modal fade" id="add-element" tabindex="-1" role="dialog" aria-labelledby="add-element-label">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form class="form-horizontal" id="add-form">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="add-element-label">Add</h4>
  		</div>
  		<div class="modal-body">
			<input type="hidden" id="add-id" value="" class="hidden">
		  	<div class="form-group">
		    	<label for="add_name" class="col-sm-2 control-label">Name</label>
		    	<div class="col-sm-10">
		      		<input type="text" class="form-control" id="add_name" name="add_name" placeholder="Name" required>
		    	</div>
		  	</div>
		  	<div class="form-group">
		    	<label for="add_kiekis" class="col-sm-2 control-label">Kiekis</label>
		    	<div class="col-sm-10">
		      		<input type="number" class="form-control" id="add_kiekis" name="add_kiekis" placeholder="Kiekis" required>
		    	</div>
		  	</div>
            <div class="form-group">
				<label for="add_spalva" class="col-sm-2 control-label">Spalva</label>
					<div class="col-sm-10">
                  		<select class="form-control" id="add_spalva" name="add_spalva">
						<% 
						for(int i = 0; i < spalList.size(); i++){ %>
							<option data-id="<%= spalList.get(i).getId() %>"><%= spalList.get(i).getName() %></option>
						<% } %>
                 		</select>
				    </div>
			</div>
		</div>
		<div class="modal-footer">
			 <button type="submit" class="btn btn-primary">Create</button>
		</div>
	  </form>
    </div>
  </div>
</div>

<!-- Delete -->
<div class="modal fade" id="delete-element" tabindex="-1" role="dialog" aria-labelledby="delete-element-label">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
	    <form class="form-horizontal" id="delete-form">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="delete-element-label">Add</h4>
	 	  </div>
	 	  <div class="modal-body">
	 	  	<input type="hidden" id="delete-id" value="" class="hidden">
			<P>Are you sure you want to delete?</p>
	      </div>
		  <div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Delete</button>
		  </div>
	  </form>
    </div>
  </div>
</div>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="js/script.js"></script>
</body>
</html>