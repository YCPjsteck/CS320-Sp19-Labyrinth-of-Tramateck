package edu.ycp.cs320.assign01.model.game;

import java.util.ArrayList;
import java.util.Scanner;

import edu.ycp.cs320.assign01.controller.MetaController;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Library;

public class GameTest {
	private static Player player;
	private static WorldMap game;
	private static Library library;
	private static MetaController controller;
	private static ArrayList<Item> items;

	// TODO: Tell the player if the location is totally complete (hostile NPCs and all events done)
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
		game.curLocation().start();
		game.curLocation().curRoom().isEntered();

		game.grantAccess(1);
		game.grantAccess(2);
		
		System.out.println(game.curLocation().curRoom().getLongDesc());
		System.out.println(game.curLocation().getMapString());
		
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		while(true) {
			// Ask the player to enter a command. If the command is "quit",
			// then terminate the program.
			System.out.println("Enter a command.");
			String input = reader.nextLine();
			if(input.equals("quit"))
				return;
			System.out.println(controller.control(input));
			if(player.isDead()) {
				player.calHealth();
				System.out.println(game.curLocation().getMapString());
			}
		}
	}
}