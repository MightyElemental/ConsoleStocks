package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.CommandLocal;

public class CommandNoot extends CommandLocal {
	
	
	public CommandNoot() {
		super("nootnoot");
		this.setHidden(true);
		this.setDescription("Noot Noot!");
	}
	
	@Override
	public void run(ArrayList<String> args) {
		try {
			ConsoleStocks.stateGame.noot.play();
		} catch (Exception e) {
			System.out.println("Sound not found");
			this.addTextToConsole("Sound file not avaliable");
		}
	}
	
}
