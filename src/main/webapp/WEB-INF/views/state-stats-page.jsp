<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Info for ${stateName }</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.4.1/litera/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-pLgJ8jZ4aoPja/9zBSujjzs7QbkTKvKw1+zfKuumQF9U+TH3xv09UUsRI52fS+A6"
	crossorigin="anonymous">
</head>
<body>
	<div class="row">
		<div class="col-lg-12 text-center pagetitle">
			<h1>Info for ${stateName } between ${beginYear } and ${endYear }:</h1>
			<br> <br>

		</div>
	</div>
	<div class="row">
		<div class="col-lg-6" id="left">
			<b>Total funds raised:</b> $${totalFunds }<br>
			<b>Total winning candidate funds raised:</b> $${totalWinningFunds }<br>
			<b>Total losing  candidate funds raised:</b> $${totalLosingFunds }<br>
			<b>Highest fundraising winner candidate:</b> ${bmw.getName() } with $${bmwBudget}<br>
			<b>Biggest money loser candidate:</b> ${bml.getName() } with $${bmlBudget}<br>
			<b>Smallest money winner candidate:</b> ${smw.getName() } with $${smwBudget}<br>
			<b>Smallest money loser candidate:</b> ${sml.getName() } with $${smlBudget}<br>
		</div>
		
		<div class="col-lg-6" id="right">
			<h2>Presidential candidates by fundraising numbers</h2>
			<br>
			<table>
				<tr>
					<th>Candidate name</th>
					<th>Campaign year</th>
					<th>Funds raised in ${stateName }</th>
				</tr>
				<c:forEach var="cand" items="${candFundsList }">
					<tr>
						<td>${cand[0]}</td>
						<td>${cand[2] }</td>
						<td>$${cand[1]}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>






	<a href="/">Go back</a>
</body>
</html>