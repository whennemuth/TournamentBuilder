package com.warren.tournament.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Round {

	private Bracket bracket;
	private List<Match> matches = new ArrayList<Match>();
	private Integer id;
	
	public Round(Bracket bracket) {
		this.bracket = bracket;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
