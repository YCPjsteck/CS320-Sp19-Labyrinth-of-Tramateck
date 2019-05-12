package edu.ycp.cs320.assign01.controller;

import edu.ycp.cs320.assign01.model.Player;
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
	Player player;
	
	@Before
	public void setup() {
		library = new Library();
		game = library.generateWorld();
		player = new Player();
		controller = new MovementController(game,player);
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

		output = controller.control("move weast");
		System.out.println(output);
		
		output = controller.control("move north");
		System.out.println(output);
		
		output = controller.control("move west");
		System.out.println(output);

		output = controller.control("move east");
		System.out.println(output);
		
		output = controller.control("run east");
		System.out.println(output);
	}
}
