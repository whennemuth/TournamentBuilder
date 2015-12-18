package com.warren.tournament.service.builders;

import com.warren.tournament.api.MatchBuilder;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;
import com.warren.tournament.enumerator.GameType;

public class BasicMatchBuilder implements MatchBuilder {

	public Match buildMatch(Round round, GameType gameType) {
		
		Match match = new Match(round);
		
		// Create all sides and add them to the match
		for(int i=0; i<gameType.getSidesPerGame(); i++) {
			Side side = new Side(match);
			match.addSide(side);
		}
		
		// Set each side of the match as an opposing side to all the other sides in the match
		for(int i=0; i<match.getSides().size(); i++) {
			Side side = match.getSides().get(i);
			for(int x=0; x<match.getSides().size(); x++) {
				if(x != i) {
					side.addOpposingSide(match.getSides().get(x));
				}
			}
		}
		
		return match;
	}
}
