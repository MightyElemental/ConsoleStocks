package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.iridgames.consolestocks.server.commands.CommandGetStockInfo;
import net.iridgames.consolestocks.server.commands.CommandGetStocks;
import net.iridgames.consolestocks.server.commands.CommandHelp;
import net.iridgames.consolestocks.server.commands.CommandMessage;
import net.iridgames.consolestocks.server.commands.CommandPing;
import net.mightyelemental.network.TCPServer;

public class Commands {
	
	
	public static CommandServer ping = new CommandPing();
	public static CommandServer getStocks = new CommandGetStocks();
	public static CommandServer getStockInfo = new CommandGetStockInfo();
	public static CommandServer message = new CommandMessage();
	public static CommandServer help = new CommandHelp();
	
	// public static String[] commandList = { "GETSTOCKS", "GETSTOCK", "PING", "MSG", "LS", "LOCAL" };
	
	public static Map<String, CommandServer> commands = new HashMap<String, CommandServer>();
	
	public static void setupCommandList() {
		addCommand(ping);
		addCommand(getStocks);
		addCommand(getStockInfo);
		addCommand(message);
		addCommand(help);
	}
	
	public static void addCommand(CommandServer com) {
		commands.put(com.getCommand().toUpperCase(), com);
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
}
