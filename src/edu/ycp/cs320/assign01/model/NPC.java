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
			if(number > 0) // If number is greater than zero
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

	/****************
	 * Type methods *
	 ****************/
	public void setType(String type) {
		this.type = NPCType.toType(type);
	}
	public NPCType getType() {
		return type;
	}
	
	/******************
	 * Vendor methods *
	 ******************/
	public void addInventory(Item item, int weight, int size) {
		fullInventory.add(new Triple<Item,Integer,Integer>(item, weight, size));
	}
	public void generateInventory() {
		// TODO: go through the fullInventory and generate a temporary
		// inventory, in a similar way to NPC's getLoot()
	}
	public ArrayList<Triple<Item,Integer,Integer>> getFullInventory() {
		return fullInventory;
	}
	
	/*****************
	 * Enemy methods *
	 *****************
	 **
	 * Set or get the list of this monster's parts and weak points
	 */
	public void setWeakness(String s) {
		weakPoints.add(s);
	}
	public void setWeakPoints(ArrayList<String> list) {
		weakPoints.addAll(list);
	}
	public Set<String> getWeakPoints() {
		return weakPoints;
	}
	public void setPart(String s) {
		partsList.add(s);
	}
	public void setPartsList(ArrayList<String> list) {
		partsList.addAll(list);
	}
	public Set<String> getPartsList() {
		return partsList;
	}

	@Override
	public int attack() {
		Random rand = new Random();
		return (rand.nextInt(getMaxAttack()-getMinAttack()) + getMinAttack()) * getLevel();
	}
}