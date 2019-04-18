package edu.ycp.cs320.assign01.model.game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;

public class GameTest {
	public static Set<String> actions, moveLocations, attackLocations, attackModifiers;
	
	public static void main(String[] args) {
		
		Player player = new Player();
		
		// Create the dungeon using the following map. Set the player's location
		// to room 1. 
		// TODO: possibly overload setPlayer to take in a room ID instead of map coordinates
		// Rotate this map 90 degrees clockwise and mirror it to get the actual map
		// TODO: generate the locations from the library instead of making one.
		Location dungeon = new Location();
		/*
		int[][] map = {	{0, 0, 4},
						{1, 2, 3},
						{0, 0, 5}};
		dungeon.setMap(map);
		dungeon.generateRooms();
		dungeon.setPlayer(1, 0);
		*/
		
		// TODO create actual NPC objects to populate the rooms with.
		/*
		ArrayList<String> monsters = new ArrayList<String>();
		
		monsters.add("zombie");
		Room room = dungeon.getRoom(2);
		room.populate(monsters);
		monsters.clear();
		
		monsters.add("skeleton");
		room = dungeon.getRoom(3);
		room.populate(monsters);
		monsters.clear();
		
		monsters.add("ghoul");
		monsters.add("witch");
		room = dungeon.getRoom(4);
		room.populate(monsters);
		monsters.clear();
		*/
		
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
		
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		while(true) {
			// At the beginning of each action, print the map
			// and make sure the attackLocations are populated.
			dungeon.printMap();
			attackLocations.addAll(dungeon.curRoom().getNPCNames());
			System.out.println(dungeon.curRoom().getLongDesc());
			
			// Ask the player to enter a command. If the command is "quit",
			// then terminate the program.
			System.out.println("Enter a command.");
			String input = reader.nextLine();
			if(input.equals("quit"))
				return;
			
			// Split up the player's input into multiple words.
			ArrayList<String> words = findWord(input);
			
			// Determine if the first word is a proper action
			if(actions.contains(words.get(0))) {
				// System.out.println("This is an action.");
				if(words.get(0).equals("move")) {
					boolean test = action(words, moveLocations);
					if(test && dungeon.curRoom().roomComplete() && dungeon.canTravel(words.get(1))) {
						dungeon.travel(words.get(1));
						System.out.println("Traveled " + words.get(1));
					} else {
						if(words.size() > 1) {
							if(!dungeon.curRoom().roomComplete())
								System.out.println("There are still monsters to fight.");
							if(!dungeon.canTravel(words.get(1)))
								System.out.println("Can not travel in that direction.");
						}
					}
				}
				if(words.get(0).equals("attack")) { // TODO handle combat in a different method/controller
					boolean test = action(words, attackLocations, attackModifiers);
					if(test) {
						//Monster monster = dungeon.curRoom().getMonster(words.get(1));
						//monster.changeHealth(-1*player.attack());
						//dungeon.curRoom().monsterKilled(words.get(1));
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
				System.out.println("This is not an action.");
			}
			if(dungeon.dungeonComplete())
				System.out.println("You've killed all the monsters in this dungeon.");
			if(player.isDead()) {
				System.out.println("You died.");
				return;
			}
			System.out.println();
		}
	}

	public static ArrayList<String> findWord(String input) {
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

	public static boolean action(ArrayList<String> input, Set<String> locations) {
		if(input.size() > 1 && locations.contains(input.get(1))) {
			if(input.size() > 2) {
				System.out.println("Looks like you typed a bit too much.");
				return false;
			}
			return true;
		} else {
			if(input.size() > 1)
				System.out.println("This is not a location.");
			else
				System.out.println("Missing action location.");
			return false;
		}
	}
	public static boolean action(ArrayList<String> input, Set<String> locations, Set<String> modifiers) {
		if(input.size() > 1 && locations.contains(input.get(1))) {
			// For one word modifiers
			if(input.size() > 2 && modifiers.contains(input.get(2))) {
				if(input.size() > 3) {
					System.out.println("Looks like you typed a bit too much.");
					return false;
				}
				System.out.println("You went for the " + input.get(2) + "!");
				return true;
				
			// For two word modifiers
			} else if(input.size() > 3 && modifiers.contains(input.get(2) + " " + input.get(3))) {
				if(input.size() > 4) {
					System.out.println("Looks like you typed a bit too much.");
					return false;
				}
				System.out.println("You went for the " + input.get(2) + " " + input.get(3) + "!");
				return true;
			} else if (input.size() > 2) {
				System.out.println("This is not a modifier.");
				return false;
			}
			return true;
		} else {
			if(input.size() > 1)
				System.out.println("This is not a location.");
			else
				System.out.println("Missing action location.");
			return false;
		}
	}
}