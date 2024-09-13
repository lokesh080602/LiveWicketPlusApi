<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Status</title>
     <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            text-align: center;
        }
        .message {
            padding: 20px;
            border-radius: 5px;
            display: inline-block;
            margin-top: 20px;
        }
        .success {
            color: green;
            border: 1px solid green;
        }
        .error {
            color: red;
            border: 1px solid red;
        }
        a {
            text-decoration: none;
            color: #007bff;
        }
        a:hover {
            text-decoration: underline;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 20px;
            font-size: 16px;
            text-decoration: none;
            color: white;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Registration Status</h1>
    
    <c:choose>
        <c:when test="${param.pass == 'pass'}">
            <div class="message success">
                <h2>Registration Successful!</h2>
                <p>Your account has been created successfully.</p>
                <a href="login.jsp" class="button">Login</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="message error">
                <h2>Registration Failed</h2>
                <p>Sorry, there was a problem with your registration. The email or username might already be in use.</p>
                <a href="addUser.jsp" class="button">Try Again</a>
            </div>
        </c:otherwise>
    </c:choose>
    <br>
    <a href="index.jsp">Back to Home</a>
</body>
</html>
