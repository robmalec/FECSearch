<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Simple Map</title>
<meta name="viewport" content="initial-scale=1.0">
<meta charset="utf-8">
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
</head>
<body>
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
				if (polyType == "MultiPolygon"){
					data.geojson.coordinates[0] = data.geojson.coordinates[0][0];
				}
				//Iterates once for every array in this state's data.geojson.coordinates
				for (var a = 0; a < data.geojson.coordinates.length; a++) {

					//don't ask.......
					var coords = data.geojson.coordinates[a];
					if (coords.length == 1){
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
					google.maps.event.addListener(thisPoly, 'click',
							function(event) {
								alert(stateCode);
								window.location="/load-state-stats-page?stateName=" + stateCode;
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
</body>
</html>