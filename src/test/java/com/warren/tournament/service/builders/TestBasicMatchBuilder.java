package com.warren.tournament.service.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.warren.tournament.api.MatchBuilder;
import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Round;
import com.warren.tournament.enumerator.GameType;

public class TestBasicMatchBuilder {

	@Test
	public void testBuildNextMatch() {
		
		Bracket bracket = new Bracket();
		Round round = new Round(bracket);
		MatchBuilder b = new BasicMatchBuilder();
		
		Match m = b.buildMatch(round, GameType.CUTTHROAT);
		assertEquals(3, m.getSides().size());
		assertEquals(2, m.getSides().get(0).getOpposingSides().size());
		assertEquals(2, m.getSides().get(1).getOpposingSides().size());
		assertEquals(2, m.getSides().get(2).getOpposingSides().size());
		assertTrue(round == m.getRound());
		assertNull(m.getGames());

		m = b.buildMatch(round, GameType.DOUBLES);
		assertEquals(2, m.getSides().size());
		assertEquals(1, m.getSides().get(0).getOpposingSides().size());
		assertEquals(1, m.getSides().get(1).getOpposingSides().size());
		assertTrue(round == m.getRound());
		assertNull(m.getGames());

		m = b.buildMatch(round, GameType.SINGLES);
		assertEquals(2, m.getSides().size());
		assertEquals(1, m.getSides().get(0).getOpposingSides().size());
		assertEquals(1, m.getSides().get(1).getOpposingSides().size());
		assertTrue(round == m.getRound());
		assertNull(m.getGames());
	}

}
