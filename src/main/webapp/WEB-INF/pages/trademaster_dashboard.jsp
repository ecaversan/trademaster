<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trade Master - Dashboard</title>

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

<!-- Format Number -->
<script type="text/javascript" charset="utf8"
	src="js/NumberFormat154.js"></script>



<link href="js/jquery-ui.css" rel="stylesheet">

<!-- DataTables -->
<script type="text/javascript" charset="utf8"
	src="//cdn.datatables.net/1.10.6/js/jquery.dataTables.js"></script>

	<script type="text/javascript" charset="utf8"
	src="//cdn.datatables.net/tabletools/2.2.4/js/dataTables.tableTools.min.js"></script>
		<!-- 
	<script type="text/javascript" charset="utf8" src="js/dataTables.editor.min.js"></script>
	 -->

<script type="text/javascript" charset="utf8">
	//var editor; // use a global for the submit and return data rendering in the examples
	$(document)
			.ready(
					function() {

						/* editor = new $.fn.dataTable.Editor({
							"ajax" : "ordens",
							"table" : "#ordens",
							"fields" : [ {
								"label" : "Tipo:",
								"name" : "tipo"
							}, {
								"label" : "Instrumento:",
								"name" : "instrumento"
							}, {
								"label" : "Quantidade:",
								"name" : "quantidade"
							}, {
								"label" : "Preco:",
								"name" : "preco"
							}, {
								"label" : "vencimentoString:",
								"name" : "vencimentoString",
								"type" : "date"
							}, {
								"label" : "Status:",
								"name" : "status"
							} ]
						}); */

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
								"data" : "preco_medio",
								render : $.fn.dataTable.render
								.number(',', '.', 2,
										'R$ ')
							}, {
								"data" : "preco_atual",
								render : $.fn.dataTable.render
								.number(',', '.', 2,
										'R$ ')
							} ]
						});

						$
								.getJSON("instrumentosJSON")
								.done(
										function(data) {
											var $ativos = $('#selectativo');
											var output = '';
											$ativos
													.append('<option selected="selected">-- selecione --</option>');
											$ativos
													.append(function() {
														$
																.each(
																		data,
																		function(
																				key,
																				val) {
																			output += '<option>'
																					+ val.symbol
																					+ '</option>';
																		});
														return output;
													});

											var $ativosbook = $('#selectativobook');
											var outputbook = '';
											$ativosbook
													.append('<option selected="selected">-- selecione --</option>');
											$ativosbook
													.append(function() {
														$
																.each(
																		data,
																		function(
																				key,
																				val) {
																			outputbook += '<option>'
																					+ val.symbol
																					+ '</option>';
																		});
														return outputbook;
													});

										});

						$('#ordens').dataTable(
								{
									"bProcessing" : true,
									"bServerSide" : true,
									"ajax" : "ordens",
									"columns" : [
											{
												"data" : null,
												"defaultContent" : '',
												"orderable" : false
											},
											{
												"data" : "tipo"
											},
											{
												"data" : "instrumento.symbol"
											},
											{
												"data" : "quantidade"
											},
											{
												"data" : "preco",
												render : $.fn.dataTable.render
														.number(',', '.', 2,
																'R$ ')
											}, {
												"data" : 'vencimentoString'
											}, {
												"data" : "status"
											} ]/* ,
									order : [ 1, 'asc' ],
									tableTools : {
										sRowSelect : "os",
										sRowSelector : 'td:first-child',
										aButtons : [ {
											sExtends : "editor_create",
											editor : editor
										}, {
											sExtends : "editor_edit",
											editor : editor
										}, {
											sExtends : "editor_remove",
											editor : editor
										} ] 
									}*/
								});

						// Activate an inline edit on click of a table cell
						/* $('#ordens').on('click', 'tbody td:not(:first-child)',
								function(e) {
									editor.inline(this);
								});

						$('#ordens').DataTable(
								{
									dom : "Tfrtip",
									ajax : "ordens",
									columns : [
											{
												data : null,
												defaultContent : '',
												orderable : false
											},
											{
												data : "first_name"
											},
											{
												data : "last_name"
											},
											{
												data : "position"
											},
											{
												data : "office"
											},
											{
												data : "start_date"
											},
											{
												data : "salary",
												render : $.fn.dataTable.render
														.number(',', '.', 0,
																'$')
											} ],
									order : [ 1, 'asc' ],
									tableTools : {
										sRowSelect : "os",
										sRowSelector : 'td:first-child',
										aButtons : [ {
											sExtends : "editor_create",
											editor : editor
										}, {
											sExtends : "editor_edit",
											editor : editor
										}, {
											sExtends : "editor_remove",
											editor : editor
										} ]
									}
								}); */

						$('#cotacoes').dataTable({
							"bProcessing" : true,
							"bServerSide" : true,
							"ajax" : "cotacaoconsultar",
							"columns" : [ {
								"data" : "instrumento.symbol"
							}, {
								"data" : "ultimoPreco",
								"sType" : "numeric",
								render : $.fn.dataTable.render
								.number(',', '.', 2,
										'R$ ')
							}, {
								"data" : "precoAbertura",
								render : $.fn.dataTable.render
								.number(',', '.', 2,
										'R$ ')
							}, {
								"data" : "precoFechamento",
								render : $.fn.dataTable.render
								.number(',', '.', 2,
										'R$ ')
							}, {
								"data" : 'precoMaximo',
								render : $.fn.dataTable.render
								.number(',', '.', 2,
										'R$ ')
							}, {
								"data" : "precoMinimo",
								render : $.fn.dataTable.render
								.number(',', '.', 2,
										'R$ ')
							}, {
								"data" : "qtdNegocios"
							}, {
								"data" : "oscilacao",
								render : $.fn.dataTable.render
								.number(',', '.', 2,
										'')
							} ]
						});

						function RenderDecimalNumber(oObj) {
							var num = new NumberFormat();
							num.setInputDecimal('.');
							num.setNumber(oObj.data);
							num.setPlaces(oObj.decimalPlaces, true);
							num.setCurrency(false);
							num.setNegativeFormat(num.LEFT_DASH);
							num.setSeparators(true, oObj.decimalSeparator,
									oObj.thousandSeparator);
							return num.toFormatted();
						}

					});
</script>
<!-- Google Charts -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	google.load("visualization", "1", {
		packages : [ "corechart" ]
	});
	google.setOnLoadCallback(drawChart);
	function drawChart() {

		/* var jsonData = $.ajax({
		      url: "instrumentosgroup",
		      dataType:"json",
		      async: false
		      }).responseText;
		 */

		var data = new google.visualization.DataTable({
			cols : [ {
				id : 'task',
				label : 'Task',
				type : 'string'
			}, {
				id : 'hours',
				label : 'Hours per Day',
				type : 'number'
			} ],
			rows : [ {
				c : [ {
					v : 'Work'
				}, {
					v : 11
				} ]
			}, {
				c : [ {
					v : 'Eat'
				}, {
					v : 2
				} ]
			}, {
				c : [ {
					v : 'Commute'
				}, {
					v : 2
				} ]
			}, {
				c : [ {
					v : 'Watch TV'
				}, {
					v : 2
				} ]
			}, {
				c : [ {
					v : 'Sleep'
				}, {
					v : 7,
					f : '7.000'
				} ]
			} ]
		}, 0.6);

		//var data = google.visualization.arrayToDataTable([
		//		[ 'Task', 'Hours per Day' ], [ 'Work', 11 ], [ 'Eat', 2 ],
		//		[ 'Commute', 2 ], [ 'Watch TV', 2 ], [ 'Sleep', 7 ] ]);

		var options = {
			title : 'Carteira'
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('piechart'));

		chart.draw(data, options);
	}
</script>
</head>
<body>


	<table id="login" cellspacing="0" width="100%">
		<tr align="right">
			<td width="90%" align="right">Usuário: ${username}&nbsp;</td>
			<td align="right">
				<ul id="icons" class="ui-widget ui-helper-clearfix">
					<li class="ui-state-default ui-corner-all" title="LogOut"><span
						id="buttonLogOut" class="ui-icon ui-icon-power"></span></li>
				</ul>
			</td>
		</tr>
	</table>

	<!-- Mensagens de alerta do sistema -->
	<c:if test="${bError}">
		<div class="ui-widget">
			<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin-right: .3em;"></span> <strong>Alert:</strong>
					<c:out value="${error_desc}" />
				</p>
			</div>
		</div>
	</c:if>

	<H1>Trade Master</H1>

	<!-- Tabs -->
	<div id="tabs">
		<ul>
			<li><a href="#tabs-dashboard">Dashboard</a></li>
			<li><a href="#tabs-cateira">Carteira</a></li>
			<li><a href="#tabs-cotacoes">Cotações</a></li>
			<li><a href="#tabs-posicao">Posição Acionária</a></li>
			<li><a href="#tabs-comprar">Comprar / Vender</a></li>
			<li><a href="#tabs-ordens">Ordens</a></li>
			<li><a href="#tabs-book">Book de Ofertas</a></li>
		</ul>
		<div id="tabs-dashboard">
			<div id="piechart" style="width: 900px; height: 500px;"></div>
		</div>
		<div id="tabs-cateira">
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
			</table>
		</div>
		<div id="tabs-cotacoes">
			<table id="cotacoes" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Ação</th>
						<th>Último</th>
						<th>Abertura</th>
						<th>Fechamento</th>
						<th>Máximo</th>
						<th>Mínimo</th>
						<th>Negocios</th>
						<th>Oscilação</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="tabs-posicao"></div>
		<div id="tabs-ordens">

			<table id="ordens" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th></th>
						<th>Tipo</th>
						<th>Ação</th>
						<th>Qtd.</th>
						<th>Preço</th>
						<th>Vencimento</th>
						<th>Situação</th>
					</tr>
				</thead>
			</table>

		</div>
		<div id="tabs-comprar">


			<table class="display" cellspacing="10px" width="100%"
				cellpadding="10px">
				<tr>
					<td><form:form id="frmCotacao" action="cotacaoconsultar"
							method="post">
							<h2 class="demoHeaders">Cotações</h2>
							<table id="cotacoes" class="display" cellspacing="3px"
								cellpadding="3px" width="100%">
								<thead>
									<tr>
										<td>Ação:</td>
										<td><select id="selectativo">

										</select></td>
									</tr>
									<tr>
										<td>Nome:</td>
										<td><div id="name"></div></td>
									</tr>
									<tr>
										<td>Preço:</td>
										<td><div id="ultimoPreco"></div></td>
									</tr>
									<tr>
										<td>Abertura:</td>
										<td><div id="precoAbertura"></div></td>
									</tr>
									<tr>
										<td>Fechamento:</td>
										<td><div id="precoFechamento"></div></td>
									</tr>
									<tr>
										<td>Oscilaçao:</td>
										<td><div id="oscilacao"></div></td>
									</tr>
									<tr>
										<td>Volume:</td>
										<td><div id="qtdNegocios"></div></td>
									</tr>
									<tr>
										<td>Cotação em:</td>
										<td></td>
									</tr>
								</thead>
							</table>
						</form:form></td>
					<td><form:form id="frmOrdem" action="ordemincluir"
							method="post">
							<div id="labelOrdem">
								<h2 class="demoHeaders">Ordem de Compra</h2>
							</div>
							<table class="display" cellspacing="3px" cellpadding="3px"
								width="100%">
								<tr>
									<td><form:label path="preco">Preço:</form:label></td>
									<td><form:input path="preco" id="ipreco" /></td>
								</tr>
								<tr>
									<td><form:label path="quantidade">Quantidade:</form:label></td>
									<td><form:input path="quantidade" id="iquantidade" /></td>
								</tr>
								<tr>
									<td>Estimativa:</td>
									<td><input type="text" id="estimativa" /></td>
								</tr>
								<tr>
									<td><form:label path="tipo">Tipo:</form:label></td>
									<td>
										<div id="radioset">
											<input type="radio" id="radio1" name="radio"
												checked="checked"><label for="radio1">Compra</label>
											<input type="radio" id="radio2" name="radio"><label
												for="radio2">Venda</label>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2"></td>
								</tr>
							</table>
							<form:label path="tipo" />
							<form:hidden path="tipo" />
							<form:hidden path="usuarioString" value="${username}" />
							<form:hidden path="instrumentoString" />
							<button id="button">Executar</button>
							<br>

						</form:form></td>

				</tr>
			</table>



		</div>
		<div id="tabs-book">
			<table width="60%">
				<tr valign="middle">
					<td>Ação:</td>
					<td><select id="selectativobook">

					</select></td>
					<td align="center"><div id="labelBook"></div></td>
				</tr>
			</table>
			<table id="book" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Qtd.Bid</th>
						<th>Preço Bid</th>
						<th>Preço Ask</th>
						<th>Qtd.Ask</th>
					</tr>
				</thead>
			</table>

		</div>
	</div>

	<!-- Button -->
	<h2 class="demoHeaders">Administração</h2>
	<button id="btnInstrumentos">Instrumentos</button>
	<form id="frmAdm" style="margin-top: 1em;"
		action="instrumentomanutencao" method="get"></form>

	<form id="frmLogOut" action="j_spring_security_logout"></form>

	<script src="js/jquery-ui.js"></script>
	<script>
		$("#buttonLogOut").click(function() {
			$("#frmLogOut").submit();
		});

		$("#tabs").tabs();

		$("#button").button();

		$("#button").click(function() {

			if ($('#instrumentoString').prop('value') == '') {
				alert('Selecione uma ação!');
				return false;
			}

			if ($('#radio1').prop('checked')) {
				$('#tipo').prop('value', 'C');
			} else {
				$('#tipo').prop('value', 'V');
			}
			;

			//$('#usuarioString').prop('value', 'ecaversan');
			$('frmOrdem').submit();
		});

		$('#ipreco').on(
				"change",
				function(event) {
					if ($('#iquantidade').prop('value') != '') {
						$('#estimativa').prop(
								'value',
								$('#ipreco').prop('value')
										* $('#iquantidade').prop('value'));
					}
				});

		$('#iquantidade').on(
				"change",
				function(event) {
					if ($('#ipreco').prop('value') != '') {
						$('#estimativa').prop(
								'value',
								$('#ipreco').prop('value')
										* $('#iquantidade').prop('value'));
					}
				});

		$("#btnInstrumentos").button();

		$("#btnInstrumentos").click(function() {
			$("#frmAdm").submit();
		});

		$("#radioset").buttonset();

		$('#radio1').on(
				"change",
				function(event) {
					$('#labelOrdem').html(
							'<h2 class=demoHeaders>Ordem de Compra</h2>');
				});

		$('#radio2').on("change", function(event) {
			$('#labelOrdem').html('<h2 class=demoHeaders>Ordem de Venda</h2>');
		});

		$("#selectativo").selectmenu({
			change : function(event, data) {
				var $acao = data.item.value;

				$('#instrumentoString').prop('value', $acao);

				$('#name').html('');
				$('#ultimoPreco').html('');
				$('#precoAbertura').html('');
				$('#precoFechamento').html('');
				$('#oscilacao').html('');
				$('#qtdNegocios').html('');

				$.getJSON("cotacaoconsultar" + $acao).done(function(data) {
					$.each(data, function(key, val) {
						$('#' + key).html(val);
						if (key == 'instrumento') {
							$.each(val, function(key, val) {
								$('#' + key).html(val);
							});
						}
						;
					});
				});
			}
		});

		$("#selectativobook").selectmenu({
			change : function(event, data) {
				var $acao = data.item.value;

				$('#labelBook').html('');
				
				if($acao!=''||$acao!='-- selecione --'){
					$('#labelBook').html('<h2 class=demoHeaders>' + $acao + ' - Book de ofertas</h2>');
					}
				

				$('#book').dataTable({
					"bProcessing" : true,
					"bServerSide" : true,
					"bDestroy" : true,
					"ajax" : "book" + $acao,
					"columns" : [ {
						"data" : "bidQuantidade"
					}, {
						"data" : "bidPreco",
						render : $.fn.dataTable.render
						.number(',', '.', 2,
								'R$')
					}, {
						"data" : "askPreco",
						render : $.fn.dataTable.render
						.number(',', '.', 2,
								'R$')
					}, {
						"data" : "askQuantidade"
					} ]
				});
			}
		});
	</script>
</body>
</html>