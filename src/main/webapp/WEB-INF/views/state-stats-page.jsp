<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Info for ${stateName }</title>
<style>
html, body{
margin:0;
padding: 0;
}


/* #h5 {
	margin-top: 100px;
	margin-bottom: 100px;
	margin-right: 200px;
	margin-left: 200px;
} */
 #left {
margin-left: 20px;
	text-align:center;
	margin-top: 10px;
margin-top: 97px; 
	
}
.nav{

background-color: lightgray;

}

.nav-link{
color: white;
}
*a:hover{
color: darkblue;
}



 tr{
 	height: 50px;
 	padding-top: 20px;
 	border-right: 2px solid black;
 	border-left: 2px solid black;
 	border-top: 2px solid black;
 }
 
 
 #right{
 	margin-left:-20px;
 	padding-left: 30px;
 	
 	
 }
 table{
 margin-left: 20px;
 }
 
 b{
	    font-size: 1.063rem;
    font-weight: 400;
    line-height: 1.5;
    color: #343a40;
    text-align: center;
    margin-left: 20px;
   
 }
 .p{
 
 	border: 2px solid black;
 	margin-left: 20px;
 }
 .box{
 border: 2px solid black;
 margin-left: 20px;
 }
 td{
 	    font-size: 1.063rem;
    font-weight: 400;
    line-height: 1.5;
    color: #343a40;
    text-align: left;
    border-bottom: 2px solid black;
    }
    .presos{
    font-size: 28px;
    text-align:center;
    margin-right: 20px;
    padding-left: -30px;
    }
 a{
 text-decoration: none;
 text-emphasis: none;
 
 }
 h1{
 color: darkred;
 }
 
 h6{
 	color: darkblue;
 }

</style>
<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.4.1/litera/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-pLgJ8jZ4aoPja/9zBSujjzs7QbkTKvKw1+zfKuumQF9U+TH3xv09UUsRI52fS+A6"
	crossorigin="anonymous">
</head>
<body>

<ul class="nav justify-content-center">

		<li class="nav-item"><a class="nav-link" href="/">Home</a></li>
		<li class="nav-item"><a class="nav-link" href="/about">About</a></li>
		<li class="nav-item"><a class="nav-link" href="/contact">Contact</a></li>
	</ul>
	<br>
	
	<div class="row">
		<div class="col-lg-12 text-center pagetitle">
			<h1>${stateName } ${beginYear } - ${endYear }</h1>
			
			<h6><strong>${predictionScore }%</strong> of ${stateName }'s campaign donations
				went to winning presidential candidates.</h6>
			<br>
			<br>

		</div>
	</div>
	<div class="fullContent">
	<div class="row">
		<div class="col-lg-6" id="left">
			<p class="box"><b>Total funds raised:</b> <br>$${totalFunds }<br> </p> 
			<p class="box"><b>Total winning candidate funds raised:</b><br> $${totalWinningFunds }<br></p> 
			<p class="box"><b>Total losing candidate funds raised:</b><br> $${totalLosingFunds }</p><br>
			<p class="box"><b>Highest fundraising winner candidate:</b><br> ${bmw.getName() } with $${bmwBudget}</p><br>
			<p class="box"><b>Biggest money loser candidate:</b><br> ${bml.getName() } with $${bmlBudget}</p><br> 
			<p class="box"><b>Smallest money winner candidate:</b><br>${smw.getName() } with $${smwBudget}</p><br> 
			<p class="box"><b>Smallest money loser candidate:</b><br> ${sml.getName() } with $${smlBudget}</p><br>
		</div>

		<div class="col-lg-6" id="right">
			<h2 class="presos">Presidential Candidates by Fundraising Numbers</h2>
			<br>
			<table>
				<tr>
				
					<th>Candidate name</th>
					<th>Campaign year</th>
					<th>Funds raised in ${stateName }</th>
				</tr>
				<c:forEach var="cand" items="${candFundsList }">
					<tr>
					
						<td>${cand[0]}</td>
						<td>${cand[2] }</td>
						<td>$${cand[1]}</td>
						
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>





	<a href="/">Go back</a>
</body>
</html>