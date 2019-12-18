<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Data import settings</title>
</head>
<body>
<h1>Select a dataset to import:</h1>
To load a data category, please click on it below:<br>
<c:forEach var="cat" items="${categories }">
<a href="/save-data-to-db?categories=${cat }">${cat }</a><br>
</c:forEach>	


</body>
</html>