package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class LibraryTest {
	private Library lib;
	
	@Before
	public void setUp() {
		lib = new Library();
	}
	
	@Test
	public void testLocations() {
		try {
			lib.generateLocations();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println();
		System.out.println();
		
		ArrayList<Location> locations = lib.getLocations();
		Location loc = locations.get(0);
		loc.setPlayer(1, 1);
		
		assertTrue(loc.getName().equals("jungle"));
		assertTrue(loc.getMinLevel() == 1);
		assertTrue(loc.getMaxLevel() == 3);
		assertTrue(loc.getType().equals("dangerous"));
		assertTrue(loc.getMap().length == 3);
		assertTrue(loc.getMap()[0].length == 3);
		assertTrue(loc.getRooms().size() == 7);
		
		loc.printMap();
		
		//System.out.println(loc.canTravel("east"));
		loc.travel("east");
		
		loc.printMap();
		Room room = loc.curRoom();
		ArrayList<String> monsters = room.getMonsters();
		System.out.println(monsters.get(0));
		//room.monsterKilled("monkey");
		//System.out.println(monsters.size());
		//System.out.println(loc.canTravel("east"));
		//System.out.println(loc.canTravel("north"));
		//System.out.println(loc.canTravel("south"));
		loc.travel("south");
		
		loc.printMap();
		
		loc = locations.get(1);
		assertTrue(loc.getName().equals("dungeon"));
		assertTrue(loc.getMinLevel() == 3);
		assertTrue(loc.getMaxLevel() == 5);
		assertTrue(loc.getType().equals("dangerous"));
		assertTrue(loc.getMap().length == 5);
		assertTrue(loc.getMap()[0].length == 5);
		assertTrue(loc.getRooms().size() == 14);
	}
}
