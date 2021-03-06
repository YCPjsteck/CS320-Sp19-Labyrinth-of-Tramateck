package edu.ycp.cs320.assign01.model.movement;

import java.util.ArrayList;

public class WorldMap {
	private ArrayList<Integer> locationAccess;
	private ArrayList<Location> locationList;
	private int player;
	
	public WorldMap() {
		locationList = new ArrayList<Location>();
		locationAccess = new ArrayList<Integer>();
		player = 1;
	}
	
	/**
	 * @param player the player's location ID
	 */
	public void setPlayer(int player) {
		this.player = player;
	}
	
	/**
	 * @return the player's current location ID
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * @param location a location to add to the WorldMap
	 */
	public void addLocation(Location location) {
		locationList.add(location);
	}
	/**
	 * @param locations a list of locations to add to the WorldMap
	 */
	public void addLocations(ArrayList<Location> locations) {
		this.locationList.addAll(locations);
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
		return getLocation(player);
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

	public ArrayList<Integer> getAccess() {
		return locationAccess;
	}
}
