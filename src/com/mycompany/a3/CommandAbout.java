package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandAbout extends Command {
	private Game myForm;
	
	public CommandAbout (Game fForm) {
		super("About");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.displayAbout();
	}
}