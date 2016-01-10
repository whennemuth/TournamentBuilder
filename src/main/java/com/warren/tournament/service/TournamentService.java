package com.warren.tournament.service;

import java.util.Comparator;
import java.util.Set;

import com.warren.tournament.api.SideSorter;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.service.builders.TournamentBuilderService;
import com.warren.tournament.service.populators.SideComparator;
import com.warren.tournament.service.populators.TournamentPopulatorService;

public class TournamentService {
	
	public Tournament getTournament(TournamentParms parms, boolean populate) {
		
		// Build the tournament
		TournamentBuilderService builder = new TournamentBuilderService();
		Tournament tournament = builder.buildTournament(parms.getFormat(), parms.getGameType(), parms.getPlayers());
		
		// Populate the tournament (or at least the first round of the first bracket)
		TournamentPopulatorService populator = new TournamentPopulatorService();
		/**
		 * Whatever the matching method, the Comparator or SideSorter will sort sides correspondingly so 
		 * that any side at the top of the list should be matched with the side at the bottom of the list
		 */
		SideSorter sideSorter = null;
		
		switch(parms.getMatchingMethod()) {
		case CLOSEST_RANK:
			sideSorter = new SideSorter() {
				@Override public Set<Side> setSortOrderValues(Set<Side> unassignedSides) {
					// TODO: Finish this.
					return null;
				}};
			break;
		case HIGHEST_WITH_LOWEST_RANK:
			Comparator<Side> comparator = SideComparator.getInstance("rank", Float.class);
			sideSorter = SideSorter.getComparatorOnlyInstance(comparator);
			break;
		case MIDDLE_RANK:
			sideSorter = new SideSorter() {
				@Override public Set<Side> setSortOrderValues(Set<Side> unassignedSides) {
					// TODO: Finish this.
					return null;
				}};
			break;
		case RANDOM:
			sideSorter = new SideSorter() {
				@Override public Set<Side> setSortOrderValues(Set<Side> unassignedSides) {
					// TODO: Finish this.
					return null;
				}};
			break;
		}

		tournament.setGamesPerMatch(parms.getGamesPerMatch());
		tournament.setMatchingMethod(parms.getMatchingMethod());
		// populate the first round
		populator.populateNextRound(tournament, sideSorter);
		
		return tournament;
	}
}
