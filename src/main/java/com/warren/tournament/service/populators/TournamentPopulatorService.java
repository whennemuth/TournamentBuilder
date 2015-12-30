package com.warren.tournament.service.populators;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatType;

public class TournamentPopulatorService {

	public boolean populateNextRound(Tournament tournament, Comparator<Side> sideComparator) {
		
		if(tournament.getFormatType().equals(FormatType.SINGLE_ELIMINATION)) {
			
			// Create a Sortable set of sides that accounts for all players in the tournament.
			// These sides can be sorted because they have players and the comparator can work off their combined ranks.
			TreeSet<Side> sides = new TreeSet<Side>(sideComparator);
			
			Bracket bracket = tournament.getBrackets().iterator().next();	// Gets the first and only bracket
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
		}
		if(tournament.getFormatType().equals(FormatType.DOUBLE_ELIMINATION)) {
		}
		if(tournament.getFormatType().equals(FormatType.CLASSIC_PLATE_DROPDOWN)) {
		}
		if(tournament.getFormatType().equals(FormatType.CONSOLATION)) {
		}
		if(tournament.getFormatType().equals(FormatType.FEED_IN_DROPDOWN)) {
		}
		if(tournament.getFormatType().equals(FormatType.FEED_IN_WITH_CONSOLATION)) {
		}
		if(tournament.getFormatType().equals(FormatType.OLYMPIC_FORMAT_DIVISION)) {
		}
		if(tournament.getFormatType().equals(FormatType.QUALIFIER_DRAW)) {
		}
		if(tournament.getFormatType().equals(FormatType.TRIPLE_KNOCKOUT)) {
		}
		if(tournament.getFormatType().equals(FormatType.ROUND_ROBIN)) {
		}
		if(tournament.getFormatType().equals(FormatType.DOUBLE_ROUND_ROBIN)) {
		}
		if(tournament.getFormatType().equals(FormatType.TRIPLE_ROUND_ROBIN)) {
		}
		
		return false;
	}

}
