package net.iridgames.consolestocks.server.commands;

import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.server.CommandServer;
import net.iridgames.consolestocks.server.Commands;

public class CommandList extends CommandServer {
	
	
	public CommandList() {
		super("ls");
	}
	
	@Override
	public void run(ArrayList<String> args, InetAddress ip, int port) {
		for (String key : Commands.commands.keySet()) {
			ConsoleStocks.serverParser.sendMessage(key, ip, port);
		}
	}
	
	@Override
	public String getUsage() {
		return "ls";
	}
	
}
