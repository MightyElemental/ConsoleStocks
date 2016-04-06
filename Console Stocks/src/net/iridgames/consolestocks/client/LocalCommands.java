package net.iridgames.consolestocks.client;

import java.util.HashMap;
import java.util.Map;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.commands.CommandClearScreen;
import net.iridgames.consolestocks.client.commands.CommandHelp;
import net.iridgames.consolestocks.client.commands.CommandList;
import net.iridgames.consolestocks.client.commands.CommandSet;

public class LocalCommands {
	
	
	public static CommandLocal setOption = new CommandSet();
	public static CommandLocal help = new CommandHelp();
	public static CommandLocal clear = new CommandClearScreen();
	public static CommandLocal list = new CommandList();
	
	public static void addTextToConsole(String text) {
		ConsoleStocks.stateGame.console.addText("> " + text);
	}
	
	public static Map<String, CommandLocal> commands = new HashMap<String, CommandLocal>();
	
	public static void setupCommandList() {
		commands.put(setOption.getCommand().toUpperCase(), setOption);
		commands.put(help.getCommand().toUpperCase(), help);
		commands.put(clear.getCommand().toUpperCase(), clear);
		commands.put(list.getCommand().toUpperCase(), list);
	}
	
}
