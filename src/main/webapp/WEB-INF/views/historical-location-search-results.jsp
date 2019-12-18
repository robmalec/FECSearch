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
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-12 text-center">
				<br>
				<h3>The highest number of contributions from ${city} went to:</h3>
				<br>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-lg-6 text-center">
				<button class="btn btn-dark" type="button" data-toggle="collapse"
					data-target="#collapseWinners" aria-expanded="false"
					aria-controls="collapseWinners">
					<c:if test="${total_winners != 1}">
						<h4>${total_winners}</h4>
						<h6>Elected Presidents</h6>
					</c:if>
					<c:if test="${total_winners == 1}">
						<h4>${total_winners}</h4>
						<h6>Elected President</h6>
					</c:if>
				</button>
			</div>
			<div class="col-lg-6 text-center">
				<button class="btn btn-dark" type="button" data-toggle="collapse"
					data-target="#collapseLosers" aria-expanded="false"
					aria-controls="collapseLosers">
					<h4>${total_losers}</h4>
					<c:if test="${total_losers != 1 }">
						<h6>Losing Candidates</h6>
					</c:if>
					<c:if test="${total_losers == 1 }">
						<h6>Losing Candidate</h6>
					</c:if>
				</button>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-6 text-center">
				<div class="collapse" id="collapseWinners">
					<br>
					<c:forEach var="r" items="${winnerNames}" varStatus="i">
						<h6>${i.count}. ${r}</h6>
					</c:forEach>
				</div>
			</div>
			<div class="col-lg-6 text-center">
				<div class="collapse" id="collapseLosers">
					<br>
					<c:forEach var="r" items="${loserNames}" varStatus="i">
						<h6>${i.count}. ${r}</h6>
					</c:forEach>
				</div>
			</div>
		</div>
		<br>
		<div class="row"
			style="border-bottom: solid 2pt; border-top: solid 2pt;">
			<div class="col-lg-12 text-center">
				<br>
				<h3>Total Contributions</h3>
				<br>
			</div>
		</div>
		<div class="row" style="border-bottom: 2pt solid;">
			<div class="col-lg-2  text-center align-self-center">
				<br>
				<div class="row">
					<div class="col-lg-12">
						<h4>${largest_total_winner_recipient}</h4>
						<img src="${urls.get(largest_total_winner_recipient)}"
							style="max-height: 200px;">
					</div>
				</div>
				<br>
			</div>
			<div class="col-lg-6"
				style="border-left: 2pt solid; border-right: 2pt solid;">
				<div class="row">
					<div class="col-lg-12">
						<br>
						<h5>${largest_total_winner_recipient}
							raised <strong>$${largest_winner_total}</strong> in ${city}
							during the ${bigWinElectionYear} election cycle.
						</h5>
						<p>That was the highest total amount of funds raised for any
							winning presidential candidate in this city between 1980 and
							2016. Those contributions accounted for ${winnerPercentState} of
							${largest_total_winner_recipient}'s total funds raised in this
							state for the ${bigWinElectionYear} cycle, and
							${winnerPercentAllStates} of their funds raised nationwide.</p>
						<h6>Click any of the buttons below to see the total contributions
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
										<div class="col-sm-6">
											<h5>${r.getWinnerName()}</h5>
											<h6>$${String.format("%,.2f",r.getWinnerTotalDonations())}</h6>
											<img src="${urls.get(r.getWinnerName())}"
												style="max-height: 200px;">
										</div>
										<div class="col-sm-6">
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
			<div class="col-lg-4 align-self-center">
				<br>
				<canvas id="myChart" style="max-width: 100%; max-height: 200px;"></canvas>
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
			<div class="col-lg-8" style="border-right: 2pt solid;">
			<br>
				<h6>
					The average contribution made to winning candidates in ${city}  was<strong> $${avg_winning_donation}</strong>, while
					the average contribution to losing candidates was <strong>$${avg_losing_donation}</strong>.
				</h6>
				<ul class="list-group list-group-flush">
					<li class="list-group-item">${highestAvgDonationRecipient}'s contributions in ${highAvgYear} were the highest on average ($${String.format("%,.2f", highestAvgDonation)}).</li>
					<li class="list-group-item">${lowestAvgDonationRecipient}'s contributions in ${lowAvgYear} were the lowest on average ($${String.format("%,.2f", lowestAvgDonation)}).</li>
					<li class="list-group-item">${partyHigherAvg}</li>
				</ul>
				<br>
			</div>
			<div class="col-lg-4 align-self-center">
			<br>
				<canvas id="avgChart" style="max-width: 100%; max-height: 300 px;"></canvas>
				<br>
			</div>
		</div>
		<!-- 
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
 -->
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
				type : 'bar',
				
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
							ticks: {
								beginAtZero:true,
								 callback: function(value, index, values) {
						                return '$' + value;
						            }
							}
						} ],
						xAxes : [{
							ticks : {
								display:false,
								beginAtZero : true
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