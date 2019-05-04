package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;
import java.util.Random;

import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class CombatController {
	private WorldMap game;
	private Location location;
	private Room room;
	private Player player;
	
	public CombatController(WorldMap game, Player player) {
		this.player = player;
		this.game = game;
		location = game.curLocation();
		room = location.curRoom();
	}

	public void updateLocation() {
		location = game.curLocation();
	}
	
	public void updateRoom() {
		room = location.curRoom();
	}
	
	// attack [<npc>] <part>
	public String attack(String input) {
		String output = "";
		WordFinder finder = new WordFinder();
		ArrayList<String> words = finder.findWords(input);
		if(words.get(0).equals("attack")) {
			Random rand = new Random();
			ArrayList<NPC> npcs = room.getNPCs();
			String name = "";
			NPC target = null;
			
			// Test if the input is valid
			// Test 1: Are there any NPCs in this room?
			// Test 2: Did the player only say "attack"?
			// Test 3: Did the player name an NPC to attack?
			// Test 4: Did the player name a valid part to attack?
			// Test 5: Did the player type more than they should have?
			// TODO: Handle the situation where the player names no part
			boolean test = true;
			boolean critical = false;
			if(npcs.size() == 0) {
				output += "There are no NPCs in this room to attack.";
				test = false;
			} else {
				if(words.size() < 2) {
					output += "Missing attack target.";
					test = false;
				}
				
				for(NPC npc : npcs) {
					if(input.contains(npc.getName())) {
						name = npc.getName();
						target = npc;
						break;
					}
				}
				
				if(test && name.equals("")) {
					output += "There is no NPC in this room with this name. \n";
					test = false;
				} else if(target.isDead()) {
						output += "This NPC is already dead. \n";
						test = false;
				} else {
					String part = words.get(words.size()-1);
					if(!target.getParts().contains(part)) { // TODO: Handle if no part given
						output += "This is not a targetable part for the " + target.getName() + ". \n";
						test = false;
					} else if(	!(input.trim()).equals("attack " + target.getName() + " " + part) ||
								!(input.trim()).equals("attack " + target.getName())) {
						output += "Looks like you typed a bit too much. \n";
						test = false;
					} else if(target.getParts().contains(part)) {
						if(target.getWeaknesses().contains(part)) {
							critical = true;
						} else {
							if(rand.nextInt(100) < 10) {
								critical = true;
							}
						}
					}
				}
			}
			
			// If all the tests passed, attack the target NPC
			if(test) {
				int attack = player.attack();
				if(critical) {
					attack *= 2;
				}
				target.changeHealth(attack);
				output += "You attacked the " + target.getName() + " for " + attack + " damage. \n";
				if(critical) {
					output += "Critical hit! \n";
				}
				if(target.isDead()) {
					output += "The " + target.getName() + " is dead. \n";
					ArrayList<Pair<Item,Integer>> loot = target.getLoot();
					output += "Found ";
					if(loot.isEmpty()) {
						output += "nothing. \n";
					} else {
						for(int i = 0; i < loot.size(); i++) {
							Pair<Item,Integer> item = loot.get(i);
							player.addItem(item.getLeft(), item.getRight());
							output += item.getRight() + " " + item.getLeft().getName();
							if(i < loot.size()-2) {
								output += ", ";
							} else if(i < loot.size()-1 && loot.size() > 1){
								output += " and ";
							}
						}
						output += ". \n";
					}
				}
				output = npcAttack(output);
			}
		}
		return output;
	}
	
	/**
	 * Generate the attack damage from each NPC in the current room
	 * and change the player's health accordingly.
	 */
	public String npcAttack(String output) {
		ArrayList<NPC> npcs = room.getNPCs();
		for(NPC npc : npcs) {
			int attack = npc.attack();
			player.changeHealth(attack);
			output += "The " + npc.getName() + " attacked you for " + attack + " damage. \n";
		}
		return output;
	}
}
