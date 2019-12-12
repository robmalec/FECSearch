<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Location Search Results</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

<link rel="stylesheet" href="location-result-style.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<br>
		<div class="row">
			<div class="col-lg-12 text-center">
				<h1>${location}</h1>
			</div>
		</div>
		<br>
		<hr>
		<div class="row">
			<div class="col-lg-12 text-center">
				<h3>The majority of donations from ${location} went to:</h3>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-12 text-center">
				<h4>${majname}</h4>
				<h6> in ${results.getElectionYear()}</h6>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-12 text-center">
				<h3>Average Donations</h3>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-6 text-center">
				<h6>To ${largest_total_winner_recipient}</h6>
				<h4 class="win-lose-total">$${avg_winning_donation}</h4>
			</div>
			<div class="col-lg-6 text-center">
				<h6>Donations to ${largest_total_loser_recipient}</h6>
				<h4 class="win-lose-total">$${avg_losing_donation}</h64>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-12 text-center">
				<h3>Largest Individual Donations</h3>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-6 text-center">
			<h6>To ${largest_total_winner_recipient}</h6>
				<h4 class="win-lose-total">$${largest_winning_donation}</h4>
			</div>
			<div class="col-lg-6 text-center">
			<h6>To ${largest_total_loser_recipient}</h6>
				<h4 class="win-lose-total">$${largest_losing_donation}</h4>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-12 text-center">
				<h3>Total Donations</h3>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-6 text-center">
			<h6>To ${largest_total_winner_recipient}</h6>
				<h4 class="win-lose-total">$${largest_winner_total}</h4>
			</div>
			<div class="col-lg-6 text-center">
			<h6>To ${largest_total_loser_recipient}</h6>
				<h4 class="win-lose-total">$${largest_loser_total}</h4>
			</div>
		</div>
		<br>
		<div class="col-lg-12">
			<canvas id="myChart" width="100%" height="400px"></canvas>
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
					backgroundColor : '#3fd715', // Add custom color background (Points and Fill)
				}, {
					label : 'Donations to ${results.getLoserName()}  (Date, Amount)', // Name the series
					data : data2, // Specify the data values array           
					backgroundColor : '#f70d1a', // Add custom color background (Points and Fill)
				}]
			},
			options : options
		});
	</script>
</body>
</html>