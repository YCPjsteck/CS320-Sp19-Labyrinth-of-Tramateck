package edu.ycp.cs320.assign01.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Index Servlet: doGet");
		
		req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Index Servlet: doPost");
		
		// See which button the user pressed and send them to the corresponding
		// jsp for that button.
		if(req.getParameter("add") != null) {
			req.getRequestDispatcher("/_view/addNumbers.jsp").forward(req, resp);
		} else if(req.getParameter("text") != null) {
			req.getRequestDispatcher("/_view/textBased.jsp").forward(req, resp);	
		}
	}
}
