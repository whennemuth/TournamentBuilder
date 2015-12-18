package com.warren.tournament.api;

import java.util.List;

import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Player;
import com.warren.tournament.enumerator.GameType;

public interface MatchPopulator {
	public void populate(Match match, GameType gameType, List<Player> unassigned);
}
