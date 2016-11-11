package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandHelp extends Command {
	private Game myForm;
	
	public CommandHelp (Game fForm) {
		super("Help?");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.displayHelp();
	}
}
