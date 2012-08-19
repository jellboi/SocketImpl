package com.socketimpl.services;

import java.util.List;

import com.socketimpl.beans.ChatRoom;
import com.socketimpl.beans.ChatUser;

public interface ChatService {
	
	
	/* ChatUsers */
	
	List<ChatUser> fetchAllConnectedChatUsers();
	
	
	
	/* Chat Room */
	
	ChatRoom findOrCreateChatRoom(ChatRoom inChatRoom);

	void closeChatRoom(String roomName);
	
	
	List<ChatRoom> fetchAllChatRooms();
	
	
	Boolean joinChatRoom(String roomName, ChatUser chatUser);
	
	Boolean leaveChatRoom(String roomName, ChatUser chatUser);
	
	

}
