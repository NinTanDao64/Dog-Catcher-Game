package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import java.util.Random;

//One of the two branches of the GameObjects class
//All concrete classes of this type inherit IMoveable's abstract methods
public abstract class Animal extends GameObjects implements IMoveable {

	private double x;
	private double y;
	private int speed;
	private int direction;
	private Random rand = new Random();
	
	public Animal (double x, double y, int color, int size, int direction, int speed, ObjectID id) {
		super(x, y, color, size, id);
		this.speed = speed;
		this.direction = direction;
	}
	
	//Animal definition of movement for Cat and Dog child methods
	public void move(int width, int height) {
		x = getX();
		y = getY();
		//Every time an Animal moves, add a random value between -5 and 5 to
		//the current value of direction to prevent movement in a straight line
		if(rand.nextInt(2) == 1) {
			if(rand.nextInt(2) == 1) {
				if(rand.nextInt(2) == 1) {
					if(rand.nextInt(2) == 1) {
						if(rand.nextInt(2) == 1) {
							if(rand.nextInt(2) == 1) {
								direction = direction + (rand.nextInt(10) - 5);
							}
						}
					}
				}
			}
		}
		double deltaX = Math.cos(90 - direction) * getSpeed();
		double deltaY = Math.sin(90- direction) * getSpeed();
		double newX = Math.round((x + deltaX) * 10.0) / 10.0;
		double newY = Math.round((y + deltaY) * 10.0) / 10.0;
		//Ensure that Animals cannot move beyond boundaries of game world
		while(newX < 0 || newX > width || newY < 0 || newY > height) {
			direction = rand.nextInt(360);
			deltaX = Math.cos(90 - direction);
			deltaY = Math.sin(90 - direction);
			//Round new coordinates to nearest tenth
			newX = Math.round((x + deltaX) * 10.0) / 10.0;
			newY = Math.round((y + deltaY) * 10.0) / 10.0;
		}
		super.setX(newX);
		super.setY(newY);
	}
	
//--------------------------------------------------------------------------------------
//
//			Accessors / Mutators / toString
//
//--------------------------------------------------------------------------------------
	
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	
	public String toString() {
		String parentString = super.toString();
		String output = parentString + " speed=" + speed + " " + "dir=" + direction;
		return output;
	}
	
}
