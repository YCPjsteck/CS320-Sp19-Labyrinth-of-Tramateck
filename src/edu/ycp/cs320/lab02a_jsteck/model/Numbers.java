package edu.ycp.cs320.lab02a_jsteck.model;

public class Numbers {
	private double first, second, third;
	
	public Numbers() {
	}
	
	public void setFirst(double first) {
		this.first = first;
	}
	public void setSecond(double second) {
		this.second = second;
	}
	public void setThird(double third) {
		this.third = third;
	}
	
	public double getFirst() {
		return first;
	}
	public double getSecond() {
		return second;
	}
	public double getThird() {
		return third;
	}
	
	public double getAdd() {
		return first + second + third;
	}
	public double getMultiply() {
		return first * second;
	}
}
