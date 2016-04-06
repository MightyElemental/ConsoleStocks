package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;

import net.iridgames.consolestocks.client.CommandLocal;
import net.iridgames.consolestocks.client.LocalCommands;

public class CommandHelp extends CommandLocal {
	
	
	public CommandHelp() {
		super("help");
	}
	
	@Override
	public void run(ArrayList<String> args) {
		if (args.size() < 3) {
			LocalCommands.addTextToConsole("Usage: " + getUsage());
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
			LocalCommands.addTextToConsole("Invalid Command.");
		}
		
	}
	
	@Override
	public String getUsage() {
		return "help <command>";
	}
	
	@Override
	public String getDescription() {
		return "Use to get information about commands";
	}
	
}
