package edu.ycp.cs320.assign01.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.assign01.controller.MetaController;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.game.Game;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

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
		
		Game model = new Game();
		String playerStr = req.getParameter("playerStr");
		String roomStr = req.getParameter("roomStr");
		String npcStr = req.getParameter("npcStr");
		String eventStr = req.getParameter("eventStr");
		System.out.println(playerStr);
		System.out.println(roomStr);
		System.out.println(npcStr);
		System.out.println(eventStr);
		ArrayList<String> stringified = new ArrayList<String>();
		stringified.add(playerStr);
		stringified.add(roomStr);
		stringified.add(npcStr);
		stringified.add(eventStr);
		if(playerStr.equals("")) {
			Player player = model.getPlayer();
			ArrayList<Item> items = model.getItems();
			player.addItem(items.get(3));
			player.addItem(items.get(4));
			player.addItem(items.get(5));
			player.addItem(items.get(6),3);
			
			WorldMap world = model.getWorld();
			world.setPlayer(1);
			world.curLocation().start();
			world.curLocation().curRoom().isEntered();

			world.grantAccess(1);
			world.grantAccess(2);
			
			output.add(world.curLocation().curRoom().getLongDesc());
			output.addAll(world.curLocation().getMapArray());
		} else {
			model.reconstruct(stringified);
		}
		
		MetaController controller = new MetaController(model.getWorld(), model.getPlayer(), model.getItems());

		// check for errors in the form data before using is in a calculation
		if (input == null || input.equals("")) {
			errorMessage = "Please specify input";
		}
		// otherwise, data is good, do the calculation
		// must create the controller each time, since it doesn't persist between POSTs
		// the view does not alter data, only controller methods should be used for that
		// thus, always call a controller method to operate on the data
		else {
			WordFinder finder = new WordFinder();
			String temp = controller.control(input);
			output = finder.findWords(temp, "\n");
		}
		
		// Add parameters as request attributes
		// this creates attributes named "first" and "second for the response, and grabs the
		// values that were originally assigned to the request attributes, also named "first" and "second"
		// they don't have to be named the same, but in this case, since we are passing them back
		// and forth, it's a good idea
		
		//req.setAttribute("game", model);
		stringified = model.stringify();
		req.setAttribute("playerStr", stringified.get(0));
		req.setAttribute("roomStr", stringified.get(1));
		req.setAttribute("npcStr", stringified.get(2));
		req.setAttribute("eventStr", stringified.get(3));
		
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("output", output);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/textBased.jsp").forward(req, resp);
	}
}
