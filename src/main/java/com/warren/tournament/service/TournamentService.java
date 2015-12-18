package com.warren.tournament.service;

import java.util.Comparator;
import java.util.Set;

import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;
import com.warren.tournament.service.builders.TournamentBuilderService;
import com.warren.tournament.service.populators.TournamentPopulatorService;

public class TournamentService {

	public Tournament getTournament(
			Enum<?> format, 
			GameType gameType, 
			MatchingMethod matchingMethod, 
			Set<Player> players) {
		
		// Build the tournament
		TournamentBuilderService builder = new TournamentBuilderService();
		Tournament tournament = builder.buildTournament(format, gameType, players);
		
		// Populate the tournament (or at least the first round of the first bracket)
		TournamentPopulatorService populator = new TournamentPopulatorService(format, gameType, players);
		/**
		 * Whatever the matching method, the comparator will sort players correspondingly so that any 
		 * player at the top of the list should be matched with the player at the bottom of the list
		 */
		Comparator<Player> comparator = null;
		switch(matchingMethod) {
		case CLOSEST_RANK:
			break;
		case HIGHEST_WITH_LOWEST_RANK:
			comparator = new Comparator<Player>() {
				public int compare(Player player1, Player player2) {
					return player1.getRank() - player2.getRank();
				}
			};
			break;
		case MIDDLE_RANK:
			break;
		case RANDOM:
			break;
		}

		populator.populateTournament(tournament, comparator);
		
		return tournament;
	}
}
