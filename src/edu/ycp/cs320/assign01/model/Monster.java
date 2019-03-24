package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Monster {
	Set<String> weakPoints, partsList;
	String monsterName, monsterDescription;
	int monsterLevel, monsterHealth, monsterID;
	
	public Monster() {
		weakPoints = new TreeSet<String>();
		partsList = new TreeSet<String>();
	}
	public Monster(int level) {
		weakPoints = new TreeSet<String>();
		partsList = new TreeSet<String>();
		level = monsterLevel;
		setHealth();
	}
	
	public int getLevel() {
		return monsterLevel;
	}
	
	/**
	 * Set, get, or change this monster's health, and
	 * find out if it is dead.
	 */
	public void setHealth() {
		monsterHealth = monsterLevel * 10;
	}
	public int getHealth() {
		return monsterHealth;
	}
	public void changeHealth(int change) {
		monsterHealth += change;
	}
	public boolean isDead() {
		return (monsterHealth <= 0);
	}
	
	// TODO: have monsters drop items on death
	public Item loot() {
		return null;
	}
	
	/**
	 * This monster's attack damage.
	 */
	public int attack() {
		return 4 + monsterLevel;
	}
	
	/**
	 * Set or get this monster's name, description, and ID.
	 */
	public void setName(String name) {
		monsterName = name;
	}
	public String getName() {
		return monsterName;
	}
	public void setDescription(String description) {
		monsterDescription = description;
	}
	public String getDescription() {
		return monsterDescription;
	}
	public void setID(int id) {
		monsterID = id;
	}
	public int getID() {
		return monsterID;
	}
	
	
	/**
	 * Set or get the list of this monster's parts and weak points
	 */
	public void setWeakPoints(ArrayList<String> list) {
		weakPoints.addAll(list);
	}
	public Set<String> getWeakPoints() {
		return weakPoints;
	}
	public void setPartsList(ArrayList<String> list) {
		partsList.addAll(list);
	}
	public Set<String> getPartsList() {
		return partsList;
	}
}