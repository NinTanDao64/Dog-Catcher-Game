package com.mycompany.a3;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

//One of the two concrete classes branching off of the Animal class
public class Dog extends Animal implements IDrawable, ICollider, ISelectable {
	
	private int scratches;
	//private Vector<Object> colVector;
	private Set<Object> colSet;
	int limitCollisionCntr;
	private boolean selected;
	
	public Dog (double x, double y, int color, int size, int direction, int speed, int scratches, ObjectID id) {
		super(x, y, color, size, direction, speed, id);
		this.scratches = scratches;
		//colVector = new Vector<Object>();
		colSet = new HashSet();
		limitCollisionCntr = 0;
		selected = false;
	}
	
	public int getScratches() {
		return scratches;
	}
	
	public void addScratch() {
		scratches++;
	}
	
	//Cure this Dog of all ailments, restoring original color, speed, and removing all scratches.
	public void healUp() {
		setSpeed(5);
		setColor(0, 128, 255);
		this.scratches = 0;
	}
	
	//Allows this Dog to self-draw
	public void draw (Graphics g, Point pCmpRelPrnt) {
		g.setColor(getColor());
		if(isSelected()) {
			g.drawArc((int)getX() - (getSize() /2) + (int)pCmpRelPrnt.getX(), 
					(int)getY() - (getSize() / 2) + (int)pCmpRelPrnt.getY(), getSize(), getSize(), 0, 360);
		} else {
			g.fillArc((int)getX() - (getSize() /2) + (int)pCmpRelPrnt.getX(), 
				(int)getY() - (getSize() / 2) + (int)pCmpRelPrnt.getY(), getSize(), getSize(), 0, 360);
		}
	}
	
	//Check to see if received object is colliding with this Dog
	public boolean collidesWith(ICollider obj) {
		boolean result = false;

		Cat tempObj = (Cat)obj;

		int halfSize = getSize() / 2;
		double xDiff = Math.abs(this.getX() - tempObj.getX());
		double yDiff = Math.abs(this.getY() - tempObj.getY());
		if((xDiff < halfSize) && (yDiff < halfSize)) {
			result = true;
		}
		return result;
	}
	
	//Invoke catFight() function on this Dog. Add a scratch, change color slightly, lower speed by 1.
	public void handleCollision(ICollider otherObject, GameObjectsCollection gwList, GameWorld gw, Game game) {
		if(super.getSpeed() > 0) {
			int tempColor = getColor();
			int newRed = ColorUtil.red(tempColor) + 40;
			int newGreen = ColorUtil.green(tempColor) + 20;
			//int newBlue = ColorUtil.blue(tempColor) - 20;
			setColor(newRed, newGreen, 255);
			setSpeed(getSpeed() - 1);
			addScratch();
			game.playDogBark();
			System.out.println("A dog has been scratched!");
		}
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean yesNo) {
		selected = yesNo;
	}
	
	//Check to see if Pointer has clicked/pressed within this Dog's bounding square
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		float px = pPtrRelPrnt.getX();
		float py = pPtrRelPrnt.getY();
		float xLoc = pCmpRelPrnt.getX() + (float)getX();
		float yLoc = pCmpRelPrnt.getY() + (float)getY();
		if ( (px >= xLoc) && (px <= xLoc + getSize())
				&& (py >= yLoc) && (py <= yLoc + getSize()) )
				return true; else return false;
	}
	
	/*public void addToColVector(Object obj) {
		colVector.addElement(obj);
	}
	
	public void clearColVector() {
		colVector.removeAllElements();
	}
	
	
	public Vector<Object> getColVector() {
		return colVector;
	}*/
	
	public void addToColSet(Object obj) {
		colSet.add(obj);
	}
	
	public void clearColSet() {
		colSet.clear();
	}
	
	public Set<Object> getColSet() {
		return colSet;
	}
	
	public void incCounter() {
		limitCollisionCntr++;
	}
	
	public int getCounter() {
		return limitCollisionCntr;
	}
	
	public void clearCounter() {
		limitCollisionCntr = 0;
	}

	public String toString() {
		String parentString = super.toString();
		String output = "Dog: " + parentString + " scratches=" + scratches;
		return output;
	}
	
}