package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.CommandLocal;
import net.iridgames.consolestocks.client.LocalCommands;

public class CommandHelp extends CommandLocal {
	
	
	public CommandHelp() {
		super("help");
	}
	
	@Override
	public void run(ArrayList<String> args) {
		if (args.size() < 3) {
			for (CommandLocal c : LocalCommands.commands.values()) {
				if (!c.isCommandHidden()) {
					LocalCommands.addTextToConsole(c.getCommand());
					for (int i = 0; i < c.getAlias().size(); i++) {
						String a = c.getAlias().get(i);
						if (a != null) {
							LocalCommands.addTextToConsole(a);
						}
					}
				}
			}
			return;
		}
		try {
			CommandLocal toUse = null;
			for (String s : LocalCommands.commands.keySet()) {
				if (args.get(2).equalsIgnoreCase(s) || LocalCommands.commands.get(s).getAlias().contains(args.get(2))) {
					toUse = LocalCommands.commands.get(s);
					break;
				}
			}
			
			String usage = toUse.getUsage();
			String desc = toUse.getDescription();
			LocalCommands.addTextToConsole(desc);
			LocalCommands.addTextToConsole("Usage: " + usage);
		} catch (NullPointerException e) {
			LocalCommands.addTextToConsole(ConsoleStocks.ERROR_INVALID_COMMAND);
		}
		
	}
	
	@Override
	public String getUsage() {
		return "help <command>";
	}
	
	@Override
	public String getDescription() {
		return "Use to get information about commands or get the list of usable commands";
	}
	
}
