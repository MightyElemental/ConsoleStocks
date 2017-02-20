package net.iridgames.consolestocks.client.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.Helper;
import net.iridgames.consolestocks.ResourceLoader;
import net.iridgames.consolestocks.client.LocalCommands;
import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.server.Commands;

public class Console {
	
	
	public int keyCodePressed = -1;
	public long keyPressedTime = -1;
	public char keyChar;
	
	public ArrayList<String> commands = new ArrayList<String>();
	public ArrayList<String> console = new ArrayList<String>();
	
	public String prefix = "root@local> ";
	public int flashSpeed = 40;
	public char cursorSymbol = '|';
	public boolean displayCursor = false;
	public String commandLine = "";
	public String dispCommandLine = prefix;
	public int cursor = 0;
	public int pastComCur = 0;
	public int commandViewOffset = 0;
	
	public boolean lControlDown = false;
	
	private boolean keySoundPlay = false;
	private boolean deleteSoundPlay = false;
	
	private void handleSoundEffects() {
		if (keySoundPlay) {
			ResourceLoader.loadSound("type_0").play(1.9f + (ConsoleStocks.rand.nextFloat() / 2f), 0.5f);
			keySoundPlay = false;
		}
		if (deleteSoundPlay) {
			ResourceLoader.loadSound("type_1").play(1.2f + (ConsoleStocks.rand.nextFloat() / 5f), 0.5f);
			deleteSoundPlay = false;
		}
	}
	
	public void renderConsole(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		if (commandViewOffset < 0) {
			commandViewOffset = 0;
		}
		if (commandViewOffset > console.size() - 20) {
			commandViewOffset = console.size() - 20;
		}
		int tempYPast = 5;
		int tempY = tempYPast;
		int iStart = console.size() - 20 - commandViewOffset;
		if (iStart < 0) {
			iStart = 0;
		}
		int iStop = console.size();
		for (int i = iStart; i < iStop; i++) {
			if (!console.isEmpty()) {
				
				if (console.get(i) == null) {
					continue;
				}
				
				int n = 85;
				char[] chars = new char[n];
				Arrays.fill(chars, '.');
				String result = new String(chars);
				
				String[] command = console.get(i).split("(?<=\\G" + result + ")");
				
				Helper.drawString(g, strJoin(command, "\n").toString(), x + 10, y + tempYPast + (20 * (i - iStart)));
				// g.drawString(strJoin(command, "\n").toString(), x + 10, y + tempYPast + (20 * (i - iStart)));
				tempYPast += ((command.length - 1) * 20);
				tempY = (20 * (iStop - iStart)) + tempYPast;
			}
		}
		
		int n = 85;
		char[] chars = new char[n];
		Arrays.fill(chars, '.');
		String result = new String(chars);
		
		String[] command = dispCommandLine.split("(?<=\\G" + result + ")");
		
		// Helper.drawString(g, strJoin(command, "\n").toString(), x + 10, y + tempY);
		g.drawString(strJoin(command, "\n").toString(), x + 10, y + tempY);
		drawCursor(g, x + 5, y + tempY);
	}
	
	private void drawCursor(Graphics g, float x, float y) {
		if (displayCursor) {
			char[] chars = new char[cursor + prefix.length()];
			Arrays.fill(chars, ' ');
			String spaces = new String(chars);
			g.drawString(spaces + cursorSymbol, x, y);
		}
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
		
		if (ticks % flashSpeed > flashSpeed / 2 && keyCodePressed != Input.KEY_LEFT && keyCodePressed != Input.KEY_RIGHT) {
			dispCommandLine = prefix + commandLine;
			displayCursor = false;
			return;
		}
		displayCursor = true;
		StringBuilder sb = new StringBuilder(commandLine);
		try {
			// sb.deleteCharAt(cursor);
		} catch (Exception e) {
		}
		// sb.insert(cursor, cursorSymbol);
		dispCommandLine = prefix + sb.toString();
	}
	
	public void processCommandLineInput() {
		StringBuilder sb = new StringBuilder(commandLine);
		if (ConsoleStocks.client == null) {
			localMode = true;
		}
		if (keyCodePressed == Input.KEY_TAB) {
			if (localMode) {
				for (String k : LocalCommands.commands.keySet()) {
					if (k.startsWith(commandLine.toUpperCase())) {
						sb.delete(0, sb.length());
						commandLine = k;
						sb.append(k);
						cursor = k.length();
					}
				}
			} else {
				for (String k : Commands.commands.keySet()) {
					if (k.startsWith(commandLine.toUpperCase())) {
						sb.delete(0, sb.length());
						commandLine = k;
						sb.append(k);
						cursor = k.length();
					}
				}
			}
		}
		if (keyCodePressed == Input.KEY_BACK) {
			if (cursor - 1 >= 0) {
				sb.deleteCharAt(cursor - 1);
				cursor--;
			}
			updateCursor(sb.length());
			deleteSoundPlay = true;// TODO remove xD
		}
		if (keyCodePressed == Input.KEY_DELETE) {
			if (cursor < sb.length()) {
				sb.deleteCharAt(cursor);
			}
			updateCursor(sb.length());
			deleteSoundPlay = true;// TODO remove xD
		}
		if (keyCodePressed != Input.KEY_LEFT && keyCodePressed != Input.KEY_RIGHT && keyCodePressed != Input.KEY_BACK
			&& keyCodePressed != Input.KEY_DELETE) {
			String c = (keyChar + "").replaceAll("[^A-Za-z0-9 -_+=./|\\;:\"'`~!@#$%^&*(){}]", "");
			sb.insert(cursor, c);
			cursor += c.length();
			updateCursor(sb.length());
			keySoundPlay = true;// TODO remove xD
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
			if (lControlDown) {
				this.commandViewOffset++;
			} else {
				if (commands.size() > 0) {
					if (commands.size() - 1 - pastComCur < 0) {
						pastComCur = commands.size() - 1;
					}
					sb.delete(0, sb.length());
					sb.append(commands.get(commands.size() - 1 - pastComCur));
					pastComCur++;
				}
				cursor = sb.length();
			}
		}
		if (keyCodePressed == Input.KEY_DOWN) {
			if (lControlDown) {
				this.commandViewOffset--;
			} else {
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
		}
		
		if (keyCodePressed == Input.KEY_ENTER) {
			StringBuilder s = new StringBuilder(sb);
			// remove unwanted spaces
			while ((s.toString().startsWith(" ") || s.toString().startsWith(";")) && s.length() > 0) {
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
		
		if (!lControlDown) {
			this.commandViewOffset = 0;
		}
		
		commandLine = sb.toString();
		// GO AT END
		
		dispCommandLine = prefix + commandLine.toString();
		
		handleSoundEffects();
	}
	
	public boolean localMode = false;
	
	/** Used to filter out local commands so that the server does not receive any */
	private void processCommand(String s) throws IOException {
		updatePrefix();
		if (ConsoleStocks.client == null || !ConsoleStocks.client.isRunning()) {
			localMode = true;
		}
		ArrayList<ArrayList<String>> list = Common.interpretCommandLine(s);
		
		// aliases
		for (int i = 0; i < list.size(); i++) {
			if (LocalCommands.alias.getAliases().containsKey(list.get(i).get(0).toUpperCase())) {
				String value = LocalCommands.alias.getAliases().get(list.get(i).get(0).toUpperCase());
				ArrayList<String> alias = Common.interpretCommandLine(value).get(0);
				list.get(i).remove(0);
				list.get(i).addAll(0, alias);
			}
		}
		if (LocalCommands.canRunOnServerMode(list.get(0).get(0))) {
			int x = 0;
			if (!localMode) {
				list.get(0).add(0, "local");
				x = 1;
			}
			LocalCommands.commands.get(list.get(0).get(x).toUpperCase()).run(list.get(0));
		} else {
			processCommands(list, s);
		}
		// if (list.get(0).get(0).equalsIgnoreCase(LocalCommands.disconnect.getCommand())) {
		// LocalCommands.disconnect.run(list.get(0));
		// } else {
		// processCommands(list, s);
		// }
		updatePrefix();
	}
	
	private void processCommands(ArrayList<ArrayList<String>> list, String s) throws IOException {
		boolean flag = false;
		// Clear Screen
		if (list.get(0).get(0).equalsIgnoreCase(LocalCommands.clear.getCommand())) {
			list.get(0).clear();
			list.get(0).add("local");
			list.get(0).add("cls");
		}
		
		// Run single local command
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
		// Run server command
		if (!localMode && !flag && ConsoleStocks.client != null) {
			if (ConsoleStocks.client.isRunning()) {
				ConsoleStocks.client.sendObject("Command", s);
			}
		}
		// Run local command while in local mode
		if (localMode && !flag) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).add(0, "local");
				processLocalCommands(list.get(i));
			}
		}
	}
	
	private void processLocalCommands(ArrayList<String> command) {
		if (command.get(0).equalsIgnoreCase("local")) {
			for (String s : LocalCommands.commands.keySet()) {
				if (command.get(1).equalsIgnoreCase(s) || LocalCommands.commands.get(s).getAlias().contains(command.get(1))) {
					LocalCommands.commands.get(s).run(command);
					return;
				}
			}
			addText("alert{Invalid Command.}");
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
		if (ConsoleStocks.client == null || !ConsoleStocks.client.isRunning()) {
			prefix = "root@local> ";
			return;
		}
		int maxPre = 7;
		if (ConsoleStocks.client.serverInfo == null) {
			prefix = ConsoleStocks.client.getUID() + "@local> ";
			return;
		}
		String s = ConsoleStocks.client.serverInfo.get("ServerName").toString().replace(" ", "");
		s = s.replace(";", "").replace("!", "").replace("\"", "").replace("\\", "").replace("/", "").replace(",", "");
		StringBuilder temp = new StringBuilder(s);
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
		if (text != null) {
			console.add(text);
		}
	}
	
	Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	public void onPaste() {
		
		Transferable t = c.getContents(this);
		if (t == null) { return; }
		try {
			String p = (String) t.getTransferData(DataFlavor.stringFlavor);
			commandLine += p;
			dispCommandLine += p;
			cursor += p.length();
		} catch (Exception e) {
			e.printStackTrace();
		} // try
	}
	
	public void onCopy() {
		StringSelection stringSelection = new StringSelection(commandLine);
		c.setContents(stringSelection, null);
	}
	
	/** When the client has been dropped from the server or the server has been closed, this method will be called. */
	public void onClientDropped(String reason) {
		updatePrefix();
		ConsoleStocks.stateGame.console.addText("Your client has been alert{dropped by the server}");
		dispCommandLine = prefix + commandLine;
	}
	
	public void onHomeKey() {
		cursor = 0;
	}
	
	public void onEndKey() {
		cursor = commandLine.length();
	}
	
}
