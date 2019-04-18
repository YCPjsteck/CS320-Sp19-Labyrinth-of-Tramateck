package edu.ycp.cs320.assign01.model.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ycp.cs320.assign01.model.Enemy;
import edu.ycp.cs320.assign01.model.Event;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.Vendor;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.movement.WorldMap;

public class Library {
	
	private ArrayList<Location> locationList; // locations.txt
	private ArrayList<NPC> npcList; // npcs.txt
	private ArrayList<Item> itemList; // items.txt
	// ArrayList<Event> eventList; // events.txt
	
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
	
	public NPC findNPC(String name) {
		for(NPC n : npcList) {
			if(n.getName().equalsIgnoreCase(name))
				return n;
		}
		return null;
	}

	public Item findItem(String name) {
		for(Item i : itemList) {
			if(i.getName().equalsIgnoreCase(name))
				return i;
		}
		return null;
	}
	
	// TODO have generateLocations track the ID number of all NPCs added to the rooms of the location
	public void generateLocations(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		
		WordFinder finder = new WordFinder();
		Location loc;
		while(reader.hasNext()) {
			String str = reader.nextLine().trim();
			loc = new Location();
			
			while(!str.equalsIgnoreCase("")) {
				ArrayList<String> words = finder.findWords(str);
				if(words.get(0).equalsIgnoreCase("location")) {
					loc.setName(str.substring(words.get(0).length()).trim());
				} else if(words.get(0).equalsIgnoreCase("level")) {
					loc.setMinLevel(Integer.parseInt(words.get(1)));
					loc.setMaxLevel(Integer.parseInt(words.get(2)));
				} else if(words.get(0).equalsIgnoreCase("type")) {
					loc.setType(words.get(1));
				} else if(words.get(0).equalsIgnoreCase("map")) {
					int x = Integer.parseInt(words.get(1));
					int y = Integer.parseInt(words.get(2));
					loc.setMap(generateLocMap(reader, x, y));
				} else if(words.get(0).equalsIgnoreCase("room")) {
					loc.addRoom(generateRoom(reader, loc));
				}
				if(reader.hasNext())
					str = reader.nextLine().trim();
				else
					str = "";
			}
			
			if(loc.getName() != null)
				locationList.add(loc);
		}
		reader.close();
	}
	
	public int[][] generateLocMap(Scanner reader, int x, int y) {
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

	public Room generateRoom(Scanner reader, Location loc) {
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
				room.addNPC(findNPC(words.get(1)));
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
	
	public void generateNPCs(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		WordFinder finder = new WordFinder();
		
		// TODO: Instead of reading for "npc" then finding the type,
		// read for the type first.
		while(reader.hasNext()) {
			NPC npc = new NPC();
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			if(words.get(0).equalsIgnoreCase("npc")) {
				String name = str.substring(words.get(0).length()).trim();
				str = reader.nextLine().trim();
				while(!str.equalsIgnoreCase("")) {
					words = finder.findWords(str);
					if(words.get(0).equalsIgnoreCase("type")) {
						String type = words.get(1);
						if(type.equalsIgnoreCase("enemy")) {
							npc = new Enemy();
						} else if(type.equalsIgnoreCase("vendor")) {
							npc = new Vendor();
						}
						npc.setName(name);
					} else if(words.get(0).equalsIgnoreCase("health")) {
						npc.setHealth(Integer.parseInt(words.get(1)));
					} else if(words.get(0).equalsIgnoreCase("attack")) {
						npc.setMinAttack(Integer.parseInt(words.get(1)));
						npc.setMaxAttack(Integer.parseInt(words.get(2)));
					} else if(words.get(0).equalsIgnoreCase("loot")) {
						int weight = Integer.parseInt(words.get(1));
						int size = Integer.parseInt(words.get(2));
						Item item = findItem(str.substring(str.indexOf("|") + 1).trim());
						npc.addLoot(item, weight, size);
					} else if(words.get(0).equalsIgnoreCase("inventory")) {
						int weight = Integer.parseInt(words.get(1));
						int size = Integer.parseInt(words.get(2));
						Item item = findItem(str.substring(str.indexOf("|") + 1).trim());
						((Vendor)npc).addInventory(item, weight, size);
					} else if(words.get(0).equalsIgnoreCase("part")) {
						((Enemy)npc).setPart(words.get(1));
					} else if(words.get(0).equalsIgnoreCase("weakness")) {
						((Enemy)npc).setWeakness(words.get(1));
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
	
	// TODO be able to read items of various subclasses
	public void generateItems(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		WordFinder finder = new WordFinder();
		
		while(reader.hasNext()) {
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			if(words.get(0).equalsIgnoreCase("item")) {
				Item item = new Item();
				item.setName(str.substring(words.get(0).length()).trim());
				str = reader.nextLine();
				while(!str.equalsIgnoreCase("")) {
					words = finder.findWords(str);
					if(words.get(0).equalsIgnoreCase("rarity")) {
						item.setRarity(words.get(1));
					} else if(words.get(0).equalsIgnoreCase("worth")) {
						item.setWorth(Integer.parseInt(words.get(1)));
					} else if(words.get(0).equalsIgnoreCase("weight")) {
						item.setWeight(Integer.parseInt(words.get(1)));
					}

					if(reader.hasNext())
						str = reader.nextLine().trim();
					else
						str = "";
				}
				itemList.add(item);
			}
		}
		
		reader.close();
	}
	
	public void generateEvents() throws FileNotFoundException {
		Scanner reader = new Scanner(new File("events.txt"));
		WordFinder finder = new WordFinder();
		
		while(reader.hasNext()) {
			Event event = new Event();
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			if(words.get(0).equals("event")) {
				event.setName(str.substring(words.get(0).length()).trim());
				str = reader.nextLine();
				words = finder.findWords(str);
				while(!str.equals("")) {
					if(words.get(0).equals("rarity")) {
						item.setRarity(words.get(1));
					} else if(words.get(0).equals("worth")) {
						item.setWorth(Integer.parseInt(words.get(1)));
					} else if(words.get(0).equals("weight")) {
						item.setWorth(Integer.parseInt(words.get(1)));
					}
					
					if(reader.hasNext())
						str = reader.nextLine().trim();
					else
						str = "";
				}
				itemList.add(item);
			}
		}
		
		reader.close();
	}
}
