package edu.ycp.cs320.assign01.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.assign01.model.Account;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.Triple;

public class DerbyDatabase /*implements IDatabase*/ {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
	// wrapper SQL transaction function that calls actual transaction function (which has retries)
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	// SQL transaction function which retries the transaction MAX_ATTEMPTS times before failing
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:C:/CS320-Sp19-Labyrinth-of-Tramateck-DB/library.db;create=true");		
		
		// Set autocommit() to false to allow the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	//  creates the Authors and Books tables
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
			
				try {
					stmt1 = conn.prepareStatement(
						"create table accounts (" +
						"	account_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +
						"	username varchar(40)," +
						"	password varchar(40)" +
						")"
					);
					stmt1.executeUpdate();
					
					System.out.println("Accounts table created");
					
					stmt2 = conn.prepareStatement(
							"create table player (" +
							"	player_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	name varchar(40)," +
							"	location_id integer," +
							"	level integer," +
							"	experience integer," +
							"	score integer," +
							"	currency integer," +
							"	dexterity integer," +
							"	strength integer," +
							"	intellect integer," +
							"	armor integer," +
							"	weapon integer," +
							"	accessory integer" +
							")"
					);
					stmt2.executeUpdate();
					
					System.out.println("Players table created");					

					stmt3 = conn.prepareStatement(
						"create table playerAccounts (" +
						"	player_id integer constraint player_id references players," +
						"	account_id integer constraint account_id references accounts" +
						")"
					);	
					stmt3.executeUpdate();
					
					System.out.println("PlayerAccounts table created");
						
					stmt4 = conn.prepareStatement(
							"create table inventory (" +
							"	player_id integer constraint player_id references players, " +
							"	item_id integer," +
							"	quantity integer" +
							")"
					);
					stmt4.executeUpdate();
					
					System.out.println("Inventory table created");					

					stmt5 = conn.prepareStatement(
							"create table locationAccess (" +
							"	player_id integer constraint player_id references players, " +
							"	location_id integer" +
							")"
					);
					stmt5.executeUpdate();
					
					System.out.println("LocationAccess table created");
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
				}
			}
		});
	}
	
	// loads data retrieved from CSV files into DB tables in batch mode
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Account> accountList;
				List<Player> playerList;
				List<Triple<Integer,Integer,Integer>> inventoryList;
				List<Pair<Integer,Integer>> accessList;
				List<Pair<Integer,Integer>> playerAccountsList;
				
				try {
					accountList   	    = InitialData.getAccounts();
					playerList   	    = InitialData.getPlayers();
					playerAccountsList  = InitialData.getPlayerAccounts();
					inventoryList 		= InitialData.getInventory();
					accessList 			= InitialData.getAccess();					
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertAccounts     	= null;
				PreparedStatement insertPlayers      	= null;
				PreparedStatement insertPlayerAccounts  = null;
				PreparedStatement insertInventory	 	= null;
				PreparedStatement insertAccess			= null;

				try {
					// must completely populate Accounts table before populating PlayerAccounts table because of primary keys
					insertAccounts = conn.prepareStatement("insert into accounts (username, password) values (?, ?)");
					for (Account account : accountList) {
						insertAccounts.setString(1, account.getUsername());
						insertAccounts.setString(2, account.getPassword());
						insertAccounts.addBatch();
					}
					insertAccounts.executeBatch();
					
					System.out.println("Accounts table populated");
					
					// must completely populate Players table before populating PlayerAccounts table because of primary keys
					insertPlayers = conn.prepareStatement("insert into players (name, location_id, level, experience, "
																			+ "score, currency, dexterity, strength, "
																			+ "intellect, armor, weapon, accessory) "
																			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for (Player player : playerList) {
						insertPlayers.setString(1, player.getName());
						insertPlayers.setInt(2, player.getLocationID());
						insertPlayers.setInt(3, player.getLevel());
						insertPlayers.setInt(4, player.getExperience());
						insertPlayers.setInt(5, player.getScore());
						insertPlayers.setInt(6, player.getCurrency());
						insertPlayers.setInt(7, player.getDexterity());
						insertPlayers.setInt(8, player.getStrength());
						insertPlayers.setInt(9, player.getIntellect());
						insertPlayers.setInt(10, player.getArmor().getId());
						insertPlayers.setInt(11, player.getWeapon().getId());
						insertPlayers.setInt(12, player.getAccessory().getId());
						insertPlayers.addBatch();
					}
					insertPlayers.executeBatch();
					
					System.out.println("Players table populated");					
					
					// must wait until all Players and all Accounts are inserted into tables before creating PlayerAccounts table
					// since this table consists entirely of foreign keys, with constraints applied
					insertPlayerAccounts = conn.prepareStatement("insert into playerAccounts (player_id, account_id) values (?, ?)");
					for (Pair<Integer,Integer> playerAccount : playerAccountsList) {
						insertPlayerAccounts.setInt(1, playerAccount.getLeft());
						insertPlayerAccounts.setInt(2, playerAccount.getRight());
						insertPlayerAccounts.addBatch();
					}
					insertPlayerAccounts.executeBatch();	
					
					System.out.println("PlayerAccounts table populated");					
					
					insertInventory = conn.prepareStatement("insert into inventory (player_id, item_id, quantity) values (?, ?, ?)");
					for(Triple<Integer,Integer,Integer> inventory : inventoryList) {
						insertInventory.setInt(1, inventory.getLeft());
						insertInventory.setInt(2, inventory.getMiddle());
						insertInventory.setInt(3, inventory.getRight());
						insertInventory.addBatch();
					}
					insertPlayerAccounts.executeBatch();
					
					System.out.println("Inventory table populated");	
					
					insertAccess = conn.prepareStatement("insert into locationAccess (player_id, location_id");
					for(Pair<Integer,Integer> access : accessList) {
						insertAccess.setInt(1, access.getLeft());
						insertAccess.setInt(2, access.getRight());
						insertAccess.addBatch();
					}
					insertAccess.executeBatch();
					
					System.out.println("LocationAccess table populated");	
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertAccounts);
					DBUtil.closeQuietly(insertPlayers);
					DBUtil.closeQuietly(insertPlayerAccounts);
					DBUtil.closeQuietly(insertInventory);	
					DBUtil.closeQuietly(insertAccess);						
				}
			}
		});
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Library DB successfully initialized!");
	}
}
