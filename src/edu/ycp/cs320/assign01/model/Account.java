package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

public class Account {
	private int id;
	private String username, password;
	private ArrayList<Player> players;
	
	// TODO hashed passwords

	public Account() {
		players = new ArrayList<Player>();
	}
	
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		players = new ArrayList<Player>();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
