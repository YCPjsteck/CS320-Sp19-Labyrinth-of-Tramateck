package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.game.Game;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;

public class GameModelTest {
	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
	}
	
	@Test
	public void testConstructor() {
		assertTrue(false);
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
	
	
	@Test
	public void testPopulate() {
		// TODO: 
		// 		use actual NPC objects instead of strings
		//		generate from the library class instead of creating a new location to test

		assertTrue(false);
		/*
		int[][] map = {	{1, 2, 0},
						{0, 3, 0},
						{0, 4, 0}};
		
		game.getDungeon().setMap(map);
		game.getDungeon().generateRooms();
		game.getDungeon().setPlayer(1, 0);
		
		ArrayList<String> monsters = new ArrayList<String>();

		monsters.add("skeleton");
		Room room = game.getDungeon().getRoom(1);
		room.populate(monsters);
		monsters.clear();
		
		monsters.add("ghoul");
		monsters.add("zombie");
		room = game.getDungeon().getRoom(2);
		room.populate(monsters);
		monsters.clear();
		
		monsters.add("witch");
		room = game.getDungeon().getRoom(4);
		room.populate(monsters);
		monsters.clear();
		
		assertTrue(game.getDungeon().getRoom(1).getNPCs().contains("skeleton"));
		assertTrue(game.getDungeon().getRoom(2).getNPCs().contains("ghoul"));
		assertTrue(game.getDungeon().getRoom(2).getNPCs().contains("zombie"));
		assertTrue(game.getDungeon().getRoom(4).getNPCs().contains("witch"));
		*/
	}
}
