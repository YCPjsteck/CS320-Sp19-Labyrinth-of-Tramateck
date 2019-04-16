package edu.ycp.cs320.assign01.model.movement;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.interfaces.Named;

public class Room implements Named {
	private ArrayList<String> monsters;
	private ArrayList<NPC> monsterList;
	private String shortDesc, longDesc;
	private int roomID;
	private boolean entered;
	private boolean start, exit;
	
	// TODO: 
	//		Add the ability for rooms to contain puzzles
	//		Long description vs short description
	
	public Room() {
		monsters = new ArrayList<String>();
		monsterList = new ArrayList<NPC>();
		entered = false;
	}
	
	/**
	 * A set of methods for setting and querying the
	 * room's ID number and description
	 */
	public void setId(int ID) {
		roomID = ID;
	}
	public int getId() {
		return roomID;
	}
	public void setLongDesc(String desc) {
		longDesc = desc;
	}
	public void setShortDesc(String desc) {
		shortDesc = desc;
	}
	public String getLongDesc() {
		return longDesc;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	
	// TODO: Replace this method with getLongDesc and getShortDesc
	public String getDescription() {
		// return roomDescription;
		String description = "This room contains: ";
		for(String s : monsters) 
			description += s + " ";
		/*
		for(Monster m : monsterList)
			if(!m.isDead())
				description += m.getName() + " "; 
				*/
		if(description.equals("This room contains: "))
			description += "nothing";
		
		return description;
	}
	
	// TODO: randomly populate the monsters list
	public void populate(ArrayList<String> list) {
		monsters.addAll(list);
		int id = 1;
		for(String s : monsters) {
			NPC monster = new NPC();
			monster.setLevel(1);
			monster.setId(id);
			monster.setName(s);
			id++;
			monsterList.add(monster);
		}
	}
	
	// TODO: return a list of monster objects
	public ArrayList<String> getMonsters() {
		return monsters;
	}
	
	public NPC getMonster(int id) {
		for(NPC m : monsterList)
			if(m.getId() == id)
				return m;
		
		return null;
	}
	public NPC getMonster(String name) {
		for(NPC m : monsterList)
			if(m.getName().equals(name))
				return m;
		
		return null;
	}
	// TODO: be able to track specific monsters in a room by an ID number,
	// meaning that two monsters can share the same name in a room.
	public void monsterKilled(String monster) {
		for(int i = 0; i < monsters.size(); i++)
			if(monster.equals(monsters.get(i))) {
				monsters.remove(i);
				return;
			}
	}
	
	/**
	 * The room is complete is there are no more monsters left alive.
	 */
	public boolean roomComplete() {
		/*
		for(Monster m : monsterList)
			if(!m.isDead())
				return false;
		return true;
		*/
		return (monsters.size() == 0);
	}

	/**
	 * Rooms don't need names; these are only here from the
	 * Named interface
	 */
	public void setName(String name) {
	}
	public String getName() {
		return null;
	}
	
	public void isStart() {
		start = true;
	}
	public boolean getStart() {
		return start;
	}
	
	public void isExit() {
		exit = true;
	}
	public boolean getExit() {
		return exit;
	}
	
}