package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;

import net.iridgames.consolestocks.client.CommandLocal;
import net.iridgames.consolestocks.client.LocalCommands;

public class CommandList extends CommandLocal {
	
	
	public CommandList() {
		super("list");
		this.addAlias("ls");
		this.setDescription("Use to list all commands");
	}
	
	@Override
	public void run(ArrayList<String> args) {
		for (CommandLocal c : LocalCommands.commands.values()) {
			LocalCommands.addTextToConsole(c.getCommand());
			for (int i = 0; i < c.getAlias().size(); i++) {
				String a = c.getAlias().get(i);
				if (a != null) {
					LocalCommands.addTextToConsole(a);
				}
			}
		}
	}
	
	@Override
	public String getUsage() {
		return "list";
	}
	
}
