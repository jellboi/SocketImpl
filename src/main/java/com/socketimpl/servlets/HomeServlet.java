package com.socketimpl.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.socketimpl.beans.ChatRoom;
import com.socketimpl.beans.User;
import com.socketimpl.services.ChatService;
import com.socketimpl.services.UserService;
import com.socketimpl.services.impl.ChatServiceImpl;
import com.socketimpl.services.impl.UserServiceImpl;

@WebServlet(name = "homeServlet", displayName="HomeServlet", urlPatterns= "/home")
public class HomeServlet extends HttpServlet {
	
	public static final Logger LOGGER = Logger.getLogger(HomeServlet.class);
	
	private UserService userService = UserServiceImpl.getInstance();
	private ChatService chatService = ChatServiceImpl.getInstance();
	
	private String responseUrl = "/pages/error.jsp";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		String emailId = request.getParameter("emailId");
		String fullName = request.getParameter("fullName");
		String displayName = request.getParameter("displayName");
		
		if(emailId != null) {
			HttpSession session= request.getSession(true);
			
			User user = userService.findOrCreateUser(new User(fullName, emailId, displayName));
			if(user != null) {
				session.setAttribute("user", user);
			}
			
			// Setting the chat rooms
			List<ChatRoom> chatRooms = chatService.fetchAllChatRooms();
			session.setAttribute("chatRooms", chatRooms);
			
			responseUrl = "/pages/home.jsp"; 
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
