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
		<br>
		<div class="row">
			<div class="col-lg-12 text-center">
				<h4>This search requires a large data pull and
					may take approximately ${estimate} seconds to load. <br>Are you sure you want to proceed?</h4>
				<form action="confirmed-location-search-results">
					<input type="hidden" name="city" value="${city}"><input
						type="hidden" name="state" value="${state}"> <input
						type="hidden" name="electionYear" value="${electionYear}">
						<input
						type="hidden" name="b" value="true"><input
						type="Submit" class="btn btn-dark" value="Yes"> <a
						href="/" class="btn btn-dark">No, take me home</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>