package edu.ycp.cs320.assign01.model;

public class NPC implements Named{
	private String name, shortDesc, longDesc;
	private int level, health, id;
	private Item loot;
	
	public NPC() {
	}
	
	public NPC(int level) {
		this.level = level;
		setHealth();
	}
	
	public int getLevel() {
		return level;
	}
	
	/**
	 * Set, get, or change this NPCs health, and
	 * find out if it is dead.
	 */
	public void setHealth() {
		health = level * 10;
	}
	public int getHealth() {
		return health;
	}
	public void changeHealth(int change) {
		health += change;
	}
	public boolean isDead() {
		return (health <= 0);
	}
	
	/**
	 * Set or get this NPC's loot.
	 */
	public void setLoot(Item i) {
		loot = i;
	}
	public Item getLoot() {
		return loot;
	}
	
	/**
	 * This NPC's attack damage.
	 */
	public int attack() {
		return 4 + level;
	}
	
	/**
	 * Set or get this NPC's name, description, and ID.
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setLongDesc(String desc) {
		longDesc = desc;
	}
	public void setShortDesc(String desc) {
		shortDesc = desc;
	}
	public String getLongDesc() {
		return longDesc;
	}
	public String getShortDesc() {
		return shortDesc;
	}
}