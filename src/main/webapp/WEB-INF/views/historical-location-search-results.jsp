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
		<li class="nav-item"><a class="nav-link active" href="#"><strong>Presidential
					Elections: Historical City Search</strong></a></li>
		<li class="nav-item"><a class="nav-link" href="/"
			style="color: white;">Home</a></li>
	</ul>

	<div class="container">
		<br>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-12 text-center">
				<h1>${location}</h1>
				<br>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-12 text-center">
				<br>
				<h3>The majority of donors in ${location} supported:</h3>
				<br>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-lg-6 text-center">
				<button class="btn btn-dark" type="button" data-toggle="collapse"
					data-target="#collapse1" aria-expanded="false"
					aria-controls="collapse1">
					<h4>${total_winners}</h4>
					<h6>Elected Presidents</h6>
				</button>
			</div>
			<div class="col-lg-6 text-center">
				<button class="btn btn-dark" type="button" data-toggle="collapse"
					data-target="#collapse2" aria-expanded="false"
					aria-controls="collapse2">
					<h4>${total_losers}</h4>
					<h6>Losing Candidates</h6>
				</button>
			</div>
		</div>
		<br>
		<div class="row"
			style="border-bottom: solid 2pt; border-top: solid 2pt;">
			<div class="col-lg-12 text-center">
				<br>
				<h3>Total Donations</h3>
				<br>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-3  text-center" style="border-right: 2pt solid;">
				<br>
				<div class="row">
					<div class="col-lg-12  align-self-center">
						<h4>${largest_total_winner_recipient}</h4>
						<img src="${urls.get(largest_total_winner_recipient)}"
							style="max-height: 200px;">
					</div>
				</div>
				<br>
			</div>
			<div class="col-lg-6">
				<div class="row">
					<div class="col-lg-12">
						<br>
						<h5>${largest_total_winner_recipient}
							raised <strong>$${largest_winner_total}</strong> in ${location}
							during the ${bigWinElectionYear} election cycle.
						</h5>
						<p>That was the highest total amount of funds raised for any
							winning presidential candidate in this city between 1980 and
							2016. Those donations accounted for Percent of
							${largest_total_winner_recipient}'s total donations from ${state}
							for the ${bigWinElectionYear} cycle, and 2Percent of their
							donations nationwide.</p>
						<h6>Click any of the buttons below to see the total donations
							to winning and losing candidates from this city for other
							election cycles.</h6>
						<c:forEach var="r" items="${results}" varStatus="i">
							<c:if test="${r.getElectionYear() != bigWinElectionYear}">
								<button class="btn btn-dark" type="button"
									data-toggle="collapse" data-target="#collapseWinYear${i.count}"
									aria-expanded="false" aria-controls="collapseWinYear${i.count}"
									style="margin: 5px;">${r.getElectionYear()}</button>
							</c:if>
						</c:forEach>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-lg-12 text-center">
						<c:forEach var="r" items="${results}" varStatus="i">
							<c:if test="${r.getElectionYear() != bigWinElectionYear}">
								<div class="collapse" id="collapseWinYear${i.count}">
									<div class="row">
										<div class="col-lg-6">
											<h5>${r.getWinnerName()}</h5>
											<h6>$${String.format("%,.2f",r.getWinnerTotalDonations())}</h6>
											<img src="${urls.get(r.getWinnerName())}"
												style="max-height: 200px;">
										</div>
										<div class="col-lg-6">
											<h5>${r.getLoserName()}</h5>
											<h6>$${String.format("%,.2f",r.getLoserTotalDonations())}</h6>
											<img src="${urls.get(r.getLoserName())}"
												style="max-height: 200px;">
										</div>
									</div>
								</div>
							</c:if>
						</c:forEach>
						<br>
					</div>
				</div>
			</div>
			<div class="col-lg-3" style="border-left: 2pt solid;">
				<br>
				<div class="row">
					<div class="col-lg-12 text-center align-self-center">
						<canvas id="myChart" width="100%" height="200 px"></canvas>
					</div>
				</div>
				<br>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-12 text-center">
				<br>
				<h3>Average Donations</h3>
				<br>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-12">
				<br>
				<div class="row">
					<div class="col-lg-8" style="border-right: 2pt solid;">
						<h6>
							On average, donations made from ${location} to winning
							presidential candidates were <strong>$${avg_winning_donation}</strong>,
							while donations to losing candidates were <strong>$${avg_losing_donation}</strong>.
						</h6>
						<ul class="list-group list-group-flush">
							<li class="list-group-item">HighestAvgCand had the largest
								average donation ($money).</li>
							<li class="list-group-item">LowestAvgCand had the lowest
								average donation ($money.)</li>
							<li class="list-group-item">Party candidates have typically
								received higher average donations than OtherParty candidates,
								except for the elections in ElectionYear[].</li>
							<li class="list-group-item">Porta ac consectetur ac</li>
							<li class="list-group-item">Vestibulum at eros</li>
						</ul>
					</div>
					<div class="col-lg-4">
						<canvas id="avgChart" width="400" height="400"></canvas>
					</div>
				</div>
				<br>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-12 text-center">
				<br>
				<h3>Largest Individual Donations</h3>
				<br>
			</div>
		</div>
		<br>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-6 text-center">
				<h4>To a winning candidate:</h4>
				<h6 class="win-lose-total">$${largest_winning_donation}</h6>
				<p>Donated to ${largest_winner_recipient}</p>
				<p>
					<button class="btn btn-dark" type="button" data-toggle="collapse"
						data-target="#collapse5" aria-expanded="false"
						aria-controls="collapse5">
						<small>See largest donations for all elections</small>
					</button>
				</p>
				<div class="collapse" id="collapse5">
					<div class="card card-body">
						<c:forEach var="lavg" items="${results}" varStatus="i">
							<small>${i.count}.
								$${String.format("%.2f",lavg.getLargestWinningDonation())}
								(${lavg.getWinnerName()}, ${lavg.getElectionYear()})</small>
							<br>
						</c:forEach>
					</div>
				</div>
				<br>
			</div>
			<div class="col-lg-6 text-center">
				<h4>To a losing candidate:</h4>
				<h6 class="win-lose-total">$${largest_losing_donation}</h6>
				<p>Donated to ${largest_loser_recipient}</p>
				<p>
					<button class="btn btn-dark" type="button" data-toggle="collapse"
						data-target="#collapse6" aria-expanded="false"
						aria-controls="collapse6">
						<small>See largest donations for all elections</small>
					</button>
				</p>
				<div class="collapse" id="collapse6">
					<div class="card card-body">
						<c:forEach var="lavg" items="${results}" varStatus="i">
							<small>${i.count}.
								$${String.format("%.2f",lavg.getLargestLosingDonation())}
								(${lavg.getLoserName()}, ${lavg.getElectionYear()})</small>
							<br>
						</c:forEach>
					</div>
				</div>
				<br>
			</div>
		</div>

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
	<script>
			var ctx = document.getElementById('myChart').getContext('2d');
			var data = {
			}
			
			var myChart = new Chart(ctx, {
				type : 'horizontalBar',
				
				data : {
					labels : [${electionYears}],
					datasets : [ {
						label : 'Total Donations ($)',
						data : [${totalData}],
						backgroundColor : [ 'rgb(0,113,205)',
								'rgb(222,0,0)',
								'rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)'],
						borderWidth : 1
					} ]
				},
				options : {
					legend : {display:false},
					scales : {
						yAxes : [ {
							ticks : {
								display:false,
								beginAtZero : true
							}
						} ],
						xAxes : [{
							offset: false,
							ticks: {
								beginAtZero:true,
								 callback: function(value, index, values) {
						                return '$' + value;
						            }
							}
						}]
					}
				}
			});
		</script>
	<script>
			var ctx = document.getElementById('avgChart').getContext('2d');
			var data = {
			}
			
			var myChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : [${electionYears}],
					datasets : [ {
						label : 'Average Donations ($)',
						data : [${avgData}],
						backgroundColor : [ 'rgb(0,113,205)',
								'rgb(222,0,0)',
								'rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)','rgb(0,113,205)',
								'rgb(222,0,0)'],
						borderWidth : 1
					} ]
				},
				options : {
					legend : {display:false},
					scales : {
						yAxes : [ {
							ticks : {
								beginAtZero : true,
								 callback: function(value, index, values) {
						                return '$' + value;
						              
						            }
								
							}
						} ],
						xAxes : [{
							ticks: {
								display:false,
								fontSize:0,
								major: {
									fontSize:0
								}
							}
						}]
					}
				}
			});
		</script>
</body>
</html>