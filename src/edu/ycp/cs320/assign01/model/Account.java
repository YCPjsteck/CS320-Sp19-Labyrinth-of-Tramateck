package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

public class Account {
	private String username, password;
	private ArrayList<Player> players;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		players = new ArrayList<Player>();
	}
	
	public boolean correctPassword(String password) {
		return (password.equals(this.password));
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	// TODO players need an ID number and name
	public Player getPlayer(int i) {
		/*
		for(Player p : players)
			if(p.getID() == i)
				return p;
		*/
		return null;
	}
}
