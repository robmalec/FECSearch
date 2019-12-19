<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Data viewer</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.4.1/litera/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-pLgJ8jZ4aoPja/9zBSujjzs7QbkTKvKw1+zfKuumQF9U+TH3xv09UUsRI52fS+A6"
	crossorigin="anonymous">
</head>
<body>
	<h1>
		Comparing state prediction rates between ${beginYear } and ${endYear }
		<br> with ${category}.........
	</h1>

	<canvas id="myChart" width="400" height="400"></canvas>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
	<script src="https://cdn.jsdelivr.net/npm/moment@2.24.0/moment.min.js"></script>
	<script>
		var ctx = document.getElementById('myChart').getContext('2d');
		
		// Define the data 
		var data1 = [${scatterData}];
		console.log(data1);

		// End Defining data
		var options = {
			responsive : true, // Instruct chart js to respond nicely.
			maintainAspectRatio : false, // Add to prevent default behaviour of full-width/height
			scales : {
				xAxes : [ {
					scaleLabel:{
						display:true,
						labelString:'Winning candidates\' share of each state\'s total presidential donations, %',
					},
					ticks : {
						beginAtZero : false,
					}
				} ],
				yAxes : [ {
					scaleLabel:{
						display:true,
						labelString:'${category}',
					},
					ticks:{
						beginAtZero: false,
					}
				}
				]
			}
		};

		// End Defining data
		var myChart = new Chart(ctx, {
			type : 'scatter',
			data : {
				datasets : [ {
					label : '${category}', // Name the series
					data : data1, // Specify the data values array          
					backgroundColor : '${winnerColor}', // Add custom color background (Points and Fill)
				}]
			},
			options : options
		});
	</script>

</body>
</html>