package com.warren.tournament.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the match database table.
 * 
 */
@Entity
@Table(name="match")
@NamedQuery(name="Match.findAll", query="SELECT m FROM Match m")
public class Match implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp created;

	private Timestamp updated;

	//bi-directional many-to-one association to Game
	@OneToMany(mappedBy="match")
	private List<Game> games;

	//bi-directional many-to-one association to Round
	@ManyToOne
	@JoinColumn(name="round_id", nullable=false)
	private Round round;

	//bi-directional many-to-one association to Side
	@OneToMany(mappedBy="match")
	private List<Side> sides;

	public Match() {
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

	public List<Game> getGames() {
		return this.games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Game addGame(Game game) {
		getGames().add(game);
		game.setMatch(this);

		return game;
	}

	public Game removeGame(Game game) {
		getGames().remove(game);
		game.setMatch(null);

		return game;
	}

	public Round getRound() {
		return this.round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public List<Side> getSides() {
		return this.sides;
	}

	public void setSides(List<Side> sides) {
		this.sides = sides;
	}

	public Side addSide(Side side) {
		getSides().add(side);
		side.setMatch(this);

		return side;
	}

	public Side removeSide(Side side) {
		getSides().remove(side);
		side.setMatch(null);

		return side;
	}

}