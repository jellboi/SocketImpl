package com.socketimpl.beans;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
	
	private String id;
	
	private String name;
	private String description;
	
	private List<ChatUser> chatUsers;

	
	/* Constructors */
	
	public ChatRoom(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		
		// SetId
		this.id = null;
	}
	
	public ChatRoom(String name, String description, List<ChatUser> chatUsers) {
		super();
		this.name = name;
		this.description = description;
		this.chatUsers = chatUsers;

		// SetId
		this.id = null;
		
	}
	
	
	/* Methods */
	
	public void addParticipant(ChatUser chatUser) {
		if(chatUsers == null)
			chatUsers = new ArrayList<ChatUser>();
		chatUsers.add(chatUser);
	}
	
	public void removeParticipant(ChatUser chatUser) {
		if(chatUsers != null) {
			chatUsers.remove(chatUser);
		}
	}
	
	

	/* Getters and Setters */
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ChatUser> getChatUsers() {
		if(chatUsers == null)
			chatUsers = new ArrayList<ChatUser>();
		return chatUsers;
	}

	public void setChatUsers(List<ChatUser> chatUsers) {
		this.chatUsers = chatUsers;
	}

}
