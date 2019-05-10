package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.utility.Pair;

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
	
	@Test
	public void testHealthOvercharge() {
		player.setHealth(100);
		player.setLevel(1);
		player.calHealth();
		player.setHealth(90);
		player.changeHealth(0);
		
		assertTrue(player.getHealth() == 90);
	}
	
	@Test
	public void testConsume() {
		Consumable item = new Consumable();
		item.setName("Test Juice");
		item.setLevelChange(2);
		item.setIntellect(5);
		item.setDexterity(5);
		item.setStrength(5);

		player.setLevel(1);
		player.setIntellect(0);
		player.setStrength(0);
		player.setDexterity(0);
		
		player.addItem(item);
		assertTrue(player.getInventory().size() == 1);
		
		player.consume(item);
		assertTrue(player.getInventory().size() == 0);
		assertTrue(player.getLevel() == 3);
	}
	
	@Test
	public void testContainsItemSingleQuantity() {
		Item item = new Item();
		item.setName("Test Item");
		
		player.addItem(item,10);
		
		assertTrue(player.containsItem(item));
	}
	
	@Test
	public void testContainsItem() {
		Item item = new Item();
		item.setName("Test Item");
		
		player.addItem(item,10);
		
		assertTrue(player.containsItem(item,5));
		assertTrue(player.containsItem(item,10));
		assertFalse(player.containsItem(item,11));
		assertFalse(player.containsItem(item,15));
	}
	
	@Test
	public void testAddItemPreexistingItemInInventory() {
		Item item = new Item();
		item.setName("Test Item");

		assertTrue(player.getInventory().size() == 0);
		
		player.addItem(item,10);
		assertTrue(player.getInventory().size() == 1);
		assertTrue(player.getInventory().get(0).getRight() == 10);
		
		player.addItem(item);
		assertTrue(player.getInventory().size() == 1);
		assertTrue(player.getInventory().get(0).getRight() == 11);

		player.addItem(item,9);
		assertTrue(player.getInventory().size() == 1);
		assertTrue(player.getInventory().get(0).getRight() == 20);
	}
	
	@Test
	public void testRemoveItemSingleQuantity() {
		Item item = new Item();
		item.setName("Test Item");
		
		player.addItem(item,10);
		assertTrue(player.getInventory().size() == 1);
		assertTrue(player.getInventory().get(0).getLeft().getName().equals("Test Item"));
		assertTrue(player.getInventory().get(0).getRight() == 10);
		
		Pair<Item,Integer> pair = player.removeItem(item,1);
		assertTrue(pair.getLeft().getName().equals("Test Item"));
		assertTrue(pair.getRight() == 1);

		assertTrue(player.getInventory().size() == 1);
		assertTrue(player.getInventory().get(0).getLeft().getName().equals("Test Item"));
		assertTrue(player.getInventory().get(0).getRight() == 9);
	}
	
	@Test
	public void testRemoveItemLessThanQuantity() {
		Item item = new Item();
		item.setName("Test Item");
		
		player.addItem(item,10);
		assertTrue(player.getInventory().size() == 1);
		assertTrue(player.getInventory().get(0).getLeft().getName().equals("Test Item"));
		assertTrue(player.getInventory().get(0).getRight() == 10);
		
		Pair<Item,Integer> pair = player.removeItem(item,5);
		assertTrue(pair.getLeft().getName().equals("Test Item"));
		assertTrue(pair.getRight() == 5);

		assertTrue(player.getInventory().size() == 1);
		assertTrue(player.getInventory().get(0).getLeft().getName().equals("Test Item"));
		assertTrue(player.getInventory().get(0).getRight() == 5);
	}

	@Test
	public void testRemoveItemExactQuantity() {
		Item item = new Item();
		item.setName("Test Item");

		player.addItem(item,10);
		assertTrue(player.getInventory().size() == 1);
		assertTrue(player.getInventory().get(0).getLeft().getName().equals("Test Item"));
		assertTrue(player.getInventory().get(0).getRight() == 10);
		
		Pair<Item,Integer> pair = player.removeItem(item,10);
		assertTrue(pair.getLeft().getName().equals("Test Item"));
		assertTrue(pair.getRight() == 10);

		assertTrue(player.getInventory().size() == 0);
	}
	
	@Test
	public void testRemoveItemMoreThanQuantity() {
		Item item = new Item();
		item.setName("Test Item");

		player.addItem(item,10);
		assertTrue(player.getInventory().size() == 1);
		assertTrue(player.getInventory().get(0).getLeft().getName().equals("Test Item"));
		assertTrue(player.getInventory().get(0).getRight() == 10);
		
		Pair<Item,Integer> pair = player.removeItem(item,20);
		assertTrue(pair.getLeft().getName().equals("Test Item"));
		assertTrue(pair.getRight() == 10);

		assertTrue(player.getInventory().size() == 0);
	}
	
	@Test
	public void testEquip() {
		Equipment item = new Equipment();
		item.setName("Test Armor");
		item.setType("armor");
		item.setIntellect(1);
		item.setStrength(1);
		item.setDexterity(2);
		item.setHealth(10);

		player.setLevel(2);
		player.setIntellect(0);
		player.setStrength(0);
		player.setDexterity(0);
		player.setHealth(100);
		player.calHealth();
		
		player.addItem(item,2);
		assertTrue(player.getInventory().get(0).getRight() == 2);
		assertTrue(player.getArmor() == null);
		
		player.equip(item);
		assertTrue(player.getInventory().get(0).getRight() == 1);
		assertTrue(player.getArmor() == item);
		assertTrue(player.getHealth() == 200);
		assertTrue(player.getBaseHealth() == 120);
		player.calHealth();
		assertTrue(player.getHealth() == 240);
		assertTrue(player.getIntellect() == 2);
		assertTrue(player.getStrength() == 2);
		assertTrue(player.getDexterity() == 4);
		assertTrue(player.getMaxHealth() == 240);
		player.incrementLevel();
		assertTrue(player.getMaxHealth() == 360);
	}
	
	@Test
	public void testUnequip() {
		Equipment item = new Equipment();
		item.setName("Test Armor");
		item.setType("armor");
		item.setIntellect(1);
		item.setStrength(1);
		item.setDexterity(2);
		item.setHealth(10);

		player.setLevel(2);
		player.setIntellect(0);
		player.setStrength(0);
		player.setDexterity(0);
		player.setHealth(100);
		player.calHealth();
		
		player.addItem(item,2);
		assertTrue(player.getInventory().get(0).getRight() == 2);
		assertTrue(player.getArmor() == null);
		
		player.equip(item);
		assertTrue(player.getInventory().get(0).getRight() == 1);
		assertTrue(player.getArmor() == item);
		
		player.unequipArmor();
		assertTrue(player.getInventory().get(0).getRight() == 2);
		assertTrue(player.getArmor() == null);
		
		assertTrue(player.getHealth() == 200);
		assertTrue(player.getBaseHealth() == 100);
		player.calHealth();
		assertTrue(player.getHealth() == 200);
		assertTrue(player.getIntellect() == 0);
		assertTrue(player.getStrength() == 0);
		assertTrue(player.getDexterity() == 0);
		assertTrue(player.getMaxHealth() == 200);
		player.incrementLevel();
		assertTrue(player.getMaxHealth() == 300);
	}
}