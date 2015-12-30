package com.warren.tournament.entity;

import java.io.Serializable;
import javax.persistence.*;
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

}