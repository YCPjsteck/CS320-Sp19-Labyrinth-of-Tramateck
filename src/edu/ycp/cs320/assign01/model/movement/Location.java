package edu.ycp.cs320.assign01.model.movement;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.enums.LocationType;
import edu.ycp.cs320.assign01.model.interfaces.Named;

public class Location implements Named {
	private ArrayList<Room> roomList;
	private int[][] roomMap;
	private int playerX, playerY, id;
	private String shortDesc, longDesc, name;
	
	private int minLevel, maxLevel;
	private LocationType type;

	public Location() {
		roomList = new ArrayList<Room>();
	}
	
	/**
	 * @param r a room to be added to this location
	 */
	public void addRoom(Room r) {
		roomList.add(r);
	}
	
	/**
	 * @return the arraylist of all rooms in this location
	 */
	public ArrayList<Room> getRooms() {
		return roomList;
	}
	
	/**
	 * @param map the 2D array of integers representing the room IDs of this location
	 */
	public void setMap(int[][] map) {
		roomMap = map;
	}
	
	/**
	 * @return the 2D array of integers representing to room IDs of this location
	 */
	public int[][] getMap() {
		return roomMap;
	}
	
	/**
	 * Set the player's x,y position in the room map.
	 * Assumes that the location is valid.
	 */
	public void setPlayer(int x, int y) {
		playerX = x;
		playerY = y;
	}

	/**
	 * @return the player's x position
	 */
	public int getX() {
		return playerX;
	}
	
	/**
	 * @return the player's y position
	 */
	public int getY() {
		return playerY;
	}
	
	/**
	 * Finds the room that has the start boolean set to true and 
	 * sets the player's coordinates to the coordinates of that room
	 */
	public void findStart() {
		for(int j = 0; j < roomMap[0].length; j++) {
			for(int i = 0; i < roomMap.length; i++) {
				if(roomMap[i][j] != 0 && getRoom(roomMap[i][j]).getStart()) {
					getRoom(roomMap[i][j]).isEntered();
					setPlayer(i,j);
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
		int x = playerX;
		int y = playerY;
		
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
			playerY--;
		else if(direction.equals("south"))
			playerY++;
		else if(direction.equals("east"))
			playerX++;
		else if(direction.equals("west"))
			playerX--;
	}
	
	/**
	 * Returns the current room that the player
	 * is located in.
	 */
	public Room curRoom() {
		int x = playerX;
		int y = playerY;
		int id = roomMap[x][y];
		for(Room r : roomList)
			if(r.getId() == id)
				return r;

		return null;
	}
	
	/**
	 * Returns the room with the given ID number
	 */
	public Room getRoom(int id) {
		for(Room r : roomList)
			if(r.getId() == id)
				return r;
		
		return null;
	}

	/**
	 * Check if the location is complete by checking
	 * if each room is complete
	 */
	public boolean locationComplete() {
		for(Room r : roomList)
			if(!r.roomComplete())
				return false;
		return true;
	}
	
	/**
	 * Print the room map to the console
	 */
	public void printMap() {
		for(int j = 0; j < roomMap[0].length; j++) {
			for(int i = 0; i < roomMap.length; i++) {
				if(roomMap[i][j] != 0) {
					if(i == playerX && j == playerY)
						System.out.print("-X-");
					else if(getRoom(roomMap[i][j]).getEntered())
						System.out.print("-O-");
					else if(isConnected(i,j))
						System.out.print("-?-");
					else
						System.out.print("---");
				} else {
					System.out.print("---");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * @return an arrayList of strings representing the room map
	 */
	public ArrayList<String> getMapArray() {
		ArrayList<String> temp = new ArrayList<String>();
		String str = "";
		
		for(int j = 0; j < roomMap[0].length; j++) {
			for(int i = 0; i < roomMap.length; i++) {
				if(roomMap[i][j] != 0) {
					if(i == playerX && j == playerY)
						str += "-X-";
					else if(getRoom(roomMap[i][j]).getEntered())
						str += "-O-";
					else if(isConnected(i,j))
						str += "-?-";
					else
						str += "---";
				} else {
					str += "---";
				}
			}
			temp.add(str);
			str = "";
		}
		return temp;
	}
	
	public String getMapString() {
		String str = "";
		
		for(int j = 0; j < roomMap[0].length; j++) {
			for(int i = 0; i < roomMap.length; i++) {
				if(roomMap[i][j] != 0) {
					if(i == playerX && j == playerY)
						str += "-X-";
					else if(getRoom(roomMap[i][j]).getEntered())
						str += "-O-";
					else if(isConnected(i,j))
						str += "-?-";
					else
						str += "---";
				} else {
					str += "---";
				}
			}
			str += "\n";
		}
		return str;
	}
	
	public boolean isConnected(int x, int y) {
		if(		(x+1 <= roomMap[0].length-1 && roomMap[x+1][y] != 0 && getRoom(roomMap[x+1][y]).getEntered()) ||
				(x-1 >= 0 && roomMap[x-1][y] != 0 && getRoom(roomMap[x-1][y]).getEntered()) ||
				(y+1 <= roomMap[0].length-1 && roomMap[x][y+1] != 0 && getRoom(roomMap[x][y+1]).getEntered()) ||
				(y-1 >= 0 && roomMap[x][y-1] != 0 && getRoom(roomMap[x][y-1]).getEntered()) )
			return true;
		return false;
	}

	/**
	 * Set this location's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Get this location's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set this location's ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Get this location's ID
	 */
	public int getId() {
		return id;
	}
	/**
	 * Set this location's long description
	 */
	public void setLongDesc(String desc) {
		longDesc = desc;
	}
	/**
	 * Set this location's short description
	 */
	public void setShortDesc(String desc) {
		shortDesc = desc;
	}
	/**
	 * Get this location's long description
	 */
	public String getLongDesc() {
		return longDesc;
	}
	/**
	 * Get this location's short description
	 */
	public String getShortDesc() {
		return shortDesc;
	}
	
	/**
	 * @param minLevel this location's min NPC level
	 */
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}
	/**
	 * @return this location's min NPC level
	 */
	public int getMinLevel() {
		return minLevel;
	}
	/**
	 * 
	 * @param maxLevel this location's max NPC level
	 */
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	/**
	 * @return this location's max NPC level
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * @param type this location's LocationType
	 */
	public void setType(String type) {
		this.type = LocationType.toType(type);
	}
	/**
	 * @return this location's LocationType
	 */
	public LocationType getType() {
		return type;
	}
}
