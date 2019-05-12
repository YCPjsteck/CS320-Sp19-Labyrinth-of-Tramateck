package edu.ycp.cs320.assign01.db;

import static org.junit.Assert.assertTrue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.Account;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.utility.Pair;

public class DatabaseTest {
	private DerbyDatabase db = null;
	
	@Before
	public void setUp() {
		db = new DerbyDatabase();
	}
	
	@Test
	public void findAllPlayersTest() {
		List<Player> players = db.findAllPlayers();
		
		assertTrue(players.size() == 3);
	}

	@Test
	public void findAllAccountsTest() {
		List<Account> accounts = db.findAllAccounts();
		
		assertTrue(accounts.size() == 2);
	}
	
	@Test
	public void findPlayerByIDTest() {
		Player player = db.findPlayerByID(1);
		assertTrue(player.getName().equals("Bob"));
		assertTrue(player.getLocationID() == 0);
		assertTrue(player.getLevel() == 1);
		assertTrue(player.getExperience() == 0);
		assertTrue(player.getScore() == 0);
		assertTrue(player.getCurrency() == 0);
		assertTrue(player.getDexterity() == 10);
		assertTrue(player.getStrength() == 10);
		assertTrue(player.getIntellect() == 10);
		assertTrue(player.getArmorID() == 0);
		assertTrue(player.getWeaponID() == 0);
		assertTrue(player.getAccessoryID() == 0);
	}
	
	@Test
	public void findPlayersByAccountIDTest() {
		List<Player> players = db.findPlayersByAccountID(1);
		
		assertTrue(players.size() == 2);
		
		Player player = players.get(0);
		assertTrue(player.getId() == 1);
		assertTrue(player.getName().equals("Bob"));
		assertTrue(player.getLocationID() == 0);
		assertTrue(player.getLevel() == 1);
		assertTrue(player.getExperience() == 0);
		assertTrue(player.getScore() == 0);
		assertTrue(player.getCurrency() == 0);
		assertTrue(player.getDexterity() == 10);
		assertTrue(player.getStrength() == 10);
		assertTrue(player.getIntellect() == 10);
		assertTrue(player.getArmorID() == 0);
		assertTrue(player.getWeaponID() == 0);
		assertTrue(player.getAccessoryID() == 0);
		
		player = players.get(1);
		assertTrue(player.getId() == 3);
		assertTrue(player.getName().equals("Billy"));
		assertTrue(player.getLocationID() == 3);
		assertTrue(player.getLevel() == 3);
		assertTrue(player.getExperience() == 3);
		assertTrue(player.getScore() == 3);
		assertTrue(player.getCurrency() == 3);
		assertTrue(player.getDexterity() == 3);
		assertTrue(player.getStrength() == 3);
		assertTrue(player.getIntellect() == 3);
		assertTrue(player.getArmorID() == 3);
		assertTrue(player.getWeaponID() == 3);
		assertTrue(player.getAccessoryID() == 3);
	}
	
	
	/**
	 * Test finds account based on Username and Password
	 * 			-Confirms that the stored account ID and username are correct
	 * 			-Confirms that password is correct by encrypting entered password
	 * 			and checking it with the stored encrypted password
	 */
	@Test
	public void findAccountByUsernameTest() {
		
		// Test account 1
		
		Account account = db.findAccountByUsername("user");
		assertTrue(account.getId() == 1);
		assertTrue(account.getUsername().equals("user"));
		
		String password = "Hunter2";
		
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String encryptedPassword = new String(messageDigest.digest());
			assertTrue(account.getPassword().equals(encryptedPassword));
		} catch (NoSuchAlgorithmException e) {
			System.err.println("MessageDigest Instance is not valid");
		}
		
		// Test account 2
		
		account = db.findAccountByUsername("admin");
		assertTrue(account.getId() == 2);
		assertTrue(account.getUsername().equals("admin"));
		
		password = "admin";
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String encryptedPassword = new String(messageDigest.digest());
			assertTrue(account.getPassword().equals(encryptedPassword));
		} catch (NoSuchAlgorithmException e) {
			System.err.println("MessageDigest Instance is not valid");
		}
	}
	
	@Test
	public void findInventoryByPlayerIDTest() {
		List<Pair<Integer,Integer>> inventory = db.findInventoryByPlayerID(2);
		
		assertTrue(inventory.size() == 4);
		assertTrue(inventory.get(0).getLeft() == 1);
		assertTrue(inventory.get(0).getRight() == 2);
		assertTrue(inventory.get(1).getLeft() == 2);
		assertTrue(inventory.get(1).getRight() == 2);
		assertTrue(inventory.get(2).getLeft() == 3);
		assertTrue(inventory.get(2).getRight() == 2);
		assertTrue(inventory.get(3).getLeft() == 4);
		assertTrue(inventory.get(3).getRight() == 2);
	}
	
	@Test
	public void findAccessByPlayerIDTest() {
		List<Integer> access = db.findAccessByPlayerID(3);
		
		assertTrue(access.size() == 3);
		assertTrue(access.get(0) == 1);
		assertTrue(access.get(1) == 2);
		assertTrue(access.get(2) == 3);
	}
}
