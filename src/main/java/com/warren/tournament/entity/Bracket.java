package com.warren.tournament.entity;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warren.tournament.util.CustomJsonSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the bracket database table.
 * 
 */
@Entity
@Table(name="bracket")
@NamedQuery(name="Bracket.findAll", query="SELECT b FROM Bracket b")
public class Bracket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp created;

	private Timestamp updated;

	//bi-directional many-to-one association to Tournament
	@ManyToOne
	@JoinColumn(name="tournament_id", nullable=false)
	private Tournament tournament;

	//bi-directional many-to-one association to Round
	@OneToMany(mappedBy="bracket")
	private List<Round> rounds;

	public Bracket() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return this.updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	@JsonSerialize(using=TournamentFieldSerializer.class)
	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public List<Round> getRounds() {
		if(this.rounds == null)
			this.rounds = new ArrayList<Round>();
		return this.rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	public Round addRound(Round round) {
		getRounds().add(round);
		round.setBracket(this);

		return round;
	}

	public Round removeRound(Round round) {
		getRounds().remove(round);
		round.setBracket(null);

		return round;
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