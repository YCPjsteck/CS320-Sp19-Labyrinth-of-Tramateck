package edu.ycp.cs320.assign01.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.Consumable;
import edu.ycp.cs320.assign01.model.Equipment;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.utility.Library;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PlayerControllerTest {
	PlayerController controller;
	Player player;
	Library library;
	ArrayList<Item> itemList;
	
	@Before
	public void setup() {
		library = new Library();
		player = new Player();
		try {
			library.generateItems("test items.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		itemList = library.getItems();
		controller = new PlayerController(player,itemList);
	}
	
	@Test
	public void wrongInputTest() {
		System.out.println("TESTING PLAYERCONTROLLER WRONG INPUT\n");
		System.out.println(controller.control("equip"));
		System.out.println(controller.control("unequip"));
		System.out.println(controller.control("consume"));
		System.out.println(controller.control("wajenfkjaewbf"));
		System.out.println(controller.control("inventory nut"));
		System.out.println(controller.control("stats please"));
	}
	
	@Test
	public void equipTest() {
		System.out.println("TESTING PLAYERCONTROLLER EQUIP\n");
		Equipment armor = new Equipment();
		armor.setName("Test Armor");
		armor.setType("armor");
		armor.setQuality(1);
		armor.setDexterity(1);
		armor.setId(4);
		Equipment weapon = new Equipment();
		weapon.setName("Test Weapon");
		weapon.setType("weapon");
		weapon.setQuality(1);
		weapon.setStrength(1);
		weapon.setId(5);
		Equipment accessory = new Equipment();
		accessory.setName("Test Accessory");
		accessory.setType("accessory");
		accessory.setQuality(1);
		accessory.setIntellect(1);
		accessory.setId(6);

		System.out.println(controller.control("inventory"));
		System.out.println(controller.control("equip test armor"));
		itemList.add(armor);
		System.out.println(controller.control("equip test armor"));
		player.addItem(armor);
		System.out.println(controller.control("equip test armor"));
		itemList.add(weapon);
		itemList.add(accessory);
		player.addItem(weapon);
		player.addItem(accessory);
		System.out.println(controller.control("equip test weapon"));
		System.out.println(controller.control("equip test accessory"));
		System.out.println(controller.control("inventory"));
		
		assertTrue(player.getDexterity() == 2);
		assertTrue(player.getIntellect() == 2);
		assertTrue(player.getStrength() == 2);
		assertTrue(player.getArmorID() == 4);
		assertTrue(player.getWeaponID() == 5);
		assertTrue(player.getAccessoryID() == 6);
	}

	@Test
	public void unequipTest() {
		System.out.println("TESTING PLAYERCONTROLLER UNEQUIP\n");
		Equipment armor = new Equipment();
		armor.setName("Test Armor");
		armor.setType("armor");
		armor.setQuality(1);
		armor.setDexterity(1);
		armor.setId(4);
		Equipment weapon = new Equipment();
		weapon.setName("Test Weapon");
		weapon.setType("weapon");
		weapon.setQuality(1);
		weapon.setStrength(1);
		weapon.setId(5);
		Equipment accessory = new Equipment();
		accessory.setName("Test Accessory");
		accessory.setType("accessory");
		accessory.setQuality(1);
		accessory.setIntellect(1);
		accessory.setId(6);

		// Not working
		System.out.println(controller.control("unequip armor"));
		System.out.println(controller.control("unequip weapon"));
		System.out.println(controller.control("unequip accessory"));
		System.out.println(controller.control("unequip test armor"));
		itemList.add(armor);
		itemList.add(weapon);
		itemList.add(accessory);
		player.addItem(armor);
		player.addItem(weapon);
		player.addItem(accessory);
		System.out.println(controller.control("inventory"));
		System.out.println(controller.control("unequip test armor")); // Not working
		controller.control("equip test armor");
		controller.control("equip test weapon");
		controller.control("equip test accessory");
		System.out.println(controller.control("inventory"));
		System.out.println(controller.control("unequip armor"));
		System.out.println(controller.control("unequip weapon"));
		System.out.println(controller.control("unequip accessory"));
		System.out.println(controller.control("inventory"));
		
		assertTrue(player.getDexterity() == 1);
		assertTrue(player.getIntellect() == 1);
		assertTrue(player.getStrength() == 1);
		assertTrue(player.getArmorID() == 0);
		assertTrue(player.getWeaponID() == 0);
		assertTrue(player.getAccessoryID() == 0);
		
		controller.control("equip test armor");
		controller.control("equip test weapon");
		controller.control("equip test accessory");
		System.out.println(controller.control("unequip test armor"));
		System.out.println(controller.control("unequip test weapon"));
		System.out.println(controller.control("unequip test accessory"));
		
		assertTrue(player.getDexterity() == 1);
		assertTrue(player.getIntellect() == 1);
		assertTrue(player.getStrength() == 1);
		assertTrue(player.getArmorID() == 0);
		assertTrue(player.getWeaponID() == 0);
		assertTrue(player.getAccessoryID() == 0);
	}
	
	@Test
	public void consumeTest() {
		System.out.println("TESTING PLAYERCONTROLLER CONSUME\n");
		Consumable item = new Consumable();
		item.setName("Test Health");
		item.setHealth(10);
		player.changeHealth(-10);
		System.out.println(controller.control("health"));
		System.out.println(controller.control("inventory"));
		System.out.println(controller.control("consume test health"));
		itemList.add(item);
		System.out.println(controller.control("consume test health"));
		player.addItem(item,2);
		System.out.println(controller.control("inventory"));
		System.out.println(controller.control("consume test health"));
		item.setType("consumable");
		System.out.println(controller.control("consume test health"));
		System.out.println(controller.control("health"));
		System.out.println(controller.control("inventory"));
	}
	
	@Test
	public void inventoryTest() {
		System.out.println("TESTING INVENTORY\n");
		System.out.println(controller.control("inventory"));
		player.addItem(itemList.get(0));
		player.addItem(itemList.get(1),4);
		player.addItem(itemList.get(2),10);
		System.out.println(controller.control("inventory"));
	}
	
	@Test
	public void equippedTest() {
		System.out.println("TESTING PLAYERCONTROLLER EQUIPMENT\n");
		Equipment armor = new Equipment();
		armor.setName("Test Armor");
		armor.setType("armor");
		armor.setQuality(1);
		armor.setDexterity(1);
		armor.setId(4);
		Equipment weapon = new Equipment();
		weapon.setName("Test Weapon");
		weapon.setType("weapon");
		weapon.setQuality(1);
		weapon.setStrength(1);
		weapon.setId(5);
		Equipment accessory = new Equipment();
		accessory.setName("Test Accessory");
		accessory.setType("accessory");
		accessory.setQuality(1);
		accessory.setIntellect(1);
		accessory.setId(6);

		System.out.println(controller.control("equipment"));
		
		itemList.add(armor);
		itemList.add(weapon);
		itemList.add(accessory);
		player.addItem(armor);
		player.addItem(weapon);
		player.addItem(accessory);
		controller.control("equip test armor");
		controller.control("equip test weapon");
		controller.control("equip test accessory");
		
		System.out.println(controller.control("equipment"));
		System.out.println(controller.control("equipped"));
	}
	
	@Test
	public void statTest() {
		System.out.println("TESTING PLAYERCONTROLLER STATS\n");
		String output = controller.control("stats");
		System.out.println(output);
		output = controller.control("health");
		System.out.println(output);
		player.changeHealth(-10);
		output = controller.control("health");
		System.out.println(output);
		System.out.println(controller.control("level"));
		player.addExperience(500);
		player.incrementLevel();
		System.out.println(controller.control("experience"));
		player.changeScore(1337);
		System.out.println(controller.control("score"));
		player.changeCurrency(101);
		System.out.println(controller.control("currency"));
		System.out.println(controller.control("intellect"));
		System.out.println(controller.control("dexterity"));
		System.out.println(controller.control("strength"));
	}
}
