package com.warren.tournament.service.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.warren.tournament.api.MatchBuilder;
import com.warren.tournament.api.RoundBuilder;
import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.service.builders.AbstractBracketBuilder;
import com.warren.tournament.service.builders.BasicRoundBuilder;
import com.warren.tournament.util.PlayerMocks;

@RunWith(MockitoJUnitRunner.class)
public class TestAbstractBracketBuilder {

	@Mock private Tournament tournament;
	@Mock private Bracket bracket;
	@Mock private MatchBuilder matchBuilder;
	@Mock private Match singlesMatch;
	@Mock private Match doublesMatch;
	@Mock private Player player1;
	
	private RoundBuilder roundBuilder;
	private AbstractBracketBuilder b;
	
	@Before
	public void setUp() throws Exception {
		
		// Build a set of 35 Mock players and make the tournament mock return them from the corresponding getter.
		final HashSet<Player> playerMocks = PlayerMocks.getPlayerMocks(35);
		when(tournament.getPlayers()).thenReturn(playerMocks);
		
		// Create the RoundBuilder
		roundBuilder = new BasicRoundBuilder();
		
		// Assign how the MatchBuilder mock creates Match mocks for singles (player assignments are arbitrary)
		when(singlesMatch.getSides()).thenAnswer(new Answer<List<Side>>(){
			public List<Side> answer(InvocationOnMock invocation) throws Throwable {
				List<Side> sides = new ArrayList<Side>();
				
				Side side1 = Mockito.mock(Side.class);
				when(side1.getMatch()).thenReturn(singlesMatch);
				when(side1.getPlayers()).thenReturn(Arrays.asList(new Player[]{(Player) playerMocks.toArray()[0]}));
				
				Side side2 = Mockito.mock(Side.class);
				when(side2.getMatch()).thenReturn(singlesMatch);
				when(side2.getPlayers()).thenReturn(Arrays.asList(new Player[]{(Player) playerMocks.toArray()[1]}));
				
				when(side1.getOpposingSides()).thenReturn(Arrays.asList(new Side[]{side2}));
				when(side2.getOpposingSides()).thenReturn(Arrays.asList(new Side[]{side1}));
				
				sides.add(side1);
				sides.add(side2);
				return sides;
			}});
		
		// Assign how the MatchBuilder mock creates Match mocks for doubles (player assignments are arbitrary)
		when(doublesMatch.getSides()).thenAnswer(new Answer<List<Side>>(){
			public List<Side> answer(InvocationOnMock invocation) throws Throwable {
				List<Side> sides = new ArrayList<Side>();
				
				Side side1 = Mockito.mock(Side.class);
				when(side1.getMatch()).thenReturn(doublesMatch);
				when(side1.getPlayers()).thenReturn(Arrays.asList(new Player[]{
						(Player) playerMocks.toArray()[0],
						(Player) playerMocks.toArray()[1]
				}));
				
				Side side2 = Mockito.mock(Side.class);
				when(side2.getMatch()).thenReturn(doublesMatch);
				when(side2.getPlayers()).thenReturn(Arrays.asList(new Player[]{
						(Player) playerMocks.toArray()[2],
						(Player) playerMocks.toArray()[3]
				}));
				
				when(side1.getOpposingSides()).thenReturn(Arrays.asList(new Side[]{side2}));
				when(side2.getOpposingSides()).thenReturn(Arrays.asList(new Side[]{side1}));
				
				sides.add(side1);
				sides.add(side2);
				return sides;
			}});
		
		when(matchBuilder.buildMatch(any(Round.class), eq(GameType.SINGLES))).thenReturn(singlesMatch);
		when(matchBuilder.buildMatch(any(Round.class), eq(GameType.DOUBLES))).thenReturn(doublesMatch);
	}

	@Test
	public void testBuildNextBracket() {
		b = new AbstractBracketBuilder() {};
		Bracket bracket = b.buildNextBracket(tournament, roundBuilder, matchBuilder, GameType.SINGLES);
		assertNotNull(bracket);
		assertEquals(6, bracket.getRounds().size());
		assertEquals(3, bracket.getRounds().get(0).getMatches().size());
		assertEquals(16, bracket.getRounds().get(1).getMatches().size());
		assertEquals(8, bracket.getRounds().get(2).getMatches().size());
		assertEquals(4, bracket.getRounds().get(3).getMatches().size());
		assertEquals(2, bracket.getRounds().get(4).getMatches().size());
		assertEquals(1, bracket.getRounds().get(5).getMatches().size());
	}

	@Test
	public void testGetMatchesInBracket() {
		AbstractBracketBuilder b = new AbstractBracketBuilder(){};
		
		for(int players=2; players<=35; players++) {
			assertEquals((players-1), b.countMatchesInBracket(players, GameType.SINGLES));
		}
		
		for(int players=2; players<=35; players+=2) {
			assertEquals((GameType.DOUBLES.getSidesPerGame(players)-1), b.countMatchesInBracket(players, GameType.DOUBLES));
		}
	}

}
