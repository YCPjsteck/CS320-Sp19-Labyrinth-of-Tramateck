package edu.ycp.cs320.assign01.db;

import java.util.List;

import edu.ycp.cs320.assign01.model.Account;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.Triple;

public interface IDatabase {
	
	public List<Player> 	findAllPlayers();
	public List<Account> 	findAllAccounts();
	public Player			findPlayerByID(int id);
	public Account 			findAccountByID(int id);
	public List<Player>		findPlayersByAccountID(int id);
	public Account 			findAccountByUsername(String name);
	public List<Pair<Integer,Integer>> 	findInventoryByPlayerID(int id);
	public List<Integer>	findAccessByPlayerID(int id);
	
	public Account 			removeAccount(int id); //These all return exactly what was removed
	public Player 			removePlayer(int id);
	public Triple<Integer,Integer,Integer>	removeInventory(int playerID, int itemID);
	public Triple<Integer,Integer,Integer>	removeInventoryByItemAmount(int playerID, int itemID, int itemAmount);
	
	public Integer 			insertAccount(String username, String password);		//These return first col Integers for validation purposes
	public Integer 			insertPlayerIntoAccount(int accountID, String name);
	public Integer 			insertLocationAccess(int playerID, int locationID);
	public Integer			insertInventory(int playerID, int itemID, int itemAmount);

	public Player			modifyPlayer(int playerID, Player player); //Returns the new player
}
