package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

//Currently the only concrete class branching off of the Catcher class
public class Net extends Catcher {
	
	public Net (double x, double y, int color, int size, ObjectID id) {
		super(x, y, color, size, id);
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(getColor());
		g.drawRect((int)getX() - (getSize() / 2) + (int)pCmpRelPrnt.getX(), 
				(int)getY() - (getSize()/2) + (int)pCmpRelPrnt.getY(), getSize(), getSize());
	}
	
	public String toString() {
		String parentString = super.toString();
		String output = "Net: " + parentString;
		return output;
	}
}
