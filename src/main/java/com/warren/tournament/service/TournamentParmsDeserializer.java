package com.warren.tournament.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.warren.tournament.entity.Member;
import com.warren.tournament.entity.Player;
import com.warren.tournament.enumerator.FormatType;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;

public class TournamentParmsDeserializer extends
		JsonDeserializer<TournamentParms> {

	@Override
	public TournamentParms deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
//		{
//			"format":{"name":"SINGLE_ELIMINATION"},
//			"gameType":{"name":"SINGLES"},
//			"matchingMethod":{"value":"HIGHEST_WITH_LOWEST_RANK"},
//			"playerCount":19,
//			"players":[],
//			"gamesPerMatch":3
//		}
		
		TournamentParms parms = new TournamentParms();
		
		JsonNode node = parser.getCodec().readTree(parser);

		String enumStr = node.findPath("format").findPath("name").textValue();
		FormatType format = FormatType.valueOf(enumStr);
		parms.setFormat(format);
		
		enumStr = node.findPath("gameType").findPath("name").textValue();
		GameType gameType = GameType.valueOf(enumStr);
		parms.setGameType(gameType);
		
		enumStr = node.findPath("matchingMethod").findPath("value").textValue();
		MatchingMethod matchingMethod = MatchingMethod.valueOf(enumStr);
		parms.setMatchingMethod(matchingMethod);
		
		JsonNode playerCountNode = node.findPath("playerCount");
		if(playerCountNode.canConvertToInt()) {
			// We are doing a demo, so players will be constructed automatically, but not persisted.
			Integer playerCount = playerCountNode.intValue();
			
			for(int i=0; i<playerCount; i++) {
				Member m = new Member();
				m.setEmail("player" + String.valueOf(i+1) + "@gmail.com");
				m.setFname("fn" + String.valueOf(i+1));
				m.setLname("ln" + String.valueOf(i+1));
				m.setRank(i+1);
				Player p = new Player();
				p.setMember(m);
				parms.addPlayer(p);
			}
		}
		
		JsonNode gamesPerMatchNode = node.findPath("gamesPerMatch");
		if(gamesPerMatchNode.canConvertToInt()) {
			parms.setGamesPerMatch(gamesPerMatchNode.intValue());
		}
		
		return parms;
	}

}
