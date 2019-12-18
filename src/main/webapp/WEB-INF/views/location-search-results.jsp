<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Location Search Results</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.4.1/litera/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-pLgJ8jZ4aoPja/9zBSujjzs7QbkTKvKw1+zfKuumQF9U+TH3xv09UUsRI52fS+A6"
	crossorigin="anonymous">
</head>
<body>
	<ul class="nav nav-tabs bg-dark navbar-dark">
		<li class="nav-item"><a class="nav-link active" href="#"><strong>Presidential
					Elections: City & Election Year Search</strong></a></li>
		<li class="nav-item"><a class="nav-link" href="/"
			style="color: white;">Home</a></li>
	</ul>
	<div class="container">
		<br>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-12 text-center">
				<h1>${location}</h1>
				<br>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-4 text-center">
				<div class="row" style="border-bottom:2pt solid; margin-top:10pt;">
					<div class="col-lg-12" style="margin-bottom: 10pt;">
						<h3>Top Fundraiser</h3>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12  align-self-center">
						<br>
						<h1>${majname}</h1>
						<img alt="" src="${urls.get(majname)}" style="max-height: 300px;">
						<br>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-lg-12">
						<h6>
							Donations from ${city} accounted for <strong>${String.format("%.1f", majDonPercentForState * 100)}%</strong>
							of the funds ${majname} raised in this state, and <strong>${String.format("%.1f", majDonPercentAllStates * 100)}%</strong>
							of their total funds raised nationwide.
						</h6>
						<br>
					</div>
				</div>
			</div>
			<div class="col-lg-8" style="border-left: solid 2pt;">
				<div class="row"
					style="border-bottom: solid 2pt;">
					<div class="col-lg-12 text-center" style=" margin-top: 5pt;margin-bottom: 5pt;">
						<h5>${majname}</h5>
					</div>
				</div>
				<div class="row" style="border-bottom: 2pt solid;">
					<div class="col-lg-4 text-center" style=" margin-top: 5pt;margin-bottom: 5pt;">
						<h6>Total Donations:</h6>
						<p>$${totWinDon}</p>
					</div>
					<div class="col-lg-4 text-center" style=" margin-top: 5pt;margin-bottom: 5pt;">
						<h6>Largest Donation:</h6>
						<p>$${bigWinDon}</p>
					</div>
					<div class="col-lg-4 text-center" style=" margin-top: 5pt;margin-bottom: 5pt;">
						<h6>Average Donation:</h6>
						<p>$${avgWinDon}</p>
					</div>
				</div>
				<div class="row"
					style="border-bottom: solid 2pt;">
					<div class="col-lg-12 text-center" style=" margin-top: 5pt;margin-bottom: 5pt;">
						<h5>${losName}</h5>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-4 text-center" style=" margin-top: 5pt;margin-bottom: 5pt;">
						<h6>Total Donations:</h6>
						<p>$${totLoseDon}</p>
					</div>
					<div class="col-lg-4 text-center" style=" margin-top: 5pt;margin-bottom: 5pt;">
						<h6>Largest Donation:</h6>
						<p>$${bigLoseDon}</p>
					</div>
					<div class="col-lg-4 text-center" style=" margin-top: 5pt;margin-bottom: 5pt;">
						<h6>Average Donation:</h6>
						<p>$${avgLoseDon}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<canvas id="myChart" style="max-width: 100%; max-height: 300px;"></canvas>
						<br>
					</div>
				</div>
			</div>
		</div>
		<br>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
	<script src="https://cdn.jsdelivr.net/npm/moment@2.24.0/moment.min.js"></script>
	<script>
		var ctx = document.getElementById("myChart").getContext('2d');

		// Define the data 
		var data1 = [${winnerDonationData}];
		var data2 = [${loserDonationData}];
		// End Defining data
		var options = {
			responsive : true, // Instruct chart js to respond nicely.
			maintainAspectRatio : false, // Add to prevent default behaviour of full-width/height
			scales : {
				xAxes : [ {
					ticks : {
						beginAtZero : false,
						 callback: function(value, index, values) {
							 var str = value.toString();
							 var mtn = String(str.substring(5,7));
							 var day = String(str.substring(7,9));
							 console.log('Month' + mtn);
							 console.log('Day' + day);
							 if (day === '') {
								 day = String('01');
							 }
							 if (mtn === '') {
								 mtn = String('01');
							 }
							 var rtrn = String(str.substring(0,4) + '-' + mtn + '-' + day);
							 console.log(rtrn);
								 return rtrn;
							 
				/*
							 else {
								 return String(str.substring(0,4) + '-12-' + str.substring(7,9));
							 } */
				            }
					}
				} ]
			}
		};

		// End Defining data
		var myChart = new Chart(ctx, {
			type : 'scatter',
			data : {
				datasets : [ {
					label : 'Donations to ${results.getWinnerName()} (Date, Amount)', // Name the series
					data : data1, // Specify the data values array          
					backgroundColor : '${winnerColor}', // Add custom color background (Points and Fill)
				}, {
					label : 'Donations to ${results.getLoserName()}  (Date, Amount)', // Name the series
					data : data2, // Specify the data values array           
					backgroundColor : '${loserColor}', // Add custom color background (Points and Fill)
				}]
			},
			options : options
		});
	</script>
</body>
</html>