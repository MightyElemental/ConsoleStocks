package net.iridgames.consolestocks.client;

import java.io.IOException;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Command;

public abstract class CommandLocal extends Command {
	
	
	public CommandLocal( String command ) {
		super(command);
	}
	
	private boolean localAndServer = false;
	
	/** Used to call the command */
	public abstract void run(ArrayList<String> args);
	
	public void addTextToConsole(String text) {
		ConsoleStocks.stateGame.console.addText(text);
	}
	
	public void sendTextToServer(String text) throws IOException {
		ConsoleStocks.client.sendObject("Command", text);
	}
	
	/** Whether or not it should run on server and local<br>
	 * If it runs on both, you will be able to use the command on server mode but it will still be processed client
	 * side */
	public boolean isLocalAndServer() {
		return localAndServer;
	}
	
	/** Should the command be able to run on server mode
	 * 
	 * @see isLocalAndServer() */
	public void setLocalAndServer(boolean localAndServer) {
		this.localAndServer = localAndServer;
	}
	
}
