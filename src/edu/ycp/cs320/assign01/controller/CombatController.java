package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;
import java.util.Random;

import edu.ycp.cs320.assign01.enums.NPCType;
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
			// Test 4: Did the player attack a living NPC?
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
					if(input.contains(npc.getName().toLowerCase())) {
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
				}
			}
			
			// If all the tests passed, attack the target NPC
			if(test) {
				int attack = player.attack();
				target.changeHealth(-attack);
				String part = words.get(words.size()-1);
				boolean partTest = target.getParts().contains(part);
				if(partTest) {
					if(target.getWeaknesses().contains(part)) {
						if(rand.nextInt(100) < 50) {
							critical = true;
						}
					} else {
						if(rand.nextInt(100) < 10) {
							critical = true;
						}
					}
				}
				if(critical) {
					attack *= 2;
					output += "Critical hit! \n";
				}
				if(partTest) {
					output += "You attacked the " + target.getName() + "'s " + part + " for " + attack + " damage. It has " + target.getHealth() + " health left. \n";	
				} else {
					output += "You attacked the " + target.getName() + " for " + attack + " damage. It has " + target.getHealth() + " health left. \n";
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
			if(!npc.isDead() && npc.getType() == NPCType.HOSTILE) {
				int attack = npc.attack();
				player.changeHealth(-attack);
				output += "The " + npc.getName() + " attacked you for " + attack + " damage. You have " + player.getHealth() + " health left. \n";
				if(player.isDead()) {
					output += "You died a painful death at the hands of the "+ npc.getName() + ". \n";
					break;
				}
			}
		}
		return output;
	}
}
