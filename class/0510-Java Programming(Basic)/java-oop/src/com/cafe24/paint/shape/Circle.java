package com.cafe24.paint.shape;

public class Circle extends Shape {
	
	private int x;
	private int y;
	private int radius;

	@Override
	public void draw() {
		System.out.println("원을 그렸습니다.");
	}

	@Override
	public double calcArea() {
		return 0.f;
	}
}
