package edu.ycp.cs320.assign01.model;

public class Item implements Named {
	private String name, longDesc, shortDesc, rarity;
	private int id, worth, weight, level;
	
	public Item() {	
	}

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
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getWeight() {
		return weight;
	}
	public void setWorth(int worth) {
		this.worth = worth;
	}
	public int getWorth() {
		return worth;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	public String getRarity() {
		return rarity;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}