package com.mycompany.a3;

import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;

//One of the two Views
//Takes up the center container in the primary BorderLayout
//Empty for this current assignment
//Updates map-related data and prints them to the console whenever a
//change is made in the Game World
public class MapView extends Container implements Observer {

	private GameWorld mapUpdate;
	private GameWorld mvGameWorld;
	private GameObjectsCollection mvObjectList;
	
	public MapView (GameWorld gw) {
		getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLUE));
		mvGameWorld = gw;
		mvObjectList = gw.getObjectList();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		for (int i = 0; i <= mvObjectList.size() - 1; i++) {
			if (mvObjectList.get(i) instanceof Dog) {
				//((Dog) mvObjectList.get(i)).setSelected(false);
				((Dog) mvObjectList.get(i)).draw(g, pCmpRelPrnt);
			}
			else if (mvObjectList.get(i) instanceof Cat) {
				((Cat) mvObjectList.get(i)).draw(g, pCmpRelPrnt);
			}
			else if (mvObjectList.get(i) instanceof Net) {
				((Net) mvObjectList.get(i)).draw(g, pCmpRelPrnt);
			}
		}
	}
	
	//Used to Select a Dog. Only used during 'Pause' mode when 'Heal' button is enabled
	public void pointerPressed(int x, int y) {
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x, y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		for(int i = 0; i <mvObjectList.size(); i++) {
			if(mvObjectList.get(i) instanceof Dog) {
				Dog tempDog = (Dog)mvObjectList.get(i);
				if(tempDog.contains(pPtrRelPrnt, pCmpRelPrnt))
					tempDog.setSelected(true);
				else
					tempDog.setSelected(false);
			}
		}
		repaint();
	}

	
	//Invoked on every call of run(). This method alongside paint() are both responsible for animation.
	public void update (Observable o, Object arg) {
		repaint();
		mapUpdate = (GameWorld) o;
		System.out.println(mapUpdate.printMap());
	}
	
}