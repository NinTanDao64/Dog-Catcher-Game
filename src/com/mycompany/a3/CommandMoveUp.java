package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandMoveUp extends Command {
	private GameWorld myForm;
	Game gameRef;
	
	public CommandMoveUp (GameWorld fForm, Game game) {
		super("Up");
		myForm = fForm;
		gameRef = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.moveNetUp();
		gameRef.playMoveSound();
	}
}