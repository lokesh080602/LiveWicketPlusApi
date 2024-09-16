package com.ta.livewicketplus.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ta.livewicketplus.dto.Match;
import com.ta.livewicketplus.dto.PlayerDetails;
import com.ta.livewicketplus.service.MatchService;
import com.ta.livewicketplus.service.PlayerService;

@WebServlet("/MatchServlet")
public class MatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(MatchServlet.class);
    private final MatchService matchService = new MatchService();
    private final PlayerService playerService = new PlayerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String matchId = request.getParameter("matchId");
            String action = request.getParameter("action");

            if ("list".equals(action)) {
                listMatches(request, response);
            } else if ("view".equals(action) && matchId != null) {
                viewMatch(request, response, matchId);
            } else if ("edit".equals(action) && matchId != null) {
                editMatch(request, response, matchId);
            } else if ("delete".equals(action)) {
                deleteMatch(request, response);
            } else {
                logger.warn("Invalid action or match ID not provided.");
                response.sendRedirect("MatchServlet?action=list");
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing the GET request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        logger.info("Action received: {}", action);

        if ("add".equals(action)) {
            addMatch(request, response);
        } else if ("update".equals(action)) {
            updateMatch(request, response);
        } else if ("startMatch".equals(action)) {
            startMatch(request, response);
        } else if ("delete".equals(action)) {
            deleteMatch(request, response);
        } else {
            logger.warn("Unknown action: {}", action);
            listMatches(request, response);
        }
    }

    private void listMatches(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Match> matches = matchService.getAllMatches();
            request.setAttribute("matches", matches);
            RequestDispatcher dispatcher = request.getRequestDispatcher("listMatches.jsp");
            dispatcher.forward(request, response);
            logger.info("Listed all matches");
        } catch (Exception e) {
            logger.error("Error listing matches", e);
            request.setAttribute("error", "Unable to list matches. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void viewMatch(HttpServletRequest request, HttpServletResponse response, String matchId)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(matchId);
            Match match = matchService.getMatchDetails(id);
            request.setAttribute("match", match);
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewMatchDetails.jsp");
            dispatcher.forward(request, response);
            logger.info("Viewed match details for ID: {}", id);
        } catch (NumberFormatException e) {
            logger.error("Invalid match ID format: {}", matchId, e);
            request.setAttribute("error", "Invalid match ID format.");
            listMatches(request, response);
        } catch (Exception e) {
            logger.error("Error viewing match details", e);
            request.setAttribute("error", "Unable to view match details. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void editMatch(HttpServletRequest request, HttpServletResponse response, String matchId)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(matchId);
            Match match = matchService.getMatchDetails(id);
            List<PlayerDetails> players = playerService.getAllPlayerDetails();

            request.setAttribute("players", players);
            request.setAttribute("match", match);
            RequestDispatcher dispatcher = request.getRequestDispatcher("updateMatch.jsp");
            dispatcher.forward(request, response);
            logger.info("Editing match details for ID: {}", id);
        } catch (NumberFormatException e) {
            logger.error("Invalid match ID format: {}", matchId, e);
            request.setAttribute("error", "Invalid match ID format.");
            listMatches(request, response);
        } catch (Exception e) {
            logger.error("Error while editing match details", e);
            request.setAttribute("error", "Unable to edit match details. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void addMatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Match match = new Match();
            match.setTeamA(request.getParameter("teamA"));
            match.setTeamB(request.getParameter("teamB"));
            match.setScoreTeamA(Integer.parseInt(request.getParameter("scoreTeamA").trim()));
            match.setScoreTeamB(Integer.parseInt(request.getParameter("scoreTeamB").trim()));
            match.setTossWinner(request.getParameter("tossWinner"));
            match.setOptedTo(request.getParameter("optedTo"));
            match.setOvers(Integer.parseInt(request.getParameter("overs").trim()));
            match.setVenue(request.getParameter("venue"));
            match.setMatchDate(new Date());
            match.setUmpires(request.getParameter("umpires"));

            String[] playerIds = request.getParameterValues("players[]");

            if (playerIds != null) {
                List<PlayerDetails> playerDetailsList = new ArrayList<>();
                
                for (String playerId : playerIds) {
                    PlayerDetails player = playerService.getPlayerDetails(Long.parseLong(playerId));
                    if (player != null) {
                        playerDetailsList.add(player);
                    }
                }

                match.setPlayerDetailsList(playerDetailsList);
            }
            matchService.saveMatch(match);
            logger.info("Added new match: {}", match);

            response.sendRedirect("MatchServlet?action=list");

        } catch (NumberFormatException e) {
            logger.error("Invalid input format", e);
            request.setAttribute("error", "Invalid input format. Please check your data.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addMatch.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("Error adding match", e);
            request.setAttribute("error", "Unable to add match. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addMatch.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void updateMatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long matchId = Long.parseLong(request.getParameter("matchId"));
            Match match = matchService.getMatchDetails(matchId);
            if (match != null) {
                match.setTeamA(request.getParameter("teamA"));
                match.setTeamB(request.getParameter("teamB"));
                match.setScoreTeamA(Integer.parseInt(request.getParameter("scoreTeamA")));
                match.setScoreTeamB(Integer.parseInt(request.getParameter("scoreTeamB")));
                match.setTossWinner(request.getParameter("tossWinner"));
                match.setOptedTo(request.getParameter("optedTo"));
                match.setOvers(Integer.parseInt(request.getParameter("overs")));
                match.setVenue(request.getParameter("venue"));
                match.setUmpires(request.getParameter("umpires"));

                // Handle player details
                String[] playerIds = request.getParameterValues("players[]");
                if (playerIds != null) {
                    List<PlayerDetails> playerDetailsList = new ArrayList<>();
                    for (String playerId : playerIds) {
                        PlayerDetails player = playerService.getPlayerDetails(Long.parseLong(playerId));
                        if (player != null) {
                            playerDetailsList.add(player);
                        }
                    }
                    match.setPlayerDetailsList(playerDetailsList);
                }

                matchService.updateMatch(match);
                logger.info("Updated match with ID: {}", matchId);
                response.sendRedirect("MatchServlet?action=list");
            } else {
                logger.warn("Match not found for update with ID: {}", matchId);
                listMatches(request, response);
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid input format", e);
            request.setAttribute("error", "Invalid input format. Please check your data.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/updateMatch.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("Error updating match", e);
            request.setAttribute("error", "Unable to update match. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/updateMatch.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deleteMatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long matchId = Long.parseLong(request.getParameter("matchId"));
            matchService.deleteMatch(matchId);
            logger.info("Deleted match with ID: {}", matchId);
            response.sendRedirect("MatchServlet?action=list");
        } catch (NumberFormatException e) {
            logger.error("Invalid match ID format: {}", request.getParameter("matchId"), e);
            request.setAttribute("error", "Invalid match ID format.");
            listMatches(request, response);
        } catch (Exception e) {
            logger.error("Error deleting match", e);
            request.setAttribute("error", "Unable to delete match. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void startMatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long matchId = Long.parseLong(request.getParameter("matchId"));
            Match match = matchService.getMatchDetails(matchId);
            if (match != null) {
                match.setMatchDate(new Date()); // Set match date to current time
                matchService.updateMatch(match);
                logger.info("Started match with ID: {}", matchId);
                response.sendRedirect("MatchUI.jsp?matchId=" + matchId);
            } else {
                logger.warn("Match not found for starting with ID: {}", matchId);
                listMatches(request, response);
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid match ID format: {}", request.getParameter("matchId"), e);
            request.setAttribute("error", "Invalid match ID format.");
            listMatches(request, response);
        } catch (Exception e) {
            logger.error("Error starting match", e);
            request.setAttribute("error", "Unable to start match. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
