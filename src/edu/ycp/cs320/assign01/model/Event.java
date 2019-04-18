package edu.ycp.cs320.assign01.model;

import edu.ycp.cs320.assign01.model.utility.Pair;

public class Event {
	private Player player;
	private String prompt, aPassLog, aFailLog, bPassLog, bFailLog;	 //0 = hp, 1 = str, 2 = int, 3 = dex, 4 = inventory
	private Pair<Integer, Integer> aReadPair, bReadPair, aPassPair, aFailPair, bPassPair, bFailPair; //LEFT = ID, RIGHT = MAGNITUDE
	private int key; //USED TO FIND DIALOGUE
	
	public Event() {
	}
	
	
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
	
	public Pair<Integer, Integer> roll(boolean choice) {
		boolean pass = false; //IS SET TO TRUE IF PLAYER STATS ARE GREATER THAN NEEDED THRESHOLD
		if (choice) { //CHOICE A
			int aReadScale = aReadPair.getRight();
			key = 2;
			switch (aReadPair.getLeft()) { //SWITCH TO COMPARE CORRESPONDING STAT TO ID
			case 0:
				if (player.getHealth() > aReadScale) {
					pass = true;
					key = 1;
				}
				break;
			case 1:
				if (player.getStrength() > aReadScale) {
					pass = true;
					key = 1;
				}
				break;
			case 3:
				if (player.getIntellect() > aReadScale) {
					pass = true;
					key = 1;
				}
				break;
			case 4:
				if (player.getHealth() > aReadScale) {
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
				if (player.getHealth() > bReadScale) {
					pass = true;
					key = 3;
				}
				break;
			case 1:
				if (player.getStrength() > bReadScale) {
					pass = true;
					key = 3;
				}
				break;
			case 3:
				if (player.getIntellect() > bReadScale) {
					pass = true;
					key = 3;
				}
				break;
			case 4:
				if (player.getHealth() > bReadScale) {
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


	public String getaPassLog() {
		return aPassLog;
	}
	public void setaPassLog(String aPassLog) {
		this.aPassLog = aPassLog;
	}


	public String getaFailLog() {
		return aFailLog;
	}
	public void setaFailLog(String aFailLog) {
		this.aFailLog = aFailLog;
	}


	public String getbPassLog() {
		return bPassLog;
	}
	public void setbPassLog(String bPassLog) {
		this.bPassLog = bPassLog;
	}


	public String getbFailLog() {
		return bFailLog;
	}
	public void setbFailLog(String bFailLog) {
		this.bFailLog = bFailLog;
	}

	public void setAReadPair(int left, int right) {
		aReadPair.setLeft(left);
		aReadPair.setRight(right);
	}
	
	public void setAPassPair(int left, int right) {
		aPassPair.setLeft(left);
		aPassPair.setRight(right);
	}
	
	public void setAFailPair(int left, int right) {
		aFailPair.setLeft(left);
		aFailPair.setRight(right);
	}
	
	public void setBReadPair(int left, int right) {
		bReadPair.setLeft(left);
		bReadPair.setRight(right);
	}
	
	public void setBPassPair(int left, int right) {
		bPassPair.setLeft(left);
		bPassPair.setRight(right);
	}
	public void setBFailPair(int left, int right) {
		bFailPair.setLeft(left);
		bFailPair.setRight(right);
	}

	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}

}