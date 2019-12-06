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
		<form action="find-contributor">
			<label>Name</label><input type="text" name="contributor_name">
			<label>State</label><input type="text" name="contributor_state"
				required>

			<style>
	.dropdown {
		position: relative; 
	 	display: inline-block; 
}

	.dropdown-content {
		display: none;
		position: absolute;
		background-color: #f9f9f9;
		min-width: 160px;
		box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
		padding: 12px 16px;
		z-index: 1;
}

	.dropdown:hover .dropdown-content {
		display: block;
}
	</style>

			<div class="dropdown">
				<span>Options</span>
				<div class="dropdown-content">
					<p>Biggest Winner</p>
					<p>Biggest Loser</p>
					<p>All Results</p>
				</div>
			</div>

			<input type="Submit" class="btn btn-dark">

			<!-- results in location
	comparison location
	biggest winner loser by cand -->
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
</body>
</html>