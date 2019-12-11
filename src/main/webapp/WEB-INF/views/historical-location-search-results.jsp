<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Location Search Results</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet" href="location-result-style.css">
</head>
<body>
	<div id="grad">
		<ul class="nav nav-tabs bg-dark navbar-dark">
			<li class="nav-item"><a class="nav-link active" href="#">Active</a>
			</li>
			<li class="nav-item"><a class="nav-link" href="#"
				style="color: white;">Link</a></li>
		</ul>

		<div class="container">
			<br>
			<div class="row">
				<div class="col-lg-12 text-center">
					<h1>${location}</h1>
				</div>
			</div>
			<hr>
			<div class="row">
				<div class="col-lg-12 text-center">
					<h3>The majority of donors in ${location} supported:</h3>
				</div>
			</div>
			<hr>
			<div class="row">
				<div class="col-lg-6 text-center">
					<h6 class="win-lose-total">${total_winners}</h6>
					<p>winning candidates</p>
					<p>
						<button class="btn btn-dark" type="button" data-toggle="collapse"
							data-target="#collapse1" aria-expanded="false"
							aria-controls="collapse1">
							<small>see all winners ...</small>
						</button>
					</p>
					<div class="collapse" id="collapse1">
						<div class="card card-body">
							<c:forEach var="w" items="${winnerNames}" varStatus="i">
								<small>${i.count}. ${w}</small>
								<br>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="col-lg-6 text-center">
					<h6 class="win-lose-total">${total_losers}</h6>
					<p>losing candidates</p>
					<p>
						<button class="btn btn-dark" type="button" data-toggle="collapse"
							data-target="#collapse2" aria-expanded="false"
							aria-controls="collapse2">
							<small>see all losers ...</small>
						</button>
					</p>
					<div class="collapse" id="collapse2">
						<div class="card card-body">
							<c:forEach var="l" items="${loserNames}" varStatus="i">
								<small>${i.count}. ${l}</small>
								<br>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<hr>
			<div class="row">
				<div class="col-lg-12 text-center">
					<h3>Average Donations</h3>
				</div>
			</div>
			<hr>

			<div class="row">
				<div class="col-lg-12">
					<div class="row">

						<div class="col-lg-6 text-center">
							<div class="row">
								<div class="col-lg-12">
									<div class="row">
										<div class="col">
											<h4>To winners:</h4>
											<h6 class="win-lose-total">$${avg_winning_donation}</h6>
											<p>
												<button class="btn btn-dark" type="button"
													data-toggle="collapse" data-target="#collapse3"
													aria-expanded="false" aria-controls="collapse3">
													<small>See averages for all elections</small>
												</button>
											</p>
										</div>
										<div>
											<div class="collapse" id="collapse3">
												<div class="card card-body">
													<c:forEach var="rtot" items="${results}" varStatus="i">
														<small>${i.count}.
															$${String.format("%.2f",rtot.getAvgWinningDonation())}
															(${rtot.getWinnerName()}, ${rtot.getElectionYear()})</small>
														<br>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<hr>
							<div class="row">
								<div class="col-lg-12">
									<div class="row">
										<div class="col">
											<h4>To losers:</h4>
											<h6 class="win-lose-total">$${avg_losing_donation}</h6>
											<p>
												<button class="btn btn-dark" type="button"
													data-toggle="collapse" data-target="#collapse4"
													aria-expanded="false" aria-controls="collapse4">
													<small>See averages for all elections</small>
												</button>
											</p>
										</div>
										<div>
											<div class="collapse" id="collapse4">
												<div class="card card-body">
													<c:forEach var="lavg" items="${results}" varStatus="i">
														<small>${i.count}.
															$${String.format("%.2f",lavg.getAvgLosingDonation())}
															(${lavg.getLoserName()}, ${lavg.getElectionYear()})</small>
														<br>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-4">
							<canvas id="avgChart" width="400" height="400"></canvas>
						</div>
					</div>
					<br>
				</div>
			</div>


			<hr>
			<div class="row">
				<div class="col-lg-12 text-center">
					<h3>Largest Individual Donations</h3>
				</div>
			</div>
			<hr>
			<div class="row">
				<div class="col-lg-6 text-center">
					<h4>Winner</h4>
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
				</div>
				<div class="col-lg-6 text-center">
					<h4>Loser</h4>
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
				</div>
			</div>
			<hr>
			<div class="row">
				<div class="col-lg-12 text-center">
					<h3>Largest Cumulative Donation Totals</h3>
				</div>
			</div>
			<hr>
			<div class="row">
				<div class="col-lg-12">
					<div class="row">
						<div class="col-lg-6">
							<canvas id="myChart" width="100%" height="75%"></canvas>
						</div>
						<div class="col-lg-6 text-center">
							<div class="row">
								<div class="col-lg-12">
									<div class="row">
										<div class="col">
											<h4>Winner</h4>
											<h6 class="win-lose-total">$${largest_winner_total}</h6>
											<p>Donated to ${largest_total_winner_recipient}</p>
											<p>
												<button class="btn btn-dark" type="button"
													data-toggle="collapse" data-target="#collapse7"
													aria-expanded="false" aria-controls="collapse7">
													<small>See totals for all elections</small>
												</button>
											</p>
										</div>
										<div>
											<div class="collapse" id="collapse7">
												<div class="card card-body">
													<c:forEach var="rtot" items="${results}" varStatus="i">
														<small>${i.count}.
															$${String.format("%.2f",rtot.getWinnerTotalDonations())}
															(${rtot.getWinnerName()}, ${rtot.getElectionYear()})</small>
														<br>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<hr>
							<div class="row">
								<div class="col-lg-12">
									<div class="row">
										<div class="col">
											<h4>Loser</h4>
											<h6 class="win-lose-total">$${largest_loser_total}</h6>
											<p>Donated to ${largest_total_loser_recipient}</p>
											<p>
												<button class="btn btn-dark" type="button"
													data-toggle="collapse" data-target="#collapse8"
													aria-expanded="false" aria-controls="collapse8">
													<small>See totals for all elections</small>
												</button>
											</p>
										</div>
										<div>
											<div class="collapse" id="collapse8">
												<div class="card card-body">
													<c:forEach var="lavg" items="${results}" varStatus="i">
														<small>${i.count}.
															$${String.format("%.2f",lavg.getLoserTotalDonations())}
															(${lavg.getLoserName()}, ${lavg.getElectionYear()})</small>
														<br>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<br>
				</div>
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