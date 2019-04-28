package edu.ycp.cs320.assign01.db;

import java.util.List;

import edu.ycp.cs320.assign01.db.Author;
import edu.ycp.cs320.assign01.db.Book;
import edu.ycp.cs320.assign01.model.utility.Pair;

public interface IDatabase {
	public List<Pair<Author, Book>> findAuthorAndBookByTitle(String title);
	public List<Pair<Author, Book>> findAuthorAndBookByAuthorLastName(String lastName);
	public Integer insertBookIntoBooksTable(String title, String isbn, int published, String lastName, String firstName);
	public List<Pair<Author, Book>> findAllBooksWithAuthors();
	public List<Author> findAllAuthors();
	public List<Author> removeBookByTitle(String title);
	
	/*
	 * public Player findPlayerByID(int id);
	 * public List<Player> findPlayerByAccountID(int id);
	 * public int findAccountIDByUsername(String name);
	 * public List<Pair<Item,Integer>> findInventoryByPlayerID(int id);
	 * public List<Integer> findAccessByPlayerID(int id);
	 * public void clearAccount(int id);
	 * public void clearPlayer(int id);
	 */
	// insert player access
	// insert new player
	// insert player inventory
	// remove player inventory
	// modify player inventory
	// modify player <insert stat here>
}
