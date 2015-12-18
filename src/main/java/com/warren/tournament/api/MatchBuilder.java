package com.warren.tournament.api;

import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Round;
import com.warren.tournament.enumerator.GameType;

public interface MatchBuilder {

	public Match buildMatch(Round round, GameType gameType);
}
