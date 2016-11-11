package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandQuit extends Command {
	private Game myForm;
	
	public CommandQuit (Game fForm) {
		super("Exit");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.displayQuit();
	}
}