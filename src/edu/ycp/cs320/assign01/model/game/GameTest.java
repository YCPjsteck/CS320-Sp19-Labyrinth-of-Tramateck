package edu.ycp.cs320.assign01.model.game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import edu.ycp.cs320.assign01.controller.MetaController;
import edu.ycp.cs320.assign01.model.Equipment;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Library;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

public class GameTest {
	private static Player player;
	private static WorldMap game;
	private static Library library;
	private static MetaController controller;
	private static ArrayList<Item> items;

	// TODO: Items have a copy constructor so that various items of the same type can have different level
	// TODO: NPCs set the item levels that they have
	// TODO: Vendor loot is their inventory, and vendors are tough enemies
	// TODO: Upon reaching an exit, be asked if you want to leave, being sent to the "overworld" to see other locations
	public static void main(String[] args) {
		player = new Player();
		library = new Library();
		game = library.generateWorld();
		items = library.getItems();
		controller = new MetaController(game, player, items);

		// Initialize the player's gear
		player.setLevel(1);
		player.addItem(items.get(3));
		player.addItem(items.get(4));
		player.addItem(items.get(5));
		player.addItem(items.get(6),3);
		
		// Set the player's location for the start of the game
		game.setPlayer(1);
		game.curLocation().findStart();
		
		System.out.println("You crash land your ship on an alien jungle planet. \n" +
							"Most of your gear has been destroyed, save for a damaged laser pistol, personal shield, battery pack, and some medkits. \n" +
							"Survive.\n");
		game.curLocation().printMap();
		
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		while(!player.isDead()) {
			// Ask the player to enter a command. If the command is "quit",
			// then terminate the program.
			System.out.println("Enter a command.");
			String input = reader.nextLine();
			if(input.equals("quit"))
				return;
			System.out.println(controller.control(input));
		}
		System.out.println("Game over.");
	}
}