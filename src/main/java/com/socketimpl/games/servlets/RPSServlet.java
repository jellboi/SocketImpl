package com.socketimpl.games.servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.List;

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

import com.socketimpl.beans.User;
import com.socketimpl.games.beans.RPSChoice;
import com.socketimpl.games.beans.RPSGame;
import com.socketimpl.games.beans.RPSPlayer;
import com.socketimpl.games.services.RPSService;
import com.socketimpl.games.services.impl.RPSServiceImpl;
import com.socketimpl.services.UserService;
import com.socketimpl.services.impl.UserServiceImpl;

@WebServlet(name = "rpsServlet", displayName = "rpsServlet", urlPatterns = "/game/rps/play")
public class RPSServlet extends WebSocketServlet {

	private static final Logger LOGGER = Logger.getLogger(RPSServlet.class);
	
	private UserService userService = UserServiceImpl.getInstance();
	private RPSService rpsService = RPSServiceImpl.getInstance();
	
	private RPSPlayer rpsPlayer = null;
	private RPSGame rpsGame = null;

	
	/* Methods */
	
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		return new RPSGameInbound();
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
	
		this.rpsPlayer = new RPSPlayer(inUser, null);
		
		// ChatRoom
		String gameId = request.getParameter("gameId");
		this.rpsGame = rpsService.findOrCreateRPSGame(new RPSGame(gameId, null));
		
		// Continue ...
		super.doGet(request, response);
	}


	/* Getters and Setters */
	
	

	
	
	/* Private Classes */

	
	private final class RPSGameInbound extends MessageInbound {

		private RPSPlayer locPlayer;
		private RPSGame locGame;
		

		/* Methods */
		
		@Override
		protected void onOpen(WsOutbound outbound) {
			if(rpsPlayer != null) {
				this.locPlayer = rpsPlayer;
				this.locGame = rpsGame;
				
				this.locPlayer.setOutbound(outbound);
				
				// Join the RPS Game
				if(this.locGame != null)
					rpsService.joinRPSGame(this.locGame.getName(), this.locPlayer);
				
				if(this.locGame.getPlayers().size() == RPSGame.maxNoOfPlayers) {
					broadCastMessage("GAME_IS_READY");
				}
			}
		}
		
		@Override
		protected void onClose(int status) {
//			// Remove from the room ..
			rpsService.leaveRPSGame(this.locGame.getName(), this.locPlayer);
			
			if(this.locGame.getPlayers().size() != RPSGame.maxNoOfPlayers) {
				broadCastMessage("GAME_IS_NOT_READY");
			}
			
			// BroadCast
			broadCastMessage("MSG:" + this.locPlayer.getDisplayName() + " left the Game !!!");
			
			LOGGER.info("No. of Players in the Game " + this.locGame.getName() + " : " + this.locGame.getPlayers().size() );
		}

		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			// this application does not expect binary data
			throw new UnsupportedOperationException("Binary message not supported.");
		}

		@Override
		protected void onTextMessage(CharBuffer msgBuffer) throws IOException {
			String message = msgBuffer.toString();
			
			if(message.startsWith("READY:")) {
				this.locPlayer.setIsReady(true);
				if(this.locGame.isAllPlayersReady()) {
					broadCastMessage("START");
				}
				
			} else if (message.startsWith("CHOICE:")) {
				RPSChoice choice = RPSChoice.valueOf(message.substring(7));
				
				// TODO Check whether the players are ready or Not and accept choice
				
				this.locPlayer.setRpsChoice(choice);
				verifyAndBroadCastResult();
				
			} else if (message.startsWith("MSG:")) {
				message = message.substring(4);
				
				// Update the message string with name
				message = this.locPlayer.getDisplayName() + " : " + message;
				broadCastMessage("MSG:" + message);
			}
		}

		// TODO Make it generic
		private void verifyAndBroadCastResult() {
			try {
				List<RPSPlayer> players = this.locGame.getPlayers();
				
				RPSPlayer leftPlayer = players.get(0);
				RPSChoice leftPlayerChoice = leftPlayer.getRpsChoice();
				
				RPSPlayer rightPlayer = players.get(1);
				RPSChoice rightPlayerChoice = rightPlayer.getRpsChoice();
				
				if(leftPlayerChoice != null && rightPlayerChoice != null){
					if(leftPlayerChoice.equals(rightPlayerChoice)) {
						broadCastMessage("RESULT:TIE");
					} else {
						if(leftPlayerChoice.winsOn().equals(rightPlayerChoice)){
							leftPlayer.addAWinToCount();
							leftPlayer.getOutbound().writeTextMessage(CharBuffer.wrap("RESULT:WON"));
							rightPlayer.getOutbound().writeTextMessage(CharBuffer.wrap("RESULT:LOST"));
						} else {
							rightPlayer.addAWinToCount();
							rightPlayer.getOutbound().writeTextMessage(CharBuffer.wrap("RESULT:WON"));
							leftPlayer.getOutbound().writeTextMessage(CharBuffer.wrap("RESULT:LOST"));
						}
					}
					
					// Reset Game
					leftPlayer.resetForNextRound();
					rightPlayer.resetForNextRound();
					this.locGame.addARoundToCount();
					this.locGame.resetForNextRound();
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void broadCastMessage(String message) {
			RPSGame currGame = rpsService.findOrCreateRPSGame(this.locGame);
			for (RPSPlayer player : currGame.getPlayers()) {
				try {
					CharBuffer buffer = CharBuffer.wrap(message);
					WsOutbound userOutbound = player.getOutbound();
					
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
