package com.warren.tournament.service.builders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.warren.tournament.api.MatchBuilder;
import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Round;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.service.builders.BasicRoundBuilder;

@RunWith(MockitoJUnitRunner.class)
public class TestBasicRoundBuilder {

	@Mock private Bracket bracket;
	@Mock private MatchBuilder matchBuilder;
	@Mock private Match match;
	
	private BasicRoundBuilder b;
	
	@Before
	public void setUp() throws Exception {
		when(matchBuilder.buildMatch(any(Round.class), any(GameType.class))).thenReturn(match);
	}

	@Test
	public void testBuildNextRound() {
		b = new BasicRoundBuilder();
		
		Round round = b.buildNextRound(bracket, matchBuilder, GameType.SINGLES, 35);
		assertEquals(3, round.getMatches().size());
		assertEquals(bracket, round.getBracket());
		
		round = b.buildNextRound(bracket, matchBuilder, GameType.SINGLES, 32);
		assertEquals(16, round.getMatches().size());
		assertEquals(bracket, round.getBracket());
		
	}

	@Test
	public void testGetMatchesInFirstRound() {
		b = new BasicRoundBuilder();
		assertEquals(1, b.getMatchesInFirstRound(2, GameType.SINGLES));
		assertEquals(1, b.getMatchesInFirstRound(3, GameType.SINGLES));
		assertEquals(2, b.getMatchesInFirstRound(4, GameType.SINGLES));
		assertEquals(1, b.getMatchesInFirstRound(5, GameType.SINGLES));
		assertEquals(2, b.getMatchesInFirstRound(6, GameType.SINGLES));
		assertEquals(3, b.getMatchesInFirstRound(7, GameType.SINGLES));
		assertEquals(4, b.getMatchesInFirstRound(8, GameType.SINGLES));
		assertEquals(1, b.getMatchesInFirstRound(9, GameType.SINGLES));
		assertEquals(3, b.getMatchesInFirstRound(35, GameType.SINGLES));
		assertEquals(16, b.getMatchesInFirstRound(32, GameType.SINGLES));
		
		assertEquals(1, b.getMatchesInFirstRound(4, GameType.DOUBLES));
		assertEquals(1, b.getMatchesInFirstRound(6, GameType.DOUBLES));
		assertEquals(2, b.getMatchesInFirstRound(8, GameType.DOUBLES));
		assertEquals(1, b.getMatchesInFirstRound(10, GameType.DOUBLES));
		assertEquals(2, b.getMatchesInFirstRound(12, GameType.DOUBLES));
		assertEquals(3, b.getMatchesInFirstRound(14, GameType.DOUBLES));
		assertEquals(4, b.getMatchesInFirstRound(16, GameType.DOUBLES));
		assertEquals(1, b.getMatchesInFirstRound(18, GameType.DOUBLES));
		assertEquals(3, b.getMatchesInFirstRound(70, GameType.DOUBLES));
		assertEquals(16, b.getMatchesInFirstRound(64, GameType.DOUBLES));		
	}

	@Test
	public void testCountMatchesInFirstRound() {
		b = new BasicRoundBuilder();
		assertEquals(1, b.countMatchesInFirstRound(2, GameType.SINGLES));
		assertEquals(1, b.countMatchesInFirstRound(3, GameType.SINGLES));
		assertEquals(2, b.countMatchesInFirstRound(4, GameType.SINGLES));
		assertEquals(1, b.countMatchesInFirstRound(5, GameType.SINGLES));
		assertEquals(2, b.countMatchesInFirstRound(6, GameType.SINGLES));
		assertEquals(3, b.countMatchesInFirstRound(7, GameType.SINGLES));
		assertEquals(4, b.countMatchesInFirstRound(8, GameType.SINGLES));
		assertEquals(1, b.countMatchesInFirstRound(9, GameType.SINGLES));
		assertEquals(3, b.countMatchesInFirstRound(35, GameType.SINGLES));
		assertEquals(16, b.countMatchesInFirstRound(32, GameType.SINGLES));
		
		assertEquals(1, b.countMatchesInFirstRound(4, GameType.DOUBLES));
		assertEquals(1, b.countMatchesInFirstRound(6, GameType.DOUBLES));
		assertEquals(2, b.countMatchesInFirstRound(8, GameType.DOUBLES));
		assertEquals(1, b.countMatchesInFirstRound(10, GameType.DOUBLES));
		assertEquals(2, b.countMatchesInFirstRound(12, GameType.DOUBLES));
		assertEquals(3, b.countMatchesInFirstRound(14, GameType.DOUBLES));
		assertEquals(4, b.countMatchesInFirstRound(16, GameType.DOUBLES));
		assertEquals(1, b.countMatchesInFirstRound(18, GameType.DOUBLES));
		assertEquals(3, b.countMatchesInFirstRound(70, GameType.DOUBLES));
		assertEquals(16, b.countMatchesInFirstRound(64, GameType.DOUBLES));
	}

	@Test
	public void testGetMatchesInBracket() {
		b = new BasicRoundBuilder(){};
		
		for(int players=2; players<=35; players++) {
			assertEquals(
					String.valueOf(players) + " players: ", 
					b.getMatchesInBracket(players, GameType.SINGLES), 
					b.countMatchesInBracket(players, GameType.SINGLES));
			
			assertEquals(
					String.valueOf(players*2) + " players: ", 
					b.getMatchesInBracket(players*2, GameType.DOUBLES), 
					b.countMatchesInBracket(players*2, GameType.DOUBLES));
		}
	}
}
