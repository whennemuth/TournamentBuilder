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
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;
import com.warren.tournament.util.CustomJsonSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;
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
	public Match(Round round) {
		this.round = round;
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
		if(games == null)
			games = new ArrayList<Game>();
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

	@JsonSerialize(using=RoundFieldSerializer.class)
	public Round getRound() {
		return this.round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public List<Side> getSides() {
		if(sides == null)
			sides = new ArrayList<Side>();
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
	/**
	 * Has a match been assigned all its players - has each side got its full complement of players (ie: 2 for doubles).
	 * The assumption is that no match would ever have more than zero, but less than the full complement of players.
	 * @return
	 */
	@Transient
	public boolean hasPlayers() {
		if(sides == null || sides.isEmpty())
			return false;
		for(Side side : sides) {
			int playersPerSide = round.getBracket().getTournament().getGameType().getPlayersPerSide();
			if(side.getPlayers() == null || side.getPlayers().size() != playersPerSide) {
				return false;
			}
		}
		return true;
	}

	public static class RoundFieldSerializer extends JsonSerializer<Round> {
		@Override public void serialize(
				Round round, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Round>()).serialize(round, generator, provider);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Match [sides=").append(sides).append(", games=")
				.append(games).append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((round == null) ? 0 : round.hashCode());
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
		Match other = (Match) obj;
		if(id != null && other.id != null) 
			return id.equals(other.id);
		
		// From this point assume equals if both matches have the same sides
		if (sides == null || other.sides == null) 
			return false;
		if (sides.isEmpty() || other.sides.isEmpty())
			return false;
		if (sides.size() != other.sides.size())
			return false;

		List<Side> thisSides = new ArrayList<Side>(sides);
		List<Side> otherSides = new ArrayList<Side>(other.sides);
		thisSides.removeAll(otherSides);
		return thisSides.isEmpty();
	}
	

}