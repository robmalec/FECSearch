<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.pagetitle {
	
}

p {
	margin-top: 100px;
	margin-bottom: 100px;
	margin-right: 200px;
	margin-left: 200px;
}

#carouselExampleIndicators {
	hieght: 500;
	width: 800;
	margin-top: 25px;
	margin-bottom: 150px;
	margin-right: 110px;
	margin-left: 155px;
}

#c2 {
color: grey;
}
</style>
<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.4.1/litera/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-pLgJ8jZ4aoPja/9zBSujjzs7QbkTKvKw1+zfKuumQF9U+TH3xv09UUsRI52fS+A6"
	crossorigin="anonymous">
	
	
</head>
<body>

	<!-- info about the app
	how calculation data we are showing
	info about us including github repos -->


	<ul class="nav justify-content-center">

		<li class="nav-item"><a class="nav-link" href="/">Home</a></li>
		<li class="nav-item"><a class="nav-link" href="/about">About</a></li>
		<li class="nav-item"><a class="nav-link" href="/contact">Contact</a></li>
	</ul>
	<br>

	<div class="row">
		<div class="col-lg-12 text-center pagetitle">
			<h3>About</h3>
			<br>

		</div>
		<div class="col-lg-12 text-center pagetitle">

			<p style="text-align: center;">Our data comes from individual
				contributions donated to candidates for primary and presidential elections, excluding PAC’s and Super PAC’s. We
				have pulled data from the FEC, Google Maps, and Open Street Map
				APIs to create algorithms that allow us to find the highest and
				lowest amount of donations by state, city, candidate, and election
				years.</p>

			<br>
		</div>
	</div>

	<div id="carouselExampleIndicators" class="carousel slide"
		data-ride="carousel">
		<ol class="carousel-indicators">
			<li data-target="#carouselExampleIndicators" data-slide-to="0"
				class="active"></li>
			<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
			<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
		</ol>
		<div class="carousel-inner">
			<div class="carousel-item active">
					<img src="https://images.unsplash.com/photo-1447727214830-cbcbf097b52c?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max" height="500" width="850" alt="First Slide">
					<div class="carousel-caption d-none d-md-block">
						<h5>Donations</h5>
						<p>Presidential and Primary</p>
				</div>
			</div>
			<div class="carousel-item" id="c2">
				<img src="https://images.unsplash.com/photo-1500496733680-167c3db69389?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1239&q=80" height="500" width="850" alt="Second Slide">
					<div class="carousel-caption d-none d-md-block">
						<h5>Following the $</h5>
						<p>Precise and fun calculations</p>
				</div>
				</div>
			<div class="carousel-item">
				<img src="https://images.unsplash.com/photo-1446776653964-20c1d3a81b06?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80" height="500" width="850" alt="Third Slide">
					<div class="carousel-caption d-none d-md-block">
						<h5>Cities across the US</h5>
						<p>Find any location you'd like</p>
				</div>
			</div>
		</div> 
		<a class="carousel-control-prev" href="#carouselExampleIndicators"
			role="button" data-slide="prev"> <span
			class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="sr-only">Previous</span>
		</a> <a class="carousel-control-next" href="#carouselExampleIndicators"
			role="button" data-slide="next"> <span
			class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="sr-only">Next</span>
		</a>
	</div>

	
	
	<script
		src="https://maps.googleapis.com/maps/api/js?key=${apiKey }&callback=initMap"
		async defer></script>

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