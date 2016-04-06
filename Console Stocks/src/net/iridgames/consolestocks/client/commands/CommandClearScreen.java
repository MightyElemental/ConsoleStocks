package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Command;

public class CommandClearScreen extends Command {
	
	
	public CommandClearScreen() {
		super("clear");
		this.addAlias("cls");
		this.setDescription("Use to clear the screen");
	}
	
	@Override
	public void run(ArrayList<String> args) {
		ConsoleStocks.stateGame.console.console.clear();
	}
	
	@Override
	public String getUsage() {
		return "clear";
	}
	
}
