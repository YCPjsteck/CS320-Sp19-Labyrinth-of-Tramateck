package edu.ycp.cs320.assign01.model;

import java.util.Random;

import edu.ycp.cs320.assign01.enums.ItemType;
import edu.ycp.cs320.assign01.model.utility.Pair;

public class Player extends Character{
	private int id, locationID, experience, score, currency, intellect, strength, dexterity;
	private Equipment weapon, armor, accessory;
	private int armorID, weaponID, accessoryID;
	private String name;
	
	public Player() {
		super();
		weapon = null;
		armor = null;
		accessory = null;
		currency = 0;
		score = 0;
		
		// Level
		experience = 0;
		level = 1;
		baseHealth = 50;
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
	 * Checks if the player's experience is high enough to level up
	 */
	public boolean levelCheck() {
		boolean levelUp = false;
		while(experience >= 1000*level) {
			if(experience >= 1000*level) {
				experience -= 1000*level;
				incrementLevel();
				levelUp = true;
			}
		}
		return levelUp;
	}
	/**
	 * Increment this player's level by 1
	 */
	public void incrementLevel() {
		level++;
		int playerMod = intellect;
		if(accessoryID != 0) {
			playerMod += accessory.getQuality() * level;
		}
		score += (100 + playerMod) * level;
		// Increase the player's stats according to their
		// equipped items
		if(armorID != 0) {
			baseHealth += armor.getHealth();
			changeHealth(0);
			intellect += armor.getIntellect();
			strength += armor.getStrength();
			dexterity += armor.getDexterity();
		}
		if(accessoryID != 0) {
			baseHealth += accessory.getHealth();
			changeHealth(0);
			intellect += accessory.getIntellect();
			strength += accessory.getStrength();
			dexterity += accessory.getDexterity();
		}
		if(weaponID != 0) {
			baseHealth += weapon.getHealth();
			changeHealth(0);
			intellect += weapon.getIntellect();
			strength += weapon.getStrength();
			dexterity += weapon.getDexterity();
		}
		calHealth();
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
			quality = weapon.getQuality() * level;
		
		maxAttack = (int)Math.ceil(((double)minAttack)*1.5) + quality;
		return (rand.nextInt(getMaxAttack()-getMinAttack()+1) + getMinAttack());
	}
	
	/**
	 * Remove the given item from the player's inventory and consume it,
	 * applying its effects to the player.
	 */
	public void consume(Consumable item) {
		removeItem(item); // Remove this item from the player's inventory to consume it
		
		// Change the player's stats according to this consumable
		changeHealth(item.getHealth() * level);
		addExperience(item.getExperience() * level);
		changeScore(item.getScore() * level);
		changeCurrency(item.getCurrency() * level);
		changeIntellect(item.getIntellect() * level);
		changeStrength(item.getStrength() * level);
		changeDexterity(item.getDexterity() * level);
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
			weaponID = item.getId();
		} else if(item.getType() == ItemType.ARMOR) {
			unequipArmor();
			armor = item;
			armorID = item.getId();
		} else if(item.getType() == ItemType.ACCESSORY) {
			unequipAccessory();
			accessory = item;
			accessoryID = item.getId();
		}
		
		// Add to the player's stats based off of this equipment
		baseHealth += item.getHealth() * level;
		changeHealth(0); // Change health by 0 to ensure that the player's health isn't overcharged
		intellect += item.getIntellect() * level;
		strength += item.getStrength() * level;
		dexterity += item.getDexterity() * level;
	}
	
	/**
	 * Unequip the player's current weapon and put it back into the inventory
	 */
	public void unequipWeapon() {
		if(weapon != null) { // If something is equipped here
			unequipItem(weapon); // Revert the player's stats
			weaponID = 0;
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
			armorID = 0;
			armor = null;
		}
	}
	/**
	 * Unequip the player's current accessory and put it back into the inventory
	 */
	public void unequipAccessory() {
		if(accessory != null) {
			unequipItem(accessory);
			accessoryID = 0;
			accessory = null;
		}
	}
	/**
	 * Helper method of the three above unequip methods to revert the player's stats
	 * back to normal.
	 */
	private void unequipItem(Equipment item) {
		// Upon unqeuipping this item, remove the stat bonuses
		baseHealth -= item.getHealth() * level;
		changeHealth(0); // Change health by 0 to ensure that the player's health isn't overcharged
		intellect -= item.getIntellect() * level;
		strength -= item.getStrength() * level;
		dexterity -= item.getDexterity() * level;

		// Readd the item to the player's inventory
		addItem(item);
	}

	/**
	 * Get the player's current weapon
	 */
	public Equipment getWeapon() {
		return weapon;
	}
	/**
	 * Get the player's current armor
	 */
	public Equipment getArmor() {
		return armor;
	}
	/**
	 * Get the player's current accessory
	 */
	public Equipment getAccessory() {
		return accessory;
	}

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
	
	/**
	 * If a player dies, their score is reset, 
	 * they lose 50% of their currency, and they have a
	 * 50% chance to lose each item in their inventory.
	 */
	public void died() {
		score = 0;
		currency /= 2;
		unequipAccessory();
		unequipArmor();
		unequipWeapon();
		// Using a normal for loop as to avoid a java.util.ConcurrentModificationException
		// that was occuring.
		for(int i = 0; i < inventory.size(); i++) {
			Pair<Item,Integer> pair = inventory.get(i);
			int remove = 0;
			Random rand = new Random();
			for(int j = 0; j < pair.getRight(); j++) {
				if(rand.nextInt(100) < 50) {
					remove++;
				}
			}
			removeItem(pair.getLeft(),remove);
			if(!containsItem(pair.getLeft())) {
				i--;
			}
		}
	}
}