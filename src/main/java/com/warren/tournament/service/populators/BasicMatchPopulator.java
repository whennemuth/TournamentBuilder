package com.warren.tournament.service.populators;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.warren.tournament.api.MatchPopulator;
import com.warren.tournament.entity1.Match;
import com.warren.tournament.entity1.Player;
import com.warren.tournament.enumerator.GameType;

public class BasicMatchPopulator implements MatchPopulator {

	public void populate(Match match, GameType gameType, List<Player> unassigned) {

		// This is wrong - change it.
		Set<Player> playersToAssign = new TreeSet<Player>(new Comparator<Player>() {
			public int compare(Player player1, Player player2) {
				return player1.getRank() - player2.getRank();
			}
		});
		
		playersToAssign.addAll(unassigned);
		// RESUME HERE
	}

}
