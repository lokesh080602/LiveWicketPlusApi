<%@page import="com.ta.livewicketplus.dto.PlayerDetails"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.ta.livewicketplus.controller.*"%>
<%@page import="java.util.*" %>
<%@page import="com.ta.livewicketplus.service.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Offline Cricket Score</title>
    <style>
        /* Your existing CSS styles */
        .toss label {
            margin-right: 10px; 
            margin-bottom: 3px;
        }

        .toss-options {
            display: flex;
            gap: 3px;
            align-items: center;
            margin-left: 30px;
            margin-bottom: 20px;
        }
        body {
            font-family: 'Aclonica', sans-serif;
            background-color: #f0f0f0;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        #main-container {
            display: flex;
            flex-direction: row; /* Row layout for double columns */
            gap: 20px;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 1000px;
            width: 100%;
            box-sizing: border-box; /* Include padding in width calculation */
        }

        .form-container {
            flex: 1; /* Make each form take up equal space */
            padding: 20px;
            background-color: #fafafa;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            box-sizing: border-box; /* Include padding in width calculation */
        }

        h2 {
            color: #d9238c;
            text-align: center;
            font-size: 24px;
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-size: 16px;
            margin-bottom: 5px;
        }

        input[type="text"], input[type="number"], select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        input[type="radio"] {
            margin-right: 5px;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #d9238c;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #c71b7a;
        }

        @media (max-width: 768px) {
            #main-container {
                flex-direction: column; /* Stack forms vertically on small screens */
                padding: 15px;
            }
        }
    </style>
</head>
<body>
    <div id="main-container">
        <!-- Match Setup Form -->
        <div class="form-container">
            <h2>Match Setup</h2>
            <form id="matchSetupForm">
                <label for="teamA">Team A:</label>
                <input type="text" id="teamA" required><br>

                <label for="teamB">Team B:</label>
                <input type="text" id="teamB" required><br>

                <div class="toss">
                    <label>Toss Won By:</label>
                    <div class="toss-options">
                        <input type="radio" id="tossA" name="tossWinner" value="Team A" required> Team A
                        <input type="radio" id="tossB" name="tossWinner" value="Team B" required> Team B
                    </div>
                </div>

                <div class="toss">
                    <label>Opted To:</label>
                    <div class="toss-options">
                        <input type="radio" id="optedBat" name="optedTo" value="Bat" required> Bat
                        <input type="radio" id="optedBowl" name="optedTo" value="Bowl" required> Bowl
                    </div>
                </div>

                <label for="overs">Overs:</label>
                <input type="number" id="overs" required><br>
            </form>
        </div>

        <!-- Player Selection Form -->
        <div class="form-container">
            <h2>Select Opening Players</h2>
            <form id="playersForm">
                <label for="strikerSelect">Striker:</label>
                <select id="strikerSelect" onchange="updatePlayer('striker', this.value, this.options[this.selectedIndex].text)">
                    <option value="">Select a player</option>
                    <% 
                        PlayerService playerService = new PlayerService();
                        List<PlayerDetails> players = playerService.getAllPlayerDetails();
                        for (PlayerDetails player : players) {
                    %>
                    <option value="<%= player.getPlayerId() %>"><%= player.getName() %> <%= player.getTeam() %></option>
                    <% 
                        }
                    %>
                </select><br>

                <label for="nonStrikerSelect">Non-Striker:</label>
                <select id="nonStrikerSelect" onchange="updatePlayer('nonStriker', this.value, this.options[this.selectedIndex].text)">
                    <option value="">Select a player</option>
                    <% 
                        for (PlayerDetails player : players) {
                    %>
                    <option value="<%= player.getPlayerId() %>"><%= player.getName() %> <%= player.getTeam() %></option>
                    <% 
                        }
                    %>
                </select><br>

                <label for="openingBowlerSelect">Opening Bowler:</label>
                <select id="openingBowlerSelect" onchange="updatePlayer('openingBowler', this.value, this.options[this.selectedIndex].text)">
                    <option value="">Select a bowler</option>
                    <% 
                        for (PlayerDetails player : players) {
                    %>
                    <option value="<%= player.getPlayerId() %>"><%= player.getName() %> <%= player.getTeam() %></option>
                    <% 
                        }
                    %>
                </select><br>

                <button type="button" onclick="submitMatchSetup()">Start Match</button>
            </form>
        </div>
    </div>

    <script>
        function updatePlayer(role, playerId, playerName) {
            console.log(`${role} selected: ${playerName} (ID: ${playerId})`);
        }

        function submitMatchSetup() {
            var matchSetupForm = document.getElementById('matchSetupForm');
            var playersForm = document.getElementById('playersForm');
            var formData = new FormData(matchSetupForm);
            var playersData = new FormData(playersForm);

            formData.append('striker', playersData.get('striker'));
            formData.append('nonStriker', playersData.get('nonStriker'));
            formData.append('openingBowler', playersData.get('openingBowler'));

            fetch('MatchServlet', {
                method: 'POST',
                body: formData
            }).then(response => response.text())
              .then(result => {
                  console.log(result);
                  alert('Match setup completed successfully!');
              }).catch(error => {
                  console.error('Error:', error);
              });
        }
    </script>
</body>
</html>
