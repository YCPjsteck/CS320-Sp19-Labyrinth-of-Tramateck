package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

import edu.ycp.cs320.assign01.model.utility.Triple;

public class Vendor extends NPC {
	private ArrayList<Triple<Item,Integer,Integer>> fullInventory;
	
	public Vendor() {
		super();
		fullInventory = new ArrayList<Triple<Item,Integer,Integer>>();
	}
	
	public void addInventory(Item item, int weight, int size) {
		fullInventory.add(new Triple<Item,Integer,Integer>(item, weight, size));
	}
	public void generateInventory() {
		// TODO: go through the fullInventory and generate a temporary
		// inventory, in a similar way to NPC's getLoot()
	}
	public ArrayList<Triple<Item,Integer,Integer>> getFullInventory() {
		return fullInventory;
	}
}
