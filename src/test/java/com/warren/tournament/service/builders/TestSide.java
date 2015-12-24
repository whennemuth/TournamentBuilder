package com.warren.tournament.service.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.util.PlayerMocks;

@RunWith(MockitoJUnitRunner.class)
public class TestSide {
	
	@Mock private Tournament tournament;
	@Mock private Bracket bracket;
	@Mock private Round round;
	@Mock private Match match1;
	@Mock private Match match2;
	TreeSet<Player> players;
	
	@Before
	public void setUp() throws Exception {
		players = PlayerMocks.getPlayerMocks(4);
		when(bracket.getTournament()).thenReturn(tournament);
		when(round.getBracket()).thenReturn(bracket);
		when(match1.getRound()).thenReturn(round);
	}

	@Test
	public void testEquals() {
		// Not using the player mocks because the equals method cannot be stubbed by mockito.
		// These player instances will serve as mocks and will be considered equal based on the email address only (for simplicity)
		Player player1 = new Player();
		player1.setEmail("player1@gmail.com");
		Player player2 = new Player();
		player2.setEmail("player2@gmail.com");
		Player player3 = new Player();
		player3.setEmail("player3@gmail.com");
		Player player4 = new Player();
		player4.setEmail("player4@gmail.com");
		
		when(match1.getId()).thenReturn(null);
		when(match2.getId()).thenReturn(null);
		Side side1 = new Side(match1);
		side1.addPlayer(player1); 
		side1.addPlayer(player2); 
		Side side2 = new Side(match2);
		side2.addPlayer(player3); 
		side2.addPlayer(player4);
		
		// Players are different between sides, so sides should not be equal.
		assertFalse(side1.equals(side2));
		
		// Both sides share one player in common, but that should not be enough for equality between sides.
		player3.setEmail(player1.getEmail());
		assertFalse(side1.equals(side2));
		
		// Both sides have all players in common between them, so they both should be equal
		player4.setEmail(player2.getEmail());
		assertTrue(side1.equals(side2));
		
		// Restore the emails
		player3.setEmail("player3@gmail.com");
		player4.setEmail("player4@gmail.com");
		
		// With players differing, two sides can only be equal if their matches both have ids and the ids are equal.
		when(match1.getId()).thenReturn(1);
		assertFalse(side1.equals(side2));
		when(match1.getId()).thenReturn(null);
		when(match2.getId()).thenReturn(2);
		assertFalse(side1.equals(side2));
		when(match1.getId()).thenReturn(1);
		assertFalse(side1.equals(side2));
		when(match2.getId()).thenReturn(1);
		assertTrue(side1.equals(side2));
		
		// With players and match ids differing, two sides can only be equal if their own ids are both not null and are equal.
		when(match2.getId()).thenReturn(2);
		side1.setId(1);
		assertFalse(side1.equals(side2));
		side1.setId(null);
		assertFalse(side1.equals(side2));
		side2.setId(2);
		assertFalse(side1.equals(side2));
		side1.setId(1);
		assertFalse(side1.equals(side2));
		side2.setId(side1.getId());
	}

	@Test
	public void testRankSingles() {
		when(tournament.getGameType()).thenReturn(GameType.SINGLES);
		
		// Assert that the rank of the side is the same as the rank of the one player in that side
		Side side = new Side(match1);		
		assertNull(side.getRank());
		Player player = (Player) players.toArray()[0];
		side.addPlayer(player); 
		assertEquals(new Float(1.0f), side.getRank());
		
		// Repeat the assertion with a different player
		side = new Side(match1);
		player = (Player) players.toArray()[1];
		side.addPlayer(player); 
		assertEquals(new Float(player.getRank()), side.getRank());
		
		// Assert that if the rank of the player is not known the method will return the rank private field value.
		when(player.getRank()).thenReturn(null);
		assertNull(side.getRank());		
		side.setRank(3.2f);
		assertEquals(new Float(3.2f), side.getRank());
	}

	@Test
	public void testRankDoubles() {
		when(tournament.getGameType()).thenReturn(GameType.DOUBLES);
		
		// Assert that the rank of the side is the same as the average rank of its players
		Side side = new Side(match1);		
		assertNull(side.getRank());
		Player player1 = (Player) players.toArray()[0];
		Player player2 = (Player) players.toArray()[1];
		side.addPlayer(player1); 
		side.addPlayer(player2); 
		Float expected = (new Float((player1.getRank() + player2.getRank()))/2.0f);
		assertEquals(expected, side.getRank());
		
		// Assert that if the rank of any of the players are not known the method will return the rank private field value.
		when(player1.getRank()).thenReturn(null);
		assertNull(side.getRank());		
		when(player2.getRank()).thenReturn(null);
		assertNull(side.getRank());
		side.setRank(3.2f);
		assertEquals(new Float(3.2f), side.getRank());
		
		// Assert that a rank of zero for all players still counts as a rank and will be used if the private rank field is null
		when(player1.getRank()).thenReturn(0);
		when(player2.getRank()).thenReturn(0);
		assertEquals(new Float(3.2f), side.getRank());
		side.setRank(null);
		assertEquals(new Float(0.0f), side.getRank());
	}

	@Test
	public void testRankCutthroat() {
		when(tournament.getGameType()).thenReturn(GameType.CUTTHROAT);
		
		// Assert that the rank of the side is the same as the average rank of its players
		Side side = new Side(match1);		
		assertNull(side.getRank());
		Player player1 = (Player) players.toArray()[0];
		Player player2 = (Player) players.toArray()[1];
		Player player3 = (Player) players.toArray()[2];
		side.addPlayer(player1); 
		side.addPlayer(player2); 
		side.addPlayer(player3); 
		Float expected = (float) ((player1.getRank() + player2.getRank() + player3.getRank())/3);
		assertEquals(expected, side.getRank());
		
		// Assert that if the rank of any of the players are not known the method will return the rank private field value.
		when(player1.getRank()).thenReturn(null);
		assertNull(side.getRank());		
		when(player2.getRank()).thenReturn(null);
		assertNull(side.getRank());
		when(player3.getRank()).thenReturn(null);
		assertNull(side.getRank());
		side.setRank(3.2f);
		assertEquals(new Float(3.2f), side.getRank());
		
		// Assert that a rank of zero for all players still counts as a rank and will be used if the private rank field is null
		when(player1.getRank()).thenReturn(0);
		when(player2.getRank()).thenReturn(0);
		when(player3.getRank()).thenReturn(0);
		assertEquals(new Float(3.2f), side.getRank());
		side.setRank(null);
		assertEquals(new Float(0.0f), side.getRank());
	}

}
