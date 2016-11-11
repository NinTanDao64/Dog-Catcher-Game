package com.mycompany.a3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

//     =^.^=     CATS!!!!     =^.^=     
//One of the two concrete classes branching off of the Animal class
public class Cat extends Animal implements IDrawable, ICollider {
	
	//private Vector<Object> colVector;
	private Set<Object> colSet;
	private int limitCollisionCntr; //Limits how often Cats can spawn new Cats
	private Random rand = new Random();
	
	public Cat (double x, double y, int color, int size, int direction, int speed, ObjectID id) {
		super(x, y, color, size, direction, speed, id);
		//colVector = new Vector<Object>();
		colSet = new HashSet();
		limitCollisionCntr = 0;
	}
	
	//Allows this Cat to self-draw
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(getColor());
		g.fillPolygon(new int [] { ((int)getX()) + (int)pCmpRelPrnt.getX(), 
				(int)getX() - (getSize()/2) + (int)pCmpRelPrnt.getX(), 
				(int)getX() + (getSize()/2) + (int)pCmpRelPrnt.getX()},
				
				new int [] { (int)getY() + (getSize()/2) + (int)pCmpRelPrnt.getY(), 
						(int)getY() - (getSize()/2) + (int)pCmpRelPrnt.getY(), 
						(int)getY() - (getSize()/2) + (int)pCmpRelPrnt.getY()}, 3);
	}
	
	//Check to see if received object is colliding with this Cat
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
	
	//Spawn another Cat near this Cat
	public void handleCollision(ICollider otherObject, GameObjectsCollection gwList, GameWorld gw, Game game) {
		if(gw.getCatCount() < 30) {
			boolean catspawnDone = false;
			while(catspawnDone == false) {
				double randX = rand.nextInt(300) - 150;
				double randY = rand.nextInt(300) - 150;
				double kittenX = getX() + randX;
				double kittenY = getY() + randY;
				while(kittenX < 0 || kittenX > gw.getWidth() || kittenY < 0 || kittenY > gw.getHeight()) {
					randX = rand.nextInt(300) - 150;
					randY = rand.nextInt(300) - 150;
					kittenX = getX() + randX;
					kittenY = getY() + randY;
				}
				gw.add(new Cat(kittenX, kittenY, ColorUtil.rgb(0,  128, 15),
						rand.nextInt(16) + 40, rand.nextInt(360), 5, ObjectID.Cat));
				System.out.println("New cat has joined the game!");
				gw.incCatCount();
				catspawnDone = true;
			}
			int RNG = rand.nextInt(4);
			if(RNG == 0) {
				game.playCatMeow1();
			} else if(RNG == 1) {
				game.playCatMeow2();
			} else if(RNG == 2) {
				game.playCatMeow3();
			} else if(RNG == 3) {
				game.playCatMeow4();
			}
		}
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
		String output = "Cat: " + parentString;
		return output;
	}
}
