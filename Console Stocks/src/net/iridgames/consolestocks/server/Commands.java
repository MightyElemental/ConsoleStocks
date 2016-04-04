package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.mightyelemental.network.Server;
import net.mightyelemental.network.TCPServer;

public class Commands {
	
	
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
	
	public static void getValueOfStock(Server server, Stocks stocks, String name, InetAddress ip, int port) throws IOException {
		name = name.toUpperCase();
		float value = 0;
		try {
			value = stocks.getStock(name).getValue();
		} catch (NullPointerException e) {
			
		}
		String message = name + " is worth \u00A3" + value;
		if (value == 0) {
			message = name + " is not a valid stock";
		}
		server.sendObject("ServerMessage", message, ip, port);
	}
}
