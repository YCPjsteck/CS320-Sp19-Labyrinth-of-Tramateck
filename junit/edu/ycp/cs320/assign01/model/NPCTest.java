package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.utility.Pair;

public class NPCTest {
	private NPC npc;
	
	@Before
	public void setUp() {
		npc = new NPC();
	}
	
	
	/**
	 * Tests setHealth method of character:
	 * 		- Sets base health to 10
	 * 		- Checks if value was set
	 * 		- Changes base health value to 35
	 * 		- Checks if value was changed
	 */
	@Test
	public void testSetHealth() {
		npc.setLevel(1);
		npc.setHealth(10);
		assertTrue(npc.getBaseHealth() == 10);
		npc.setHealth(35);
		assertFalse(npc.getBaseHealth() == 10);
		assertTrue(npc.getBaseHealth() == 35);
	}
	
	
	/**
	 * Tests Calculate health method of character:
	 * 		- Sets base health to 55 
	 * 		- current health should still be initial value (10)
	 * 		- checks to see that current health is not max
	 * 		- calHealth sets current health to max
	 * 		- checks to see that current health s max
	 */
	@Test
	public void testCalHealth() {
		npc.setLevel(1);
		npc.setHealth(55);
		assertFalse(npc.getHealth() == npc.getMaxHealth());
		npc.calHealth();
		assertTrue(npc.getHealth() == npc.getMaxHealth());
	}
	
	
	/**
	 * Tests changeHealth method of character:
	 * 		- Sets base health to 23
	 * 		- Adds 10 to NPC's current health
	 * 		- Checks to see if current health is 10 more than previous health
	 * 		- Adds 100 to current health (NPC is level 1 so max health would be 23)
	 * 		- Checks to see if current health is 23
	 * 			* Current health should not be higher than max health
	 * 		- Adds -999 to current health (NPC's health should be less than 0)
	 * 		- Checks to see if NPC is dead 
	 */
	@Test
	public void testChangeHealth() {
		npc.setLevel(1);
		npc.setHealth(23);
		int tempHealth = npc.getHealth();
		npc.changeHealth(10);
		assertTrue(npc.getHealth() == tempHealth + 10);
		npc.changeHealth(100);
		assertTrue(npc.getHealth() == npc.getMaxHealth());
		npc.changeHealth(-999);
		assertTrue(npc.isDead());
	}
	
	@Test
	public void testIsDead() {
		npc.setLevel(1);
		npc.setHealth(23);
		npc.calHealth();
		assertFalse(npc.isDead());
		npc.changeHealth(-23);
		assertTrue(npc.isDead());
		npc.changeHealth(-100);
		assertTrue(npc.isDead());
	}
	
	
	// getLoot not implemented yet
	@Test
	public void testGetLoot() {
		Item item1 = new Item();
		Item item2 = new Item();
		item1.setName("Monkey Paw");
		item2.setName("Monkey Head");
		
		npc.addLoot(item1, 100, 4);
		npc.addLoot(item2, 100, 1);
		
		ArrayList<Pair<Item,Integer>> pairs = npc.getLoot();

		assertTrue(npc.getAllLoot().size() == 2);
		
		assertTrue(pairs.get(0).getLeft().getName().equals("Monkey Paw"));
		assertTrue(pairs.get(0).getRight() == 4);
		
		assertTrue(pairs.get(1).getLeft().getName().equals("Monkey Head"));
		assertTrue(pairs.get(1).getRight() == 1);
	}

}
