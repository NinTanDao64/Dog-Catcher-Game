package com.mycompany.a3;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.io.Log;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.charts.util.ColorUtil;
import java.util.Observer;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

//Controller class in charge of generating the GameWorld, ScoreView, MapView, registering
//both views as observers (observing the GameWorld), and creating the GUI with Action Listeners
//for each button/checkbox.
public class Game extends Form implements Runnable, ActionListener {
	
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	private Random rand = new Random();
	
	private UITimer timer = new UITimer(this);
	
	private Button buttonExpand = new Button("Expand");
	private Button buttonUp = new Button("Up");
	private Button buttonDown = new Button("Down");
	private Button buttonContract = new Button("Contract");
	private Button buttonLeft = new Button("Left");
	private Button buttonRight = new Button("Right");
	private Button buttonScoop = new Button("Scoop");
	private Button buttonJumpToDog = new Button("Jump To Dog");
	private Button buttonJumpToCat = new Button("Jump To Cat");
	private Button buttonHeal = new Button ("Heal");
	private Button buttonPausePlay = new Button("Pause");
	
	private boolean pauseCheck = false;
	private boolean muteCheck = false;
	
	private Sound dogBarkSound;
	private Sound catMeowSound1;
	private Sound catMeowSound2;
	private Sound catMeowSound3;
	private Sound catMeowSound4;
	private Sound scoopSound;
	private Sound moveSound;
	private Sound healSound;
	private Sound pauseSound;
	private BGSound bgMusic;
	private BGSound bgEndMusic;
	
	private CommandScoop myScoopCommand;
	private CommandMoveUp myMoveUpCommand;
	private CommandMoveDown myMoveDownCommand;
	private CommandMoveLeft myMoveLeftCommand;
	private CommandMoveRight myMoveRightCommand;
	private CommandExpand myExpandCommand;
	private CommandContract myContractCommand;
	private CommandJumpToDog myJumpToDogCommand;
	private CommandJumpToCat myJumpToCatCommand;
	private CommandSpawnCat mySpawnCatCommand;
	private CommandCatFight myCatFightCommand;
	private CommandTick myTickCommand;
	private CommandHeal myHealCommand;
	
	private CommandAbout myAboutCommand;
	private CommandQuit myExitCommand;
	private CommandHelp myHelpCommand;
		
	public Game() {
		gw = new GameWorld();
		mv = new MapView(gw);
		sv = new ScoreView();
		gw.addObserver(mv);
		gw.addObserver(sv);
	    
		this.setTitle("Dog Catcher Game"); //Title bar text
		
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY)); //Set color for content pane's border
		this.setLayout(new BorderLayout());
			
//*--*--*--*--*--*--* Set Commands & Keybindings *--*--*--*--*--*--*
		myScoopCommand = new CommandScoop(gw, this);
		myMoveUpCommand = new CommandMoveUp(gw, this);
		myMoveDownCommand = new CommandMoveDown(gw, this);
		myMoveLeftCommand = new CommandMoveLeft(gw, this);
		myMoveRightCommand = new CommandMoveRight(gw, this);
		myExpandCommand = new CommandExpand(gw);
		myContractCommand = new CommandContract(gw);
		myJumpToDogCommand = new CommandJumpToDog(gw);
		myJumpToCatCommand = new CommandJumpToCat(gw);
		mySpawnCatCommand = new CommandSpawnCat(gw);
		myCatFightCommand = new CommandCatFight(gw);
		myTickCommand = new CommandTick(gw);
		myHealCommand = new CommandHeal(this);
		
		myAboutCommand = new CommandAbout(this);
		myExitCommand = new CommandQuit(this);
		myHelpCommand = new CommandHelp(this);
		
		addKeyListener('s', myScoopCommand);
		addKeyListener('u', myMoveUpCommand);
		addKeyListener('d', myMoveDownCommand);
		addKeyListener('l', myMoveLeftCommand);
		addKeyListener('r', myMoveRightCommand);
		addKeyListener('e', myExpandCommand);
		addKeyListener('c', myContractCommand);
		addKeyListener('o', myJumpToDogCommand);
		addKeyListener('a', myJumpToCatCommand);
		addKeyListener('k', mySpawnCatCommand);
		addKeyListener('f', myCatFightCommand);
		addKeyListener('t', myTickCommand);
		addKeyListener('x', myExitCommand);
		
//*--*--*--*--*--*--* LEFT SIDE BUTTONS *--*--*--*--*--*--*
		
		Container leftSide = new Container();
		leftSide.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		leftSide.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY));
				
		buttonExpand = new Button("Expand");
		buttonExpand.setCommand(myExpandCommand);
		buttonExpand.getDisabledStyle().setBgTransparency(255);
		buttonExpand.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonExpand.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonExpand.getUnselectedStyle().setBgTransparency(255);
		buttonExpand.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonExpand.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonExpand.getAllStyles().setMargin(Component.TOP, 130);
		buttonExpand.getAllStyles().setPadding(Component.TOP, 5);
		buttonExpand.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonExpand.getAllStyles().setPadding(Component.LEFT, 5);
		buttonExpand.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonUp = new Button("Up");
		buttonUp.setCommand(myMoveUpCommand);
		buttonUp.getDisabledStyle().setBgTransparency(255);
		buttonUp.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonUp.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonUp.getUnselectedStyle().setBgTransparency(255);
		buttonUp.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 204, 204));
		buttonUp.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonUp.getAllStyles().setPadding(Component.TOP, 5);
		buttonUp.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonUp.getAllStyles().setPadding(Component.LEFT, 5);
		buttonUp.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonDown = new Button("Down");
		buttonDown.setCommand(myMoveDownCommand);
		buttonDown.getDisabledStyle().setBgTransparency(255);
		buttonDown.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonDown.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonDown.getUnselectedStyle().setBgTransparency(255);
		buttonDown.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 204, 204));
		buttonDown.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonDown.getAllStyles().setPadding(Component.TOP, 5);
		buttonDown.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonDown.getAllStyles().setPadding(Component.LEFT, 5);
		buttonDown.getAllStyles().setPadding(Component.RIGHT, 5);
		
		//Add buttons to the left-side container
		leftSide.add(buttonExpand);
		leftSide.add(buttonUp);
		leftSide.add(buttonDown);
		
//*--*--*--*--*--*--* RIGHT SIDE BUTTONS *--*--*--*--*--*--*		
		
		Container rightSide = new Container();
		rightSide.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		rightSide.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY));
				
		buttonContract = new Button("Contract");
		buttonContract.setCommand(myContractCommand);
		buttonContract.getDisabledStyle().setBgTransparency(255);
		buttonContract.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonContract.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonContract.getUnselectedStyle().setBgTransparency(255);
		buttonContract.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonContract.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonContract.getAllStyles().setMargin(Component.TOP, 130);
		buttonContract.getAllStyles().setPadding(Component.TOP, 5);
		buttonContract.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonContract.getAllStyles().setPadding(Component.LEFT, 5);
		buttonContract.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonLeft = new Button("Left");
		buttonLeft.setCommand(myMoveLeftCommand);
		buttonLeft.getDisabledStyle().setBgTransparency(255);
		buttonLeft.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonLeft.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonLeft.getUnselectedStyle().setBgTransparency(255);
		buttonLeft.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 204, 204));
		buttonLeft.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonLeft.getAllStyles().setPadding(Component.TOP, 5);
		buttonLeft.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonLeft.getAllStyles().setPadding(Component.LEFT, 5);
		buttonLeft.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonRight = new Button("Right");
		buttonRight.setCommand(myMoveRightCommand);
		buttonRight.getDisabledStyle().setBgTransparency(255);
		buttonRight.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonRight.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonRight.getUnselectedStyle().setBgTransparency(255);
		buttonRight.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 204, 204));
		buttonRight.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonRight.getAllStyles().setPadding(Component.TOP, 5);
		buttonRight.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonRight.getAllStyles().setPadding(Component.LEFT, 5);
		buttonRight.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonScoop = new Button("Scoop");
		buttonScoop.setCommand(myScoopCommand);
		buttonScoop.getDisabledStyle().setBgTransparency(255);
		buttonScoop.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonScoop.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonScoop.getUnselectedStyle().setBgTransparency(255);
		buttonScoop.getUnselectedStyle().setBgColor(ColorUtil.rgb(255, 51, 51));
		buttonScoop.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonScoop.getAllStyles().setPadding(Component.TOP, 10);
		buttonScoop.getAllStyles().setPadding(Component.BOTTOM, 10);
		buttonScoop.getAllStyles().setPadding(Component.LEFT, 5);
		buttonScoop.getAllStyles().setPadding(Component.RIGHT, 5);

		//Add buttons to the right-side container
		rightSide.add(buttonContract);
		rightSide.add(buttonLeft);
		rightSide.add(buttonRight);
		rightSide.add(buttonScoop);
	
//*--*--*--*--*--*--* BOTTOM SIDE BUTTONS *--*--*--*--*--*--*
		
		Container bottomSide = new Container();
		bottomSide.setLayout(new FlowLayout(Component.CENTER));
		bottomSide.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY));
		
		buttonHeal.setCommand(myHealCommand);
		buttonHeal.setEnabled(false);
		buttonHeal.getDisabledStyle().setBgTransparency(255);
		buttonHeal.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonHeal.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonHeal.getUnselectedStyle().setBgTransparency(255);
		buttonHeal.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonHeal.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonHeal.getAllStyles().setPadding(Component.TOP, 5);
		buttonHeal.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonHeal.getAllStyles().setPadding(Component.LEFT, 5);
		buttonHeal.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonPausePlay.addActionListener(this);
		buttonPausePlay.getUnselectedStyle().setBgTransparency(255);
		buttonPausePlay.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonPausePlay.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonPausePlay.getAllStyles().setPadding(Component.TOP, 5);
		buttonPausePlay.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonPausePlay.getAllStyles().setPadding(Component.LEFT, 10);
		buttonPausePlay.getAllStyles().setPadding(Component.RIGHT, 10);
		
		buttonJumpToDog = new Button("JumpToADog");
		buttonJumpToDog.setCommand(myJumpToDogCommand);
		buttonJumpToDog.getDisabledStyle().setBgTransparency(255);
		buttonJumpToDog.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonJumpToDog.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonJumpToDog.getUnselectedStyle().setBgTransparency(255);
		buttonJumpToDog.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonJumpToDog.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonJumpToDog.getAllStyles().setPadding(Component.TOP, 5);
		buttonJumpToDog.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonJumpToDog.getAllStyles().setPadding(Component.LEFT, 5);
		buttonJumpToDog.getAllStyles().setPadding(Component.RIGHT, 5);
		
		/*Button buttonCatSpawn = new Button("Kitten");
		buttonCatSpawn.setCommand(mySpawnCatCommand);
		buttonCatSpawn.getUnselectedStyle().setBgTransparency(255);
		buttonCatSpawn.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonCatSpawn.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonCatSpawn.getAllStyles().setPadding(Component.TOP, 5);
		buttonCatSpawn.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonCatSpawn.getAllStyles().setPadding(Component.LEFT, 5);
		buttonCatSpawn.getAllStyles().setPadding(Component.RIGHT, 5);
		
		Button buttonCatFight = new Button("Fight");
		buttonCatFight.setCommand(myCatFightCommand);
		buttonCatFight.getUnselectedStyle().setBgTransparency(255);
		buttonCatFight.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonCatFight.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonCatFight.getAllStyles().setPadding(Component.TOP, 5);
		buttonCatFight.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonCatFight.getAllStyles().setPadding(Component.LEFT, 5);
		buttonCatFight.getAllStyles().setPadding(Component.RIGHT, 5);
		
		Button buttonTick = new Button("Tick");
		buttonTick.setCommand(myTickCommand);
		buttonTick.getUnselectedStyle().setBgTransparency(255);
		buttonTick.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonTick.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonTick.getAllStyles().setPadding(Component.TOP, 5);
		buttonTick.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonTick.getAllStyles().setPadding(Component.LEFT, 5);
		buttonTick.getAllStyles().setPadding(Component.RIGHT, 5);*/
		
		buttonJumpToCat = new Button("JumpToACat");
		buttonJumpToCat.setCommand(myJumpToCatCommand);
		buttonJumpToCat.getDisabledStyle().setBgTransparency(255);
		buttonJumpToCat.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonJumpToCat.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonJumpToCat.getUnselectedStyle().setBgTransparency(255);
		buttonJumpToCat.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonJumpToCat.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonJumpToCat.getAllStyles().setPadding(Component.TOP, 5);
		buttonJumpToCat.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonJumpToCat.getAllStyles().setPadding(Component.LEFT, 5);
		buttonJumpToCat.getAllStyles().setPadding(Component.RIGHT, 5);
		
		//Add buttons to the bottom-side container
		bottomSide.add(buttonJumpToDog);
		bottomSide.add(buttonHeal);
		bottomSide.add(buttonPausePlay);
		/*bottomSide.add(buttonCatSpawn);
		bottomSide.add(buttonCatFight);
		bottomSide.add(buttonTick);*/
		bottomSide.add(buttonJumpToCat);
		
//*--*--*--*--*--*--* TOOLBAR & SIDE MENU ITEMS *--*--*--*--*--*--*

		Toolbar gameToolbar = new Toolbar();
		setToolbar(gameToolbar);
		
		gameToolbar.addCommandToSideMenu(myScoopCommand);
		
		Command sideMenuSound = new Command("Sound");
		CheckBox checkSideMenuSound = new CheckBox("Mute Sound");
		Command mySideMenuSoundCheck = new SideMenuSoundCheck(this);
		checkSideMenuSound.setCommand(mySideMenuSoundCheck);
		checkSideMenuSound.getAllStyles().setBgTransparency(255);
		checkSideMenuSound.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		sideMenuSound.putClientProperty("SideComponent", checkSideMenuSound);
		gameToolbar.addCommandToSideMenu(sideMenuSound);
	
		//Add commands to the side menu
		gameToolbar.addCommandToSideMenu(myAboutCommand);
		gameToolbar.addCommandToSideMenu(myExitCommand);
		
		//Add 'Help?' button to right side of content pane
		gameToolbar.addCommandToRightBar(myHelpCommand);
				
//*--*--*--*--*--*--* Add Components to Primary BorderLayout *--*--*--*--*--*--*		
		
		this.add(BorderLayout.WEST, leftSide);
		this.add(BorderLayout.EAST, rightSide);
		this.add(BorderLayout.SOUTH, bottomSide);
		this.add(BorderLayout.NORTH, sv);
		this.add(BorderLayout.CENTER, mv);
		this.show();
		
		//Now that the center component has been fit into the display, obtain its dimensions
		//and give them to the GameWorld to initialize GameObject locations, as well as grant
		//GameWorld methods their dimension limits.
		gw.setWidth(mv.getWidth());
		gw.setHeight(mv.getHeight());
		gw.initLayout();
		
		//Attach a sound file to each Sound object
		bgMusic = new BGSound("Bomberman Hero OST - Redial.mp3");
		bgEndMusic = new BGSound("Victory Fanfare.mp3");
		bgMusic.play();
		
		dogBarkSound = new Sound("DogBark.wav");
		catMeowSound1 = new Sound("CatMeow1.wav");
		catMeowSound2 = new Sound("CatMeow2.wav");
		catMeowSound3 = new Sound("CatMeow3.wav");
		catMeowSound4 = new Sound("CatMeow4.wav");
		moveSound = new Sound("Select Screen1.wav");
		healSound = new Sound("FF7 Cure2.wav");
		pauseSound = new Sound("Mario Pause.wav");
		scoopSound = new Sound("Special Item.wav");
		
		//Start the UITimer, allowing Animal movement and animation every 15ms
		timer.schedule(15,  true,  this);
	}
	
//*--*--*--*--*--*--* Methods for command classes to execute when buttons or hotkeys are used *--*--*--*--*--*--*	
//*--*--*--*--*--*--* Also includes run() which is invoked on every tick of the UITimer*--*--*--*--*--*--*	

	public void run() {
		gw.tick(); //Causes all Animal objects to move, and update their positions, forcing a repaint() on the MapView
		GameObjectsCollection listRef = gw.getObjectList();
		for(int i = 1; i < listRef.size(); i++) { //Search through entire list of Animals (exclude the net)
			if(listRef.get(i) instanceof Dog) { //Verify if object is a Dog, to handle Dog-Cat collision
				Dog curDog = (Dog)listRef.get(i);
				curDog.incCounter(); //Increment collision counter; cannot go further unless this value breaks 250
				//curDog.clearColSet();
				//if(curDog.getCounter() > 250) { //This Dog is only eligible to handle a collision if it has been through 150 run() calls without colliding
					//curDog.clearColSet();
					for(int j = 1; j < listRef.size(); j++) {
						if(listRef.get(j) instanceof Cat) { //Verify that second object is a Cat
							if(!curDog.getColSet().contains(listRef.get(j))) { //Check to see if the Cat has not already handled it's collision with the same Dog
								if(curDog.collidesWith((ICollider)listRef.get(j))) { //Check to see if the two objects are colliding
									curDog.handleCollision((ICollider)listRef.get(j), gw.getObjectList(), gw, this); //Handle Dog-Cat collision (invoke catFight on this Dog)
									curDog.addToColSet(listRef.get(j)); //Add Cat to Dog's Collision Set, so the same collision isn't handled repeatedly every 15ms
								}
							}
						}
					}
				//}
			}
			if(listRef.get(i) instanceof Cat) { //Verify if object is a Cat, to handle Cat-Cat collision
				Cat curCat = (Cat)listRef.get(i);
				curCat.incCounter(); //Increment collision counter; cannot go further until this value breaks 375
				if(curCat.getCounter() > 375) { //This cat is only eligible to handle a collision if it has been through 375 run() calls without colliding
					curCat.clearColSet();
					for(int k = 1; k < listRef.size(); k++) { //Search for a second Cat to handle collision
						if(listRef.get(k) instanceof Cat) { //Verify that second object is a Cat
							if(curCat != listRef.get(k)) {	//Verify that this second Cat isn't the same Cat as the first, to prevent self-collision
								if(!curCat.getColSet().contains(listRef.get(k))) { //Check to see if the second Cat hasn't already handled it's collision with the same first Cat
									if(curCat.collidesWith((ICollider)listRef.get(k))) { //Check to see if the two objects are colliding
										//L O L CATSPLOSION
										curCat.handleCollision((ICollider)listRef.get(k), gw.getObjectList(), gw, this); //Handle Cat-Cat collision (spawn a cat)
										curCat.addToColSet(listRef.get(k)); //Add second Cat to first Cat's Collision Set, so same collision isn't handled repeatedly every 15ms
										curCat.clearCounter(); //Restart the limiter counter, so the same Cat cannot spawn another Cat for at least another 375 run() invocations
									}
								}
							}
						}
					}
				}
			}
		}
		/*for (int n = 0; n < listRef.size(); n++) {
			if(listRef.get(n) instanceof Dog) {
				Dog curDog = (Dog) listRef.get(n);
				Set tempColSet = curDog.getColSet();
				Iterator itr = tempColSet.iterator();
				while(itr.hasNext()) {
					Cat tempCat = (Cat) itr.next();
					curDog.handleCollision((ICollider)tempCat, gw.getObjectList(), gw, this);
				}
			}
		}*/
	}
	
	//Displays Dialog box showing information regarding the program
	public void displayAbout() {
		Dialog.show("About", "Dog Catcher Game\nVersion 1.0\nProgrammer: Tan Dao\nCourse Name: CSC 133", "Ok", null);
	}
	
	
	//Displays Dialog box with a list of all the keybindings
	public void displayHelp() {
		Dialog.show("Keybindings:", "Expand net: 'e' | Contract net: 'c' | Scoop: 's'\nMove net up, down, left, right: 'u', 'd', 'l', 'r'\n"
				+ "Teleport net to random dog's location: 'o'\n Teleport net to random cat's location: 'a'\nSpawn a kitten: 'k'\n"
				+ "Apply a 'tick' to game clock: 't'\nQuit the game: 'q'", "Ok", null);
	}
	
	//Displays a dialog box requesting confirmation for the user to quit the game
	public void displayQuit() {
		Boolean bOk = Dialog.show("Confirm quit", "Are you sure you want to quit?", "Ok", "Cancel");
		if (bOk) {
			Display.getInstance().exitApplication();
		}
	}
	
	//Triggers once all Dogs are captured, ending the game
	public void displayEndGame() {
		bgMusic.pause(); //Stop the background music
		bgEndMusic.play(); //Queue the FF7 Victory Fanfare (^_^)
		Boolean exOk = Dialog.show("Final Score: " + gw.getScore(), "Thanks for playing!", "Exit game", null);
		if (exOk) {
			Display.getInstance().exitApplication();
		}
	}
	
	//Toggle sound Label depending on whether or not the CheckBox is checked
	public void setCheckStatusVal (boolean bVal) {
		if (bVal) {
			sv.setSoundLabel("OFF");
			bgMusic.pause();
			muteCheck = true;
		} else {
			sv.setSoundLabel("ON");
			/*if(pauseCheck = false) {
				bgMusic.play();
			}*/
			muteCheck = false;
			if(!muteCheck && !pauseCheck) {
				bgMusic.play();
			}
		}
	}
	
	//Action Performed method that handles toggling between Pause and Play
	public void actionPerformed (ActionEvent evt) {
		pauseCheck = !pauseCheck;
		if(pauseCheck) { //If game is currently in 'Pause' mode
			playPauseSound();
			buttonPausePlay.setText("Play");
			timer.cancel(); //Stop the timer, halting all Animal movement and repaint() calls
			buttonHeal.setEnabled(true); //Enable the 'Heal' button during 'Pause' mode
			//Disable all left-side and right-side buttons
			buttonExpand.setEnabled(false);
			buttonUp.setEnabled(false);
			buttonDown.setEnabled(false);
			buttonContract.setEnabled(false);
			buttonLeft.setEnabled(false);
			buttonRight.setEnabled(false);
			buttonScoop.setEnabled(false);
			buttonJumpToDog.setEnabled(false);
			buttonJumpToCat.setEnabled(false);
			myScoopCommand.setEnabled(false);
			bgMusic.pause();
			
			//Unbind all keybinds
			removeKeyListener('s', myScoopCommand);
			removeKeyListener('u', myMoveUpCommand);
			removeKeyListener('d', myMoveDownCommand);
			removeKeyListener('l', myMoveLeftCommand);
			removeKeyListener('r', myMoveRightCommand);
			removeKeyListener('e', myExpandCommand);
			removeKeyListener('c', myContractCommand);
			removeKeyListener('o', myJumpToDogCommand);
			removeKeyListener('a', myJumpToCatCommand);
			removeKeyListener('k', mySpawnCatCommand);
			removeKeyListener('f', myCatFightCommand);
			removeKeyListener('t', myTickCommand);
			removeKeyListener('x', myExitCommand);
		} else { //If in 'Play' mode
			buttonPausePlay.setText("Pause  "); //Change Label on Pause/Play button
			timer.schedule(15, true, this); //Re-enable timer, allowing Animal movement and animation
			buttonHeal.setEnabled(false); //Disable 'Heal' button while in 'Play' mode
			//Re-enable left-side and right-side buttons
			buttonExpand.setEnabled(true);
			buttonUp.setEnabled(true);
			buttonDown.setEnabled(true);
			buttonContract.setEnabled(true);
			buttonLeft.setEnabled(true);
			buttonRight.setEnabled(true);
			buttonScoop.setEnabled(true);
			buttonJumpToDog.setEnabled(true);
			buttonJumpToCat.setEnabled(true);
			myScoopCommand.setEnabled(true);
			
			//Only re-enable background music if sound is not muted
			if(muteCheck == false) {
				bgMusic.play();
			}
			
			//Re-enable keybinds
			addKeyListener('s', myScoopCommand);
			addKeyListener('u', myMoveUpCommand);
			addKeyListener('d', myMoveDownCommand);
			addKeyListener('l', myMoveLeftCommand);
			addKeyListener('r', myMoveRightCommand);
			addKeyListener('e', myExpandCommand);
			addKeyListener('c', myContractCommand);
			addKeyListener('o', myJumpToDogCommand);
			addKeyListener('a', myJumpToCatCommand);
			addKeyListener('k', mySpawnCatCommand);
			addKeyListener('f', myCatFightCommand);
			addKeyListener('t', myTickCommand);
			addKeyListener('x', myExitCommand);
			
			//De-select all Dogs
			for(int i = 0; i < gw.getObjectList().size(); i++) {
				if(gw.getObjectList().get(i) instanceof Dog) {
					Dog tempDog = (Dog)gw.getObjectList().get(i);
					tempDog.setSelected(false);
				}
			}
		}
	}
	
	//Only usable while in 'Pause' mode. Removes all ailments from currently selected Dog
	//In other words, restores speed back to 5, removes all scratches, reverts Dog back to it's original color
	public void heal() {
		for(int i = 0; i < gw.getObjectList().size(); i++) {
			if(gw.getObjectList().get(i) instanceof Dog) {
				Dog tempDog = (Dog)gw.getObjectList().get(i);
				if(tempDog.getSelected()) {
					tempDog.healUp();
				}
			}
		}
	}
	
//---------- Sound Effects -----------

	public void playDogBark() {
		if(!muteCheck) {
			dogBarkSound.play();
		}
	}
	
	public void playCatMeow1() {
		if(!muteCheck) {
			catMeowSound1.play();
		}
	}
	
	public void playCatMeow2() {
		if(!muteCheck) {
			catMeowSound2.play();
		}
	}
	
	public void playCatMeow3() {
		if(!muteCheck) {
			catMeowSound3.play();
		}
	}
	
	public void playCatMeow4() {
		if(!muteCheck) {
			catMeowSound4.play();
		}
	}
	
	public void playScoopSound() {
		if(!muteCheck) {
			scoopSound.play();
		}
	}
	
	public void playMoveSound() {
		if(!muteCheck) {
			moveSound.play();
		}
	}
	
	public void playHealSound() {
		if(!muteCheck) {
			healSound.play();
		}
	}
	
	public void playPauseSound() {
		if(!muteCheck) {
			pauseSound.play();
		}
	}
}
