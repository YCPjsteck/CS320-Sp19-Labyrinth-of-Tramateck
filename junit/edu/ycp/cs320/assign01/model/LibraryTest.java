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
	public void testItemGeneration() {
		try {
			lib.generateItems("items.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Item> itemList = lib.getItems();
		assertTrue(itemList.size() == 3);
		
		assertTrue(itemList.get(0).getName().equals("Monkey Paw"));
		assertTrue(itemList.get(1).getName().equals("Monkey Tail"));
		assertTrue(itemList.get(2).getName().equals("Monkey Head"));
		
		assertTrue(itemList.get(0).getRarity().equals("common"));
		assertTrue(itemList.get(1).getRarity().equals("rare"));
		assertTrue(itemList.get(2).getRarity().equals("legendary"));
		
		assertTrue(itemList.get(0).getWeight() == 1);
		assertTrue(itemList.get(1).getWeight() == 2);
		assertTrue(itemList.get(2).getWeight() == 5);

		assertTrue(itemList.get(0).getWorth() == 10);
		assertTrue(itemList.get(1).getWorth() == 50);
		assertTrue(itemList.get(2).getWorth() == 1000);
	}
	
	@Test
	public void testNpcGeneration() {
		try {
			lib.generateItems("items.txt");
			lib.generateNPCs("npcs.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<NPC> npcList = lib.getNPCs();
		
		assertTrue(npcList.size() == 2);
		assertTrue(npcList.get(0).getName().equals("Monkey"));
		assertTrue(npcList.get(1).getName().equals("Rat"));

		assertTrue(npcList.get(0).getBaseHealth() == 10);
		assertTrue(npcList.get(0).getMinAttack() == 1);
		assertTrue(npcList.get(0).getMaxAttack() == 2);
		
		assertTrue(npcList.get(0).getAllLoot().get(0).getLeft().getName().equalsIgnoreCase("Monkey Paw"));
		assertTrue(npcList.get(0).getAllLoot().get(0).getMiddle() == 10);
		assertTrue(npcList.get(0).getAllLoot().get(0).getRight() == 4);
		
		assertTrue(npcList.get(1).getBaseHealth() == 100);
		assertTrue(npcList.get(1).getMinAttack() == 0);
		assertTrue(npcList.get(1).getMaxAttack() == 1);
	}
	
	@Test
	public void testLocationGeneration() {
		try {
			lib.generateItems("items.txt");
			lib.generateNPCs("npcs.txt");
			lib.generateLocations("locations.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Location> locations = lib.getLocations();
		
		Location loc = locations.get(0);
		assertTrue(loc.getName().equals("Jungle"));
		assertTrue(loc.getMinLevel() == 1);
		assertTrue(loc.getMaxLevel() == 3);
		assertTrue(loc.getType().equals("dangerous"));
		assertTrue(loc.getMap().length == 3);
		assertTrue(loc.getMap()[0].length == 3);
		assertTrue(loc.getRooms().size() == 7);
		loc.printMap();
		
		System.out.println();
		
		loc = locations.get(1);
		assertTrue(loc.getName().equals("Dungeon"));
		assertTrue(loc.getMinLevel() == 3);
		assertTrue(loc.getMaxLevel() == 5);
		assertTrue(loc.getType().equals("dangerous"));
		assertTrue(loc.getMap().length == 5);
		assertTrue(loc.getMap()[0].length == 5);
		assertTrue(loc.getRooms().size() == 14);
		loc.printMap();
	}
	
	@Test
	public void testEventGeneration() {
		try {
			lib.generateItems("events.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Event> eventList = lib.getEvents();
		assertTrue(eventList.size() == 1);
		
		assertTrue(eventList.get(0).getId() == 1);
		
		assertTrue(eventList.get(0).getPrompt().equals("You enter a dark smelly room, the floor is flooded and brooding%a. Search through water.%b. Scan the walls for information."));
		
		

	}
}
