<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cricket Scorecard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #f7f9fc;
    margin: 0;
    padding: 20px;
}

.scorecard-container {
    max-width: 1200px;
    margin: auto;
    background-color: #ffffff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

header {
    text-align: center;
}

.match-info {
    margin-top: 10px;
    font-size: 16px;
}

.team-scores {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.team {
    width: 48%;
}

h2 {
    background-color: #01796f;
    color: white;
    padding: 10px;
    border-radius: 5px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}

table th, table td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center;
}

table th {
    background-color: #009688;
    color: white;
}

footer {
    text-align: center;
    margin-top: 20px;
}

button {
    background-color: #ff4081;
    color: white;
    border: none;
    padding: 10px 20px;
    margin: 10px;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #e91e63;
}

</style>
<body>
    <div class="scorecard-container">
        <header>
            <h1>Cricket Match Scorecard</h1>
            <div class="match-info">
                <p><strong>Match:</strong> Team A vs Team B</p>
                <p><strong>Date:</strong> 14th September 2024</p>
                <p><strong>Location:</strong> Chennai</p>
            </div>
        </header>

        <section class="team-scores">
            <div class="team">
                <h2>Team A</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Player</th>
                            <th>Runs</th>
                            <th>Balls</th>
                            <th>4s</th>
                            <th>6s</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Player 1</td>
                            <td>45</td>
                            <td>30</td>
                            <td>5</td>
                            <td>2</td>
                        </tr>
                        <!-- More players -->
                    </tbody>
                </table>
            </div>

            <div class="team">
                <h2>Team B</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Player</th>
                            <th>Runs</th>
                            <th>Balls</th>
                            <th>4s</th>
                            <th>6s</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Player 1</td>
                            <td>50</td>
                            <td>40</td>
                            <td>6</td>
                            <td>3</td>
                        </tr>
                        <!-- More players -->
                    </tbody>
                </table>
            </div>
        </section>

        <footer>
            <button class="update-button">Update Score</button>
            <button class="submit-button">Submit</button>
        </footer>
    </div>
</body>
</html>
