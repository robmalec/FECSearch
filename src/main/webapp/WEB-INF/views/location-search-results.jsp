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
			<div class="col-lg-12">
				<h1>${location}</h1>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-lg-12">
				<h4>The majority of donors in ${location} have supported:</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4">
				<h6 class="win-lose-total">${total_winners}</h6>
				<p>winning candidates</p>
			</div>
			<div class="col-lg-4">
				<h6>and ...</h6>
			</div>
			<div class="col-lg-4">
				<h6 class="win-lose-total">${total_losers}</h6>
				<p>losing candidates</p>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<h4>On average ...</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4">
				<p>Donations to winners were:</p>
				<h6 class="win-lose-total">$${avg_winning_donation}</h6>
			</div>
			<div class="col-lg-4">
				<h6>while ...</h6>
			</div>
			<div class="col-lg-4">
				<p>Donations to losers were:</p>
				<h6 class="win-lose-total">$${avg_losing_donation}</h6>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<h4>The largest winning and losing donations were:</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4">
				<h6 class="win-lose-total">$${largest_winning_donation}</h6>
				<p>Donated to ${largest_winner_recipient}</p>
			</div>
			<div class="col-lg-4">
				<h6>and ...</h6>
			</div>
			<div class="col-lg-4">
				<h6 class="win-lose-total">$${largest_losing_donation}</h6>
				<p>Donated to ${largest_loser_recipient}</p>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<h4>The largest amount of money donated was:</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4">
				<h6 class="win-lose-total">$${largest_winner_total}</h6>
				<p>Donated to ${largest_total_winner_recipient}</p>
			</div>
			<div class="col-lg-4">
				<h6>and ...</h6>
			</div>
			<div class="col-lg-4">
				<h6 class="win-lose-total">$${largest_loser_total}</h6>
				<p>Donated to ${largest_total_loser_recipient}</p>
			</div>
		</div>
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
							 console.log(values);
							 var str = value.toString();
							 var rtrn = String(str.substring(0,4) + '-' + str.substring(4,6));
							 console.log(rtrn)
				                return rtrn;
				              
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