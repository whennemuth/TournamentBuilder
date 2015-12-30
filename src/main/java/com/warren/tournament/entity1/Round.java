package com.warren.tournament.entity1;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warren.tournament.util.CustomJsonSerializer;

public class Round {

	private Bracket bracket;
	private List<Match> matches = new ArrayList<Match>();
	private Integer id;
	private Timestamp created;
	private Timestamp updated;
	
	public Round(Bracket bracket) {
		this.bracket = bracket;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonSerialize(using=BracketFieldSerializer.class)
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
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		if(created == null)
			created = new Timestamp(System.currentTimeMillis());
		this.created = created;
	}
	public Timestamp getUpdated() {
		return updated;
	}
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
	public int getSideCount() {
		int i = 0;
		for(Match m : matches) {
			i += m.getSides().size();
		}
		return i;
	}

	/**
	 * @return Every side for every match in the round that has been assigned all its players.
	 */
	@JsonIgnore
	public Set<Side> getPopulatedSides() {
		return getSides(true);
	}

	/**
	 * @return Every side for every match in the round that has NOT been assigned all its players.
	 */
	@JsonIgnore
	public Set<Side> getEmptySides() {
		return getSides(false);
	}
	
	private Set<Side> getSides(boolean populated) {
		Set<Side> sides = new HashSet<Side>();
		for(Match match : matches) {
			for(Side side : match.getSides()) {
				int expectedPlayers = bracket.getTournament().getGameType().getPlayersPerSide();
				if(side.getPlayers().size() == expectedPlayers && populated) {
					sides.add(side); // Adding side that is populated with players
				}
				else if(side.getPlayers().size() < expectedPlayers && !populated) {
					sides.add(side); // Adding side that is not populated (fully) with players
				}
			}
		}
		return sides;
	}

	/**
	 * @return Has every game for each match in the round been played.
	 */
	public boolean isComplete() {
		for(Match match : matches) {
			int expectedGames = bracket.getTournament().getGamesPerMatch();
			if(match.getGames() == null || match.getGames().isEmpty())
				return false;
			if(match.getGames().size() < expectedGames) 
				return false;	
			for(Game game : match.getGames()) {
				if(!game.isComplete()) {
					return false;
				}
			}
		}
		return true;
	}

	public static class BracketFieldSerializer extends JsonSerializer<Bracket> {
		@Override public void serialize(
				Bracket bracket, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Bracket>()).serialize(bracket, generator, provider);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Round [matches=").append(matches).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bracket == null) ? 0 : bracket.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Round other = (Round) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (bracket == null) {
			if (other.bracket != null)
				return false;
		} else if (!bracket.equals(other.bracket))
			return false;
		return true;
	}
	
}
