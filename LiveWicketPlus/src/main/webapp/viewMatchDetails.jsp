<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Match Details</title>
  <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
<link rel="stylesheet" type="text/css" href="styles.css">
<style>
 /* Centering the table */
        .table-container {
            width: 60%;
            margin: 0 auto; /* Centering the table horizontally */
        }
table {
	width: 50%;
	border-collapse: collapse;
	margin-left: auto;
	margin-right:auto;
	font-size: 18px;
	text-align: left;
}

table th, table td {
	padding: 12px;
	border: 1px solid #ddd;
}


th{
color :  #007BFF;
}
</style>
</head>
<body>
<c:if test="${empty sessionScope.loggedInUser}">
    <c:redirect url="login.jsp"/>
</c:if>

	<h1>Match Details</h1>
	<c:if test="${not empty match}">
		<table class="center">
			<tr>
				<th>Field</th>
				<th>Value</th>
			</tr>
			<tr>
				<td>Match ID:</td>
				<td>${match.matchId}</td>
			</tr>
			<tr>
				<td>Team A:</td>
				<td>${match.teamA}</td>
			</tr>
			<tr>
				<td>Team B:</td>
				<td>${match.teamB}</td>
			</tr>
			<tr>
				<td>Score Team A:</td>
				<td>${match.scoreTeamA}</td>
			</tr>
			<tr>
				<td>Score Team B:</td>
				<td>${match.scoreTeamB}</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${empty match}">
		<p>No match found with the provided ID.</p>
	</c:if>
	<br>
<a style="text-align:center;" href="MatchServlet?action=list">Back to Match List</a>

</body>
</html>
