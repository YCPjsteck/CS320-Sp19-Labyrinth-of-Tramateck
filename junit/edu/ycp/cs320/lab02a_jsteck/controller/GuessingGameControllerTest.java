package edu.ycp.cs320.lab02a_jsteck.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab02a_jsteck.controller.GuessingGameController;
import edu.ycp.cs320.lab02a_jsteck.model.GuessingGame;

public class GuessingGameControllerTest {
	private GuessingGame model;
	private GuessingGameController controller;
	
	@Before
	public void setUp() {
		model = new GuessingGame();
		controller = new GuessingGameController();
		
		model.setMin(1);
		model.setMax(100);
		
		controller.setModel(model);
	}
	
	@Test
	public void testNumberIsGreater() {
		int currentGuess = model.getGuess();
		controller.setNumberIsGreaterThanGuess();
		assertTrue(model.getGuess() > currentGuess);
	}
	
	@Test
	public void testNumberIsLess() {
		int currentGuess = model.getGuess();
		controller.setNumberIsLessThanGuess();
		assertTrue(model.getGuess() < currentGuess);
	}
	
	@Test
	public void testNumberFound() {
		controller.setNumberFound();
		assertTrue(model.isDone());
	}
}
