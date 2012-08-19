package com.socketimpl.games.beans;

public enum RPSChoice {
	
	ROCK (1, 2, 3), 
	PAPER (2, 3, 1), 
	SCISSORS (3, 1, 2);
	
	private final Integer id;
	private final Integer losesToId;
	private final Integer winsOnId;
	
	
	/* Constructor */

	private RPSChoice(Integer id, Integer losesToId, Integer winsOnId){
		this.id = id;
		this.losesToId = losesToId;
		this.winsOnId = winsOnId;
	}
	
	/* Methods */
	
	public Integer id() {
		return id;
	}
	
	public RPSChoice losesTo() {
		switch (losesToId) {
			case 1:
				return RPSChoice.ROCK;
			case 2:
				return RPSChoice.PAPER;
			case 3:
				return RPSChoice.SCISSORS;
			default:
				return null;
		}
	}
	
	public RPSChoice winsOn() {
		switch (winsOnId) {
			case 1:
				return RPSChoice.ROCK;
			case 2:
				return RPSChoice.PAPER;
			case 3:
				return RPSChoice.SCISSORS;
			default:
				return null;
		}
	}
	
}
