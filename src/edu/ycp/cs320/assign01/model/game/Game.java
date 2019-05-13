package edu.ycp.cs320.assign01.model.game;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.assign01.db.DerbyDatabase;
import edu.ycp.cs320.assign01.enums.NPCType;
import edu.ycp.cs320.assign01.model.Equipment;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Library;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class Game {
	private Player player;
	private WorldMap game;
	private Library library;
	private ArrayList<Item> items;
	
	public Game() {
		player = new Player();
		library = new Library();
		game = library.generateWorld();
		items = library.getItems();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public WorldMap getWorld() {
		return game;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	/* 
	 * Spaces seperate values
	 * Commas seperate different objects
	 * Horizontal bars seperate objects within an object (e.g. items within an NPC)
	 * 
	 * 0 = Player
	 *		0 = player ID
	 *		1 = location ID
	 *		2 = X
	 *		3 = Y
	 *		4 = HP
	 * 1 = Rooms
	 *		X = ID number of the room (0 for unentered, 1 for entered)
	 * 2 = NPCs
	 *		X = ID number of the NPC
	 *			0 = basic stats
	 *				0 = ID number within location
	 *				1 = Level
	 *				2 = HP
	 *				3 = Hostility (0 for friendly, 1 for hostile)
	 *			1 = inventory
	 *				X = inventory item
	 *					0 = item ID
	 *					1 = item quantity
	 * 3 = Events
	 * 		0 = roomID
	 * 		1 = Done? (0 for not, 1 for yes)
	 */
	public ArrayList<String> stringify() {
		// Update the player's model, inventory, and access in the DB
		updatePlayerDB();
		
		ArrayList<String> output = new ArrayList<String>();
		Location loc = game.curLocation();
		
		String playerStr = player.getId() + " " + loc.getId() + " " + loc.getX() + " " + loc.getY() + " " + player.getHealth();
		
		String roomStr = "";
		for(int i = 0; i < loc.getRooms().size(); i++) {
			if(loc.getRoom(i+1).getEntered()) {
				roomStr += "1 ";
			} else {
				roomStr += "0 ";
			}
		}
		
		String npcStr = "";
		for(Room room: loc.getRooms()) {
			for(NPC npc : room.getNPCs()) {
				npcStr += npc.getId() + " " + npc.getLevel() + " " + npc.getHealth() + " ";
				if(npc.getType() == NPCType.FRIENDLY) {
					npcStr += "0";
				} else {
					npcStr += "1";
				}
				ArrayList<Pair<Item,Integer>> inventory = npc.getInventory();
				if(!inventory.isEmpty()) {
					for(Pair<Item,Integer> pair : inventory) {
						npcStr += " | " + pair.getLeft().getId() + " " + pair.getRight();
					}
				}
				npcStr += " , ";
			}
		}
		
		String eventStr = "";
		for(Room room : loc.getRooms()) {
			if(room.curEvent() != null) {
				eventStr += room.getId() + " ";
				if(room.curEvent().isDone()) {
					eventStr += "1";
				} else {
					eventStr += "0";
				}
				eventStr += " , ";
			}
		}
		
		output.add(playerStr);
		output.add(roomStr);
		output.add(npcStr);
		output.add(eventStr);
		return output;
	}
	
	public void reconstruct(ArrayList<String> stringified) {
		String playerStr = stringified.get(0);
		String roomStr = stringified.get(1);
		String npcStr = stringified.get(2);
		String eventStr = stringified.get(3);
		
		WordFinder finder = new WordFinder();
		
		ArrayList<String> playerStats = finder.findWords(playerStr);
		
		// Update the player's model, location, and access from the DB
		updatePlayerReal(playerStats);
		
		game.setPlayer(Integer.parseInt(playerStats.get(1)));
		Location loc = game.curLocation();
		loc.setPlayer(Integer.parseInt(playerStats.get(2)), Integer.parseInt(playerStats.get(3)));
		player.setCurrHealth(Integer.parseInt(playerStats.get(4)));
		
		ArrayList<String> rooms = finder.findWords(roomStr);
		for(int i = 0; i < rooms.size(); i++) {
			if(Integer.parseInt(rooms.get(i)) == 1) {
				loc.getRoom(i+1).isEntered(true);
			} else {
				loc.getRoom(i+1).isEntered(false);
			}
		}
		
		ArrayList<String> npcs = finder.findWords(npcStr, ",");
		for(String npc : npcs) {
			ArrayList<String> npcParts = finder.findWords(npc, "|");
			ArrayList<String> npcStats = finder.findWords(npcParts.get(0));
			for(Room room : loc.getRooms()) {
				NPC thisNPC = room.getNPC(Integer.parseInt(npcStats.get(0)));
				if(thisNPC != null) {
					thisNPC.setLevel(Integer.parseInt(npcStats.get(1)));
					thisNPC.setCurrHealth(Integer.parseInt(npcStats.get(2)));
					if(Integer.parseInt(npcStats.get(3)) == 0) {
						thisNPC.setType("friendly");
					} else {
						thisNPC.setType("hostile");
					}
					for(int i = 1; i < npcParts.size(); i++) {
						ArrayList<String> item = finder.findWords(npcParts.get(i));
						thisNPC.addItem(items.get(Integer.parseInt(item.get(0))-1),Integer.parseInt(item.get(1)));
					}
				}
			}
		}
		
		ArrayList<String> events = finder.findWords(eventStr, ",");
		for(String event : events) {
			ArrayList<String> thisEvent = finder.findWords(event);
			Room room = loc.getRoom(Integer.parseInt(thisEvent.get(0)));
			if(Integer.parseInt(thisEvent.get(1)) == 1)
				room.getEvents().get(0).setKey(5);
			else {
				room.getEvents().get(0).reset();
			}
		}
	}
	
	/**
	 * Unequip all the player's equipment so that their stats save properly
	 * BUT keep the IDs of the equipment as being equipped
	 * 
	 * Check if there's any changes to the player's location access
	 * Add/remove access as necessary
	 * 
	 * Check if there's any changes to the player's inventory
	 * Add/remove items as necessary
	 */
	public void updatePlayerDB() {
		DerbyDatabase db = new DerbyDatabase();
		
		// Unequip all the player's equipment but save the IDs
		// so that the player's stats don't save with the equipment
		// on.
		int acc = player.getAccessoryID();
		int arm = player.getArmorID();
		int wep = player.getWeaponID();
		player.unequipAccessory();
		player.unequipArmor();
		player.unequipWeapon();
		player.setAccessoryID(acc);
		player.setArmorID(arm);
		player.setWeaponID(wep);
		
		// Update the player's stats
		db.modifyPlayer(player);
		// Update the player's inventory
		db.modifyInventory(player);
		// Update the player's location access
		db.modifyAccess(game, player.getId());
	}
	
	public void updatePlayerReal(ArrayList<String> playerStats) {
		DerbyDatabase db = new DerbyDatabase();
		
		// Get the player's stats
		player = db.findPlayerByID(Integer.parseInt(playerStats.get(0)));
		
		// Get the player's inventory
		List<Pair<Integer,Integer>> inv = db.findInventoryByPlayerID(player.getId());
		for(Pair<Integer,Integer> pair : inv) {
			player.addItem(items.get(pair.getLeft()-1),pair.getRight());
		}
		
		// Equip the proper items on the player
		int acc = player.getAccessoryID();
		int arm = player.getArmorID();
		int wep = player.getWeaponID();
		if(acc != 0) {
			player.equip((Equipment)items.get(acc-1));
		}
		if(arm != 0) {
			player.equip((Equipment)items.get(arm-1));
		}
		if(wep != 0) {
			player.equip((Equipment)items.get(wep-1));
		}
		
		// Update the player's location access
		List<Integer> access = db.findAccessByPlayerID(player.getId());
		for(Integer i : access) {
			game.grantAccess(i);
		}
	}
}
