package com.ta.livewicketplus.controllers;
import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;

import com.ta.livewicketplus.dto.PlayerDetails;
import com.ta.livewicketplus.service.PlayerService;
import com.ta.livewicketplus.util.LogUtil;

@WebServlet("/PlayerServlet")
public class PlayerDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogUtil.getPlayerServletLogger();
    private final PlayerService playerService = new PlayerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        logger.info("Action received: {}", action);

        if (action == null || action.isEmpty() || "list".equals(action)) {
            listPlayers(request, response);
        } else if ("view".equals(action)) {
            viewPlayer(request, response);
        } else if ("delete".equals(action)) {
            deletePlayer(request, response);
        } else if ("update".equals(action)) {
            showUpdateForm(request, response);
        } else {
            logger.warn("Unknown action: {}", action);
            listPlayers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        logger.info("Action received: {}", action);

        if ("add".equals(action)) {
            addPlayer(request, response);
        } else if ("update".equals(action)) {
            updatePlayer(request, response);
        } else {
            logger.warn("Unknown action: {}", action);
            listPlayers(request, response);
        }
    }

    private void listPlayers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<PlayerDetails> players = playerService.getAllPlayerDetails();
        request.setAttribute("players", players);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listPlayers.jsp");
        dispatcher.forward(request, response);
        logger.info("Listed all players");
    }

    private void viewPlayer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("playerId");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                PlayerDetails player = playerService.getPlayerDetails(id);
                request.setAttribute("player", player);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPlayerDetails.jsp");
                dispatcher.forward(request, response);
                logger.info("Viewed player details for ID: {}", id);
            } catch (NumberFormatException e) {
                logger.error("Invalid player ID format: {}", idParam, e);
                request.setAttribute("error", "Invalid player ID format.");
                listPlayers(request, response);
            }
        } else {
            logger.error("Invalid player ID format: {}", idParam);
            listPlayers(request, response);
        }
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("playerId");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                PlayerDetails player = playerService.getPlayerDetails(id);
                request.setAttribute("player", player);
                RequestDispatcher dispatcher = request.getRequestDispatcher("updatePlayer.jsp");
                dispatcher.forward(request, response);
                logger.info("Preparing update form for player ID: {}", id);
            } catch (NumberFormatException e) {
                logger.error("Invalid player ID format: {}", idParam, e);
                request.setAttribute("error", "Invalid player ID format.");
                listPlayers(request, response);
            }
        } else {
            logger.error("Player ID not provided for update.");
            listPlayers(request, response);
        }
    }

    private void addPlayer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PlayerDetails player = new PlayerDetails();
            player.setName(request.getParameter("name"));
            player.setAge(Integer.parseInt(request.getParameter("age")));
            player.setNationality(request.getParameter("nationality"));
            player.setTeam(request.getParameter("team"));
            player.setRole(request.getParameter("role"));
            player.setBattingStyle(request.getParameter("battingStyle"));
            player.setBowlingStyle(request.getParameter("bowlingStyle"));
            player.setCurrentMatchStatus(request.getParameter("currentMatchStatus"));
            playerService.addPlayerDetails(player);
            logger.info("Added new player: {}", player.getName());
            response.sendRedirect("PlayerServlet?action=list");
        } catch (NumberFormatException e) {
            logger.error("Invalid input format", e);
            request.setAttribute("error", "Invalid input format. Please check your data.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addPlayer.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void updatePlayer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            int id = Integer.parseInt(request.getParameter("id").trim());
            PlayerDetails player = playerService.getPlayerDetails(id);
            if (player != null) {
                player.setName(request.getParameter("name"));
                player.setAge(Integer.parseInt(request.getParameter("age")));
                player.setNationality(request.getParameter("nationality"));
                player.setTeam(request.getParameter("team"));
                player.setRole(request.getParameter("role"));
                player.setBattingStyle(request.getParameter("battingStyle"));
                player.setBowlingStyle(request.getParameter("bowlingStyle"));
                player.setCurrentMatchStatus(request.getParameter("currentMatchStatus"));
                playerService.updatePlayerDetails(player);
                logger.info("Updated player with ID: {}", id);
                response.sendRedirect("PlayerServlet?action=list");
            } else {
                logger.warn("Player not found for update with ID: {}", id);
                listPlayers(request, response);
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid input format", e);
            request.setAttribute("error", "Invalid input format. Please check your data.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/updatePlayer.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deletePlayer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Integer.parseInt(request.getParameter("playerId"));
            playerService.deletePlayerDetails(id);
            logger.info("Deleted player with ID: {}", id);
            response.sendRedirect("PlayerServlet?action=list");
        } catch (NumberFormatException e) {
            logger.error("Invalid player ID format", e);
            request.setAttribute("error", "Invalid player ID format. Please check your data.");
            listPlayers(request, response);
        }
    }
}
