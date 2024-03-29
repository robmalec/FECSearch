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
	height: 300px;
	width: 100%;
	outline: solid 4pt;
}

.pagetitle {
	border-bottom: solid 2pt;
}

#navhead {
	text-decoration: none !important;
	color: white !important;
}
/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
	/*  background-image: url(https://slack-imgs.com/?c=1&o1=ro&url=https%3A%2F%2Fimages.unsplash.com%2Fphoto-1447727214830-cbcbf097b52c%3Fixlib%3Drb-1.2.1%26q%3D80%26fm%3Djpg%26crop%3Dentropy%26cs%3Dtinysrgb%26w%3D1080%26fit%3Dmax);
background-size: cover;*/
}
</style>
<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.4.1/litera/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-pLgJ8jZ4aoPja/9zBSujjzs7QbkTKvKw1+zfKuumQF9U+TH3xv09UUsRI52fS+A6"
	crossorigin="anonymous">

</head>
<body>
	<ul class="nav nav-tabs bg-dark navbar-dark">
		<li class="nav-item"><a class="nav-link active" href="#"><strong>Home</strong></a></li>
		<li class="nav-item"><a class="nav-link" href="/about"
			style="color: white;">About</a></li>
		<li class="nav-item"><a class="nav-link" href="/contact"
			style="color: white;">Contact</a></li>
	</ul>
	<div class="container" id="container">
		<br> <br>
		<div class="row">
			<div class="col-lg-12 text-center pagetitle">
				<h1>Follow the $</h1>
				<br> <br>

			</div>
			<div class="pagetitle">
				<br>

				<h6>
					Welcome to <strong>Follow the $</strong>, a campaign finance web
					application. Choose one of the search options below to explore
					information about individual contributions to presidential general
					election candidates from 1980-2016, or presidential primary
					candidates for the 2020 primary.
				</h6>

				<br>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid">
			<div class="col-lg-12 text-center">
				<br>
				<h2>General Election Searches (1980-2016)</h2>
				<p>
					<small><strong>* Note: </strong>Data for elections prior to
						1996 is typically less robust than data for more recent elections,
						particularly when searching for information from smaller cities.</small>
				</p>
				<button type="button" class="btn btn-dark" data-toggle="collapse"
					data-target="#collapseGeneralSearch" aria-expanded="false"
					aria-controls="collapseGeneralSearch">
					<strong>+</strong> click to expand
				</button>
				<br> <br>
			</div>
		</div>
		<div class="row">
			<div class="collapse" id="collapseGeneralSearch">
				<div class="col-lg-12">
					<div class="row" style="border-bottom: 2pt solid;">
						<div class="col-lg-12">
							<br>
							<div class="page-header">
								<h3>Search Contributions by City</h3>
								<br>
							</div>
							<h6>Select one of the search options from the drop-down
								below to find information about donations from individual
								cities.</h6>
							<small><strong>* Note: </strong>Due to a bug with the
								OpenFEC API, city searches for the 2000 election cycle are
								currently unavailable. <a
								href="https://github.com/fecgov/openFEC/issues/4111">We've
									logged this bug with the FEC</a> and will update the search
								functionality once it has been fixed.</small> <br> <br>
							<ul class="nav">

								<li class="nav-item dropdown"><a
									class="nav-link dropdown-toggle  btn btn-dark" data-toggle="dropdown"
									href="#" role="button" aria-haspopup="true"
									aria-expanded="false" id="navhead">Search Options</a>
									<div class="dropdown-menu">
										<!-- <a class="dropdown-item" data-toggle="collapse"
						data-target="#firstForm">Candidate</a> -->
										<a class="dropdown-item" data-toggle="collapse"
											data-target="#secondForm">For All Elections</a> <a
											class="dropdown-item" data-toggle="collapse"
											data-target="#thirdForm">For Individual Elections</a> <a
											class="dropdown-item" data-toggle="collapse"
											data-target="#fourthForm">Compare Cities for Individual
											Elections</a> <a class="dropdown-item" data-toggle="collapse"
											data-target="#fifthForm">Compare search results to other
											data</a>
										<!--  <a class="dropdown-item" data-toggle="collapse"
								data-target="#fifthForm">Biggest Winner</a> <a
								class="dropdown-item" data-toggle="collapse"
								data-target="#sixthForm">Biggest Losers</a>-->

									</div></li>
							</ul>
							<div class="row">
								<div class="col-lg-12">
									<br>
									<form action="/historical-search-results">
										<div id="secondForm" class="collapse card card-body">
											<h5>Search for All Elections</h5>
											<hr>
											<div class="form-group">
												<label for="form1city">City</label><input type="text"
													name="city" required id="form1city" class="form-control">
											</div>
											<div class="form-group">

												<label for="form1state">State</label> <select
													class="form-control" name="state" id="form1state">

													<option value="AL" name="AL">Alabama</option>
													<option value="AK" name="AK">Alaska</option>
													<option value="AZ" name="AZ">Arizona</option>
													<option value="AR" name="AR">Arkansas</option>
													<option value="CA" name="CA">California</option>
													<option value="CO" name="CO">Colorado</option>
													<option value="CT" name="CT">Connecticut</option>
													<option value="DE" name="DE">Delaware</option>
													<option value="DC" name="DC">District Of Columbia</option>
													<option value="FL" name="FL">Florida</option>
													<option value="GA" name="GA">Georgia</option>
													<option value="HI" name="HI">Hawaii</option>
													<option value="ID" name="ID">Idaho</option>
													<option value="IL" name="IL">Illinois</option>
													<option value="IN" name="IN">Indiana</option>
													<option value="IA" name="IA">Iowa</option>
													<option value="KS" name="KS">Kansas</option>
													<option value="KY" name="KY">Kentucky</option>
													<option value="LA" name="LA">Louisiana</option>
													<option value="ME" name="ME">Maine</option>
													<option value="MD" name="MD">Maryland</option>
													<option value="MA" name="MA">Massachusetts</option>
													<option value="MI" name="MI">Michigan</option>
													<option value="MN" name="MN">Minnesota</option>
													<option value="MS" name="MS">Mississippi</option>
													<option value="MO" name="MO">Missouri</option>
													<option value="MT" name="MT">Montana</option>
													<option value="NE" name="NE">Nebraska</option>
													<option value="NV" name="NV">Nevada</option>
													<option value="NH" name="NH">New Hampshire</option>
													<option value="NJ" name="NJ">New Jersey</option>
													<option value="NM" name="NM">New Mexico</option>
													<option value="NY" name="NY">New York</option>
													<option value="NC" name="NC">North Carolina</option>
													<option value="ND" name="ND">North Dakota</option>
													<option value="OH" name="OH">Ohio</option>
													<option value="OK" name="OK">Oklahoma</option>
													<option value="OR" name="OR">Oregon</option>
													<option value="PA" name="PA">Pennsylvania</option>
													<option value="RI" name="RI">Rhode Island</option>
													<option value="SC" name="SC">South Carolina</option>
													<option value="SD" name="SD">South Dakota</option>
													<option value="TN" name="TN">Tennessee</option>
													<option value="TX" name="TX">Texas</option>
													<option value="UT" name="UT">Utah</option>
													<option value="VT" name="VT">Vermont</option>
													<option value="VA" name="VA">Virginia</option>
													<option value="WA" name="WA">Washington</option>
													<option value="WV" name="WV">West Virginia</option>
													<option value="WI" name="WI">Wisconsin</option>
													<option value="WY" name="WY">Wyoming</option>
													<option value="GU" name="GU">Guam</option>
													<option value="AA" name="AA">Armed Forces Americas</option>
													<option value="AS" name="AS">American Samoa</option>
													<option value="AE" name="AE">Armed Forces Europe</option>
													<option value="AP" name="AP">Armed Forces Pacific</option>
													<option value="ZZ" name="ZZ">Foreign Countries</option>
													<option value="MP" name="MP">Northern Mariana
														Islands</option>
													<option value="PR" name="PR">Puerto Rico</option>
													<option value="VI" name="VI">Virgin Islands</option>
												</select> <br> <input type="Submit" class="btn btn-dark">
											</div>
										</div>

									</form>

									<br>
									<form action="/location-search-results">
										<div id="thirdForm" class="collapse card card-body">
											<h5>Search by Individual Elections</h5>
											<hr>
											<div class="form-group">
												<label for="form2city">City</label><input type="text"
													name="city" required class="form-control" id="form2city">
											</div>
											<div class="form-group">

												<label for="form2state">State</label> <select name="state"
													class="form-control" id="form2state">

													<option value="AL" name="AL">Alabama</option>
													<option value="AK" name="AK">Alaska</option>
													<option value="AZ" name="AZ">Arizona</option>
													<option value="AR" name="AR">Arkansas</option>
													<option value="CA" name="CA">California</option>
													<option value="CO" name="CO">Colorado</option>
													<option value="CT" name="CT">Connecticut</option>
													<option value="DE" name="DE">Delaware</option>
													<option value="DC" name="DC">District Of Columbia</option>
													<option value="FL" name="FL">Florida</option>
													<option value="GA" name="GA">Georgia</option>
													<option value="HI" name="HI">Hawaii</option>
													<option value="ID" name="ID">Idaho</option>
													<option value="IL" name="IL">Illinois</option>
													<option value="IN" name="IN">Indiana</option>
													<option value="IA" name="IA">Iowa</option>
													<option value="KS" name="KS">Kansas</option>
													<option value="KY" name="KY">Kentucky</option>
													<option value="LA" name="LA">Louisiana</option>
													<option value="ME" name="ME">Maine</option>
													<option value="MD" name="MD">Maryland</option>
													<option value="MA" name="MA">Massachusetts</option>
													<option value="MI" name="MI">Michigan</option>
													<option value="MN" name="MN">Minnesota</option>
													<option value="MS" name="MS">Mississippi</option>
													<option value="MO" name="MO">Missouri</option>
													<option value="MT" name="MT">Montana</option>
													<option value="NE" name="NE">Nebraska</option>
													<option value="NV" name="NV">Nevada</option>
													<option value="NH" name="NH">New Hampshire</option>
													<option value="NJ" name="NJ">New Jersey</option>
													<option value="NM" name="NM">New Mexico</option>
													<option value="NY" name="NY">New York</option>
													<option value="NC" name="NC">North Carolina</option>
													<option value="ND" name="ND">North Dakota</option>
													<option value="OH" name="OH">Ohio</option>
													<option value="OK" name="OK">Oklahoma</option>
													<option value="OR" name="OR">Oregon</option>
													<option value="PA" name="PA">Pennsylvania</option>
													<option value="RI" name="RI">Rhode Island</option>
													<option value="SC" name="SC">South Carolina</option>
													<option value="SD" name="SD">South Dakota</option>
													<option value="TN" name="TN">Tennessee</option>
													<option value="TX" name="TX">Texas</option>
													<option value="UT" name="UT">Utah</option>
													<option value="VT" name="VT">Vermont</option>
													<option value="VA" name="VA">Virginia</option>
													<option value="WA" name="WA">Washington</option>
													<option value="WV" name="WV">West Virginia</option>
													<option value="WI" name="WI">Wisconsin</option>
													<option value="WY" name="WY">Wyoming</option>
													<option value="GU" name="GU">Guam</option>
													<option value="AA" name="AA">Armed Forces Americas</option>
													<option value="AS" name="AS">American Samoa</option>
													<option value="AE" name="AE">Armed Forces Europe</option>
													<option value="AP" name="AP">Armed Forces Pacific</option>
													<option value="ZZ" name="ZZ">Foreign Countries</option>
													<option value="MP" name="MP">Northern Mariana
														Islands</option>
													<option value="PR" name="PR">Puerto Rico</option>
													<option value="VI" name="VI">Virgin Islands</option>
												</select>
											</div>

											<div class="form-group">

												<label for="electionYear">Election Year</label> <select
													class="form-control" name="electionYear" id="electionYear">

													<option>1980</option>
													<option>1984</option>
													<option>1988</option>
													<option>1992</option>
													<option>1996</option>
													<option>2004</option>
													<option>2008</option>
													<option>2016</option>
												</select>
											</div>
											<input type="Submit" class="btn btn-dark">
										</div>
									</form>
									<form action="compare-location-search-results">
										<div id="fourthForm" class="collapse card card-body">
											<h5>Compare Cities for Individual Elections</h5>
											<hr>
											<div class="form-row">
												<div class="col">
													<div class="form-group">
														<label for="form3city1">First City</label><input
															type="text" name="city1" required class="form-control"
															id="form3city1">
													</div>
													<div class="form-group">

														<label for="form3state1">State</label> <select
															class="form-control" name="state1" id="form3state1">

															<option value="AL" name="AL">Alabama</option>
															<option value="AK" name="AK">Alaska</option>
															<option value="AZ" name="AZ">Arizona</option>
															<option value="AR" name="AR">Arkansas</option>
															<option value="CA" name="CA">California</option>
															<option value="CO" name="CO">Colorado</option>
															<option value="CT" name="CT">Connecticut</option>
															<option value="DE" name="DE">Delaware</option>
															<option value="DC" name="DC">District Of
																Columbia</option>
															<option value="FL" name="FL">Florida</option>
															<option value="GA" name="GA">Georgia</option>
															<option value="HI" name="HI">Hawaii</option>
															<option value="ID" name="ID">Idaho</option>
															<option value="IL" name="IL">Illinois</option>
															<option value="IN" name="IN">Indiana</option>
															<option value="IA" name="IA">Iowa</option>
															<option value="KS" name="KS">Kansas</option>
															<option value="KY" name="KY">Kentucky</option>
															<option value="LA" name="LA">Louisiana</option>
															<option value="ME" name="ME">Maine</option>
															<option value="MD" name="MD">Maryland</option>
															<option value="MA" name="MA">Massachusetts</option>
															<option value="MI" name="MI">Michigan</option>
															<option value="MN" name="MN">Minnesota</option>
															<option value="MS" name="MS">Mississippi</option>
															<option value="MO" name="MO">Missouri</option>
															<option value="MT" name="MT">Montana</option>
															<option value="NE" name="NE">Nebraska</option>
															<option value="NV" name="NV">Nevada</option>
															<option value="NH" name="NH">New Hampshire</option>
															<option value="NJ" name="NJ">New Jersey</option>
															<option value="NM" name="NM">New Mexico</option>
															<option value="NY" name="NY">New York</option>
															<option value="NC" name="NC">North Carolina</option>
															<option value="ND" name="ND">North Dakota</option>
															<option value="OH" name="OH">Ohio</option>
															<option value="OK" name="OK">Oklahoma</option>
															<option value="OR" name="OR">Oregon</option>
															<option value="PA" name="PA">Pennsylvania</option>
															<option value="RI" name="RI">Rhode Island</option>
															<option value="SC" name="SC">South Carolina</option>
															<option value="SD" name="SD">South Dakota</option>
															<option value="TN" name="TN">Tennessee</option>
															<option value="TX" name="TX">Texas</option>
															<option value="UT" name="UT">Utah</option>
															<option value="VT" name="VT">Vermont</option>
															<option value="VA" name="VA">Virginia</option>
															<option value="WA" name="WA">Washington</option>
															<option value="WV" name="WV">West Virginia</option>
															<option value="WI" name="WI">Wisconsin</option>
															<option value="WY" name="WY">Wyoming</option>
															<option value="GU" name="GU">Guam</option>
															<option value="AA" name="AA">Armed Forces
																Americas</option>
															<option value="AS" name="AS">American Samoa</option>
															<option value="AE" name="AE">Armed Forces Europe</option>
															<option value="AP" name="AP">Armed Forces
																Pacific</option>
															<option value="ZZ" name="ZZ">Foreign Countries</option>
															<option value="MP" name="MP">Northern Mariana
																Islands</option>
															<option value="PR" name="PR">Puerto Rico</option>
															<option value="VI" name="VI">Virgin Islands</option>

														</select>
													</div>
												</div>
												<div class="col">
													<div class="form-group">
														<label for="form3city2">Second City</label> <input
															type="text" name="city2" class="form-control" required
															id="form3city2">
													</div>
													<div class="form-group">

														<label for="form3state2">State</label> <select
															class="form-control" name="state2" id="form3state2">

															<option value="AL" name="AL">Alabama</option>
															<option value="AK" name="AK">Alaska</option>
															<option value="AZ" name="AZ">Arizona</option>
															<option value="AR" name="AR">Arkansas</option>
															<option value="CA" name="CA">California</option>
															<option value="CO" name="CO">Colorado</option>
															<option value="CT" name="CT">Connecticut</option>
															<option value="DE" name="DE">Delaware</option>
															<option value="DC" name="DC">District Of
																Columbia</option>
															<option value="FL" name="FL">Florida</option>
															<option value="GA" name="GA">Georgia</option>
															<option value="HI" name="HI">Hawaii</option>
															<option value="ID" name="ID">Idaho</option>
															<option value="IL" name="IL">Illinois</option>
															<option value="IN" name="IN">Indiana</option>
															<option value="IA" name="IA">Iowa</option>
															<option value="KS" name="KS">Kansas</option>
															<option value="KY" name="KY">Kentucky</option>
															<option value="LA" name="LA">Louisiana</option>
															<option value="ME" name="ME">Maine</option>
															<option value="MD" name="MD">Maryland</option>
															<option value="MA" name="MA">Massachusetts</option>
															<option value="MI" name="MI">Michigan</option>
															<option value="MN" name="MN">Minnesota</option>
															<option value="MS" name="MS">Mississippi</option>
															<option value="MO" name="MO">Missouri</option>
															<option value="MT" name="MT">Montana</option>
															<option value="NE" name="NE">Nebraska</option>
															<option value="NV" name="NV">Nevada</option>
															<option value="NH" name="NH">New Hampshire</option>
															<option value="NJ" name="NJ">New Jersey</option>
															<option value="NM" name="NM">New Mexico</option>
															<option value="NY" name="NY">New York</option>
															<option value="NC" name="NC">North Carolina</option>
															<option value="ND" name="ND">North Dakota</option>
															<option value="OH" name="OH">Ohio</option>
															<option value="OK" name="OK">Oklahoma</option>
															<option value="OR" name="OR">Oregon</option>
															<option value="PA" name="PA">Pennsylvania</option>
															<option value="RI" name="RI">Rhode Island</option>
															<option value="SC" name="SC">South Carolina</option>
															<option value="SD" name="SD">South Dakota</option>
															<option value="TN" name="TN">Tennessee</option>
															<option value="TX" name="TX">Texas</option>
															<option value="UT" name="UT">Utah</option>
															<option value="VT" name="VT">Vermont</option>
															<option value="VA" name="VA">Virginia</option>
															<option value="WA" name="WA">Washington</option>
															<option value="WV" name="WV">West Virginia</option>
															<option value="WI" name="WI">Wisconsin</option>
															<option value="WY" name="WY">Wyoming</option>
															<option value="GU" name="GU">Guam</option>
															<option value="AA" name="AA">Armed Forces
																Americas</option>
															<option value="AS" name="AS">American Samoa</option>
															<option value="AE" name="AE">Armed Forces Europe</option>
															<option value="AP" name="AP">Armed Forces
																Pacific</option>
															<option value="ZZ" name="ZZ">Foreign Countries</option>
															<option value="MP" name="MP">Northern Mariana
																Islands</option>
															<option value="PR" name="PR">Puerto Rico</option>
															<option value="VI" name="VI">Virgin Islands</option>
														</select>
													</div>
												</div>
											</div>
											<div class="form-row">
												<div class="col">
													<div class="form-group">

														<label for="form3electionYear">Election Year</label> <select
															class="form-control" name="electionYear"
															id="form3electionYear">

															<option>1980</option>
															<option>1984</option>
															<option>1988</option>
															<option>1992</option>
															<option>1996</option>
															<option>2004</option>
															<option>2008</option>
															<option>2016</option>
														</select>
													</div>
												</div>
											</div>
											<input type="Submit" class="btn btn-dark">
										</div>
									</form>
									<form action="load-custom-data">
										<div id="fifthForm" class="collapse card card-body">
											<h2>Compare statewide prediction ability scores with
												other data</h2>
											<h3>Search range:</h3>
											<div class="form-group">
												<label for="stateSearchStartYear">Beginning year
													(1980 or later):</label><input type="text" name="beginYear"
													id="stateSearchStartYear">
											</div>
											<br> Ending year (2016 or earlier): <input type="text"
												name="endYear"><br> Data file (.csv): <input
												type="file" name="filePath" value="Select a file"><br>
											<input class="btn btn-dark" type="submit"
												value="Submit query">
										</div>
									</form>


									<!-- 
						<form>
							<div id="fifthForm" class="collapse">
								<label>Biggest Winner</label><br> <label>Candidate
									Name</label><br> <input type="text" name="name" required>
								<br> <label>City</label><br> <input type="text"
									name="city" required> <br> <label>State</label><br>
								<input type="text" name="state" required> <br> <br>
								<input type="Submit" class="btn btn-dark">
							</div>
						</form>

						<form>
							<div id="sixthForm" class="collapse">
								<label>Biggest Loser</label><br> <label>Candidate
									Name</label> <br> <input type="text" name="biggest_loser" required><br>
								<label>City</label><br> <input type="text" name="city"
									required> <br> <label>State</label><br> <input
									type="text" name="state" required><br> <input
									type="Submit" class="btn btn-dark">
							</div>
						</form> -->
									<br>
								</div>
							</div>
						</div>
					</div>



					<!-- 1980-2016 -->

					<div class="row">
						<div class="col-lg-12" style="border-bottom: 2pt solid;">
							<div class="row">
								<div class="col-lg-12">
									<br>
									<div class="page-header">
										<h3>Search Contributions by State</h3>
										<br>
									</div>
									<h6>Enter a start and end year (optional) and then click
										on any state below to see information about contributions from
										that state for any presidential election from 1980 to 2016:</h6>
									<div class="form-group">
										<label for="beginYear">Start Year:</label><input type="text"
											id="beginYear" class="form-control"></input>
									</div>
									<div class="form-group">
										<label for="endYear">End Year:</label><input type="text"
											id="endYear" class="form-control"></input>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12">
									<!--  Map placeholder -->
									<div id="map"></div>
								</div>
							</div>
							<br> <br> <br>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid">
			<div class="col-lg-12 text-center">
				<br>
				<h2>2020 Primary Election Searches</h2>
				<br>
				<button type="button" class="btn btn-dark" data-toggle="collapse"
					data-target="#collapsePrimary" aria-expanded="false"
					aria-controls="collapsePrimary">
					<strong>+</strong> click to expand
				</button>
				<br> <br>
			</div>
		</div>
		<div class="collapse" id="collapsePrimary">
			<div class="row">
				<div class="col-lg-8" style="border-right: 2pt solid;">
					<br>
					<h3>Search Contributions by City</h3>
					<br>
					<form action="/primary-location-search">
						<div class="form-group">
							<label for="formPrimaryCity">City</label> <input type="text"
								name="city" class="form-control" required id="formPrimaryCity">
						</div>
						<div class="form-group">
							<label for="formPrimaryState">State</label> <select
								class="form-control" name="state" id="formPrimaryState">
								<option value="AL" name="AL">Alabama</option>
								<option value="AK" name="AK">Alaska</option>
								<option value="AZ" name="AZ">Arizona</option>
								<option value="AR" name="AR">Arkansas</option>
								<option value="CA" name="CA">California</option>
								<option value="CO" name="CO">Colorado</option>
								<option value="CT" name="CT">Connecticut</option>
								<option value="DE" name="DE">Delaware</option>
								<option value="DC" name="DC">District Of Columbia</option>
								<option value="FL" name="FL">Florida</option>
								<option value="GA" name="GA">Georgia</option>
								<option value="HI" name="HI">Hawaii</option>
								<option value="ID" name="ID">Idaho</option>
								<option value="IL" name="IL">Illinois</option>
								<option value="IN" name="IN">Indiana</option>
								<option value="IA" name="IA">Iowa</option>
								<option value="KS" name="KS">Kansas</option>
								<option value="KY" name="KY">Kentucky</option>
								<option value="LA" name="LA">Louisiana</option>
								<option value="ME" name="ME">Maine</option>
								<option value="MD" name="MD">Maryland</option>
								<option value="MA" name="MA">Massachusetts</option>
								<option value="MI" name="MI">Michigan</option>
								<option value="MN" name="MN">Minnesota</option>
								<option value="MS" name="MS">Mississippi</option>
								<option value="MO" name="MO">Missouri</option>
								<option value="MT" name="MT">Montana</option>
								<option value="NE" name="NE">Nebraska</option>
								<option value="NV" name="NV">Nevada</option>
								<option value="NH" name="NH">New Hampshire</option>
								<option value="NJ" name="NJ">New Jersey</option>
								<option value="NM" name="NM">New Mexico</option>
								<option value="NY" name="NY">New York</option>
								<option value="NC" name="NC">North Carolina</option>
								<option value="ND" name="ND">North Dakota</option>
								<option value="OH" name="OH">Ohio</option>
								<option value="OK" name="OK">Oklahoma</option>
								<option value="OR" name="OR">Oregon</option>
								<option value="PA" name="PA">Pennsylvania</option>
								<option value="RI" name="RI">Rhode Island</option>
								<option value="SC" name="SC">South Carolina</option>
								<option value="SD" name="SD">South Dakota</option>
								<option value="TN" name="TN">Tennessee</option>
								<option value="TX" name="TX">Texas</option>
								<option value="UT" name="UT">Utah</option>
								<option value="VT" name="VT">Vermont</option>
								<option value="VA" name="VA">Virginia</option>
								<option value="WA" name="WA">Washington</option>
								<option value="WV" name="WV">West Virginia</option>
								<option value="WI" name="WI">Wisconsin</option>
								<option value="WY" name="WY">Wyoming</option>
								<option value="GU" name="GU">Guam</option>
								<option value="AA" name="AA">Armed Forces Americas</option>
								<option value="AS" name="AS">American Samoa</option>
								<option value="AE" name="AE">Armed Forces Europe</option>
								<option value="AP" name="AP">Armed Forces Pacific</option>
								<option value="ZZ" name="ZZ">Foreign Countries</option>
								<option value="MP" name="MP">Northern Mariana Islands</option>
								<option value="PR" name="PR">Puerto Rico</option>
								<option value="VI" name="VI">Virgin Islands</option>
							</select>
						</div>
						<input type="Submit" value="Search" class="btn btn-dark">
					</form>
					<br>
				</div>
				<div class="col-lg-4 align-self-center text-center">
					<h5>Click here to see data for all U.S. states and
						territories.</h5>
					<a href="/primary-states" class="btn btn-dark"><strong>Go</strong></a>
				</div>
			</div>
		</div>
	</div>



	<script>
		var map;
		function initMap() {
			map = new google.maps.Map(document.getElementById('map'), {
				center : {
					lat : 40.1748,
					lng : -115.0130
				},
				zoom : 3
			});
			//getting massive string containing all state IDs from EL tag
			var stateIds = "${idString}";
			//getting similar string with state codes from EL tag
			var stateCodes = "${stateCodes}";
			var stateOpacities = "${opacities}";
			//splitting these strings up into arrays
			const idArr = stateIds.split(" ");
			var stateCodeArr = stateCodes.split(" ");
			var opacityArr = stateOpacities.split(" ");
			//Variable for maximum opacity, if we set this to 1.0 it blocks out the entire map underneath the state data polygons
			const maxOpacity = parseFloat(1.3);
			//running through every state ID and requesting data on its borders from OpenStreetMap API
			for (var i = 0; i < idArr.length; i++) {
				const thisId = idArr[i].toString();
				console.log(thisId);
				const stateCode = stateCodeArr[i].toString();
				const thisOpacity = parseFloat(opacityArr[i]);
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
						//Checking to see if coords is in the form of a direct list of latlng points, or if the latlng points are nested within a second array
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
							strokeColor : 'ff00fb',
							strokeOpacity : (maxOpacity * thisOpacity),
							strokeWeight : 1,
							fillColor : '#ff00fb',
							fillOpacity : (maxOpacity * (thisOpacity - 0.4))
						});
						//Adding wrapper variable to map
						thisPoly.setMap(map);
						//Code for what happens when map is clicked
						google.maps.event
								.addListener(
										thisPoly,
										'click',
										function(event) {
											var beginYear = document
													.getElementById('beginYear').value;
											var endYear = document
													.getElementById('endYear').value;
											if (beginYear == "") {
												beginYear = 1980;
											}
											if (endYear == "") {
												endYear = 2016;
											}
											window.location = "/load-state-stats-page?stateCode="
													+ stateCode
													+ "&beginYear="
													+ beginYear
													+ "&endYear="
													+ endYear;
										});
						json.features[0].geometry.type = data.geojson.type;
						json.features[0].geometry.coordinates = data.geojson.coordinates;
					}//Ending iteration for each member array of Coordinates
				}//Ending iteration for each state ID
				request.send();
			}
		}
	</script>
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