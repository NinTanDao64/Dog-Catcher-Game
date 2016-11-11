package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandContract extends Command {
	private GameWorld myForm;
	
	public CommandContract (GameWorld fForm) {
		super("Contract");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.contract();
	}
}