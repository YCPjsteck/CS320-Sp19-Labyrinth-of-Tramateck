package edu.ycp.cs320.lab02a_jsteck.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab02a_jsteck.model.GuessingGame;

public class GuessingGameTest {
	private GuessingGame model;
	
	@Before
	public void setUp() {
		model = new GuessingGame();
	}
	
	@Test
	public void testSetMin() {
		model.setMin(1);
		assertEquals(1, model.getMin());
	}
	
	@Test
	public void testSetMax() {
		model.setMax(100);
		assertEquals(100, model.getMax());
	}
	
	@Test
	public void testDone() {
		model.setMax(50);
		model.setMin(50);
		assertTrue(model.isDone());
	}
	
	@Test
	public void testSetIsLessThan() {
		model.setIsLessThan(30);
		assertEquals(29, model.getMax());
	}
	
	@Test
	public void testSetIsGreaterThan() {
		model.setIsGreaterThan(60);
		assertEquals(61,model.getMin());
	}
	
	@Test
	public void testGetGuess() {
		model.setMax(48);
		model.setMin(21);
		assertEquals(34,model.getGuess());
	}
}
