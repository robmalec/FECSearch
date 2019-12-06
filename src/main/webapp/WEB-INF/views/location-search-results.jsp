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
		<br>
	</div>
</body>
</html>