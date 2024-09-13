package com.ta.livewicketplus.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ta.livewicketplus.controller.LoginServlet;
import com.ta.livewicketplus.controller.LogoutServlet;
import com.ta.livewicketplus.controller.MatchServlet;
import com.ta.livewicketplus.controller.PlayerDetailsServlet;
import com.ta.livewicketplus.controller.UserServlet;

public class LogUtil {

	public static void getLog(){
		System.setProperty("log4j.configurationFile", "D:\\LiveWicketPlus\\config\\log4j2.xml");
		Configurator.initialize(null, System.getProperty("log4j.configurationFile"));
	}
	private LogUtil() {
	}

	public static Logger getPlayerServletLogger() {
		return LogManager.getLogger(PlayerDetailsServlet.class);
	}

	public static Logger getMatchServletLogger() {
		return LogManager.getLogger(MatchServlet.class);
	}

	public static Logger getUserServletLogger() {
		return LogManager.getLogger(UserServlet.class);
	}

	public static Logger getLoginServletLogger() {
		getLog();
		return LogManager.getLogger(LoginServlet.class);
	}

	public static Logger getLogoutServletLogger() {
		return LogManager.getLogger(LogoutServlet.class);
	}

	public static Logger getJPAUtilLogger() {
		return LogManager.getLogger(JPAUtil.class);
	}
}
