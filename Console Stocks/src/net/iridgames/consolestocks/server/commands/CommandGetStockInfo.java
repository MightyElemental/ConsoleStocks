package net.iridgames.consolestocks.server.commands;

import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.server.CommandServer;
import net.iridgames.consolestocks.server.Commands;

public class CommandGetStockInfo extends CommandServer {
	
	
	public CommandGetStockInfo() {
		super("stockinfo");
	}
	
	@Override
	public String getUsage() {
		return getCommand() + " <stockID>";
	}
	
	@Override
	public void run(ArrayList<String> args, InetAddress ip, int port) {
		String name = args.get(1);
		name = name.toUpperCase();
		float value = 0;
		try {
			value = ConsoleStocks.serverParser.stocks.getStock(name).getValue();
		} catch (NullPointerException e) {
			
		}
		String message = name + " is worth " + Commands.getCurrencySymbol() + value;
		if (value == 0) {
			message = name + " is not a valid stock";
		}
		ConsoleStocks.serverParser.sendMessage(message, ip, port);
	}
	
}
