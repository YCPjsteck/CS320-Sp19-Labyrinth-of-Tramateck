package edu.ycp.cs320.assign01.model.movement;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.interfaces.Navigable;

public class WorldMap implements Navigable {
	private ArrayList<Integer> locationAccess;
	private ArrayList<Location> locationList;
	private int[][] locationMap;
	private int playerX, playerY;
	
	public WorldMap() {
		locationList = new ArrayList<Location>();
	}

	/**
	 * @param map the 2D array of integers representing the location IDs of this WorldMap
	 */
	public void setMap(int[][] map) {
		locationMap = map;
	}
	
	/**
	 * @return the 2D array of integers representing to location IDs of this WorldMap
	 */
	public int[][] getMap() {
		return locationMap;
	}
	
	/**
	 * Set the player's x,y coordinates on the location map
	 */
	public void setPlayer(int x, int y) {
		playerX = x;
		playerY = y;
	}
	
	// TODO: these two methods are duplicated in WorldMap and Location,
	// 		 make a MapMaker utility class?
	/**
	 * Print the room map to the console
	 */
	public void printMap() {
		for(int j = 0; j < locationMap[0].length; j++) {
			for(int i = 0; i < locationMap.length; i++) {
				if(locationMap[i][j] != 0) {
					if(i == playerX && j == playerY)
						System.out.print("x ");
					else
						System.out.print("o ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * @return an arrayList of strings representing the room map
	 */
	public ArrayList<String> getMapString() {
		ArrayList<String> temp = new ArrayList<String>();
		String str = "";
		
		for(int j = 0; j < locationMap[0].length; j++) {
			for(int i = 0; i < locationMap.length; i++) {
				if(locationMap[i][j] != 0) {
					if(i == playerX && j == playerY)
						str += "-x-";
					else
						str += "-o-";
				} else {
					str += "---";
				}
			}
			temp.add(str);
			str = "";
		}
		return temp;
	}


	/**
	 * @param l a location to add to the list of locations
	 */
	public void addLocation(Location l) {
		locationList.add(l);
	}
	
	/**
	 * @return a list of all locations
	 */
	public ArrayList<Location> getLocations() {
		return locationList;
	}
	
	/**
	 * @return the current location that the player is in
	 */
	public Location curLocation() {
		int x = playerX;
		int y = playerY;
		int id = locationMap[x][y];
		
		return getLocation(id);
	}
	
	/**
	 * @param id the ID number of the location being looked for
	 * @return the location matching the given ID number
	 */
	public Location getLocation(int id) {
		for(Location l : locationList)
			if(l.getId() == id)
				return l;
		
		return null;
	}
	
	/**
	 * @param l the ID number of a location
	 * @return true if the player has access to this location
	 */
	public boolean hasAccess(int l) {
		return (locationAccess.contains(l));
	}
	/**
	 * @param l the ID number of the location the player is being given access to
	 */
	public void grantAccess(int l) {
		locationAccess.add(l);
	}
}
