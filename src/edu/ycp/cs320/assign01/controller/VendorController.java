package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.enums.NPCType;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class VendorController {
	private ArrayList<Item> itemList;
	private Player player;
	private WorldMap game;
	private Location location;
	private Room room;
	
	public VendorController(WorldMap game, Player player, ArrayList<Item> itemList) {
		this.game = game;
		location = game.curLocation();
		room = location.curRoom();
		this.player = player;
		this.itemList = itemList;
	}

	public void updateLocation() {
		location = game.curLocation();
	}
	
	public void updateRoom() {
		room = location.curRoom();
	}
	
	/**
	 * Handles the following commands
	 * 		* Talk to [npc]
	 * 		* Buy [number] [item]
	 * 		* Sell [number] [item]
	 */
	public String control(String input) {
		String output = "";
		WordFinder finder = new WordFinder();
		ArrayList<String> words = finder.findWords(input);
		if(words.get(0).equals("talk") && words.get(1).equals("to")) {
			ArrayList<NPC> npcs = room.getNPCs();
			String name = "";
			NPC target = null;
			boolean test = true;
			
			if(npcs.size() == 0) {
				output += "There are no NPCs in this room to talk to. \n";
				test = false;
			} else if(words.size() < 3) {
					output += "Missing talk target.";
					test = false;
			} else {
				for(NPC npc : npcs) {
					if(input.contains(npc.getName().toLowerCase())) {
						name = npc.getName();
						target = npc;
						break;
					}
				}
				
				if(name.equals("")) {
					output += "There is no NPC in this room with this name. \n";
					test = false;
				} else if(target.isDead()) {
					output += "This NPC is already dead. \n";
					test = false;
				}
			}
			
			if(test) {
				if(target.getType() == NPCType.HOSTILE) {
					output += "The " + target.getName() + " doesn't seem interesting in talking. \n";
				} else {
					if(target.getInventory().isEmpty()) {
						output += "The " + target.getName() + " doesn't have anything to sell. \n";
					} else {
						output += "The " + target.getName() + " shows you its wares. \n";
						ArrayList<Pair<Item,Integer>> inv = target.getInventory();
						for(Pair<Item,Integer> pair : inv) {
							output += pair.getRight() + " " + pair.getLeft().getName() + " for $" + pair.getLeft().getWorth() + " each \n";
						}
					}
				}
			}
		} else if(words.get(0).equals("buy") && words.size() > 2) {
			// Test 1: Is this an item?
			// Test 2: Is there an NPC here?
			// Test 3: Is there a friendly NPC here?
			// Test 4: Does this NPC have this item?
			// Test 5: Does this NPC have enough of the item?
			// Test 6: Does the player have enough currency for this item?
			int quantity = Integer.parseInt(words.get(1));
			String name = input.substring(words.get(0).length() + words.get(1).length() + 1).trim();
			NPC target = null;
			Pair<Item,Integer> item = null;
			boolean test = itemCheck(name);
			if(!test) {
				output += "This is not an item. \n";
			} else {
				ArrayList<NPC> npcs = room.getNPCs();
				if(npcs.isEmpty()) {
					output += "There are no NPCs in this room to trade with. \n";
					test = false;
				} else {
					for(NPC npc : npcs) {
						if(npc.getType() == NPCType.FRIENDLY) {
							target = npc;
							break;
						}
					}
				}
				
				if(target == null) {
					output += "There are no friendly NPCs in this room to trade with. \n"; 
					test = false;
				} else {
					item = target.getItem(name);
					
					if(!target.containsItem(item.getLeft())) {
						output += "The " + target.getName() + " does not have this item. \n";
						test = false;
					} else if(!target.containsItem(item.getLeft(), quantity)) {
						output += "The " + target.getName() + " only has " + item.getRight() + " of this item. \n";
						test = false;
					} else if(item.getLeft().getWorth() * quantity > player.getCurrency()) {
						output += "You do not have enough currency to buy that much. \n";
						test = false;
					}
				}
			}
			
			// If all tests have passed, make the transaction
			if(test) {
				item = target.removeItem(item.getLeft(), quantity);
				player.addItem(item.getLeft(), quantity);
				player.changeCurrency(-quantity * item.getLeft().getWorth());
				output += "You bought " + quantity + " " + item.getLeft().getName() + " from the " + target.getName() + " for $" + item.getLeft().getWorth() * quantity + ". \n"; 
			}
		} else if(words.get(0).equals("sell") && words.size() > 2) {
			// Test 1: Is this an item?
			// Test 2: Is there an NPC here?
			// Test 3: Is there a friendly NPC here?
			// Test 4: Does the player have this item?
			// Test 5: Does the player have enough of the item?
			int quantity = Integer.parseInt(words.get(1));
			String name = input.substring(words.get(0).length() + words.get(1).length() + 1).trim();
			NPC target = null;
			Pair<Item,Integer> item = null;
			boolean test = itemCheck(name);
			if(!test) {
				output += "This is not an item. \n";
			} else {
				ArrayList<NPC> npcs = room.getNPCs();
				if(npcs.isEmpty()) {
					output += "There are no NPCs in this room to trade with. \n";
					test = false;
				} else {
					for(NPC npc : npcs) {
						if(npc.getType() == NPCType.FRIENDLY) {
							target = npc;
							break;
						}
					}
				}
				
				if(target == null) {
					output += "There are no friendly NPCs in this room to trade with. \n"; 
					test = false;
				} else {
					item = player.getItem(name);
					
					if(item == null) {
						output += "You don't have this item. \n";
						test = false;
					} else if(!player.containsItem(item.getLeft(), quantity)) {
						output += "You only have " + item.getRight() + " of this item. \n";
						test = false;
					}
				}
			}

			// If all tests have passed, make the transaction
			if(test) {
				item = player.removeItem(item.getLeft(), quantity);
				target.addItem(item.getLeft(), quantity);
				player.changeCurrency(quantity * item.getLeft().getWorth());
				output += "You sold " + quantity + " " + item.getLeft().getName() + " to the " + target.getName() + " for $" + item.getLeft().getWorth() * quantity + ". \n"; 
			}
		}
		if(output.equals(""))
			output += "This is not a command. \n";
		return output;
	}

	private boolean itemCheck(String name) {
		boolean test = false;
		for(Item item : itemList) {
			if(item.getName().equalsIgnoreCase(name)) {
				test = true;
				break;
			}
		}
		return test;
	}
}
