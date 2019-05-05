package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class PlayerController {
	private Player player;
	
	public PlayerController(Player player) {
		this.player = player;
	}
	
	public String control(String input) {
		String output = "";
		WordFinder finder = new WordFinder();
		ArrayList<String> words = finder.findWords(input);
		/*
		 * Equip/Consume test:
		 * 		Is this an item?
		 * 		Do I have this item?
		 * 		Can this item be equipped/consumed?
		 */
		if(words.get(0).equals("equip")) {
			
		} else if(words.get(0).equals("unequip")) {
			
		} else if(words.get(0).equals("consume")) {
			
		} else if(words.size() == 1) {
			if(words.get(0).equals("inventory")) {
				ArrayList<Pair<Item,Integer>> inv = player.getInventory();
				for(Pair<Item,Integer> pair : inv) {
					output += pair.getRight() + " " + pair.getLeft().getName() + "\n";
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
				output += "Currency: " + player.getCurrency() + "\n";
				output += "Intellect: " + player.getIntellect() + "\n";
				output += "Dexterity: " + player.getDexterity() + "\n";
				output += "Strength: " + player.getStrength() + "\n";
				output += "Score: " + player.getScore() + "\n";
			} else if(words.get(0).equals("level") || words.get(0).equals("experience")) {
				output += "Level: " + player.getLevel() + ", " + player.getExperience() + "/1000 exp\n";
			} else if(words.get(0).equals("score")) {
				output += "Score: " + player.getScore() + "\n";
			} else if(words.get(0).equals("currency")) {
				output += "Currency: " + player.getCurrency() + "\n";
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
			output += "Invalid command. \n";
		return output;
	}
}
