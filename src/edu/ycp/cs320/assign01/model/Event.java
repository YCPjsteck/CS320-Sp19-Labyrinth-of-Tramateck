package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.utility.Pair;

public class Event {
	private Player player;
	private String prompt, aPassLog, aFailLog, bPassLog, bFailLog;	 //0 = hp, 1 = str, 2 = int, 3 = dex, 4 = inventory
	private Pair<Integer, Integer> aReadPair, bReadPair, aPassPair, aFailPair, bPassPair, bFailPair; //LEFT = ID, RIGHT = MAGNITUDE
	private int key, id; //KEY USED TO FIND DIALOGUE, ID USED TO IDENTIFY EVENT
	
	public Event() {		//Get prompt -> Get Dialogue after roll -> Modify Player -> Done
		key = 0;
	}
	
	private Pair<Integer,Integer> coords;
	
	public String getDialogue() {
		switch (key) {
		case 1:
			return aPassLog;
		case 2:
			return aFailLog;
		case 3:
			return bPassLog;
		case 4:
			return bFailLog;
		default:
			return null;
		}
	}
	
	public Boolean isDone() {
		return (key > 0);
	}
	
	/**
	 * True = choice A
	 * False = choice B
	 * key 0 = prompt
	 * key 1 = A pass
	 * key 2 = A fail
	 * key 3 = B pass
	 * key 4 = B fail
	 */
	public Pair<Integer, Integer> roll(boolean choice) {
		boolean pass = false; //IS SET TO TRUE IF PLAYER STATS ARE GREATER THAN NEEDED THRESHOLD
		if (choice) { //CHOICE A
			int aReadScale = aReadPair.getRight();
			key = 2;
			switch (aReadPair.getLeft()) { //SWITCH TO COMPARE CORRESPONDING STAT TO ID
			case 0:
				if (player.getHealth() >= aReadScale) {
					pass = true;
					key = 1;
				}
				break;
			case 1:
				if (player.getStrength() >= aReadScale) {
					pass = true;
					key = 1;
				}
				break;
			case 2:
				if (player.getIntellect() >= aReadScale) {
					pass = true;
					key = 1;
				}
				break;
			case 3:
				if (player.getDexterity() >= aReadScale) {
					pass = true;
					key = 1;
				}
				break;
			default:
				break;
			}
			
			if (pass) {
				return aPassPair;
			} else {
				return aFailPair;
			}
			
		} else { //CHOICE B
			int bReadScale = bReadPair.getRight();
			key = 4;
			switch (bReadPair.getLeft()) {
			case 0:
				if (player.getHealth() >= bReadScale) {
					pass = true;
					key = 3;
				}
				break;
			case 1:
				if (player.getStrength() >= bReadScale) {
					pass = true;
					key = 3;
				}
				break;
			case 2:
				if (player.getIntellect() >= bReadScale) {
					pass = true;
					key = 3;
				}
				break;
			case 3:
				if (player.getDexterity() >= bReadScale) {
					pass = true;
					key = 3;
				}
				break;
			default:
				break;
			}
			
			if (pass) {
				return bPassPair;
			} else {
				return bFailPair;
			}
		}
	}
	
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}


	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}


	public String getAPassLog() {
		return aPassLog;
	}
	public void setAPassLog(String aPassLog) {
		this.aPassLog = aPassLog;
	}

	public String getAFailLog() {
		return aFailLog;
	}
	public void setaFailLog(String aFailLog) {
		this.aFailLog = aFailLog;
	}

	public void setAReadPair(int left, int right) {
		aReadPair = new Pair<Integer, Integer>(new Integer(left), new Integer(right));
	}
	
	public void setAPassPair(int left, int right) {
		aPassPair = new Pair<Integer, Integer>(new Integer(left), new Integer(right));
	}
	
	public void setAFailPair(int left, int right) {
		aFailPair = new Pair<Integer, Integer>(new Integer(left), new Integer(right));
	}

	
	public String getBPassLog() {
		return bPassLog;
	}
	public void setBPassLog(String bPassLog) {
		this.bPassLog = bPassLog;
	}

	public String getBFailLog() {
		return bFailLog;
	}
	public void setBFailLog(String bFailLog) {
		this.bFailLog = bFailLog;
	}
	
	public void setBReadPair(int left, int right) {
		bReadPair = new Pair<Integer, Integer>(new Integer(left), new Integer(right));
	}
	
	public void setBPassPair(int left, int right) {
		bPassPair = new Pair<Integer, Integer>(new Integer(left), new Integer(right));
	}
	
	public void setBFailPair(int left, int right) {
		bFailPair = new Pair<Integer, Integer>(new Integer(left), new Integer(right));
	}

	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public Pair<Integer, Integer> getAReadPair() {
		return aReadPair;
	}


	public Pair<Integer, Integer> getBReadPair() {
		return bReadPair;
	}


	public Pair<Integer, Integer> getAPassPair() {
		return aPassPair;
	}


	public Pair<Integer, Integer> getAFailPair() {
		return aFailPair;
	}


	public Pair<Integer, Integer> getBPassPair() {
		return bPassPair;
	}


	public Pair<Integer, Integer> getBFailPair() {
		return bFailPair;
	}

	/**
	 * Resets this event back to its beginning state
	 */
	public void reset() {
		key = 0;
	}
}