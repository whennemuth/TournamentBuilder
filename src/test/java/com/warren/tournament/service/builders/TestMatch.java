package com.warren.tournament.service.builders;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.warren.tournament.entity1.Bracket;
import com.warren.tournament.entity1.Match;
import com.warren.tournament.entity1.Player;
import com.warren.tournament.entity1.Round;
import com.warren.tournament.entity1.Side;
import com.warren.tournament.entity1.Tournament;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.util.PlayerMocks;

@RunWith(MockitoJUnitRunner.class)
public class TestMatch {
	
	@Mock private Tournament tournament;
	@Mock private Bracket bracket;
	@Mock private Round round;
	
	private TreeSet<Player> players;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private Player player5;
	private Player player6;
	
	private Match match1;
	private Side side1;
	private Side side2;
	
	private Match match2;
	private Side side3;
	private Side side4;

	@Before
	public void setUp() throws Exception {
		players = PlayerMocks.getPlayerMocks(6);
		when(bracket.getTournament()).thenReturn(tournament);
		when(round.getBracket()).thenReturn(bracket);
		
		// Create the match
		match1 = new Match(round);
		match2 = new Match(round);
		
		// Create the sides
		side1 = new Side(match1);
		side2 = new Side(match1);
		side1.addOpposingSide(side2);
		side2.addOpposingSide(side1);
		
		side3 = new Side(match2);
		side4 = new Side(match2);
		side3.addOpposingSide(side4);
		side4.addOpposingSide(side3);
		
		// Create players for the sides, 2 for each side (doubles match).
		// Not using the player mocks because the equals method cannot be stubbed by mockito.
		// These player instances will serve as mocks and will be considered equal based on the email address only (for simplicity)
		player1 = (Player) players.toArray()[0];
		player2 = (Player) players.toArray()[1];
		player3 = (Player) players.toArray()[2];
		player4 = (Player) players.toArray()[3];
		player5 = (Player) players.toArray()[4];
		player6 = (Player) players.toArray()[5];
		
		// Add the sides to the match
		match1.addSide(side1);
		match1.addSide(side2);
		match2.addSide(side3);
		match2.addSide(side4);
	}

	@Test
	public void testHasPlayers() {
		
		// Assert that the match been assigned all its players - has each side got its full complement of players (ie: 2 for doubles).
		// The assumption is that no match would ever have more than zero, but less than the full complement of players.
		when(tournament.getGameType()).thenReturn(GameType.SINGLES);
		assertFalse(match1.hasPlayers());
		side1.addPlayer(player1);
		assertFalse(match1.hasPlayers());
		side2.addPlayer(player4);
		assertTrue(match1.hasPlayers());		
		
		when(tournament.getGameType()).thenReturn(GameType.DOUBLES);
		assertFalse(match1.hasPlayers());
		side1.addPlayer(player2);
		assertFalse(match1.hasPlayers());
		side2.addPlayer(player5);
		assertTrue(match1.hasPlayers());
		
		when(tournament.getGameType()).thenReturn(GameType.CUTTHROAT);
		assertFalse(match1.hasPlayers());
		side1.addPlayer(player3);
		assertFalse(match1.hasPlayers());
		side2.addPlayer(player6);
		assertTrue(match1.hasPlayers());
	}

	@Test
	public void testEquals() throws Exception {
		
		assertFalse(match1.equals(match2));
		
		match2.getSides().remove(side3);
		match2.getSides().add(side1);
		assertFalse(match1.equals(match2));
		
		match2.getSides().remove(side4);
		match2.getSides().add(side2);
		assertTrue(match1.equals(match2));
		
		setUp();
		match1.setId(1);
		assertFalse(match1.equals(match2));
		match2.setId(2);
		assertFalse(match1.equals(match2));
		match2.setId(match1.getId());
		assertTrue(match1.equals(match2));
	}

}
