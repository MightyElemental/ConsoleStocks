package net.iridgames.consolestocks.server.commands;

import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.server.CommandServer;
import net.iridgames.stockAPI.Stock;

public class CommandGetStocks extends CommandServer {
	
	
	public CommandGetStocks() {
		super("getstocks");
		this.setDescription("Use to get a list of all possible stocks");
	}
	
	@Override
	public void run(ArrayList<String> args, InetAddress ip, int port) {
		int i = 0;
		for (Stock stock : ConsoleStocks.serverParser.stocks.stockList.values()) {
			String sym = stock.getSymbol();
			while (sym.length() < 4) {
				sym += " ";
			}
			ConsoleStocks.serverParser.sendMessage(i + " | " + sym + " | " + stock.getName(), ip, port);
			i++;
		}
		
		// for (int i = 0; i < ConsoleStocks.serverParser.stocks.stockList.size(); i++) {
		// ConsoleStocks.serverParser.sendMessage(i + " | " + s.get(i).getName(), ip, port);
		// }
	}
	
}
