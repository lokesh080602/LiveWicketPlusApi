<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List Matches</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">    
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <c:if test="${empty sessionScope.loggedInUser}">
        <c:redirect url="login.jsp"/>
    </c:if>
    <h2>Existing Matches</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Match ID</th>
                <th>Team A</th>
                <th>Team B</th>
                <th>Score Team A</th>
                <th>Score Team B</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="match" items="${matches}">
                <tr>
                <td>${match.matchId}</td>
                <td>${match.teamA}</td>
                <td>${match.teamB}</td>
                <td>${match.scoreTeamA}</td>
                <td>${match.scoreTeamB}</td>
                <td>
                    <a href="MatchServlet?action=view&matchId=${match.matchId}">View</a> 
                     <c:if test="${sessionScope.loggedInUser.username == 'admin'}">|
                    <a href="MatchServlet?action=edit&matchId=${match.matchId}">Update</a> |
                    <a href="MatchServlet?action=delete&matchId=${match.matchId}" onclick="return confirm('Are you sure you want to delete this match?');">Delete</a>
                    </c:if>
                </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <a style="text-align:center;" href="home.jsp">Back to Home</a>
    	<c:if test="${sessionScope.loggedInUser.username == 'admin'}"> | 
    <a style="text-align:center;" href="addMatch.jsp">Add New Match</a>
    </c:if>
</body>
</html>

