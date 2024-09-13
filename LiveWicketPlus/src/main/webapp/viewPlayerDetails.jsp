<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Player Details</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        /* Centering the content */
        .content-container {
            width: 60%;
            margin: 0 auto;
            text-align: center;
        }
        .player-photo {
            width: 150px; 
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #007BFF;
            margin-bottom: 20px; 
        }
        table {
            width: 80%;
            border-collapse: collapse;
            margin: 0 auto;
            font-size: 18px;
            text-align: left;
        }
        table th, table td {
            padding: 12px;
            border: 1px solid #ddd;
        }
        th {
            color: #007BFF;
        }
        .no-player {
            color: #ff0000;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <c:if test="${empty sessionScope.loggedInUser}">
        <c:redirect url="login.jsp"/>
    </c:if>

    <div class="content-container">
        <h1>Player Details</h1>
        <c:if test="${not empty player}">

            <img src="static/playerspic/${player.name}.png" alt="Player" class="player-photo"/>
            <table>
                <tr>
                    <th>Field</th>
                    <th>Value</th>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td>${player.name}</td>
                </tr>
                <tr>
                    <td>Age:</td>
                    <td>${player.age}</td>
                </tr>
                <tr>
                    <td>Nationality:</td>
                    <td>${player.nationality}</td>
                </tr>
                <tr>
                    <td>Team:</td>
                    <td>${player.team}</td>
                </tr>
                <tr>
                    <td>Role:</td>
                    <td>${player.role}</td>
                </tr>
                <tr>
                    <td>Batting Style:</td>
                    <td>${player.battingStyle}</td>
                </tr>
                <tr>
                    <td>Bowling Style:</td>
                    <td>${player.bowlingStyle}</td>
                </tr>
                <tr>
                    <td>Current Match Status:</td>
                    <td>${player.currentMatchStatus}</td>
                </tr>
            </table>
        </c:if>
        <c:if test="${empty player}">
            <p class="no-player">No player found with the provided ID.</p>
        </c:if>
        <br>
        <a href="PlayerServlet?action=list">Back to Player List</a>
    </div>
</body>
</html>
