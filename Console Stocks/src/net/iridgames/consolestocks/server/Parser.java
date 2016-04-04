package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

import net.mightyelemental.network.TCPServer;
import net.mightyelemental.network.listener.MessageListenerServer;

public class Parser implements MessageListenerServer {
	
	
	private TCPServer server;
	private Stocks stocks;
	
	public Parser( TCPServer server ) {
		this.server = server;
		this.stocks = new Stocks();
	}
	
	public void parseMessage(String message, InetAddress ip, int port) throws IOException {
		
		try {
			String[] msg = message.split(" ");
			
			for (int i = 0; i < msg.length; i++) {
				System.out.println(msg[i]);
			}
			
			switch (msg[0].toUpperCase()) {
				case "GETSTOCKS":
					for (int i = 0; i < stocks.stockList.size(); i++) {
						sendMessage(i + " | " + stocks.stockList.get(i).getName(), ip, port);
					}
					break;
				case "GETSTOCK":
					Commands.getValueOfStock(server, stocks, msg[1], ip, port);
					break;
				case "PING":
					sendMessage("PONG!", ip, port);
					break;
				case "MSG":
					Commands.messageUser(server, msg, ip, port);
					break;
				default:
					sendMessage("Invalid command.", ip, port);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("Internal Server Error.", ip, port);
		}
	}
	
	public void sendMessage(String message, InetAddress ip, int port) throws IOException {
		server.sendObject("ServerMessage", message, ip, port);
	}
	
	// public void broadCastmessage(String message) {
	// for (int i = 0; i < addressList.size(); i++) {
	// sendMessage(message, addressList.get(i), this.port);
	// }
	// }
	
	@Override
	public void onNewClientAdded(InetAddress ip, int port, String uid) {
		
	}
	
	@SuppressWarnings( "unchecked" )
	@Override
	public void onObjectRecievedFromClient(InetAddress ip, int port, Object obj) {
		System.out.println(obj);
		String UID = server.getTCPConnectionFromIP(ip, port).getUID();
		String command = (String) ((Map<String, Object>) obj).get("Command");
		server.getGUI().addCommand(UID + ">>" + command);
		try {
			parseMessage(command, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
