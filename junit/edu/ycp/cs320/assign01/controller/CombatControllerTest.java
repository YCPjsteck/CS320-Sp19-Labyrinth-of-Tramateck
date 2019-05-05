package edu.ycp.cs320.assign01.controller;

import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Library;
import edu.ycp.cs320.assign01.model.utility.WordFinder;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CombatControllerTest {
	CombatController controller;
	WorldMap game;
	Library library;
	Player player;
	
	@Before
	public void setup() {
		library = new Library();
		player = new Player();
		game = library.generateWorld();
		controller = new CombatController(game, player);
	}
	
	@Test
	public void attackTest() {
		game.setPlayer(1);
		game.curLocation().findStart();
		game.curLocation().travel("west");
		controller.updateRoom();
		NPC npc = game.curLocation().curRoom().getNPCs().get(0);
		
		assertTrue(game.curLocation().curRoom().getNPCs().size() > 0);
		assertTrue(npc.getName().equals("Jaguar"));
		
		String output = controller.attack("attack the jaguar");
		System.out.println(output);
		
		output = controller.attack("attack jaguar tail");
		System.out.println(output);

		while(!npc.isDead() && !player.isDead()) {
			output = controller.attack("attack jaguar head");
			System.out.println(output);
		}
	}
}
