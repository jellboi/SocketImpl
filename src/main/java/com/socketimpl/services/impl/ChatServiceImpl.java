package com.socketimpl.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.socketimpl.api.IDao;
import com.socketimpl.beans.ChatRoom;
import com.socketimpl.beans.ChatUser;
import com.socketimpl.daos.GenericDao;
import com.socketimpl.services.ChatService;

public class ChatServiceImpl implements ChatService {
	
	public static final Logger LOGGER = Logger.getLogger(ChatServiceImpl.class);

	private IDao genericDao = GenericDao.getInstance();
	
	private List<ChatRoom> chatRooms = new ArrayList<ChatRoom>();
	private List<ChatUser> chatUsers = new ArrayList<ChatUser>();
	
	private Map<String, ChatRoom> chatRoomNameMap = new HashMap<String, ChatRoom>();
	
	
	/* Singleton Implementation */
	
	private static final ChatServiceImpl INSTANCE = new ChatServiceImpl();
	
	private ChatServiceImpl() {
		super();
		this.createDefaultChatRooms();
	}
	
	public static ChatServiceImpl getInstance() { 
		return INSTANCE; 
	}
	
	
	
	/* ChatUsers */

	public List<ChatUser> fetchAllConnectedChatUsers() {
		if(chatUsers == null) 
			chatUsers = new ArrayList<ChatUser>();
		return chatUsers;
	}
	
	
	
	/* Chat Room */

	public ChatRoom findOrCreateChatRoom(ChatRoom inChatRoom) {
		ChatRoom chatRoom = chatRoomNameMap.get(inChatRoom.getName());
		if(chatRoom == null) {
			// Add to the lists
			chatRooms.add(inChatRoom);
			chatRoomNameMap.put(inChatRoom.getName(), inChatRoom);
			return inChatRoom;
		}
		return chatRoom;
	}

	public void closeChatRoom(String roomName) {
		ChatRoom chatRoom = chatRoomNameMap.get(roomName);
		if(chatRoom != null) {
			// remove from the lists
			chatRooms.remove(chatRoom);
			chatRoomNameMap.remove(roomName);
		}
	}

	public List<ChatRoom> fetchAllChatRooms() {
		if(chatRooms == null)
			chatRooms = new ArrayList<ChatRoom>();
		return chatRooms;
	}
	
	
	

	public Boolean joinChatRoom(String roomName, ChatUser chatUser) {
		ChatRoom chatRoom = chatRoomNameMap.get(roomName);
		if(chatRoom != null) {
			// add ChatUser to the list
			chatRoom.addParticipant(chatUser);
			return true;
		}
		return false;
	}
	
	public Boolean leaveChatRoom(String roomName, ChatUser chatUser) {
		ChatRoom chatRoom = chatRoomNameMap.get(roomName);
		if(chatRoom != null) {
			// remove ChatUser from the list
			chatRoom.removeParticipant(chatUser);
			return true;
		}
		return false;
	}
	
	
	/* Private Methods */
	
	private void createDefaultChatRooms(){
		this.findOrCreateChatRoom(new ChatRoom("Practice_School", " Hahaha "));
		this.findOrCreateChatRoom(new ChatRoom("Pramati_Chennai", " pramati "));
		this.findOrCreateChatRoom(new ChatRoom("Pramati_LSI", " LSI Only "));
	}
	
	

	
	
	/* Getters and Setters */
	
	

}
