<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .profile-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #f9f9f9;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        .profile-details {
            margin-bottom: 20px;
        }
        .profile-details p {
            font-size: 18px;
            margin: 10px 0;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 10px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
        }
        .btn.logout {
            background-color: #dc3545;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .btn.logout:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
	<c:if test="${empty sessionScope.loggedInUser}">
		<c:redirect url="login.jsp" />
	</c:if>
    <div class="profile-container">
        <h1>User Profile</h1>

        <c:choose>
            <c:when test="${not empty sessionScope.loggedInUser}">  
                <div class="profile-details">
                    <p><strong>Username:</strong> ${sessionScope.loggedInUser.username}</p>
                    <p><strong>Email:</strong> ${sessionScope.loggedInUser.email}</p>
                    <!-- Add more user details here as needed -->
                    <a href="UserServlet?action=update&id=${sessionScope.loggedInUser.userId}" class="btn">Edit Profile</a>
                    <a href="logout" class="btn logout">Logout</a>
                </div>
            </c:when>  
        </c:choose>
    </div>
     <br>
    <a href="home.jsp" >Back to Home</a>
</body>
</html>
