package com.warren.tournament.service.populators;

import java.util.Comparator;

import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatType.Orientation;

public class TournamentPopulatorService {

	public boolean populateNextRound(Tournament tournament, Comparator<Side> sideComparator) {
		
		Orientation orientation = tournament.getFormatType().getOrientation();
		boolean populated = false;
		
		switch(orientation) {
		case BRACKET:
			BasicBracketPopulator populator = new BasicBracketPopulator();
			for(Bracket bracket : tournament.getBrackets()) {
				populated |= populator.populate(bracket, sideComparator);
			}
			break;
		case ROUND_ROBIN:
			break;
		case HEAT:
			break;
		case POOL_PLAY:
			break;
		default:
			break;
		}
		
		return populated;
	}

}
