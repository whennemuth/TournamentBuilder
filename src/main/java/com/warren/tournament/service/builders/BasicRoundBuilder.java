package com.warren.tournament.service.builders;

import com.warren.tournament.api.MatchBuilder;
import com.warren.tournament.api.RoundBuilder;
import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Round;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.util.Utils;

public class BasicRoundBuilder implements RoundBuilder {

	public Round buildNextRound(Bracket bracket, MatchBuilder matchBuilder, GameType gameType, int players) {
		if(gameType.getSidesPerGame(players) == 1) {
			// Not enough players to have any matches in the round, meaning the finals ended, so return null
			return null;
		}
		Round round = new Round(bracket);
		int matches = getMatchesInFirstRound(players, gameType);
		for(int i=0; i<matches; i++) {
			Match match = matchBuilder.buildMatch(round, gameType);
			round.addMatch(match);
		}
		return round;
	}
	
	public int getMatchesInFirstRound(int players, GameType gameType) {
		int sides = gameType.getSidesPerGame(players);
		int log2 = Utils.closestLog2(sides);
		int closest = (int) Math.pow(2, log2);
		return sides == closest ? sides/2 : (sides - closest);
	}
	
	/**
	 * For single elimination, the number of matches in a bracket is always one less than the number of sides competing.
	 * This assumes that a match is 2 sides compete against each other.
	 * 
	 * @param players
	 * @param gameType
	 * @return
	 */
	public int getMatchesInBracket(int players, GameType gameType) {
		return gameType.getSidesPerGame(players) - 1;		
	}
	
	/**
	 * The algorithm for determining how many matches are in a bracket is simply to subtract one from 
	 * the number of sides participating in the bracket. This method comes to that number through
	 * brute force by building a running sum of round match counts from the earlier rounds to the later rounds. 
	 * This approach may provide points in which custom overrides or exceptions might be
	 * incorporated to accommodate rules that bend the single elimination aspect of bracket creation.
	 * As of now, there is not really any good reason not to go with the simple algorithm.
	 * 
	 * @param players
	 * @param gameType
	 * @return
	 */
	public int countMatchesInBracket(int players, GameType gameType) {
		int total = 0;
		int matches = 0;
		int sides = gameType.getSidesPerGame(players);
		int playersLeft = players;
		while(true) {
			matches = countMatchesInFirstRound(playersLeft, gameType);
			if((total + matches) >= sides) {
				break;
			}
			
			// Half the players participating in the matches of the round will be knocked out and the remainder participating in the next round.
			playersLeft -= (matches * gameType.getPlayersPerSide());
			total += matches;
		}
		return total;
	}
	
	public int countMatchesInFirstRound(int players, GameType gameType) {
		
		int sides = gameType.getSidesPerGame(players);
		
		int i = 0;
		int matches = 0;
		for(i=2; i<=sides; i*=2) {
			if(i > sides) {
				break;
			}
			matches = i/2;
		}
		
		int leftover = sides - (matches * 2);
		return leftover == 0 ? matches : leftover;
	}
	
	
}
