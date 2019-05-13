package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.movement.Room;

public class RoomTest {
	
	private Room room;
	private NPC npc1, npc2, npc3;
	
	@Before
	public void setup() {
		room = new Room();
		npc1 = new NPC();
		npc2 = new NPC();
		npc3 = new NPC();
		npc1.setName("bird");
		npc1.setId(1);
		npc2.setName("squid");
		npc2.setId(2);
		npc3.setName("rat");
		npc3.setId(3);
	}

	/**
	 * Test adds three NPCs to test room
	 * checks to see if each NPC is in the room by it's name
	 */
	@Test
	public void testPopulate() {
		room.addNPC(npc1);
		room.addNPC(npc2);
		room.addNPC(npc3);
		
		assertTrue(room.getNPC(1).getName() == "bird");
		assertTrue(room.getNPC(2).getName() == "squid");
		assertTrue(room.getNPC(3).getName() == "rat");
	}
	
	/**
	 * Test roomComplete method of room
	 * 		-adds 3 NPCs to room
	 * 			-sets each NPC's level to 1
	 * 			-sets each NPC's baseHealth to 10 and calculates health
	 * 		-checks to see room is not complete until every NPC is dead
	 */
	@Test
	public void testRoomComplete() {
		room.addNPC(npc1);
		room.addNPC(npc2);
		room.addNPC(npc3);
		for(int i = 1; i <= 3; i++) {
			room.getNPC(i).setLevel(1);
			room.getNPC(i).setHealth(10);
			room.getNPC(i).calHealth();
			room.getNPC(i).setType("hostile");
		}
		
		assertFalse(room.roomComplete());
		System.out.println(room.getShortDesc());
		
		System.out.println("\nDeal 100 damage to NPC1");
		room.getNPC(1).changeHealth(-100);
		
		assertFalse(room.roomComplete());
		System.out.println(room.getShortDesc());
		System.out.println("\nDeal 100 damage to NPC2");
		room.getNPC(2).changeHealth(-100);
		
		assertFalse(room.roomComplete());
		System.out.println(room.getShortDesc());
		System.out.println("\nDeal 100 damage to NPC3");
		room.getNPC(3).changeHealth(-100);
		
		assertTrue(room.roomComplete());
		System.out.println(room.getShortDesc());
	}

}
