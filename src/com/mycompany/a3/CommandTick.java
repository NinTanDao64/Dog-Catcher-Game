package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandTick extends Command {
	private GameWorld myForm;
	
	public CommandTick (GameWorld fForm) {
		super("Tick");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.tick();
	}
}
