<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>

<title>Words With Friends Resources</title>
<link type="text/css" rel="stylesheet" href="/css/main.css"></link>
<link type="text/css" rel="stylesheet" href="/css/sunny/jquery-ui-1.8.10.custom.css"></link>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/jquery-ui.js"></script>
<script type="text/javascript" src="/js/main.js"></script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-21817422-2']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>
<body>
	<div id="page">
		<div id="header"><h1>Words With Friends</h1></div>
		<div class="column">
			
			<h2>Anagram Solver</h2>
			
			Requirements (* represents blank, can have up to two):<br/>
			<div class="help">Click the (?) for help</div>
			<form id="solver">
				<table width="450px">
					<tr>
						<td class="rack" width="150px">Rack <div class="help">(?)</div>:</td>
						<td width="300px"><input type="text" name="rack" class="rack" /></td>
					</tr>
					<tr>
						<td class="start">Starts with <div class="help">(?)</div>:</td>
						<td><input type="text" name="start" class="start" /></td>
					</tr>
					<tr>
						<td class="contains">Contains <div class="help">(?)</div>:</td>
						<td><input type="text" name="contains" class="contains" /></td>
					</tr>
					<tr>
						<td class="end">Ends with <div class="help">(?)</div>:</td>
						<td><input type="text" name="end" class="end" /></td>
					</tr>
				</table>
				<input type="submit" value="Submit" />
			</form>
			
			<div id="solveResults">
			</div>
		</div>
		<div class="column-spacer"></div>
		<div class="column">
			<h2>Word Value</h2>
			
			<table width="400px">
				<tr>
					<td width="100px" class="word">Word <div class="help">(?)</div>:</td>
					<td width="300px"><input type="text" name="word" class="word" /></td>
				</tr>
			</table>
			<br />
			<div id="testResults">
			</div>
		</div>
	</div>
	<div id="dialog" title="Basic dialog">
		This is an animated dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.
	</div>
</body>
</html>