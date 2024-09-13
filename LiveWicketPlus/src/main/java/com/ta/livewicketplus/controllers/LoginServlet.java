package com.ta.livewicketplus.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.ta.livewicketplus.dto.User;
import com.ta.livewicketplus.service.UserService;
import com.ta.livewicketplus.util.LogUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final org.apache.logging.log4j.Logger logger = LogUtil.getLoginServletLogger();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		logger.info("Login attempt with username: {}", username);

		UserService userService = new UserService();
		User user = userService.authenticateUser(username, password);

		if (user != null) {
			logger.info("User authenticated successfully: {} ", username);
			HttpSession session = request.getSession();
			session.setAttribute("loggedInUser", user);
			response.sendRedirect("home.jsp");
		} else {
			logger.warn("Authentication failed for username");
			request.setAttribute("error", "Invalid credentials");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
