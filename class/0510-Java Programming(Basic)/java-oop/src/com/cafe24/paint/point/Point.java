package com.cafe24.paint.point;

import com.cafe24.paint.i.Drawable;

public class Point implements Drawable {

	protected int x;
	protected int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void show() {
		
	}
	
	@Override
	public void draw() {
		System.out.println("점("+x+","+y+")에 점을 찍었습니다.");
	}
	
	public void draw(boolean visible) {
		if(visible) {
			draw();
			return;
		}
		
		System.out.println("점("+x+","+y+")에 점을 지웠습니다.");
	}
}
