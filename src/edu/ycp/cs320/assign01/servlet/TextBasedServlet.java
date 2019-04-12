package edu.ycp.cs320.assign01.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.assign01.controller.GameController;
import edu.ycp.cs320.assign01.model.game.Game;

public class TextBasedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("TextBased Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/textBased.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("TextBased Servlet: doPost");
		
		String errorMessage = null;
		ArrayList<String> output = new ArrayList<String>();
		String input = req.getParameter("input");
		
		// TODO: Sustain the model/model information between actions
		Game model;
		System.out.println(req.getAttribute("game") != null);
		if(req.getAttribute("game") != null)
		 	model = new Game(((Game) req.getAttribute("game")).getPlayer(), ((Game) req.getAttribute("game")).getDungeon());
		else
			model = new Game();
		
		GameController controller = new GameController();
		controller.setModel(model);

		// check for errors in the form data before using is in a calculation
		if (input == null || input.equals("")) {
			errorMessage = "Please specify input";
		}
		// otherwise, data is good, do the calculation
		// must create the controller each time, since it doesn't persist between POSTs
		// the view does not alter data, only controller methods should be used for that
		// thus, always call a controller method to operate on the data
		else {
			controller.actionSet(input);
			output = controller.getGameLog();
		}
		
		// Add parameters as request attributes
		// this creates attributes named "first" and "second for the response, and grabs the
		// values that were originally assigned to the request attributes, also named "first" and "second"
		// they don't have to be named the same, but in this case, since we are passing them back
		// and forth, it's a good idea
		
		req.setAttribute("game", model);
		
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("output", output);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/textBased.jsp").forward(req, resp);
	}
}
