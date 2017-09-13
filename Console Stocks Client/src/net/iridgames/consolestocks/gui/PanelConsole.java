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
import net.iridgames.consolestocks.Settings;

public class PanelConsole {

	private List<String>		userInputs			= new ArrayList<String>();
	private List<String>		consoleBuffer		= new ArrayList<String>();
	private List<List<String>>	displayedEntries	= new ArrayList<List<String>>();
	private int					entryPos;

	public String			prefix	= "name@server:~$ ";
	public StringBuilder	buffer	= new StringBuilder();
	public int				cursorPos, ticks = 0;

	public long cursorTime;

	public void addEntry(String s) {
		consoleBuffer.add(s);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws SlickException {
		renderConsoleEntries(gc, sbg, g, x, y);
	}

	public void renderConsoleEntries(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		int currentLines = 0;
		int height = (int) Math
				.floor((gc.getHeight() / 2f - Settings.newLineSpace * 1.5f) / (float) Settings.newLineSpace);
		int iStart = displayedEntries.size() - height - entryPos;
		if ( iStart < 0 ) iStart = 0;
		currentLines += Helper.getHeightLines(prefix + buffer.toString());
		for ( int i = iStart; i < displayedEntries.size(); i++ ) {
			Helper.drawProcessedString(displayedEntries.get(i), x + 5, y + 5);
			currentLines++;
			y += Settings.newLineSpace;
		}
		entryPos = height - currentLines + 2;
		renderInput(gc, sbg, g, x + 5, y);
	}

	public void renderInput(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		ConsoleDrawInfo cdi = Helper.drawConsoleInput(prefix + buffer.toString(), x, y + 5);
		// drawCursor(gc, sbg, g, x + g.getFont().getWidth(prefix), y + 5);
		drawCursor(gc, sbg, g, cdi.getCursorX(), cdi.getCursorY());
	}

	public void drawCursor(GameContainer gc, StateBasedGame sbg, Graphics g, float x, float y) {
		if ( cursorTime + 500 < System.currentTimeMillis() ) {
			g.drawString("|", x - 5, y);
		}
		if ( cursorTime + 1000 < System.currentTimeMillis() ) cursorTime = System.currentTimeMillis();
		g.drawString("Cursor Pos: " + cursorPos, 500, 500);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		List<String> temp = new ArrayList<String>();
		temp.addAll(consoleBuffer);
		for ( String s : temp ) {
			displayedEntries.addAll(Helper.formatLine(s));
		}
		consoleBuffer.clear();
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
		String s = buffer.toString().replace("<", "\u300C").replace(">", "\u300D").replace("{", "\u3014")
				.replace("}", "\u3015");
		if ( buffer.toString().equals("CLEAR") ) {
			displayedEntries.clear();
		} else {
			addEntry(prefix + s);
			CSClient.client.send(s);
		}
		userInputs.add(s);
		buffer.delete(0, buffer.length());
		cursorPos = 0;
	}

}
