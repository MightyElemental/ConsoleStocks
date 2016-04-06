package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.server.commands.CommandGetStockInfo;
import net.iridgames.consolestocks.server.commands.CommandGetStocks;
import net.iridgames.consolestocks.server.commands.CommandList;
import net.iridgames.consolestocks.server.commands.CommandMessage;
import net.iridgames.consolestocks.server.commands.CommandPing;
import net.mightyelemental.network.TCPServer;

public class Commands {
	
	
	public static CommandServer ping = new CommandPing();
	public static CommandServer getStocks = new CommandGetStocks();
	public static CommandServer getStockInfo = new CommandGetStockInfo();
	public static CommandServer message = new CommandMessage();
	public static CommandServer list = new CommandList();
	
	public static String[] commandList = { "GETSTOCKS", "GETSTOCK", "PING", "MSG", "LS", "LOCAL" };
	
	public static Map<String, CommandServer> commands = new HashMap<String, CommandServer>();
	
	public static void setupCommandList() {
		commands.put(ping.getCommand(), ping);
		commands.put(getStocks.getCommand(), getStocks);
		commands.put(getStockInfo.getCommand(), getStockInfo);
		commands.put(message.getCommand(), message);
		commands.put(list.getCommand(), list);
	}
	
	public static void messageUser(TCPServer server, ArrayList<String> commands, InetAddress ip, int port) throws UnknownHostException {
		if (commands.size() < 3) { return; }
		InetAddress sendIP = InetAddress.getByName(commands.get(1));
		String sendMessage = ip.getHostAddress() + "> ";
		for (int i = 2; i < commands.size(); i++) {
			sendMessage += commands.get(i) + " ";
		}
		try {
			server.sendObject("U2UvS", sendMessage, sendIP, port); // U2UvS - User To User Via Server
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getCurrencySymbol() {
		String name = Common.serverSettings.get("CURRENCY");
		switch (name) {
			case "pound":
				return "\u00A3";
			case "dollar":
				return "$";
			case "yen":
				return "\u00A5";
			default:
				return "\u00A3";
		}
	}
}
