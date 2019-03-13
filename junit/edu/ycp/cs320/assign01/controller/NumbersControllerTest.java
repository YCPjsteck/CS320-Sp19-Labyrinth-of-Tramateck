package edu.ycp.cs320.assign01.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.controller.NumbersController;
import edu.ycp.cs320.assign01.model.Numbers;

public class NumbersControllerTest {
	private Numbers model;
	private NumbersController controller;
	
	@Before
	public void setUp() {
		model = new Numbers();
		controller = new NumbersController();
		
		controller.setModel(model);
	}
	
	@Test
	public void addTest() {
		controller.setFirst(4);
		controller.setSecond(1);
		controller.setThird(2);
		System.out.println(controller.add());
		assertTrue(7. == controller.add());
	}
	@Test
	public void multiplyTest() {
		controller.setFirst(2);
		controller.setSecond(3);
		assertTrue(6. == controller.multiply());
	}
}
