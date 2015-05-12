<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manutenção de Instrumentos</title>

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
		$('#instrumentos').dataTable({
			"bProcessing" : true,
			"bServerSide" : true,
			"ajax" : "instrumentos",
			"columns" : [ {
				"data" : "symbol"
			}, {
				"data" : "name"
			}, {
				"data" : "type"
			}, {
				"data" : "maturity"
			}, {
				"data" : "situation"
			} ]
		});
	});
</script>
</head>
<body>
	<H1>Manutenção de Instrumentos</H1>

	<table id="instrumentos" class="display" cellspacing="0" width="50%">
		<thead>
			<tr>
				<th>Símbolo</th>
				<th>Nome</th>
				<th>Tipo</th>
				<th>Vencimento</th>
				<th>Situacao</th>
			</tr>
		</thead>
	</table>

	<!-- Autocomplete -->
	<h2 class="demoHeaders">Incluir Instrumento</h2>
	<div>
		<form:form id="frmIncluir" action="instrumentoincluir" method="post">
			<table cellspacing="3px" width="50%">
				<tr>
					<td><form:label path="symbol">Símbolo</form:label></td>
					<td><form:input path="symbol" /></td>
				</tr>
				<tr>
					<td><form:label path="name">Nome</form:label></td>
					<td><form:input path="name" /></td>
				</tr>
				<tr>
					<td><form:label path="type">Tipo</form:label></td>
					<td><form:input path="type" id="autocomplete" /></td>
				</tr>
				<tr>
					<td colspan="2"><button id="button">Incluir</button></td>
				</tr>
			</table>

		</form:form>

		<form id="frmDash" action="home"></form>

		<br>
		<ul id="icons" class="ui-widget ui-helper-clearfix">
			<li class="ui-state-default ui-corner-all" title=".ui-icon-home"><span
				id="buttonDash" class="ui-icon ui-icon-home"></span></li>
		</ul>

	</div>
	<script src="js/jquery-ui.js"></script>
	<script>
		$("#button").click(function() {
			$("#frmIncluir").submit();
		});

		$("#buttonDash").click(function() {
			$("#frmDash").submit();
		});

		var availableTags = [ "Equity", "Future", ];
		$("#autocomplete").autocomplete({
			source : availableTags
		});

		// Hover states on the static widgets
		$("#dialog-link, #icons li").hover(function() {
			$(this).addClass("ui-state-hover");
		}, function() {
			$(this).removeClass("ui-state-hover");
		});
	</script>
</body>
</html>