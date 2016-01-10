package com.warren.tournament.service.populators;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.warren.tournament.api.MatchPopulator;
import com.warren.tournament.api.SideSorter;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Side;

public class BasicMatchPopulator implements MatchPopulator {

	/**
	 * Populate a single match with sides picked from the provided set.
	 * For now it is assumed that the set has been sorted so that those sides that should pair up for the match are the 
	 * first and last items in the set.
	 */
	public void populate(Match match, TreeSet<Side> unassignedSides) {
		if(!match.hasPlayers()) {
			Side side1 = unassignedSides.pollFirst();
			Side side2 = unassignedSides.pollLast();
			match.getSides().get(0).setPlayers(side1.getPlayers());
			match.getSides().get(1).setPlayers(side2.getPlayers());
		}
	}
	
	/**
	 * Populate a single match with sides picked from the provided set.
	 * This involves a specialized sorting object that will apply a numeric value to the sortOrder field of the
	 * Sides in the provided Set. With this value set, a comparator can be provided to complete the sorting the
	 * normal way (sides to be matched exist at the top and bottom of the Set).
	 * 
	 * @param match
	 * @param unassignedSides
	 * @param sideSorter
	 */
	public void populate(Match match, Set<Side> unassignedSides, SideSorter sideSorter) {
		if(!match.hasPlayers()) {
			Set<Side> sortableSides = sideSorter.setSortOrderValues(unassignedSides);
			TreeSet<Side> sortedSides = new TreeSet<Side>(new Comparator<Side>(){
				@Override public int compare(Side s1, Side s2) {
					return s1.getSortOrder().compareTo(s2.getSortOrder());
				}});
			sortedSides.addAll(sortableSides);
			populate(match, sortedSides);
			unassignedSides.retainAll(sortedSides);
		}
	}

}
