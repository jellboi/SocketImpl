package com.socketimpl.beans;

import org.apache.catalina.websocket.WsOutbound;

public class ChatUser extends User {
	
	private WsOutbound outbound;
	
	/* Constructors */

	public ChatUser(User user, WsOutbound outbound) {
		super(user.getFullName(), user.getEmailId(), user.getDisplayName());
		this.outbound = outbound;
	}
	
	
	/* Getters and Setters */
	
	public WsOutbound getOutbound() {
		return outbound;
	}

	public void setOutbound(WsOutbound outbound) {
		this.outbound = outbound;
	}
	
}
