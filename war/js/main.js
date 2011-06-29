	$(document).ready(function() {

		function validInput(key)
		{
			if(key != 8 && key != 9 && key != 13 && (key < 65 || key > 90)) {
				return false;}
			else {
				return true;}
		}
		
		$("input.rack").keydown(function(event) {
			if(!validInput(event.which) && event.which != 56) return false;
		});

		$("input.start").keydown(function(event) {
			if(!validInput(event.which)) return false;
		});

		$("input.contains").keydown(function(event) {
			if(!validInput(event.which)) return false;
		});

		$("input.end").keydown(function(event) {
			if(!validInput(event.which)) return false;
		});

		$("input.word").keydown(function(event) {
			if(!validInput(event.which)) return false;
		});
		
		$("#solver").submit(function(){
			$("#solveResults").html("Loading...");
			$.ajax({
				type:"POST",
				url: "/wwfsolver2/solve",
				data: "rack="+$("input.rack").val() + "&start="+$("input.start").val() + "&contains="+$("input.contains").val() + "&end="+$("input.end").val(),
				success: function(json) {
					$("#solveResults").html(parseResults(json));}
				});
			return false;
		});

		function parseResults(json)
		{
			var results = $.parseJSON(json);
			var length = 0;
			var tableBegin = "<table width=\"300px\">";
			var tableHeader = "<tr><td width=\"100px\"><b>Word</b></td><td width=\"100px\"><b>Score</b></td><td width=\"100px\"><b># Chars</b></td></tr>";
			var tableEnd = "</table>";
			var tableBody = "";

			if(results.error) {
				if(results.error == "invalid query") {
					return "Invalid query";}
				else {
					return "";}}
			
			$.each(results.results, function() {
				tableBody += "<tr>";
				tableBody += "<td>" + this.word.toUpperCase() + "</td>";
				tableBody += "<td>" + this.score + "</td>";
				tableBody += "<td>" + this.length + "</td>";
				tableBody += "</tr>";
				length++;});
			
			var start = "Rack: " + results.word.toUpperCase() + " (" + length + " results)<br />";

			if(length == 0) {
				if(results.error == "invalid query") {
					return "Invalid query";}
				else if(results.error == "no query") {
					return "";}}
				
			return start + tableBegin + tableHeader + tableBody + tableEnd;
		}

		$("input.word").keyup(function(event) {
			$.ajax({
				type:"POST",
				url: "/test",
				data: "word="+$("input.word").val(),
				success: function(json) {
					var result = $.parseJSON(json);
					if(result != null) {
						var word = $("input.word").val().toUpperCase();
						var out = word + " ";
						if(result.value == 0) {
							out += "is <b>not</b> a word";
						}
						else {
							out += "is worth <b>" + result.value + " points</b>";
						}
						$("#testResults").html(out);
					}
					else {
						$("#testResults").html("");}
				}});
		});


		// increase the default animation speed to exaggerate the effect
		$.fx.speeds._default = 150;
		$( "#dialog" ).dialog({
			autoOpen: false,
			modal: true,
			show: "blind",
			hide: "blind"
		});

		function openDialog(position,title,body) {
			$( "#dialog" ).dialog( "option", "position", [position.left-50,position.top-75]);
			$( "#dialog" ).dialog( "option", "title", title);
			$( "#dialog" ).html(body);
			$( "#dialog" ).dialog("open");
		};
		
		$("#dialog").click( function() {
			$("#dialog").dialog( "close");
		});

		$("td.rack").click(function() {
			var title = "Rack";
			var body = "This field should contain all the letters that are available to you this turn.<br /><br />(Required)";
			openDialog($(this).position(),title,body);
		});
		
		$("td.start").click(function() {
			var title = "Start";
			var body = "This field contains a string on the board that you want to match your letters against. Any generated words will start with this string.<br /><br />(Optional)";
			openDialog($(this).position(),title,body);
		});

		$("td.contains").click(function() {
			var title = "Contains";
			var body = "This field contains a string on the board that you want to be somewhere in your words - it could be anywhere, from the beginning to the end.<br /><br />(Optional)";
			openDialog($(this).position(),title,body);
		});

		$("td.end").click(function() {
			var title = "End";
			var body = "This field contains a string on the board that you want to match your letters against. Any generated words will end with this string.<br /><br />(Optional)";
			openDialog($(this).position(),title,body);
		});

		$("td.word").click(function() {
			var title = "Word value";
			var body = "Enter in a word here to find out if it's in the Words With Friends dictionary and, if it does, how many points it's worth.";
			openDialog($(this).position(),title,body);
		});
		
	});