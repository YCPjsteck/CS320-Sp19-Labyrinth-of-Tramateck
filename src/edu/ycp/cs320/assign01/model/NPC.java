package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;
import java.util.Random;

import edu.ycp.cs320.assign01.model.interfaces.Named;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.Triple;

public class NPC implements Named {
	private String name, shortDesc, longDesc;
	private int level, baseHealth, currHealth, id, minAttack, maxAttack;
	private ArrayList<Triple<Item,Integer,Integer>> loot;
	
	public NPC() {
		loot = new ArrayList<Triple<Item,Integer,Integer>>();
	}
	
	/*****************
	 * Level methods *
	 *****************/
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevel() {
		return level;
	}
	
	/******************
	 * Health methods *
	 ******************/
	public void setHealth(int health) {
		baseHealth = health;
	}
	public int getBaseHealth() {
		return baseHealth;
	}
	public int getHealth() {
		return currHealth;
	}
	public int getMaxHealth() {
		return baseHealth * level;
	}
	public void calHealth() {
		currHealth = getMaxHealth();
	}
	public void changeHealth(int change) {
		currHealth += change;
		if(currHealth > getMaxHealth())
			currHealth = getMaxHealth();
	}
	public boolean isDead() {
		return (currHealth <= 0);
	}
	
	/****************
	 * Loot methods *
	 ****************/
	public void addLoot(Item item, int weight, int size) {
		loot.add(new Triple<Item,Integer,Integer>(item, weight, size));
	}
	/**
	 * Travel through this NPC's loot arraylist and calculate the loot to randomly return
	 * based off of each loot's drop chance.
	 * @return an arraylist of pairs of items and an integer representing the number of that item.
	 */
	public ArrayList<Pair<Item,Integer>> getLoot() {
		ArrayList<Pair<Item,Integer>> items = new ArrayList<Pair<Item,Integer>>();
		Random rand = new Random();
		// For each triple in the loot list
		for(Triple<Item,Integer,Integer> t : loot) {
			Item item = t.getLeft(); // set the item to the triple's item
			int number = 0;
			// From 0 to the size of this triple (right integer)
			for(int i = 0; i < t.getRight(); i++) {
				// If a random number from 0 to 99 is less than this triple's chance (middle integer)
				if(rand.nextInt(100) < t.getMiddle())
					number++; // increment the number of items
			}
			if(number >= 0) // If number is greater than zero
				items.add(new Pair<Item,Integer>(item,number)); // Add this item and its number to the loot drop list
		}
		return items;
	}
	public ArrayList<Triple<Item,Integer,Integer>> getAllLoot() {
		return loot;
	}
	
	/*************************************
	 * Name, ID, and description methods *
	 *************************************/
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setLongDesc(String desc) {
		longDesc = desc;
	}
	public void setShortDesc(String desc) {
		shortDesc = desc;
	}
	public String getLongDesc() {
		return longDesc;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	
	/******************
	 * Attack methods *
	 ******************/
	/**
	 * @return a random attack number between the min and 
	 * max attack of this NPC multiplied by its level
	 */
	public int attack() {
		Random rand = new Random();
		return (rand.nextInt(maxAttack-minAttack) + minAttack) * level;
	}
	public void setMinAttack(int minAttack) {
		this.minAttack = minAttack;
	}
	public int getMinAttack() {
		return minAttack;
	}
	public void setMaxAttack(int maxAttack) {
		this.maxAttack = maxAttack;
	}
	public int getMaxAttack() {
		return maxAttack;
	}
}