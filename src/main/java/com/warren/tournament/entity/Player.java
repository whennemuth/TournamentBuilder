package com.warren.tournament.entity;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.util.CustomJsonSerializer;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the player database table.
 * 
 */
@Entity
@Table(name="player")
@NamedQuery(name="Player.findAll", query="SELECT p FROM Player p")
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp created;

	private Integer rank;

	private Timestamp updated;

	//bi-directional many-to-one association to Member
	@ManyToOne
	@JoinColumn(name="member_id", nullable=false)
	private Member member;

	//bi-directional many-to-one association to Tournament
	@ManyToOne
	@JoinColumn(name="tournament_id", nullable=false)
	private Tournament tournament;

	//bi-directional many-to-one association to SidePlayer
	@OneToMany(mappedBy="player")
	private List<SidePlayer> sidePlayers;

	public Player() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Integer getRank() {
		if(this.rank == null && this.member != null)
			return member.getRank();
			
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Timestamp getUpdated() {
		return this.updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@JsonSerialize(using=TournamentFieldSerializer.class)
	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public List<SidePlayer> getSidePlayers() {
		return this.sidePlayers;
	}

	public void setSidePlayers(List<SidePlayer> sidePlayers) {
		this.sidePlayers = sidePlayers;
	}

	public SidePlayer addSidePlayer(SidePlayer sidePlayer) {
		getSidePlayers().add(sidePlayer);
		sidePlayer.setPlayer(this);

		return sidePlayer;
	}

	public SidePlayer removeSidePlayer(SidePlayer sidePlayer) {
		getSidePlayers().remove(sidePlayer);
		sidePlayer.setPlayer(null);

		return sidePlayer;
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