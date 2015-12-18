package com.warren.tournament.entity;


public class Game {
	private Integer id;
	private Integer order;
	private Match match;
	private Score winningScore;
	private Score losingScore;
	
	public Game(Match match) {
		this.match = match;
	}
	
	public void addScore(Side side, Integer points) throws IllegalArgumentException {
		if(!match.getSides().contains(side)) {
			throw new IllegalArgumentException(
					"Attempting to add a side score to a game where the side is not in the match: \n" + 
					side + "\n match: " + match);
		}
		
		if(winningScore == null) {
			winningScore = new Score(side, points);
		}
		else {
			losingScore = new Score(side, points);
		}
		
		if(losingScore == null) {
			return;
		}

		if(losingScore.getPoints() > winningScore.getPoints()) {
			flipScores();
		}
	}

	private void flipScores() {
		Score tempScore = winningScore;
		winningScore = losingScore;
		losingScore = tempScore;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public Score getWinningScore() {
		return winningScore;
	}
	public Score getLosingScore() {
		return losingScore;
	}
	
	
}
