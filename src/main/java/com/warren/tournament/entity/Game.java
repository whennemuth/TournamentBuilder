package com.warren.tournament.entity;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Side;
import com.warren.tournament.util.CustomJsonSerializer;

import java.sql.Timestamp;


/**
 * The persistent class for the game database table.
 * 
 */
@Entity
@Table(name="game")
@NamedQuery(name="Game.findAll", query="SELECT g FROM Game g")
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp created;

	@Column(name="losing_score")
	private Integer losingScore;

	@Column(nullable=false)
	private Integer order;

	private Timestamp updated;

	@Column(name="winning_score")
	private Integer winningScore;

	//bi-directional many-to-one association to Match
	@ManyToOne
	@JoinColumn(name="match_id", nullable=false)
	private Match match;

	//bi-directional many-to-one association to Side
	@ManyToOne
	@JoinColumn(name="losing_side_id")
	private Side losingSide;

	//bi-directional many-to-one association to Side
	@ManyToOne
	@JoinColumn(name="winning_side_id")
	private Side winningSide;

	public Game() { }
	
	public Game(Match match) {
		this.match = match;
	}
	
	@Transient
	public void addScore(Side side, Integer points) throws IllegalArgumentException {
		if(!match.getSides().contains(side)) {
			throw new IllegalArgumentException(
					"Attempting to add a side score to a game where the side is not in the match: \n" + 
					side + "\n match: " + match);
			// TODO: put log statement here
		}
		
		if(winningScore == null) {
			winningScore = points;
			winningSide = side;
		}
		else {
			losingScore = points;
			losingSide = side;
		}
		
		if(losingScore == null) {
			return;
		}

		if(losingScore > winningScore) {
			flipScores();
		}
	}

	private void flipScores() {
		Integer tempScore = winningScore;
		winningScore = losingScore;
		losingScore = tempScore;
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
	public Integer getLosingScore() {
		return this.losingScore;
	}
	public void setLosingScore(Integer losingScore) {
		this.losingScore = losingScore;
	}
	public Integer getOrder() {
		return this.order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Timestamp getUpdated() {
		return this.updated;
	}
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
	public Integer getWinningScore() {
		return this.winningScore;
	}
	public void setWinningScore(Integer winningScore) {
		this.winningScore = winningScore;
	}
	@JsonSerialize(using=MatchFieldSerializer.class)
	public Match getMatch() {
		return this.match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public Side getLosingSide() {
		return this.losingSide;
	}
	public void setLosingSide(Side losingSide) {
		this.losingSide = losingSide;
	}
	public Side getWinningSide() {
		return this.winningSide;
	}
	public void setWinningSide(Side winningSide) {
		this.winningSide = winningSide;
	}

	@Transient
	public boolean isComplete() {
		return winningScore != null && losingScore != null;
	}

	public static class MatchFieldSerializer extends JsonSerializer<Match> {
		@Override public void serialize(
				Match match, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Match>()).serialize(match, generator, provider);
		}
	}
}