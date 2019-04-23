package edu.ycp.cs320.assign01.model.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	// ArrayList<Event> eventList; // events.txt
	
	// TODO
	//		Read events
	
	public Library() {
		locationList = new ArrayList<Location>();
		npcList = new ArrayList<NPC>();
		itemList = new ArrayList<Item>();
	}
	
	public ArrayList<Location> getLocations() {
		return locationList;
	}
	
	public ArrayList<NPC> getNPCs() {
		return npcList;
	}

	public ArrayList<Item> getItems() {
		return itemList;
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
	 * Generate and return the entire game world.
	 */
	public WorldMap generateWorld() {
		WorldMap map = new WorldMap();
		int[][] locMap = new int[1][1];
		locMap[0][0] = 1;
		map.setMap(locMap);
		
		map.setPlayer(0, 0);
		
		try {
			generateItems("items.txt");
			generateNPCs("npcs.txt");
			// generateEvents("events.txt");
			generateLocations("locations.txt");
		} 
		catch (FileNotFoundException e) { 
			e.printStackTrace();
		}
		
		return map;
	}

	/**
	 * Read the given file to generate the game's locations.
	 */
	public void generateLocations(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		WordFinder finder = new WordFinder();
		int locID = 1;
		int min = 0;
		int max = 0;
		
		while(reader.hasNext()) {
			String str = reader.nextLine().trim();
			Location loc = new Location();
			loc.setId(locID);
			locID++;
			int npcID = 1;
			
			while(!str.equalsIgnoreCase("")) {
				ArrayList<String> words = finder.findWords(str);
				if(words.get(0).equalsIgnoreCase("location")) {
					loc.setName(str.substring(words.get(0).length()).trim());
				} else if(words.get(0).equalsIgnoreCase("level")) {
					min = Integer.parseInt(words.get(1));
					max = Integer.parseInt(words.get(2));
					loc.setMinLevel(min);
					loc.setMaxLevel(max);
				} else if(words.get(0).equalsIgnoreCase("type")) {
					loc.setType(words.get(1));
				} else if(words.get(0).equalsIgnoreCase("map")) {
					int x = Integer.parseInt(words.get(1));
					int y = Integer.parseInt(words.get(2));
					loc.setMap(generateLocMap(reader, x, y));
				} else if(words.get(0).equalsIgnoreCase("room")) {
					loc.addRoom(generateRoom(reader, loc, npcID, min, max));
				}
				if(reader.hasNext())
					str = reader.nextLine().trim();
				else
					str = "";
			}
			locationList.add(loc);
		}
		reader.close();
	}
	
	/**
	 * Create a 2D array of ints of the given size, using the scanner as input.
	 */
	private int[][] generateLocMap(Scanner reader, int x, int y) {
		int[][] map = new int[x][y];
		int id = 1;
		
		for(int i = 0; i < y; i++) {
			String str = reader.nextLine().trim();
			for(int j = 0; j < str.length(); j++) {
				char c = str.charAt(j);
				if(c == 'o') {
					map[j][i] = id;
					id++;
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
			if(words.get(0).equalsIgnoreCase("coord")) {
				int x = Integer.parseInt(words.get(1));
				int y = Integer.parseInt(words.get(2));
				int ID = loc.getMap()[x][y];
				room.setId(ID);
			} else if(words.get(0).equalsIgnoreCase("start")) {
				room.isStart();
			} else if(words.get(0).equalsIgnoreCase("exit")) {
				room.isExit();
			} else if(words.get(0).equalsIgnoreCase("npc")) {
				NPC npc = findNPC(words.get(1));
				npc.setId(id);
				id++;
				Random rand = new Random();
				npc.setLevel(rand.nextInt(max-min) + min);
				room.addNPC(npc);
			} else if(words.get(0).equalsIgnoreCase("event")) {
			} else if(words.get(0).equalsIgnoreCase("item")) {
			} else if(words.get(0).equalsIgnoreCase("travel")) {
			}
			if(reader.hasNext())
				str = reader.nextLine().trim();
			else
				str = "";
		}
		return room;
	}
	
	/**
	 * Read the given file to generate the game's NPCs.
	 */
	public void generateNPCs(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		WordFinder finder = new WordFinder();
		
		while(reader.hasNext()) {
			NPC npc = new NPC();
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			if(words.get(0).equalsIgnoreCase("npc")) {
				npc.setName(str.substring(words.get(0).length()).trim());
				str = reader.nextLine().trim();
				while(!str.equalsIgnoreCase("")) {
					words = finder.findWords(str);
					if(words.get(0).equalsIgnoreCase("type")) {
						npc.setType(words.get(1));
					} else if(words.get(0).equalsIgnoreCase("health")) {
						npc.setHealth(Integer.parseInt(words.get(1)));
					} else if(words.get(0).equalsIgnoreCase("attack")) {
						npc.setMinAttack(Integer.parseInt(words.get(1)));
						npc.setMaxAttack(Integer.parseInt(words.get(2)));
					} else if(words.get(0).equalsIgnoreCase("loot")) {
						int chance = Integer.parseInt(words.get(1));
						int quantity = Integer.parseInt(words.get(2));
						Item item = findItem(str.substring(str.indexOf("|") + 1).trim());
						npc.addLoot(item, chance, quantity);
					} else if(words.get(0).equalsIgnoreCase("inventory")) {
						int chance = Integer.parseInt(words.get(1));
						int quantity = Integer.parseInt(words.get(2));
						Item item = findItem(str.substring(str.indexOf("|") + 1).trim());
						npc.addInventory(item, chance, quantity);
					} else if(words.get(0).equalsIgnoreCase("part")) {
						npc.setPart(words.get(1));
					} else if(words.get(0).equalsIgnoreCase("weakness")) {
						npc.setWeakness(words.get(1));
					}
					
					if(reader.hasNext())
						str = reader.nextLine().trim();
					else
						str = "";
				}
				npcList.add(npc);
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
		int id = 0;
		
		while(reader.hasNext()) {
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
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
	
	/**
	 * Generate a basic item.
	 */
	private void itemGeneration(Scanner reader, String str, WordFinder finder, ArrayList<String> words, int id) {
		Item item = new Item();
		item.setName(str.substring(words.get(0).length()).trim());
		str = reader.nextLine();
		while(!str.equalsIgnoreCase("")) {
			words = finder.findWords(str);
			itemCheck(item,str,words);
		
			if(reader.hasNext())
				str = reader.nextLine().trim();
			else
				str = "";
		}
		item.setId(id);
		id++;
		itemList.add(item);
	}
	
	/**
	 * Generate a consumable item.
	 */
	private void consumableGeneration(Scanner reader, String str, WordFinder finder, ArrayList<String> words, int id) {
		Consumable item = new Consumable();
		item.setName(str.substring(words.get(0).length()).trim());
		str = reader.nextLine();
		while(!str.equalsIgnoreCase("")) {
			words = finder.findWords(str);
			itemCheck(item,str,words);
			consumableCheck(item,str,words);
			
			if(reader.hasNext())
				str = reader.nextLine().trim();
			else
				str = "";
		}
		item.setId(id);
		id++;
		itemList.add(item);
	}
	
	/**
	 * Generate an equipment item.
	 */
	private void equipmentGeneration(Scanner reader, String str, WordFinder finder, ArrayList<String> words, int id) {
		Equipment item = new Equipment();
		item.setName(str.substring(words.get(0).length()).trim());
		str = reader.nextLine();
		while(!str.equalsIgnoreCase("")) {
			words = finder.findWords(str);
			itemCheck(item,str,words);
			equipmentCheck(item,str,words);
			
			if(reader.hasNext())
				str = reader.nextLine().trim();
			else
				str = "";
		}
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
		} else if(words.get(0).equalsIgnoreCase("level")) {
			item.setLevelChange(Integer.parseInt(words.get(1)));
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
