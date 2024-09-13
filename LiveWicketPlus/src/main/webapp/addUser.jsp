<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add User</title>
     <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
    
</head>
<body>

    <h1>Add User</h1>
    <form id="addUserForm" action="UserServlet" method="post">
        <input type="hidden" name="action" value="add">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required aria-required="true">
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required aria-required="true">
        
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required aria-required="true">
        
        <label for="favoriteTeams">Favorite Teams (comma-separated):</label>
        <input type="text" id="favoriteTeams" name="favoriteTeams">
        
        <label for="favoritePlayers">Favorite Players (comma-separated):</label>
        <input type="text" id="favoritePlayers" name="favoritePlayers">
        
        <input type="submit" id="sub" value="Add User">
    </form>><br>
    <a href="UserServlet?action=list">Back to List</a>

</body>
</html>
