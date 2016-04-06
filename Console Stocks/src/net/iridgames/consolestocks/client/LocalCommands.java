package net.iridgames.consolestocks.client;

import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;

public class LocalCommands {
	
	
	public static String[] commands = { "set", "cls", "clear" };
	
	public static void setOption(ArrayList<String> command) {
		switch (command.get(2)) { // E.G. local set name
			case "name":
				String name = "";
				if (command.size() < 4) {
					ConsoleStocks.stateGame.console.addText("Invalid Command.");
				} else {
					for (int i = 3; i < command.size(); i++) {
						name += command.get(i);
						if (i < command.size() - 1) {
							name += " ";
						}
					}
					ConsoleStocks.client.setName(name);
					ConsoleStocks.stateGame.console.addText("Your username has been set to: " + name);
				}
				break;
		}
	}
	
	public static void clearScreen(ArrayList<String> command) {
		ConsoleStocks.stateGame.console.console.clear();
	}
	
	public static void listCommands(ArrayList<String> command) {
		for (String s : commands) {
			ConsoleStocks.stateGame.console.addText(s);
		}
	}
	
}
