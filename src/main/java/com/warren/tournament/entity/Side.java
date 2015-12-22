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

public class Side {

	private Integer id;
	private List<Side> opposingSides;
	private List<Player> players;
	private Match match;
	private Float rank;
	
	public Side(Match match) {
		super();
		this.match = match;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JsonSerialize(using=SideFieldSerializer.class)
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
	public void addPlayer(Player player) {
		if(!players.contains(player)) {
			players.add(player);
		}
	}
	@JsonSerialize(using=MatchFieldSerializer.class)
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	/**
	 * @return The private rank field if it was explicitly set, else an average computed from the ranks of the players of the side. 
	 */
	public Float getRank() {
		if(this.rank != null) {
			return this.rank;
		}
		if(players == null) {
			return null;
		}
		Float rank = null;
		try {
			Integer playerCount = match.getRound().getBracket().getTournament().getGameType().getPlayersPerSide();
			if(playerCount.equals(players.size())){
				for(Player player : players) {
					rank = rank == null ? new Float(player.getRank()) : rank + new Float(player.getRank());
				}
			}
			rank = rank/playerCount;
		} 
		catch (Exception e) {
			e.printStackTrace();	// TODO: make this a log statement
			return null;
		}
		return rank;
	}
	public void setRank(Float rank) {
		this.rank = rank;
	}

	public static class MatchFieldSerializer extends JsonSerializer<Match> {
		@Override public void serialize(
				Match match, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Match>()).serialize(match, generator, provider);
		}
	}

	public static class SideFieldSerializer extends JsonSerializer<List<Side>> {
		@Override public void serialize(
				List<Side> sides, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<List<Side>>()).serialize(sides, generator, provider);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Side [opposingSides=").append(opposingSides.size())
				.append(", players=").append(players).append("]");
		return builder.toString();
	}
	
	
}
