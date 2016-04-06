package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Command;

public class CommandSet extends Command {
	
	
	public CommandSet() {
		super("set");
	}
	
	public String getDescription() {
		return "Use to set options and other client variables";
	}
	
	@Override
	public String getUsage() {
		return "set [name]";
	}
	
	@Override
	public void run(ArrayList<String> args) {
		switch (args.get(2)) { // E.G. local set name
			case "name":
				String name = "";
				if (args.size() < 4) {
					ConsoleStocks.stateGame.console.addText("Invalid Command.");
				} else {
					for (int i = 3; i < args.size(); i++) {
						name += args.get(i);
						if (i < args.size() - 1) {
							name += " ";
						}
					}
					ConsoleStocks.client.setName(name);
					ConsoleStocks.stateGame.console.addText("Your username has been set to: " + name);
				}
				break;
		}
	}
}
