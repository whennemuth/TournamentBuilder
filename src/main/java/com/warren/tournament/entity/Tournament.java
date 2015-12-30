package com.warren.tournament.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.warren.tournament.entity.Bracket;
import com.warren.tournament.enumerator.FormatType;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

	@Enumerated(EnumType.STRING)
	@Column(name="format_type", nullable=false, length=50)
	private FormatType formatType;

	@Enumerated(EnumType.STRING)
	@Column(name="game_type", nullable=false, length=50)
	private GameType gameType;

	@Column(name="games_per_match", nullable=false)
	private Integer gamesPerMatch;

	@Enumerated(EnumType.STRING)
	@Column(name="matching_method", nullable=false, length=50)
	private MatchingMethod matchingMethod;

	private Timestamp updated;

	//bi-directional many-to-one association to Bracket
	@OneToMany(mappedBy="tournament")
	private List<Bracket> brackets;

	//bi-directional many-to-one association to Player
	@OneToMany(mappedBy="tournament")
	private Set<Player> players;

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

	public FormatType getFormatType() {
		return this.formatType;
	}

	public void setFormatType(FormatType formatType) {
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

	public MatchingMethod getMatchingMethod() {
		return this.matchingMethod;
	}

	public void setMatchingMethod(MatchingMethod matchingMethod) {
		this.matchingMethod = matchingMethod;
	}

	public Timestamp getUpdated() {
		return this.updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public List<Bracket> getBrackets() {
		if(this.brackets == null)
			this.brackets = new ArrayList<Bracket>();
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

	public Set<Player> getPlayers() {
		if(players == null)
			players = new HashSet<Player>();
		return this.players;
	}

	public void setPlayers(Set<Player> players) {
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
	public int getSideCount() {
		int i = 0;
		for(Bracket b : brackets) {
			i += b.getSideCount();
		}
		return i;
	}
	public int getUnBuiltSideCount(GameType gameType) {
		return gameType.getSidesPerGame( players.size()) - getSideCount();
	}
	public boolean isComplete() {
		for(Bracket bracket: brackets) {
			if(!bracket.isComplete())
				return false;
		}
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tournament [brackets=").append(brackets)
				.append(", players=").append(players).append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((formatType == null) ? 0 : formatType.hashCode());
		result = prime * result + ((gameType == null) ? 0 : gameType.hashCode());
		result = prime * result + ((gamesPerMatch == null) ? 0 : gamesPerMatch.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((matchingMethod == null) ? 0 : matchingMethod.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
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
		Tournament other = (Tournament) obj;
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
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (formatType == null) {
			if (other.formatType != null)
				return false;
		} else if (!formatType.equals(other.formatType))
			return false;
		if (gameType != other.gameType)
			return false;
		if (gamesPerMatch == null) {
			if (other.gamesPerMatch != null)
				return false;
		} else if (!gamesPerMatch.equals(other.gamesPerMatch))
			return false;
		if (matchingMethod != other.matchingMethod)
			return false;
		return true;
	}

}