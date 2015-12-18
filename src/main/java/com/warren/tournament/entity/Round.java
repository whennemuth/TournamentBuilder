package com.warren.tournament.entity;

import java.util.ArrayList;
import java.util.List;

public class Round {

	private Bracket bracket;
	private List<Match> matches = new ArrayList<Match>();
	
	public Round(Bracket bracket) {
		this.bracket = bracket;
	}
	
	public Bracket getBracket() {
		return bracket;
	}
	public void setBracket(Bracket bracket) {
		this.bracket = bracket;
	}
	public List<Match> getMatches() {
		return matches;
	}
	public void setMatches(List<Match> matches) {
		this.matches.addAll(matches);
	}
	public void addMatch(Match match) {
		this.matches.add(match);
	}
	public int getSideCount() {
		int i = 0;
		for(Match m : matches) {
			i += m.getSides().size();
		}
		return i;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Round [matches=").append(matches).append("]");
		return builder.toString();
	}
	
}
