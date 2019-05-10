package edu.ycp.cs320.assign01.model.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import edu.ycp.cs320.assign01.model.Event;
import edu.ycp.cs320.assign01.model.Consumable;
import edu.ycp.cs320.assign01.model.Equipment;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.movement.WorldMap;

public class Library {
	
	private ArrayList<Location> locationList; // locations.txt
	private ArrayList<NPC> npcList; // npcs.txt
	private ArrayList<Item> itemList; // items.txt
	ArrayList<Event> eventList; // events.txt
	
	public Library() {
		locationList = new ArrayList<Location>();
		npcList = new ArrayList<NPC>();
		itemList = new ArrayList<Item>();
		eventList = new ArrayList<Event>();
	}

	/**
	 * @return an arraylist of all the locations
	 */
	public ArrayList<Location> getLocations() {
		return locationList;
	}
	/**
	 * @return an arraylist of all the NPCs
	 */
	public ArrayList<NPC> getNPCs() {
		return npcList;
	}
	/**
	 * @return an arraylist of all the items
	 */
	public ArrayList<Item> getItems() {
		return itemList;
	}
	/**
	 * @return an arraylist of all the events
	 */
	public ArrayList<Event> getEvents() {
		return eventList;
	}
	
	/**
	 * Find and return an NPC given its name/
	 */
	public NPC findNPC(String name) {
		for(NPC n : npcList) {
			if(n.getName().equalsIgnoreCase(name))
				return n;
		}
		return null;
	}

	/**
	 * Find and return an item given its name/
	 */
	public Item findItem(String name) {
		for(Item i : itemList) {
			if(i.getName().equalsIgnoreCase(name))
				return i;
		}
		return null;
	}
	
	/**
	 * Find and return an event given its id
	 */
	public Event findEvent(int id) {
		for (Event e : eventList) {
			if (e.getId() == id)
				return e;
		}
		return null;
	}
	
	/**
	 * Generate and return the entire game world.
	 */
	public WorldMap generateWorld() {
		WorldMap map = new WorldMap();
		
		try {
			generateItems("test items.txt");
			generateNPCs("test npcs.txt");
			generateEvents("events.txt");
			generateLocations("test locations.txt");
		} 
		catch (FileNotFoundException e) { 
			e.printStackTrace();
		}
		map.addLocations(locationList);
		
		return map;
	}

	/**
	 * Read the given file to generate the game's locations.
	 */
	public void generateLocations(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		WordFinder finder = new WordFinder();
		int locID = 1; // Location ID
		int min = 0; // Min level
		int max = 0; // Max level
		
		while(reader.hasNext()) {
			String str = reader.nextLine().trim(); // Read the current line
			ArrayList<String> words = finder.findWords(str); // Divide the current line into words
			
			if(words.get(0).equalsIgnoreCase("location")) { // If the first word is "location"
				Location loc = new Location(); // Create a new location
				loc.setName(str.substring(words.get(0).length()).trim()); // Set the location name to the rest of the string
				loc.setId(locID); // Set its ID number
				locID++; // Increment the locID for the next location
				int npcID = 1; // Create an npcID starting at 1
				str = reader.nextLine().trim();
				while(!str.equalsIgnoreCase("")) { // Begin reading the rest of the lines to finish making this location
					words = finder.findWords(str); 
					if(words.get(0).equalsIgnoreCase("level")) { // If the first word is "level"
						// Get the min and max levels and set them
						min = Integer.parseInt(words.get(1));
						max = Integer.parseInt(words.get(2));
						loc.setMinLevel(min);
						loc.setMaxLevel(max);
					} else if(words.get(0).equalsIgnoreCase("type")) { // If the first word is "type"
						loc.setType(words.get(1));
					} else if(words.get(0).equalsIgnoreCase("map")) { // If the first word is "map"
						// Get the x and y size of this map
						int x = Integer.parseInt(words.get(1));
						int y = Integer.parseInt(words.get(2));
						// Pass this information to the generateLocMap method to generate the 2D array for the room map
						loc.setMap(generateLocMap(reader, x, y));
					} else if(words.get(0).equalsIgnoreCase("room")) { // If the first word is "room"
						// Pass everything to the generateRoom method to create the room.
						loc.addRoom(generateRoom(reader, loc, npcID, min, max));
					} else if(words.get(0).equalsIgnoreCase("long")) {
						loc.setLongDesc(str.substring(words.get(0).length()).trim());
					} else if(words.get(0).equalsIgnoreCase("short")) {
						loc.setShortDesc(str.substring(words.get(0).length()).trim());
					}
					// Get the next line
					if(reader.hasNext())
						str = reader.nextLine().trim();
					else
						str = "";
				}
			locationList.add(loc); // Add the finished location to the list
			}
		}
		reader.close();
	}
	
	/**
	 * Create a 2D array of ints of the given size, using the scanner as input.
	 */
	private int[][] generateLocMap(Scanner reader, int x, int y) {
		int[][] map = new int[x][y]; // Create a 2D array of the specified size
		int id = 1; // Start counting the room IDs at 1
		
		// Read the strings from the scanner
		for(int i = 0; i < y; i++) {
			String str = reader.nextLine().trim();
			for(int j = 0; j < str.length(); j++) {
				char c = str.charAt(j);
				if(c == 'o') { // If the current character is 'o'
					map[j][i] = id; // Give this room an ID number
					id++; // Increment the ID number counter
				}
			}
		}
		return map;
	}

	/**
	 * Create a Room, using the scanner as input.
	 */
	private Room generateRoom(Scanner reader, Location loc, int id, int min, int max) {
		WordFinder finder = new WordFinder();
		Room room = new Room();

		String str = reader.nextLine().trim();
		while(!str.equalsIgnoreCase("")) {
			ArrayList<String> words = finder.findWords(str);
			if(words.get(0).equalsIgnoreCase("coord")) { // If the first word is "coord"
				// Get the x and y coordinates for this room
				int x = Integer.parseInt(words.get(1));
				int y = Integer.parseInt(words.get(2));
				// Look in the location's room map for this room's ID number
				int ID = loc.getMap()[x][y];
				// Set the ID number
				room.setId(ID);
			} else if(words.get(0).equalsIgnoreCase("start")) { // If the first word is "start"
				room.isStart();
			} else if(words.get(0).equalsIgnoreCase("exit")) { // If the first word is "exit"
				room.isExit();
			} else if(words.get(0).equalsIgnoreCase("npc")) { // if the first word is "npc"
				NPC npc = new NPC(findNPC(words.get(1))); // Copy the requested NPC into a new NPC object
				
				// Set this NPC's ID and level, then calculate its health
				npc.setId(id);
				id++;
				Random rand = new Random();
				npc.setLevel(rand.nextInt(max-min) + min);
				npc.calHealth();
				
				// Add the NPC to the room
				room.addNPC(npc);
			} else if(words.get(0).equalsIgnoreCase("event")) { // If the first word is "event"
				room.addEvent(findEvent(Integer.parseInt(words.get(1))));
			} else if(words.get(0).equalsIgnoreCase("long")) {
				room.setLongDesc(str.substring(words.get(0).length()).trim());
			} else if(words.get(0).equalsIgnoreCase("short")) {
				room.setShortDesc(str.substring(words.get(0).length()).trim());
			}
			// Get the next line
			if(reader.hasNext())
				str = reader.nextLine().trim();
			else
				str = "";
		}
		return room; // Return the finished room
	}
	
	/**
	 * Read the given file to generate the game's NPCs.
	 */
	public void generateNPCs(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		WordFinder finder = new WordFinder();
		
		while(reader.hasNext()) {
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			if(words.get(0).equalsIgnoreCase("npc")) { // If the first word is NPC
				NPC npc = new NPC(); // Create a new NPC
				npc.setName(str.substring(words.get(0).length()).trim()); // Set the rest of the string to the name
				str = reader.nextLine().trim();
				while(!str.equalsIgnoreCase("")) { // Begin reading the rest of the lines to finish making this NPC
					words = finder.findWords(str);
					if(words.get(0).equalsIgnoreCase("type")) { // If the first word is "type"
						npc.setType(words.get(1));
					} else if(words.get(0).equalsIgnoreCase("health")) { // If the first word is "health"
						npc.setHealth(Integer.parseInt(words.get(1)));
					} else if(words.get(0).equalsIgnoreCase("attack")) { // If the first word is "attack"
						npc.setMinAttack(Integer.parseInt(words.get(1)));
						npc.setMaxAttack(Integer.parseInt(words.get(2)));
					} else if(words.get(0).equalsIgnoreCase("loot")) { // If the first word is "loot"
						int chance = Integer.parseInt(words.get(1));
						int quantity = Integer.parseInt(words.get(2));
						// Find the item that this NPC needs
						Item item = findItem(str.substring(str.indexOf("|") + 1).trim());
						npc.addLoot(item, chance, quantity);
					} else if(words.get(0).equalsIgnoreCase("inventory")) { // If the first word is "inventory"
						int chance = Integer.parseInt(words.get(1));
						int quantity = Integer.parseInt(words.get(2));
						// Find the item that this NPC needs
						Item item = findItem(str.substring(str.indexOf("|") + 1).trim());
						npc.addInventory(item, chance, quantity);
					} else if(words.get(0).equalsIgnoreCase("part")) { // If the first word is "part"
						npc.setPart(words.get(1));
					} else if(words.get(0).equalsIgnoreCase("weakness")) { // If the first word is "weakness"
						npc.setWeakness(words.get(1));
					} else if(words.get(0).equalsIgnoreCase("long")) {
						npc.setLongDesc(str.substring(words.get(0).length()).trim());
					} else if(words.get(0).equalsIgnoreCase("short")) {
						npc.setShortDesc(str.substring(words.get(0).length()).trim());
					}
					
					// Read the next line
					if(reader.hasNext())
						str = reader.nextLine().trim();
					else
						str = "";
				}
				npcList.add(npc); // Add the finished NPC to the list
			}
		}
		reader.close();
	}
	
	/**
	 * Read the given file to generate the game's items.
	 */
	public void generateItems(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		WordFinder finder = new WordFinder();
		int id = 1;
		
		while(reader.hasNext()) {
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			// Find if this item is a generic item, a consumable, or an equipment.
			// If so, pass the information on to the necessary helper method.
			if(words.get(0).equalsIgnoreCase("item")) {
				itemGeneration(reader,str,finder,words,id);
			} else if(words.get(0).equalsIgnoreCase("consumable")) {
				consumableGeneration(reader,str,finder,words,id);
			} else if(words.get(0).equalsIgnoreCase("equipment")) {
				equipmentGeneration(reader,str,finder,words,id);
			}
		}
		reader.close();
	}
	
	public void generateEvents(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		WordFinder finder = new WordFinder();
		Event event;
		
		while(reader.hasNext()) {
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			str = reader.nextLine();
			
			if(words.get(0).equals("event")) {
				event = new Event();
				event.setId(Integer.parseInt(words.get(1)));	//Sets event ID to organize events
				
				while(!str.equalsIgnoreCase("")) {
					words = finder.findWords(str);
					if(words.get(0).equals("prompt")) {
						event.setPrompt(str.substring(words.get(0).length()).trim());		//Sets event Prompt
					} else if(words.get(0).equals("apasslog")) {
						event.setAPassLog(str.substring(words.get(0).length()).trim());		//Sets event A Pass Log
					} else if(words.get(0).equals("afaillog")) {
						event.setaFailLog(str.substring(words.get(0).length()).trim());;	//Sets event A Fail Log
					} else if(words.get(0).equals("bpasslog")) {
						event.setBPassLog(str.substring(words.get(0).length()).trim());		//Sets event B Pass Log
					} else if(words.get(0).equals("bfaillog")) {
						event.setBFailLog(str.substring(words.get(0).length()).trim());		//Sets event A Fail Log
					} else if (words.get(0).equals("areadpair")) {
						event.setAReadPair(Integer.parseInt(words.get(1)), Integer.parseInt(words.get(2)));	//Sets A Read Pair
					} else if (words.get(0).equals("apasspair")) {
						event.setAPassPair(Integer.parseInt(words.get(1)), Integer.parseInt(words.get(2)));	//Sets A Pass Pair
					} else if (words.get(0).equals("afailpair")) {
						event.setAFailPair(Integer.parseInt(words.get(1)), Integer.parseInt(words.get(2)));	//Sets A Fail Pair
					} else if (words.get(0).equals("breadpair")) {
						event.setBReadPair(Integer.parseInt(words.get(1)), Integer.parseInt(words.get(2)));	//Sets B Read Pair
					} else if (words.get(0).equals("bpasspair")) {
						event.setBPassPair(Integer.parseInt(words.get(1)), Integer.parseInt(words.get(2)));	//Sets B Pass Pair
					} else if (words.get(0).equals("bfailpair")) {
						event.setBFailPair(Integer.parseInt(words.get(1)), Integer.parseInt(words.get(2)));	//Sets B Fail Pair
					} else if (words.get(0).equals("repeat")) {
						event.setRepeatable(true);
					}
					
					if(reader.hasNext())
						str = reader.nextLine().trim();
					else
						str = "";
				}
				eventList.add(event);
			}
		}
		
		reader.close();
	}
	
	/**
	 * Generate a basic item.
	 */
	private void itemGeneration(Scanner reader, String str, WordFinder finder, ArrayList<String> words, int id) {
		Item item = new Item(); // Create a new item
		item.setName(str.substring(words.get(0).length()).trim()); // Read the rest of the string for this item's name
		str = reader.nextLine();
		while(!str.equalsIgnoreCase("")) {
			words = finder.findWords(str);
			itemCheck(item,str,words); // Pass the necessary information onto the itme check

			// Read the next line
			if(reader.hasNext())
				str = reader.nextLine().trim();
			else
				str = "";
		}
		// Set this item's ID and add it to the list.
		item.setId(id);
		id++;
		itemList.add(item);
	}
	
	/**
	 * Generate a consumable item.
	 */
	private void consumableGeneration(Scanner reader, String str, WordFinder finder, ArrayList<String> words, int id) {
		Consumable item = new Consumable(); // Create a new consumable
		item.setName(str.substring(words.get(0).length()).trim()); // Read the rest of the string for this item's name
		str = reader.nextLine();
		while(!str.equalsIgnoreCase("")) {
			words = finder.findWords(str);
			itemCheck(item,str,words); // Pass the necessary information onto the itme check
			consumableCheck(item,str,words); // Then pass it to the consumable check

			// Read the next line
			if(reader.hasNext())
				str = reader.nextLine().trim();
			else
				str = "";
		}
		// Set this item's ID and add it to the list.
		item.setId(id);
		id++;
		itemList.add(item);
	}
	
	/**
	 * Generate an equipment item.
	 */
	private void equipmentGeneration(Scanner reader, String str, WordFinder finder, ArrayList<String> words, int id) {
		Equipment item = new Equipment(); // Create a new equipment
		item.setName(str.substring(words.get(0).length()).trim()); // Read the rest of the string for this item's name
		str = reader.nextLine();
		while(!str.equalsIgnoreCase("")) {
			words = finder.findWords(str);
			itemCheck(item,str,words); // Pass the necessary information onto the itme check
			equipmentCheck(item,str,words); // Then pass it to the equipment check
			
			// Read the next line
			if(reader.hasNext())
				str = reader.nextLine().trim();
			else
				str = "";
		}
		// Set this item's ID and add it to the list.
		item.setId(id);
		id++;
		itemList.add(item);
	}
	
	/**
	 * Check for the keywords of all items.
	 */
	private void itemCheck(Item item, String str, ArrayList<String> words) {
		if(words.get(0).equalsIgnoreCase("type")) {
			item.setType(words.get(1));
		} else if(words.get(0).equalsIgnoreCase("rarity")) {
			item.setRarity(words.get(1));
		} else if(words.get(0).equalsIgnoreCase("worth")) {
			item.setWorth(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("weight")) {
			item.setWeight(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("short")) {
			item.setShortDesc(str.substring(words.get(0).length()).trim());
		} else if(words.get(0).equalsIgnoreCase("long")) {
			item.setLongDesc(str.substring(words.get(0).length()).trim());
		}
	}
	
	/**
	 * Check for the keywords of consumable items.
	 */
	private void consumableCheck(Consumable item, String str, ArrayList<String> words) {
		if(words.get(0).equalsIgnoreCase("health")) {
			item.setHealth(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("score")) {
			item.setScore(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("currency")) {
			item.setCurrency(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("intellect")) {
			item.setIntellect(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("dexterity")) {
			item.setDexterity(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("strength")) {
			item.setStrength(Integer.parseInt(words.get(1)));
		}
	}
	
	/**
	 * Check for the keywords of equipment items.
	 */
	private void equipmentCheck(Equipment item, String str, ArrayList<String> words) {
		if(words.get(0).equalsIgnoreCase("health")) {
			item.setHealth(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("intellect")) {
			item.setIntellect(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("dexterity")) {
			item.setDexterity(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("strength")) {
			item.setStrength(Integer.parseInt(words.get(1)));
		} else if(words.get(0).equalsIgnoreCase("quality")) {
			item.setQuality(Integer.parseInt(words.get(1)));
		}
	}
}
