package com.warren.tournament.entity;

public class Score {

	private Side side;
	private Integer points;
	public Score(Side side) {
		this(side, null);
	}
	public Score(Side side, Integer points) {
		this.side = side;
		this.points = points;
	}
	public Side getSide() {
		return side;
	}
	public void setSide(Side side) {
		this.side = side;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
}
