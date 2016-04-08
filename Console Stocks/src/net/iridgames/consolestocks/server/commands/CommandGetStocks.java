package net.iridgames.consolestocks.server.commands;

import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.server.CommandServer;

public class CommandGetStocks extends CommandServer {
	
	
	public CommandGetStocks() {
		super("getstocks");
		this.setDescription("Use to get a list of all possible stocks");
	}
	
	@Override
	public void run(ArrayList<String> args, InetAddress ip, int port) {
		for (int i = 0; i < ConsoleStocks.serverParser.stocks.stockList.size(); i++) {
			ConsoleStocks.serverParser.sendMessage(i + " | " + ConsoleStocks.serverParser.stocks.stockList.get(i).getName(), ip, port);
		}
	}
	
}
