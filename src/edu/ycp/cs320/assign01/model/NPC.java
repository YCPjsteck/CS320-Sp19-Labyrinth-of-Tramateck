package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;
import java.util.Random;

import edu.ycp.cs320.assign01.model.interfaces.Named;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.Triple;

public class NPC implements Named {
	private String name, shortDesc, longDesc;
	private int level, currHealth, baseHealth, id, minAttack, maxAttack;
	private ArrayList<Triple<Item,Integer,Integer>> loot;
	
	public NPC() {
		loot = new ArrayList<Triple<Item,Integer,Integer>>();
	}
	
	public NPC(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	/**
	 * Set, get, or change this NPCs health, and
	 * find out if it is dead.
	 */
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
	
	/**
	 * Set or get this NPC's loot.
	 */
	public void addLoot(Item item, int weight, int size) {
		loot.add(new Triple<Item,Integer,Integer>(item, weight, size));
	}
	public ArrayList<Pair<Item,Integer>> getLoot() {
		// TODO: Run through the loot array list and calculate the loot that
		// gets returned based off of the weights and sizes.
		return null;
	}
	public ArrayList<Item> getAllLoot() {
		return null;
	}
	
	/**
	 * This NPC's attack damage.
	 */
	public int attack() {
		Random rand = new Random();
		return (rand.nextInt(maxAttack-minAttack) + minAttack) * level;
	}
	
	/**
	 * Set or get this NPC's name, description, and ID.
	 */
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

	public int getMinAttack() {
		return minAttack;
	}

	public void setMinAttack(int minAttack) {
		this.minAttack = minAttack;
	}

	public int getMaxAttack() {
		return maxAttack;
	}

	public void setMaxAttack(int maxAttack) {
		this.maxAttack = maxAttack;
	}
}