<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Location Search Results</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.4.1/litera/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-pLgJ8jZ4aoPja/9zBSujjzs7QbkTKvKw1+zfKuumQF9U+TH3xv09UUsRI52fS+A6"
	crossorigin="anonymous">
</head>
<body>
	<ul class="nav nav-tabs bg-dark navbar-dark">
		<li class="nav-item"><a class="nav-link active" href="#"><strong>2020 Primary: City Search</strong></a>
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
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-12 text-center">
				<h1>${location}</h1>
				<br>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-lg-12 text-center" style="border-bottom: 2pt solid;">
				<h3>The majority of donations from ${location} went to:</h3>
				<br>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-4 text-center align-self-center">
				<div class="row">
					<div class="col-lg-12">
						<h4>${majname}</h4>
						<h6>(${parties.get(winner.getCandidateName())})</h6>
						<img alt="" src="${urls.get(winner.getCandidateName())}" style="max-height: 200px;"> <br>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-lg-12">
						<h6>
							Donations from ${location} accounted for <strong>${String.format("%.2f", winner.getPercentDonationsForState())}%</strong>
							of the funds ${majname} raised in this state, and <strong>${String.format("%.2f", winner.getPercentTotalDonations())}%</strong>
							of their total funds raised nationwide.
						</h6>
					</div>
				</div>
			</div>
			<div class="col-lg-8" style="border-left: solid 2pt;">
				<br>
				<div class="row">
					<div class="col-lg-4 text-center">
						<h6>Total Donations:</h6>
						<p>$${totWinDon}</p>
					</div>
					<div class="col-lg-4 text-center">
						<h6>Largest Donation:</h6>
						<p>$${bigWinDon}</p>
					</div>
					<div class="col-lg-4 text-center">
						<h6>Average Donation:</h6>
						<p>$${avgWinDon}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<canvas id="myChart" width="100%" max-height="200px"></canvas>
						<br>
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="border-bottom: solid 2pt;">
			<div class="col-lg-12 text-center">
				<br>
				<h3>Information for Other Candidates</h3>
				<br>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12 text-center">
				<br>
				<h6>Click any of the buttons below to view expanded information for the candidate. Click the button again to collapse the information again.</h6>
				<c:forEach var="r" items="${result}" varStatus="i">
					<c:if test="${!r.getCandidateName().equals(majname)}">
						<button class="btn btn-dark" type="button" data-toggle="collapse"
							data-target="#collapse${i.count}" aria-expanded="false"
							aria-controls="collapse${i.count}" style="margin: 5px;">
							<small>${r.getCandidateName()}</small>
						</button>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<br>
		<c:forEach var="r" items="${result}" varStatus="i">
			<c:if test="${!r.getCandidateName().equals(majname)}">
				<div class="collapse" id="collapse${i.count}">
					<div class="row" style="border-top: 2pt solid;">
						<div class="col-lg-4 text-center align-self-center">
							<div class="row">
								<div class="col-lg-12">
									<h4>${r.getCandidateName()}</h4>
									<h6>(${parties.get(r.getCandidateName())})</h6>
									<img alt="" src="${urls.get(r.getCandidateName())}"
										style="height: 200px;">
								</div>
							</div>
							<br>
							<div class="row">
								<div class="col-lg-12">
									<p>
										Donations from ${location} accounted for <strong>${String.format("%.2f", r.getPercentDonationsForState())}%</strong>
										of the funds ${r.getCandidateName()} raised in this state, and <strong>${String.format("%.2f", r.getPercentTotalDonations())}%</strong>
										of their total funds raised nationwide.
									</p>
								</div>
							</div>
						</div>

						<div class="col-lg-8" style="border-left: solid 2pt;">
							<br>
							<div class="row">
								<div class="col-lg-4 text-center">
									<h6>Total Donations:</h6>
									<p>$${String.format("%,.2f", r.getTotalSumDonations())}</p>
								</div>
								<div class="col-lg-4 text-center">
									<h6>Largest Donation:</h6>
									<p>$${String.format("%,.2f", r.getLargestDonation())}</p>
								</div>
								<div class="col-lg-4 text-center">
									<h6>Average Donation:</h6>
									<p>$${String.format("%,.2f", r.getAvgDonation())}</p>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12 text-center">
									<canvas id="chart${i.count}" width="200px" height="100px"></canvas>
									<br>
								</div>
							</div>
						</div>
						<br>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
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
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
	<script src="https://cdn.jsdelivr.net/npm/moment@2.24.0/moment.min.js"></script>
	<c:forEach var="r" items="${result}" varStatus="i">
		<c:if test="${!r.getCandidateName().equals(majname)}">
			<script>
			var ctx = document.getElementById('chart${i.count}').getContext('2d');

			// Define the data 
			var data1 = [${r.getDonationScatterData()}];
			// End Defining data
			var options = {
				responsive : true, // Instruct chart js to respond nicely.
				maintainAspectRatio : true, // Add to prevent default behaviour of full-width/height
				scales : {
					xAxes : [ {
						ticks : {
							beginAtZero : false,
							 callback: function(value, index, values) {
								 var str = value.toString();
								 var mtn = String(str.substring(5,7));
								 var day = String(str.substring(7,9));
								 console.log('Month' + mtn);
								 console.log('Day' + day);
								 if (day === '') {
									 day = String('01');
								 }
								 if (mtn === '') {
									 mtn = String('01');
								 }
								 var rtrn = String(str.substring(0,4) + '-' + mtn + '-' + day);
									 return rtrn;
					            }
						}
					} ]
				}
			};

			// End Defining data
			var chart${i.count} = new Chart(ctx, {
				type : 'scatter',
				data : {
					datasets : [ {
						label : "Donations to ${r.getCandidateName()} (Date, Amount)", // Name the series
						data : data1, // Specify the data values array          
						backgroundColor : '${colors.get(r.getCandidateName())}', // Add custom color background (Points and Fill)
					}]
				},
				options : options
			});
			</script>
		</c:if>
	</c:forEach>
	<script>
		var ctx = document.getElementById("myChart").getContext('2d');

		// Define the data 
		var data1 = [${scatDat}];
		var data2 = [${loserDonationData}];
		// End Defining data
		var options = {
			responsive : true, // Instruct chart js to respond nicely.
			maintainAspectRatio : true, // Add to prevent default behaviour of full-width/height
			scales : {
				xAxes : [ {
					ticks : {
						beginAtZero : false,
						 callback: function(value, index, values) {
							 var str = value.toString();
							 var mtn = String(str.substring(5,7));
							 var day = String(str.substring(7,9));
							 console.log('Month' + mtn);
							 console.log('Day' + day);
							 if (day === '') {
								 day = String('01');
							 }
							 if (mtn === '') {
								 mtn = String('01');
							 }
							 var rtrn = String(str.substring(0,4) + '-' + mtn + '-' + day);
								 return rtrn;
				            }
					}
				} ]
			}
		};

		// End Defining data
		var myChart = new Chart(ctx, {
			type : 'scatter',
			data : {
				datasets : [ {
					label : "Donations to ${majname} (Date, Amount)", // Name the series
					data : data1, // Specify the data values array          
					backgroundColor : '#0071cd', // Add custom color background (Points and Fill)
				}]
			},
			options : options
		});
	</script>
</body>
</html>