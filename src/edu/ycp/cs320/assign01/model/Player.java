package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

public class Player {
	private ArrayList<Item> inventory, equipment;
	private int health, experience, level, score, currency, intellect, strength, dexterity;
	/* TODO: 
	* 		add player skills
	*			- Added set and get methods as a start 
	*			as we implement the skills we may want to
	*			add different methods to more easily adjust 
	*			these values
	*
	*		create equip/unequip methods
	*/
	
	public Player() {
		health = 100;
		inventory = new ArrayList<Item>();
		equipment = new ArrayList<Item>();
		currency = 0;
		score = 0;
		
		//Level
		experience = 0;
		level = 1;
		
		//Stats
		intellect = 5;
		strength = 5;
		dexterity = 5;
	}
	
	public int attack() {
		return strength;
	}
	
	public void changeHealth(int change) {
		health += change;
	}
	
	public void changeStrength(int change) {
		strength += change;
	}
	
	public void changeIntellect(int change) {
		intellect += change;
	}
	
	public void changeDexterity(int change) {
		dexterity += change;
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}
	
	public boolean isDead() {
		return (health <= 0);
	}

	public void addExperience(int experience) {
		this.experience += experience;
	}
	public int getExperience() {
		return experience;
	}

	public void incrementLevel() {
		level++;
	}
	public int getLevel() {
		return level;
	}

	public int getScore() {
		return score;
	}
	public void changeScore(int score) {
		this.score += score;
	}

	public int getCurrency() {
		return currency;
	}
	public void changeCurrency(int currency) {
		this.currency += currency;
	}
	
	
	// Set and Get methods for Player stats
	public int getHealth() {
		return health;
	}
	
	public int getIntellect() {
		return intellect;
	}
	
	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public int getDexterity() {
		return dexterity;
	}
	
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
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
}