package com.warren.tournament.service.builders;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatBracket;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.service.builders.TournamentBuilderService;
import com.warren.tournament.util.PlayerMocks;

@RunWith(MockitoJUnitRunner.class)
public class TestTournamentBuilderService {

	private TournamentBuilderService svc;
	private HashSet<Player> playerMocks;
	
	@Before
	public void setUp() throws Exception {
		
		// Build a set of 35 Mock players and make the tournament mock return them from the corresponding getter.
		playerMocks = PlayerMocks.getPlayerMocks(35);
	}

	@Test
	public void testBuildTournament() {
		svc = new TournamentBuilderService();
		Tournament t = svc.buildTournament(FormatBracket.SINGLE_ELIMINATION, GameType.SINGLES, playerMocks);
		assertEquals(1, t.getBrackets().size());
		System.out.println(t);
	}

}
