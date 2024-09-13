<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Match</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <c:if test="${empty sessionScope.loggedInUser}">
        <c:redirect url="login.jsp"/>
    </c:if>
    <h1>Update Match</h1>
    <c:if test="${not empty match}">
        <form action="MatchServlet" method="post">
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="matchId" value="${match.matchId}" />
            <label for="teamA">Team A:</label>
            <input type="text" name="teamA" value="${match.teamA}" required /><br />
            <label for="teamB">Team B:</label>
            <input type="text" name="teamB" value="${match.teamB}" required /><br />
            <label for="scoreTeamA">Score Team A:</label>
            <input type="number" name="scoreTeamA" value="${match.scoreTeamA}" required /><br />
            <label for="scoreTeamB">Score Team B:</label>
            <input type="number" name="scoreTeamB" value="${match.scoreTeamB}" required /><br />
            
            <!-- Add Select Players Field -->
            <label for="players">Select Players:</label>
            <select name="players" multiple>
                <c:forEach var="player" items="${players}">
                    <option value="${player.playerId}">
                        ${player.name}
                    </option>
                </c:forEach>
            </select><br />
            
            <input type="submit" value="Update Match" />
        </form>
    </c:if>
    <br>
    <a href="MatchServlet?action=list" style="text-align:center;">Back to Match List</a>
</body>
</html>
