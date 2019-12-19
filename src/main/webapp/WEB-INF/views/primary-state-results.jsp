<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>State Search Results - 2020 Primary</title>
<script>var simplemaps_usmap_mapdata={
		  main_settings: {
			    //General settings
					width: "responsive", //or 'responsive'
			    background_color: "#FFFFFF",
			    background_transparent: "yes",
			    popups: "detect",
			    
					//State defaults
					state_description: "State description",
			    state_color: "#88A4BC",
			    state_hover_color: "#3B729F",
			    state_url: "https://simplemaps.com",
			    border_size: 1.5,
			    border_color: "#ffffff",
			    all_states_inactive: "no",
			    all_states_zoomable: "no",
			    state_url: '',
			    
					//Location defaults
					location_description: "Location description",
			    location_color: "#FF0067",
			    location_opacity: 0.8,
			    location_hover_opacity: 1,
			    location_url: "",
			    location_size: 25,
			    location_type: "square",
			    location_border_color: "#FFFFFF",
			    location_border: 2,
			    location_hover_border: 2.5,
			    all_locations_inactive: "no",
			    all_locations_hidden: "yes",
			    
					//Label defaults
					label_color: "#ffffff",
			    label_hover_color: "#ffffff",
			    label_size: 22,
			    label_font: "Arial",
			    hide_labels: "no",
			   
					//Zoom settings
					manual_zoom: "no",
			    back_image: "no",
			    arrow_box: "no",
			    navigation_size: "40",
			    navigation_color: "#f7f7f7",
			    navigation_border_color: "#636363",
			    initial_back: "no",
			    initial_zoom: -1,
			    initial_zoom_solo: "no",
			    region_opacity: 1,
			    region_hover_opacity: 0.6,
			    zoom_out_incrementally: "yes",
			    zoom_percentage: 0.99,
			    zoom_time: 0.5,
			    
					//Popup settings
					popup_color: "white",
			    popup_opacity: 0.9,
			    popup_shadow: 1,
			    popup_corners: 5,
			    popup_font: "12px/1.5 Verdana, Arial, Helvetica, sans-serif",
			    popup_nocss: "yes",
			    
					//Advanced settings
					div: "map",
			    auto_load: "yes",
			    rotate: "0",
			    url_new_tab: "yes",
			    images_directory: "default",
			    import_labels: "no",
			    fade_time: 0.1,
			    link_text: "View Website"
			  },
			  state_specific: {
			    ${results}
			  },
			  locations: {
			    "0": {
			      name: "New York",
			      lat: 40.71,
			      lng: -74,
			      description: "default",
			      color: "default",
			      url: "default",
			      type: "default",
			      size: "default"
			    },
			    "1": {
			      name: "Anchorage",
			      lat: 61.2180556,
			      lng: -149.9002778,
			      color: "default",
			      type: "circle"
			    }
			  },
			  labels: {
			    NH: {
			      parent_id: "NH",
			      x: "932",
			      y: "183",
			      pill: "yes",
			      width: 45,
			      display: "all"
			    },
			    VT: {
			      parent_id: "VT",
			      x: "883",
			      y: "243",
			      pill: "yes",
			      width: 45,
			      display: "all"
			    },
			    RI: {
			      parent_id: "RI",
			      x: "932",
			      y: "273",
			      pill: "yes",
			      width: 45,
			      display: "all"
			    },
			    NJ: {
			      parent_id: "NJ",
			      x: "883",
			      y: "273",
			      pill: "yes",
			      width: 45,
			      display: "all"
			    },
			    DE: {
			      parent_id: "DE",
			      x: "883",
			      y: "303",
			      pill: "yes",
			      width: 45,
			      display: "all"
			    },
			    MD: {
			      parent_id: "MD",
			      x: "932",
			      y: "303",
			      pill: "yes",
			      width: 45,
			      display: "all"
			    },
			    DC: {
			      parent_id: "DC",
			      x: "884",
			      y: "332",
			      pill: "yes",
			      width: 45,
			      display: "all"
			    },
			    MA: {
			      parent_id: "MA",
			      x: "932",
			      y: "213",
			      pill: "yes",
			      width: 45,
			      display: "all"
			    },
			    CT: {
			      parent_id: "CT",
			      x: "932",
			      y: "243",
			      pill: "yes",
			      width: 45,
			      display: "all"
			    },
			    HI: {
			      parent_id: "HI",
			      x: 305,
			      y: 565,
			      pill: "yes"
			    },
			    AK: {
			      parent_id: "AK",
			      x: "113",
			      y: "495"
			    },
			    FL: {
			      parent_id: "FL",
			      x: "773",
			      y: "510"
			    },
			    ME: {
			      parent_id: "ME",
			      x: "893",
			      y: "85"
			    },
			    NY: {
			      parent_id: "NY",
			      x: "815",
			      y: "158"
			    },
			    PA: {
			      parent_id: "PA",
			      x: "786",
			      y: "210"
			    },
			    VA: {
			      parent_id: "VA",
			      x: "790",
			      y: "282"
			    },
			    WV: {
			      parent_id: "WV",
			      x: "744",
			      y: "270"
			    },
			    OH: {
			      parent_id: "OH",
			      x: "700",
			      y: "240"
			    },
			    IN: {
			      parent_id: "IN",
			      x: "650",
			      y: "250"
			    },
			    IL: {
			      parent_id: "IL",
			      x: "600",
			      y: "250"
			    },
			    WI: {
			      parent_id: "WI",
			      x: "575",
			      y: "155"
			    },
			    NC: {
			      parent_id: "NC",
			      x: "784",
			      y: "326"
			    },
			    TN: {
			      parent_id: "TN",
			      x: "655",
			      y: "340"
			    },
			    AR: {
			      parent_id: "AR",
			      x: "548",
			      y: "368"
			    },
			    MO: {
			      parent_id: "MO",
			      x: "548",
			      y: "293"
			    },
			    GA: {
			      parent_id: "GA",
			      x: "718",
			      y: "405"
			    },
			    SC: {
			      parent_id: "SC",
			      x: "760",
			      y: "371"
			    },
			    KY: {
			      parent_id: "KY",
			      x: "680",
			      y: "300"
			    },
			    AL: {
			      parent_id: "AL",
			      x: "655",
			      y: "405"
			    },
			    LA: {
			      parent_id: "LA",
			      x: "550",
			      y: "435"
			    },
			    MS: {
			      parent_id: "MS",
			      x: "600",
			      y: "405"
			    },
			    IA: {
			      parent_id: "IA",
			      x: "525",
			      y: "210"
			    },
			    MN: {
			      parent_id: "MN",
			      x: "506",
			      y: "124"
			    },
			    OK: {
			      parent_id: "OK",
			      x: "460",
			      y: "360"
			    },
			    TX: {
			      parent_id: "TX",
			      x: "425",
			      y: "435"
			    },
			    NM: {
			      parent_id: "NM",
			      x: "305",
			      y: "365"
			    },
			    KS: {
			      parent_id: "KS",
			      x: "445",
			      y: "290"
			    },
			    NE: {
			      parent_id: "NE",
			      x: "420",
			      y: "225"
			    },
			    SD: {
			      parent_id: "SD",
			      x: "413",
			      y: "160"
			    },
			    ND: {
			      parent_id: "ND",
			      x: "416",
			      y: "96"
			    },
			    WY: {
			      parent_id: "WY",
			      x: "300",
			      y: "180"
			    },
			    MT: {
			      parent_id: "MT",
			      x: "280",
			      y: "95"
			    },
			    CO: {
			      parent_id: "CO",
			      x: "320",
			      y: "275"
			    },
			    UT: {
			      parent_id: "UT",
			      x: "223",
			      y: "260"
			    },
			    AZ: {
			      parent_id: "AZ",
			      x: "205",
			      y: "360"
			    },
			    NV: {
			      parent_id: "NV",
			      x: "140",
			      y: "235"
			    },
			    OR: {
			      parent_id: "OR",
			      x: "100",
			      y: "120"
			    },
			    WA: {
			      parent_id: "WA",
			      x: "130",
			      y: "55"
			    },
			    ID: {
			      parent_id: "ID",
			      x: "200",
			      y: "150"
			    },
			    CA: {
			      parent_id: "CA",
			      x: "79",
			      y: "285"
			    },
			    MI: {
			      parent_id: "MI",
			      x: "663",
			      y: "185"
			    },
			    PR: {
			      parent_id: "PR",
			      x: "620",
			      y: "545"
			    },
			    GU: {
			      parent_id: "GU",
			      x: "550",
			      y: "540"
			    },
			    VI: {
			      parent_id: "VI",
			      x: "680",
			      y: "519"
			    },
			    MP: {
			      parent_id: "MP",
			      x: "570",
			      y: "575"
			    },
			    AS: {
			      parent_id: "AS",
			      x: "665",
			      y: "580"
			    }
			  }
			};</script>
<script src="usmap.js"></script>

<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.4.1/litera/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-pLgJ8jZ4aoPja/9zBSujjzs7QbkTKvKw1+zfKuumQF9U+TH3xv09UUsRI52fS+A6"
	crossorigin="anonymous">
<style type="text/css">
.tt_sm {
	border-radius: 5px;
	box-shadow: 3px 3px 4px rgba(0, 0, 0, .5);
	z-index: 1000000;
	background-color: white;
	padding: 7px;
	opacity: 0.9;
	color: black;
}

.tt_name_sm {
	float: left;
	font-weight: bold
}

.xmark_sm {
	float: right;
	margin-left: 5px;
	cursor: pointer;
	line-height: 0px;
}

.tt_custom_sm {
	
}

.tt_mobile_sm {
	margin-top: 5px;
}

.btn_simplemaps {
	color: black;
	text-decoration: none;
	background: #ffffff;
	padding: 5px 5px;
	margin: 0;
	width: 100%;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	line-height: 1.43;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	border: 1px solid;
	border-radius: 4px;
}

.btn_simplemaps:hover {
	text-decoration: underline;
}

#map {
	border: solid 2pt;
}

#title {
	border-bottom: solid 2pt;
}
</style>
</head>
<body>
	<ul class="nav nav-tabs bg-dark navbar-dark">
		<li class="nav-item"><a class="nav-link active" href="#"><strong>2020 Primary: State Search</strong></a>
		</li>
		<li class="nav-item"><a class="nav-link" href="/"
			style="color: white;">Home</a></li>
			<li class="nav-item"><a class="nav-link" href="/about"
			style="color: white;">About</a></li>
			<li class="nav-item"><a class="nav-link" href="/contact"
			style="color: white;">Contact</a></li>
	</ul>
	<div class="container">
		<br>
		<div class="row">
			<div class="col-lg-12 text-center" id="title">
				<h1>2020 Primary: Contributions by State</h1>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-lg-12">
				<h5>
					Hover over any of the states and U.S. territories in the map below
					to see details about the top fundraiser among the 2020
					primary candidates (including Democrats, Republicans, and other parties).
				</h5>
				<br>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div>
					<div id="map"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
	<script src="https://cdn.jsdelivr.net/npm/moment@2.24.0/moment.min.js"></script>
</body>
</html>