package com.mycompany.a3;

import java.util.Observer;
import java.util.Observable;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

//One of the two Views
//Takes up the North container in the primary BorderLayout
//Updates score-replated data using Labels
public class ScoreView extends Container implements Observer {
	
	private GameWorld scoreUpdate;
	private Label labelScore = new Label("Score:  0         ");	
	private Label labelDogsCaptured = new Label("Dogs Caught:  0");
	private Label labelCatsCaptured = new Label("Cats Caught:  0      ");
	private Label labelDogsRemaining = new Label("Dogs Left:  3");
	private Label labelCatsRemaining = new Label("Cats Left:  2         ");
	private Label labelSound = new Label("Sound:   ON");
	
	//Constructor. Initializes Label colors/padding
	public ScoreView() {
		setLayout(new FlowLayout(Component.CENTER));
		getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY));
		
		labelScore.getStyle().setFgColor(ColorUtil.rgb(255, 128, 0));
		//labelScore.getStyle().setPadding(Component.LEFT, 1);
		labelScore.getStyle().setPadding(Component.RIGHT, 17);
		
		labelDogsCaptured.getStyle().setFgColor(ColorUtil.rgb(255, 128, 0));
		//labelDogsCaptured.getStyle().setPadding(Component.LEFT, 1);
		labelDogsCaptured.getStyle().setPadding(Component.RIGHT, 2);

		labelCatsCaptured.getStyle().setFgColor(ColorUtil.rgb(255, 128, 0));
		labelCatsCaptured.getStyle().setPadding(Component.LEFT, 2);
		labelCatsCaptured.getStyle().setPadding(Component.RIGHT, 7);
				
		labelDogsRemaining.getStyle().setFgColor(ColorUtil.rgb(255, 128, 0));
		labelDogsRemaining.getStyle().setPadding(Component.LEFT, 2);
		labelDogsRemaining.getStyle().setPadding(Component.RIGHT, 2);

		labelCatsRemaining.getStyle().setFgColor(ColorUtil.rgb(255, 128, 0));
		labelCatsRemaining.getStyle().setPadding(Component.LEFT, 2);
		labelCatsRemaining.getStyle().setPadding(Component.RIGHT, 7);
		
		labelSound.getStyle().setFgColor(ColorUtil.rgb(255, 128, 0));
		labelSound.getStyle().setPadding(Component.LEFT, 1);
		//labelSound.getStyle().setPadding(Component.RIGHT, 2);
		
		//Add labels to the container
		add(labelScore);
		add(labelDogsCaptured);
		add(labelCatsCaptured);
		add(labelDogsRemaining);
		add(labelCatsRemaining);
		add(labelSound);
	}
	
	public void setSoundLabel(String text) {
		labelSound.setText("Sound:   " + text);
	}
	
	//Obtain updated information from the GameWorld and display to the user through the GUI
	public void update (Observable o, Object arg) {
		scoreUpdate = (GameWorld) o;
		
		labelScore.setText("Score:  " + scoreUpdate.getScore() + "   ");
		//labelScore.getStyle().setPadding(Component.LEFT, 1);
		labelScore.getStyle().setPadding(Component.RIGHT, 17);
		labelDogsCaptured.setText("Dogs Caught:  " + scoreUpdate.getDogsCaught());
		labelCatsCaptured.setText("Cats Caught:  " + scoreUpdate.getCatsCaught());
		labelCatsCaptured.getStyle().setPadding(Component.LEFT, 2);
		labelCatsCaptured.getStyle().setPadding(Component.RIGHT, 7);
		labelDogsRemaining.setText("Dogs Left:  " + scoreUpdate.getDogCount() + "   ");
		labelCatsRemaining.setText("Cats Left:  " + scoreUpdate.getCatCount() + "   ");
		labelCatsRemaining.getStyle().setPadding(Component.LEFT, 2);
		labelCatsRemaining.getStyle().setPadding(Component.RIGHT, 7);
		
		
	}
	
}
