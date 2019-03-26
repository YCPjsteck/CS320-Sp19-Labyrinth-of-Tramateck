package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

public class Player {
	private ArrayList<Item> inventory, equipment;
	private int health, experience, level, score, currency;
	// TODO: 
	// 		add player skills
	//		create equip/unequip methods
	
	public Player() {
		health = 100;
		inventory = new ArrayList<Item>();
		equipment = new ArrayList<Item>();
	}
	
	public int attack() {
		return 10;
	}
	
	public void changeHealth(int change) {
		health += change;
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
}