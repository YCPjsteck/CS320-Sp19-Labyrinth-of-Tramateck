package edu.ycp.cs320.assign01.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
	
	ArrayList<Location> locationList; // locations.txt
	ArrayList<NPC> npcList; // npcs.txt
	ArrayList<Item> itemList; // items.txt
	// ArrayList<Event> eventList; // events.txt
	
	public ArrayList<Location> getLocations() {
		return locationList;
	}
	
	public WorldMap generateWorld() {
		WorldMap map = new WorldMap();
		int[][] locMap = new int[1][1];
		locMap[0][0] = 1;
		map.setMap(locMap);
		
		map.setPlayer(0, 0);
		
		try { 
			generateLocations();
			generateNPCs();
			generateItems();
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
		locationList = new ArrayList<Location>();
		
		WordFinder finder = new WordFinder();
		Location loc;
		while(reader.hasNext()) {
			String str = reader.nextLine().trim();
			loc = new Location();
			
			while(!str.equals("")) {
				ArrayList<String> words = finder.findWords(str);
				//System.out.println(str);
				if(words.get(0).equals("location")) {
					loc.setName(words.get(1));
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
		NPC npc;
		while(reader.hasNext()) {
			npc = new NPC();
			String str = reader.nextLine();
			ArrayList<String> words = finder.findWords(str);
			if(words.get(0).equals("npc")) {
				
			} else if(words.get(0).equals("type")) {
				
			} else if(words.get(0).equals("health")) {
				
			} else if(words.get(0).equals("attack")) {
				
			} else if(words.get(0).equals("loot")) {
				
			} else if(words.get(0).equals("inventory")) {
				
			} else if(words.get(0).equals("part")) {
				
			} else if(words.get(0).equals("weakness")) {
				
			}
		}
		
		reader.close();
	}
	
	public void generateItems() throws FileNotFoundException {
		Scanner reader = new Scanner(new File("items.txt"));
		reader.close();
	}
}
