package net.iridgames.consolestocks.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class PanelConsole {

	public List<String> consoleEntries = new ArrayList<String>();

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws SlickException {
		for (int i = 0; i < consoleEntries.size() && i < (gc.getHeight() / 2f - 10) / 17f; i++) {
			g.drawString(consoleEntries.get(i), x + 5, y + 5 + (i * 17));
		}
	}

}
