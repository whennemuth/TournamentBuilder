package com.warren.tournament.service.populators;

import java.util.Set;
import java.util.TreeSet;

import com.warren.tournament.api.MatchPopulator;
import com.warren.tournament.api.RoundPopulator;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;

public class BasicRoundPopulator implements RoundPopulator {

	public boolean populate(Round round, TreeSet<Side> unassignedSides) {
		
		Tournament tournament = round.getBracket().getTournament();
		boolean populated = false;
		
		switch(tournament.getFormatType()) {
		case SINGLE_ELIMINATION:
			Set<Side> fullSides = round.getPopulatedSides();
			Set<Side> emptySides = round.getEmptySides();
							
			if(emptySides.isEmpty()) {
				// This round is already fully populated and either each match has ended or some matches are not over.
				populated = false; 
			}
			else {
				// Reduce sides down to those that have not lost yet and have not already been assigned to matches in the round
				unassignedSides.removeAll(fullSides);
				// populate all of, or the remainder of the round and no further rounds.
				MatchPopulator matchPopulator = new BasicMatchPopulator();
				for(Match match : round.getMatches()) {
					matchPopulator.populate(match, unassignedSides);
				}
				populated = true;
			}
			break;
		case CLASSIC_PLATE_DROPDOWN:
			break;
		case CONSOLATION:
			break;
		case DOUBLE_ELIMINATION:
			break;
		case FEED_IN_DROPDOWN:
			break;
		case FEED_IN_WITH_CONSOLATION:
			break;
		case OLYMPIC_FORMAT_DIVISION:
			break;
		case QUALIFIER_DRAW:
			break;
		case TRIPLE_KNOCKOUT:
			break;
		default:
			break;
		}
		
		return populated;
	}

}
