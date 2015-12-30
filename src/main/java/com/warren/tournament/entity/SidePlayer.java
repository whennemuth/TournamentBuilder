package com.warren.tournament.entity;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warren.tournament.entity.SidePlayer;
import com.warren.tournament.entity.Side.SideFieldSerializer;
import com.warren.tournament.util.CustomJsonSerializer;

import java.sql.Timestamp;
import java.util.Set;


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

	@JsonSerialize(using=SideFieldSerializer.class)
	public Side getSide() {
		return this.side;
	}

	public void setSide(Side side) {
		this.side = side;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((side == null) ? 0 : side.hashCode());
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
		SidePlayer other = (SidePlayer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (side == null) {
			if (other.side != null)
				return false;
		} else if (!side.equals(other.side))
			return false;
		return true;
	}

}