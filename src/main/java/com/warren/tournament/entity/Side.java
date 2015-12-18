package com.warren.tournament.entity;

import java.util.ArrayList;
import java.util.List;

public class Side {

	private List<Side> opposingSides;
	private List<Player> players;
	private Match match;
	
	public Side(Match match) {
		super();
		this.match = match;
	}
	public List<Side> getOpposingSides() {
		return opposingSides;
	}
	public void setOpposingSides(List<Side> opposingSides) {
		this.opposingSides = opposingSides;
	}
	public void addOpposingSide(Side side) {
		if(opposingSides == null) {
			opposingSides = new ArrayList<Side>();
		}
		opposingSides.add(side);
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Side [opposingSides=").append(opposingSides.size())
				.append(", players=").append(players).append("]");
		return builder.toString();
	}
	
	
}
