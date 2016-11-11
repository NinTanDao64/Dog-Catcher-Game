package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandCatFight extends Command {
	private GameWorld myForm;
	
	public CommandCatFight (GameWorld fForm) {
		super("Fight");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.catFight();
	}
}