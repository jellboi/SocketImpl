package com.socketimpl.games.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.socketimpl.api.IDao;
import com.socketimpl.beans.ChatRoom;
import com.socketimpl.daos.GenericDao;
import com.socketimpl.games.beans.RPSGame;
import com.socketimpl.games.beans.RPSPlayer;
import com.socketimpl.games.services.RPSService;

public class RPSServiceImpl implements RPSService {
	
	public static final Logger LOGGER = Logger.getLogger(RPSServiceImpl.class);

	private IDao genericDao = GenericDao.getInstance();
	
	private List<RPSGame> rpsGames = new ArrayList<RPSGame>();
	private List<RPSPlayer> rpsPlayers = new ArrayList<RPSPlayer>();
	
	private Map<String, RPSGame> rpsGameNameMap = new HashMap<String, RPSGame>();
	
	
	
	/* Singleton Implementation */
	
	private static final RPSServiceImpl INSTANCE = new RPSServiceImpl();
	
	private RPSServiceImpl() {
		super();
	}
	
	public static RPSServiceImpl getInstance() { 
		return INSTANCE; 
	}
	
	
	
	/* RPS Players */
	
	public List<RPSPlayer> fetchAllConnectedPlayers() {
		if(rpsPlayers == null) 
			rpsPlayers = new ArrayList<RPSPlayer>();
		return rpsPlayers;
	}
	
	
	/* RPS Game */

	public RPSGame findOrCreateRPSGame(RPSGame inRpsGame) {
		RPSGame rpsGame = rpsGameNameMap.get(inRpsGame.getName());
		if(rpsGame == null) {
			// Add to the lists
			rpsGames.add(inRpsGame);
			rpsGameNameMap.put(inRpsGame.getName(), inRpsGame);
			return inRpsGame;
		}
		return rpsGame;
	}

	public void closeRPSGame(String rpsGameName) {
		RPSGame rpsGame = rpsGameNameMap.get(rpsGameName);
		if(rpsGame != null) {
			// remove from the lists
			rpsGames.remove(rpsGame);
			rpsGameNameMap.remove(rpsGameName);
		}
	}

	
	public List<RPSGame> fetchAllRPSGames() {
		if(rpsGames == null)
			rpsGames = new ArrayList<RPSGame>();
		return rpsGames;
	}
	
	

	public Boolean joinRPSGame(String rpsGameName, RPSPlayer rpsPlayer) {
		RPSGame rpsGame = rpsGameNameMap.get(rpsGameName);
		if(rpsGame != null) {
			// add RPSPlayer to the Game
			rpsGame.addPlayer(rpsPlayer);
			return true;
		}
		return false;
	}

	public Boolean leaveRPSGame(String rpsGameName, RPSPlayer rpsPlayer) {
		RPSGame rpsGame = rpsGameNameMap.get(rpsGameName);
		if(rpsGame != null) {
			// remove RPSPlayer from the Game
			rpsGame.removePlayer(rpsPlayer);
			return true;
		}
		return false;
	}
	
	
}
