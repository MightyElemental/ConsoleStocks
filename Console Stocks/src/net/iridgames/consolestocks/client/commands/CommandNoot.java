package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.CommandLocal;

public class CommandNoot extends CommandLocal {
	
	
	public CommandNoot() {
		super("nootnoot");
		this.setHidden(true);
	}
	
	@Override
	public void run(ArrayList<String> args) {
		ConsoleStocks.stateGame.noot.play();
	}
	
}
