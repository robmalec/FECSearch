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
	<h1>Info for ${stateName }</h1>
	<h2>Biggest money winner candidate:</h2><br>
	${bmw.getCandidate().getName() } with $${bmw.getFunds() }
	<h2>Biggest money loser candidate:</h2><br>
	${bml.getCandidate().getName() } with $${bml.getFunds() }
	<h2>Smallest money winner candidate:</h2><br>
	${smw.getCandidate().getName() } with $${smw.getFunds() }
	<h2>Smallest money loser candidate:</h2><br>
	${sml.getCandidate().getName() } with $${sml.getFunds() }
	<h2>Presidential candidates by fundraising numbers</h2><br>
	<table>
		<tr>
			<th>Candidate name</th><th>Funds raised in ${stateName }</th>
		</tr>
		<c:forEach var="cand" items="${candFundsList }">
			<tr>
				<td>${cand.getCandidate().getName() }</td><td>$${cand.getFunds()}</td>
			</tr>
		</c:forEach>
	</table>
	<a href="/">Go back</a>
</body>
</html>