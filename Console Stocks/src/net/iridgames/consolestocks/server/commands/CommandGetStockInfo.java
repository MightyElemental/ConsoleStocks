package net.iridgames.consolestocks.server.commands;

import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.server.CommandServer;
import net.iridgames.consolestocks.server.Stock;

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
		Stock stock;
		try {
			stock = ConsoleStocks.serverParser.stocks.getStock(name);
			value = stock.getValue();
		} catch (NullPointerException e) {
			ConsoleStocks.serverParser.sendMessage(name + " is not a valid stock", ip, port);
			return;
		}
		String message = name + " is worth " + Common.getCurrencySymbol() + value + " ("
			+ ConsoleStocks.serverParser.stocks.calculateValueIncrease(stock) + "%)";
		ConsoleStocks.serverParser.sendMessage(message, ip, port);
	}
	
}
