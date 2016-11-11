package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;

//One of the two branches of the GameObjects class
//All concrete classes branching from this class are guideable 
public abstract class Catcher extends GameObjects implements IGuideable {
	
	private double temp = 0;

	public Catcher (double x, double y, int color, int size, ObjectID id) {
		super(x, y, color, size, id);
	}
	
	public void moveLeft() {
		temp = super.getX();
		setX(temp - 100);
	}
	
	public void moveRight() {
		temp = super.getX();
		setX(temp + 100);
	}
	
	public void moveUp() {
		temp = super.getY();
		setY(temp + 100);
	}
	
	public void moveDown() {
		temp = super.getY();
		setY(temp - 100);
	}
	
//Was unable to come up with an implementation to allow this class
//to manipulate the array list in the GameWorld class.
//The working implementation  for these functions are located in the Game class
//So for now this class inherits the following two abstract classes
//from the IGuideable interface, containing no implementations.
	public void jumptoDog() {
	}
	
	public void jumptoCat() {	
	}
	
}