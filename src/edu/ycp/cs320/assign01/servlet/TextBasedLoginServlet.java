package edu.ycp.cs320.assign01.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.assign01.controller.LoginController;
import edu.ycp.cs320.assign01.controller.MetaController;
import edu.ycp.cs320.assign01.db.DerbyDatabase;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.game.Game;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class TextBasedLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DerbyDatabase db;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("TextBasedLogin Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/textBasedLogin.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("TextBasedLogin Servlet: doPost");
		
		ArrayList<String> output = new ArrayList<String>();
		String input = req.getParameter("input");
		LoginController loginCon = new LoginController();

		// check for errors in the form data before using is in a calculation
		if (input == null || input.equals("")) {
			output.add("Please specify input");
		}
		else if (input.contains("login")) {
			WordFinder finder = new WordFinder();
			ArrayList <String> words = finder.findWords(input);
			if(words.size() >= 3 && words.get(0).equals("login")) {
				String username = words.get(1);
				String password = words.get(2);
				
				boolean validLogin = loginCon.validateCredentials(username, password);
				
				if (!validLogin) {
					output.add("Username and/or password invalid");
				} else {
					System.out.println("   Valid login - starting session");

					// store user object in session
					req.getSession().setAttribute("user", username);
					req.getSession().setAttribute("player", db.findAccountByUsername(username).getPlayer(0).getId());
					
					resp.sendRedirect(req.getContextPath() + "/textBased");

					return;
				}
				output.add("Invalid login");
			}
			else {
				output.add("You must specify username and password.");
			}
		}
		// otherwise, data is good, do the calculation
		// must create the controller each time, since it doesn't persist between POSTs
		// the view does not alter data, only controller methods should be used for that
		// thus, always call a controller method to operate on the data
		else {
			output.add("You must login first");
		}
		
		// Add parameters as request attributes
		// this creates attributes named "first" and "second for the response, and grabs the
		// values that were originally assigned to the request attributes, also named "first" and "second"
		// they don't have to be named the same, but in this case, since we are passing them back
		// and forth, it's a good idea
		
		//req.setAttribute("game", model);
		
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("output", output);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/textBasedLogin.jsp").forward(req, resp);
	}
}
