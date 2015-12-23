package com.warren.tournament.entity;

import java.util.HashSet;
import java.util.Set;

import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;

public class Tournament {

	private Integer id;
	private Set<Bracket> brackets = new HashSet<Bracket>();
	private Set<Player> players = new HashSet<Player>();
	private GameType gameType;
	private MatchingMethod matchingMethod;
	private Enum<?> formatType;
	private String format;
	private Integer gamesPerMatch;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void addPlayer(Player player) {
		players.add(player);
	}
	public void removePlayer(Player player) {
		players.remove(player);
	}
	public void addBracket(Bracket bracket) {
		brackets.add(bracket);
		bracket.setTournament(this);
	}
	public void removeBracket(Bracket bracket) {
		brackets.remove(bracket);
		bracket.setTournament(null);
	}
	public Set<Bracket> getBrackets() {
		return brackets;
	}
	public void setBrackets(Set<Bracket> brackets) {
		this.brackets = brackets;
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
	public Enum<?> getFormatType() {
		return formatType;
	}
	public void setFormatType(Enum<?> formatType) {
		this.formatType = formatType;
		this.format = formatType.name();
	}
	public String getFormat() {
		return this.formatType == null ? format : this.formatType.name();
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getSideCount() {
		int i = 0;
		for(Bracket b : brackets) {
			i += b.getSideCount();
		}
		return i;
	}
	public int getUnBuiltSideCount(GameType gameType) {
		return gameType.getSidesPerGame( players.size()) - getSideCount();
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tournament [brackets=").append(brackets)
				.append(", players=").append(players).append("]");
		return builder.toString();
	}
	
	
}
