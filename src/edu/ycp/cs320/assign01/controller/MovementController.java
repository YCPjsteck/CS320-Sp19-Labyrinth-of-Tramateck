package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class MovementController {
	private Set<String> moveTargets;
	private WorldMap game;
	private Location location;
	private Room room;
	
	public MovementController(WorldMap game) {
		moveTargets = new TreeSet<String>();
		moveTargets.add("north");
		moveTargets.add("south");
		moveTargets.add("east");
		moveTargets.add("west");
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
	 * Changes the game state according to the player's movement input
	 * @param input The player's input
	 * @return output The game's output
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
			if(words.size() > 2) {
				output += "Looks like you typed a bit too much.\n";
				test = false;
			}
			if(words.size() < 2) {
				output += "Missing movement target.\n";
				test = false;
			}
			if(words.size() > 1 && !moveTargets.contains(words.get(1))) {
				output +=  "This is not a movement target.\n";
				test = false;
			}
			if(!room.roomComplete() && words.get(0).equals("move")) {
				output += "This room is not clear.\n";
				test = false;
			}
			if(test && words.size() > 1 && !location.canTravel(words.get(1))) {
				output += "Can not travel in that direction.\n";
				test = false;
			}
			
			// If the input is valid, move the player and get the output
			if(test) {
				location.travel(words.get(1));
				if(words.get(0).equals("move"))
					output += "Traveled " + words.get(1) + ".\n";
				else if(!room.roomComplete()) // TODO if the player runs, they recieve final damage
					output += "Ran from combat " + words.get(1) + ".\n";
				else
					output += "Ran " + words.get(1) + ".\n";
				updateRoom();
				if(!room.getEntered()) {
					output += "Room long: " + room.getLongDesc() + "\n";
					room.isEntered();
				} else {
					output += "Room short: " + room.getShortDesc() + "\n";
				}
				output += location.getMapString();
			}
		}
		if(output.equals(""))
			output += "This is not a command. \n";
		return output;
	}
}
