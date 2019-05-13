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
			lib.generateItems("test items.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Item> itemList = lib.getItems();

		assertTrue(itemList.size() == 8);
		
		assertTrue(itemList.get(0).getName().equals("Monkey Paw"));
		assertTrue(itemList.get(1).getName().equals("Monkey Tail"));
		assertTrue(itemList.get(2).getName().equals("Monkey Head"));
		
		assertTrue(itemList.get(0).getRarity().getString().equals("common"));
		assertTrue(itemList.get(1).getRarity().getString().equals("rare"));
		assertTrue(itemList.get(2).getRarity().getString().equals("legendary"));
		
		assertTrue(itemList.get(0).getWeight() == 1);
		assertTrue(itemList.get(1).getWeight() == 2);
		assertTrue(itemList.get(2).getWeight() == 5);

		assertTrue(itemList.get(0).getWorth() == 10);
		assertTrue(itemList.get(1).getWorth() == 50);
		assertTrue(itemList.get(2).getWorth() == 1000);
		
		assertTrue(itemList.get(3).getName().equalsIgnoreCase("Laser Pistol"));
		assertTrue(itemList.get(4).getName().equalsIgnoreCase("Battery Pack"));
		assertTrue(itemList.get(5).getName().equalsIgnoreCase("Personal Shield"));
		assertTrue(itemList.get(6).getName().equalsIgnoreCase("Medkit"));
		
		assertTrue(((Equipment)itemList.get(3)).getStrength() == 1);
		assertTrue(((Equipment)itemList.get(3)).getQuality() == 0);
	}
	
	@Test
	public void testNpcGeneration() {
		try {
			lib.generateItems("test items.txt");
			lib.generateNPCs("test npcs.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<NPC> npcList = lib.getNPCs();
		
		assertTrue(npcList.size() == 3);
		assertTrue(npcList.get(0).getName().equals("Monkey"));
		assertTrue(npcList.get(1).getName().equals("Jaguar"));
		assertTrue(npcList.get(2).getName().equals("Rat"));

		assertTrue(npcList.get(0).getType().getString().equalsIgnoreCase("hostile"));
		assertTrue(npcList.get(0).getBaseHealth() == 5);
		assertTrue(npcList.get(0).getMinAttack() == 1);
		assertTrue(npcList.get(0).getMaxAttack() == 2);
		
		assertTrue(npcList.get(0).getAllLoot().get(0).getLeft().getName().equalsIgnoreCase("Monkey Paw"));
		assertTrue(npcList.get(0).getAllLoot().get(0).getMiddle() == 50);
		assertTrue(npcList.get(0).getAllLoot().get(0).getRight() == 4);
		
		assertTrue(npcList.get(2).getType().getString().equalsIgnoreCase("friendly"));
		assertTrue(npcList.get(2).getBaseHealth() == 100);
		assertTrue(npcList.get(2).getMinAttack() == 0);
		assertTrue(npcList.get(2).getMaxAttack() == 1);
	}
	
	@Test
	public void testLocationGeneration() {
		try {
			lib.generateItems("test items.txt");
			lib.generateNPCs("test npcs.txt");
			lib.generateLocations("test locations.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Location> locations = lib.getLocations();
		
		Location loc = locations.get(0);
		assertTrue(loc.getName().equals("Jungle"));
		assertTrue(loc.getMinLevel() == 1);
		assertTrue(loc.getMaxLevel() == 3);
		assertTrue(loc.getType().getString().equals("dangerous"));
		assertTrue(loc.getMap().length == 3);
		assertTrue(loc.getMap()[0].length == 3);
		assertTrue(loc.getRooms().size() == 7);
		loc.findStart();
		loc.printMap();
		assertTrue(loc.canTravel("west"));
		loc.travel("west");
		loc.curRoom().isEntered();
		System.out.println();
		loc.printMap();
		
		System.out.println();
		
		loc = locations.get(1);
		assertTrue(loc.getName().equals("Dungeon"));
		assertTrue(loc.getMinLevel() == 3);
		assertTrue(loc.getMaxLevel() == 5);
		assertTrue(loc.getType().getString().equals("dangerous"));
		assertTrue(loc.getMap().length == 5);
		assertTrue(loc.getMap()[0].length == 5);
		assertTrue(loc.getRooms().size() == 14);
		loc.findStart();
		loc.printMap();
		assertTrue(loc.canTravel("north"));
		loc.travel("north");
		loc.curRoom().isEntered();
		System.out.println();
		loc.printMap();
		assertTrue(loc.canTravel("north"));
		loc.travel("north");
		loc.curRoom().isEntered();
		System.out.println();
		loc.printMap();
	}
	
	@Test
	public void testEventGeneration() {
		try {
			lib.generateEvents("events.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Event> eventList = lib.getEvents();
		assertTrue(eventList.size() == 3);
		
		//TEST 1
		assertTrue(eventList.get(0).getId() == 1);
		assertTrue(eventList.get(0).getPrompt().equals("You enter a dark smelly room, the floor is flooded and brooding%a. Search through water.%b. Scan the walls for information."));
		assertTrue(eventList.get(0).getAPassLog().equals("You stumble across a plug to a drain.% You pull the plug and the water drains revealing precious stones!"));
		assertTrue(eventList.get(0).getAFailLog().equals("Your search appears to be empty handed.% You pull your hand from the water revealing leeches."));
		assertTrue(eventList.get(0).getBPassLog().equals("Dusting the walls reveals an alien text.% You recognize the dialect and a discover a powerful mantra.% Strength increased by 2!"));
		assertTrue(eventList.get(0).getBFailLog().equals("You scan the walls uncovering unintelligible runes."));
		
		assertTrue(eventList.get(0).getAReadPair().getLeft() == 3);
		assertTrue(eventList.get(0).getAReadPair().getRight() == 10);
		
		
		assertTrue(eventList.get(0).getAPassPair().getLeft() == 4);
		assertTrue(eventList.get(0).getAPassPair().getRight() == 0);
		
		assertTrue(eventList.get(0).getAFailPair().getLeft() == 0);
		assertTrue(eventList.get(0).getAFailPair().getRight() == -10);
		
		assertTrue(eventList.get(0).getBReadPair().getLeft() == 2);
		assertTrue(eventList.get(0).getBReadPair().getRight() == 10);
		
		assertTrue(eventList.get(0).getBPassPair().getLeft() == 1);
		assertTrue(eventList.get(0).getBPassPair().getRight() == 2);
		
		assertTrue(eventList.get(0).getBFailPair().getLeft() == 0);
		assertTrue(eventList.get(0).getBFailPair().getRight() == 0);
		
		//TEST 2
		assertTrue(eventList.get(1).getId() == 2);
		assertTrue(eventList.get(1).getPrompt().equals("this is a prompt"));
		assertTrue(eventList.get(1).getAPassLog().equals("this is an aPassLog"));
		assertTrue(eventList.get(1).getAFailLog().equals("this is an aFailLog"));
		assertTrue(eventList.get(1).getBPassLog().equals("this is a bPassLog"));
		assertTrue(eventList.get(1).getBFailLog().equals("this is a bFailLog"));
		
		assertTrue(eventList.get(1).getAReadPair().getLeft() == 1);
		assertTrue(eventList.get(1).getAReadPair().getRight() == 2);
		
		assertTrue(eventList.get(1).getAPassPair().getLeft() == 3);
		assertTrue(eventList.get(1).getAPassPair().getRight() == 4);
		
		assertTrue(eventList.get(1).getAFailPair().getLeft() == 5);
		assertTrue(eventList.get(1).getAFailPair().getRight() == 6);
		
		assertTrue(eventList.get(1).getBReadPair().getLeft() == 7);
		assertTrue(eventList.get(1).getBReadPair().getRight() == 8);
		
		assertTrue(eventList.get(1).getBPassPair().getLeft() == 9);
		assertTrue(eventList.get(1).getBPassPair().getRight() == 10);
		
		assertTrue(eventList.get(1).getBFailPair().getLeft() == 11);
	}
}
