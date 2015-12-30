package com.warren.tournament.api;

import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.GameType;

public interface BracketBuilder {
	
	public Bracket buildNextBracket(
			Tournament tournament, 
			RoundBuilder roundBuilder, 
			MatchBuilder matchBuilder, 
			GameType gameType);
}
