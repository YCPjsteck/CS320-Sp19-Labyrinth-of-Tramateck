package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Account {
	private int id;
	private String username, password;
	private ArrayList<Player> players;
	
	// TODO hashed passwords

	public Account() {
		players = new ArrayList<Player>();
	}
	
	
	/**
	 * Creates account using MessageDigest instance "SHA-256" to encrypt the password
	 * If Message Digest instance is not found, throws NoSuchAlgorithmException
	 */
	public Account(String username, String password) {
		this.username = username;
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String encryptedPassword = new String(messageDigest.digest());
			this.password = encryptedPassword;
			players = new ArrayList<Player>();
		} catch (NoSuchAlgorithmException e) {
			System.err.println("MessageDigest Instance is not valid");
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets account password using MessageDigest instance "SHA-256" to encrypt the password
	 * If Message Digest instance is not found, throws NoSuchAlgorithmException
	 */
	public void setPassword(String password) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String encryptedPassword = new String(messageDigest.digest());
			this.password = encryptedPassword;
		} catch (NoSuchAlgorithmException e) {
			System.err.println("MessageDigest Instance is not valid");
		}
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
