package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

public class WorldMap implements Navigable {
	private ArrayList<Location> locationList, locationAccess;
	private int[][] locationMap;
	private int playerX, playerY;
	
	public WorldMap() {
		locationList = new ArrayList<Location>();
	}

	public void setMap(int[][] map) {
		locationMap = map;
	}

	public void setPlayer(int x, int y) {
		playerX = x;
		playerY = y;
	}

	public void addLocation(Location l) {
		locationList.add(l);
	}
	
	public ArrayList<Location> getLocations() {
		return locationList;
	}
	
	public Location curLocation() {
		int x = playerX;
		int y = playerY;
		int id = locationMap[x][y];
		
		return getLocation(id);
	}
	
	public Location getLocation(int id) {
		for(Location l : locationList)
			if(l.getId() == id)
				return l;
		
		return null;
	}
	
	public boolean hasAccess(Location l) {
		return (locationAccess.contains(l));
	}
	public void grantAccess(Location l) {
		locationAccess.add(l);
	}
}
