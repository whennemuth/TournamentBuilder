package com.warren.tournament.rest.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.warren.tournament.entity.Member;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatType;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;
import com.warren.tournament.service.TournamentParms;
import com.warren.tournament.service.TournamentService;


@Path("/rest/tournament")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TournamentResource {

	@GET
	@Path("/{tournamentId}")
	public Response getTournament(@PathParam("tournamentId") Integer tournamentId) {
		Tournament tournament = null;
		return Response.status(Status.OK).entity(tournament).build();
	}

	@POST
	@Path("/demo")
	public Response getTournamentDemo(TournamentParms parms) {
		
		TournamentService svc = new TournamentService();
		Set<Player> players = new HashSet<Player>();
		
		for(int i=0; i<parms.getPlayers().size(); i++) {
			Member m = new Member();
			m.setEmail("player" + String.valueOf(i+1) + "@gmail.com");
			m.setFname("fn" + String.valueOf(i+1));
			m.setLname("ln" + String.valueOf(i+1));
			m.setRank(i+1);
			Player p = new Player();
			p.setMember(m);
			players.add(p);
		}
		
		parms.setFormat(FormatType.SINGLE_ELIMINATION);
		parms.setGamesPerMatch(3);
		parms.setGameType(GameType.SINGLES);
		parms.setMatchingMethod(MatchingMethod.HIGHEST_WITH_LOWEST_RANK);
		parms.setPlayers(players);
		
		Tournament t = svc.getTournament(parms, true);
		Response r = Response.status(Status.OK).entity(t).build();
		return r;
	}

	@POST
	@Path("/build")
	public Response getTournament(TournamentParms parms) {
		
		TournamentService svc = new TournamentService();
		
		parms.setFormat(FormatType.SINGLE_ELIMINATION);
		parms.setGamesPerMatch(3);
		parms.setGameType(GameType.SINGLES);
		parms.setMatchingMethod(MatchingMethod.HIGHEST_WITH_LOWEST_RANK);
		parms.setPlayers(parms.getPlayers());
		
		Tournament t = svc.getTournament(parms, true);
		Response r = Response.status(Status.OK).entity(t).build();
		return r;
	}
}
