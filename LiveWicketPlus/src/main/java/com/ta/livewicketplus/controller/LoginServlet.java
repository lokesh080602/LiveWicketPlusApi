package com.ta.livewicketplus.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ta.livewicketplus.dto.User;
import com.ta.livewicketplus.service.UserService;
import com.ta.livewicketplus.util.LogUtil;
import com.ta.livewicketplus.util.ResponseStructure;

@WebServlet("/logins")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogUtil.getLoginServletLogger();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("Login attempt with username: {}", username);

        ResponseStructure<User> responseStructure = new ResponseStructure<>();
        UserService userService = new UserService();
        User user = userService.authenticateUser(username, password);

        if (user != null) {
            logger.info("User authenticated successfully: {}", username);
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);

            responseStructure.setStatus(HttpServletResponse.SC_OK);
            responseStructure.setMessage("User authenticated successfully.");
            responseStructure.setData(user);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
        } else {
            logger.warn("Authentication failed for username: {}", username);

            responseStructure.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseStructure.setMessage("Invalid credentials.");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseStructure));
        }
    }
}
