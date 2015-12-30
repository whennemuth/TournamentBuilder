package com.warren.tournament.api;

import java.util.Set;

import com.warren.tournament.entity1.Player;
import com.warren.tournament.entity1.Round;
import com.warren.tournament.enumerator.GameType;

public interface RoundPopulator {
	public void populate(Round round, GameType gameType, Set<Player> unassigned);
}
