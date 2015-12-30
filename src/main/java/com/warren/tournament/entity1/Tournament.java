package com.warren.tournament.entity1;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;

public class Tournament {

	private Integer id;
	private List<Bracket> brackets = new ArrayList<Bracket>();
	private Set<Player> players = new HashSet<Player>();
	private GameType gameType;
	private MatchingMethod matchingMethod;
	private Enum<?> formatType;
	private String format;
	private Integer gamesPerMatch;
	private Timestamp created;
	private Timestamp updated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void addPlayer(Player player) {
		players.add(player);
	}
	public void removePlayer(Player player) {
		players.remove(player);
	}
	public void addBracket(Bracket bracket) {
		brackets.add(bracket);
		bracket.setTournament(this);
	}
	public void removeBracket(Bracket bracket) {
		brackets.remove(bracket);
		bracket.setTournament(null);
	}
	public List<Bracket> getBrackets() {
		return brackets;
	}
	public void setBrackets(List<Bracket> brackets) {
		this.brackets = brackets;
	}
	public Set<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Set<Player> players) {
		this.players = players;
		for(Player p : players) {
			p.setTournament(this);
		}
	}
	public Integer getGamesPerMatch() {
		return gamesPerMatch;
	}
	public void setGamesPerMatch(Integer gamesPerMatch) {
		this.gamesPerMatch = gamesPerMatch;
	}
	public GameType getGameType() {
		return gameType;
	}
	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}
	public MatchingMethod getMatchingMethod() {
		return matchingMethod;
	}
	public void setMatchingMethod(MatchingMethod matchingMethod) {
		this.matchingMethod = matchingMethod;
	}
	public Enum<?> getFormatType() {
		return formatType;
	}
	public void setFormatType(Enum<?> formatType) {
		this.formatType = formatType;
		this.format = formatType.name();
	}
	public String getFormat() {
		return this.formatType == null ? format : this.formatType.name();
	}
	public void setFormat(String format) {
		this.format = format;
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
		return updated;
	}
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
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
