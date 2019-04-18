package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.movement.Room;

public class RoomTest {
	
	private Room room;
	
	@Before
	public void setup() {
		room = new Room();
	}

	/**
	 * checks to see if each monster is properly added and has a unique id
	 */
	@Test
	public void testPopulate() {
		assertTrue(false);
		// TODO: use actual NPC objects instead of strings
		/*
		ArrayList<String> monsters = new ArrayList<String>();
		monsters.add("rat");
		monsters.add("squid");
		monsters.add("rat");
		
		room.populate(monsters);
		assertTrue(room.getNPC(1).getName() == "rat");
		assertTrue(room.getNPC(2).getName() == "squid");
		assertTrue(room.getNPC(3).getName() == "rat");
		*/
	}
	
	@Test
	public void testRoomComplete() {
		assertTrue(false);
		// TODO: use actual NPC objects instead of strings
		/*
		ArrayList<String> monsters = new ArrayList<String>();
		monsters.add("ghost");
		monsters.add("skeleton");
		
		room.populate(monsters);
		assertFalse(room.roomComplete());
		
		room.monsterKilled("skeleton");
		assertFalse(room.roomComplete());
		
		room.monsterKilled("ghost");
		assertTrue(room.roomComplete());
		*/
	}

}
