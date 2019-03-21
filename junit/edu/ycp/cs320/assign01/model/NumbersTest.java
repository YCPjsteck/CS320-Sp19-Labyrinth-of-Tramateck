package edu.ycp.cs320.assign01.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.assign01.model.Numbers;

public class NumbersTest {
	private Numbers model;
	
	@Before
	public void setUp() {
		model = new Numbers();
	}
	
	@Test
	public void testFirst() {
		model.setFirst(5);
		assertTrue(5 == model.getFirst());
	}
	@Test
	public void testSecond() {
		model.setSecond(28);
		assertTrue(28 == model.getSecond());
	}
	@Test
	public void testThird() {
		model.setThird(14);
		assertTrue(14 == model.getThird());
	}
	
	@Test
	public void testAdd() {
		model.setFirst(9);
		model.setSecond(10);
		model.setThird(2);
		assertTrue(21 == model.getAdd());
	}
	@Test
	public void testMultiply() {
		model.setFirst(9);
		model.setSecond(7);
		assertTrue(63 == model.getMultiply());
	}
}
