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

public class Match {

	private Integer id;
	private List<Side> sides;
	private List<Game> games;
	private Round round;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Match(Round round) {
		this.round = round;
	}
	@JsonSerialize(using=RoundFieldSerializer.class)
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

	public static class RoundFieldSerializer extends JsonSerializer<Round> {
		@Override public void serialize(
				Round round, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Round>()).serialize(round, generator, provider);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Match [sides=").append(sides).append(", games=")
				.append(games).append("]");
		return builder.toString();
	}
}
