package com.socketimpl.games.beans;

import java.util.ArrayList;
import java.util.List;

import com.socketimpl.beans.ChatUser;

public class RPSGame {
	
	public static final Integer maxNoOfPlayers = 2; 

	private String id;
	
	private String name;
	private Integer roundsCount;
	
	private List<RPSPlayer> players = new ArrayList<RPSPlayer>();
	
	private Boolean isAllPlayersReady = false;
	
	
	/* Constructors */

	public RPSGame(String name) {
		super();
		this.name = name;
		
		// Set Id 
		
	}
	
	public RPSGame(String name, List<RPSPlayer> players) {
		super();
		this.name = name;
		this.players = players;

		// Set Id
		
	}
	
	/* Methods */
	
	public void addPlayer(RPSPlayer rpsPlayer) {
		if(players == null) {
			players = new ArrayList<RPSPlayer>();
		}
		
		if(players.size() < maxNoOfPlayers)
			players.add(rpsPlayer);
		
	}

	public void removePlayer(RPSPlayer rpsPlayer) {
		if(players != null) {
			players.remove(rpsPlayer);
		}
	}
	
	public Integer addARoundToCount(){
		if(roundsCount == null)
			roundsCount = 0;
		
		roundsCount++;
		return roundsCount;
	}
	
	public Boolean isAllPlayersReady() {
		this.isAllPlayersReady = true;
		
		for (RPSPlayer player : this.players) {
			if(!player.getIsReady())
				isAllPlayersReady = false;
		}
		
		return isAllPlayersReady;
	}
	
	public void resetForNextRound() {
		this.isAllPlayersReady = false;
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

	public Integer getRoundsCount() {
		return roundsCount;
	}

	public void setRoundsCount(Integer roundsCount) {
		this.roundsCount = roundsCount;
	}

	public List<RPSPlayer> getPlayers() {
		if(players == null)
			players = new ArrayList<RPSPlayer>();
		return players;
	}

	public void setPlayers(List<RPSPlayer> players) {
		this.players = players;
	}

	public Boolean getIsAllPlayersReady() {
		return isAllPlayersReady;
	}

	public void setIsAllPlayersReady(Boolean isAllPlayersReady) {
		this.isAllPlayersReady = isAllPlayersReady;
	}
	
}
