package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandScoop extends Command {
	private GameWorld myForm;
	private Game gameRef;
	
	public CommandScoop (GameWorld fForm, Game game) {
		super("Scoop");
		myForm = fForm;
		gameRef = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.scoop();
		gameRef.playScoopSound();
		if(myForm.getDogCount() == 0) {
			gameRef.displayEndGame();
		}
	}
}
