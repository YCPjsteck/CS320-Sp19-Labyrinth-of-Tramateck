package edu.ycp.cs320.assign01.model;

import java.util.ArrayList;

public class WordFinder {
	
	public ArrayList<String> findWords(String input) {
		ArrayList<String> words = new ArrayList<String>();
		input = input.trim().toLowerCase();
		while(!input.equals("")) {
			int space = input.indexOf(" ");
			if(space == -1) {
				words.add(input);
				input = "";
			} else {
				words.add(input.substring(0, space));
				input = input.substring(space).trim();
			}
		}
		return words;
	}

}
