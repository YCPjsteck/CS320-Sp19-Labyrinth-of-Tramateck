package edu.ycp.cs320.lab02a_jsteck.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab02a_jsteck.controller.NumbersController;
import edu.ycp.cs320.lab02a_jsteck.model.Numbers;

public class MultiplyNumbersAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doRequest(req, resp);
	}

	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get parameters
		Double first = getDouble(req, "first");
		Double second = getDouble(req, "second");
		
		// Check whether parameters are valid
		if (first == null || second == null) {
			badRequest("Bad parameters", resp);
			return;
		}
		
		// Use a controller to process the request
		Numbers model = new Numbers();
		NumbersController controller = new NumbersController();
		controller.setModel(model);
		controller.setFirst(first);
		controller.setSecond(second);
		// Double result = controller.multiply();
		
		// Send back a response
		//resp.setContentType("text/plain");
		//resp.getWriter().println(result.toString());
		
		resp.setContentType("application/json");
		resp.getWriter().println(
				"{ \"first\": " + model.getFirst() +
				", \"second\": " + model.getSecond() + "}" );
	}

	private Double getDouble(HttpServletRequest req, String name) {
		String val = req.getParameter(name);
		if (val == null) {
			return null;
		}
		try {
			return Double.parseDouble(val);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void badRequest(String message, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		resp.getWriter().println(message);
	}
}
