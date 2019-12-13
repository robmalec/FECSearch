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
	<ul class="nav nav-tabs bg-dark navbar-dark">
		<li class="nav-item"><a class="nav-link active" href="#">Active</a>
		</li>
		<li class="nav-item"><a class="nav-link" href="#"
			style="color: white;">Link</a></li>
	</ul>
	<div class="container">
		<br>
		<div class="row">
			<div class="col-lg-12 text-center">
				<h1>${location1} vs. ${location2}</h1>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-12 text-center">
				<h3>The majority of donations in
					${location1result.getElectionYear()} went to:</h3>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-6 text-center">
				<h4 class="win-lose-total">${location1MostDon}</h4>
				<h6>In ${location1}</h6>
			</div>
			<div class="col-lg-6 text-center">
				<h4 class="win-lose-total">${location2MostDon}</h4>
				<h6>In ${location2}</h6>
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
				<h4 class="win-lose-total">$${avg_winning_donation_location1}</h4>
				<h6>In ${location1}</h6>
			</div>
			<div class="col-lg-6 text-center">
				<h4 class="win-lose-total">$${avg_winning_donation_location2}</h4>
				<h6>In ${location2}</h6>
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
			<div class="col-lg-6">
				<div class="row">
					<div class="col-lg-12 text-center">
						<h5>To ${location1result.getWinnerName()}</h5>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-6 text-center">
						<h4 class="win-lose-total">$${largest_winning_donation_location1}</h4>
						<h6>From ${location1}</h6>
					</div>
					<div class="col-lg-6 text-center">
						<h4 class="win-lose-total">$${largest_winning_donation_location2}</h4>
						<h6>From ${location2}</h6>
					</div>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="row">
					<div class="col-lg-12 text-center">
						<h5>To ${location2result.getLoserName()}</h5>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-6 text-center">
						<h4 class="win-lose-total">$${String.format("%.2f",location1result.getLargestLosingDonation())}</h4>
						<h6>From ${location1}</h6>
					</div>
					<div class="col-lg-6 text-center">
						<h4 class="win-lose-total">$${String.format("%.2f",location2result.getLargestLosingDonation())}</h4>
						<h6>From ${location2}</h6>
					</div>
				</div>
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
			<div class="col-lg-6">
				<div class="row">
					<div class="col-lg-12 text-center">
						<h5>To ${location1result.getWinnerName()}</h5>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-6 text-center">
						<h4 class="win-lose-total">$${String.format("%.2f", location1result.getWinnerTotalDonations())}</h4>
						<h6>From ${location1}</h6>
					</div>
					<div class="col-lg-6 text-center">
						<h4 class="win-lose-total">$${String.format("%.2f", location2result.getWinnerTotalDonations())}</h4>
						<h6>From ${location2}</h6>
					</div>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="row">
					<div class="col-lg-12 text-center">
						<h5>To ${location2result.getLoserName()}</h5>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-6 text-center">
						<h4 class="win-lose-total">$${String.format("%.2f",location1result.getLoserTotalDonations())}</h4>
						<h6>From ${location1}</h6>
					</div>
					<div class="col-lg-6 text-center">
						<h4 class="win-lose-total">$${String.format("%.2f",location2result.getLoserTotalDonations())}</h4>
						<h6>From ${location2}</h6>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="col-lg-12">
			<canvas id="myChart" width="100%" height="400px"></canvas>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
	<script src="https://cdn.jsdelivr.net/npm/moment@2.24.0/moment.min.js"></script>
	<script>
		var ctx = document.getElementById("myChart").getContext('2d');

		// Define the data 
		var data1 = [${location1result.getWinnerDonationScatterData()}];
		var data2 = [${location1result.getLoserDonationScatterData()}];
		var data3 = [${location2result.getWinnerDonationScatterData()}];
		var data4 = [${location2result.getLoserDonationScatterData()}];
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
					label : 'Donations to ${location1result.getWinnerName()} from ${location1} (Date, Amount)', // Name the series
					data : data1, // Specify the data values array          
					backgroundColor : '${winnerColor1}', // Add custom color background (Points and Fill)
				}, {
					label : 'Donations to ${location1result.getWinnerName()} from ${location1} (Date, Amount)', // Name the series
					data : data2, // Specify the data values array           
					backgroundColor : '${winnerColor2}', // Add custom color background (Points and Fill)
				}, {
					label : 'Donations to ${location2result.getLoserName()} from ${location2} (Date, Amount)', // Name the series
					data : data3, // Specify the data values array           
					backgroundColor : '${loserColor1}', // Add custom color background (Points and Fill)
				}, {
					label : 'Donations to ${location2result.getLoserName()} from ${location2} (Date, Amount)', // Name the series
					data : data4, // Specify the data values array           
					backgroundColor : '${loserColor2}', // Add custom color background (Points and Fill)
				}]
			},
			options : options
		});
	</script>
</body>
</html>