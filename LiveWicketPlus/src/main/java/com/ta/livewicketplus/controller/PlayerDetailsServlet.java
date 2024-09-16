package com.ta.livewicketplus.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ta.livewicketplus.dto.PlayerDetails;
import com.ta.livewicketplus.service.PlayerService;
import com.ta.livewicketplus.util.LogUtil;
import com.ta.livewicketplus.util.ResponseStructure;

@WebServlet("/PlayerServlets")
public class PlayerDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogUtil.getPlayerServletLogger();
    private final PlayerService playerService = new PlayerService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private JSONObject json;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	BufferedReader reader=request.getReader();
    	StringBuilder  builder=new StringBuilder();
    	String line;
    	while((line=reader.readLine())!=null) {
    		
    		builder.append(line);
    	}

    		json=new JSONObject(builder.toString());
	
    	String action=json.getString("action");
        logger.info("Action received: {}", action);

        try {
            if (action == null || action.isEmpty() || "list".equals(action)) {
                ResponseStructure<List<PlayerDetails>> listResponseStructure = new ResponseStructure<>();
                listPlayers(request, response, listResponseStructure);
            } else if ("view".equals(action)) {
                ResponseStructure<PlayerDetails> viewResponseStructure = new ResponseStructure<>();
                viewPlayer(request, response, viewResponseStructure);
            } else if ("delete".equals(action)) {
                ResponseStructure<String> deleteResponseStructure = new ResponseStructure<>();
                deletePlayer(request, response, deleteResponseStructure);
            } else if ("update".equals(action)) {
                ResponseStructure<PlayerDetails> updateResponseStructure = new ResponseStructure<>();
                showUpdateForm(request, response, updateResponseStructure);
            } else {
                logger.warn("Unknown action: {}", action);
                ResponseStructure<String> unknownActionResponseStructure = new ResponseStructure<>();
                unknownActionResponseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                unknownActionResponseStructure.setMessage("Unknown action.");
                response.getWriter().write(objectMapper.writeValueAsString(unknownActionResponseStructure));
            }
        } catch (Exception e) {
            logger.error("Error processing request", e);
            ResponseStructure<String> errorResponseStructure = new ResponseStructure<>();
            errorResponseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errorResponseStructure.setMessage("Internal server error.");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponseStructure));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	BufferedReader reader=request.getReader();
    	StringBuilder  builder=new StringBuilder();
    	String line;
    	while((line=reader.readLine())!=null) {
    		
    		builder.append(line);
    	}
 
    		json=new JSONObject(builder.toString());
    
        String action = json.getString("action");
        logger.info("Action received: {}", action);

        try {
            if ("add".equals(action)) {
                ResponseStructure<String> addResponseStructure = new ResponseStructure<>();
                addPlayer(request, response, addResponseStructure);
            } else if ("update".equals(action)) {
                ResponseStructure<PlayerDetails> updateResponseStructure = new ResponseStructure<>();
                updatePlayer(request, response, updateResponseStructure);
            } else {
                logger.warn("Unknown action: {}", action);
                ResponseStructure<String> unknownActionResponseStructure = new ResponseStructure<>();
                unknownActionResponseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                unknownActionResponseStructure.setMessage("Unknown action.");
                response.getWriter().write(objectMapper.writeValueAsString(unknownActionResponseStructure));
            }
        } catch (Exception e) {
            logger.error("Error processing request", e);
            ResponseStructure<String> errorResponseStructure = new ResponseStructure<>();
            errorResponseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errorResponseStructure.setMessage("Internal server error.");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponseStructure));
        }
    }

    private void listPlayers(HttpServletRequest request, HttpServletResponse response, ResponseStructure<List<PlayerDetails>> responseStructure)
            throws IOException {
        List<PlayerDetails> players = playerService.getAllPlayerDetails();
        responseStructure.setStatus(HttpServletResponse.SC_OK);
        responseStructure.setMessage("Players listed successfully.");
        responseStructure.setData(players);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
        logger.info("Listed all players");
    }

    private void viewPlayer(HttpServletRequest request, HttpServletResponse response, ResponseStructure<PlayerDetails> responseStructure)
            throws IOException {
        String idParam = String.valueOf(json.getInt("playerId"));
        
        if (idParam != null && !idParam.isEmpty()) {
            try {        
            	int id = Integer.parseInt(idParam);
            	PlayerDetails player = playerService.getPlayerDetails(id);

                if (player != null) {
                    responseStructure.setStatus(HttpServletResponse.SC_OK);
                    responseStructure.setMessage("Player details retrieved successfully.");
                    responseStructure.setData(player);
                } else {
                    responseStructure.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    responseStructure.setMessage("Player not found.");
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.info("Viewed player details for ID: {}", id);
            } catch (NumberFormatException e) {
                responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseStructure.setMessage("Invalid player ID format.");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.error("Invalid player ID format: {}", idParam, e);
            }
        } else {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Player ID not provided.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Player ID not provided.");
        }
    }

    private void addPlayer(HttpServletRequest request, HttpServletResponse response, ResponseStructure<String> responseStructure)
            throws IOException {
        try {
            PlayerDetails player = new PlayerDetails();
            player.setName(json.getString("name"));
            player.setAge(json.getInt("age"));
            player.setNationality(json.getString("nationality"));
            player.setTeam(json.getString("team"));
            player.setRole(json.getString("role"));
            player.setBattingStyle(json.getString("battingStyle"));
            player.setBowlingStyle(json.getString("bowlingStyle"));
            player.setCurrentMatchStatus(json.getString("currentMatchStatus"));
            playerService.addPlayerDetails(player);
            responseStructure.setStatus(HttpServletResponse.SC_CREATED);
            responseStructure.setMessage("Player added successfully.");
            responseStructure.setData(player.getName());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.info("Added new player: {}", player.getName());
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid input format. Please check your data.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Invalid input format", e);
        }
    }

    private void updatePlayer(HttpServletRequest request, HttpServletResponse response, ResponseStructure<PlayerDetails> responseStructure)
            throws IOException {
        try {
            int id = json.getInt("playerId");
            PlayerDetails player = playerService.getPlayerDetails(id);
            if (player != null) {
                player.setName(json.getString("name"));
                player.setAge(Integer.parseInt(json.getString("age")));
                player.setNationality(json.getString("nationality"));
                player.setTeam(json.getString("team"));
                player.setRole(json.getString("role"));
                player.setBattingStyle(json.getString("battingStyle"));
                player.setBowlingStyle(json.getString("bowlingStyle"));
                player.setCurrentMatchStatus(json.getString("currentMatchStatus"));
                playerService.updatePlayerDetails(player);
                responseStructure.setStatus(HttpServletResponse.SC_OK);
                responseStructure.setMessage("Player updated successfully.");
                responseStructure.setData(player);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.info("Updated player with ID: {}", id);
            } else {
                responseStructure.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseStructure.setMessage("Player not found for update.");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.warn("Player not found for update with ID: {}", id);
            }
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid input format. Please check your data.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Invalid input format", e);
        }
    }

    private void deletePlayer(HttpServletRequest request, HttpServletResponse response, ResponseStructure<String> responseStructure)
            throws IOException {
        try {
            long id = json.getLong("playerId");
            playerService.deletePlayerDetails(id);
            responseStructure.setStatus(HttpServletResponse.SC_OK);
            responseStructure.setMessage("Player deleted successfully.");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.info("Deleted player with ID: {}", id);
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid player ID format.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Invalid player ID format", e);
        }
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response, ResponseStructure<PlayerDetails> responseStructure)
            throws IOException {
        String idParam = json.getString("playerId");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                PlayerDetails player = playerService.getPlayerDetails(id);
                if (player != null) {
                    responseStructure.setStatus(HttpServletResponse.SC_OK);
                    responseStructure.setMessage("Player details retrieved successfully for update.");
                    responseStructure.setData(player);
                } else {
                    responseStructure.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    responseStructure.setMessage("Player not found for update.");
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.info("Preparing update form for player ID: {}", id);
            } catch (NumberFormatException e) {
                responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseStructure.setMessage("Invalid player ID format.");
                response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
                logger.error("Invalid player ID format: {}", idParam, e);
            }
        } else {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Player ID not provided for update.");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
            logger.error("Player ID not provided for update.");
        }
    }
}
