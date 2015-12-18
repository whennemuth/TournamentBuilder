package com.warren.tournament.entity;

import java.util.ArrayList;
import java.util.List;

public class Match {

	private List<Side> sides;
	private List<Game> games;
	private Round round;
	
	public Match(Round round) {
		this.round = round;
	}
	public Round getRound() {
		return round;
	}
	public void setRound(Round round) {
		this.round = round;
	}
	public List<Side> getSides() {
		return sides;
	}
	public void setSides(List<Side> sides) {
		this.sides = sides;
	}
	public void addSide(Side side) {
		if(sides == null) {
			sides = new ArrayList<Side>();
		}
		sides.add(side);
		side.setMatch(this);
	}
	public List<Game> getGames() {
		return games;
	}
	public void setGames(List<Game> games) {
		this.games = games;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Match [sides=").append(sides).append(", games=")
				.append(games).append("]");
		return builder.toString();
	}
}
