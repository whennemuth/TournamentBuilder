package com.warren.tournament.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warren.tournament.util.CustomJsonSerializer;

public class Side {

	private Integer id;
	private Set<Side> opposingSides = new HashSet<Side>();
	private Set<Player> players = new HashSet<Player>();
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
	public Set<Side> getOpposingSides() {
		return opposingSides;
	}
	public void setOpposingSides(Set<Side> opposingSides) {
		this.opposingSides = opposingSides;
	}
	public void addOpposingSide(Side side) {
		if(opposingSides == null) {
			opposingSides = new HashSet<Side>();
		}
		opposingSides.add(side);
	}
	public Set<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Set<Player> players) {
		this.players = new HashSet<Player>(players);
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
		if(players == null || players.isEmpty()) {
			return null;
		}
		Float rank = null;
		try {
			Integer playerCount = match.getRound().getBracket().getTournament().getGameType().getPlayersPerSide();
			if(playerCount.equals(players.size())){
				for(Player player : players) {
					if(player.getRank() == null) {
						return null;
					}
					else if(rank == null) {
						rank = new Float(player.getRank());
					}
					else {
						rank = rank + new Float(player.getRank());
					}
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

	public static class SideFieldSerializer extends JsonSerializer<Set<Side>> {
		@Override public void serialize(
				Set<Side> sides, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Set<Side>>()).serialize(sides, generator, provider);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Side [opposingSides=").append(opposingSides.size())
				.append(", players=").append(players).append("]");
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
		Side other = (Side) obj;
		
		if(id != null && other.id != null) 
			return id.equals(other.id);
		
		if (match != null && match.getId() != null && other.match != null && other.match.getId() != null) {
			return match.getId().equals(other.match.getId());
		}		
		
		// From this point assume equals if both sides have the same players
		if (players == null || other.players == null) 
			return false;
		if (players.isEmpty() || other.players.isEmpty())
			return false;
		if (players.size() != other.players.size())
			return false;

		List<Player> thisPlayers = new ArrayList<Player>(players);
		List<Player> otherPlayers = new ArrayList<Player>(other.players);
		thisPlayers.removeAll(otherPlayers);
		return thisPlayers.isEmpty();
	}
	
}
