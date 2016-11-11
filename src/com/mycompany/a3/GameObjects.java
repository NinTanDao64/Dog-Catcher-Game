package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;


//Top of the hierarchy for key objects within the game
//Defines attributes and methods that all game objects must come with
//including accessing and changing location/speed/color/size.
public abstract class GameObjects {
	
	private double x, y;
	private int color;
	private int size;
	private ObjectID id;
	
	public GameObjects(double x, double y, int color, int size, ObjectID id) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size;
	}
	
	public ObjectID getID() {
		return id;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setX(double newX) {
		x = Math.abs(newX);
	}
	
	public void setY(double newY) {
		y = Math.abs(newY);
	}
	
	public void setSize(int newSize) {
		size = newSize;
	}
	
	public void setColor(int r, int g, int b) {
		color = ColorUtil.rgb(r, g, b);
	}
	
	public String toString() {
		String output = "loc=" + x + "," + y + " color=[" + ColorUtil.red(color) + ","
				        + ColorUtil.green(color) + "," + ColorUtil.blue(color) + "] " +
				        "size=" + size;
		return output;
	}
}