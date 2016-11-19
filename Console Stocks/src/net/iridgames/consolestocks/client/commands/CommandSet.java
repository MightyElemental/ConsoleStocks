package net.iridgames.consolestocks.client.commands;

import java.io.IOException;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.CommandLocal;
import net.iridgames.consolestocks.common.Common;

public class CommandSet extends CommandLocal {
	
	
	public CommandSet() {
		super("set");
		this.setLocalAndServer(true);
	}
	
	public String getDescription() {
		return "Use to set options and other client variables";
	}
	
	@Override
	public String getUsage() {
		return "set [name/shownumbers]";
	}
	
	@Override
	public void run(ArrayList<String> args) {
		if (args.size() < 3) {
			ConsoleStocks.stateGame.console.addText("Usage: " + getUsage());
			return;
		}
		switch (args.get(2)) { // E.G. local set name
			case "name":
				if (ConsoleStocks.client == null) {
					ConsoleStocks.stateGame.console.addText("No client available.");
					break;
				}
				String name = "";
				if (args.size() < 4) {
					ConsoleStocks.stateGame.console.addText("Usage: set name <name>");
				} else {
					for (int i = 3; i < args.size(); i++) {
						name += args.get(i);
						if (i < args.size() - 1) {
							name += " ";
						}
					}
					ConsoleStocks.client.setName(name);
					try {
						Common.clientConfig.setVariable("USER", name);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// Common.setClientVariable("USER", name);
					ConsoleStocks.stateGame.console.addText("Your username has been set to: " + name);
				}
				break;
			case "shownumbers":
				if (args.size() < 4) {
					ConsoleStocks.stateGame.console.addText("Usage: set shownumbers [true/false]");
				} else {
					try {
						Common.clientConfig.setVariable("SHOWPANELNUMBERS", args.get(3).contains("t") + "");
						// Common.setClientVariable("SHOWPANELNUMBERS", Boolean.parseBoolean(args.get(3)) + "");
					} catch (Exception e) {
						e.printStackTrace();
						ConsoleStocks.stateGame.console.addText("Usage: set shownumbers [true/false]");
					}
				}
				break;
			case "defaultip":
				break;
			case "defaultport":
				break;
			case "panel":
				break;
			default:
				ConsoleStocks.stateGame.console.addText("Usage: " + getUsage());
				break;
		}
	}
}
