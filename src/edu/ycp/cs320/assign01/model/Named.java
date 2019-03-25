package edu.ycp.cs320.assign01.model;

public interface Named {
	public void setName(String name);
	public String getName();
	public void setId(int id);
	public int getId();
	
	public abstract void setLongDesc(String desc);
	public abstract void setShortDesc(String desc);
	public abstract String getLongDesc();
	public abstract String getShortDesc();
}
