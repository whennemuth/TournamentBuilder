package com.warren.tournament.service.builders;

import com.warren.tournament.api.BracketBuilder;
import com.warren.tournament.api.MatchBuilder;
import com.warren.tournament.api.RoundBuilder;
import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.GameType;

public abstract class AbstractBracketBuilder implements BracketBuilder {

	// TODO: modify the logic to accommodate double elimination and do the same in BasicRoundBuilder.
	public Bracket buildNextBracket(
			Tournament tournament,
			RoundBuilder roundBuilder, 
			MatchBuilder matchBuilder,
			GameType gameType) {
		
		Bracket bracket = new Bracket();
		int remainingPlayers = tournament.getPlayers().size();
		while(true) {
			Round round = roundBuilder.buildNextRound(
					bracket, 
					matchBuilder, 
					gameType, 
					remainingPlayers);
			
			if(round == null) {
				break;
			}
			
			bracket.addRound(round);
			remainingPlayers -= (gameType.getPlayersPerSide() * round.getSideCount())/2;
		}
		
		return bracket;
	}
	
	/**
	 * For single elimination this function should output one less match than the amount of players participating.
	 * 
	 * TODO: modify the logic to accommodate double elimination and do the same in BasicRoundBuilder.
	 * 
	 * @param players
	 * @param gameType
	 * @return
	 */
	public int countMatchesInBracket(int players, GameType gameType) {
		
		int sides = players/gameType.getPlayersPerSide();
		
		int i = 0;
		int matches = 0;
		for(i=2; i<=sides; i*=2) {
			if(i > sides) {
				break;
			}
			matches += i/2;
		}
		
		return matches + (sides - i/2);
	}
	
}
