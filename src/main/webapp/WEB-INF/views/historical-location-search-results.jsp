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
				<h4>The majority of donations from ${location} went to:</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4">
				<h6 class="win-lose-total">${total_winners}</h6>
				<p>winning candidates</p>
				<p>
					<button class="btn btn-dark" type="button"
						data-toggle="collapse" data-target="#collapse1"
						aria-expanded="false" aria-controls="collapse1">
						<small>see all winners ...</small>
					</button>
				</p>
				<div class="collapse" id="collapse1">
					<div class="card card-body">
						<c:forEach var="w" items="${winnerNames}" varStatus="i"><small>${i.count}. ${w}</small><br></c:forEach></div>
				</div>
			</div>
			<div class="col-lg-4">
				<h6>and ...</h6>
			</div>
			<div class="col-lg-4">
				<h6 class="win-lose-total">${total_losers}</h6>
				<p>losing candidates</p>
				<p>
					<button class="btn btn-dark" type="button"
						data-toggle="collapse" data-target="#collapse2"
						aria-expanded="false" aria-controls="collapse2">
						<small>see all losers ...</small>
					</button>
				</p>
				<div class="collapse" id="collapse2">
					<div class="card card-body">
						<c:forEach var="l" items="${loserNames}" varStatus="i"><small>${i.count}. ${l}</small><br></c:forEach></div>
				</div>
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
				<p>
					<button class="btn btn-dark" type="button"
						data-toggle="collapse" data-target="#collapse3"
						aria-expanded="false" aria-controls="collapse3">
						<small>see average for all elections</small>
					</button>
				</p>
				<div class="collapse" id="collapse3">
					<div class="card card-body">
						<c:forEach var="wavg" items="${results}" varStatus="i"><small>${i.count}. $${String.format("%.2f",wavg.getAvgWinningDonation())} (${wavg.getWinnerName()}, ${wavg.getElectionYear()})</small><br></c:forEach></div>
				</div>
			</div>
			<div class="col-lg-4">
				<h6>while ...</h6>
			</div>
			<div class="col-lg-4">
				<p>Donations to losers were:</p>
				<h6 class="win-lose-total">$${avg_losing_donation}</h6>
				<p>
					<button class="btn btn-dark" type="button"
						data-toggle="collapse" data-target="#collapse4"
						aria-expanded="false" aria-controls="collapse4">
						<small>see average for all elections</small>
					</button>
				</p>
				<div class="collapse" id="collapse4">
					<div class="card card-body">
						<c:forEach var="lavg" items="${results}" varStatus="i"><small>${i.count}. $${String.format("%.2f",lavg.getAvgLosingDonation())} (${lavg.getLoserName()}, ${lavg.getElectionYear()})</small><br></c:forEach></div>
				</div>
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
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>
</body>
</html>