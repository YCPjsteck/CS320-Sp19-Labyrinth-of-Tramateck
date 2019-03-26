package edu.ycp.cs320.assign01.controller;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.Game;

public class GameControllerTest {
	private Game model;
	private GameController controller;
	
	
	@Before
	public void setUp() {
		model = new Game();
		controller = new GameController();
		controller.setModel(model);
	}
	
	@Test
	public void testOutput() {
		controller.actionSet("move north");
		ArrayList<String> log = controller.getGameLogArray();
		assertTrue(log.get(log.size()-1).equals("Can not travel in that direction."));
		
		controller.actionSet("test");
		log = controller.getGameLogArray();
		assertTrue(log.get(log.size()-1).equals("This is not an action."));
	}
}
