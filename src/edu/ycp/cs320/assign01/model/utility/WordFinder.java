package edu.ycp.cs320.assign01.model.utility;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class WordFinder {
	
	/**
	 * Tokenize the given string by its spaces
	 * @param input a string
	 * @return an arraylist of strings where each element is a word from the input string, in order
	 */
	public ArrayList<String> findWords(String input) {
		return findWords(input, " ");
	}
	
	/**
	 * Tokenize the given string by the given delimiter
	 * @param input a string
	 * @param delim a delimiter for the string
	 * @return an arraylist of strings where each element is a word from the input string, in order
	 */
	public ArrayList<String> findWords(String input, String delim) {
		ArrayList<String> words = new ArrayList<String>();
		input = input.trim().toLowerCase();
		
		// Tokenize the input string by the given delimiter
		StringTokenizer token = new StringTokenizer(input, delim);
		while(token.hasMoreTokens())
			words.add(token.nextToken());
		
		return words; // Return the list of words
	}
}
