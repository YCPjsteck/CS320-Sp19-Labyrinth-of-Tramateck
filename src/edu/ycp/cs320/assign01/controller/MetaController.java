package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import edu.ycp.cs320.assign01.enums.ItemType;
import edu.ycp.cs320.assign01.model.Consumable;
import edu.ycp.cs320.assign01.model.Equipment;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class MetaController {
	Set<String> combatSet, movementSet, playerSet, vendorSet, bonusSet;
	CombatController combatCon;
	PlayerController playerCon;
	MovementController movementCon;
	VendorController vendorCon;
	Player player;
	WorldMap game;
	ArrayList<Item> itemList;
	
	public MetaController(WorldMap game, Player player, ArrayList<Item> itemList) {
		this.player = player;
		this.game = game;
		this.itemList = itemList;
		
		playerCon = new PlayerController(player, itemList);
		movementCon = new MovementController(game, player);
		combatCon = new CombatController(game, player);
		vendorCon = new VendorController(game, player, itemList);
		
		combatSet = new TreeSet<String>();
		combatSet.add("attack");
		movementSet = new TreeSet<String>();
		movementSet.add("move");
		movementSet.add("run");
		playerSet = new TreeSet<String>();
		playerSet.add("equip");
		playerSet.add("consume");
		playerSet.add("unequip");
		playerSet.add("inventory");
		playerSet.add("equipped");
		playerSet.add("equipment");
		playerSet.add("stats");
		playerSet.add("level");
		playerSet.add("experience");
		playerSet.add("currency");
		playerSet.add("score");
		playerSet.add("health");
		playerSet.add("dexterity");
		playerSet.add("intellect");
		playerSet.add("strength");
		vendorSet = new TreeSet<String>();
		vendorSet.add("talk");
		vendorSet.add("buy");
		vendorSet.add("sell");
		bonusSet = new TreeSet<String>();
		bonusSet.add("jump");
		bonusSet.add("barrel");
	}
	
	public String control(String input) {
		String output = "";
		WordFinder finder = new WordFinder();
		ArrayList<String> words = finder.findWords(input);
		
		if(!words.isEmpty()) {
			if(movementSet.contains(words.get(0))) {
				movementCon.updateLocation();
				movementCon.updateRoom();
				output += movementCon.control(input);
			} else if(playerSet.contains(words.get(0))) {
				output += playerCon.control(input);
			} else if(combatSet.contains(words.get(0))) {
				combatCon.updateLocation();
				combatCon.updateRoom();
				output += combatCon.control(input);
			} else if(vendorSet.contains(words.get(0))) {
				vendorCon.updateLocation();
				vendorCon.updateRoom();
				output += vendorCon.control(input);
			} else {
				if(words.get(0).equals("help")) {
					output += help();
				} else if(words.get(0).equals("inspect")) {
					if(words.get(1).equals("room")) {
						output += game.curLocation().curRoom().getLongDesc();
					} else {
						String name = input.substring(words.get(0).length()).trim();
						output += npcCheck(name);
						output += itemCheck(name);
					}
				} else if(bonusSet.contains(words.get(0))) {
					output += bonus(words);
				}
			}
		}
		if(output.equals(""))
			output += "This is not a command. \n";
		
		return output;
	}

	private String itemCheck(String name) {
		String output = "";
		Item target = null;
		for(Item item : itemList) {
			if(item.getName().equalsIgnoreCase(name)) {
				target = item;
				break;
			}
		}
		if(target != null) {
			output += target.getRarity() + " " + target.getType() + "\n";
			output += target.getLongDesc() + "\n";
			output += "Worth $" + target.getWorth() + " per item. \n";
			if(target.getType() == ItemType.CONSUMABLE) {
				Consumable consumable = (Consumable)target;
				if(consumable.getHealth() != 0) {
					output += "Heals " + consumable.getHealth() * player.getLevel() + " health. \n";
				}
				if(consumable.getScore() != 0) {
					output += "Grants you " + consumable.getScore() * player.getScore() + " score. \n";
				}
				if(consumable.getExperience() != 0) {
					output += "Grants you " + consumable.getScore() * player.getExperience() + " experience. \n";
				}
				if(consumable.getCurrency() != 0) {
					output += "Grants you $" + consumable.getScore() * player.getCurrency() + ". \n";
				}
				if(consumable.getDexterity() != 0) {
					output += "Grants you " + consumable.getScore() * player.getDexterity() + " dexterity. \n";
				}
				if(consumable.getIntellect() != 0) {
					output += "Grants you " + consumable.getScore() * player.getIntellect() + " intellect. \n";
				}
				if(consumable.getStrength() != 0) {
					output += "Grants you " + consumable.getScore() * player.getStrength() + " strength. \n";
				}
			} else if(target.getType() == ItemType.ARMOR || target.getType() == ItemType.ACCESSORY || target.getType() == ItemType.WEAPON) {
				Equipment equipment = (Equipment)target;
				output += "Has a quality of " + equipment.getQuality() + ". \n";
				if(equipment.getHealth() != 0) {
					output += "Changes your base health by " + equipment.getHealth() * player.getLevel() + ". \n";
				}
				if(equipment.getDexterity() != 0) {
					output += "Changes your dexterity by " + equipment.getDexterity() * player.getLevel() + ". \n";
				}
				if(equipment.getIntellect() != 0) {
					output += "Changes your intellect by " + equipment.getIntellect() * player.getLevel() + ". \n";
				}
				if(equipment.getStrength() != 0)  {
					output += "Changes your strength by " + equipment.getStrength() * player.getLevel() + ". \n";
				}
			}
		}
		return output;
	}

	private String npcCheck(String name) {
		String output = "";
		NPC target = game.curLocation().curRoom().getNPC(name);
		if(target != null) {
			output += "This is a level " + target.getLevel() + " " + target.getName() + ". It has " + target.getHealth() + " health remaining. \n";
			output += target.getLongDesc() + "\n";
		}
		return output;
	}

	private String bonus(ArrayList<String> words) {
		String output = "";
		if(words.get(0).equalsIgnoreCase("barrel") && words.get(1).equalsIgnoreCase("roll")) {
			output += "Do a barrel roll! \n";
		} else if(words.get(0).equalsIgnoreCase("jump")) {
			output += "You jump up into the air and hit the ground with a thud. \n";
		}
		return output;
	}

	private String help() {
		ArrayList<String> help = new ArrayList<String>();
		help.add("Sorry, I don't have any help for you at the moment.");
		help.add("Typing \"help\" will return some helpful tips!");
		help.add("You can inspect the room that you're in to recieve its long description again.");
		help.add("The amount of score, experience, and currency that you gain can be increased by having a higher intellect stat and accessory quality.");
		help.add("You get nothing! You lose! Good day, sir!");
		help.add("Say that again?");
		help.add("You called for help.");
		Random rand = new Random();
		return help.get(rand.nextInt(help.size())) + "\n";
	}
}
