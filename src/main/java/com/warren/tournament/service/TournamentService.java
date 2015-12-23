package com.warren.tournament.service;

import java.util.Comparator;
import java.util.Set;

import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Side;
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
			Set<Player> players,
			int gamesPerMatch) {
		
		// Build the tournament
		TournamentBuilderService builder = new TournamentBuilderService();
		Tournament tournament = builder.buildTournament(format, gameType, players);
		
		// Populate the tournament (or at least the first round of the first bracket)
		TournamentPopulatorService populator = new TournamentPopulatorService();
		/**
		 * Whatever the matching method, the comparator will sort sides correspondingly so that any 
		 * side at the top of the list should be matched with the side at the bottom of the list
		 * 
		 * TODO: The above is probably too complicated - figure out another way (may involve injecting 
		 * something else along with the comparator that controls selection from the sorted list of sides 
		 * so that selecting from the top and bottom of the list is not the only option).
		 */
		Comparator<Side> comparator = null;
		switch(matchingMethod) {
		case CLOSEST_RANK:
			break;
		case HIGHEST_WITH_LOWEST_RANK:
			comparator = new Comparator<Side>() {
				public int compare(Side side1, Side side2) {
					try {
						return side1.getRank().compareTo(side2.getRank());
					} 
					catch (Exception e) {
						return 0;
					}
				}
			};
			break;
		case MIDDLE_RANK:
			break;
		case RANDOM:
			break;
		}

		tournament.setGamesPerMatch(gamesPerMatch);
		tournament.setMatchingMethod(matchingMethod);
		populator.populateTournament(tournament, comparator);
		
		return tournament;
	}
}
