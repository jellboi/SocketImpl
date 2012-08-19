package com.socketimpl.games.services;

import java.util.List;

import com.socketimpl.games.beans.RPSGame;
import com.socketimpl.games.beans.RPSPlayer;
import com.socketimpl.services.GameService;

public interface RPSService extends GameService {
	
	
	/* RPS Players */
	
	List<RPSPlayer> fetchAllConnectedPlayers();
	
	
	
	/* RPS Game */
	
	RPSGame findOrCreateRPSGame(RPSGame inRpsGame);

	void closeRPSGame(String rpsGameName);
	
	
	List<RPSGame> fetchAllRPSGames();
	
	
	Boolean joinRPSGame(String rpsGameName, RPSPlayer rpsPlayer);
	
	Boolean leaveRPSGame(String rpsGameName, RPSPlayer rpsPlayer);
	
	

}
