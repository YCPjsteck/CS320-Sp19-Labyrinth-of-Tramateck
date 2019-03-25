package edu.ycp.cs320.assign01.controller;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import edu.ycp.cs320.assign01.model.Game;

public class GameController {
	private Game model;
	private Set<String> actions, moveLocations, attackLocations, attackModifiers;
	private ArrayList<String> gameLog;
	
	public void setModel(Game model) {
		this.model = model;
	}
	
	public String getGameLog() {
		String log = "";
		for (String str : gameLog) {
			log += "" + str + "\n";
		}
		return log;
	}
	
	public void actionSet(String input) {
		loadActions();
		preAction();
		Action(input);
		postAction();
	}
	
	private void preAction() {
		gameLog.addAll(model.getDungeon().getMapString());
		attackLocations.addAll(model.getDungeon().curRoom().getMonsters());
		gameLog.add(model.getDungeon().curRoom().getDescription());
	}
	
	private void Action(String input) {
		// Split up the player's input into multiple words.
		ArrayList<String> words = findWord(input);
		
		// Determine if the first word is a proper action
		if(actions.contains(words.get(0))) {
			// System.out.println("This is an action.");
			if(words.get(0).equals("move")) {
				boolean test = action(words, moveLocations);
				if(test && model.getDungeon().curRoom().roomComplete() && model.getDungeon().canTravel(words.get(1))) {
					model.getDungeon().travel(words.get(1));
					gameLog.add("Traveled " + words.get(1));
				} else {
					if(!model.getDungeon().curRoom().roomComplete())
						gameLog.add("There are still monsters to fight.");
					if(!model.getDungeon().canTravel(words.get(1)))
						gameLog.add("Can not travel in that direction.");
				}
			}
			else if(words.get(0).equals("attack")) {
				boolean test = action(words, attackLocations, attackModifiers);
				if(test) {
					//Monster monster = dungeon.curRoom().getMonster(words.get(1));
					//monster.changeHealth(-1*player.attack());
					model.getDungeon().curRoom().monsterKilled(words.get(1));
					gameLog.add("You killed " + words.get(1));
					/*
					System.out.println("You attacked the " + words.get(1));
					player.changeHealth(-1*monster.attack());
					System.out.println("You lost " + monster.attack() + " health.");
					if(monster.isDead()) {
						System.out.println("The " + monster.getName() + " is dead.");
					}
					*/
				}
			}
		} else {
			gameLog.add("This is not an action.");
		}
	}
	
	private void postAction() {
		if(model.getDungeon().dungeonComplete())
			gameLog.add("You've killed all the monsters in this dungeon.");
		if(model.getPlayer().isDead()) {
			gameLog.add("You died.");
		}
	}
	
	private void loadActions() {
		gameLog = new ArrayList<String>();
		
		// Actions would be always accessible
		// New actions could be added though over time
		actions = new TreeSet<String>();
		actions.add("move");
		actions.add("attack");
		
		// Move locations would be always accessible
		moveLocations = new TreeSet<String>();
		moveLocations.add("north");
		moveLocations.add("south");
		moveLocations.add("east");
		moveLocations.add("west");
		
		// Monsters would be added to the attackLocations by the dungeon class,
		// probably using addAll to add a collection of the monster names.
		attackLocations = new TreeSet<String>();
		
		// Attack modifiers would be queried from the monster class being attacked,
		// not added like this to a set. e.g. if the player is attacking "ghoul",
		// ask the dungeon class to give the attack points for the ghoul monster.
		attackModifiers = new TreeSet<String>();
		attackModifiers.add("head");
		attackModifiers.add("left leg");
		attackModifiers.add("right leg");
		attackModifiers.add("arm");
		attackModifiers.add("chest");
	}
	
	private ArrayList<String> findWord(String input) {
		ArrayList<String> words = new ArrayList<String>();
		input = input.trim().toLowerCase();
		while(!input.equals("")) {
			int space = input.indexOf(" ");
			if(space == -1) {
				words.add(input);
				input = "";
			} else {
				words.add(input.substring(0, space));
				input = input.substring(space).trim();
			}
		}
		return words;
	}

	private boolean action(ArrayList<String> input, Set<String> locations) {
		if(input.size() > 1 && locations.contains(input.get(1))) {
			if(input.size() > 2) {
				gameLog.add("Looks like you typed a bit too much.");
				return false;
			}
			return true;
		} else {
			if(input.size() > 1)
				gameLog.add("This is not a location.");
			else
				gameLog.add("Missing action location.");
			return false;
		}
	}
	private boolean action(ArrayList<String> input, Set<String> locations, Set<String> modifiers) {
		if(input.size() > 1 && locations.contains(input.get(1))) {
			// For one word modifiers
			if(input.size() > 2 && modifiers.contains(input.get(2))) {
				if(input.size() > 3) {
					gameLog.add("Looks like you typed a bit too much.");
					return false;
				}
				gameLog.add("You went for the " + input.get(2) + "!");
				return true;
				
			// For two word modifiers
			} else if(input.size() > 3 && modifiers.contains(input.get(2) + " " + input.get(3))) {
				if(input.size() > 4) {
					gameLog.add("Looks like you typed a bit too much.");
					return false;
				}
				gameLog.add("You went for the " + input.get(2) + " " + input.get(3) + "!");
				return true;
			} else if (input.size() > 2) {
				gameLog.add("This is not a modifier.");
				return false;
			}
			return true;
		} else {
			if(input.size() > 1)
				gameLog.add("This is not a location.");
			else
				gameLog.add("Missing action location.");
			return false;
		}
	}
}