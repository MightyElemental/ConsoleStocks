package net.iridgames.consolestocks.client.commands;

import java.io.IOException;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.CommandLocal;
import net.iridgames.consolestocks.client.gui.PRender;
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
		return "set [name/shownumbers/panel]";
	}
	
	@Override
	public void run(ArrayList<String> args) {
		System.out.println(args);
		if (args.get(0).equalsIgnoreCase("local")) {
			args.remove(0);
		}
		if (args.size() < 2) {
			ConsoleStocks.stateGame.console.addText("Usage: " + getUsage());
			return;
		}
		switch (args.get(1)) { // E.G. set name
			case "name":
				if (ConsoleStocks.client == null) {
					ConsoleStocks.stateGame.console.addText("No client available.");
					break;
				}
				String name = "";
				if (args.size() < 3) {
					ConsoleStocks.stateGame.console.addText("Usage: set name <name>");
				} else {
					for (int i = 2; i < args.size(); i++) {
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
				if (args.size() < 3) {
					ConsoleStocks.stateGame.console.addText("Usage: set shownumbers [true/false]");
				} else {
					try {
						Common.clientConfig.setVariable("SHOWPANELNUMBERS", args.get(2).contains("t") + "");
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
				if (args.size() < 4) {
					ConsoleStocks.stateGame.console.addText("Usage: set panel [bl/tr/tl] [server/client/account/chat/stock]");
				} else {
					switch (args.get(2)) {
						case "bl":
							ConsoleStocks.stateGame.p_bl = PRender.nameToID(args.get(3));
							break;
						case "tr":
							ConsoleStocks.stateGame.p_tr = PRender.nameToID(args.get(3));
							break;
						case "tl":
							ConsoleStocks.stateGame.p_tl = PRender.nameToID(args.get(3));
							break;
						default:
							ConsoleStocks.stateGame.console.addText("Usage: set panel [bl/tr/tl] [server/client/account/chat/stock]");
							break;
					}
				}
				break;
			default:
				ConsoleStocks.stateGame.console.addText("Usage: " + getUsage() + "a");
				break;
		}
	}
}
