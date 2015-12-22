package com.warren.tournament.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warren.tournament.util.CustomJsonSerializer;

public class Bracket {
	
	private Integer id;
	private List<Round> rounds = new ArrayList<Round>();
	private Tournament tournament;

	public Bracket() { }

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonSerialize(using=TournamentFieldSerializer.class)
	public Tournament getTournament() {
		return tournament;
	}
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
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

	public static class TournamentFieldSerializer extends JsonSerializer<Tournament> {
		@Override public void serialize(
				Tournament tournament, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Tournament>()).serialize(tournament, generator, provider);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bracket [rounds=").append(rounds).append("]");
		return builder.toString();
	}
	
}
