package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

public class Dungeon {
	private ArrayList<Room> roomList;
	private int[][] roomMap;
	private int roomX, roomY;

	public Dungeon() {
		roomList = new ArrayList<Room>();
	}
	public Dungeon(int[][] map) {
		roomMap = map;
		roomList = new ArrayList<Room>();
	}
	
	/**
	 * Set the dungeon's active room location
	 * Assumes that the location is valid.
	 */
	public void setActiveLocation(int x, int y) {
		roomX = x;
		roomY = y;
	}
	
	// TODO: randomly generate the map instead of having the map
	// be passed to the Dungeon on creation.
	public void generateMap() {
		generateRooms();
	}
	
	/**
	 * Creates the rooms in the dungeon based off of
	 * the roomMap 2D array. Each created room is given an ID
	 * number based off of the roomMap.
	 * Assumed that no IDs repeat.
	 */
	public void generateRooms() {
		for(int i = 0; i < roomMap.length; i++) {
			for(int j = 0; j < roomMap[0].length; j++) {
				if(roomMap[i][j] != 0) {
					Room r = new Room();
					r.setID(roomMap[i][j]);
					roomList.add(r);
				}
			}
		}
	}
	
	/**
	 * When given the direction that the player wishes to travel,
	 * returns whether or not the player is able to travel in that direction
	 * by checking the roomMap.
	 */
	public boolean canTravel(String direction) {
		int x = roomX;
		int y = roomY;
		
		if(direction.equals("north"))
			return (y-1 >= 0 && roomMap[x][y-1] != 0);
		else if(direction.equals("south"))
			return (y+1 <= roomMap[0].length-1 && roomMap[x][y+1] != 0);
		else if(direction.equals("east"))
			return (x+1 <= roomMap[0].length-1 && roomMap[x+1][y] != 0);
		else if(direction.equals("west"))
			return (x-1 >= 0 && roomMap[x-1][y] != 0);
		else
			return false;
	}
	
	/**
	 * Changes the player's location in the dungeon.
	 * Assumes that the move is valid.
	 */
	public void travel(String direction) {
		if(direction.equals("north"))
			roomY--;
		else if(direction.equals("south"))
			roomY++;
		else if(direction.equals("east"))
			roomX++;
		else if(direction.equals("west"))
			roomX--;
	}
	
	/**
	 * Returns the current room that the player
	 * is located in.
	 */
	public Room curRoom() {
		int x = roomX;
		int y = roomY;
		int id = roomMap[x][y];
		
		for(Room r : roomList)
			if(r.getID() == id)
				return r;
		
		return null;
	}
	
	/**
	 * Returns the room with the given ID number
	 */
	public Room getRoom(int id) {
		for(Room r : roomList)
			if(r.getID() == id)
				return r;
		
		return null;
	}

	/**
	 * Check if the dungeon is complete by checking
	 * if each room is complete
	 */
	public boolean dungeonComplete() {
		for(Room r : roomList)
			if(!r.roomComplete())
				return false;
		return true;
	}
	
	public ArrayList<String> getMapString() {
		ArrayList<String> str = new ArrayList<String>();
		String temp = "";
		
		for(int j = 0; j < roomMap[0].length; j++) {
			for(int i = 0; i < roomMap.length; i++) {
				if(roomMap[i][j] != 0) {
					if(i == roomX && j == roomY)
						temp += "x ";
					else
						temp += "o ";
				} else {
					temp += "  ";
				}
			}
			str.add(temp);
			temp = "";
		}
		return str;
	}
}
