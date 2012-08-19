package com.socketimpl.servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.log4j.Logger;

import com.socketimpl.beans.ChatRoom;
import com.socketimpl.beans.ChatUser;
import com.socketimpl.beans.User;
import com.socketimpl.services.ChatService;
import com.socketimpl.services.UserService;
import com.socketimpl.services.impl.ChatServiceImpl;
import com.socketimpl.services.impl.UserServiceImpl;

@WebServlet(name = "chatServlet", displayName = "chatServlet", urlPatterns = "/chat/room")
public class ChatServlet extends WebSocketServlet {

	private static final Logger LOGGER = Logger.getLogger(ChatServlet.class);
	
	private UserService userService = UserServiceImpl.getInstance();
	private ChatService chatService = ChatServiceImpl.getInstance();
	
	private ChatUser chatUser = null;
	private ChatRoom chatRoom = null;
	
	
	/* Methods */
	
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		return new ChatMessageInbound();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		
		// Chat User
		User inUser = (User) session.getAttribute("user");
		if(inUser == null) {
			String emailId = request.getParameter("emailId");
			if(emailId == null) {
				inUser = new User("Anon", "anonymous@anon.com", "Who Am I");
			} else {
				inUser = userService.findOrCreateUser(new User(null, emailId, null));
			}
		}
	
		this.chatUser = new ChatUser(inUser, null);
		
		// ChatRoom
		String roomName = request.getParameter("roomName");
		this.chatRoom = chatService.findOrCreateChatRoom(new ChatRoom(roomName, null));
		
		// Continue ...
		super.doGet(request, response);
	}

	
	/* Getters and Setters */


	/* Private Classes */
	
	private final class ChatMessageInbound extends MessageInbound {
		
		private ChatUser locUser;
		private ChatRoom locRoom;

		/* Methods */
		
		@Override
		protected void onOpen(WsOutbound outbound) {
			if(chatUser != null) {
				chatUser.setOutbound(outbound);
				
				// Join the ChatRoom
				if(chatRoom != null)
					chatService.joinChatRoom(chatRoom.getName(), chatUser);
				
				this.locUser = chatUser;
				this.locRoom = chatRoom;
			}
			
			LOGGER.info("No. of users in " + chatRoom.getName() + " : " + chatService.findOrCreateChatRoom(chatRoom).getChatUsers().size());
		}
		
		@Override
		protected void onClose(int status) {
			
			// Remove from the room ..
			chatService.leaveChatRoom(this.locRoom.getName(), this.locUser);
			
			// BroadCast
			broadcast( this.locUser.getDisplayName() + " left the chat !!!");
			
			LOGGER.info("No. of Connections : " );
		}

		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			// this application does not expect binary data
			throw new UnsupportedOperationException("Binary message not supported.");
		}

		@Override
		protected void onTextMessage(CharBuffer msgBuffer) throws IOException {
			String message = msgBuffer.toString();

			// Update the message string with name
			message = this.locUser.getDisplayName() + " : " + message;
			
			// Broadcast
			broadcast(message);
		}

		private void broadcast(String message) {
			ChatRoom currRoom = chatService.findOrCreateChatRoom(this.locRoom);
			for (ChatUser user : currRoom.getChatUsers()) {
				try {
					CharBuffer buffer = CharBuffer.wrap(message);
					WsOutbound userOutbound = user.getOutbound();
					
					// Push the message to other users
					if(userOutbound != null)
						userOutbound.writeTextMessage(buffer);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
}
