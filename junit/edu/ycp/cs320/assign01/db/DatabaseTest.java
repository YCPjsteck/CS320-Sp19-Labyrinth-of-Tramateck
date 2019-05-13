package edu.ycp.cs320.assign01.db;

import static org.junit.Assert.assertTrue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.Account;
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.Location;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.Triple;

public class DatabaseTest {
	private DerbyDatabase db = null;
	
	@Before
	public void setUp() {
		db = new DerbyDatabase();
	}
	
	@Test
	public void findAllPlayersTest() {
		List<Player> players = db.findAllPlayers();
		System.out.println("\nno. players: " + players.size());
		
		for (Player player : players) {
			System.out.println("ID: " + player.getId() + " || Name: " + player.getName());
		}
		assertTrue(players.size() == 3);
	}

	@Test
	public void findAllAccountsTest() {
		List<Account> accounts = db.findAllAccounts();
		System.out.println("\nno. accounts: " + accounts.size());
		
		for (Account account : accounts) {
			System.out.println("ID: " + account.getId() + " || Username: " + account.getUsername());
		}
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
	
	@Test
	public void findAllPlayerAccountsTest() {
		List<Pair<Integer, Integer>> playerAccounts = db.findAllPlayerAccounts();
		System.out.println("\nno. playerAccounts: " + playerAccounts.size());
		
		for (Pair<Integer,Integer> playerAccount : playerAccounts) {
			System.out.println("Player ID: " + playerAccount.getLeft() + " || Account ID: " + playerAccount.getRight());
		}
	}
	
	@Test
	public void testRemoveAccount() {
		System.out.println("\n*** Testing removeAccount ***");

		String username    		= "testusername";
		String username2     	= "testusername2";
		String password   		= "testpassword";
		String name    			= "testname";
		String name2    			= "testname2";
		int endAccountSize 		= db.findAllAccounts().size();
		int endPlayerSize 		= db.findAllPlayers().size();
		Integer account1 = db.insertAccount(username, password);
		Integer account2 = db.insertAccount(username2, password);
		
		db.insertPlayerIntoAccount(account2, name);
		db.insertPlayerIntoAccount(account2, name2);
		
		System.out.println("*TEST: Removing account");
		assertTrue(db.removeAccount(account1).getId() == account1);
		System.out.println("*TEST: Removing account with 2 players");
		assertTrue(db.removeAccount(account2).getId() == account2);
		System.out.println("*TEST: Removing account with invalid account ID");
		assertTrue(db.removeAccount(account2 + 1) == null);
		
		
		System.out.println("*TEST: Account and Player amount at start are equal to end amounts");
		assertTrue(endAccountSize == db.findAllAccounts().size());
		assertTrue(endPlayerSize == db.findAllPlayers().size());
	}
	
	@Test
	public void testRemovePlayer() {
		System.out.println("\n*** Testing removePlayer ***");

		String username    		= "testusername";
		String password   		= "testpassword";
		String name    			= "testname";
		int endSize 			= db.findAllPlayers().size();
		Integer account1 		= db.insertAccount(username, password);
		Integer player1 		= db.insertPlayerIntoAccount(account1, name);

		System.out.println("*TEST: Removing player");
		assertTrue(db.removePlayer(player1).getId() == player1);
		System.out.println("*TEST: Removing player with invalid ID");
		assertTrue(db.removePlayer(player1) == null);
		assertTrue(endSize == db.findAllAccounts().size());
		
		db.removeAccount(db.findAccountByUsername(username).getId());
	}
	
	@Test
	public void testRemoveInventory() {
		System.out.println("\n*** Testing removeInventory ***");

		String username    		= "testusername";
		String password   		= "testpassword";
		String name    			= "testname";
		Integer account 		= db.insertAccount(username, password);
		Integer player 		= db.insertPlayerIntoAccount(account, name);
		Triple<Integer, Integer, Integer> inventory;
		
		db.insertInventory(player, -1, 20);
		System.out.println("*TEST: Removing inventory of invalid item id");
		assertTrue(db.removeInventory(player, -2) == null);
		System.out.println("*TEST: Removing inventory of invalid player id");
		assertTrue(db.removeInventory(player + 1, -1) == null);
		System.out.println("*TEST: Removing inventory");
		inventory = db.removeInventory(player, -1);
		assertTrue(inventory.getLeft() == player.intValue());
		assertTrue(inventory.getMiddle() == -1);
		assertTrue(inventory.getRight() == 20);

		db.removeAccount(db.findAccountByUsername(username).getId());
	}
	
	@Test
	public void testRemoveInventoryByItemAmount() {
		System.out.println("\n*** Testing removeInventoryByItemAmount ***");

		String username    		= "testusername";
		String password   		= "testpassword";
		String name    			= "testname";
		Integer account 		= db.insertAccount(username, password);
		Integer player 		= db.insertPlayerIntoAccount(account, name);
		Triple<Integer, Integer, Integer> inventory;
		Pair<Integer, Integer> foundInventory;
		
		db.insertInventory(player, -1, 20);
		System.out.println("*TEST: Removing inventory of invalid item id");
		assertTrue(db.removeInventoryByItemAmount(player, -2, 20) == null);
		System.out.println("*TEST: Removing inventory of invalid player id");
		assertTrue(db.removeInventoryByItemAmount(player + 1, -1, 20) == null);
		System.out.println("*TEST: Removing inventory partially");
		inventory = db.removeInventoryByItemAmount(player, -1, 10);
		assertTrue(inventory != null);
		foundInventory = db.findInventoryByPlayerID(player).get(0);
		System.out.println("*TEST: Removing inventory partially");
		assertTrue(inventory.getLeft() == player.intValue());
		assertTrue(inventory.getMiddle() == foundInventory.getLeft());
		assertTrue(inventory.getRight() == foundInventory.getRight());
		db.removeAccount(db.findAccountByUsername(username).getId());
		
	}
	
	@Test
	public void testInsertAccount() {
		System.out.println("\n*** Testing insertAccount ***");

		String username     = "testusername";
		String password     = "testpassword";
		int endSize 		= db.findAllAccounts().size() + 1;
		
		System.out.println("*TEST: Inserting new account");
		assertTrue(db.insertAccount(username, password) != null);
		System.out.println("*TEST: Inserting new account with duplicate username");
		assertTrue(db.insertAccount(username, password) == null);
		assertTrue(endSize == db.findAllAccounts().size());
		
		db.removeAccount(db.findAccountByUsername(username).getId());
	}
	
	@Test
	public void testInsertPlayerIntoAccount() {
		System.out.println("\n*** Testing insertPlayerIntoAccount ***");
		String username     = "testusername";
		String password     = "testpassword";
		String name    		= "testname";
		int endSize 		= db.findAllPlayers().size() + 1;
		Integer account = db.insertAccount(username, password);
		
		System.out.println("*TEST: Inserting new player");
		assertTrue(db.insertPlayerIntoAccount(account, name) != null);
		System.out.println("*TEST: Inserting player into invalid account ID");
		assertTrue(db.insertPlayerIntoAccount(account + 1, name) == null);
		assertTrue(endSize == db.findAllPlayers().size());
		
		db.removeAccount(db.findAccountByUsername(username).getId());
	}
	
	@Test
	public void testInsertLocationAccess() {
		System.out.println("\n*** Testing insertLocationAccess ***");
		String username     = "testusername";
		String password     = "testpassword";
		String name    		= "testname";
		int accountID = db.insertAccount(username, password);
		int playerID = db.insertPlayerIntoAccount(accountID, name);
		
		System.out.println("*TEST: Inserting new locationAccess");
		assertTrue(db.insertLocationAccess(playerID, 0) != null); 
		System.out.println("*TEST: Inserting locationAccess with invalid player ID");
		assertTrue(db.insertLocationAccess(playerID + 1, 0) == null); 
		
		db.removeAccount(db.findAccountByUsername(username).getId());
	}
	
	@Test
	public void testInsertInventory() {
		System.out.println("\n*** Testing insertInventory ***");
		String username     = "testusername";
		String password     = "testpassword";
		String name    		= "testname";
		int accountID = db.insertAccount(username, password);
		int playerID = db.insertPlayerIntoAccount(accountID, name);
		
		System.out.println("*TEST: Inserting new inventory");
		assertTrue(db.insertInventory(playerID, -1, 1) != null);
		System.out.println("*TEST: Inserting inventory with invalid player ID");
		assertTrue(db.insertInventory(playerID + 1, -1, 1) == null);
		System.out.println("*TEST: Inserting inventory with duplicate item ID");
		db.insertInventory(playerID, -1, 1);
		assertTrue(db.removeInventory(playerID, -1).getRight() == 2);
		
		db.removeAccount(db.findAccountByUsername(username).getId());
	}
	
	@Test
	public void testModifyPlayer() {
		System.out.println("\n*** Testing modifyPlayer ***");
		//db.removePlayer(28);
		String username    		= "testusername";
		String password   		= "testpassword";
		String name    			= "testname";
		int accountID 			= db.insertAccount(username, password);
		int playerID 			= db.insertPlayerIntoAccount(accountID, name);
		Player playerStart 		= db.findPlayerByID(playerID);
		Player playerEnd;
		
		playerStart.setStrength(20);
		playerStart.setDexterity(31);
		playerStart.setIntellect(44);
		
		assertTrue(playerStart.getId() == playerID);
		assertTrue(playerStart.getName().equals(name));
		assertTrue(playerStart.getStrength() == 20);
		assertTrue(playerStart.getDexterity() == 31);
		assertTrue(playerStart.getIntellect() == 44);
		
		System.out.println("*TEST: Modifying player");
		playerEnd = db.modifyPlayer(playerStart);
		
		assertTrue(playerEnd != null);
		assertTrue(playerStart.getId() == playerEnd.getId());
		assertTrue(playerStart.getStrength() == playerEnd.getStrength());
		assertTrue(playerStart.getDexterity() == playerEnd.getDexterity());
		assertTrue(playerStart.getIntellect() == playerEnd.getIntellect());
		
		playerEnd.setId(playerID + 1);
		System.out.println("*TEST: Modifying player with invalid id");
		assertTrue(db.modifyPlayer(playerEnd) == null);
		
		db.removeAccount(db.findAccountByUsername(username).getId());
	}
	
	@Test
	public void testModifyInventory() {
		System.out.println("\n*** Testing modifyInventory ***");
//		db.removePlayer(66);
		String username    		= "testusername";
		String password   		= "testpassword";
		String name    			= "testname";
		int accountID 			= db.insertAccount(username, password);
		int playerID 			= db.insertPlayerIntoAccount(accountID, name);
		Player player			= db.findPlayerByID(playerID);
		
		Item item1 = new Item();
		item1.setName("TestItem1");
		item1.setId(-1);
		Item item2 = new Item();
		item2.setName("TestItem2");
		item2.setId(-2);
		Item item3 = new Item();
		item3.setName("TestItem3");
		item3.setId(-3);
		
		player.addItem(item1, 10);
		player.addItem(item2, 20);
		player.addItem(item3, 30);
		
		List<Pair<Item ,Integer>>  inventoryStart = player.getInventory();
		List<Pair<Integer ,Integer>>  inventoryEnd;

		
		assertTrue(player.getId() == playerID);
		
		System.out.println("*TEST: Modifying inventory");
		inventoryEnd = db.modifyInventory(player);
		
		assertTrue(inventoryEnd != null);
		assertTrue(inventoryStart.get(0).getLeft().getId() == inventoryEnd.get(0).getLeft());
		assertTrue(inventoryStart.get(0).getRight() == inventoryEnd.get(0).getRight());
		assertTrue(inventoryStart.get(1).getLeft().getId() == inventoryEnd.get(1).getLeft());
		assertTrue(inventoryStart.get(1).getRight() == inventoryEnd.get(1).getRight());
		assertTrue(inventoryStart.get(2).getLeft().getId() == inventoryEnd.get(2).getLeft());
		assertTrue(inventoryStart.get(2).getRight() == inventoryEnd.get(2).getRight());

		
		player.setId(playerID + 1);
		System.out.println("*TEST: Modifying inventory with invalid id");
		assertTrue(db.modifyPlayer(player) == null);
		
		db.removeAccount(db.findAccountByUsername(username).getId());
	}
	
	@Test
	public void testModifyAccess() {
		System.out.println("\n*** Testing modifyAccess ***");

		String username    		= "testusername";
		String password   		= "testpassword";
		String name    			= "testname";
		int accountID 			= db.insertAccount(username, password);
		int playerID 			= db.insertPlayerIntoAccount(accountID, name);
		Player player			= db.findPlayerByID(playerID);
		WorldMap world			= new WorldMap();

		world.grantAccess(-1);
		world.grantAccess(-2);
		world.grantAccess(-3);
		world.grantAccess(-4);
		
		List<Integer> accessStart = world.getAccess();
		
		
		assertTrue(player.getId() == playerID);
		
		System.out.println("*TEST: Modifying access");
		List<Integer> accessEnd = db.modifyAccess(world, playerID);
		
		assertTrue(accessStart.get(0) == accessEnd.get(0));
		assertTrue(accessStart.get(1) == accessEnd.get(1));
		assertTrue(accessStart.get(2) == accessEnd.get(2));
		assertTrue(accessStart.get(3) == accessEnd.get(3));
		
		System.out.println("*TEST: Modifying access with invalid id");
		assertTrue(db.modifyAccess(world, playerID + 1) == null);
		
		db.removeAccount(db.findAccountByUsername(username).getId());
	}
	
	@Test
	public void testValidateLogin() {
		System.out.println("\n*** Testing validateLogin ***");
		//db.removePlayer(28);
		String username    		= "testusername";
		String password   		= "testpassword";
		db.insertAccount(username, password);

		System.out.println("*TEST: Validating login with valid login");
		assertTrue(db.validateLogin(username, password));
		System.out.println("*TEST: Validating login with invalid login");
		assertTrue(!db.validateLogin(username + "2", password));
		assertTrue(!db.validateLogin(username, password + "2"));

		db.removeAccount(db.findAccountByUsername(username).getId());
	}
}
