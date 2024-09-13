<%@page import="com.ta.livewicketplus.dto.PlayerDetails"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.ta.livewicketplus.controller.*"%>
<%@page import="java.util.*" %>
<%@page import="com.ta.livewicketplus.service.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Match</title>
    <link rel="icon" type="image/x-icon" href="../resources/static/png/logo-white.png">
    <link rel="stylesheet" type="text/css" href="styles.css">

    <style>
        input[type="number"] {
            width: calc(100% - 16px);
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .player-list {
            margin-top: 20px;
            padding: 10px;
            background-color: #f1f1f1;
            border-radius: 5px;
            width: 300px;
        }

        .player-list li {
            list-style-type: none;
            padding: 5px 0;
        }

    </style>

    <script>
        // Array to hold selected players
        var selectedPlayers = [];

        // Function to add a player to the list
        function addPlayer(playerId, playerName) {
            // Check if the player is already selected
            if (!selectedPlayers.includes(playerId)) {
                selectedPlayers.push(playerId);
                // Display the selected player in the list
                var playerList = document.getElementById("selected-players");
                var li = document.createElement("li");
                li.setAttribute("data-player-id", playerId);
                li.innerHTML = playerName + ' <button onclick="removePlayer(\'' + playerId + '\')">Remove</button>';
                playerList.appendChild(li);
            }
        }

        // Function to remove a player from the list
        function removePlayer(playerId) {
            // Remove player from array
            selectedPlayers = selectedPlayers.filter(id => id !== playerId);
            // Remove player from the displayed list
            var li = document.querySelector('li[data-player-id="' + playerId + '"]');
            li.remove();
        }

        // Function to submit form with selected players
        function submitForm() {
            // Add selected players as hidden input fields
            var form = document.getElementById("matchForm");
            selectedPlayers.forEach(function(playerId) {
                var input = document.createElement("input");
                input.type = "hidden";
                input.name = "players[]";
                input.value = playerId;
                form.appendChild(input);
            });
            // Submit the form
            form.submit();
        }
    </script>
</head>
<body>

<h1>Add New Match</h1>

<form id="matchForm" action="MatchServlet" method="post">
    <input type="hidden" name="action" value="add" />
    <label for="teamA">Team A:</label>
    <input type="text" name="teamA" required /><br />
    <label for="teamB">Team B:</label>
    <input type="text" name="teamB" required /><br />
    <label for="scoreTeamA">Score Team A:</label>
    <input type="number" name="scoreTeamA" required /><br />
    <label for="scoreTeamB">Score Team B:</label>
    <input type="number" name="scoreTeamB" required /><br />

    <!-- Players Selection -->
    <label for="playerSelect">Select Players:</label>
    <select id="playerSelect" onchange="addPlayer(this.value, this.options[this.selectedIndex].text)">
        <option value="">Select a player</option>
        <% 
            PlayerService playerService = new PlayerService();
            List<PlayerDetails> players = playerService.getAllPlayerDetails();
            for (PlayerDetails player : players) {
        %>
            <option value="<%= player.getPlayerId() %>"><%= player.getName() %><%="  "+player.getTeam()%></option>
        <% 
            }
        %>
    </select>

    <!-- Display Selected Players -->
    <div class="player-list">
        <h3>Selected Players:</h3>
        <ul id="selected-players">
            <!-- Selected players will be added here dynamically -->
        </ul>
    </div>

    <input class="button" type="submit" value="Add Match" onclick="submitForm()" />
</form>
<br>
<a href="MatchServlet?action=list">Back to Match List</a>

</body>
</html>
