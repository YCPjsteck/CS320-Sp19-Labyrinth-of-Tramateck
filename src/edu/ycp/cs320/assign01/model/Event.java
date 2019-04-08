package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;
import java.util.TreeMap;

public class Event {
	private Player player;
	private String prompt, aPassLog, aFailLog, bPassLog, bFailLog;	 //0 = hp, 1 = str, 2 = int, 3 = dex, 4 = inventory
	private int aReadId, aReadScale, aPassId, aPassScale, aFailId, aFailScale,
				bReadId, bReadScale, bPassId, bPassScale, bFailId, bFailScale;
	private int key;
	
	public Event(Player player) {
		this.player = player;
		
	}
	public String getPrompt() {
		return prompt;
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
	
	public EventResult roll(boolean choice) {
		boolean pass = false;
		if (choice) {
			key = 2;
			switch (aReadId) {
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
				return new EventResult(aPassId, aPassScale);
			} else {
				return new EventResult(aFailId, aFailScale);
			}
			
		} else {
			key = 4;
			switch (bReadId) {
			case 0:
				if (player.getHealth() > bReadScale) {
					pass = true;
					key = 1;
				}
				break;
			case 1:
				if (player.getStrength() > bReadScale) {
					pass = true;
					key = 1;
				}
				break;
			case 3:
				if (player.getIntellect() > bReadScale) {
					pass = true;
					key = 1;
				}
				break;
			case 4:
				if (player.getHealth() > bReadScale) {
					pass = true;
					key = 1;
				}
				break;
			default:
				break;
			}
			
			if (pass) {
				return new EventResult(bPassId, bPassScale);
			} else {
				return new EventResult(bFailId, bFailScale);
			}
		}
	}

}