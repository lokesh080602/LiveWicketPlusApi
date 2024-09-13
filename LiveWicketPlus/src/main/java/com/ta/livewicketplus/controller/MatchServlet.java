package com.ta.livewicketplus.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ta.livewicketplus.dto.Match;
import com.ta.livewicketplus.dto.PlayerDetails;
import com.ta.livewicketplus.service.MatchService;
import com.ta.livewicketplus.service.PlayerService;
import com.ta.livewicketplus.util.ResponseStructure;

@WebServlet("/MatchServlets")
public class MatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(MatchServlet.class);
    private final MatchService matchService = new MatchService();
    private final PlayerService playerService = new PlayerService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String matchId = request.getParameter("matchId");

        try {
            if ("list".equals(action)) {
                listMatches(request, response);
            } else if ("view".equals(action) && matchId != null) {
                viewMatch(request, response, matchId);
            } else if ("edit".equals(action) && matchId != null) {
                editMatch(request, response, matchId);
            } else {
                logger.warn("Invalid action or match ID not provided.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid action or match ID not provided.\"}");
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing the GET request", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Internal server error.\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        logger.info("Action received: {}", action);

        try {
            if ("add".equals(action)) {
                addMatch(request, response);
            } else if ("update".equals(action)) {
                updateMatch(request, response);
            } else if ("delete".equals(action)) {
                deleteMatch(request, response);
            } else {
                logger.warn("Unknown action: {}", action);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Unknown action.\"}");
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing the POST request", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Internal server error.\"}");
        }
    }

    private void listMatches(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseStructure<List<Match>> responseStructure = new ResponseStructure<>();
        try {
            List<Match> matches = matchService.getAllMatches();
            responseStructure.setStatus(HttpServletResponse.SC_OK);
            responseStructure.setMessage("Matches listed successfully.");
            responseStructure.setData(matches);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.info("Listed all matches");
        } catch (Exception e) {
            logger.error("Error listing matches", e);
            responseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseStructure.setMessage("Unable to list matches. Please try again later.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
        }
    }

    private void viewMatch(HttpServletRequest request, HttpServletResponse response, String matchId) throws IOException {
        ResponseStructure<Match> responseStructure = new ResponseStructure<>();
        try {
            int id = Integer.parseInt(matchId);
            Match match = matchService.getMatchDetails(id);
            if (match != null) {
                responseStructure.setStatus(HttpServletResponse.SC_OK);
                responseStructure.setMessage("Match details retrieved successfully.");
                responseStructure.setData(match);
            } else {
                responseStructure.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseStructure.setMessage("Match not found.");
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.info("Viewed match details for ID: {}", id);
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid match ID format.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Invalid match ID format: {}", matchId, e);
        } catch (Exception e) {
            responseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseStructure.setMessage("Unable to view match details. Please try again later.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Error viewing match details", e);
        }
    }

    private void editMatch(HttpServletRequest request, HttpServletResponse response, String matchId) throws IOException {
        ResponseStructure<Match> responseStructure = new ResponseStructure<>();
        try {
            int id = Integer.parseInt(matchId);
            Match match = matchService.getMatchDetails(id);
            if (match != null) {
                List<PlayerDetails> players = matchService.getPlayersByMatchId(match.getMatchId());
                responseStructure.setStatus(HttpServletResponse.SC_OK);
                responseStructure.setMessage("Match details retrieved for editing.");
                responseStructure.setData(match);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.info("Editing match details for ID: {}", id);
            } else {
                responseStructure.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseStructure.setMessage("Match not found.");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.warn("Match not found for ID: {}", id);
            }
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid match ID format.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Invalid match ID format: {}", matchId, e);
        } catch (Exception e) {
            responseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseStructure.setMessage("Unable to retrieve match details for editing. Please try again later.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Error retrieving match details for editing", e);
        }
    }

    private void addMatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseStructure<String> responseStructure = new ResponseStructure<>();
        try {
            Match match = new Match();
            match.setTeamA(request.getParameter("teamA"));
            match.setTeamB(request.getParameter("teamB"));
            match.setScoreTeamA(Integer.parseInt(request.getParameter("scoreTeamA").trim()));
            match.setScoreTeamB(Integer.parseInt(request.getParameter("scoreTeamB").trim()));

            String[] playerIds = request.getParameterValues("players[]");
            if (playerIds != null) {
                List<PlayerDetails> playerDetailsList = new ArrayList<>();
                for (String playerId : playerIds) {
                    PlayerDetails player = playerService.getPlayerDetails(Integer.parseInt(playerId));
                    if (player != null) {
                        playerDetailsList.add(player);
                    }
                }
                match.setPlayerDetailsList(playerDetailsList);
            }
            matchService.saveMatch(match);
            responseStructure.setStatus(HttpServletResponse.SC_CREATED);
            responseStructure.setMessage("Match added successfully.");
            responseStructure.setData("Match added: " + match.getTeamA() + " vs " + match.getTeamB());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.info("Added new match: {}", match);
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid input format. Please check your data.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Invalid input format", e);
        } catch (Exception e) {
            responseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseStructure.setMessage("Unable to add match. Please try again later.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Error adding match", e);
        }
    }

    private void updateMatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseStructure<String> responseStructure = new ResponseStructure<>();
        try {
            int matchId = Integer.parseInt(request.getParameter("matchId"));
            Match match = matchService.getMatchDetails(matchId);
            if (match != null) {
                match.setTeamA(request.getParameter("teamA"));
                match.setTeamB(request.getParameter("teamB"));
                match.setScoreTeamA(Integer.parseInt(request.getParameter("scoreTeamA")));
                match.setScoreTeamB(Integer.parseInt(request.getParameter("scoreTeamB")));
                matchService.updateMatch(match);
                responseStructure.setStatus(HttpServletResponse.SC_OK);
                responseStructure.setMessage("Match updated successfully.");
                responseStructure.setData("Match updated: " + match.getTeamA() + " vs " + match.getTeamB());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.info("Updated match with ID: {}", matchId);
            } else {
                responseStructure.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseStructure.setMessage("Match not found for update.");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.warn("Match not found for ID: {}", matchId);
            }
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid input format. Please check your data.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Invalid input format", e);
        } catch (Exception e) {
            responseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseStructure.setMessage("Unable to update match. Please try again later.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Error updating match", e);
        }
    }

    private void deleteMatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseStructure<String> responseStructure = new ResponseStructure<>();
        try {
            int matchId = Integer.parseInt(request.getParameter("matchId"));
            matchService.deleteMatch(matchId);
            responseStructure.setStatus(HttpServletResponse.SC_OK);
            responseStructure.setMessage("Match deleted successfully.");
            responseStructure.setData("Match ID: " + matchId);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.info("Deleted match with ID: {}", matchId);
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid match ID format.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Invalid match ID format", e);
        } catch (Exception e) {
            responseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseStructure.setMessage("Unable to delete match. Please try again later.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Error deleting match", e);
        }
    }
}
