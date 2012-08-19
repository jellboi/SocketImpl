package com.socketimpl.games.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.socketimpl.beans.User;

@WebServlet(name = "rpsGameServlet", displayName="rpsGameServlet", urlPatterns= "/game/rps")
public class RPSRoomServlet extends HttpServlet {
	
public static final Logger LOGGER = Logger.getLogger(RPSHomeServlet.class);
	
	private String responseUrl = "/pages/error.jsp";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		HttpSession session= request.getSession(true);
		User user = (User) session.getAttribute("user");
		
		if(user != null) {
			responseUrl = "/pages/rps/rps_game.jsp"; 
		}
	    
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(responseUrl);
		dispatcher.include(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
