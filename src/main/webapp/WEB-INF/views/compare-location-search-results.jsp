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
				<h3>The majority of donations in ${location1result.getElectionYear()} went to:</h3>
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
			<div class="col-lg-12">
				<h4>The largest winning donations to
					${largest_winner_recipient_location1} were:</h4>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-4">
				<h6 class="win-lose-total">$${largest_winning_donation_location1}</h6>
				<p>From ${location1}</p>
			</div>
			<div class="col-lg-4">
				<h6>and ...</h6>
			</div>
			<div class="col-lg-4">
				<h6 class="win-lose-total">$${largest_winning_donation_location2}</h6>
				<p>From ${location2}</p>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<h4>The largest amount of money donated to
					${largest_winner_recipient_location1} was:</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4">
				<h6 class="win-lose-total">$${largest_winner_total_location1}</h6>
				<p>From ${location1}</p>
			</div>
			<div class="col-lg-4">
				<h6>and ...</h6>
			</div>
			<div class="col-lg-4">
				<h6 class="win-lose-total">$${largest_winner_total_location2}</h6>
				<p>From ${location2}</p>
			</div>
		</div>
		<br>
	</div>
</body>
</html>