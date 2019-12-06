<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>


	<div class="container">

		<div class="jumbotron">
			<h1>Follow The $</h1>
		</div>

		<div class="dropdown">
			<button class="btn btn-secondary dropdown-toggle" type="button"
				id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false">Search Options</button>
			<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				<a class="dropdown-item" href="#">Location</a> <a
					class="dropdown-item" href="#">Location Comparison</a> <a
					class="dropdown-item" href="#">Biggest Winner</a> <a
					class="dropdown-item" href="#">Biggest Loser</a>
			</div>
		</div>
		<form action="find-contributor">
			<label>Candidate Name</label><input type="text"
				name="contributor_name"> <label>City</label><input
				type="text" name="contributor_name"> <label>Zip Code</label><input
				type="text" name="contributor_name"> <label>State</label><input
				type="text" name="contributor_state" required>s <input
				type="Submit" class="btn btn-dark">

		</form>
	</div>
	<br>
	<div class="alert alert-primary" role="alert" id="alert"
		style="display: none">${badSearch}</div>
	<br>

	<script>
		if (document.getElementById('alert').innerHTML !== "") {
			document.getElementById('alert').removeAttribute("style");
		}
	</script>
	<script>$('.dropdown-toggle').dropdown()</script>
</body>
</html>