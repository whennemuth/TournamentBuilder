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
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.SidePlayer;
import com.warren.tournament.util.CustomJsonSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the side database table.
 * 
 */
@Entity
@Table(name="side")
@NamedQuery(name="Side.findAll", query="SELECT s FROM Side s")
public class Side implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp created;

	private Float rank;
	
	private Integer sortOrder;

	private Timestamp updated;

	//bi-directional many-to-one association to Game
	@OneToMany(mappedBy="losingSide")
	private List<Game> losingGames;

	//bi-directional many-to-one association to Game
	@OneToMany(mappedBy="winningSide")
	private List<Game> winningGames;

	//bi-directional many-to-one association to Match
	@ManyToOne
	@JoinColumn(name="match_id", nullable=false)
	private Match match;

	//bi-directional many-to-one association to SidePlayer
	@OneToMany(mappedBy="side")
	private Set<SidePlayer> sidePlayers;

	//bi-directional many-to-one association to Side
	@ManyToOne
	@JoinColumn(name="id", nullable=false, insertable=false, updatable=false)
	private Side thisSide;

	//bi-directional many-to-one association to Side
	@OneToMany(mappedBy="side")
	private Set<Side> opposingSides;

	public Side() { }
	
	public Side(Match match) {
		super();
		this.match = match;
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

	/**
	 * @return The private rank field if it was explicitly set, else an average computed from the ranks of the players of the side. 
	 */
	public Float getRank() {
		// Exit out early if rank is already set of there are no players on which to base a rank.
		if(this.rank != null) {
			return this.rank;
		}
		Set<Player> players = getPlayers();
		if(players == null || players.isEmpty()) {
			return null;
		}
		
		// Compute the rank from the ranks of the players in the side.
		Float rank = null;
		try {
			Integer playerCount = match.getRound().getBracket().getTournament().getGameType().getPlayersPerSide();
			if(playerCount.equals(players.size())){
				for(Player player : players) {
					if(player.getRank() == null) {
						return null;
					}
					else if(rank == null) {
						rank = new Float(player.getRank());
					}
					else {
						rank = rank + new Float(player.getRank());
					}
				}
			}
			rank = rank/playerCount;
		} 
		catch (Exception e) {
			e.printStackTrace();	// TODO: make this a log statement
			return null;
		}
		return rank;
	}

	public void setRank(Float rank) {
		this.rank = rank;
	}

	@Transient
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * Used so that sorted collections of Side instances can use a comparator that references this value to sort against.
	 * @param sortOrder
	 */
	@Transient
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Timestamp getUpdated() {
		return this.updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public List<Game> getLosingGames() {
		if(losingGames == null)
			losingGames = new ArrayList<Game>();
		return this.losingGames;
	}

	public void setLosingGames(List<Game> losingGames) {
		this.losingGames = losingGames;
	}

	public Game addLosingGame(Game losingGames) {
		getLosingGames().add(losingGames);
		losingGames.setLosingSide(this);

		return losingGames;
	}

	public Game removeLosingGame(Game losingGames) {
		getLosingGames().remove(losingGames);
		losingGames.setLosingSide(null);

		return losingGames;
	}

	public List<Game> getWinningGames() {
		if(this.winningGames == null)
			this.winningGames = new ArrayList<Game>();
		return this.winningGames;
	}

	public void setWinningGames(List<Game> winningGames) {
		this.winningGames = winningGames;
	}

	public Game addWinningGame(Game winningGames) {
		getWinningGames().add(winningGames);
		winningGames.setWinningSide(this);

		return winningGames;
	}

	public Game removeWinningGame(Game winningGames) {
		getWinningGames().remove(winningGames);
		winningGames.setWinningSide(null);

		return winningGames;
	}

	@JsonSerialize(using=MatchFieldSerializer.class)
	public Match getMatch() {
		return this.match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Set<SidePlayer> getSidePlayers() {
		if(this.sidePlayers == null)
			this.sidePlayers = new HashSet<SidePlayer>();
		return this.sidePlayers;
	}

	public void setSidePlayers(Set<SidePlayer> sidePlayers) {
		this.sidePlayers = sidePlayers;
	}

	public SidePlayer addSidePlayer(SidePlayer sidePlayer) {
		getSidePlayers().add(sidePlayer);
		sidePlayer.setSide(this);

		return sidePlayer;
	}

	@JsonSerialize(using=SideFieldSerializer.class)
	public Side getThisSide() {
		return this.thisSide;
	}

	public void setThisSide(Side side) {
		this.thisSide = side;
	}

	@JsonSerialize(using=SideFieldsSerializer.class)
	public Set<Side> getOpposingSides() {
		if(this.opposingSides == null)
			this.opposingSides = new HashSet<Side>();
		return this.opposingSides;
	}

	public void setOpposingSides(Set<Side> sides) {
		this.opposingSides = sides;
	}

	public Side addOpposingSide(Side side) {
		getOpposingSides().add(side);
		side.setThisSide(this);
		return side;
	}

	public Side removeOpposingSide(Side side) {
		getOpposingSides().remove(side);
		side.setThisSide(null);
		return side;
	}

	@Transient
	public Set<Player> getPlayers() {
		Set<Player> players = new HashSet<Player>();
		for(SidePlayer sp : getSidePlayers()) {
			if(sp.getPlayer() != null)
				players.add(sp.getPlayer());
		}
		return players;
	}
	@Transient
	public void setPlayers(Set<Player> players) {
		if(players == null || players.isEmpty())
			return;

		for(Player player : players) {
			addPlayer(player);
		}
	}
	@Transient
	public void addPlayer(Player player) {
		if(player == null)
			return;
		
		SidePlayer sidePlayer = new SidePlayer();
		sidePlayer.setSide(this);
		sidePlayer.setPlayer(player);
		if(!getSidePlayers().contains(sidePlayer)) {
			getSidePlayers().add(sidePlayer);
		}
	}

	public SidePlayer removeSidePlayer(SidePlayer sidePlayer) {
		getSidePlayers().remove(sidePlayer);
		sidePlayer.setSide(null);
		return sidePlayer;
	}

	public static class MatchFieldSerializer extends JsonSerializer<Match> {
		@Override public void serialize(
				Match match, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Match>()).serialize(match, generator, provider);
		}
	}

	public static class SideFieldsSerializer extends JsonSerializer<Set<Side>> {
		@Override public void serialize(
				Set<Side> sides, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Set<Side>>()).serialize(sides, generator, provider);
		}
	}

	public static class SideFieldSerializer extends JsonSerializer<Side> {
		@Override public void serialize(
				Side side, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Side>()).serialize(side, generator, provider);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Side [opposingSides=").append(opposingSides.size())
				.append(", players=").append(getPlayers()).append("]");
		return builder.toString();
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((created == null) ? 0 : created.hashCode());
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((match == null) ? 0 : match.hashCode());
//		result = prime * result + ((getPlayers() == null) ? 0 : getPlayers().hashCode());
//		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
//		return result;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Side other = (Side) obj;
		
		if(id != null && other.id != null) 
			return id.equals(other.id);
		
		if (match != null && match.getId() != null && other.match != null && other.match.getId() != null) {
			return match.getId().equals(other.match.getId());
		}		
		
		// From this point assume equals if both sides have the same players
		Set<Player> players = getPlayers();
		if (players == null || other.getPlayers() == null) 
			return false;
		if (players.isEmpty() || other.getPlayers().isEmpty())
			return false;
		if (players.size() != other.getPlayers().size())
			return false;

		List<Player> thisPlayers = new ArrayList<Player>(players);
		List<Player> otherPlayers = new ArrayList<Player>(other.getPlayers());
		thisPlayers.removeAll(otherPlayers);
		return thisPlayers.isEmpty();
	}

}