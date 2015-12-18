package com.warren.tournament.entity;

import java.util.ArrayList;
import java.util.List;

public class Bracket {
	
	private List<Round> rounds = new ArrayList<Round>();

	public Bracket() { }

	public List<Round> getRounds() {
		return rounds;
	}
	public void addRound(Round round) {
		rounds.add(round);
	}
	public int getSideCount() {
		int i = 0;
		for(Round r : rounds) {
			i += r.getSideCount();
		}
		return i;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bracket [rounds=").append(rounds).append("]");
		return builder.toString();
	}
	
}
