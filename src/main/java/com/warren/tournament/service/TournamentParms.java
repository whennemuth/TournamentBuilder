package com.warren.tournament.service;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.warren.tournament.entity.Player;
import com.warren.tournament.enumerator.FormatType;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;

@JsonDeserialize(using=TournamentParmsDeserializer.class)
public class TournamentParms {
	private FormatType format;
	private GameType gameType;
	private MatchingMethod matchingMethod; 
	private Set<Player> players = new HashSet<Player>();
	private Integer gamesPerMatch;
	
	public FormatType getFormat() {
		return format;
	}
	public void setFormat(FormatType format) {
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
	@JsonIgnore
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	public Integer getGamesPerMatch() {
		return gamesPerMatch;
	}
	public void setGamesPerMatch(Integer gamesPerMatch) {
		this.gamesPerMatch = gamesPerMatch;
	}
	
	public static class EnumValues {
		public FormatType[] getFormatTypes() {
			return FormatType.values();
		}
		public GameType[] getGameTypes() {
			return GameType.values();
		}
		public MatchingMethod[] getMatchingMethods() {
			return MatchingMethod.values();
		}
	}
}
