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
	/**
	 * For now we are considering that a match has been assigned all its players if any side has at least one player.
	 * The assumption is that no match or side would ever be in a state of having been partially assigned players.
	 * @return
	 */
	public boolean hasPlayers() {
		if(sides == null || sides.isEmpty())
			return false;
		for(Side side : sides) {
			if(side.getPlayers() == null || side.getPlayers().isEmpty()) {
				return false;
			}
		}
		return true;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if(id != null && other.id != null) 
			return id.equals(other.id);
		
		// From this point assume equals if both matches have the same sides
		if (sides == null || other.sides == null) 
			return false;
		if (sides.isEmpty() || other.sides.isEmpty())
			return false;
		if (sides.size() != other.sides.size())
			return false;

		List<Side> thisSides = new ArrayList<Side>(sides);
		List<Side> otherSides = new ArrayList<Side>(other.sides);
		thisSides.removeAll(otherSides);
		return thisSides.isEmpty();
	}
	
}
