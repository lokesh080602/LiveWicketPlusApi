<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update User Profile</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

    <div class="form-container">
        <h1>Update User Profile</h1>
        <form action="UserServlet" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${user.userId}">

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value="${user.username}" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${user.email}" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" value="${user.password}" required>

            <label for="favoriteTeams">Favorite Teams:</label>
            <input type="text" id="favoriteTeams" name="favoriteTeams"
                   value="${user.favoriteTeams}">

            <label for="favoritePlayers">Favorite Players:</label>
            <input type="text" id="favoritePlayers" name="favoritePlayers"
                   value="${user.favoritePlayers}">
            <input type="submit" value="Update Profile">
        </form>
        <br>
        <a href="profile.jsp">Back to Profile</a>
    </div>
</body>
</html>
