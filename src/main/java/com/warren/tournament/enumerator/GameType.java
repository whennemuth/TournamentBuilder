package com.warren.tournament.enumerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GameType {

	SINGLES("Singles", 1, 2),
	DOUBLES("Doubles", 2, 2),
	CUTTHROAT("Cutthroat", 3, 3);
	
	private String description;
	private int playersPerSide;
	private int sidesPerGame;
	
	private GameType(String description, int playersPerSide, int sidesPerGame) {
		this.description = description;
		this.playersPerSide = playersPerSide;
		this.sidesPerGame = sidesPerGame;
	}
	
	public String getDescription() {
		return description;
	}
	public int getPlayersPerSide() {
		return playersPerSide;
	}
	public int getSidesPerGame(int players) {
		return players/playersPerSide;
	}
	public int getSidesPerGame() {
		return sidesPerGame;
	}
	public String getName() {
		return this.name();
	}
}
