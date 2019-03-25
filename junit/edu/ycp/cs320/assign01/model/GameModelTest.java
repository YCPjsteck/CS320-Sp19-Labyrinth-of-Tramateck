package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GameModelTest {
	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
	}
	
	@Test
	public void testConstructor() {
		// Test that a dungeon was created and the player
		// can only travel south.
		Location loc = game.getDungeon();
		
		assertTrue(loc.canTravel("south"));
		assertFalse(loc.canTravel("north"));
		assertFalse(loc.canTravel("east"));
		assertFalse(loc.canTravel("west"));
		
		// Test that the player was created and
		// is alive.
		Player play = game.getPlayer();
		assertFalse(play.isDead());
	}
}
