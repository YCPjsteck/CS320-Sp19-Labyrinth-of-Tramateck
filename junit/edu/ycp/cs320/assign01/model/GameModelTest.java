package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.game.Game;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.utility.Library;

public class GameModelTest {
	private Game game;
	private Library library;
	private NPC npc1, npc2, npc3;
	
	@Before
	public void setUp() {
		library = new Library();
		try {
			library.generateItems("test items.txt");
			library.generateNPCs("test npcs.txt");
			library.generateLocations("test locations.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Location> locations = library.getLocations();
		ArrayList<NPC> npcs = library.getNPCs();
		
		Location loc = locations.get(0);
		
		npc1 = npcs.get(0);
		npc2 = npcs.get(1);
		npc3 = npcs.get(2);
		
		game = new Game(new Player(), loc);
	}
	
	@Test
	public void testConstructor() {

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
		Room room = game.getDungeon().getRoom(1);
		room.addNPC(npc1);

		assertTrue(room.getNPCs().get(0).getName().equals("Monkey"));
		
		room = game.getDungeon().getRoom(3);
		assertTrue(room.getNPCs().get(0).getName().equals("Jaguar"));
		
	}
}