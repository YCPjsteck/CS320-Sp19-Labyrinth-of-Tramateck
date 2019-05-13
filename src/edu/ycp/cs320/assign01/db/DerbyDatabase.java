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
import edu.ycp.cs320.assign01.model.Item;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.movement.WorldMap;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.Triple;

public class DerbyDatabase implements IDatabase {
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
		/***************JADEN CONNECTION BELOW**************/
		//Connection conn = DriverManager.getConnection("jdbc:derby:/Users/jadenmarini/CS320-Sp19-Labyrinth-of-Tramateck-DB/library.db;create=true");	
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
							"create table players (" +
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
						"	player_id integer," +
						"	item_id integer," +
						"	quantity integer" +
						")"
					);
					stmt4.executeUpdate();
					
					System.out.println("Inventory table created");					

					stmt5 = conn.prepareStatement(
							"create table locationAccess (" +
							"	player_id integer," +
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
						insertPlayers.setInt(10, player.getArmorID());
						insertPlayers.setInt(11, player.getWeaponID());
						insertPlayers.setInt(12, player.getAccessoryID());
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
					insertInventory.executeBatch();
					
					System.out.println("Inventory table populated");	
					
					insertAccess = conn.prepareStatement("insert into locationAccess (player_id, location_id) values (?,?)");
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

	@Override
	public List<Player> findAllPlayers() {
		return executeTransaction(new Transaction<List<Player>>() {
			@Override
			public List<Player> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select players.* " +
						"	from players"
					);
					
					List<Player> result = new ArrayList<Player>();
					
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						Player player = new Player();
						loadPlayer(player, resultSet, 1);
						result.add(player);
					}
					
					if(!found) {
						System.out.println("No players found");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Account> findAllAccounts() {
		return executeTransaction(new Transaction<List<Account>>() {
			@Override
			public List<Account> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select accounts.* " +
						"	from accounts"
					);
					
					List<Account> result = new ArrayList<Account>();
					
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						Account account = new Account();
						loadAccount(account, resultSet, 1);
						result.add(account);
					}
					
					if(!found) {
						System.out.println("No accounts found");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	@Override
	public Player findPlayerByID(int id) {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select players.* " +
						"	from players " +
						"	where player_id = ? "
					);
					stmt.setInt(1, id);
					
					Player result = null;
					
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						result = new Player();
						loadPlayer(result, resultSet, 1);
					}
					
					if(!found) {
						System.out.println("Player of ID <" + id + "> was not found");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Account findAccountByID(int id) {
		return executeTransaction(new Transaction<Account>() {
			@Override
			public Account execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select accounts.* " +
						"	from accounts " +
						"	where account_id = ? "
					);
					stmt.setInt(1, id);
					
					Account result = null;
					
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						result = new Account();
						loadAccount(result, resultSet, 1);
					}
					
					if(!found) {
						System.out.println("Account of ID <" + id + "> was not found");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public List<Player> findPlayersByAccountID(int id) {
		return executeTransaction(new Transaction<List<Player>>() {
			@Override
			public List<Player> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select players.* " +
						"	from players, playerAccounts" +
						"	where playerAccounts.account_id = ? " + 
						"		and players.player_id = playerAccounts.player_id "
					);
					stmt.setInt(1, id);
					
					List<Player> result = new ArrayList<Player>();
					
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						Player player = new Player();
						loadPlayer(player, resultSet, 1);
						result.add(player);
					}
					
					if(!found) {
						System.out.println("No players found of account ID <" + id + ">");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public Account findAccountByUsername(String name) {
		return executeTransaction(new Transaction<Account>() {
			@Override
			public Account execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select accounts.* " +
						"	from accounts" +
						"	where username = ? "
					);
					stmt.setString(1, name);
					
					Account result = null;
					
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						result = new Account();
						loadAccount(result, resultSet, 1);
					}
					
					if(!found) {
						System.out.println("No account of username <" + name + "> was found");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Pair<Integer, Integer>> findInventoryByPlayerID(int id) {
		return executeTransaction(new Transaction<List<Pair<Integer, Integer>>>() {
			@Override
			public List<Pair<Integer, Integer>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select inventory.* " +
						"	from inventory " +
						"	where player_id = ? "
					);
					stmt.setInt(1, id);
					
					List<Pair<Integer, Integer>> result = new ArrayList<Pair<Integer, Integer>>();
					
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						int index = 1;
						resultSet.getInt(index++); // Ignore the player ID
						int itemID = resultSet.getInt(index++);
						int quantity = resultSet.getInt(index++);
						Pair<Integer, Integer> pair = new Pair<Integer, Integer>(itemID, quantity);
						result.add(pair);
					}
					
					if(!found) {
						System.out.println("No inventory found with player id <" + id + ">");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Integer> findAccessByPlayerID(int id) {
		return executeTransaction(new Transaction<List<Integer>>() {
			@Override
			public List<Integer> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select locationAccess.* " +
						"	from locationAccess" +
						"	where player_id = ? "
					);
					stmt.setInt(1, id);
					
					List<Integer> result = new ArrayList<Integer>();
					
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						int index = 1;
						resultSet.getInt(index++); // Ignore the player ID
						result.add(resultSet.getInt(index++));
					}
					
					if(!found) {
						System.out.println("No access found with player id <" + id + ">");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Pair<Integer, Integer>> findAllPlayerAccounts() {
		return executeTransaction(new Transaction<List<Pair<Integer, Integer>>>() {
			@Override
			public List<Pair<Integer, Integer>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select playerAccounts.* " +
						"	from playerAccounts"
					);
					
					List<Pair<Integer, Integer>> result = new ArrayList<Pair<Integer, Integer>>(); 
					
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						int index = 1;
						result.add(new Pair<Integer, Integer>(resultSet.getInt(index++), resultSet.getInt(index++)));
					}
					
					if(!found) {
						System.out.println("No playerAccounts found found");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Account removeAccount(int id) {
		return executeTransaction(new Transaction<Account>() {
			@Override
			public Account execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;	
				PreparedStatement stmt2 = null;	
				
				ResultSet resultSet1 = null;
				
				try {
					Account account = findAccountByID(id);
					List <Player> players = findPlayersByAccountID(id);
					
					for (Player player : players) {
						System.out.println("removeAccount: Removing player ID: " + player.getId());
						removePlayer(player.getId());
					}									
					
					stmt1 = conn.prepareStatement(
							"select playerAccounts.* from playerAccounts " +
							"  where account_id = ? "
					);
					
					stmt1.setInt(1, id);
					resultSet1 = stmt1.executeQuery();
					
					if (resultSet1.next()) {
						stmt2 = conn.prepareStatement(
								"delete from playerAccounts " +
								"  where account_id = ? "
						);
						
						stmt2.setInt(1, id);
						stmt2.executeUpdate();
					}
					
					stmt2 = conn.prepareStatement(
							"delete from accounts " +
							"  where account_id = ? "
					);
					
					stmt2.setInt(1, id);
					stmt2.executeUpdate();
					
					System.out.println("removeAccount: Deleted account with accountID <" + id + "> from DB");									

					return account;
				} finally {
					DBUtil.closeQuietly(stmt1);				
				}
			}
		});
	}

	@Override
	public Player removePlayer(int id) {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				
				
				try {
					Player player = findPlayerByID(id);
					
					if (player == null) {
						System.out.println("removePlayer: Player ID: " + id + " could not be removed from DB because it could not be found");
						return null;
					}

					stmt1 = conn.prepareStatement(
							"delete from playerAccounts " +
							"	where playerAccounts.player_id = ? "
					);
					
					stmt1.setInt(1, player.getId());
					stmt1.executeUpdate();
					
					System.out.println("removePlayer: Deleted junction table entries for player <" + id + "> from DB");									
					
					stmt2 = conn.prepareStatement(
							"delete from players " +
							"  where player_id = ? "
					);
					
					stmt2.setInt(1, id);
					stmt2.executeUpdate();
					
					System.out.println("removePlayer: Deleted player with playerID <" + id + "> from DB");									
					
					stmt3 = conn.prepareStatement(
							"delete from inventory " +
							"  where player_id = ? "
					);
					
					stmt3.setInt(1, id);
					stmt3.executeUpdate();
					
					System.out.println("removePlayer: Deleted inventory with playerID <" + id + "> from DB");		
					
					stmt4 = conn.prepareStatement(
							"delete from locationAccess " +
							"  where player_id = ? "
					);
					
					stmt4.setInt(1, id);
					stmt4.executeUpdate();
					
					System.out.println("removePlayer: Deleted locationAccess with playerID <" + id + "> from DB");	
					
					return player;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);					
					DBUtil.closeQuietly(stmt4);					
				}
			}
		});
	}

	@Override
	public Triple<Integer, Integer, Integer> removeInventory(int playerID, int itemID) {
		return executeTransaction(new Transaction<Triple<Integer, Integer, Integer>>() {
			@Override
			public Triple<Integer, Integer, Integer> execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				
				ResultSet resultSet1 = null;
				
				try {
					
					stmt1 = conn.prepareStatement(
							"select * from inventory " +
							"  where player_id = ? and item_id = ? "
					);								
					
					stmt1.setInt(1, playerID);
					stmt1.setInt(2, itemID);
					resultSet1 = stmt1.executeQuery();
					
					if (resultSet1.next()) {
						Triple<Integer, Integer, Integer> inventory	= loadInventory(resultSet1, 1);
						stmt2 = conn.prepareStatement(
								"delete from inventory " +
								"  where player_id = ? and item_id = ? "
						);
						
						stmt2.setInt(1, playerID);
						stmt2.setInt(2, itemID);
						stmt2.executeUpdate();
						System.out.println("removeInventory: Deleted inventory with player ID: " + playerID + " and item ID: " + itemID + " from DB");									

						return inventory;
					} else {
						System.out.println("removeInventory: Inventory with player ID: " + playerID + " and item ID: " + itemID + " not found");	
						return null;
					}
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	
	@Override
	public Triple<Integer, Integer, Integer> removeInventoryByItemAmount(int playerID, int itemID, int itemAmount) {
		return executeTransaction(new Transaction<Triple<Integer, Integer, Integer>>() {
			@Override
			public Triple<Integer, Integer, Integer> execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;		
				
				ResultSet resultSet1 = null;
				
				try {
					stmt1 = conn.prepareStatement(
							"select inventory.* from inventory " +
							"  where player_id = ? and item_id = ? "
					);								
					
					stmt1.setInt(1, playerID);
					stmt1.setInt(2, itemID);
					resultSet1 = stmt1.executeQuery();
					
					if (resultSet1.next()) {
						if (resultSet1.getInt(3) < itemAmount) {
							System.out.println("" + resultSet1.getInt(3));
							System.out.println("removeInventoryByItemAmount: Inventory found contains insufficient item amount to remove from DB");	
							return null;
						}
						else if (resultSet1.getInt(3) == itemAmount) {
							System.out.println("whole");
							return removeInventory(playerID, itemID);
						} else {
							removeInventory(playerID, itemID);
							System.out.println("remove");
							insertInventory(playerID, itemID, resultSet1.getInt(3) - itemAmount);
							System.out.println("insert");
							return new Triple<Integer, Integer, Integer>(playerID, itemID, itemAmount);
						}
					} else {
						System.out.println("removeInventoryByItemAmount: Inventory with player ID: " + playerID + " and item ID: " + itemID + " not found");	
						return null;
					}
					
				} finally {
					DBUtil.closeQuietly(resultSet1);	
					DBUtil.closeQuietly(stmt1);				
				}
			}
		});
	}
	
	@Override
	public Integer insertAccount(String username, String password) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;

				try {
					stmt1 = conn.prepareStatement(
						"select accounts.* " +
						"	from accounts" +
						"	where username = ? "
					);
					stmt1.setString(1, username);
					resultSet1 = stmt1.executeQuery();
					
					if (resultSet1.next()) {
						System.out.println("insertAccount: Account with username <" + username + "> already exists in players table");
						return null;
					} else {
						stmt2 = conn.prepareStatement(
								"insert into accounts (username, password)" +
								"  values(?, ?) "
						);
						stmt2.setString(1, username);
						stmt2.setString(2, password);
						stmt2.executeUpdate();
						
						System.out.println("insertAccount: New Account <" + username + "> inserted into players table");
						stmt3 = conn.prepareStatement(
							"select accounts.* " +
							"	from accounts" +
							"	where username = ? "
						);
						stmt3.setString(1, username);
						resultSet3 = stmt3.executeQuery();
						if (resultSet3.next()) {
							return resultSet3.getInt(1);
						} else {
							System.out.println("insertAccount: If you're seeing this please let me know");
							return null; //REALLY SHOULDNT HAPPEN
						}
					}
					
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}
	
	@Override
	public Integer insertPlayerIntoAccount(int accountID, String name) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
							
				ResultSet resultSet2 = null;
				Account account;
			
				// for saving playerID
				Integer playerID = -1;

				try {
					account = findAccountByID(accountID);
					if (account != null) {
						System.out.println("insertPlayerIntoAccount: Account found with accountID: " + accountID);						
					} else {
						System.out.println("insertPlayerIntoAccount: Account with accountID: " + accountID + " not found");
						return null;
					}
					
					List<Player> players = findPlayersByAccountID(accountID);
					for (Player player : players) {
						if (name.equalsIgnoreCase(player.getName())) {
							System.out.println("insertPlayerIntoAccount: Player <" + name + "> already exists in Account with accountID: " + accountID);
							return null;
						}
					}
					
					stmt1 = conn.prepareStatement(
							"insert into players (location_id, level, experience,"	+
							 					"score, currency, dexterity, strength, "+
							 					"intellect, armor, weapon, accessory) " +
							"  values(0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0) "
					);
					stmt1.executeUpdate();
					
					System.out.println("insertPlayerIntoAccount: New player <" + name + "> inserted into players table");					

					stmt2 = conn.prepareStatement(
							"select players.player_id from players " +
							"	where name is null "
					);
					resultSet2 = stmt2.executeQuery();
					
					if (resultSet2.next()) {
						playerID = resultSet2.getInt(1);
					} else {
						System.out.println("insertPlayerIntoAccount: Error inserting new player into players table");
						return null;
					}
					
					stmt3 = conn.prepareStatement(
							"insert into playerAccounts (player_id, account_id) " +
							"  values(?, ?) "
					);
					stmt3.setInt(1, playerID);
					stmt3.setInt(2, accountID);
					stmt3.executeUpdate();
					System.out.println("insertPlayerIntoAccount: New entry for player ID <" + playerID + "> and account ID <" + accountID + "> inserted into PlayerAccounts junction table");
					
					stmt4 = conn.prepareStatement(
							"update players " +
							"	set players.name = ?" +
							"	where players.name is null "
									
					);
					stmt4.setString(1, name);
					stmt4.executeUpdate();
					
					System.out.println("insertPlayerIntoAccount: New player <" + name + "> inserted into Player table");
					
					return playerID;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
				}
			}
		});
	}
	
	@Override
	public Integer insertLocationAccess(int playerID, int locationID) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				
				ResultSet resultSet2 = null;
				
				try {
				
					if (findPlayerByID(playerID) != null) {
						System.out.println("insertLocationAccess: Player found with playerID: " + playerID);						
					} else {
						System.out.println("insertLocationAccess: Player with playerID: " + playerID + " not found");
						return null;
					}
					
					stmt1 = conn.prepareStatement(
							"insert into locationAccess (player_id, location_id)" +
							"  values(?, ?) "
					);
					stmt1.setInt(1, playerID);
					stmt1.setInt(2, locationID);
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"select * from locationAccess " +
							"  where player_id = ? and location_id = ? "
					);
					stmt2.setInt(1, playerID);
					stmt2.setInt(2, locationID);
					resultSet2 = stmt2.executeQuery();
					
					if (resultSet2.next()) {
						System.out.println("insertLocationAccess: New LocationAccess location ID: " + locationID + "for player ID: " + playerID + " inserted into players table");
						return resultSet2.getInt(1);
					} else {
						System.out.println("insertLocationAccess: LocationAccess location ID: " + locationID + "for player ID: " + playerID + " was not inserted into players table");
						return null;
					}

				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	
	@Override
	public Integer insertInventory(int playerID, int itemID, int itemAmount) { //NEEDS FINISHING
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
							
				ResultSet resultSet1 = null;
				Integer tempQuantity = 0;
			
				// for saving playerID

				try {
				
					if (findPlayerByID(playerID) != null) {
						System.out.println("insertInventory: Player found with playerID: " + playerID);						
					} else {
						System.out.println("insertInventory: Player with playerID: " + playerID + " not found");
						return null;
					}
					System.out.println("TestPoint");
					
					stmt1 = conn.prepareStatement(
							"select inventory.* from inventory " +
							"  where player_id = ? and item_id = ? "
									
					);
					stmt1.setInt(1, playerID);
					stmt1.setInt(2, itemID);
					resultSet1 = stmt1.executeQuery();
					
					if (resultSet1.next()) {
						tempQuantity = removeInventory(playerID, itemID).getRight();		
					}
					
					
					stmt2 = conn.prepareStatement(
							"insert into inventory (player_id, item_id, quantity)" +
							"  values(?, ?, ?) "
					);
					stmt2.setInt(1, playerID);
					stmt2.setInt(2, itemID);
					stmt2.setInt(3, itemAmount + tempQuantity);
					
					stmt2.executeUpdate();
					
					System.out.println("insertInventory: New Inventory item ID:" + itemID + " quantity:" + itemAmount + " for player ID:" + playerID + " inserted into Inventory table");					
					return playerID;
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	
	@Override
	public Player modifyPlayer(Player player) {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
							
				ResultSet resultSet2 = null;
			
				Player tempPlayer = new Player();
				String name;
				int playerID;

				try {
					name = player.getName();
					playerID = player.getId();
					
					if (findPlayerByID(playerID) != null) {
						System.out.println("modifyPlayer: Player found with player ID: " + playerID);						
					} else {
						System.out.println("modifyPlayer: Player with player ID: " + playerID + " not found");
						return null;
					}
					
					stmt1 = conn.prepareStatement(
							"update players" + 
							"	set name = ?, location_id = ?, level = ?, experience = ?," +
							"		score = ?, currency = ?, dexterity = ?, strength = ?," +
							"		intellect = ?, armor = ?, weapon = ?, accessory = ? " +
							"	where player_id = ? "
					);
					stmt1.setString(1, name);
					stmt1.setInt(2, player.getLocationID());
					stmt1.setInt(3, player.getLevel());
					stmt1.setInt(4, player.getExperience());
					stmt1.setInt(5, player.getScore());
					stmt1.setInt(6, player.getCurrency());
					stmt1.setInt(7, player.getDexterity());
					stmt1.setInt(8, player.getStrength());
					stmt1.setInt(9, player.getIntellect());
					stmt1.setInt(10, player.getArmorID());
					stmt1.setInt(11, player.getWeaponID());
					stmt1.setInt(12, player.getAccessoryID());
					stmt1.setInt(13, playerID);
					stmt1.executeUpdate();
					System.out.println("modifyPlayer: Player <" + name + "> updated in players table");	
					
					stmt2 = conn.prepareStatement(
						"select players.* " +
						"	from players " +
						"	where player_id = ? "
					);
					stmt2.setInt(1, playerID);
					resultSet2 = stmt2.executeQuery();
					
					if (resultSet2.next()) {
						System.out.println("modifyPlayer: New player <" + name + "> ID: " + playerID);
						loadPlayer(tempPlayer, resultSet2, 1);
						return tempPlayer;
					} else	{
						System.out.println("modifyPlayer: New player <" + name + "> not found in Player table ID: " + playerID);
						return null;
					}
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	
	@Override
	public List<Pair<Integer,Integer>> modifyInventory(Player player) {
		return executeTransaction(new Transaction<List<Pair<Integer,Integer>>>() {
			@Override
			public List<Pair<Integer,Integer>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				String preparedString = "insert into inventory (player_id, item_id, quantity) values ";
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				
				ResultSet resultSet3 = null;
				int playerID;
				int index = 1;

				try {
					playerID = player.getId();
					
					if (findPlayerByID(playerID) != null) {
						System.out.println("modifyInventory: Player found with player ID: " + playerID);						
					} else {
						System.out.println("modifyInventory: Player with player ID: " + playerID + " not found");
						return null;
					}
					
					stmt1 = conn.prepareStatement(
						"delete from inventory " +
						"	where player_id = ? "
					);
					stmt1.setInt(1, playerID);
					stmt1.executeUpdate();
					
					ArrayList<Pair<Item, Integer>> inventory = player.getInventory();
					
					for (Pair<Item, Integer> item : inventory) {
						preparedString += "  (?, ?, ?),";
					}
					
					if (inventory != null) {
						preparedString = preparedString.substring(0, preparedString.length() - 1);
						System.out.println(preparedString);
						stmt2 = conn.prepareStatement( preparedString );
						for (Pair<Item, Integer> item : inventory) {
							stmt2.setInt(index++, playerID);
							stmt2.setInt(index++, item.getLeft().getId());
							stmt2.setInt(index++, item.getRight());
						}
						stmt2.executeUpdate();
					}
					
					
					stmt3 = conn.prepareStatement(
						"select inventory.* " +
						"	from inventory " +
						"	where player_id = ? "
					);
					stmt3.setInt(1, playerID);
					
					List<Pair<Integer, Integer>> result = new ArrayList<Pair<Integer, Integer>>();
					
					resultSet3 = stmt3.executeQuery();

					while(resultSet3.next()) {
						index = 1;
						resultSet3.getInt(index++); // Ignore the player ID
						int itemID = resultSet3.getInt(index++);
						int quantity = resultSet3.getInt(index++);
						Pair<Integer, Integer> pair = new Pair<Integer, Integer>(itemID, quantity);
						result.add(pair);
					}
					
					return result;
					
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	
	@Override
	public List<Integer> modifyAccess(WorldMap world, int playerID) {
		return executeTransaction(new Transaction<List<Integer>>() {
			@Override
			public List<Integer> execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
							
				ResultSet resultSet3 = null;
				
				int index;

				try {
					
					if (findPlayerByID(playerID) != null) {
						System.out.println("modifyAccess: Player found with player ID: " + playerID);						
					} else {
						System.out.println("modifyAccess: Player with player ID: " + playerID + " not found");
						return null;
					}
					
					stmt1 = conn.prepareStatement(
						"delete from locationAccess " +
						"	where player_id = ? "
					);
					stmt1.setInt(1, playerID);
					stmt1.executeUpdate();

					ArrayList<Integer> accesses = world.getAccess();
					
					for (Integer access : accesses) {
						stmt2 = conn.prepareStatement(
								"insert into locationAccess (player_id, location_id)" +
								"  values(?, ?) "
						);
						stmt2.setInt(1, playerID);
						stmt2.setInt(2, access);
						stmt2.executeUpdate();
					}
					
					List<Integer> result = new ArrayList<Integer>();
					
					stmt3 = conn.prepareStatement(
						"select locationAccess.* " +
						"	from locationAccess" +
						"	where player_id = ? "
					);
					stmt3.setInt(1, playerID);
					resultSet3 = stmt3.executeQuery();
					
					while(resultSet3.next()) {
						index = 1;
						resultSet3.getInt(index++); // Ignore the player ID
						result.add(resultSet3.getInt(index++));
					}
					
					return result;
					
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	
	@Override
	public boolean validateLogin (String username, String password) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"select accounts.* " +
						"	from accounts" +
						"	where username = ? and password = ? "
					);
					stmt.setString(1, username);
					stmt.setString(2, password);
					resultSet = stmt.executeQuery();
					
					while(resultSet.next()) {
						return true;
					}
					
					return false;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	private void loadPlayer(Player player, ResultSet resultSet, int index) throws SQLException {
		player.setId(resultSet.getInt(index++));
		player.setName(resultSet.getString(index++));
		player.setLocationID(resultSet.getInt(index++));
		player.setLevel(resultSet.getInt(index++));
		player.setExperience(resultSet.getInt(index++));
		player.setScore(resultSet.getInt(index++));
		player.setCurrency(resultSet.getInt(index++));
		player.setDexterity(resultSet.getInt(index++));
		player.setStrength(resultSet.getInt(index++));
		player.setIntellect(resultSet.getInt(index++));
		player.setArmorID(resultSet.getInt(index++));
		player.setWeaponID(resultSet.getInt(index++));
		player.setAccessoryID(resultSet.getInt(index++));
	}
	
	private void loadAccount(Account account, ResultSet resultSet, int index) throws SQLException {
		account.setId(resultSet.getInt(index++));
		account.setUsername(resultSet.getString(index++));
		account.setPassword(resultSet.getString(index++));
	}
	
	private Triple<Integer, Integer, Integer> loadInventory(ResultSet resultSet, int index) throws SQLException {
		return new Triple<Integer, Integer, Integer>(resultSet.getInt(index++), resultSet.getInt(index++), resultSet.getInt(index++));
	}
}
