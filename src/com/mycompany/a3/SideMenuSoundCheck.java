package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.CheckBox;
import com.codename1.ui.events.ActionEvent;

public class SideMenuSoundCheck extends Command {
	private Game myForm;
	
	public SideMenuSoundCheck (Game fForm) {
		super("Mute Sound");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed (ActionEvent evt) {
		if (((CheckBox)evt.getComponent()).isSelected())
			myForm.setCheckStatusVal(true);
		else
			myForm.setCheckStatusVal(false);
		SideMenuBar.closeCurrentMenu();
	}
}
