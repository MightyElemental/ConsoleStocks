package net.iridgames.consolestocks.client;

import java.util.HashMap;
import java.util.Map;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.commands.CommandAlias;
import net.iridgames.consolestocks.client.commands.CommandClearScreen;
import net.iridgames.consolestocks.client.commands.CommandConnect;
import net.iridgames.consolestocks.client.commands.CommandDisconnect;
import net.iridgames.consolestocks.client.commands.CommandHelp;
import net.iridgames.consolestocks.client.commands.CommandList;
import net.iridgames.consolestocks.client.commands.CommandNoot;
import net.iridgames.consolestocks.client.commands.CommandSet;

public class LocalCommands {
	
	
	public static CommandLocal setOption = new CommandSet();
	public static CommandLocal help = new CommandHelp();
	public static CommandLocal clear = new CommandClearScreen();
	public static CommandLocal list = new CommandList();
	public static CommandLocal disconnect = new CommandDisconnect();
	public static CommandLocal connect = new CommandConnect();
	public static CommandLocal noot = new CommandNoot();
	public static CommandAlias alias = new CommandAlias();
	
	public static void addTextToConsole(String text) {
		ConsoleStocks.stateGame.console.addText("> " + text);
	}
	
	public static void addCommand(CommandLocal com) {
		commands.put(com.getCommand().toUpperCase(), com);
	}
	
	public static Map<String, CommandLocal> commands = new HashMap<String, CommandLocal>();
	
	public static void setupCommandList() {
		addCommand(setOption);
		addCommand(help);
		addCommand(clear);
		// addCommand(list);
		addCommand(disconnect);
		addCommand(connect);
		addCommand(noot);
		addCommand(alias);
	}
	
}
