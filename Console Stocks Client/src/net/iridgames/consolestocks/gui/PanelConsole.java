package net.iridgames.consolestocks.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.CSClient;
import net.iridgames.consolestocks.Helper;

public class PanelConsole {

	public List<String>	consoleEntries	= new ArrayList<String>();
	private int			entryPos;

	public String			prefix	= "name@server:~$ ";
	public StringBuilder	buffer	= new StringBuilder();
	public int				cursorPos, ticks = 0;

	public long cursorTime;

	private int x, y;

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		int iStart = consoleEntries.size() - (int)((gc.getHeight() / 2f - 40) / 20f) - entryPos;
		while (iStart < 0)
			iStart++;
		for ( int i = iStart; i < consoleEntries.size()
				&& i - iStart < (gc.getHeight() / 2f - 40) / 20f; i++ ) {
			Helper.drawString(consoleEntries.get(i), x + 5, y + 5);
			y += 20;
		}
		renderInput(gc, sbg, g, x + 5, y);
	}

	public void renderInput(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		g.drawString(prefix + buffer.toString(), x, y + 5);
		drawCursor(gc, sbg, g, x + g.getFont().getWidth(prefix), y + 5);
	}

	public void drawCursor(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		if ( cursorTime + 500 < System.currentTimeMillis() ) {
			g.drawString("|", x + 1 + g.getFont().getWidth(buffer.toString().substring(0, cursorPos) + "|"),
					y);
		}
		if ( cursorTime + 1000 < System.currentTimeMillis() ) cursorTime = System.currentTimeMillis();
		g.drawString("Cursor Pos: " + cursorPos, 500, 500);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		updateInput(gc, sbg, delta);
		ticks++;
	}

	public void updateInput(GameContainer gc, StateBasedGame sbg, int delta) {
		if ( (CSClient.userInterface.pressedChar != 0
				&& CSClient.userInterface.keyPressedTime + 500 < System.currentTimeMillis()
				&& ticks % 2 == 0) ) {
			processInput();
		}
	}

	public void processInput() {
		if ( CSClient.userInterface.pressedKey == Input.KEY_BACK ) backspace();
		if ( CSClient.userInterface.pressedKey == Input.KEY_ENTER ) submitConsoleEntry();
		addBuffer();
	}

	private void backspace() {
		cursorTime = System.currentTimeMillis() - 500;
		if ( cursorPos > 0 ) {
			buffer.deleteCharAt(cursorPos - 1);
			cursorPos--;
		}
	}

	private void addBuffer() {
		String append = (CSClient.userInterface.pressedChar + "")
				.replaceAll("[^A-Za-z0-9 -_+=./|\\;:\"'`~!@#$%^&*(){}]", "");
		buffer.append(append);
		if ( append.length() > 0 ) cursorPos++;
	}

	private void submitConsoleEntry() {
		consoleEntries.add(prefix + (buffer.toString().replace("<", "\u300C").replace(">", "\u300D")));
		buffer.delete(0, buffer.length());
		cursorPos = 0;
	}

}
