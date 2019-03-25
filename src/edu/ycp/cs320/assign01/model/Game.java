package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

public class Game {
	private Player player;
	private Location dungeon;
	private ArrayList<String> gameLog;
	
	public Game() {
		player = new Player();
		int[][] map = {	{0, 0, 4},
						{1, 2, 3},
						{0, 0, 5}};
		dungeon = new Location();
		dungeon.setMap(map);
		dungeon.generateRooms();
		dungeon.setPlayer(1, 0);
		
		ArrayList<String> monsters = new ArrayList<String>();
		
		monsters.add("zombie");
		Room room = dungeon.getRoom(2);
		room.populate(monsters);
		monsters.clear();
		
		monsters.add("skeleton");
		room = dungeon.getRoom(3);
		room.populate(monsters);
		monsters.clear();
		
		monsters.add("ghoul");
		monsters.add("witch");
		room = dungeon.getRoom(4);
		room.populate(monsters);
		monsters.clear();
	}
	
	public Game(Player player, Location dungeon) {
		this.player = player;
		this.dungeon = dungeon;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Location getDungeon() {
		return dungeon;
	}
}
