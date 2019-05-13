package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import edu.ycp.cs320.assign01.enums.NPCType;
import edu.ycp.cs320.assign01.model.interfaces.Named;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.Triple;

public class NPC extends Character implements Named {
	private String name, shortDesc, longDesc;
	private int id;
	private NPCType type;
	
	private ArrayList<Triple<Item,Integer,Integer>> loot;
	private ArrayList<Triple<Item,Integer,Integer>> fullInventory;
	private Set<String> weakPoints, partsList;
	
	public NPC() {
		loot = new ArrayList<Triple<Item,Integer,Integer>>();
		fullInventory = new ArrayList<Triple<Item,Integer,Integer>>();
		weakPoints = new TreeSet<String>();
		partsList = new TreeSet<String>();
	}
	
	/**
	 * @param npc an NPC object to be copied into a new NPC object
	 */
	public NPC(NPC npc) {
		loot = npc.getAllLoot();
		fullInventory = npc.getFullInventory();
		weakPoints = npc.getWeaknesses();
		partsList = npc.getParts();
		
		minAttack = npc.getMinAttack();
		maxAttack = npc.getMaxAttack();
		baseHealth = npc.getBaseHealth();
		name = npc.getName();
		type = npc.getType();
		shortDesc = npc.getShortDesc();
		longDesc = npc.getLongDesc();
	}
	
	/****************
	 * Loot methods *
	 ****************/
	/**
	 * Add an item to the inventory of possible items for this NPC to drop upon death.
	 * @param item the item to be dropped
	 * @param chance the chance for this item to individually be dropped
	 * @param quantity the max possible number of this item that can be dropped
	 */
	public void addLoot(Item item, int chance, int quantity) {
		loot.add(new Triple<Item,Integer,Integer>(item, chance, quantity));
	}
	
	/**
	 * Travel through this NPC's loot arraylist and calculate the loot to randomly return
	 * based off of each loot's drop chance.
	 * @return an arraylist of pairs of items and an integer representing the quantity of that item.
	 */
	public ArrayList<Pair<Item,Integer>> getLoot() {
		for(Pair<Item,Integer> pair : inventory) {
			addLoot(pair.getLeft(),50,pair.getRight());
		}
		return tripleItem(loot);
	}
	/**
	 * Get this NPC's loot arraylist.
	 */
	public ArrayList<Triple<Item,Integer,Integer>> getAllLoot() {
		return loot;
	}
	
	/******************
	 * Vendor methods *
	 ******************/
	/**
	 * Add an item to the inventory of possible items for this NPC to offer during trading.
	 * @param item the item to be offered
	 * @param chance the chance for this item to individually be offered
	 * @param quantity the max possible number of this item that can be offered
	 */
	public void addInventory(Item item, int chance, int quantity) {
		fullInventory.add(new Triple<Item,Integer,Integer>(item, chance, quantity));
	}
	/**
	 * Generate an inventory from the NPC's full inventory that was populated by addInventory().
	 */
	public void generateInventory() {
		inventory = tripleItem(fullInventory);
	}
	/**
	 * Get the full inventory of this NPC.
	 */
	public ArrayList<Triple<Item,Integer,Integer>> getFullInventory() {
		return fullInventory;
	}

	/**
	 * A helper method for generateInventory() and getLoot(). When given an arraylist of triples,
	 * crawl through the list to generate an arraylist of pairs.
	 */
	private ArrayList<Pair<Item,Integer>> tripleItem(ArrayList<Triple<Item,Integer,Integer>> list) {
		ArrayList<Pair<Item,Integer>> items = new ArrayList<Pair<Item,Integer>>();
		Random rand = new Random();
		// For each triple in the given list
		for(Triple<Item,Integer,Integer> t : list) {
			Item item = t.getLeft(); // set the item to the triple's item
			int quantity = 0;
			// From 0 to the quantity of this triple (right integer)
			for(int i = 0; i < t.getRight(); i++) {
				// If a random number from 0 to 99 is less than this triple's chance (middle integer)
				if(rand.nextInt(100) < t.getMiddle())
					quantity++; // increment the quantity of items
			}
			if(quantity > 0) // If quantity is greater than zero
				items.add(new Pair<Item,Integer>(item,quantity)); // Add this item and its quantity to the loot drop list
		}
		return items;
	}
	
	/*************************************
	 * Name, ID, and description methods *
	 *************************************/
	/**
	 * Set this NPC's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Get this NPC's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set this NPC's ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Get this NPC's ID
	 */
	public int getId() {
		return id;
	}
	/**
	 * Set this NPC's long description
	 */
	public void setLongDesc(String desc) {
		longDesc = desc;
	}
	/**
	 * Set this NPC's short description
	 */
	public void setShortDesc(String desc) {
		shortDesc = desc;
	}
	/**
	 * Get this NPC's long description
	 */
	public String getLongDesc() {
		return longDesc;
	}
	/**
	 * Get this NPC's short description
	 */
	public String getShortDesc() {
		return shortDesc;
	}

	/****************
	 * Type methods *
	 ****************/
	/**
	 * Set this NPC's type
	 */
	public void setType(String type) {
		this.type = NPCType.toType(type);
	}
	/**
	 * Get this NPC's type
	 */
	public NPCType getType() {
		return type;
	}
	
	/*****************
	 * Combat methods *
	 *****************/
	/**
	 * Return a random attack damage value from this NPC based off of 
	 * its min attack, max attack, and level
	 */
	public int attack() {
		Random rand = new Random();
		int min = getMinAttack() * getLevel();
		int max = getMaxAttack() * getLevel();
		return (rand.nextInt(max-min+1) + min);
	}
	 /**
	 * Give this NPC a weakness
	 */
	public void setWeakness(String s) {
		weakPoints.add(s);
	}
	 /**
	 * Give this NPC a set of weaknesses
	 */
	public void setWeakness(ArrayList<String> list) {
		weakPoints.addAll(list);
	}
	/**
	 * Get this NPC's weaknesses
	 */
	public Set<String> getWeaknesses() {
		return weakPoints;
	}
	 /**
	 * Give this NPC a part
	 */
	public void setPart(String s) {
		partsList.add(s);
	}
	 /**
	 * Give this NPC a set of parts
	 */
	public void setPart(ArrayList<String> list) {
		partsList.addAll(list);
	}
	/**
	 * Get this NPC's parts
	 */
	public Set<String> getParts() {
		return partsList;
	}
}