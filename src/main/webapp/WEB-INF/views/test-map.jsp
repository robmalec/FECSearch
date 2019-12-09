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
          center: {lat: 44.96, lng: -103.76},
          zoom: 5
        });     
        

  	 
        var stateIds = "${idString}";
        var idArr = stateIds.split(" ");
        var thisId = -1;
        for (var i = 1; i < idArr.length; i++){
        	thisId = idArr[i];
        	
        	var request = new XMLHttpRequest();
            request.open('GET', 'https://nominatim.openstreetmap.org/reverse?format=json&osm_id='+thisId+'&osm_type=R&polygon_geojson=1.json', true);
            request.onload = function() {
            	 var json = { "type": "FeatureCollection",
           	            "features": [
           	              { "type": "Feature",
           	                 "geometry": {
           	                   "type": "Polygon"
           	                 }
           	              }
           	            ]
           	          };
            	var data = JSON.parse(this.response);
                

            	var coords = data.geojson.coordinates;
            	//alert(coords);
            	console.log(data);
            	
            	json.features[0].geometry.type = data.geojson.type;
            	json.features[0].geometry.coordinates = data.geojson.coordinates;
            	map.data.addGeoJson(json);
           	}
            request.send();
        }
      }
    </script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=${apiKey }&callback=initMap"
		async defer></script>
</body>
</html>