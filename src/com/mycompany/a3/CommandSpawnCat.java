package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandSpawnCat extends Command {
	private GameWorld myForm;
	
	public CommandSpawnCat (GameWorld fForm) {
		super("Kitten");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.spawnCat();
	}
}