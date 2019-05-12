package edu.ycp.cs320.assign01.db;

import java.util.List;

import edu.ycp.cs320.assign01.model.Account;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.utility.Pair;

public interface IDatabase {
	/*
	public List<Pair<Author, Book>> findAuthorAndBookByTitle(String title);
	public List<Pair<Author, Book>> findAuthorAndBookByAuthorLastName(String lastName);
	public Integer insertBookIntoBooksTable(String title, String isbn, int published, String lastName, String firstName);
	public List<Pair<Author, Book>> findAllBooksWithAuthors();
	public List<Author> findAllAuthors();
	public List<Author> removeBookByTitle(String title);
	*/
	
	public List<Player> findAllPlayers();
	public List<Account> findAllAccounts();
	public Player findPlayerByID(int id);
	public List<Player> findPlayersByAccountID(int id);
	public Account findAccountByUsername(String name);
	public List<Pair<Integer,Integer>> findInventoryByPlayerID(int id);
	public List<Integer> findAccessByPlayerID(int id);
	public void clearAccount(int id);
	public void clearPlayer(int id);
	public void addPlayerAccessByPlayerID(int playerID, int locationID);
	public void insertPlayerIntoAccount(int accountID);
	// insert player inventory
	// remove player inventory
	// modify player inventory
	// modify player [pass a player and all stats override]
}
