package net.iridgames.consolestocks.gui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;

public class Console {

	public int	keyCodePressed	= -1;
	public long	keyPressedTime	= -1;
	public char	keyChar;

	public ArrayList<String>	commands	= new ArrayList<String>();
	public ArrayList<String>	console		= new ArrayList<String>();

	public String	prefix			= "$ ";
	public int		flashSpeed		= 40;
	public char		cursorSymbol	= '_';
	public String	commandLine		= "";
	public String	dispCommandLine	= prefix;
	public int		cursor			= 0;
	public int		pastComCur		= 0;

	public void renderConsole(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		int tempY = 0;
		int iStart = console.size() - 20;
		if (iStart < 0) {
			iStart = 0;
		}
		int iStop = console.size();
		for (int i = iStart; i < iStop; i++) {
			if (!console.isEmpty()) {
				g.drawString(console.get(i), x + 10, y + (20 * (i - iStart)));
				tempY = (20 * (iStop - iStart));
			}
		}
		g.drawString(dispCommandLine, x + 10, y + tempY);
	}

	public void updateCommandLine(float ticks) {
		if (keyCodePressed >= 0) {
			if (keyPressedTime + 500 < System.currentTimeMillis()) {
				if (ticks % 2 == 0) {
					processCommandLineInput();
				}
			}
		}
		flashCursor(ticks);
	}

	private void flashCursor(float ticks) {

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
		if (keyCodePressed == Input.KEY_UP) {
			if (commands.size() > 0) {
				pastComCur++;
				if (commands.size() - 1 - pastComCur < 0) {
					pastComCur = commands.size() - 1;
				}
				sb.delete(0, sb.length());
				sb.append(commands.get(commands.size() - 1 - pastComCur));
			}
			cursor = sb.length();
		}
		if (keyCodePressed == Input.KEY_DOWN) {
			if (commands.size() > 0) {
				pastComCur--;
				if (commands.size() - 1 - pastComCur > commands.size() - 1) {
					pastComCur = 0;
				}
				sb.delete(0, sb.length());
				sb.append(commands.get(commands.size() - 1 - pastComCur));
			}
			cursor = sb.length();
		}

		if (keyCodePressed == Input.KEY_ENTER) {
			ConsoleStocks.client.sendMessage(sb.toString());
			commands.add(sb.toString());
			addText(prefix + sb.toString());
			cursor = 0;
			sb.delete(0, sb.length());
			updateCursor(sb.length());
			pastComCur = 0;
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

	public void addText(String text) {
		console.add(text);
	}

}
