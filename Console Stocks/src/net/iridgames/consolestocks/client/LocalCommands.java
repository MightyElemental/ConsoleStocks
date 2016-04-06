package net.iridgames.consolestocks.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.commands.CommandClearScreen;
import net.iridgames.consolestocks.client.commands.CommandHelp;
import net.iridgames.consolestocks.client.commands.CommandList;
import net.iridgames.consolestocks.client.commands.CommandSet;
import net.iridgames.consolestocks.common.Command;

public class LocalCommands {
	
	
	public static Command setOption = new CommandSet();
	public static Command help = new CommandHelp();
	public static Command clear = new CommandClearScreen();
	public static Command list = new CommandList();
	
	public static void addTextToConsole(String text) {
		ConsoleStocks.stateGame.console.addText("> " + text);
	}
	
	public static Map<String, Command> commands = new HashMap<String, Command>();
	
	public static void setupCommandList() {
		commands.put(setOption.getCommand().toUpperCase(), setOption);
		commands.put(help.getCommand().toUpperCase(), help);
		commands.put(clear.getCommand().toUpperCase(), clear);
		commands.put(list.getCommand().toUpperCase(), list);
	}
	
	public static void listCommands(ArrayList<String> command) {
		for (Command c : commands.values()) {
			addTextToConsole(c.getCommand());
		}
	}
	
}
