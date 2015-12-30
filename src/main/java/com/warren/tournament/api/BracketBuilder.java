package com.warren.tournament.api;

import com.warren.tournament.entity1.Bracket;
import com.warren.tournament.entity1.Tournament;
import com.warren.tournament.enumerator.GameType;

public interface BracketBuilder {
	
	public Bracket buildNextBracket(
			Tournament tournament, 
			RoundBuilder roundBuilder, 
			MatchBuilder matchBuilder, 
			GameType gameType);
}
