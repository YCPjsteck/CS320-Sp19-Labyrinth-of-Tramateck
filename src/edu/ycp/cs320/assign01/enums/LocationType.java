package edu.ycp.cs320.assign01.enums;

public enum LocationType {
	TOWN("town"),
	DANGEROUS("dangerous");

	private final String string;
	
	private LocationType(String string) {
		this.string = string;
	}
	public String getString() {
		return string;
	}

	public static LocationType toType(String type) {
		if(type.equalsIgnoreCase("town"))
			return TOWN;
		else if(type.equalsIgnoreCase("dangerous"))
			return DANGEROUS;
		else
			return null;
	}
}
