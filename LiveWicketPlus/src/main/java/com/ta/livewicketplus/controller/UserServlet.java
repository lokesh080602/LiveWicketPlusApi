package com.ta.livewicketplus.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ta.livewicketplus.dto.User;
import com.ta.livewicketplus.service.UserService;
import com.ta.livewicketplus.util.LogUtil;
import com.ta.livewicketplus.util.ResponseStructure;

@WebServlet("/UserServlets")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogUtil.getUserServletLogger();
    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    JSONObject json;

    @SuppressWarnings("unchecked")
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseStructure<?> responseStructure = new ResponseStructure<>();
        
        try {
            switch (action != null ? action : "") {
                case "list":
                    listUsers(response, (ResponseStructure<List<User>>) responseStructure);
                    break;
                case "view":
                    viewUser(request, response, (ResponseStructure<User>) responseStructure);
                    break;
                case "delete":
                    deleteUser(request, response, (ResponseStructure<String>) responseStructure);
                    break;
                case "update":
                    showUpdateForm(request, response, (ResponseStructure<User>) responseStructure);
                    break;
                default:
                    responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    responseStructure.setMessage("Unknown action.");
                    sendResponse(response, responseStructure);
                    logger.warn("Unknown action: {}", action);
            }
        } catch (Exception e) {
            handleException(response, e, responseStructure);
        }
    }

    @SuppressWarnings("unchecked")
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
	
    	String action=json.getString("action");
        logger.info("Action received: {}", action);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseStructure<?> responseStructure = new ResponseStructure<>();
        
        try {
            switch (action != null ? action : "") {
                case "add":
                    addUser(request, response, (ResponseStructure<String>) responseStructure);
                    break;
                case "update":
                    updateUser(request, response, (ResponseStructure<User>) responseStructure);
                    break;
                default:
                    responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    responseStructure.setMessage("Unknown action.");
                    sendResponse(response, responseStructure);
                    logger.warn("Unknown action: {}", action);
            }
        } catch (Exception e) {
            handleException(response, e, responseStructure);
        }
    }

    private void listUsers(HttpServletResponse response, ResponseStructure<List<User>> responseStructure)
            throws IOException {
        List<User> users = userService.getAllUsers();
        responseStructure.setStatus(HttpServletResponse.SC_OK);
        responseStructure.setMessage("Users listed successfully.");
        responseStructure.setData(users);
        sendResponse(response, responseStructure);
        logger.info("Listed all users");
    }

    private void viewUser(HttpServletRequest request, HttpServletResponse response, ResponseStructure<User> responseStructure)
            throws IOException {
        try {
            // First, check if the JSON contains the required "id" key
            if (!json.has("id")) {
                responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseStructure.setMessage("User ID not provided.");
                sendResponse(response, responseStructure);
                logger.error("User ID not provided in JSON.");
                return;
            }

            // Get the user ID from the JSON and validate the input format
            String idParam = String.valueOf(json.getInt("id"));
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    User user = userService.getUserById(id);
                    
                    if (user != null) {
                        // User found, set success response
                        responseStructure.setStatus(HttpServletResponse.SC_OK);
                        responseStructure.setMessage("User details retrieved successfully.");
                        responseStructure.setData(user);
                        logger.info("Viewed user profile for ID: {}", id);
                    } else {
                        // User not found, set not found response
                        responseStructure.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        responseStructure.setMessage("User not found.");
                        logger.warn("User not found for ID: {}", id);
                    }
                    
                    // Send the response after user lookup
                    sendResponse(response, responseStructure);
                    
                } catch (NumberFormatException e) {
                    // Handle invalid user ID format
                    responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    responseStructure.setMessage("Invalid user ID format.");
                    sendResponse(response, responseStructure);
                    logger.error("Invalid user ID format: {}", idParam, e);
                }
            } else {
                // If user ID is not provided or empty
                responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseStructure.setMessage("User ID not provided.");
                sendResponse(response, responseStructure);
                logger.error("User ID not provided or is empty.");
            }
        } catch (JSONException e) {
            // Handle JSON parsing errors
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid JSON input.");
            sendResponse(response, responseStructure);
            logger.error("Error parsing JSON input", e);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            responseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseStructure.setMessage("An unexpected error occurred.");
            sendResponse(response, responseStructure);
            logger.error("Unexpected error while viewing user", e);
        }
    }



    private void addUser(HttpServletRequest request, HttpServletResponse response, ResponseStructure<String> responseStructure)
            throws IOException {
        try {
            User user = new User();
            user.setUsername(json.getString("username"));
            user.setPassword(json.getString("password"));
            user.setEmail(json.getString("email"));
            user.setFavoritePlayers(json.getString("favoritePlayers"));
            user.setFavoriteTeams(json.getString("favoriteTeams"));
            boolean userExist = userService.alreadExitUser(user);
            if (!userExist) {
                userService.saveUser(user);
                responseStructure.setStatus(HttpServletResponse.SC_CREATED);
                responseStructure.setMessage("User added successfully.");
                responseStructure.setData(user.getUsername());
            } else {
                responseStructure.setStatus(HttpServletResponse.SC_CONFLICT);
                responseStructure.setMessage("User already exists.");
            }
            sendResponse(response, responseStructure);
            logger.info("Added new user: {}", user.getUsername());
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid input format.");
            sendResponse(response, responseStructure);
            logger.error("Invalid input format", e);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response, ResponseStructure<User> responseStructure)
            throws IOException {
        try {
            long id = json.getLong("id");
            User user = userService.getUserById(id);
            if (user != null) {
                user.setUsername(json.getString("username"));
                user.setPassword(json.getString("password"));
                user.setEmail(json.getString("email"));
                user.setFavoritePlayers(json.getString("favoritePlayers"));
                user.setFavoriteTeams(json.getString("favoriteTeams"));

                
                responseStructure.setStatus(HttpServletResponse.SC_OK);
                responseStructure.setMessage("User updated successfully.");
                responseStructure.setData(user);
                
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", user);

               
            } else {

                responseStructure.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseStructure.setMessage("User not found for update.");
                sendResponse(response, responseStructure);
                logger.warn("User not found for update with ID: {}", id);
            }
            sendResponse(response, responseStructure);
        } catch (JSONException e) {

            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid JSON input.");
            sendResponse(response, responseStructure);
            logger.error("JSON parsing error", e);
        } catch (NumberFormatException e) {
            // Handle invalid input format
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid input format.");
            sendResponse(response, responseStructure);
            logger.error("Invalid input format", e);
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            responseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseStructure.setMessage("An unexpected error occurred.");
            sendResponse(response, responseStructure);
            logger.error("Unexpected error", e);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response, ResponseStructure<String> responseStructure)
            throws IOException {
        try {
            long id =json.getLong("id");
            logger.info("Deleted user with ID: {}", id);
            if(userService.getUserById(id)!=null) {
            
            userService.deleteUser(id);
            responseStructure.setStatus(HttpServletResponse.SC_OK);
            responseStructure.setMessage("User deleted successfully.");
            sendResponse(response, responseStructure);
            }
            else {
            	 responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                 responseStructure.setMessage("Invalid user ID");
                 sendResponse(response, responseStructure);
                 logger.error("Invalid user ID");
            }
        } catch (NumberFormatException e) {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("Invalid user ID format.");
            sendResponse(response, responseStructure);
            logger.error("Invalid user ID format", e);
        }
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response, ResponseStructure<User> responseStructure)
            throws IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                long id = json.getInt(idParam);
                User user = userService.getUserById(id);
                if (user != null) {
                    responseStructure.setStatus(HttpServletResponse.SC_OK);
                    responseStructure.setMessage("User details retrieved successfully for update.");
                    responseStructure.setData(user);
                } else {
                    responseStructure.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    responseStructure.setMessage("User not found for update.");
                }
                sendResponse(response, responseStructure);
                logger.info("Preparing update form for user ID: {}", id);
            } catch (NumberFormatException e) {
                responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseStructure.setMessage("Invalid user ID format.");
                sendResponse(response, responseStructure);
                logger.error("Invalid user ID format: {}", idParam, e);
            }
        } else {
            responseStructure.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseStructure.setMessage("User ID not provided for update.");
            sendResponse(response, responseStructure);
            logger.error("User ID not provided for update.");
        }
    }

    private void sendResponse(HttpServletResponse response, ResponseStructure<?> responseStructure) throws IOException {
        response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
    }

    private void handleException(HttpServletResponse response, Exception e, ResponseStructure<?> responseStructure) throws IOException {
        responseStructure.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        responseStructure.setMessage("Internal server error.");
        sendResponse(response, responseStructure);
        logger.error("Internal server error", e);
    }
}
