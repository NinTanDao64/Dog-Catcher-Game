package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandJumpToDog extends Command {
	private GameWorld myForm;
	
	public CommandJumpToDog (GameWorld fForm) {
		super("JumpToADog");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.jumpToDog();
	}
}