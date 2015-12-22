package com.warren.tournament.service.populators;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatBracket;
import com.warren.tournament.enumerator.FormatRoundRobin;

public class TournamentPopulatorService {

	public void populateTournament(Tournament tournament, Comparator<Side> sideComparator) {
		if(tournament.getFormatType().equals(FormatBracket.SINGLE_ELIMINATION)) {
			Set<Side> sides = new TreeSet<Side>(sideComparator);
			for(Player player : tournament.getPlayers()) {
				Side side = new Side(null);
				side.addPlayer(player);
			}
			
			if(tournament.getBrackets() != null && tournament.getBrackets().isEmpty() == false) {
				Bracket bracket = tournament.getBrackets().iterator().next();
				for(Round round : bracket.getRounds()) {
					Set<Side> fullSides = round.getPopulatedSides();
					Set<Side> emptySides = round.getEmptySides();
					sides.removeAll(fullSides); // TODO: Write equals methods for Match and Side, for this removeAll to work 
					if(emptySides.isEmpty()) {
						// This round is already fully populated, so go to the next round.
						continue;
					}
					if(!fullSides.isEmpty()) {
						// TODO: write code here to populate all of, or the remainder of the round and no further rounds.
						// All remaining content in sides should be those that have not yet been knocked out and have not already been assigned to matches. 
						break;
					}
				}
			}
		}
		if(tournament.getFormatType().equals(FormatBracket.DOUBLE_ELIMINATION)) {
		}
		if(tournament.getFormatType().equals(FormatBracket.CLASSIC_PLATE_DROPDOWN)) {
		}
		if(tournament.getFormatType().equals(FormatBracket.CONSOLATION)) {
		}
		if(tournament.getFormatType().equals(FormatBracket.FEED_IN_DROPDOWN)) {
		}
		if(tournament.getFormatType().equals(FormatBracket.FEED_IN_WITH_CONSOLATION)) {
		}
		if(tournament.getFormatType().equals(FormatBracket.OLYMPIC_FORMAT_DIVISION)) {
		}
		if(tournament.getFormatType().equals(FormatBracket.QUALIFIER_DRAW)) {
		}
		if(tournament.getFormatType().equals(FormatBracket.TRIPLE_KNOCKOUT)) {
		}
		if(tournament.getFormatType().equals(FormatRoundRobin.ROUND_ROBIN)) {
		}
		if(tournament.getFormatType().equals(FormatRoundRobin.DOUBLE_ROUND_ROBIN)) {
		}
		if(tournament.getFormatType().equals(FormatRoundRobin.TRIPLE_ROUND_ROBIN)) {
		}
		
	}

}
