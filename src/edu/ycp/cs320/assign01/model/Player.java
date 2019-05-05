package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;
import java.util.Random;

import edu.ycp.cs320.assign01.enums.ItemType;
import edu.ycp.cs320.assign01.model.utility.Pair;

public class Player extends Character{
	private int id, locationID, experience, score, currency, intellect, strength, dexterity;
	private ArrayList<Pair<Item,Integer>> inventory;
	private Equipment weapon, armor, accessory;
	private String name;
	
	public Player() {
		inventory = new ArrayList<Pair<Item,Integer>>();
		weapon = null;
		armor = null;
		accessory = null;
		currency = 0;
		score = 0;
		
		// Level
		experience = 0;
		level = 1;
		baseHealth = 100;
		calHealth();
		
		// Stats
		intellect = 1;
		strength = 1;
		dexterity = 1;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	public void setExperience(int experience) {
		this.experience = experience;
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
	 * Set this player's score
	 */
	public void setScore(int score) {
		this.score = score;
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
	/**
	 * Set this player's current funds
	 */
	public void setCurrency(int currency) {
		this.currency = currency;
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
  
	/******************
	 * Action methods *
	 ******************/
	/**
	 * Get an attack value from this player, based off of their level, stats, and equipment
	 */
	public int attack() {
		Random rand = new Random();
		minAttack = level * strength;
		int quality = 0;
		if(weapon != null)
			quality = weapon.getQuality();
		
		maxAttack = (int)Math.ceil(((double)minAttack)*1.5) + quality;
		return (rand.nextInt(getMaxAttack()-getMinAttack()) + getMinAttack());
	}
	
	/**
	 * Remove the given item from the player's inventory and consume it,
	 * applying its effects to the player.
	 */
	public void consume(Consumable item) {
		removeItem(item); // Remove this item from the player's inventory to consume it
		
		// Change the player's stats according to this consumable
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
		
		// Unequip the previously equipped item and indicate
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
		changeHealth(0); // Change health by 0 to ensure that the player's health isn't overcharged
		intellect += item.getIntellect();
		strength += item.getStrength();
		dexterity += item.getDexterity();
	}
	
	/**
	 * Unequip the player's current weapon and put it back into the inventory
	 */
	public void unequipWeapon() {
		if(weapon != null) { // If something is equipped here
			unequipItem(weapon); // Revert the player's stats
			// Set this equipment to null to indicate that the
			// player has nothing equipped
			weapon = null;
		}
	}
	/**
	 * Unequip the player's current armor and put it back into the inventory
	 */
	public void unequipArmor() {
		if(armor != null) {
			unequipItem(armor);
			armor = null;
		}
	}
	/**
	 * Unequip the player's current accessory and put it back into the inventory
	 */
	public void unequipAccessory() {
		if(accessory != null) {
			unequipItem(accessory);
			accessory = null;
		}
	}
	/**
	 * Helper method of the three above unequip methods to revert the player's stats
	 * back to normal.
	 */
	private void unequipItem(Equipment item) {
		// Upon unqeuipping this item, remove the stat bonuses
		baseHealth -= item.getHealth();
		changeHealth(0); // Change health by 0 to ensure that the player's health isn't overcharged
		intellect -= item.getIntellect();
		strength -= item.getStrength();
		dexterity -= item.getDexterity();

		// Readd the item to the player's inventory
		addItem(item);
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
	
	private int armorID, weaponID, accessoryID;

	public int getArmorID() {
		return armorID;
	}

	public void setArmorID(int armorID) {
		this.armorID = armorID;
	}

	public int getWeaponID() {
		return weaponID;
	}

	public void setWeaponID(int weaponID) {
		this.weaponID = weaponID;
	}

	public int getAccessoryID() {
		return accessoryID;
	}

	public void setAccessoryID(int accessoryID) {
		this.accessoryID = accessoryID;
	}
}