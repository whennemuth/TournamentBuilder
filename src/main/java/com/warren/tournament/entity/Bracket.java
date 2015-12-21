package com.warren.tournament.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Bracket {
	
	private Integer id;
	private List<Round> rounds = new ArrayList<Round>();

	public Bracket() { }

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
