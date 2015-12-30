package com.warren.tournament.api;

import java.util.List;

import com.warren.tournament.entity1.Match;
import com.warren.tournament.entity1.Player;
import com.warren.tournament.enumerator.GameType;

public interface MatchPopulator {
	public void populate(Match match, GameType gameType, List<Player> unassigned);
}
