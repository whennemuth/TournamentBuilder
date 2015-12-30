package com.warren.tournament.service;

import java.util.Comparator;

import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.service.builders.TournamentBuilderService;
import com.warren.tournament.service.populators.TournamentPopulatorService;

public class TournamentService {
	
	public Tournament getTournament(TournamentParms parms, boolean populate) {
		
		// Build the tournament
		TournamentBuilderService builder = new TournamentBuilderService();
		Tournament tournament = builder.buildTournament(parms.getFormat(), parms.getGameType(), parms.getPlayers());
		
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
		switch(parms.getMatchingMethod()) {
		case CLOSEST_RANK:
			break;
		case HIGHEST_WITH_LOWEST_RANK:
			comparator = new Comparator<Side>() {
				/**
				 * Sides will be sorted by rank. If ranks are equal, then comparing the two sides would return zero.
				 * This is to be avoided because the TreeSet would consider the two sides equal and where the comparison is 
				 * triggered through the add method the set would not allow such a side to be added because this would 
				 * violate the uniqueness of the set. Therefore 1 is returned in case of zero. 
				 */
				public int compare(Side side1, Side side2) {
					try {
						int retval = 0;
						
						if(side1.getRank() == null) {
							if(side2.getRank() != null)
								retval = 1;
						}
						else if(side2.getRank() == null)
							retval = -1;
						else
							retval = side1.getRank().compareTo(side2.getRank());
						// Have exhausted all basis for comparison, so arbitrarily assign a non-zero value
						return retval == 0 ? 1 : retval;
							
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

		tournament.setGamesPerMatch(parms.getGamesPerMatch());
		tournament.setMatchingMethod(parms.getMatchingMethod());
		// populate the first round
		populator.populateNextRound(tournament, comparator);
		
		return tournament;
	}
}
