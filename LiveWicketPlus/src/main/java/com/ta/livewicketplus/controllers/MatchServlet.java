package com.ta.livewicketplus.controllers;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String matchId = request.getParameter("matchId");
            String action = request.getParameter("action");

            if ("list".equals(action)) {
                List<Match> matches = matchService.getAllMatches();
                request.setAttribute("matches", matches);
                request.getRequestDispatcher("listMatches.jsp").forward(request, response);
                logger.info("Listing all matches");

            }else if (action.equals("delete")) {
                deleteMatch(request, response);}
            else if ("view".equals(action) && matchId != null) {
                int id = Integer.parseInt(matchId);
                Match match = matchService.getMatchDetails(id);
                request.setAttribute("match", match);
                request.getRequestDispatcher("viewMatchDetails.jsp").forward(request, response);
                logger.info("Viewing match details for ID: {}", id);

            }else if ("edit".equals(action) && matchId != null) {
                try {
                    int id = Integer.parseInt(matchId);
                    Match match = matchService.getMatchDetails(id);

                    List<PlayerDetails> players = matchService.getPlayersByMatchId(match.getMatchId());

                    request.setAttribute("players", players);           
                    request.setAttribute("match", match);
                    request.getRequestDispatcher("updateMatch.jsp").forward(request, response);
                    
                    logger.info("Editing match details for ID: {}", id);

                } catch (NumberFormatException e) {
                    logger.error("Invalid matchId format: {}", matchId, e);
                } catch (Exception e) {
                    logger.error("Error while editing match details", e);

                }
            }
 else {
                logger.warn("Invalid action or match ID not provided.");
                response.sendRedirect("MatchServlet?action=list");
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing the GET request", e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        logger.info("Action received: {}", action);
        
        if (action.equals("add")) {
            addMatch(request, response);
        } else if (action.equals("update")) {
            updateMatch(request, response);
        } else if (action.equals("delete")) {
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

    protected void viewMatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String matchIdParam = request.getParameter("matchId");
        if (matchIdParam != null && !matchIdParam.isEmpty()) {
            try {
                int matchId = Integer.parseInt(matchIdParam);
                Match match = matchService.getMatchDetails(matchId);
                request.setAttribute("match", match);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewMatchDetails.jsp");
                dispatcher.forward(request, response);
                logger.info("Viewed match details for ID: {}", matchId);
            } catch (NumberFormatException e) {
                logger.error("Invalid match ID format: {}", matchIdParam, e);
                request.setAttribute("error", "Invalid match ID format.");
                listMatches(request, response);
            } catch (Exception e) {
                logger.error("Error viewing match details", e);
                request.setAttribute("error", "Unable to view match details. Please try again later.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            logger.warn("Match ID is required but not provided.");
            listMatches(request, response);
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

            String[] playerIds = request.getParameterValues("players[]");
 
            if (playerIds != null) {
                List<PlayerDetails> playerDetailsList = new ArrayList<>();
                PlayerService playerService = new PlayerService();
                
                for (String playerId : playerIds) {

                    PlayerDetails player = playerService.getPlayerDetails(Integer.parseInt(playerId));
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
            int matchId = Integer.parseInt(request.getParameter("matchId"));
            Match match = matchService.getMatchDetails(matchId);
            if (match != null) {
                match.setTeamA(request.getParameter("teamA"));
                match.setTeamB(request.getParameter("teamB"));
                match.setScoreTeamA(Integer.parseInt(request.getParameter("scoreTeamA")));
                match.setScoreTeamB(Integer.parseInt(request.getParameter("scoreTeamB")));
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
            int matchId = Integer.parseInt(request.getParameter("matchId"));
            matchService.deleteMatch(matchId);
            logger.info("Deleted match with ID: {}", matchId);
            response.sendRedirect("MatchServlet?action=list");
        } catch (NumberFormatException e) {
            logger.error("Invalid match ID format", e);
            request.setAttribute("error", "Invalid match ID format. Please check your data.");
            listMatches(request, response);
        } catch (Exception e) {
            logger.error("Error deleting match", e);
            request.setAttribute("error", "Unable to delete match. Please try again later.");
            listMatches(request, response);
        }
    }
}
