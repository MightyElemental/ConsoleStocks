package net.iridgames.consolestocks.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.LocalCommands;
import net.iridgames.consolestocks.common.Common;

public class Console {
	
	
	public int keyCodePressed = -1;
	public long keyPressedTime = -1;
	public char keyChar;
	
	public ArrayList<String> commands = new ArrayList<String>();
	public ArrayList<String> console = new ArrayList<String>();
	
	public String prefix = "abcd@local> ";
	public int flashSpeed = 40;
	public char cursorSymbol = '_';
	public String commandLine = "";
	public String dispCommandLine = prefix;
	public int cursor = 0;
	public int pastComCur = 0;
	
	public void renderConsole(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		int tempY = 0;
		int iStart = console.size() - 20;
		int tempYPast = 0;
		if (iStart < 0) {
			iStart = 0;
		}
		int iStop = console.size();
		for (int i = iStart; i < iStop; i++) {
			if (!console.isEmpty()) {
				
				int n = 85;
				char[] chars = new char[n];
				Arrays.fill(chars, '.');
				String result = new String(chars);
				
				String[] command = console.get(i).split("(?<=\\G" + result + ")");
				
				g.drawString(strJoin(command, "\n").toString(), x + 10, y + tempYPast + (20 * (i - iStart)));
				tempYPast += ((command.length - 1) * 20);
				tempY = (20 * (iStop - iStart)) + tempYPast;
			}
		}
		
		int n = 85;
		char[] chars = new char[n];
		Arrays.fill(chars, '.');
		String result = new String(chars);
		
		String[] command = dispCommandLine.split("(?<=\\G" + result + ")");
		
		g.drawString(strJoin(command, "\n").toString(), x + 10, y + tempY);
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
			StringBuilder s = new StringBuilder(sb);
			// remove unwanted spaces
			while (s.toString().startsWith(" ") && s.length() > 0) {
				s.deleteCharAt(0);
				if (s.length() <= 0) {
					break;
				}
			}
			// Send Message
			if (s.length() > 0) {
				commands.add(sb.toString());
				addText(prefix + sb.toString());
				cursor = 0;
				sb.delete(0, sb.length());
				updateCursor(sb.length());
				pastComCur = 0;
				try {
					processCommand(s.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		commandLine = sb.toString();
		// GO AT END
		
		dispCommandLine = prefix + commandLine.toString();
	}
	
	private boolean localMode = false;
	
	/** Used to filter out local commands so that the server does not receive any */
	private void processCommand(String s) throws IOException {
		updatePrefix();
		if (ConsoleStocks.client == null) {
			localMode = true;
		}
		ArrayList<ArrayList<String>> list = Common.interpretCommandLine(s);
		boolean flag = false;
		if (list.get(0).get(0).equalsIgnoreCase("local")) {
			flag = true;
			if (list.get(0).size() == 1) {
				localMode = !localMode;
			} else {
				for (int i = 0; i < list.size(); i++) {
					processLocalCommands(list.get(i));
				}
			}
		}
		if (!localMode && !flag && ConsoleStocks.client != null) {
			ConsoleStocks.client.sendObject("Command", s);
		}
		if (localMode && !flag) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).add(0, "local");
				processLocalCommands(list.get(i));
				System.out.println(list);
			}
		}
		updatePrefix();
	}
	
	private void processLocalCommands(ArrayList<String> command) {
		if (command.get(0).equalsIgnoreCase("local")) {
			for (String s : LocalCommands.commands.keySet()) {
				if (command.get(1).equalsIgnoreCase(s) || LocalCommands.commands.get(s).getAlias().contains(command.get(1))) {
					LocalCommands.commands.get(s).run(command);
					return;
				}
			}
			addText("Invalid Command.");
		}
	}
	
	public static String strJoin(String[] aArr, String sSep) {
		StringBuilder sbStr = new StringBuilder();
		for (int i = 0, il = aArr.length; i < il; i++) {
			if (i > 0) sbStr.append(sSep);
			sbStr.append(aArr[i]);
		}
		return sbStr.toString();
	}
	
	public void updateCursor(int length) {
		if (cursor < 0) {
			cursor = 0;
		}
		if (cursor > commandLine.length()) {
			cursor = length;
		}
	}
	
	public void updatePrefix() {
		if (ConsoleStocks.client == null) {
			prefix = "abcd@local> ";
			return;
		}
		int maxPre = 7;
		StringBuilder temp = new StringBuilder(ConsoleStocks.client.serverInfo.get("ServerName").toString().replace(" ", ""));
		if (temp.length() > maxPre) {
			temp.delete(maxPre, temp.length());
		}
		if (localMode) {
			prefix = ConsoleStocks.client.getUID() + "@local> ";
		} else {
			prefix = ConsoleStocks.client.getUID() + "@" + temp.toString() + ">> ";
		}
	}
	
	public void addText(String text) {
		console.add(text);
	}
	
}
