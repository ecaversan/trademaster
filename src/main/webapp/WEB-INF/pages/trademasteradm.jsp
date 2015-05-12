<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/1.10.6/css/jquery.dataTables.css">

<style>
body {
	font: 75% "Trebuchet MS", sans-serif;
	margin: 50px;
}

.demoHeaders {
	margin-top: 2em;
}

#dialog-link {
	padding: .4em 1em .4em 20px;
	text-decoration: none;
	position: relative;
}

#dialog-link span.ui-icon {
	margin: 0 5px 0 0;
	position: absolute;
	left: .2em;
	top: 50%;
	margin-top: -8px;
}

#icons {
	margin: 0;
	padding: 0;
}

#icons li {
	margin: 2px;
	position: relative;
	padding: 4px 0;
	cursor: pointer;
	float: left;
	list-style: none;
}

#icons span.ui-icon {
	float: left;
	margin: 0 4px;
}

.fakewindowcontain .ui-widget-overlay {
	position: absolute;
}

select {
	width: 200px;
}
</style>
<!-- jQuery -->
<script type="text/javascript" charset="utf8"
	src="//code.jquery.com/jquery-1.10.2.min.js"></script>

<link href="js/jquery-ui.css" rel="stylesheet">

<!-- DataTables -->
<script type="text/javascript" charset="utf8"
	src="//cdn.datatables.net/1.10.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" charset="utf8">
	
	
	$(document).ready(function() {
		/*
		var jqxhr = $.ajax("carteira");

		// Set another completion function for the request above
		jqxhr.always(function() {
			var dataSet = jqxhr.responseText ;
			localStorage.setItem('datatable_todo', JSON.stringify(dataSet));
			//alert(JSON.parse( localStorage.getItem('datatable_todo') ));
			 $('#carteira').dataTable( {
			        data: JSON.parse( localStorage.getItem('datatable_todo') ),
			        columns: [ 
						{ data : "instrumento.symbol" }, 
						{ data : "instrumento.name" }, 
						{ data : "quantidade" }, 
						{ data : "preco_medio" }, 
						{ data : "preco_atual" } 
					]
			    } );   
		});

		*/

		

			
		$('#carteira').dataTable({
			"bProcessing" : true,
			"bServerSide" : true,
			"ajax" : "carteira",
			"columns" : [ {
				"data" : "instrumento.symbol"
			}, {
				"data" : "instrumento.name"
			}, {
				"data" : "quantidade"
			}, {
				"data" : "preco_medio"
			}, {
				"data" : "preco_atual"
			} ]
		});
		

	});
</script>

<title>Trade Master - Administration</title>
</head>
<body>
	<H1>Projeto TradeMaster</H1>

	Usuário: ${user.username}

	<table id="carteira" class="display" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th>Ação</th>
				<th>Nome</th>
				<th>Qtd.</th>
				<th>Preço Médio</th>
				<th>Valor Atual</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<th>Ação</th>
				<th>Nome</th>
				<th>Qtd.</th>
				<th>Preço Médio</th>
				<th>Valor Atual</th>
			</tr>
		</tfoot>
	</table>

	<!-- Button -->
	<h2 class="demoHeaders">Button</h2>
	<button id="button">A button element</button>
	<form id="frmHome" style="margin-top: 1em;" action="home">
		<div id="radioset">
			<input type="radio" id="radio1" name="radio">
			<label for="radio1">Choice 1</label> 
			<input type="radio" id="radio2" name="radio" checked="checked">
			<label for="radio2">Choice 2</label>
			<input type="radio" id="radio3" name="radio">
			<label	for="radio3">Choice 3</label>
		</div>
	</form>

	<!-- script src="js/external/jquery/jquery.js"></script-->
	<script src="js/jquery-ui.js"></script>
	<script>
		$("#button").button();
		$("#radioset").buttonset();

		$("#button").click(function() {
			$("#frmHome").submit();
		});
	</script>
</body>
</html>