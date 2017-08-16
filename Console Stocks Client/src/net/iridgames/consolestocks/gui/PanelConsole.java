package net.iridgames.consolestocks.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.CSClient;
import net.iridgames.consolestocks.Helper;

public class PanelConsole {

	public List<String> consoleEntries = new ArrayList<String>();

	public String			prefix	= "name@server:~$ ";
	public StringBuilder	buffer	= new StringBuilder();

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws SlickException {
		for (int i = 0; i < consoleEntries.size() && i < (gc.getHeight() / 2f - 27) / 17f; i++) {
			Helper.drawString(consoleEntries.get(i), x + 5, y + 5);
			y += 17;
		}
		renderInput(gc, sbg, g, x + 5, y);
	}

	public void renderInput(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		g.drawString(prefix + buffer.toString(), x, y);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		"[^A-Za-z0-9 -_+=./|\\;:\"'`~!@#$%^&*(){}]".length();
		if (CSClient.userInterface.pressedKey != 0
				&& CSClient.userInterface.keyPressedTime + 500 < System.currentTimeMillis()) {
			
		}
	}

}
