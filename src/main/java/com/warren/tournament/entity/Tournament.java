package com.warren.tournament.entity;

import java.util.HashSet;
import java.util.Set;

import com.warren.tournament.enumerator.GameType;

public class Tournament {

	Set<Bracket> brackets = new HashSet<Bracket>();
	Set<Player> players = new HashSet<Player>();
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	public void removePlayer(Player player) {
		players.remove(player);
	}
	public void addBracket(Bracket bracket) {
		brackets.add(bracket);
	}
	public void removeBracket(Bracket bracket) {
		brackets.remove(bracket);
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
