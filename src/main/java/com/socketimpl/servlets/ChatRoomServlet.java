package com.socketimpl.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.socketimpl.beans.User;

@WebServlet(name = "chatRoomServlet", displayName = "chatRoomServlet", urlPatterns = "/chat")
public class ChatRoomServlet extends HttpServlet {
	
	private String responseUrl = "/pages/error.jsp";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		HttpSession session= request.getSession(true);
		User user = (User) session.getAttribute("user");
		
		if(user != null) {
			responseUrl = "/pages/chat/chat_room.jsp"; 
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
