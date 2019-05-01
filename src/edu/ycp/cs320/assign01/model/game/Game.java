package edu.ycp.cs320.assign01.model.game;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.Room;

public class Game {
	private Player player;
	private Location dungeon;
	
	public Game() {
		player = new Player();
		dungeon = new Location();
		// TODO: use the library class instead of creating your own location
		/*
		int[][] map = {	{0, 0, 4},
						{1, 2, 3},
						{0, 0, 5}};
		dungeon.setMap(map);
		dungeon.generateRooms();
		dungeon.setPlayer(1, 0);
		*/
		
		// TODO create actual NPC objects to populate the rooms with.
		/*
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
		*/
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
