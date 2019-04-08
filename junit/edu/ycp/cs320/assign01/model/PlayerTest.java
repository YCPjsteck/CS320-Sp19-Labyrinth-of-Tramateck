package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private Player player;
	
	@Before
	public void setUp() {
		player = new Player();
	}
	
	@Test
	public void testChangeHealth() {
		int health = player.getHealth();
		player.changeHealth(-50);
		assertTrue(player.getHealth() == health - 50);
		health = player.getHealth();
		player.changeHealth(25);
		assertTrue(player.getHealth() == health + 25);
	}
	
	@Test
	public void testIsDead() {
		player.changeHealth(-1000);
		assertTrue(player.isDead());
	}
}