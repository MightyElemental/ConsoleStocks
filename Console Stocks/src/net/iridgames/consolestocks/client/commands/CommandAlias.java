package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.iridgames.consolestocks.client.CommandLocal;

public class CommandAlias extends CommandLocal {
	
	
	/** This is not aliases for this command. */
	protected Map<String, String> aliases = new HashMap<String, String>();
	
	public CommandAlias() {
		super("alias");
		this.setDescription("Use to define a new command");
	}
	
	public String getUsage() {
		return this.getCommand() + " <key> <command>";
	}
	
	public void addAlias(String key, String value) {
		aliases.put(key, value);
		System.err.println(value);
	}
	
	public Map<String, String> getAliases() {
		return this.aliases;
	}
	
	@Override
	public void run(ArrayList<String> args) { // local alias <key> <command>
		if (args.size() < 4) {
			this.addTextToConsole("> " + getUsage());
			return;
		}
		String key = args.get(2).toUpperCase();// <key>
		String value = "";// <command>
		for (int i = 3; i < args.size(); i++) {// For multiple argument aliases
			value += args.get(i) + " ";
		}
		
		this.addAlias(key, value);
		this.addTextToConsole("> '" + key + "' has been defined");
	}
	
}
