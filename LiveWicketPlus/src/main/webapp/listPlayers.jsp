<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Player List</title>
     <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
    
</head>
<body>
<c:if test="${empty sessionScope.loggedInUser}">
    <c:redirect url="login.jsp"/>
</c:if>
<h1>Player List</h1>
<table border="1">
    <thead>
        <tr>
            <th>Player ID</th>
            <th>Name</th>
            <th>Age</th>
            <th>Nationality</th>
            <th>Team</th>
            <th>Role</th>
            <th>Batting Style</th>
            <th>Bowling Style</th>
            <th>Current Match Status</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="player" items="${players}">
            <tr>
                <td>${player.playerId}</td>
                <td>${player.name}</td>
                <td>${player.age}</td>
                <td>${player.nationality}</td>
                <td>${player.team}</td>
                <td>${player.role}</td>
                <td>${player.battingStyle}</td>
                <td>${player.bowlingStyle}</td>
                <td>${player.currentMatchStatus}</td>
                <td>
                    <a style="text-align:center;"href="PlayerServlet?action=view&playerId=${player.playerId}">View</a> 
                     <c:if test="${sessionScope.loggedInUser.username == 'admin'}">|
                    <a style="text-align:center;" href="PlayerServlet?action=update&playerId=${player.playerId}">Update</a> |
                    <a style="text-align:center;"href="PlayerServlet?action=delete&playerId=${player.playerId}">Delete</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<br>
	<c:if test="${sessionScope.loggedInUser.username == 'admin'}">
<a href="addPlayer.jsp">Add New Player</a> |
</c:if>
<a href="home.jsp">Back to Home</a>

</body>
</html>
