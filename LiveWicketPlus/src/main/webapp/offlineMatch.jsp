<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Offline Cricket Score</title>
  <style>/* General styles for the body */
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

/* Container for the form and UI */
#start-match-form, #select-players-form, #match-ui {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 20px;
    width: 300px;
    margin-top: 20px;
}

/* Headings */
h2 {
    color: #d9238c;
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
}

/* Form labels */
label {
    display: block;
    font-size: 16px;
    margin-bottom: 5px;
}

/* Form input fields */
input[type="text"], input[type="number"] {
    width: 100%;
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 14px;
}

/* Radio buttons */
input[type="radio"] {
    margin-right: 5px;
}

/* Buttons */
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

/* Tables */
table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}

table thead th {
    background-color: #d9238c;
    color: white;
    padding: 10px;
}

table tbody td {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: center;
}

/* Responsive Design */
@media (max-width: 768px) {
    #start-match-form, #select-players-form, #match-ui {
        width: 100%;
        padding: 15px;
    }

    h2 {
        font-size: 20px;
    }

    button {
        font-size: 14px;
    }
}
  </style>
</head>
<body>
    <!-- Team, Toss, Overs Form -->
    <div id="start-match-form">
        <h2>Match Setup</h2>
        <form id="matchSetupForm">
            <label for="teamA">Team A:</label>
            <input type="text" id="teamA" required><br>

            <label for="teamB">Team B:</label>
            <input type="text" id="teamB" required><br>

            <lable for="toss">Toss Won By:</lable>
            <input type="radio" id="toss" name="tossWinner" value="teamA" required>Team A
            <input type="radio" id="toss" name="tossWinner" value="teamB" required> Team B
        

            <p>Opted To:</p>
            <lable for="opted">Opted To:</lable>
            <input type="radio" id="opted" name="optedTo" value="bat" required>Bat
          
            <input type="radio" id="opted" name="optedTo" value="bowl" required>Bowl<br>
       

            <label for="overs">Overs:</label>
            <input type="number" id="overs" required><br>

            <button type="button" onclick="navigateToPlayers()">Start Match</button>
        </form>
    </div>

    <!-- Select Opening Players Form -->
    <div id="select-players-form" style="display:none;">
        <h2>Select Opening Players</h2>
        <form id="playersForm">
            <label for="striker">Striker:</label>
            <input type="text" id="striker" required><br>

            <label for="nonStriker">Non-Striker:</label>
            <input type="text" id="nonStriker" required><br>

            <label for="openingBowler">Opening Bowler:</label>
            <input type="text" id="openingBowler" required><br>

            <button type="button" onclick="startMatch()">Start Match</button>
        </form>
    </div>

    <!-- Match UI -->
    <div id="match-ui" style="display:none;">
        <h2 id="match-heading">Match: </h2>
        <p id="toss-info"></p>
        <p id="score-info">0 - 0 (0.0)</p>
        <div id="batsman-info">
            <table>
                <thead>
                    <tr>
                        <th>Batsman</th>
                        <th>R</th>
                        <th>B</th>
                        <th>4s</th>
                        <th>6s</th>
                        <th>SR</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td id="striker-info"></td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0.00</td>
                    </tr>
                    <tr>
                        <td id="nonStriker-info"></td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0.00</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        function navigateToPlayers() {
            document.getElementById('start-match-form').style.display = 'none';
            document.getElementById('select-players-form').style.display = 'block';
        }

        function startMatch() {
            // Get the values from forms
            const teamA = document.getElementById('teamA').value;
            const teamB = document.getElementById('teamB').value;
            const striker = document.getElementById('striker').value;
            const nonStriker = document.getElementById('nonStriker').value;
            const openingBowler = document.getElementById('openingBowler').value;
            const tossWinner = document.querySelector('input[name="tossWinner"]:checked').nextSibling.textContent;
            const optedTo = document.querySelector('input[name="optedTo"]:checked').nextSibling.textContent;

            // Set match UI details
            document.getElementById('match-heading').textContent = `${teamA} vs ${teamB}`;
            document.getElementById('toss-info').textContent = `Toss won by ${tossWinner} and opted to ${optedTo}`;
            document.getElementById('striker-info').textContent = striker;
            document.getElementById('nonStriker-info').textContent = nonStriker;

            // Hide player form and show match UI
            document.getElementById('select-players-form').style.display = 'none';
            document.getElementById('match-ui').style.display = 'block';
        }
    </script>
</body>
</html>
