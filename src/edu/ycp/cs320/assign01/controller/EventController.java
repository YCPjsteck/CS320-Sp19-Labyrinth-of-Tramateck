package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.Event;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class EventController {
	private Player player;
	private WorldMap game;
	private Location location;
	private Room room;
	
	public EventController(WorldMap game, Player player) {
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
	
	public String control(String input) {
		String output = "";
		WordFinder finder = new WordFinder();
		ArrayList<String> words = finder.findWords(input);
		if(words.get(0).equals("action")) {
			boolean test = true;
			if(room.eventsClear()) {
				output += "There is no event in this room to take action on. \n";
				test = false;
			}
			if(test && words.size() < 2) {
				output += "Missing action. \n";
				test = false;
			}
			if(test && !words.get(1).equals("a") && !words.get(1).equals("b")) {
				output += "This is not a valid action. \n";
				test = false;
			}
			
			if(test) {
				Event event = room.curEvent();
				event.setPlayer(player);
				if(words.get(1).equals("a")) {
					event.roll(true);
				} else {
					event.roll(false);
				}
				output += event.getDialogue() + "\n";
				/**
				 * key 1 = A pass
				 * key 2 = A fail
				 * key 3 = B pass
				 * key 4 = B fail
				 */
				Pair<Integer,Integer> pair = null;
				if(event.getKey() == 1) {
					pair = event.getAPassPair();
				} else if(event.getKey() == 2) {
					pair = event.getAFailPair();
				} else if(event.getKey() == 3) {
					pair = event.getBPassPair();
				} else if(event.getKey() == 4) {
					pair = event.getBFailPair();
				}
				
				switch (pair.getLeft()) {
					case 0:
						player.changeHealth(pair.getRight());
						if(pair.getRight() < 0) {
							output += "You lost " + -pair.getRight() + " health.\n";
						} else {
							output += "You gained " + pair.getRight() + " health.\n";
						}
						break;
					case 1:
						player.changeStrength(pair.getRight());
						if(pair.getRight() < 0) {
							output += "You lost " + -pair.getRight() + " strength.\n";
						} else {
							output += "You gained " + pair.getRight() + " strength.\n";
						}
						break;
					case 2:
						player.changeIntellect(pair.getRight());
						if(pair.getRight() < 0) {
							output += "You lost " + -pair.getRight() + " intellect.\n";
						} else {
							output += "You gained " + pair.getRight() + " intellect.\n";
						}
						break;
					case 3:
						player.changeDexterity(pair.getRight());
						if(pair.getRight() < 0) {
							output += "You lost " + -pair.getRight() + " dexterity.\n";
						} else {
							output += "You gained " + pair.getRight() + " dexterity.\n";
						}
						break;
					default:
						break;
				}
			}
		}
		return output;
	}
}
