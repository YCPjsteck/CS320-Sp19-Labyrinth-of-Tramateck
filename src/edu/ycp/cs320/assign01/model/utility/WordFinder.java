package edu.ycp.cs320.assign01.model.utility;

import java.util.ArrayList;

public class WordFinder {
	
	/**
	 * @param input a string
	 * @return an arraylist of strings where each element is a word from the input string, in order
	 */
	public ArrayList<String> findWords(String input) {
		ArrayList<String> words = new ArrayList<String>();
		input = input.trim().toLowerCase();
		// Keep reading the string until it's empty
		while(!input.equals("")) {
			int space = input.indexOf(" "); // Find the index of the next space
			if(space == -1) { // If no space is found
				words.add(input); // Add the current input to the list of words
				input = ""; // Empty the input
			} else { // Otherwise, there is a space somewhere
				words.add(input.substring(0, space)); // Add a substring containing the first word of the string to the list of words
				input = input.substring(space).trim(); // Replace the input with a substring of everything past the first word
			}
		}
		return words; // Return the list of words
	}
}
