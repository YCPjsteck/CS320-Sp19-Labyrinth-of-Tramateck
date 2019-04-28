package edu.ycp.cs320.assign01.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.assign01.model.Account;
import edu.ycp.cs320.assign01.model.Player;
import edu.ycp.cs320.assign01.model.utility.Pair;
import edu.ycp.cs320.assign01.model.utility.Triple;

public class InitialData {

	public static List<Account> getAccounts() throws IOException {
		List<Account> accountsList = new ArrayList<Account>();
		ReadCSV readAccounts = new ReadCSV("accounts.csv");
		try {
			Integer accountID = 1;
			while(true) {
				List<String> tuple = readAccounts.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Account account = new Account();
				Integer.parseInt(i.next());
				account.setId(accountID);
				accountID++;
				account.setUsername(i.next());
				account.setPassword(i.next());
				accountsList.add(account);
			}
			System.out.println("accountslist loaded from CSV file");
			return accountsList;
		} finally {
			readAccounts.close();
		}
	}

	public static List<Player> getPlayers() throws IOException {
		List<Player> playersList = new ArrayList<Player>();
		ReadCSV readPlayers = new ReadCSV("players.csv");
		try {
			Integer playerID = 1;
			while(true) {
				List<String> tuple = readPlayers.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Player player = new Player();
				Integer.parseInt(i.next());
				player.setId(playerID);
				playerID++;
				player.setName(i.next());
				player.setLocationID(Integer.parseInt(i.next()));
				player.setLevel(Integer.parseInt(i.next()));
				player.setExperience(Integer.parseInt(i.next()));
				player.setScore(Integer.parseInt(i.next()));
				player.setCurrency(Integer.parseInt(i.next()));
				player.setDexterity(Integer.parseInt(i.next()));
				player.setStrength(Integer.parseInt(i.next()));
				player.setIntellect(Integer.parseInt(i.next()));
				// TODO: Have this query a list of items to get the actual items
				player.setArmorID(Integer.parseInt(i.next()));
				player.setWeaponID(Integer.parseInt(i.next()));
				player.setAccessoryID(Integer.parseInt(i.next()));
				playersList.add(player);
			}
			System.out.println("playersList loaded from CSV file");
			return playersList;
		} finally {
			readPlayers.close();
		}
	}

	public static List<Pair<Integer, Integer>> getPlayerAccounts() throws IOException {
		List<Pair<Integer,Integer>> playerAccountsList = new ArrayList<Pair<Integer,Integer>>();
		ReadCSV readPlayerAccounts = new ReadCSV("player_accounts.csv");
		try {
			while(true) {
				List<String> tuple = readPlayerAccounts.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Pair<Integer,Integer> pair = new Pair<Integer,Integer>(Integer.parseInt(i.next()),Integer.parseInt(i.next()));
				playerAccountsList.add(pair);
			}
			System.out.println("playerAccountsList loaded from CSV file");
			return playerAccountsList;
		} finally {
			readPlayerAccounts.close();
		}
	}

	public static List<Triple<Integer, Integer, Integer>> getInventory() throws IOException {
		List<Triple<Integer,Integer,Integer>> inventoryList = new ArrayList<Triple<Integer,Integer,Integer>>();
		ReadCSV readInventory = new ReadCSV("inventory.csv");
		try {
			while(true) {
				List<String> tuple = readInventory.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Triple<Integer,Integer,Integer> triple = new Triple<Integer,Integer,Integer>(Integer.parseInt(i.next()),Integer.parseInt(i.next()),Integer.parseInt(i.next()));
				inventoryList.add(triple);
			}
			System.out.println("inventoryList loaded from CSV file");
			return inventoryList;
		} finally {
			readInventory.close();
		}
	}

	public static List<Pair<Integer, Integer>> getAccess() throws IOException {
		List<Pair<Integer,Integer>> accessList = new ArrayList<Pair<Integer,Integer>>();
		ReadCSV readLocationAccess = new ReadCSV("location_access.csv");
		try {
			while(true) {
				List<String> tuple = readLocationAccess.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Pair<Integer,Integer> pair = new Pair<Integer,Integer>(Integer.parseInt(i.next()),Integer.parseInt(i.next()));
				accessList.add(pair);
			}
			System.out.println("accessList loaded from CSV file");
			return accessList;
		} finally {
			readLocationAccess.close();
		}
	}
}