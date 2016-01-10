package com.warren.tournament.service.populators;

import com.warren.tournament.api.SideSorter;
import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatType.Orientation;

public class TournamentPopulatorService {

	public boolean populateNextRound(Tournament tournament, SideSorter sideSorter) {
		
		Orientation orientation = tournament.getFormatType().getOrientation();
		boolean populated = false;
		
		switch(orientation) {
		case BRACKET:
			BasicBracketPopulator populator = new BasicBracketPopulator();
			for(Bracket bracket : tournament.getBrackets()) {
				populated |= populator.populate(bracket, sideSorter);
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
