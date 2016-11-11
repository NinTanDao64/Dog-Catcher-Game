package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandMoveDown extends Command {
	private GameWorld myForm;
	private Game gameRef;
	
	public CommandMoveDown (GameWorld fForm, Game game) {
		super("Down");
		myForm = fForm;
		gameRef = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.moveNetDown();
		gameRef.playMoveSound();
	}
}