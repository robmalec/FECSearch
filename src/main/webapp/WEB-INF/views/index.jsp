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

<script language="JavaScript">
	function showCN() {
		document.getElementById('display').innerHTML = document
				.getElementById("contributor_name").value;
	}
</script>
</head>
<body>


	<div class="container">

		<div class="jumbotron">
			<h1 style="font-family: Times New Roman;">Follow The $</h1>
		</div>

<form action="/test">
<input type="hidden" name="test" value="working">
		<div class="dropdown">
			<!-- <button class="btn btn-secondary dropdown-toggle" 
				id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false">Search Options</button> -->
			<select type="button" class="btn btn-secondary dropdown-toggle" aria-labelledby="dropdownMenuButton">
				<option value="Candidate Look Up">Intel Options</option>
					<option
					class="dropdown-item" value="Candidate Look Up">Candidate Look Up</option>
					<option
					class="dropdown-item" value="Location Look Up">Location Look Up </option>
					<option
					class="dropdown-item" value="Biggest Winner">Biggest Winner </option>
					<option
					class="dropdown-item" value="Biggest Loser">Biggest Loser</option>
					
					</select>
					<input
				type="Submit" class="btn btn-dark">
			</div>
	</form>
		
		<c:if test="${not empty testing }">
		<form>
			<label>Candidate Name</label><input type="text"
				name="contributor_name" id = "contributor_name" style="inheret"> <label>City</label><input
				type="text" name="contributor_name"> <label>State</label><input
				type="text" name="contributor_name"> <label>Zip Code</label><input
				type="text" name="contributor_state" required> <input
				type="Submit" onclick="showInput();" class="btn btn-dark">
				 <p><span id='display'></span></p>
		</form>
		<form>
			<label>Location</label><input type="text" name="location_name"><input
				type="text" name="location_name"> <label>City</label><input
				type="text" name="location_name"> <label>State</label> <input
				type="text" name="location_name"> <label>Zip Code</label><input
				type="text" name="contributor_state" required> <input
				type="Submit" class="btn btn-dark">

		</form>
		<form>
			<label>Location Comparer</label><input type="text"
				name="location_comparison"> <label>City</label><input
				type="text" name="location_comparison"> <label>City</label><input
				type="text" name="location_comparison"> <label>State</label>
			<input type="text" name="location_comparison"> <label>Zip
				Code</label><input type="text" name="location_comparison"> <label>City</label><input
				type="text" name="location_comparison"> <label>State</label><input
				type="text" name="location_comparison"> <label>Zip
				Code</label><input type="text" name="location_comparison" required>
			<input type="Submit" class="btn btn-dark">

		</form>
		<form>
			<label>Biggest Winner</label><input type="text" name="biggest_winner">
			<label>Candidate Name</label><input type="text" name="biggest_winner">
			<label>City</label><input type="text" name="biggest_winner">
			<label>State</label> <input type="text" name="biggest_winner">
			<label>Zip Code</label><input type="text" name="biggest_winner"
				required> <input type="Submit" class="btn btn-dark">

		</form>
		<form>
			<label>Biggest Loser</label><input type="text" name="biggest_loser">
			<label>City</label><input type="text" name="biggest_loser"> <label>Biggest
				loser</label><input type="text" name="biggest_loser"> <label>Candidate
				Name</label> <input type="text" name="biggest_loser"> <label>City</label>
			<input type="text" name="biggest_loser"> <label>State</label>
			<input type="text" name="biggest_loser"> <label>Zip
				Code</label><input type="text" name="biggest_loser" required> <input
				type="Submit" class="btn btn-dark">

		</form>

	
	<br>
	<div class="alert alert-primary" role="alert" id="alert"
		style="display: none">${badSearch}</div>
	<br>
</c:if>
</div>
	<script>
		if (document.getElementById('alert').innerHTML !== "") {
			document.getElementById('alert').removeAttribute("style");
		}
	</script>

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