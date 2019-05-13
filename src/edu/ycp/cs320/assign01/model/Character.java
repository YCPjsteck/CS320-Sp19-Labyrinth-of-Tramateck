package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.utility.Pair;

public abstract class Character {
	protected int baseHealth, currHealth, minAttack, maxAttack, level;
	protected ArrayList<Pair<Item,Integer>> inventory;
	
	public Character() {
		inventory = new ArrayList<Pair<Item,Integer>>();
	}
	
	/*****************
	 * Level methods *
	 *****************/
	/**
	 * @param level this Character's level
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return this character's level
	 */
	public int getLevel() {
		return level;
	}
	
	/******************
	 * Health methods *
	 ******************/
	/**
	 * @param health the base health of this Character
	 */
	public void setHealth(int health) {
		baseHealth = health;
	}
	/**
	 * @param health the current health of this Character
	 */
	public void setCurrHealth(int health) {
		currHealth = health;
	}
	/**
	 * @return this Character's base health
	 */
	public int getBaseHealth() {
		return baseHealth;
	}
	/**
	 * @return the current health of this Character
	 */
	public int getHealth() {
		return currHealth;
	}
	/**
	 * @return the max health of this Character, based off of the base health multiplied by the level
	 */
	public int getMaxHealth() {
		return baseHealth * level;
	}
	/**
	 * Sets the current health to the max health
	 */
	public void calHealth() {
		currHealth = getMaxHealth();
	}
	/**
	 * Change the current health by the given integer.
	 * If the change causes current health to go above max health,
	 * then the current health is set to the max health.
	 */
	public void changeHealth(int change) {
		currHealth += change;
		if(currHealth > getMaxHealth())
			currHealth = getMaxHealth();
	}
	/**
	 * Returns true if the current health is less than or equal to zero
	 */
	public boolean isDead() {
		return (currHealth <= 0);
	}
	
	/******************
	 * Attack methods *
	 ******************/
	/**
	 * @return a random attack number between the min and 
	 * max attack of this Character multiplied by its level
	 */
	public abstract int attack();
	/**
	 * @param minAttack this Character's minimum attack damage
	 */
	public void setMinAttack(int minAttack) {
		this.minAttack = minAttack;
	}
	/**
	 * @return this Character's minimum attack damage
	 */
	public int getMinAttack() {
		return minAttack;
	}
	/**
	 * @param maxAttack this Character's maximum attack damage
	 */
	public void setMaxAttack(int maxAttack) {
		this.maxAttack = maxAttack;
	}
	/**
	 * @return this Character's maximum attack damage
	 */
	public int getMaxAttack() {
		return maxAttack;
	}

	/*********************
	 * Inventory methods *
	 *********************/
	/**
	 * Get the character's inventory
	 */
	public ArrayList<Pair<Item,Integer>> getInventory() {
		return inventory;
	}
	/**
	 * Returns true if the character's inventory contains at least as many
	 * of the item given as the quantity given.
	 */
	public boolean containsItem(Item item, int quantity) {
		for(Pair<Item,Integer> pair : inventory) {
			if(pair.getLeft().getName().equals(item.getName()) 
					&& pair.getRight() >= quantity) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns true if the character's inventory contains at least one of
	 * the given item.
	 */
	public boolean containsItem(Item item) {
		return containsItem(item,1);
	}
	/**
	 * Add a given quantity of a given item to the character's inventory
	 */
	public void addItem(Item item, int quantity) {
		boolean has = containsItem(item);
		
		if(has) {
			for(Pair<Item,Integer> pair : inventory)
				if(pair.getLeft().getName().equals(item.getName()))
					pair.setRight(pair.getRight() + quantity);
		} else
			inventory.add(new Pair<Item,Integer>(item,quantity));
	}
	/**
	 * Add one of the given item to the character's inventory
	 */
	public void addItem(Item item) {
		addItem(item,1);
	}
	/**
	 * Remove a given quantity of a given item from the character's inventory
	 */
	public Pair<Item,Integer> removeItem(Item item, int quantity) {
		// Check if the character's inventory contains quantity+1 of this item.
		// The +1 is to hand the situation where the is requesting for exactly
		// as many items as they have.
		boolean has = containsItem(item, quantity+1);
		
		// If the character does have item x quantity+1
		if(has) {
			for(int i = 0; i < inventory.size(); i++) { // Loop through the inventory
				Pair<Item,Integer> pair = inventory.get(i);
				if(pair.getLeft().getName().equals(item.getName())) { // Check that the items are the same
					pair.setRight(pair.getRight() - quantity); // Decrement the quantity of this item by how much is being taken
					return new Pair<Item,Integer>(item, quantity); // Return the item and its quantity
				}
			}
		} else { // If the character has item x quantity or less
			for(int i = 0; i < inventory.size(); i++) {
				Pair<Item,Integer> pair = inventory.get(i);
				if(pair.getLeft().getName().equals(item.getName())) {
					return inventory.remove(i); // Simply return the pair, removing it from the player's inventory
				}
			}
		}
		return null;
	}
	/**
	 * Remove one of the given item from the character's inventory
	 */
	public Pair<Item,Integer> removeItem(Item item) {
		return removeItem(item,1);
	}
	
	public Pair<Item,Integer> getItem(String name) {
		for(Pair<Item,Integer> pair : inventory) {
			if(pair.getLeft().getName().equalsIgnoreCase(name)) {
				return pair;
			}
		}
		return null;
	}
}
