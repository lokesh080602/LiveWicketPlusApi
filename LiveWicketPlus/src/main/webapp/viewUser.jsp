<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Details</title>
      <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        /* Centering the table */
        .table-container {
            width: 60%;
            margin: 0 auto; /* Centering the table horizontally */
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            font-size: 18px;
            text-align: left;
        }

        table th, table td {
            padding: 12px;
            border: 1px solid #ddd;
        }

        table th {
            background-color: #007BFF;
            color: #fff;
        }

        th {
            color: #fff;
        }

        .back-button {
            display: block;
            width: 200px;
            margin: 20px auto;
            padding: 10px;
            border-radius: 4px;
            background-color: #007BFF;
            color: white;
            text-align: center;
            text-decoration: none;
        }

        .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<c:if test="${empty sessionScope.loggedInUser}">
    <c:redirect url="login.jsp"/>
</c:if>
<h1>User Details</h1>
<c:if test="${not empty user}">
    <div class="table-container">
        <table>
            <tr>
                <th>Field</th>
                <th>Value</th>
            </tr>
            <tr>
                <td>Username:</td>
                <td>${user.username}</td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>${user.email}</td>
            </tr>
            <tr>
                <td>Favorite Teams:</td>
                <td>${user.favoriteTeams != null ? user.favoriteTeams.join(', ') : 'None'}</td>
            </tr>
            <tr>
                <td>Favorite Players:</td>
                <td>${user.favoritePlayers != null ? user.favoritePlayers.join(', ') : 'None'}</td>
            </tr>
        </table>
    </div>
</c:if>
<c:if test="${empty user}">
    <p>No user found with the provided ID.</p>
</c:if>
<br>
<a class="back-button" href="UserServlet?action=list">Back to User List</a>

</body>
</html>
