package com.warren.tournament.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Tournament;
import com.warren.tournament.enumerator.FormatBracket;
import com.warren.tournament.enumerator.GameType;
import com.warren.tournament.enumerator.MatchingMethod;
import com.warren.tournament.service.TournamentService;
import com.warren.tournament.util.Utils;

/**
 * Servlet implementation class Demo
 */
public class Demo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Demo() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("<html>");
		response.getWriter().println("<body leftmargin=50 topmargin=50>");
		response.getWriter().println("<form method='POST' action='demo.htm'>");
		response.getWriter().println("<h1>Single Elimination Bracket</h1>");
		response.getWriter().println("Enter the number of players participating:<br>");
		response.getWriter().println("<select id='count' name='count'>");
		for(int i=2; i<=256; i++) {
			response.getWriter().print("    <option value='" + String.valueOf(i) + "'>");
			response.getWriter().print(String.valueOf(i));
			response.getWriter().println("</option>");
		}
		response.getWriter().println("<input type='submit' value='submit'>");
		response.getWriter().println("</form>");
		response.getWriter().println("</body>");
		response.getWriter().println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int playerCount = Integer.parseInt(request.getParameter("count"));
		TournamentService svc = new TournamentService();
		Set<Player> players = new HashSet<Player>();
		
		for(int i=0; i<playerCount; i++) {
			Player p = new Player();
			p.setEmail("player" + String.valueOf(i+1) + "@gmail.com");
			p.setFname("fn" + String.valueOf(i+1));
			p.setLname("fn" + String.valueOf(i+1));
			p.setRank(i+1);
			players.add(p);
		}
		
		Tournament t = svc.getTournament(
				FormatBracket.SINGLE_ELIMINATION, 
				GameType.SINGLES, 
				MatchingMethod.HIGHEST_WITH_LOWEST_RANK, 
				players);

		String json = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			// 
			/**
			 * TODO: Due to bi-directional references in Bracket class and Round class, the mapper gets stuck in a recursion 
			 * loop when serializing to json. Find out why the @JsonIdentityInfo annotations have not fixed this or, try using
			 * @JsonSerialize, creating a custom serializer for the "many" field in the one-to-many collections.
			 */
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
		} 
		catch (Exception e) {
			json = Utils.stackTraceToString(e);
		}
		
		response.getWriter().println("<html>");
		response.getWriter().println("<body leftmargin=50 topmargin=50>");
		response.getWriter().println("<form method='GET' action='demo.htm'>");
		response.getWriter().print("<textarea cols=100 rows=30>");
		response.getWriter().println(json);
		response.getWriter().println("</textarea><br>");
		response.getWriter().println("<input type='submit' value='go back'>");
		response.getWriter().println("</form>");
		response.getWriter().println("</body>");
		response.getWriter().println("</html>");
	}

}
