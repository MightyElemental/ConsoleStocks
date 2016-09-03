package net.iridgames.consolestocks.server.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.server.CommandServer;
import net.iridgames.stockAPI.Stock;

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
		if (args.size() < 2) {
			try {
				this.sendTextToClient("Usage: " + getUsage(), ip, port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String name = args.get(1);
		name = name.toUpperCase();
		float value = 0;
		Stock stock;
		try {
			stock = ConsoleStocks.serverParser.stocks.getStock(name);
			value = stock.getPrice();
		} catch (NullPointerException e) {
			e.printStackTrace();
			ConsoleStocks.serverParser.sendMessage(name + " is not a valid stock", ip, port);
			return;
		}
		String valueshift = Common.getCurrencySymbol() + stock.getChangeAmount() + "";
		if (stock.getChangeAmount() > 0) {
			valueshift = "+" + valueshift;
		}
		String percent = stock.getPercentChange() + "%";
		if (stock.getPercentChange() > 0) {
			percent = "+" + percent;
		}
		String message = name + " is worth " + Common.getCurrencySymbol() + value + " | " + percent + " | " + valueshift;
		ConsoleStocks.serverParser.sendMessage(message, ip, port);
	}
	
}
