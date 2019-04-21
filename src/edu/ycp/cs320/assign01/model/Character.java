package edu.ycp.cs320.assign01.model;

public abstract class Character {
	private int baseHealth, currHealth, minAttack, maxAttack, level;
	
	public Character() {
	}
	
	/*****************
	 * Level methods *
	 *****************/
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevel() {
		return level;
	}
	
	/******************
	 * Health methods *
	 ******************/
	/**
	 * @param health the base health of this NPC
	 */
	public void setHealth(int health) {
		baseHealth = health;
	}
	/**
	 * @return this NPC's base health
	 */
	public int getBaseHealth() {
		return baseHealth;
	}
	/**
	 * @return the current health of this NPC
	 */
	public int getHealth() {
		return currHealth;
	}
	/**
	 * @return the max health of this NPC, based off of the base health multiplied by the level
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
	 * max attack of this NPC multiplied by its level
	 */
	public abstract int attack();
	public void setMinAttack(int minAttack) {
		this.minAttack = minAttack;
	}
	public int getMinAttack() {
		return minAttack;
	}
	public void setMaxAttack(int maxAttack) {
		this.maxAttack = maxAttack;
	}
	public int getMaxAttack() {
		return maxAttack;
	}
}
