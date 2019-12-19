<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Data viewer</title>
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
					ticks : {
						beginAtZero : false,
					}
				} ],
				yAxes : [ {
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