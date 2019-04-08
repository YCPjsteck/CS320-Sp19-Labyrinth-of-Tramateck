package edu.ycp.cs320.assign01.model;

public class EventResult {
	private int id;		//0 = hp, 1 = str, 2 = int, 3 = dex, 4 = inventory
	private int scale;
	
	public EventResult(int id, int scale) {
		this.setId(id);
		this.setScale(scale);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

}
