<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Player Details</title>
     <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        body {
            text-align: center;
            font-family: Arial, sans-serif;
        }
        form {
            display: inline-block;
            text-align: left;
        }
        label {
            display: block;
            margin: 5px 0 2px;
        }
        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
        }
        input[type="submit"] {
            padding: 10px 20px;
            font-size: 16px;
        }
        a {
    text-align:center;
}
    </style>
</head>
<body>
<c:if test="${empty sessionScope.loggedInUser}">
    <c:redirect url="login.jsp"/>
</c:if>
<h1>Update Player Details</h1>
<form action="PlayerServlet" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${player.playerId}">
    
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" value="${player.name}" required>
    
    <label for="age">Age:</label>
    <input type="number" id="age" name="age" value="${player.age}" required>
    
    <label for="nationality">Nationality:</label>
    <input type="text" id="nationality" name="nationality" value="${player.nationality}" required>
    
    <label for="team">Team:</label>
    <input type="text" id="team" name="team" value="${player.team}" required>
    
    <label for="role">Role:</label>
    <input type="text" id="role" name="role" value="${player.role}" required>
    
    <label for="battingStyle">Batting Style:</label>
    <input type="text" id="battingStyle" name="battingStyle" value="${player.battingStyle}">
    
    <label for="bowlingStyle">Bowling Style:</label>
    <input type="text" id="bowlingStyle" name="bowlingStyle" value="${player.bowlingStyle}">
    
    <label for="currentMatchStatus">Current Match Status:</label>
    <input type="text" id="currentMatchStatus" name="currentMatchStatus" value="${player.currentMatchStatus}">
    
    <input type="submit" value="Update Player">
</form>
<br>
<a href="PlayerServlet?action=list" style="text-align:center;">Back to Player List</a>

</body>
</html>
