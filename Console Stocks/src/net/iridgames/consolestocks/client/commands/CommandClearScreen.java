package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.CommandLocal;

public class CommandClearScreen extends CommandLocal {
	
	
	public CommandClearScreen() {
		super("clear");
		this.addAlias("cls");
		this.setDescription("Use to clear the screen");
		this.setLocalAndServer(true);
	}
	
	@Override
	public void run(ArrayList<String> args) {
		ConsoleStocks.stateGame.console.console.clear();
	}
	
}
