package com.ta.livewicketplus.controllers;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = LogManager.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            logger.info("User {} is logging out.", session.getAttribute("loggedInUser") );
            session.invalidate(); 
            logger.info("Session invalidated successfully.");
        } else {
            logger.warn("Logout attempt with no active session.");
        }
        
        
        response.sendRedirect("index.jsp");
    }
}
