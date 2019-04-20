package edu.ycp.cs320.assign01.model;

public class Consumable extends Item {
	private int health, experience, levelChange, score, currency, intellect, strength, dexterity;
	
	public Consumable() {
		super();
	}
	
	/**
	 * When given a player, apply the effects that this consumable has
	 */
	// TODO have this be a method of player?
	public void consume(Player p) {
		p.changeHealth(getHealth());
		p.addExperience(getExperience());
		for(int i = 0; i < levelChange; i++)
			p.incrementLevel();
		p.changeScore(getScore());
		p.changeCurrency(getCurrency());
		p.changeIntellect(getIntellect());
		p.changeStrength(getStrength());
		p.changeDexterity(getDexterity());
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
