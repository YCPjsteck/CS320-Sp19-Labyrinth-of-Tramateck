package edu.ycp.cs320.assign01.controller;

import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Library;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MovementControllerTest {
	MovementController controller;
	WorldMap game;
	Library library;
	
	@Before
	public void setup() {
		library = new Library();
		game = library.generateWorld();
		controller = new MovementController(game);
	}
	
	@Test
	public void moveTest() {
		System.out.println("TESTING MOVEMENTCONTROLLER MOVE");
		game.setPlayer(1);
		game.curLocation().findStart();
		WordFinder finder = new WordFinder();
		
		String output = controller.control("move");
		System.out.println(output);
		assertTrue(output.equals("Missing movement target.\n"));
		
		output = controller.control("move south please");
		System.out.println(output);
		assertTrue(output.equals("Looks like you typed a bit too much.\n"));

		output = controller.control("move weast");
		System.out.println(output);
		assertTrue(output.equals("This is not a movement target.\n"));
		
		output = controller.control("move north");
		System.out.println(output);
		assertTrue(output.equals("Can not travel in that direction.\n"));
		
		output = controller.control("move west");
		System.out.println(output);
		ArrayList<String> array = finder.findWords(output,"\n");
		System.out.println(array.get(1));
		assertTrue(array.get(0).equalsIgnoreCase("Traveled west."));
		assertTrue(array.get(1).contains("room long:"));
		assertTrue(array.size() == 5);

		output = controller.control("move east");
		System.out.println(output);
		assertTrue(output.equals("This room is not clear.\n"));
		
		output = controller.control("run east");
		System.out.println(output);
		array = finder.findWords(output,"\n");
		assertTrue(array.get(0).equalsIgnoreCase("Ran from combat east."));
		assertTrue(array.get(1).toLowerCase().contains("room short:"));
		assertTrue(array.size() == 5);
	}
}
