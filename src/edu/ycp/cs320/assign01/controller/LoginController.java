package edu.ycp.cs320.assign01.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ycp.cs320.assign01.db.DerbyDatabase;
import edu.ycp.cs320.assign01.model.Account;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.WorldMap;

public class LoginController {
	private Player player;
	private WorldMap game;
	private Account accountModel;
	private DerbyDatabase db;
	
	public LoginController(Player player) {
		this.player = player;
	}
	
	public boolean validateCredentials(String username, String password) {
		boolean valid = false;
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String encryptedPassword = new String(messageDigest.digest());
			// check with account to see if contains username with encryptedPassword
			accountModel = db.findAccountByUsername(username);
			if (accountModel.correctPassword(encryptedPassword)) {
				valid = true;
			}
		} catch (NoSuchAlgorithmException e) {
			System.err.println("MessageDigest Instance is not valid");
		}
		return valid;
	}
	
	public String login(String username, String password) {
		String output = "";
		/**
		 * -encrypt password
		 * -Check username and password with database
		 * -if username and password exist in database
		 * 		-switch account to account with that username
		 * 		-return that the login was successful
		 * -if not
		 * 		-return login credentials were not valid
		 */
		return output;
	}
}
