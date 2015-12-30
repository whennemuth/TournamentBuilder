package com.warren.tournament.entity1;

import java.io.IOException;
import java.sql.Timestamp;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warren.tournament.util.CustomJsonSerializer;

/**
 * A Player is a member who participating in a specified tournament (not just a member).
 * 
 * @author Warren
 *
 */
public class Player {

	private Integer id;
	private Tournament tournament;
	private Member member;
	private Integer rank;
	private Timestamp created;
	private Timestamp updated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@JsonSerialize(using=TournamentFieldSerializer.class)
	public Tournament getTournament() {
		return tournament;
	}
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	public Integer getRank() {
		return rank == null ? member.getRank() : rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Timestamp getCreated() {
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
	
	public static class TournamentFieldSerializer extends JsonSerializer<Tournament> {
		@Override public void serialize(
				Tournament tournament, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Tournament>()).serialize(tournament, generator, provider);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + 
				+ ((tournament == null || tournament.getId() == null) ? 0 : tournament.getId().hashCode());
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
		Player other = (Player) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tournament == null) {
			if (other.tournament != null)
				return false;
		} else if (!tournament.equals(other.tournament))
			return false;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (rank == null) {
			if (other.rank != null)
				return false;
		} else if (!rank.equals(other.rank))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player [id=").append(id).append(", tournament=")
				.append(tournament).append(", member=").append(member)
				.append(", rank=").append(rank).append(", created=")
				.append(created).append(", updated=").append(updated)
				.append("]");
		return builder.toString();
	}

}
