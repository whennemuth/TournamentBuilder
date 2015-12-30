package com.warren.tournament.api;

import java.util.Set;

import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Round;
import com.warren.tournament.enumerator.GameType;

public interface RoundPopulator {
	public void populate(Round round, GameType gameType, Set<Player> unassigned);
}
