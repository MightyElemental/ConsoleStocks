package net.iridgames.consolestocks.gui;

public class ConsoleDrawInfo {

	private float	curX, curY;
	private int		newL;

	public ConsoleDrawInfo(float cursorX, float cursorY, int newLines) {
		this(newLines);
		curX = cursorX;
		curY = cursorY;
	}

	public ConsoleDrawInfo(int newLines) {
		newL = newLines;
	}

	public float getCursorX() {
		return curX;
	}

	public float getCursorY() {
		return curY;
	}

	public int getNumNewLines() {
		return newL;
	}

}
