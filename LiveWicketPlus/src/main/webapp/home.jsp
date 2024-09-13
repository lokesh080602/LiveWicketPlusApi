
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Match and Player Management</title>
<link rel="icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/static/png/logo-white.png">

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Aclonica&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<style>
.material-symbols-outlined {
	font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 48
}

body {
	font-family: "Aclonica", sans-serif;
	font-weight: 400;
	color: #E1D9D1;
	text-align: center;
	margin: 0;
	padding: 0;
	color: #333;
	width: 100%;
	height: 100vh;
	background-image:
		url('${pageContext.request.contextPath}/static/png/logo-color.png');
	background-size: cover;
	background-repeat: no-repeat;
	background-position: center;
}

.header {
	color: #fff;
	padding: 10px;
	text-align: right;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.logout-button, .user-list-button {
	color: white;
	text-decoration: none;
	background-color: black;
	padding: 6px 16px;
	border-radius: 5px;
	margin-left: 10px;
	font-size: 14px;
}

.logout-button:hover, .user-list-button:hover {
	background-color: white;
	color: black;
	text-decoration: none;
}

h1 {
	text-align: center;
	color: black;;
	margin-top: 0px;
}

h2 {
	color: #333;
}

ul {
	list-style: none;
	padding-left: 0px;
	padding-right: 10px;
	width: 600px;
}

li {
	display: inline-block;
}

.button {
	text-decoration: none;
	border-radius: 5px;
	font-size: 17px;
	color: white;
	display: inline-block;
	margin-top: 30px;
}

.button:hover {
	color: red;
}

#content {
	display: flex;
	justify-content: space-around;
	align-items: flex-start;
	flex-wrap: wrap;
	padding: 20px;
}

#match, #player {
	padding: 20px;
	border-radius: 8px;
	width: 500px;
	margin: 10px;
}

#match h2, #player h2 {
	font-size: 18px; /* Reduced font size for headings */
}

#forms {
	margin: 20px;
}

.li1 {
	margin-left: 0px;
	margin-right: 90px;
}

.li2 {
	margin-right: 70px;
}

svg {
	color: white;
}
<style>
  .hoverable {
    position: relative;
    display: inline-block;
    cursor: pointer;
    
  }

  .hoverable__main {
    color: #0056b3;
    font-weight: bold;
    font-size: 16px;
  }

  .hoverable>.hoverable__tooltip {
    display: none;
    position: absolute;
    top: 4em; /* Slightly further from the main text */
    left: 0.5em;
    background: #333;
    color: white;
    padding: 8px;
    border-radius: 6px;
    border: 1px solid #222;
    font-size: 14px;
    line-height: 1.4;
    white-space: nowrap; /* Prevents tooltip from wrapping */
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    z-index: 100;
  }

  .hoverable:hover>.hoverable__tooltip {
    display: inline-block;
  }

  /* Optional arrow below the tooltip */
  .hoverable__tooltip::after {
    content: "";
    position: absolute;
    top: -5px;
    left: 10px;
    border-width: 5px;
    border-style: solid;
    border-color: transparent transparent #333 transparent;
  }
</style>

</style>
</head>
<body>

	<c:if test="${empty sessionScope.loggedInUser}">
		<c:redirect url="login.jsp" />
	</c:if>

	<div class="header">
		<c:choose>
			<c:when test="${sessionScope.loggedInUser.username == 'admin'}">
				<a href="UserServlet?action=list" class="user-list-button">View
					User List</a>
			</c:when>
			<c:otherwise>
		<a href="UserServlet?action=view&id=${sessionScope.loggedInUser.userId}" class="hoverable">
  <svg xmlns="http://www.w3.org/2000/svg" height="48px" viewBox="0 -960 960 960" width="48px" fill="#000000">
    <path d="M222-255q63-44 125-67.5T480-346q71 0 133.5 23.5T739-255q44-54 62.5-109T820-480q0-145-97.5-242.5T480-820q-145 0-242.5 97.5T140-480q0 61 19 116t63 109Zm257.81-195q-57.81 0-97.31-39.69-39.5-39.68-39.5-97.5 0-57.81 39.69-97.31 39.68-39.5 97.5-39.5 57.81 0 97.31 39.69 39.5 39.68 39.5 97.5 0 57.81-39.69 97.31-39.68 39.5-97.5 39.5Zm.66 370Q398-80 325-111.5t-127.5-86q-54.5-54.5-86-127.27Q80-397.53 80-480.27 80-563 111.5-635.5q31.5-72.5 86-127t127.27-86q72.76-31.5 155.5-31.5 82.73 0 155.23 31.5 72.5 31.5 127 86t86 127.03q31.5 72.53 31.5 155T848.5-325q-31.5 73-86 127.5t-127.03 86Q562.94-80 480.47-80Zm-.47-60q55 0 107.5-16T691-212q-51-36-104-55t-107-19q-54 0-107 19t-104 55q51 40 103.5 56T480-140Zm0-370q34 0 55.5-21.5T557-587q0-34-21.5-55.5T480-664q-34 0-55.5 21.5T403-587q0 34 21.5 55.5T480-510Zm0-77Zm0 374Z" />
  </svg>

  <!-- Tooltip that appears on hover -->
  <span class="hoverable__tooltip">
   Profile
  </span>
</a>
			</c:otherwise>
		</c:choose>

		<a href="logout" class="logout-button">Logout</a>
	</div>

	<h1>Match and Player Management System</h1>

	<div id="content">
		<div id="match">
			<h2>Manage Matches</h2>
			<ul>
			<c:if test="${sessionScope.loggedInUser.username == 'admin'}">
				<li class="li1"><a href="addMatch.jsp" class="button">Add
						New Match</a></li>
						</c:if>
				<li class="li2"><a href="MatchServlet?action=list"
					class="button">View All Matches</a></li>
			</ul>
		</div>

		<div id="player">
			<h2>Manage Players</h2>
			<ul>
			<c:if test="${sessionScope.loggedInUser.username == 'admin'}">
				<li class="li1"><a href="addPlayer.jsp" class="button">Add
						New Player</a></li>
			</c:if>
				<li class="li2"><a href="PlayerServlet?action=list"
					class="button">View All Players</a></li>
			</ul>
		</div>
	</div>
</body>
</html>
