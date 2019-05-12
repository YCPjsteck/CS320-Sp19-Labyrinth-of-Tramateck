package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class MovementController {
	private Set<String> moveTargets;
	private Player player;
	private WorldMap game;
	private Location location;
	private Room room;
	
	public MovementController(WorldMap game, Player player) {
		moveTargets = new TreeSet<String>();
		moveTargets.add("north");
		moveTargets.add("south");
		moveTargets.add("east");
		moveTargets.add("west");
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
	
	/**
	 * Handles the following commands
	 * 		* move [direction]
	 * 		* run [direction]
	 */
	// [move/run] [north/south/east/west]
	public String control(String input) {
		String output = "";
		WordFinder finder = new WordFinder();
		ArrayList<String> words = finder.findWords(input);
		if(words.get(0).equals("move") || words.get(0).equals("run")) {
			// Test if the input is valid
			// Test 1: Did the player type too much?
			// Test 2: Did the player type too little?
			// Test 3: Did the player enter a possible direction?
			// Test 4: Is the player able to move from this room?
			// Test 5: Did the player enter a valid direction?
			boolean test = true;
			if(words.size() < 2) {
				output += "Missing movement target.\n";
				test = false;
			}
			if(words.size() > 1 && !words.get(1).equals("to") && !moveTargets.contains(words.get(1))) {
				output +=  "This is not a movement target.\n";
				test = false;
			}
			if(words.size() > 1 && words.get(1).equals("to") && !room.getExit()) {
				output += "This room is not an exit. \n";
				test = false;
			}
			if(!room.eventsClear()) {
				output += "There is an event that still needs finished. \n";
				test = false;
			}
			if(!room.roomClear() && words.get(0).equals("move")) {
				output += "This room is not clear of hostile NPCs.\n";
				test = false;
			}
			if(test && words.size() > 1 && !words.get(1).equals("to") && !location.canTravel(words.get(1))) {
				output += "Can not travel in that direction.\n";
				test = false;
			}
			if(test && words.size() > 1 && words.get(1).equals("to")) {
				int location = 0;
				try {
					location = Integer.parseInt(words.get(2));
				} catch(Exception e) {
					output += "That is not an integer. \n"; 
					test = false;
				}
				if(test && !game.hasAccess(location)) {
					output += "You do not have access to this location. \n";
					test = false;
				}
			}
			
			// If the input is valid, move the player and get the output
			if(test) {
				if(words.get(0).equals("move") && !words.get(1).equals("to")) {
					output += "Traveled " + words.get(1) + ".\n";
					location.travel(words.get(1));
					updateRoom();
				} else if(words.get(0).equals("run")) {
					if(!room.roomClear()) {
						output += "Ran from combat " + words.get(1) + ".\n";
						CombatController combat = new CombatController(game,player);
						output = combat.npcAttack(output);
					} else {
						output += "Ran " + words.get(1) + ".\n";
					}
					location.travel(words.get(1));
					updateRoom();
				} else {
					int location = Integer.parseInt(words.get(2));
					game.setPlayer(location);
					game.curLocation().start();
					output += "You traveled to " + game.curLocation().getName() + ". \n";
					updateLocation();
					updateRoom();
				}
				if(!room.getEntered()) {
					output += room.getLongDesc() + "\n";
					room.isEntered();
				} else {
					output += room.getShortDesc() + "\n";
				}
				if(!room.eventsClear()) {
					output += room.curEvent().getPrompt() + "\n";
					output += "Type \"action\" and then A or B for the first or second action. \n";
				}
				if(room.getExit()) {
					output += "You can leave this location from here. \n";
					
					ArrayList<Integer> access = new ArrayList<Integer>();
					access.addAll(game.getAccess());
					for(int i = 0; i < access.size(); i++) {
						if(access.get(i) == location.getId()) {
							access.remove(i);
							break;
						}
					}
					
					if(access.isEmpty()) {
						output += "But you have no where to go! \n";
					} else {
						output += "You have access to: \n";
						
						for(Integer i : access) {
							output += "	" +  i + ": " + game.getLocation(i).getName() + "\n";
						}
						
						output += "Say \"move to\" and then the number of the location that you want to enter. \n";
					}
				}
				output += location.getMapString();
			}
		}
		if(output.equals(""))
			output += "This is not a command. \n";
		return output;
	}
}
