package edu.ycp.cs320.assign01.model.movement;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.Event;
import edu.ycp.cs320.assign01.model.NPC;
import edu.ycp.cs320.assign01.model.interfaces.Named;

public class Room implements Named {
	private ArrayList<NPC> npcList;
	private ArrayList<Event> eventList;
	private String shortDesc, longDesc, name;
	private int id;
	private boolean entered;
	private boolean start, exit;
	
	// TODO: 
	//		Implement events
	//		Rooms override travel
	//		Rooms connected to non-adjacent rooms
	
	public Room() {
		npcList = new ArrayList<NPC>();
		entered = false;
		start = false;
		exit = false;
	}
	
	/***************
	 * NPC methods *
	 ***************/
	/**
	 * Add an NPC to this room
	 */
	public void addNPC(NPC n) {
		npcList.add(n);
	}
	/**
	 * @return the arrayList of NPCs in this room
	 */
	public ArrayList<NPC> getNPCs() {
		return npcList;
	}
	/**
	 * @return an arrayList of the names of all the NPCs in this room
	 */
	public ArrayList<String> getNPCNames() {
		ArrayList<String> names = new ArrayList<String>();
		for(NPC n : npcList) {
			names.add(n.getName());
		}
		return names;
	}
	/**
	 * Find an NPC by its ID
	 */
	public NPC getNPC(int id) {
		for(NPC m : npcList)
			if(m.getId() == id)
				return m;
		
		return null;
	}
	/**
	 * Find an NPC by its name
	 */
	public NPC getNPC(String name) {
		for(NPC m : npcList)
			if(m.getName().equals(name))
				return m;
		
		return null;
	}
	
	/**
	 * The room is complete if there are no more hostile NPCs left alive and all events are complete.
	 */
	// TODO: check for events and make distinctions between hostile and friendly NPCs
	public boolean roomComplete() {
		for(Event e : eventList)
			if(!e.isDone()) {
				return false;
			}
		for(NPC n : npcList)
			if(!n.isDead()) {
				return false;
			}
		return true;
		
	}
	/*****************
	 * Event methods *
	 *****************/
	/**
	 * 
	 */
	public void runEvents() {
		
	}

	/*******************
	 * Boolean methods *
	 *******************/
	/**
	 * Toggles the entered boolean of this rooms. Starts off false.
	 */
	public void isEntered() {
		entered = !entered;
	}
	/**
	 * @return the entered boolean of this room
	 */
	public boolean getEntered() {
		return entered;
	}
	/**
	 * Toggles the start boolean of this rooms. Starts off false.
	 */
	public void isStart() {
		start = !start;
	}
	/**
	 * @return the start boolean of this room
	 */
	public boolean getStart() {
		return start;
	}
	/**
	 * Toggles the exit boolean of this rooms. Starts off false.
	 */
	public void isExit() {
		exit = !exit;
	}
	/**
	 * @return the exit boolean of this room
	 */
	public boolean getExit() {
		return exit;
	}

	/*************************************
	 * Name, ID, and description methods *
	 *************************************/
	/**
	 * Set this room's ID number
	 */
	public void setId(int ID) {
		this.id = ID;
	}
	/**
	 * Get this room's ID number
	 */
	public int getId() {
		return id;
	}
	/**
	 * Set this room's long description
	 */
	public void setLongDesc(String desc) {
		longDesc = desc;
	}
	/**
	 * Set this room's short description
	 */
	public void setShortDesc(String desc) {
		shortDesc = desc;
	}
	/**
	 * Get this room's long description
	 */
	public String getLongDesc() {
		String desc = "";
		if(name != null) {
			desc += "This room is known as " + name + " ";
		}
		return desc + longDesc + getContains();
	}
	/**
	 * Get this room's short description
	 */
	public String getShortDesc() {
		return shortDesc + getContains();
	}
	/**
	 * Creates a string that says what NPCs are in this room
	 */
	// TODO return infomration about events.
	// Short contains vs long contains?
	private String getContains() {
		String desc = " ";
		if(npcList.size() > 0) {
			String cont = "This room contains: ";
			for(NPC n : npcList) {
				if(!n.isDead())
					cont += "a " + n.getName() + ", ";
				if(n.isDead())
					cont += "a dead " + n.getName() + ", ";
			}
			if(!cont.equals("This room contains: "))
				desc += cont;
		}
		return desc;
	}
	/**
	 * Set this room's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Get this room's name
	 */
	public String getName() {
		return name;
	}
}