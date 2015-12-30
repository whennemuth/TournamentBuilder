package com.warren.tournament.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the side_player database table.
 * 
 */
@Entity
@Table(name="side_player")
@NamedQuery(name="SidePlayer.findAll", query="SELECT s FROM SidePlayer s")
public class SidePlayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp created;

	private Timestamp updated;

	//bi-directional many-to-one association to Player
	@ManyToOne
	@JoinColumn(name="player_id", nullable=false)
	private Player player;

	//bi-directional many-to-one association to Side
	@ManyToOne
	@JoinColumn(name="side_id", nullable=false)
	private Side side;

	public SidePlayer() {
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

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Side getSide() {
		return this.side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

}