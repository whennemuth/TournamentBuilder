package com.warren.tournament.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.warren.tournament.enumerator.GameType;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tournament database table.
 * 
 */
@Entity
@Table(name="tournament")
@NamedQuery(name="Tournament.findAll", query="SELECT t FROM Tournament t")
public class Tournament implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp created;

	@Column(nullable=false, length=50)
	private String format;

	@Column(name="format_type", nullable=false, length=50)
	private String formatType;

	@Enumerated(EnumType.STRING)
	@Column(name="game_type", nullable=false, length=50)
	private GameType gameType;

	@Column(name="games_per_match", nullable=false)
	private Integer gamesPerMatch;

	@Column(name="matching_method", nullable=false, length=50)
	private String matchingMethod;

	private Timestamp updated;

	//bi-directional many-to-one association to Bracket
	@OneToMany(mappedBy="tournament")
	private List<Bracket> brackets;

	//bi-directional many-to-one association to Player
	@OneToMany(mappedBy="tournament")
	private List<Player> players;

	public Tournament() {
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

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFormatType() {
		return this.formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public GameType getGameType() {
		return this.gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public Integer getGamesPerMatch() {
		return this.gamesPerMatch;
	}

	public void setGamesPerMatch(Integer gamesPerMatch) {
		this.gamesPerMatch = gamesPerMatch;
	}

	public String getMatchingMethod() {
		return this.matchingMethod;
	}

	public void setMatchingMethod(String matchingMethod) {
		this.matchingMethod = matchingMethod;
	}

	public Timestamp getUpdated() {
		return this.updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public List<Bracket> getBrackets() {
		return this.brackets;
	}

	public void setBrackets(List<Bracket> brackets) {
		this.brackets = brackets;
	}

	public Bracket addBracket(Bracket bracket) {
		getBrackets().add(bracket);
		bracket.setTournament(this);

		return bracket;
	}

	public Bracket removeBracket(Bracket bracket) {
		getBrackets().remove(bracket);
		bracket.setTournament(null);

		return bracket;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Player addPlayer(Player player) {
		getPlayers().add(player);
		player.setTournament(this);

		return player;
	}

	public Player removePlayer(Player player) {
		getPlayers().remove(player);
		player.setTournament(null);

		return player;
	}

}