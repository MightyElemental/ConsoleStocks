package net.iridgames.consolestocks.server.commands;

import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.server.CommandServer;

public class CommandGetStockInfo extends CommandServer {
	
	
	public CommandGetStockInfo() {
		super("stockinfo");
		this.setDescription("Use to get information about a specific stock");
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
		String message = name + " is worth " + Common.getCurrencySymbol() + value;
		if (value == 0) {
			message = name + " is not a valid stock";
		}
		ConsoleStocks.serverParser.sendMessage(message, ip, port);
	}
	
}
