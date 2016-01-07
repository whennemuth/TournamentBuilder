package com.warren.tournament.service.populators;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.warren.tournament.api.BracketPopulator;
import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;

public class BasicBracketPopulator implements BracketPopulator {

	public boolean populate(Bracket bracket, Comparator<Side> sideComparator) {
		
		Tournament tournament = bracket.getTournament();
		
		switch(tournament.getFormatType()) {
		case SINGLE_ELIMINATION:
			// Create a Sortable set of sides that accounts for all players in the tournament.
			// These sides can be sorted because they have players and the comparator can work off their combined ranks.
			TreeSet<Side> sides = new TreeSet<Side>(sideComparator);
			
			for(Round round : bracket.getRounds()) {
				
				if(sides.isEmpty()) {
					for(Player player : tournament.getPlayers()) {
						Match dummyMatch = new Match(round);
						Side side = new Side(dummyMatch);
						side.addPlayer(player);
						sides.add(side);
					}
				}
				
				Set<Side> fullSides = round.getPopulatedSides();
				Set<Side> emptySides = round.getEmptySides();
								
				if(emptySides.isEmpty() && round.isComplete()) {
					// This round is already fully populated and each match has ended, so go to the next round.
					continue; 
				}
				else if(emptySides.isEmpty()) {
					// This round is already fully populated, but not every match has ended, so exit out.
					return false; 
				}
				else {
					// Reduce sides down to those that have not lost yet and have not already been assigned to matches in the round
					sides.removeAll(fullSides);
					// populate all of, or the remainder of the round and no further rounds.
					for(Match match : round.getMatches()) {
						if(!match.hasPlayers()) {
							Side side1 = sides.first();
							Side side2 = sides.last();
							match.getSides().get(0).setPlayers(side1.getPlayers());
							match.getSides().get(1).setPlayers(side2.getPlayers());
							sides.remove(side1);
							sides.remove(side2);
						}
					}
					return true;
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
