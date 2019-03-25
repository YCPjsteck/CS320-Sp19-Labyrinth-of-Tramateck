package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Enemy extends NPC {
	private Set<String> weakPoints, partsList;
	
	public Enemy() {
		weakPoints = new TreeSet<String>();
		partsList = new TreeSet<String>();
	}
	
	/**
	 * Set or get the list of this monster's parts and weak points
	 */
	public void setWeakPoints(ArrayList<String> list) {
		weakPoints.addAll(list);
	}
	public Set<String> getWeakPoints() {
		return weakPoints;
	}
	public void setPartsList(ArrayList<String> list) {
		partsList.addAll(list);
	}
	public Set<String> getPartsList() {
		return partsList;
	}
}
