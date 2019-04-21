package edu.ycp.cs320.assign01.enums;

public enum ItemRarity {
	COMMON("common"),
	UNCOMMON("uncommon"),
	RARE("rare"),
	LEGENDARY("legendary"),
	EPIC("epic");
	
	private final String string;
	
	private ItemRarity(String string) {
		this.string = string;
	}
	public String getString(ItemRarity type) {
		return string;
	}
	
	public static ItemRarity toType(String type) {
		if(type.equalsIgnoreCase("common"))
			return COMMON;
		else if(type.equalsIgnoreCase("uncommon"))
			return UNCOMMON;
		else if(type.equalsIgnoreCase("rare"))
			return RARE;
		else if(type.equalsIgnoreCase("legendary"))
			return LEGENDARY;
		else if(type.equalsIgnoreCase("epic"))
			return EPIC;
		else
			return null;
	}
}
