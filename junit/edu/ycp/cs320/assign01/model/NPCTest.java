package edu.ycp.cs320.assign01.model;

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
