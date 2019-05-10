package com.cafe24.paint.main;

import com.cafe24.paint.i.Drawable;
import com.cafe24.paint.point.ColorPoint;
import com.cafe24.paint.point.Point;
import com.cafe24.paint.shape.Circle;
import com.cafe24.paint.shape.Rect;
import com.cafe24.paint.shape.Triangle;
import com.cafe24.paint.text.GraphicString;

public class MainApp {

	public static void main(String[] args) {
		Point p1 = new Point(10,20);
		draw(p1);
		
		Point p2 = new ColorPoint(50,100,"red");;
		
		p2.draw(true);
		
		p1.draw(false);
		p2.draw(false);
		
		Triangle triangle = new Triangle();
		draw(triangle);
		
		Rect rect = new Rect();
		draw(rect);
		
		Circle circle = new Circle();
		draw(circle);
		
		draw(new GraphicString("hello"));
	}
	
	public static void draw(Drawable drawable) {
		drawable.draw();
	}
}
