package net.iridgames.consolestocks.server.commands;

import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.server.CommandServer;

public class CommandPing extends CommandServer {
	
	
	public CommandPing() {
		super("ping");
		this.setDescription("Use to test connection between server and client");
	}
	
	@Override
	public void run(ArrayList<String> args, InetAddress ip, int port) {
		ConsoleStocks.serverParser.sendMessage("PONG!", ip, port);
	}
	
}
