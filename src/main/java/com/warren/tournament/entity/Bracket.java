package com.warren.tournament.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
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
		return this.created;
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

	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public List<Round> getRounds() {
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

}