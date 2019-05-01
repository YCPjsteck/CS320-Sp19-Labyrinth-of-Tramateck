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
	
	@Test
	public void testSetHealth() {
		npc.setLevel(1);
		npc.setHealth(10);
		assertTrue(npc.getBaseHealth() == 10);
		npc.setHealth(35);
		assertFalse(npc.getBaseHealth() == 10);
		assertTrue(npc.getBaseHealth() == 35);
	}
	
	@Test
	public void testCalHealth() {
		npc.setLevel(1);
		npc.setHealth(55);
		assertFalse(npc.getHealth() == npc.getMaxHealth());
		npc.calHealth();
		assertTrue(npc.getHealth() == npc.getMaxHealth());
	}
	
	@Test
	public void testChangeHealth() {
		npc.setLevel(1);
		npc.setHealth(23);
		npc.changeHealth(10);
		assertTrue(npc.getHealth() == 10);
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
	
	@Test
	public void testGetAllLoot() {
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
	
	@Test
	public void testNPCCopyConstructor() {
		npc.setHealth(10);
		npc.setType("Hostile");
		Item item1 = new Item();
		item1.setName("Test Item 1");
		Item item2 = new Item();
		item2.setName("Test Item 2");
		npc.addLoot(item1, 100, 1);
		npc.addInventory(item2, 100, 1);
		npc.setMinAttack(1);
		npc.setMaxAttack(2);
		npc.setShortDesc("A test NPC.");
		npc.setLongDesc("A test NPC used for finding out if the copy constructor is properly working for NPCs.");
		npc.setName("Test NPC");
		
		NPC copy = new NPC(npc);
		
		assertTrue(npc.getType() == copy.getType());
		assertTrue(npc.getFullInventory().get(0).getLeft().getName().equals(copy.getFullInventory().get(0).getLeft().getName()));
		assertTrue(npc.getAllLoot().get(0).getLeft().getName().equals(copy.getAllLoot().get(0).getLeft().getName()));
		assertTrue(npc.getName().equals(copy.getName()));
		assertTrue(npc.getMinAttack() == copy.getMinAttack());
		assertTrue(npc.getMaxAttack() == copy.getMaxAttack());
		assertTrue(npc.getBaseHealth() == copy.getBaseHealth());
		assertTrue(npc.getShortDesc().equals(copy.getShortDesc()));
		assertTrue(npc.getLongDesc().equals(copy.getLongDesc()));
	}
}
