package com.warren.tournament.api;

import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Round;
import com.warren.tournament.enumerator.GameType;

public interface RoundBuilder {
	public Round buildNextRound(Bracket bracket, MatchBuilder matchBuilder, GameType gameType, int playerCount);
}
