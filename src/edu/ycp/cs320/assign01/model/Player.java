package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.utility.Pair;

public class Player extends Character{
	private ArrayList<Item> inventory, equipment;
	private int experience, score, currency, intellect, strength, dexterity;
	/* TODO: 
	*
	*		create equip/unequip methods
	*/
	
	public Player() {
		super(1);
		inventory = new ArrayList<Item>();
		equipment = new ArrayList<Item>();
		currency = 0;
		score = 0;
		
		//Level
		experience = 0;
		
		//Stats
		intellect = 5;
		strength = 5;
		dexterity = 5;

	}
	
	/****************
	 * Stat methods *
	 ****************/
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

	public void addExperience(int experience) {
		this.experience += experience;
	}
	public int getExperience() {
		return experience;
	}

	public void incrementLevel() {
		super.setLevel(super.getLevel() + 1);
	}
	public int getLevel() {
		return super.getLevel();
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
	
//	public void eventResult(Pair<Integer, Integer> result) {
//		switch (result.getId()) {
//			case 0:
//				changeHealth(result.getScale());
//				break;
//			case 1:
//				changeStrength(result.getScale());
//				break;
//			case 2:
//				changeIntellect(result.getScale());
//				break;
//			case 3:
//				changeDexterity(result.getScale());
//				break;
//			case 4: //TO LATER MODIFY INVENTORY
//				break;
//			default:
//				break;
//		}
//	}
}