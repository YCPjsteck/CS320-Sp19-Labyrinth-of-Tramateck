package edu.ycp.cs320.assign01.enums;

public enum ItemType {
	TRASH("trash"),
	COMMODITY("commodity"),
	ARTIFACT("artifact"),
	QUEST("quest"),
	ARMOR("armor"),
	WEAPON("weapon"),
	ACCESSORY("accessory");
	
	private final String string;
	
	private ItemType(String string) {
		this.string = string;
	}
	public String getString(ItemType type) {
		return string;
	}
	
	public static ItemType toType(String type) {
		if(type.equalsIgnoreCase("trash"))
			return TRASH;
		else if(type.equalsIgnoreCase("commodity"))
			return COMMODITY;
		else if(type.equalsIgnoreCase("artifact"))
			return ARTIFACT;
		else if(type.equalsIgnoreCase("quest"))
			return QUEST;
		else if(type.equalsIgnoreCase("armor"))
			return ARMOR;
		else if(type.equalsIgnoreCase("weapon"))
			return WEAPON;
		else if(type.equalsIgnoreCase("accessory"))
			return ACCESSORY;
		else
			return null;
	}
}
