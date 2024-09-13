<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
  <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" >
    <link href="https://fonts.googleapis.com/css2?family=Aclonica&display=swap" rel="stylesheet">
    <style>
        /* General Styles */
        body {
            margin: 0;
            padding: 0;
            font-family: "Aclonica", sans-serif;
            font-weight: 400;
            color: #E1D9D1;
            text-align: center;
            background-color: #f4f4f4;
            position: relative;
        }

        h1, h2 {
            color: rgb(255, 255, 255);
            font-weight: 400;
        }

        a {
            color: black;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        /* Background Image */
        .background-logo {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100vh;
            background-image: url('${pageContext.request.contextPath}/static/png/logo-white.png');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            z-index: -1; /* Ensures background is behind all other content */
        }

        /* Container Styles */
        .container {
            max-width: 900px;
           margin-right:auto;
           margin-left:auto;
            padding: 20px;
            margin-bottom:0px;
        }

        h1 {
        margin-top:28px;
            font-size: 2.5em;
            margin-bottom:150px;
        }

        h2 {
            font-size: 1.8em;
            margin-top: 0px;
            margin-bottom: 70px;
        }

        /* Links Section */
        .links {
            display: flex;
            justify-content: center;
            gap: 180px;
            margin-top: 20px;
        }

        .links a {
            display: inline-block;
            color:white;
            font-size: 1.1em;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .links a:hover {
        padding: 10px 20px;
            border-radius: 4px;
            background-color: #007BFF;
            color: black;
            background-color: white;
           
            text-decoration: none;
        }

        .links a:active {
            background-color: #004080;
            transform: translateY(0);
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .container {
                padding: 15px;
            }

            .links {
                flex-direction: column;
                gap: 10px;
            }

            .links a {
                font-size: 1em;
                padding: 10px 20px;
            }
        }

        /* Content Positioning */
        #content {
            position: relative;
            z-index: 1;
        }
    </style>
</head>
<body>
    <div class="background-logo"></div>
 
        <h1 style="color:white">Welcome to Live Wicket Plus</h1>
        <div class="container">
            
            <div class="links">
                <a href="login.jsp">Login</a>
                <a href="addUser.jsp" style="margin-left:100px;">Register</a>
            </div>
        </div>
 
</body>
</html>

