package net.iridgames.consolestocks.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Command;

public abstract class CommandLocal extends Command {
	
	
	public CommandLocal( String command, List<String> alias ) {
		this(command);
		this.alias = alias;
	}
	
	public CommandLocal( String command ) {
		super(command);
	}
	
	/** Used to call the command */
	public abstract void run(ArrayList<String> args);
	
	public void addTextToConsole(String text) {
		ConsoleStocks.stateGame.console.addText(text);
	}
	
	public void sendTextToServer(String text) throws IOException {
		ConsoleStocks.client.sendObject("Command", text);
	}
	
}
