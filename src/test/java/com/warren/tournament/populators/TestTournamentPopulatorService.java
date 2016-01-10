package com.warren.tournament.populators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.warren.tournament.api.SideSorter;
import com.warren.tournament.entity.Game;
import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Side;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatType;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.service.builders.TournamentBuilderService;
import com.warren.tournament.service.populators.SideComparator;
import com.warren.tournament.service.populators.TournamentPopulatorService;
import com.warren.tournament.util.PlayerMocks;

@RunWith(MockitoJUnitRunner.class)
public class TestTournamentPopulatorService {

	private TournamentBuilderService builder;
	private TournamentPopulatorService svc;
	private TreeSet<Player> playerMocks;
	private Tournament tournament;
	private Comparator<Side> sideComparator;
	private SideSorter sideSorter;

	@Before
	public void setUp() throws Exception {
		sideComparator = SideComparator.getInstance("rank", Float.class);
		sideSorter = new SideSorter() {
			@Override public Set<Side> setSortOrderValues(Set<Side> unassignedSides) {
				return unassignedSides; // Don't actually apply any sort values.
			}};
		sideSorter.setComparator(sideComparator);
	}

	/**
	 * Keep adding games to the matches and then scores to those games, asserting regularly
	 * about whether or not the round is finished and finally that the bracket is finished.
	 */
	@Test
	public void testPopulateNextRound_SingleElimination_Singles() {
		playerMocks = PlayerMocks.getPlayerMocks(10);
		builder = new TournamentBuilderService();
		tournament = builder.buildTournament(FormatType.SINGLE_ELIMINATION, GameType.SINGLES, playerMocks);
		tournament.setGamesPerMatch(3);
		svc = new TournamentPopulatorService();
				
		// Should always be able to populate the first round for the first time.
		boolean retval = svc.populateNextRound(tournament, sideSorter);
		assertTrue(retval);
		
		// Now the first round should not be populatable if no game activity has occurred.
		retval = svc.populateNextRound(tournament, sideSorter);
		assertFalse(retval);
		
		// Add games for first match, first round
		assertNextGame(1, 0, 0, 1, false);
		assertNextGame(2, 0, 0, 2, false);
		assertNextGame(3, 0, 0, 3, false);		
		// Add games for second match, first round
		assertNextGame(4, 0, 1, 1, false);
		assertNextGame(5, 0, 1, 2, false);
		assertNextGame(6, 0, 1, 3, false);

		// Add scores for first match, first round
		assertNextGameScore(0, 0, 0, 11, 9, false);
		assertNextGameScore(0, 0, 1, 11, 9, false);
		assertNextGameScore(0, 0, 2, 11, 9, false);
		// Add scores for second match, first round
		assertNextGameScore(0, 1, 0, 11, 9, false);
		assertNextGameScore(0, 1, 1, 11, 9, false);
		assertTrue(tournament.getBrackets().get(0).getRounds().get(1).getPopulatedSides().isEmpty());
		assertNextGameScore(0, 1, 2, 11, 9, true);
		assertFalse(tournament.getBrackets().get(0).getRounds().get(1).getPopulatedSides().isEmpty());
		
		// Add games for first match, second round
		assertNextGame(7, 1, 0, 1, false);
		assertNextGame(8, 1, 0, 2, false);
		assertNextGame(9, 1, 0, 3, false);
		// Add games for second match, second round
		assertNextGame(10, 1, 1, 1, false);
		assertNextGame(11, 1, 1, 2, false);
		assertNextGame(12, 1, 1, 3, false);
		// Add games for third match, second round
		assertNextGame(13, 1, 2, 1, false);
		assertNextGame(14, 1, 2, 2, false);
		assertNextGame(15, 1, 2, 3, false);
		// Add games for fourth match, second round
		assertNextGame(16, 1, 3, 1, false);
		assertNextGame(17, 1, 3, 2, false);
		assertNextGame(18, 1, 3, 3, false);
		
		// Add scores for first match, second round
		assertNextGameScore(1, 0, 0, 11, 9, false);
		assertNextGameScore(1, 0, 1, 11, 9, false);
		assertNextGameScore(1, 0, 2, 11, 9, false);
		// Add scores for first match, second round
		assertNextGameScore(1, 1, 0, 11, 9, false);
		assertNextGameScore(1, 1, 1, 11, 9, false);
		assertNextGameScore(1, 1, 2, 11, 9, false);
		// Add scores for first match, second round
		assertNextGameScore(1, 2, 0, 11, 9, false);
		assertNextGameScore(1, 2, 1, 11, 9, false);
		assertNextGameScore(1, 2, 2, 11, 9, false);
		// Add scores for first match, second round
		assertNextGameScore(1, 3, 0, 11, 9, false);
		assertNextGameScore(1, 3, 1, 11, 9, false);
		assertTrue(tournament.getBrackets().get(0).getRounds().get(2).getPopulatedSides().isEmpty());
		assertNextGameScore(1, 3, 2, 11, 9, true);
		assertFalse(tournament.getBrackets().get(0).getRounds().get(2).getPopulatedSides().isEmpty());

		// Add games for first match, third round
		assertNextGame(19, 2, 0, 1, false);
		assertNextGame(20, 2, 0, 2, false);
		assertNextGame(21, 2, 0, 3, false);
		// Add games for second match, third round
		assertNextGame(22, 2, 1, 1, false);
		assertNextGame(23, 2, 1, 2, false);
		assertNextGame(24, 2, 1, 3, false);

		// Add scores for first match, third round
		assertNextGameScore(2, 0, 0, 11, 9, false);
		assertNextGameScore(2, 0, 1, 11, 9, false);
		assertNextGameScore(2, 0, 2, 11, 9, false);
		// Add scores for second match, third round
		assertNextGameScore(2, 1, 0, 11, 9, false);
		assertNextGameScore(2, 1, 1, 11, 9, false);
		assertTrue(tournament.getBrackets().get(0).getRounds().get(3).getPopulatedSides().isEmpty());
		assertNextGameScore(2, 1, 2, 11, 9, true);
		assertFalse(tournament.getBrackets().get(0).getRounds().get(3).getPopulatedSides().isEmpty());

		// Add games for first match, final round
		assertNextGame(25, 3, 0, 1, false);
		assertNextGame(26, 3, 0, 2, false);
		assertNextGame(26, 3, 0, 3, false);
		
		// Add scores for first match, final round
		assertNextGameScore(3, 0, 0, 11, 9, false);
		assertNextGameScore(3, 0, 1, 11, 9, false);
		assertNextGameScore(3, 0, 2, 11, 9, false);
		
		assertTrue(tournament.getBrackets().get(0).getRounds().get(0).isComplete());
		assertTrue(tournament.getBrackets().get(0).getRounds().get(1).isComplete());
		assertTrue(tournament.getBrackets().get(0).getRounds().get(2).isComplete());
		assertTrue(tournament.getBrackets().get(0).getRounds().get(3).isComplete());
		
		assertTrue(tournament.getBrackets().get(0).isComplete());
		assertTrue(tournament.isComplete());

		// Should not be able to add any more games to any more rounds 
		assertNextGame(27, 3, 0, 3, false);
		assertNextGameScore(3, 0, 0, 11, 9, false);
	}
	
	//@Test
	public void testPopulateNextRound_SingleElimination_Doubles() {
		playerMocks = PlayerMocks.getPlayerMocks(20);
		builder = new TournamentBuilderService();
		tournament = builder.buildTournament(FormatType.SINGLE_ELIMINATION, GameType.DOUBLES, playerMocks);
		tournament.setGamesPerMatch(3);
		svc = new TournamentPopulatorService();
		
		// TODO: finish this
	}
	
	//@Test
	public void testPopulateNextRound_SingleElimination_Cutthroat() {
		playerMocks = PlayerMocks.getPlayerMocks(15);
		builder = new TournamentBuilderService();
		tournament = builder.buildTournament(FormatType.SINGLE_ELIMINATION, GameType.CUTTHROAT, playerMocks);
		tournament.setGamesPerMatch(3);
		svc = new TournamentPopulatorService();
		
		// TODO: finish this
	}
	
	//@Test
	public void testPopulateNextRound_DoubleElimination_Singles() {
		playerMocks = PlayerMocks.getPlayerMocks(10);
		builder = new TournamentBuilderService();
		tournament = builder.buildTournament(FormatType.SINGLE_ELIMINATION, GameType.DOUBLES, playerMocks);
		tournament.setGamesPerMatch(3);
		svc = new TournamentPopulatorService();
		
		// TODO: finish this
	}
	
	//@Test
	public void testPopulateNextRound_DoubleElimination_Doubles() {
		playerMocks = PlayerMocks.getPlayerMocks(15);
		builder = new TournamentBuilderService();
		tournament = builder.buildTournament(FormatType.SINGLE_ELIMINATION, GameType.CUTTHROAT, playerMocks);
		tournament.setGamesPerMatch(3);
		svc = new TournamentPopulatorService();
		
		// TODO: finish this
	}
	
	//@Test
	public void testPopulateNextRound_DoubleElimination_Cutthroat() {
		playerMocks = PlayerMocks.getPlayerMocks(20);
		builder = new TournamentBuilderService();
		tournament = builder.buildTournament(FormatType.SINGLE_ELIMINATION, GameType.CUTTHROAT, playerMocks);
		tournament.setGamesPerMatch(3);
		svc = new TournamentPopulatorService();
		
		// TODO: finish this
	}

	/**
	 * Add games to a specified match. The games will not have scores, which indicates the games have not started yet.
	 * Make the specified assertion after this is done.
	 * 
	 * @param gameId
	 * @param roundIdx
	 * @param matchIdx
	 * @param order
	 * @param assertTrue
	 */
	private void assertNextGame(int gameId, int roundIdx, int matchIdx, int order, boolean assertTrue) {
		Match match = tournament.getBrackets().get(0).getRounds().get(roundIdx).getMatches().get(matchIdx);
		Game game = new Game(match);
		game.setId(gameId);
		game.setOrder(order);
		match.addGame(game);
		boolean retval = svc.populateNextRound(tournament, sideSorter);
		assertTrue(assertTrue ? retval : !retval);
	}
	
	/**
	 * Games have already been added to the specified match, so now add scores to a specified game in that match.
	 * Make the specified assertion after this is done.
	 * 
	 * @param roundIdx
	 * @param matchIdx
	 * @param gameIdx
	 * @param score1
	 * @param score2
	 * @param assertTrue
	 */
	private void assertNextGameScore(int roundIdx, int matchIdx, int gameIdx, int score1, int score2, boolean assertTrue) {
		Match match = tournament.getBrackets().get(0).getRounds().get(roundIdx).getMatches().get(matchIdx);
		Game game = match.getGames().get(gameIdx);
		Side side1 = match.getSides().get(0);
		Side side2 = match.getSides().get(1);
		game.addScore(side1, score1);
		game.addScore(side2, score2);
		boolean retval = svc.populateNextRound(tournament, sideSorter);
		assertTrue(assertTrue ? retval : !retval);
	}
}
