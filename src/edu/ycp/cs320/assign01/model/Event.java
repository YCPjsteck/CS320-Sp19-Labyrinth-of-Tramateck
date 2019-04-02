package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;
import java.util.TreeMap;

public class Event {
	private TreeMap<String, String> dialogue = new TreeMap<String, String>();
	private String logKey;
	private String logLine;
	private int ticks;		//When ticks == 0 the event is failed and exited
	private int threshold;	//Will add later value to specify stat
	private Player player;
	private String playerKey;
	
	public Event(ArrayList<String> rawLog, int ticks, int threshold, Player player) {
		loadDialogue(rawLog);
		this.ticks = ticks;
		this.threshold = threshold;
		this.player = player;
		playerKey = "T";
	}
	
	public boolean isResolved() {
		return (ticks > 0);
	}
	
	public String runEvent() {
		if (dialogue.get(playerKey).indexOf("%R") < 0) {
			
			return dialogue.get(playerKey).substring(2);
		}
		return dialogue.get(playerKey).substring(2);
	}
	
	private void loadDialogue(ArrayList<String> rawLog) {
		while (!rawLog.isEmpty()) {
			logLine = rawLog.get(0).substring(rawLog.get(0).indexOf("%%") + 2);
			logKey = rawLog.get(0).substring(0, rawLog.get(0).indexOf("%%")); //KEY WOULD LOOK LIKE T, TTF, TTT, TFFF
			dialogue.put(logKey, logLine);
		}
	}
	
	private boolean roll() {
		if (player.attack() > threshold) {
			playerKey += 'T';
			return true;
		} else {
			playerKey += 'F';
			ticks--;
			return false;
		}
	}
	
}