package com.warren.tournament.entity1;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.warren.tournament.util.CustomJsonSerializer;

public class Game {
	private Integer id;
	private Integer order;
	private Match match;
	private Integer winningScore;
	private Integer losingScore;
	private Side winningSide;
	private Side losingSide;
	
	public Game(Match match) {
		this.match = match;
	}
	
	public void addScore(Side side, Integer points) throws IllegalArgumentException {
		if(!match.getSides().contains(side)) {
			throw new IllegalArgumentException(
					"Attempting to add a side score to a game where the side is not in the match: \n" + 
					side + "\n match: " + match);
			// TODO: put log statement here
		}
		
		if(winningScore == null) {
			winningScore = points;
			winningSide = side;
		}
		else {
			losingScore = points;
			losingSide = side;
		}
		
		if(losingScore == null) {
			return;
		}

		if(losingScore > winningScore) {
			flipScores();
		}
	}

	private void flipScores() {
		Integer tempScore = winningScore;
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
	@JsonSerialize(using=MatchFieldSerializer.class)
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public Integer getWinningScore() {
		return winningScore;
	}
	public Integer getLosingScore() {
		return losingScore;
	}
	public Side getWinningSide() {
		return winningSide;
	}
	public Side getLosingSide() {
		return losingSide;
	}

	public boolean isComplete() {
		return winningScore != null && losingScore != null;
	}

	public static class MatchFieldSerializer extends JsonSerializer<Match> {
		@Override public void serialize(
				Match match, 
				JsonGenerator generator, 
				SerializerProvider provider) throws IOException, JsonProcessingException {
			
			(new CustomJsonSerializer<Match>()).serialize(match, generator, provider);
		}
	}
}
