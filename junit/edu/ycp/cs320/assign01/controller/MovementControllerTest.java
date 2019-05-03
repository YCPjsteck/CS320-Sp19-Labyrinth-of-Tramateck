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
		game.setPlayer(1);
		game.curLocation().findStart();
		WordFinder finder = new WordFinder();
		
		String output = controller.move("move");
		assertTrue(output.equals("Missing movement target.\n"));
		
		output = controller.move("move south please");
		assertTrue(output.equals("Looks like you typed a bit too much.\n"));

		output = controller.move("move weast");
		assertTrue(output.equals("This is not a movement target.\n"));
		
		output = controller.move("move north");
		assertTrue(output.equals("Can not travel in that direction.\n"));
		
		output = controller.move("move west");
		ArrayList<String> array = finder.findWords(output,"\n");
		assertTrue(array.get(0).equals("Traveled west."));
		assertTrue(array.get(1).contains("Room long:"));
		assertTrue(array.size() == 5);

		output = controller.move("move east");
		assertTrue(output.equals("This room is not clear.\n"));
		
		output = controller.move("run east");
		array = finder.findWords(output,"\n");
		assertTrue(array.get(0).equals("Ran from combat east."));
		assertTrue(array.get(1).contains("Room short:"));
		assertTrue(array.size() == 5);
	}
}
