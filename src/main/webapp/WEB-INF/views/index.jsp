<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
<style>
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
	height: 100%;
}
/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}
</style>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

</head>
<body>

	<div class="container">

		<div class="jumbotron jumbotron-fluid">
			<div class="container">
				<h1 class="display-4">Follow the $</h1>
			</div>
		</div>

		<div class="tab-content" id="myTabContent">

			<!-- <div class="tab-pane fade show active" id="home" role="tabpanel"
				aria-labelledby="home-tab"></div> -->
			<!-- <li class="nav-item"><a class="nav-link active show"
				href="#home">Home</a></li> -->

			<ul class="nav nav-tabs">
				<li class="nav-item dropdown">
					<div class="tab-pane fade show active" id="home" role="tabpanel"
						aria-labelledby="home-tab"></div>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"
					role="button" aria-haspopup="true" aria-expanded="false">Search Options</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="#contributor_name">Candidate
							Donations</a> <a class="dropdown-item" href="#location_name">Location
							Donations</a> <a class="dropdown-item" href="#location_comparison">Location
							Comparison</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#biggest_winner">Biggest Winner</a>
						<a class="dropdown-item" href="#biggest_loser">Biggest Loser</a>
					</div></li>
			</ul>
		</div>
		<div></div>

		<div class="tab-pane fade">
		<form action="/find-contributor">
			<label>Candidate Donations</label><input type="text"
				name="contributor_name" id="contributor_name" style=""> <label>City</label><input
				type="text" name="contributor_name"> <label>State</label><input
				type="text" name="contributor_name"> <label>Zip Code</label><input
				type="text" name="contributor_state" required> <input
				type="Submit" onclick="showInput();" class="btn btn-dark">
		</form>
		<form action="/test-search-historical">
			<label>Location Donations</label><input type="text"
				id="location_name" name="location_name"><input type="text"
				name="location_name"> <label>City</label><input type="text"
				name="location_name"> <label>State</label> <input
				type="text" name="location_name"> <label>Zip Code</label><input
				type="text" name="contributor_state" required> <input
				type="Submit" class="btn btn-dark">

		</form>
		<form action="compare-location-search-results">
			<label>Location Comparer</label><input type="text"
				id="location_comparison" name="location_comparison"> <label>City</label><input
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
			<label>Biggest Winner</label><input type="text" id="biggest_winner"
				name="biggest_winner"> <label>Candidate Name</label><input
				type="text" name="biggest_winner"> <label>City</label><input
				type="text" name="biggest_winner"> <label>State</label> <input
				type="text" name="biggest_winner"> <label>Zip Code</label><input
				type="text" name="biggest_winner" required> <input
				type="Submit" class="btn btn-dark">

		</form>
		<form>
			<label>Biggest Loser</label><input type="text" id="biggest_loser"
				name="biggest_loser"> <label>City</label><input type="text"
				name="biggest_loser"> <label>Biggest loser</label><input
				type="text" name="biggest_loser"> <label>Candidate
				Name</label> <input type="text" name="biggest_loser"> <label>City</label>
			<input type="text" name="biggest_loser"> <label>State</label>
			<input type="text" name="biggest_loser"> <label>Zip
				Code</label><input type="text" name="biggest_loser" required> <input
				type="Submit" class="btn btn-dark">

		</form>

		</div>


		<br>
		<div class="alert alert-primary" role="alert" id="alert"
			style="display: none">${badSearch}</div>
		<br>

	</div>
	<div id="map"></div>
	<script>
		var map;
		function initMap() {
			map = new google.maps.Map(document.getElementById('map'), {
				center : {
					lat : 44.96,
					lng : -103.76
				},
				zoom : 5
			});

			//getting massive string containing all state IDs from EL tag
			var stateIds = "${idString}";
			//getting similar string with state codes from EL tag
			var stateCodes = "${stateCodes}";

			//splitting these strings up into arrays
			const idArr = stateIds.split(" ");
			var stateCodeArr = stateCodes.split(" ");

			//running through every state ID and requesting data on its borders from OpenStreetMap API
			for (var i = 0; i < idArr.length; i++) {
				const thisId = idArr[i].toString();

				const stateCode = stateCodeArr[i].toString();

				var request = new XMLHttpRequest();
				request
						.open('GET',
								'https://nominatim.openstreetmap.org/reverse?format=json&osm_id='
										+ thisId
										+ '&osm_type=R&polygon_geojson=1.json',
								true);
				request.onload = function() {
					//this code runs when the request is returned
					var json = {
						"type" : "FeatureCollection",
						"features" : [ {
							"type" : "Feature",
							"geometry" : {
								"type" : "Polygon"
							}
						} ]
					};

					//State line data goes into this variable
					var data = JSON.parse(this.response);

					var polyType = data.geojson.type;
					if (polyType == "MultiPolygon") {
						data.geojson.coordinates[0] = data.geojson.coordinates[0][0];
					}
					//Iterates once for every array in this state's data.geojson.coordinates
					for (var a = 0; a < data.geojson.coordinates.length; a++) {

						//don't ask.......
						var coords = data.geojson.coordinates[a];
						if (coords.length == 1) {
							coords = coords[0];
						}

						var polygon = [];

						//Creating a new polygon for everything in coords
						var coordLen = coords.length;
						for (var c = 0; c < coordLen; c++) {
							polygon.push({
								lat : coords[c][1],
								lng : coords[c][0]
							})
						}

						//Adding polygon to wrapper variable to be added to Google Maps along with styling
						var thisPoly = new google.maps.Polygon({
							paths : polygon,
							strokeColor : '#FF0000',
							strokeOpacity : 0.8,
							strokeWeight : 1,
							fillColor : '#FF0000',
							fillOpacity : 0.35
						});

						//Adding wrapper variable to map
						thisPoly.setMap(map);

						//Code for what happens when map is clicked
						google.maps.event
								.addListener(
										thisPoly,
										'click',
										function(event) {
											alert(stateCode);
											window.location = "/load-state-stats-page?stateName="
													+ stateCode;
										});

						json.features[0].geometry.type = data.geojson.type;
						json.features[0].geometry.coordinates = data.geojson.coordinates;

					}//Ending iteration for each member array of Coordinates
				}//Ending iteration for each state ID
				request.send();
			}
			map.data.addListener('click', function(event) {
				console.log(map.data);
			});
		}
		function clickSelection() {
			alert("Clicked");
		}
	</script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=${apiKey }&callback=initMap"
		async defer></script>
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