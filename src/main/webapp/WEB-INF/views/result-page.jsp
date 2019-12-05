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
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>State</th>
					<th>Contribution Amount</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${result}" varStatus="i">
					<tr>
						<th>${i.count}</th>
						<td>${c.contributor_first_name}</td>
						<td>${c.contributor_last_name}</td>
						<td>${c.contributor_state}</td>
						<td>$${String.format("%.2f",
							Double.parseDouble(c.contribution_receipt_amount))}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
		<form action="find-contributor">
			<label>Name</label><input type="text" name="contributor_name">
			<label>State</label><input type="text" name="contributor_state"><input
				type="Submit" class="btn btn-dark">
		</form>
		<br> <div class="alert alert-primary" role="alert" id="alert"
		style="display: none">${badSearch}
	</div>
	<br>
	<script>
		if (document.getElementById('alert').innerHTML !== "") {
			document.getElementById('alert').removeAttribute("style");
		}
	</script>
</body>
</html>