package com.warren.tournament.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the round database table.
 * 
 */
@Entity
@Table(name="round")
@NamedQuery(name="Round.findAll", query="SELECT r FROM Round r")
public class Round implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp created;

	private Timestamp updated;

	//bi-directional many-to-one association to Match
	@OneToMany(mappedBy="round")
	private List<Match> matches;

	//bi-directional many-to-one association to Bracket
	@ManyToOne
	@JoinColumn(name="bracket_id", nullable=false)
	private Bracket bracket;

	public Round() {
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

	public List<Match> getMatches() {
		return this.matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public Match addMatch(Match match) {
		getMatches().add(match);
		match.setRound(this);

		return match;
	}

	public Match removeMatch(Match match) {
		getMatches().remove(match);
		match.setRound(null);

		return match;
	}

	public Bracket getBracket() {
		return this.bracket;
	}

	public void setBracket(Bracket bracket) {
		this.bracket = bracket;
	}

}