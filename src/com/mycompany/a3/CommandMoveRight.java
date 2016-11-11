package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandMoveRight extends Command {
	private GameWorld myForm;
	private Game gameRef;
	
	public CommandMoveRight (GameWorld fForm, Game game) {
		super("Right");
		myForm = fForm;
		gameRef = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.moveNetRight();
		gameRef.playMoveSound();
	}
}