package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.iridgames.consolestocks.common.Common;
import net.mightyelemental.network.TCPServer;

public class Commands {
	
	
	public static String[] commandList = { "GETSTOCKS", "GETSTOCK", "PING", "MSG", "LS" };
	
	public static void messageUser(TCPServer server, String[] commands, InetAddress ip, int port) throws UnknownHostException {
		if (commands.length < 3) { return; }
		InetAddress sendIP = InetAddress.getByName(commands[1]);
		String sendMessage = ip.getHostAddress() + "> ";
		for (int i = 2; i < commands.length; i++) {
			sendMessage += commands[i] + " ";
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
	
	public static void getValueOfStock(Parser parse, String name, InetAddress ip, int port) throws IOException {
		name = name.toUpperCase();
		float value = 0;
		try {
			value = parse.stocks.getStock(name).getValue();
		} catch (NullPointerException e) {
			
		}
		String message = name + " is worth " + getCurrencySymbol() + value;
		if (value == 0) {
			message = name + " is not a valid stock";
		}
		parse.sendMessage(message, ip, port);
	}
	
	public static void listCommands(Parser parse, InetAddress ip, int port) throws IOException {
		for (String com : commandList) {
			parse.sendMessage(com, ip, port);
		}
	}
}
