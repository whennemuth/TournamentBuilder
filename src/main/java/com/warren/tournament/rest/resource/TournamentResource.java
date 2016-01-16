package com.warren.tournament.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatType;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;
import com.warren.tournament.rest.util.ServiceResponse;
import com.warren.tournament.service.TournamentParms;
import com.warren.tournament.service.TournamentService;


@Path("/tournament")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TournamentResource {

	@GET
	@Path("/{tournamentId}")
	public Response getTournament(@PathParam("tournamentId") Integer tournamentId) {
		Tournament tournament = null;	// TODO: write code to lookup the tournament.
		return Response.status(Status.OK).entity(tournament).build();
	}

	@GET
	@Path("/demo/parms")
	public Response getTournamentDemoParms() {
		return Response.status(Status.OK).entity(new TournamentParms.EnumValues()).build();
	}

	@POST
	@Path("/demo")
	public Response getTournamentDemo(TournamentParms parms) throws Exception {
		TournamentService svc = new TournamentService();		
		Tournament t;
		try {
//			if(true)
//				throw new Exception("hello");
			t = svc.getTournament(parms, true);
			return ServiceResponse.getSuccessInstance(t);
		} 
		catch (Exception e) {
			return ServiceResponse.getErrorInstance(e);
		}
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
