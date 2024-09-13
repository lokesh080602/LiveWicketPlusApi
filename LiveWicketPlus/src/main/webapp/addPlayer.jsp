<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Player</title>
     <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">

</head>
<body>
<c:if test="${empty sessionScope.loggedInUser}">
    <c:redirect url="login.jsp"/>
</c:if>

<h1>Add New Player</h1>
<form action="PlayerServlet" method="post">
    <input type="hidden" name="action" value="add">
    
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required>
    
    <label for="age">Age:</label>
    <input type="number" id="age" name="age" required>
    
    <label for="nationality">Nationality:</label>
    <input type="text" id="nationality" name="nationality" required>
    
    <label for="team">Team:</label>
    <input type="text" id="team" name="team" required>
    
    <label for="role">Role:</label>
    <input type="text" id="role" name="role" required>
    
    <label for="battingStyle">Batting Style:</label>
    <input type="text" id="battingStyle" name="battingStyle">
    
    <label for="bowlingStyle">Bowling Style:</label>
    <input type="text" id="bowlingStyle" name="bowlingStyle">
    
    <label for="currentMatchStatus">Current Match Status:</label>
    <input type="text" id="currentMatchStatus" name="currentMatchStatus">
    
    <input type="submit" value="Add Player">
</form>
<br>
<a style="text-align:center;"  href="home.jsp">Back to Home</a>

</body>
</html>
