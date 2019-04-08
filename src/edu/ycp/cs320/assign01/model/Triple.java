package edu.ycp.cs320.assign01.model;

public class Triple<LeftType, MiddleType, RightType> {
	private LeftType left;
	private MiddleType middle;
	private RightType right;
	
	public Triple(LeftType left, MiddleType middle, RightType right) {
		this.setLeft(left);
		this.setMiddle(middle);
		this.setRight(right);
	}

	public LeftType getLeft() {
		return left;
	}

	public void setLeft(LeftType left) {
		this.left = left;
	}

	public MiddleType getMiddle() {
		return middle;
	}

	public void setMiddle(MiddleType middle) {
		this.middle = middle;
	}

	public RightType getRight() {
		return right;
	}

	public void setRight(RightType right) {
		this.right = right;
	}
}
