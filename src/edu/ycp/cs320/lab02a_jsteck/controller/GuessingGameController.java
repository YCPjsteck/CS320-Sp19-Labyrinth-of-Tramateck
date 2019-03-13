package edu.ycp.cs320.lab02a_jsteck.controller;

import edu.ycp.cs320.lab02a_jsteck.model.GuessingGame;

/**
 * Controller for the guessing game.
 */
public class GuessingGameController {
	private GuessingGame model;

	/**
	 * Set the model.
	 * 
	 * @param model the model to set
	 */
	public void setModel(GuessingGame model) {
		this.model = model;
	}

	/**
	 * Start a new guessing game by setting the minimum to 1 and the maximum to 100.
	 */
	public void startGame() {
		model.setMax(100);
		model.setMin(1);
	}

	/**
	 * Called to indicate that the current guess is correct.
	 * Set the min and max to the current guess.
	 */
	public void setNumberFound() {
		// calling getGuess and assigning it to either min or max directly
		// changes the value of getGuess, so store the getGuess in a temp int
		int temp = model.getGuess(); 
		model.setMin(temp);
		model.setMax(temp);
	}

	/**
	 * Called to indicate that the user is thinking of a number that
	 * is less than the current guess.
	 */
	public void setNumberIsLessThanGuess() {
		model.setIsLessThan(model.getGuess());
		if(model.getMin() < 1)
			model.setMin(1);
		// If for some reason the minimum number is now above the max,
		// set them equal to each other to avoid any scenarios in which
		// the game seems stuck on one number
		if(model.getMin() > model.getMax())
			model.setMin(model.getMax());
	}

	/**
	 * Called to indicate that the user is thinking of a number that
	 * is greater than the current guess.
	 */
	public void setNumberIsGreaterThanGuess() {
		model.setIsGreaterThan(model.getGuess());
		if(model.getMax() > 100)
			model.setMax(100);
		// If for some reason the maximum number is now below the min,
		// set them equal to each other to avoid any scenarios in which
		// the game seems stuck on one number
		if(model.getMax() < model.getMin())
			model.setMax(model.getMin());
	}
}
