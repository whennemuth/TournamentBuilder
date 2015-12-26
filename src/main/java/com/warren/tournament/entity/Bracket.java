package com.warren.tournament.entity;

import java.io.IOException;
import java.sql.Timestamp;
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
	private Timestamp created;
	private Timestamp updated;

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
	public Timestamp getCreated() {
		if(created == null)
			created = new Timestamp(System.currentTimeMillis());
		return created;
	}
	public void setCreated(Timestamp created) {
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
		for(Round r : rounds) {
			i += r.getSideCount();
		}
		return i;
	}

	public boolean isComplete() {
		for(Round round : rounds) {
			if(!round.isComplete())
				return false;
		}
		return true;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tournament == null) ? 0 : tournament.hashCode());
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
		Bracket other = (Bracket) obj;
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
		if (tournament == null) {
			if (other.tournament != null)
				return false;
		} else if (!tournament.equals(other.tournament))
			return false;
		return true;
	}
	
	
}
