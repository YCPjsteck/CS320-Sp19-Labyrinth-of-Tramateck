package edu.ycp.cs320.assign01.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
			generateItems();
			generateNPCs();
			// generateEvents();
			generateLocations();
		} 
		catch (FileNotFoundException e) { 
			e.printStackTrace();
		}
		
		return map;
	}
	
	public NPC findNPC(String name) {
		for(NPC n : npcList) {
			if(n.getName().equals(name))
				return n;
		}
		return null;
	}

	public Item findItem(String name) {
		for(Item i : itemList) {
			if(i.getName().equals(name))
				return i;
		}
		return null;
	}
	
	public void generateLocations() throws FileNotFoundException {
		Scanner reader = new Scanner(new File("locations.txt"));
		
		WordFinder finder = new WordFinder();
		Location loc;
		while(reader.hasNext()) {
			String str = reader.nextLine().trim();
			loc = new Location();
			
			while(!str.equals("")) {
				ArrayList<String> words = finder.findWords(str);
				//System.out.println(str);
				if(words.get(0).equals("location")) {
					loc.setName(str.substring(words.get(0).length()).trim());
				} else if(words.get(0).equals("level")) {
					loc.setMinLevel(Integer.parseInt(words.get(1)));
					loc.setMaxLevel(Integer.parseInt(words.get(2)));
				} else if(words.get(0).equals("type")) {
					loc.setType(words.get(1));
				} else if(words.get(0).equals("map")) {
					int x = Integer.parseInt(words.get(1));
					int y = Integer.parseInt(words.get(2));
					loc.setMap(generateLocMap(reader, x, y));
				} else if(words.get(0).equals("room")) {
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
			//System.out.println(str);
			for(int j = 0; j < str.length(); j++) {
				char c = str.charAt(j);
				if(c == 'o') {
					//System.out.println(i + " " + j + " " + id);
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
		while(!str.equals("")) {
			ArrayList<String> words = finder.findWords(str);
			//System.out.println(str);
			if(words.get(0).equals("coord")) {
				int x = Integer.parseInt(words.get(1));
				int y = Integer.parseInt(words.get(2));
				int ID = loc.getMap()[x][y];
				room.setId(ID);
			} else if(words.get(0).equals("start")) {
				room.isStart();
			} else if(words.get(0).equals("exit")) {
				room.isExit();
			} else if(words.get(0).equals("npc")) {
				room.getMonsters().add(words.get(1));
				// room.getMonsters().add(findNPC(words.get(1)));
			} else if(words.get(0).equals("event")) {
			} else if(words.get(0).equals("item")) {
			} else if(words.get(0).equals("travel")) {
			}
			if(reader.hasNext())
				str = reader.nextLine().trim();
			else
				str = "";
		}
		return room;
	}
	
	public void generateNPCs() throws FileNotFoundException {
		Scanner reader = new Scanner(new File("npcs.txt"));
		WordFinder finder = new WordFinder();
		
		// TODO: Instead of reading for "npc" then finding the type,
		// read for the type first.
		while(reader.hasNext()) {
			NPC npc = new NPC();
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			if(words.get(0).equals("npc")) {
				String name = str.substring(words.get(0).length()).trim();
				str = reader.nextLine();
				words = finder.findWords(str);
				while(!str.equals("")) {
					if(words.get(0).equals("type")) {
						String type = words.get(1);
						if(type.equals("enemy")) {
							npc = new Enemy();
						} else if(type.equals("vendor")) {
							npc = new Vendor();
						}
						npc.setName(name);
					} else if(words.get(0).equals("health")) {
						npc.setHealth(Integer.parseInt(words.get(1)));
					} else if(words.get(0).equals("attack")) {
						npc.setMinAttack(Integer.parseInt(words.get(1)));
						npc.setMaxAttack(Integer.parseInt(words.get(2)));
					} else if(words.get(0).equals("loot")) {
						Item item = findItem(words.get(1));
						int weight = Integer.parseInt(words.get(2));
						int size = Integer.parseInt(words.get(3));
						npc.addLoot(item, weight, size);
					} else if(words.get(0).equals("inventory")) {
						Item item = findItem(words.get(1));
						int weight = Integer.parseInt(words.get(2));
						int size = Integer.parseInt(words.get(3));
						((Vendor)npc).addInventory(item, weight, size);
					} else if(words.get(0).equals("part")) {
						((Enemy)npc).setPart(words.get(1));
					} else if(words.get(0).equals("weakness")) {
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
	
	public void generateItems() throws FileNotFoundException {
		Scanner reader = new Scanner(new File("items.txt"));
		WordFinder finder = new WordFinder();
		
		while(reader.hasNext()) {
			Item item = new Item();
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			if(words.get(0).equals("item")) {
				item.setName(str.substring(words.get(0).length()).trim());
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
