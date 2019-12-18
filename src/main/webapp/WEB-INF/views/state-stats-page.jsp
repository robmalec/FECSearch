<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Info for ${stateName }</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/sketchy/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-N8DsABZCqc1XWbg/bAlIDk7AS/yNzT5fcKzg/TwfmTuUqZhGquVmpb5VvfmLcMzp"
	crossorigin="anonymous">
</head>
<body>
	<h1>Info for ${stateName } between ${beginYear } and ${endYear }:</h1>
	<h2>Total funds raised</h2><br>
	${totalFunds }
	<h2>Total winning funds raised</h2><br>
	${totalWinningFunds }
	<h2>Total losing funds raised</h2><br>
	${totalLosingFunds }
	<h2>Biggest money winner candidate:</h2><br>
	${bmw.getName() } with $${bmwBudget}
	<h2>Biggest money loser candidate:</h2><br>
	${bml.getName() } with $${bmlBudget}
	<h2>Smallest money winner candidate:</h2><br>
	${smw.getName() } with $${smwBudget}
	<h2>Smallest money loser candidate:</h2><br>
	${sml.getName() } with $${smlBudget}
	<h2>Presidential candidates by fundraising numbers</h2><br>
	<table>
		<tr>
			<th>Candidate name</th><th>Campaign year</th><th>Funds raised in ${stateName }</th>
		</tr>
		<c:forEach var="cand" items="${candFundsList }">
			<tr>
				<td>${cand[0]}</td><td>${cand[2] }</td><td>$${cand[1]}</td>
			</tr>
		</c:forEach>
	</table>
	<a href="/">Go back</a>
</body>
</html>