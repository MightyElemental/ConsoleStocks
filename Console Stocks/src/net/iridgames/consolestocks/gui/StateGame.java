package net.iridgames.consolestocks.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;

public class StateGame extends BasicGameState {

	private final int ID;

	public int	keyCodePressed	= -1;
	public long	keyPressedTime	= -1;
	public char	keyChar;

	public ArrayList<String> commands = new ArrayList<String>();

	public String	prefix			= "$ ";
	public int		flashSpeed		= 40;
	public char		cursorSymbol	= '_';
	public String	commandLine		= "";
	public String	dispCommandLine	= prefix;
	public int		cursor			= 0;

	public StateGame( int id ) {
		this.ID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		this.renderBL(gc, sbg, g);
		this.renderBR(gc, sbg, g);
		this.renderTL(gc, sbg, g);
		this.renderTR(gc, sbg, g);
		g.setColor(Color.white);
		int w = gc.getWidth() / 2;
		int h = gc.getHeight() / 2;
		g.drawRect(0, 0, gc.getWidth() / 2, gc.getHeight() / 2);
		g.drawRect(w - 1, 0, gc.getWidth() / 2, gc.getHeight() / 2);
		g.drawRect(0, h - 1, gc.getWidth() / 2, gc.getHeight() / 2);
		g.drawRect(w - 1, h - 1, gc.getWidth() / 2, gc.getHeight() / 2);
	}

	public void renderTL(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = 0;
		final int yDisp = 0;
		if (keyCodePressed >= 0) {
			g.drawString(Input.getKeyName(keyCodePressed), xDisp + 10, yDisp + 10);
		}
	}

	public void renderTR(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = gc.getWidth() / 2;
		final int yDisp = 0;
	}

	public void renderBR(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = gc.getWidth() / 2;
		final int yDisp = gc.getHeight() / 2;
	}

	public void renderBL(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = 0;
		final int yDisp = gc.getHeight() / 2;
		renderConsole(gc, sbg, g, xDisp, yDisp);
	}

	public void renderConsole(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		int tempY = 0;
		for (int i = commands.size() - 1; i >= 0; i--) {//max = 21
			if (!commands.isEmpty()) {
				if (commands.get(i) != null) {
					g.drawString(commands.get(i), x + 10, y + (20 * i));
				}
				tempY = commands.size()*20;
			}
		}
		g.drawString(dispCommandLine + "\n" + commandLine + ".\n" + cursor + "\n" + commandLine.length(), x + 10, y + tempY);
	}

	private float ticks;

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		ticks += delta / 17;
		updateCommandLine();
	}

	public void updateCommandLine() {
		if (keyCodePressed >= 0) {
			if (keyPressedTime + 500 < System.currentTimeMillis()) {
				if (ticks % 2 == 0) {
					processCommandLineInput();
				}
			}
		}
		flashCursor();
	}

	private void flashCursor() {

		if (ticks % flashSpeed > flashSpeed / 2) {
			dispCommandLine = prefix + commandLine;
			return;
		}
		StringBuilder sb = new StringBuilder(commandLine);
		try {
			sb.deleteCharAt(cursor);
		} catch (Exception e) {
		}

		sb.insert(cursor, cursorSymbol);
		dispCommandLine = prefix + sb.toString();
	}

	public void processCommandLineInput() {
		StringBuilder sb = new StringBuilder(commandLine);
		if (keyCodePressed == Input.KEY_BACK) {
			if (cursor - 1 >= 0) {
				sb.deleteCharAt(cursor - 1);
				cursor--;
			}
			updateCursor(sb.length());
		}
		if (keyCodePressed == Input.KEY_DELETE) {
			if (cursor < sb.length()) {
				sb.deleteCharAt(cursor);
			}
			updateCursor(sb.length());
		}
		if (keyCodePressed != Input.KEY_LEFT && keyCodePressed != Input.KEY_RIGHT && keyCodePressed != Input.KEY_BACK
				&& keyCodePressed != Input.KEY_DELETE) {
			sb.insert(cursor, (keyChar + "").replaceAll("[^A-Za-z0-9 -_+=./|\\;:\"'`~!@#$%^&*(){}]", ""));
			cursor++;
			updateCursor(sb.length());
		}
		if (keyCodePressed == Input.KEY_LEFT) {
			cursor--;
			updateCursor(sb.length());
		}
		if (keyCodePressed == Input.KEY_RIGHT) {
			cursor++;
			updateCursor(sb.length());
		}
		if (keyCodePressed == Input.KEY_ENTER) {
			ConsoleStocks.client.sendMessage(sb.toString());
			commands.add(sb.toString());
			cursor = 0;
			sb.delete(0, sb.length());
			updateCursor(sb.length());
		}
		commandLine = sb.toString();
		// GO AT END
		dispCommandLine = prefix + sb.toString();
	}

	public void updateCursor(int length) {
		if (cursor < 0) {
			cursor = 0;
		}
		if (cursor > commandLine.length()) {
			cursor = length;
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		keyCodePressed = key;
		keyPressedTime = System.currentTimeMillis();
		keyChar = c;
		processCommandLineInput();
	}

	@Override
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		keyCodePressed = -1;
		keyPressedTime = -1;
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// TODO Auto-generated method stub
		super.mouseClicked(button, x, y, clickCount);
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		super.mouseReleased(button, x, y);
	}

	@Override
	public void mouseWheelMoved(int newValue) {
		// TODO Auto-generated method stub
		super.mouseWheelMoved(newValue);
	}

}
