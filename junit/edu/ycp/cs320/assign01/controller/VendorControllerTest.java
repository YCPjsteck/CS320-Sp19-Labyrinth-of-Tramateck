package edu.ycp.cs320.assign01.controller;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Library;

public class VendorControllerTest {
	VendorController controller;
	PlayerController pController;
	Player player;
	Library library;
	WorldMap game;
	ArrayList<Item> itemList;
	
	@Before
	public void setup() {
		player = new Player();
		library = new Library();
		try {
			library.generateItems("test items.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		itemList = library.getItems();
		game = library.generateWorld();
		controller = new VendorController(game, player, itemList);
		pController = new PlayerController(player, itemList);
	}
	
	@Test
	public void talkToNPCTest() {
		System.out.println("TESTING VENDORCONTROLLER TALK TO NPC");
		game.setPlayer(1);
		game.curLocation().findStart();
		controller.updateLocation();
		controller.updateRoom();

		System.out.println(controller.control("Talk to"));
		System.out.println(controller.control("Talk to rat"));
		game.curLocation().travel("west");
		controller.updateRoom();
		System.out.println(controller.control("Talk to rat"));
		System.out.println(controller.control("Talk to jaguar"));
		
		game.curLocation().travel("east");
		game.curLocation().travel("east");
		controller.updateRoom();
		NPC npc = game.curLocation().curRoom().getNPCs().get(0);
		
		assertTrue(npc.getName().equals("Rat"));
		
		System.out.println(controller.control("Talk to rat"));
		npc.generateInventory();
		System.out.println(controller.control("Talk to rat"));
	}
	
	@Test
	public void buyTest() {
		System.out.println("TESTING VENDORCONTROLLER BUY");
		game.setPlayer(1);
		game.curLocation().findStart();
		controller.updateLocation();
		controller.updateRoom();
		game.curLocation().travel("east");
		controller.updateRoom();
		NPC npc = game.curLocation().curRoom().getNPCs().get(0);
		assertTrue(npc.getName().equals("Rat"));
		npc.generateInventory();
		System.out.println(pController.control("inventory"));
		System.out.println(controller.control("Talk to rat"));
		System.out.println(controller.control("buy"));
		System.out.println(controller.control("buy 200 monkey paw"));
		System.out.println(controller.control("buy 20 monkey paw"));
		player.changeCurrency(300);
		System.out.println(controller.control("buy 20 monkey paw"));
		System.out.println(pController.control("currency"));
		System.out.println(pController.control("inventory"));
		System.out.println(controller.control("talk to rat"));
	}
	
	@Test
	public void sellTest() {
		System.out.println("TESTING VENDORCONTROLLER SELL");
		game.setPlayer(1);
		game.curLocation().findStart();
		controller.updateLocation();
		controller.updateRoom();
		game.curLocation().travel("east");
		controller.updateRoom();
		NPC npc = game.curLocation().curRoom().getNPCs().get(0);
		assertTrue(npc.getName().equals("Rat"));
		npc.generateInventory();
		System.out.println(controller.control("Talk to rat"));
		System.out.println(controller.control("sell"));
		System.out.println(controller.control("sell 200 monkey legs"));
		System.out.println(controller.control("sell 20 personal shield"));
		Item item = itemList.get(6);
		System.out.println(item.getName());
		player.addItem(item, 5);
		System.out.println(pController.control("inventory"));
		System.out.println(controller.control("sell 5 medkit"));
		System.out.println(pController.control("inventory"));
		System.out.println(pController.control("currency"));
		System.out.println(controller.control("Talk to rat"));
	}
}
