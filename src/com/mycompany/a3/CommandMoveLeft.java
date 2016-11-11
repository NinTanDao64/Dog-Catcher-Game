package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandMoveLeft extends Command {
	private GameWorld myForm;
	private Game gameRef;
	
	public CommandMoveLeft (GameWorld fForm, Game game) {
		super("Left");
		myForm = fForm;
		gameRef = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.moveNetLeft();
		gameRef.playMoveSound();
	}
}