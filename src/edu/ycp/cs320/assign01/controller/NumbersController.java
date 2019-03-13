package edu.ycp.cs320.assign01.controller;

import edu.ycp.cs320.assign01.model.Numbers;

public class NumbersController {
	private Numbers model;
	
	public void setModel(Numbers model) {
		this.model = model;
	}
	
	public void setFirst(double first) {
		model.setFirst(first);
	}
	public void setSecond(double second) {
		model.setSecond(second);
	}
	public void setThird(double third) {
		model.setThird(third);
	}
	public double getFirst() {
		return model.getFirst();
	}
	public double getSecond() {
		return model.getSecond();
	}
	public double getThird() {
		return model.getThird();
	}
	
	public double add() {
		return model.getAdd();
	}
	public double multiply() {
		return model.getMultiply();
	}
}
