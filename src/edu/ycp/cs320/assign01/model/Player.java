package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.enums.ItemType;
import edu.ycp.cs320.assign01.model.utility.Pair;

public class Player extends Character{
	private int experience, score, currency, intellect, strength, dexterity;
	private ArrayList<Pair<Item,Integer>> inventory;
	private Equipment weapon, armor, accessory;
	
	public Player() {
		inventory = new ArrayList<Pair<Item,Integer>>();
		currency = 0;
		score = 0;
		
		// Level
		experience = 0;
		
		// Stats
		intellect = 5;
		strength = 5;
		dexterity = 5;
	}

	/*********************
	 * Inventory methods *
	 *********************/
	/**
	 * Get the player's inventory
	 */
	public ArrayList<Pair<Item,Integer>> getInventory() {
		return inventory;
	}
	/**
	 * Returns true if the player's inventory contains at least as many
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
	 * Returns true if the player's inventory contains at least one of
	 * the given item.
	 */
	public boolean containsItem(Item item) {
		return containsItem(item,1);
	}
	/**
	 * Add a given quantity of a given item to the player's inventory
	 */
	public void addItem(Item item, int quantity) {
		boolean has = containsItem(item, quantity);
		
		if(has) {
			for(Pair<Item,Integer> pair : inventory)
				if(pair.getLeft().getName().equals(item.getName()))
					pair.setRight(pair.getRight() + quantity);
		} else
			inventory.add(new Pair<Item,Integer>(item,quantity));
	}
	/**
	 * Add one of the given item to the player's inventory
	 */
	public void addItem(Item item) {
		addItem(item,1);
	}
	/**
	 * Remove a given quantity of a given item from the player's inventory
	 */
	public Pair<Item,Integer> removeItem(Item item, int quantity) {
		// Check if the player's inventory contains quantity+1 of this item.
		// The +1 is to hand the situation where the is requesting for exactly
		// as many items as they have.
		boolean has = containsItem(item, quantity+1);
		
		// If the player does have item x quantity+1
		if(has) {
			for(int i = 0; i < inventory.size(); i++) { // Loop through the inventory
				Pair<Item,Integer> pair = inventory.get(i);
				if(pair.getLeft().getName().equals(item.getName())) { // Check that the items are the same
					pair.setRight(pair.getRight() - quantity); // Decrement the quantity of this item by how much is being taken
					return new Pair<Item,Integer>(item, quantity); // Return the item and its quantity
				}
			}
		} else { // If the player has item x quantity or less
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
	 * Remove one of the given item from the player's inventory
	 */
	public Pair<Item,Integer> removeItem(Item item) {
		return removeItem(item,1);
	}

	/****************
	 * Stat methods *
	 ****************/
	/**
	 * Add experience to this player
	 */
	public void addExperience(int experience) {
		this.experience += experience;
	}
	/**
	 * Get this player's current experience
	 */
	public int getExperience() {
		return experience;
	}
	/**
	 * Increment this player's level by 1
	 */
	public void incrementLevel() {
		level++;
	}
	/**
	 * Increment this player's level by a given value
	 */
	public void incrementLevel(int level) {
		this.level += level;
	}

	/**
	 * Get this player's current score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Change this player's score
	 */
	public void changeScore(int score) {
		this.score += score;
	}
	/**
	 * Get this player's current funds
	 */
	public int getCurrency() {
		return currency;
	}
	/**
	 * Change this player's funds
	 */
	public void changeCurrency(int currency) {
		this.currency += currency;
	}
	
	/*****************
	 * Skill methods *
	 *****************/
	/**
	 * Set this player's intellect
	 */
	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}
	/**
	 * Change this player's intellect
	 */
	public void changeIntellect(int change) {
		intellect += change;
	}
	/**
	 * Get this player's intellect
	 */
	public int getIntellect() {
		return intellect;
	}

	/**
	 * Set this player's strength
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}
	/**
	 * Change this player's strength
	 */
	public void changeStrength(int change) {
		strength += change;
	}
	/**
	 * Get this player's strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * Set this player's dexterity
	 */
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
	/**
	 * Change this player's dexterity
	 */
	public void changeDexterity(int change) {
		dexterity += change;
	}
	/**
	 * Get this player's dexterity
	 */
	public int getDexterity() {
		return dexterity;
	}
	
	public void eventResult(EventResult result) {
		switch (result.getId()) {
			case 0:
				changeHealth(result.getScale());
				break;
			case 1:
				changeStrength(result.getScale());
				break;
			case 2:
				changeIntellect(result.getScale());
				break;
			case 3:
				changeDexterity(result.getScale());
				break;
			case 4: //TO LATER MODIFY INVENTORY
				break;
			default:
				break;
		}
	}

	/******************
	 * Action methods *
	 ******************/
	/**
	 * Get an attack value from this player, based off of their level, stats, and equipment
	 */
	// TODO check the player's level, stats, and equipment to determine their attack damage.
	public int attack() {
		return 0;
	}
	
	/**
	 * Remove the given item from the player's inventory and consume it,
	 * applying its effects to the player.
	 */
	public void consume(Consumable item) {
		removeItem(item);
		changeHealth(item.getHealth());
		addExperience(item.getExperience());
		incrementLevel(item.getLevelChange());
		changeScore(item.getScore());
		changeCurrency(item.getCurrency());
		changeIntellect(item.getIntellect());
		changeStrength(item.getStrength());
		changeDexterity(item.getDexterity());
	}
	
	/**
	 * Equip the given piece of equipment
	 */
	public void equip(Equipment item) {
		// Remove this item from the player's inventory, as it
		// is assumed that that is where the item is coming from.
		removeItem(item);
		
		// Unequip the previously equipped item and indicated
		// that the new item has been equipped
		if(item.getType() == ItemType.WEAPON) {
			unequipWeapon();
			weapon = item;
		} else if(item.getType() == ItemType.ARMOR) {
			unequipArmor();
			armor = item;
		} else if(item.getType() == ItemType.ACCESSORY) {
			unequipAccessory();
			accessory = item;
		}
		
		// Add to the player's stats based off of this equipment
		baseHealth += item.getHealth();
		intellect += item.getIntellect();
		strength += item.getStrength();
		dexterity += item.getDexterity();
	}
	
	/**
	 * Unequip the player's current weapon and put it back into the inventory
	 */
	public void unequipWeapon() {
		if(weapon != null) { // If something is equipped here
			// Upon unqeuipping this item, remove the stat bonuses
			baseHealth -= weapon.getHealth();
			changeHealth(0); // Change health by 0 to ensure that the player's health isn't overcharged
			intellect -= weapon.getIntellect();
			strength -= weapon.getStrength();
			dexterity -= weapon.getDexterity();
			
			// Readd the item to the player's inventory
			addItem(weapon);
			// Set this equipment to null to indicated that the
			// player has nothing equipped
			weapon = null;
		}
	}
	/**
	 * Unequip the player's current armor and put it back into the inventory
	 */
	public void unequipArmor() {
		if(armor != null) {
			baseHealth -= armor.getHealth();
			changeHealth(0);
			intellect -= armor.getIntellect();
			strength -= armor.getStrength();
			dexterity -= armor.getDexterity();
			
			addItem(armor);
			armor = null;
		}
	}
	/**
	 * Unequip the player's current accessory and put it back into the inventory
	 */
	public void unequipAccessory() {
		if(accessory != null) {
			baseHealth -= accessory.getHealth();
			changeHealth(0);
			intellect -= accessory.getIntellect();
			strength -= accessory.getStrength();
			dexterity -= accessory.getDexterity();
			
			addItem(accessory);
			accessory = null;
		}
	}

	/**
	 * Get the player's current weapon
	 */
	public Item getWeapon() {
		return weapon;
	}
	/**
	 * Get the player's current armor
	 */
	public Item getArmor() {
		return armor;
	}
	/**
	 * Get the player's current accessory
	 */
	public Item getAccessory() {
		return accessory;
	}
}