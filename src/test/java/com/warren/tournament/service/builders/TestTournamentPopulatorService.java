package com.warren.tournament.service.builders;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatBracket;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.service.populators.TournamentPopulatorService;
import com.warren.tournament.util.PlayerMocks;

//@RunWith(MockitoJUnitRunner.class)
public class TestTournamentPopulatorService {

	private TournamentBuilderService builder;
	private TournamentPopulatorService svc;
	private TreeSet<Player> playerMocks;
	private Tournament tournament;
	private Comparator<Side> sideComparator;

	//@Before
	public void setUp() throws Exception {
		// Build a set of 35 Mock players and make the tournament mock return them from the corresponding getter.
		playerMocks = PlayerMocks.getPlayerMocks(35);
		sideComparator = new Comparator<Side>() {
			public int compare(Side side1, Side side2) {
				try {
					return side1.getRank().compareTo(side2.getRank());
				} 
				catch (Exception e) {
					return 0;
				}
			}
		};	
	}

	//@Test
	public void testPopulateNextRound() {
		builder = new TournamentBuilderService();
		tournament = builder.buildTournament(FormatBracket.SINGLE_ELIMINATION, GameType.SINGLES, playerMocks);
		
		boolean retval = svc.populateNextRound(tournament, sideComparator);
		assertTrue(retval);
		retval = svc.populateNextRound(tournament, sideComparator);
		assertTrue(retval);
		
		/**
		 * TODO:
		 * 1) Add games and scores to some of the matches of the populated round and try to populate the next round - should still return false
		 * 2) Add games and scores to the remaining matches of the populated round and try to populate the next round - should return true.
		 * 3) repeat 1) and 2) until the tournament is "over".
		 */
	}

}
