package com.warren.tournament.api;

import java.util.Comparator;
import java.util.Set;

import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Player;
import com.warren.tournament.enumerator.GameType;

public interface BracketPopulator {
	
	public void populate(
			Bracket bracket, 
			GameType gameType, 
			Set<Player> unassigned, 
			Comparator<Player> comparator);
}
