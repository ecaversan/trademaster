<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trade Master - ERROR</title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

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

</head>
<body>

	<div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin-right: .3em;"></span> <strong>Alert:</strong>${error_desc}
			</p>
		</div>
	</div>

	<form id="frmLogOut" action="j_spring_security_logout"></form>

	<br>
	<ul id="icons" class="ui-widget ui-helper-clearfix">
		<li class="ui-state-default ui-corner-all" title=".ui-icon-home"><span
			id="buttonDash" class="ui-icon ui-icon-home"></span></li>
	</ul>

	<script src="js/jquery-ui.js"></script>
	<script>
		$("#buttonDash").click(function() {
			$("#frmLogOut").submit();
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