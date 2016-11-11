package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandJumpToCat extends Command {
	private GameWorld myForm;
	
	public CommandJumpToCat (GameWorld fForm) {
		super("JumpToACat");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.jumpToCat();
	}
}