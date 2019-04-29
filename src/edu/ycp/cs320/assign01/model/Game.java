package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

public class Game {
	private Player player;
	private Location dungeon;
	
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
	
	public String stringify() {
		String str = "";
		str += dungeon.getX() + " " + dungeon.getY();
		ArrayList<Room> rooms = dungeon.getRooms();
		for(Room r : rooms)
			if(r.roomComplete())
				str += " " + 1;
			else
				str += " " + 0;
		return str;
	}
	
	// Word 0 = X
	// Word 1 = Y
	// Word 2... = If the room is complete
	public void reconstruct(String str) {
		WordFinder finder = new WordFinder();
		ArrayList<String> words = finder.findWords(str);
		if(words.size() > 0) {
			int x = Integer.parseInt(words.get(0));
			int y = Integer.parseInt(words.get(1));
			dungeon.setPlayer(x, y);
			ArrayList<Room> rooms = dungeon.getRooms();
			for(int i = 2; i < words.size(); i++)
				if(Integer.parseInt(words.get(i)) == 1)
					rooms.get(i-2).getMonsters().clear();
		}
	}
}
