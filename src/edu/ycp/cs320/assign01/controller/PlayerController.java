package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.enums.ItemType;
import edu.ycp.cs320.assign01.model.Consumable;
import edu.ycp.cs320.assign01.model.Equipment;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class PlayerController {
	private Player player;
	private ArrayList<Item> itemList;
	
	public PlayerController(Player player, ArrayList<Item> itemList) {
		this.player = player;
		this.itemList = itemList;
	}
	
	/**
	 * Handles the following commands
	 * 		* equip [item]
	 * 		* consume [item]
	 * 		* unequip [item/"armor"/"weapon"/"accessory"]
	 * 		* inventory
	 * 		* equipped/equipment
	 * 		* stats
	 * 		* level/experience
	 * 		* currency
	 * 		* score
	 * 		* health
	 * 		* dexterity
	 * 		* intellect
	 * 		* strength
	 */
	public String control(String input) {
		String output = "";
		WordFinder finder = new WordFinder();
		ArrayList<String> words = finder.findWords(input);
		
		if(words.get(0).equals("equip") || words.get(0).equals("consume")) {
			// Test that the input is valid.
			// Test 1: Is this an item that exists?
			// Test 2: Is this an item that the player has?
			// Test 3: Is this item able to be equipped or consumed?
			String name = input.substring(words.get(0).length()).trim();
			boolean test = itemCheck(name);
			Item item = null;
			if(!test) {
				output += "\"" + name + "\" is not an item. \n";
			} else {
				ArrayList<Pair<Item,Integer>> inv = player.getInventory();
				test = false;
				for(Pair<Item,Integer> pair : inv) {
					if(pair.getLeft().getName().equalsIgnoreCase(name)) {
						test = true;
						item = pair.getLeft();
					}
				}
				if(!test) {
					output += "You do not have \"" + name + "\" in your inventory. \n";
				} else {
					ItemType type = item.getType();
					if(words.get(0).equals("consume")) {
						if(type != ItemType.CONSUMABLE) {
							output += "\"" + name + "\" can not be consumed. \n";
							test = false;
						}
					} else if(words.get(0).equals("equip")) {
						if(type != ItemType.ARMOR && type != ItemType.WEAPON && type != ItemType.ACCESSORY) {
							output += "\"" + name + "\" can not be equipped. \n";
							test = false;
						}
					}
				}
			}
			
			// If all the tests have passed, equip or consume the given item
			if(test) {
				if(words.get(0).equals("consume")) {
					player.consume((Consumable)item);
					output += "You consumed the \"" + item.getName() + ".\" \n";
				} else if(words.get(0).equals("equip")) {
					player.equip((Equipment)item);
					output += "You equipped the \"" + item.getName() + ".\" \n";
				}
			}
		} else if(words.get(0).equals("unequip")) {
			String name = input.substring(words.get(0).length()).trim();
			if(words.size() == 1) {
				output += "\"\" is not an item. \n";
			} else if(words.get(1).equals("armor") || (player.getArmorID() != 0 && player.getArmor().getName().equalsIgnoreCase(name))) {
				if(player.getArmorID() != 0) {
					output += "You unequipped your armor, the \"" + player.getArmor().getName() + ".\" \n";
					player.unequipArmor();
				} else {
					output += "You do not have any armor equipped. \n";
				}
			} else if(words.get(1).equals("weapon") || (player.getWeaponID() != 0 && player.getWeapon().getName().equalsIgnoreCase(name))) {
				if(player.getWeaponID() != 0) {
					output += "You unequipped your weapon, the \"" + player.getWeapon().getName() + ".\" \n";
					player.unequipWeapon();
				} else {
					output += "You do not have any weapon equipped. \n";
				}
			} else if(words.get(1).equals("accessory") || (player.getAccessoryID() != 0 && player.getAccessory().getName().equalsIgnoreCase(name))) {
				if(player.getAccessoryID() != 0) {
					output += "You unequipped your accessory, the \"" + player.getAccessory().getName() + ".\" \n";
					player.unequipAccessory();
				} else {
					output += "You do not have any accessory equipped. \n";
				}
			} else {
				boolean test = itemCheck(name);
				if(test) {
					output += "You do not have \"" + name + "\" equipped. \n";
				} else {
					output += "\"" + name + "\" is not an item. \n";
				}
			}
		} else if(words.size() == 1) {
			if(words.get(0).equals("inventory")) {
				ArrayList<Pair<Item,Integer>> inv = player.getInventory();
				if(inv.isEmpty()) {
					output += "You have nothing in your inventory. \n";
				}
				for(Pair<Item,Integer> pair : inv) {
					output += pair.getRight() + " " + pair.getLeft().getName() + ": " + pair.getLeft().getShortDesc() + " \n";
				}
			} else if(words.get(0).equals("equipped") || words.get(0).equals("equipment")) {
				if(player.getArmorID() == 0)
					output += "Armor: none\n";
				else
					output += "Armor: " + player.getArmor().getName() + "\n";
				if(player.getWeaponID() == 0)
					output += "Weapon: none\n";
				else
					output += "Weapon: " + player.getWeapon().getName() + "\n";
				if(player.getAccessoryID() == 0)
					output += "Accessory: none\n";
				else
					output += "Accessory: " + player.getAccessory().getName() + "\n";
			} else if(words.get(0).equals("stats")) {
				output += "Health: " + player.getHealth() + "/" + player.getMaxHealth() + "\n";
				output += "Level: " + player.getLevel() + ", " + player.getExperience() + "/1000 exp\n";
				output += "Currency: $" + player.getCurrency() + "\n";
				output += "Intellect: " + player.getIntellect() + "\n";
				output += "Dexterity: " + player.getDexterity() + "\n";
				output += "Strength: " + player.getStrength() + "\n";
				output += "Score: " + player.getScore() + "\n";
			} else if(words.get(0).equals("level") || words.get(0).equals("experience")) {
				output += "Level: " + player.getLevel() + ", " + player.getExperience() + "/1000 exp\n";
			} else if(words.get(0).equals("score")) {
				output += "Score: " + player.getScore() + "\n";
			} else if(words.get(0).equals("currency")) {
				output += "Currency: $" + player.getCurrency() + "\n";
			} else if(words.get(0).equals("health")) {
				output += "Health: " + player.getHealth() + "/" + player.getMaxHealth() + "\n";
			} else if(words.get(0).equals("intellect")) {
				output += "Intellect: " + player.getIntellect() + "\n";
			} else if(words.get(0).equals("dexterity")) {
				output += "Dexterity: " + player.getDexterity() + "\n";
			} else if(words.get(0).equals("strength")) {
				output += "Strength: " + player.getStrength() + "\n";
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
