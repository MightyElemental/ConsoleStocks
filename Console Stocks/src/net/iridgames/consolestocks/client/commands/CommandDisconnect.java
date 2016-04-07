package net.iridgames.consolestocks.client.commands;

import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.CommandLocal;

public class CommandDisconnect extends CommandLocal {
	
	
	public CommandDisconnect() {
		super("disconnect");
		this.setDescription("Use to disconnect from a server");
	}
	
	@Override
	public void run(ArrayList<String> args) {
		if (ConsoleStocks.client != null) {
			try {
				ConsoleStocks.client.stopClient();
				ConsoleStocks.client = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			this.addTextToConsole("No server to disconnect from");
		}
	}
	
}
