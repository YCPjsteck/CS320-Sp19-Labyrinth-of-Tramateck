package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import edu.ycp.cs320.assign01.model.Item;
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
		movementCon = new MovementController(game);
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
		}
		if(output.equals(""))
			output += "This is not a command. \n";
		
		return output;
	}

	private String bonus(ArrayList<String> words) {
		// TODO Auto-generated method stub
		return null;
	}

	private String help() {
		// TODO Auto-generated method stub
		return null;
	}
}
