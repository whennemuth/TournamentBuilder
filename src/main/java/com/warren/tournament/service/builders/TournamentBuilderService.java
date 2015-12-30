package com.warren.tournament.service.builders;

import java.util.Set;

import com.warren.tournament.api.BracketBuilder;
import com.warren.tournament.api.MatchBuilder;
import com.warren.tournament.api.RoundBuilder;
import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatType;
import com.warren.tournament.enumerator.GameType;

public class TournamentBuilderService {

	/**
	 * TODO: Make builders not take a Player set, but a player count. Have the builders make objects without players.
	 * Then create populators and pass those the player lists.
	 * 
	 * @param format
	 * @param gameType
	 * @param playerMocks
	 * @return
	 */
	public Tournament buildTournament(FormatType format, GameType gameType, Set<Player> players) {
		Tournament tournament = new Tournament();
		tournament.setFormatType(format);
		tournament.setGameType(gameType);
		tournament.setPlayers(players);
		
		if(format.equals(FormatType.SINGLE_ELIMINATION)) {
			
			BracketBuilder bracketBuilder = new AbstractBracketBuilder(){};
			RoundBuilder roundBuilder = new BasicRoundBuilder();
			MatchBuilder matchBuilder = new BasicMatchBuilder();
				
			Bracket bracket = bracketBuilder.buildNextBracket(tournament, roundBuilder, matchBuilder, gameType);
			tournament.addBracket(bracket);
		}
		if(format.equals(FormatType.DOUBLE_ELIMINATION)) {
			
			BracketBuilder bracketBuilder = new AbstractBracketBuilder(){};
			RoundBuilder roundBuilder = new BasicRoundBuilder();
			MatchBuilder matchBuilder = new BasicMatchBuilder();
				
			Bracket startBracket = null;	// RESUME NEXT
			Bracket loserBracket = null;	// RESUME NEXT
			tournament.addBracket(startBracket);
			tournament.addBracket(loserBracket);
		}
		if(format.equals(FormatType.CLASSIC_PLATE_DROPDOWN)) {
		}
		if(format.equals(FormatType.CONSOLATION)) {
		}
		if(format.equals(FormatType.FEED_IN_DROPDOWN)) {
		}
		if(format.equals(FormatType.FEED_IN_WITH_CONSOLATION)) {
		}
		if(format.equals(FormatType.OLYMPIC_FORMAT_DIVISION)) {
		}
		if(format.equals(FormatType.QUALIFIER_DRAW)) {
		}
		if(format.equals(FormatType.TRIPLE_KNOCKOUT)) {
		}
		if(format.equals(FormatType.ROUND_ROBIN)) {
		}
		if(format.equals(FormatType.DOUBLE_ROUND_ROBIN)) {
		}
		if(format.equals(FormatType.TRIPLE_ROUND_ROBIN)) {
		}
		return tournament;
	}
}
