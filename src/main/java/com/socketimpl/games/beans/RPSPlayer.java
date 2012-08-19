package com.socketimpl.games.beans;

import org.apache.catalina.websocket.WsOutbound;

import com.socketimpl.beans.User;

public class RPSPlayer extends User {
	
	private WsOutbound outbound;
	
	private Integer winsCount = 0;
	
	private Boolean isReady = false;
	private RPSChoice rpsChoice;

	
	

	/* Constructors */

	public RPSPlayer(User user, WsOutbound outbound) {
		super(user.getFullName(), user.getEmailId(), user.getDisplayName());
		this.outbound = outbound;
	}
	
	
	
	/* Methods */
	
	public Integer addAWinToCount(){
		if(winsCount == null)
			winsCount = 0;
		
		winsCount++;
		return winsCount;
	}
	
	public void resetForNextRound() {
		this.isReady = false;
		this.rpsChoice = null;
	}
	
	
	/* Getters and Setters */
	
	public WsOutbound getOutbound() {
		return outbound;
	}

	public void setOutbound(WsOutbound outbound) {
		this.outbound = outbound;
	}
	
	public Integer getWinsCount() {
		return winsCount;
	}

	public void setWinsCount(Integer winsCount) {
		this.winsCount = winsCount;
	}

	public Boolean getIsReady() {
		return isReady;
	}

	public void setIsReady(Boolean isReady) {
		this.isReady = isReady;
	}

	public RPSChoice getRpsChoice() {
		return rpsChoice;
	}

	public void setRpsChoice(RPSChoice rpsChoice) {
		this.rpsChoice = rpsChoice;
	}

	
}
