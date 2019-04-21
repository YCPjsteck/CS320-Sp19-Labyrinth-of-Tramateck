package edu.ycp.cs320.assign01.enums;

public enum NPCType {
	FRIENDLY("friendly"),
	HOSTILE("hostile");
	
	private final String string;
	
	private NPCType(String string) {
		this.string = string;
	}
	public String getString() {
		return string;
	}
	
	public static NPCType toType(String type) {
		if(type.equalsIgnoreCase("friendly"))
			return FRIENDLY;
		else if(type.equalsIgnoreCase("hostile"))
			return HOSTILE;
		else
			return null;
	}
}
