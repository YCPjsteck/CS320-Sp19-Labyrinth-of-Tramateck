package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.utility.Library;

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
		
		assertTrue(loc.getName().equals("Jungle"));
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
		assertTrue(loc.getName().equals("Dungeon"));
		assertTrue(loc.getMinLevel() == 3);
		assertTrue(loc.getMaxLevel() == 5);
		assertTrue(loc.getType().equals("dangerous"));
		assertTrue(loc.getMap().length == 5);
		assertTrue(loc.getMap()[0].length == 5);
		assertTrue(loc.getRooms().size() == 14);
	}
	
	@Test
	public void testNPCs() {
		try {
			lib.generateNPCs();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<NPC> npcList = lib.getNPCs();
		
		assertTrue(npcList.size() == 2);
		assertTrue(npcList.get(0).getName().equals("Monkey"));
		assertTrue(npcList.get(1).getName().equals("Rat"));
	}
	
	@Test
	public void testItems() {
		try {
			lib.generateItems();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Item> itemList = lib.getItems();
		assertTrue(itemList.size() == 3);
		assertTrue(itemList.get(0).getName().equals("Monkey Paw"));
		assertTrue(itemList.get(1).getName().equals("Monkey Tail"));
		assertTrue(itemList.get(2).getName().equals("Monkey Head"));
	}
}
