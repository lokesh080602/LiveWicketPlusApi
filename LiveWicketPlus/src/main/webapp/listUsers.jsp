<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User List</title>
     <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<c:if test="${empty sessionScope.loggedInUser}">
    <c:redirect url="login.jsp"/>
</c:if>
    <div class="container">
        <h1>User List</h1>
        <table class="table-container">
            <tr>
                <th>User ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>
                    <c:if test="${user.username != 'admin'}">
                        <a style="text-align:center;" href="UserServlet?action=delete&id=${user.userId}">Delete</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <a href="addUser.jsp">Add New User</a>
    </div>
</body>
</html>
