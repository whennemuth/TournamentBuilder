package com.warren.tournament.service.populators;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.warren.tournament.api.BracketPopulator;
import com.warren.tournament.api.SideSorter;
import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;

public class BasicBracketPopulator implements BracketPopulator {

	@Override
	public boolean populate(Bracket bracket, SideSorter sideSorter) {
		
		Tournament tournament = bracket.getTournament();
		
		switch(tournament.getFormatType()) {
		case SINGLE_ELIMINATION:			
			// Create a Sortable set of sides that accounts for all players in the tournament.
			// These sides can be sorted because they have players and the comparator can work off their combined ranks.
			
			TreeSet<Side> sides = new TreeSet<Side>();
			BasicRoundPopulator roundPopulator = new BasicRoundPopulator();
			
			for(Round round : bracket.getRounds()) {
				
				if(sides.isEmpty()) {
					Set<Side> unsortedSides = new HashSet<Side>();
					for(Player player : tournament.getPlayers()) {
						Match dummyMatch = new Match(round);
						Side side = new Side(dummyMatch);
						// TODO: extend the line below to add players enough to satisfy GameType.playersPerSide.
						side.addPlayer(player);
						unsortedSides.add(side);
					}
					sides = sideSorter.getSortedSides(unsortedSides);
				}
				
				if(roundPopulator.populate(round, sides)) {
					return true;
				}
				else if(round.isComplete()) {
					continue; // Round is already fully populated and each match has ended, so go to the next round.
				}
				else {
					return false; // Round is already fully populated, but not every match has ended, so exit out.
				}
			}
			break;
		case DOUBLE_ELIMINATION:
			break;
		case TRIPLE_KNOCKOUT:
			break;
		case CLASSIC_PLATE_DROPDOWN:
			break;
		case CONSOLATION:
			break;
		case FEED_IN_DROPDOWN:
			break;
		case FEED_IN_WITH_CONSOLATION:
			break;
		case OLYMPIC_FORMAT_DIVISION:
			break;
		case QUALIFIER_DRAW:
			break;
		default:
			return false;
		}
		
		return false;
	}

}
