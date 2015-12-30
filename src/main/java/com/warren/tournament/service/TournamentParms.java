package com.warren.tournament.service;

import java.util.Set;

import com.warren.tournament.entity1.Player;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;

public class TournamentParms {
	private Enum<?> format;
	private GameType gameType;
	private MatchingMethod matchingMethod; 
	private Set<Player> players;
	private Integer gamesPerMatch;
	
	public Enum<?> getFormat() {
		return format;
	}
	public void setFormat(Enum<?> format) {
		this.format = format;
	}
	public GameType getGameType() {
		return gameType;
	}
	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}
	public MatchingMethod getMatchingMethod() {
		return matchingMethod;
	}
	public void setMatchingMethod(MatchingMethod matchingMethod) {
		this.matchingMethod = matchingMethod;
	}
	public Set<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	public Integer getGamesPerMatch() {
		return gamesPerMatch;
	}
	public void setGamesPerMatch(Integer gamesPerMatch) {
		this.gamesPerMatch = gamesPerMatch;
	}
}
