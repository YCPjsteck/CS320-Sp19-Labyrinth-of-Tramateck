package edu.ycp.cs320.assign01.model;

public class Equipment extends Item {
	private int quality, health, intellect, strength, dexterity;
	
	public Equipment() {
		super();
	}
	
	// TODO have these be methods of player?
	public void equip(Player p) {
		p.changeHealth(getHealth());
		p.changeIntellect(getIntellect());
		p.changeStrength(getStrength());
		p.changeDexterity(getDexterity());
	}
	public void unequip(Player p) {
		p.changeHealth(-getHealth());
		p.changeIntellect(-getIntellect());
		p.changeStrength(-getStrength());
		p.changeDexterity(-getDexterity());
	}

	public int getQuality() {
		return quality * super.getLevel();
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getHealth() {
		return health * super.getLevel();
	}
	public void setHealth(int health) {
		this.health = health;
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
