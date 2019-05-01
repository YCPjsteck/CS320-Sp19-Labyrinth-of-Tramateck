package edu.ycp.cs320.assign01.model;

public class Consumable extends Item {
	private int health, experience, levelChange, score, currency, intellect, strength, dexterity;
	
	public Consumable() {
		super();
	}
	
	public int getHealth() {
		return health * super.getLevel();
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getExperience() {
		return experience * super.getLevel();
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getLevelChange() {
		return levelChange;
	}
	public void setLevelChange(int level) {
		this.levelChange = level;
	}
	public int getScore() {
		return score * super.getLevel();
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getCurrency() {
		return currency * super.getLevel();
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public int getIntellect() {
		return intellect * super.getLevel();
	}
	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}
	public int getStrength() {
		return strength * super.getLevel();
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getDexterity() {
		return dexterity * super.getLevel();
	}
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
}
