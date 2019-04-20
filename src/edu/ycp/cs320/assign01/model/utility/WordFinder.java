package edu.ycp.cs320.assign01.model.utility;

import java.util.ArrayList;

public class WordFinder {
	
	public ArrayList<String> findWords(String input) {
		ArrayList<String> words = new ArrayList<String>();	//Create new words ArrayList
		input = input.trim().toLowerCase();					//trims input and sets to lowercase
		while(!input.equals("")) {							//while the input is not empty
			int space = input.indexOf(" ");					//set int space to the index of space in input
			if(space == -1) {								//if the index of a space is -1 add the word and set the input to a blank string
				words.add(input);
				input = "";
			} else {										//if the index of a space is not -1
				words.add(input.substring(0, space));		//add the substring of the input from the beginning of input through to the index of the first space
				input = input.substring(space).trim();		//set input to iteself without the newly added word and trim
			}
		}
		return words;
	}

}
