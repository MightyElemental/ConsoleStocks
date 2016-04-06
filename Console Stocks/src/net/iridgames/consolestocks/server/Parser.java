package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import net.iridgames.consolestocks.common.Common;
import net.mightyelemental.network.TCPServer;
import net.mightyelemental.network.listener.MessageListenerServer;

public class Parser implements MessageListenerServer {
	
	
	public TCPServer server;
	public Stocks stocks;
	
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
					Commands.getValueOfStock(this, msg[1], ip, port);
					break;
				case "PING":
					sendMessage("PONG!", ip, port);
					break;
				case "MSG":
					Commands.messageUser(server, msg, ip, port);
					break;
				case "LS":
					Commands.listCommands(this, ip, port);
					break;
				default:
					sendMessage("Invalid Command.", ip, port);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("Internal Server Error.", ip, port);
		}
	}
	
	public void sendMessage(String message, InetAddress ip, int port) throws IOException {
		server.sendObject("ServerMessage", message, ip, port);
		// String UID = server.getTCPConnectionFromIP(ip, port).getUID();
		// server.getGUI().addCommand("Server>" + UID + ">>" + message);
	}
	
	// public void broadCastmessage(String message) {
	// for (int i = 0; i < addressList.size(); i++) {
	// sendMessage(message, addressList.get(i), this.port);
	// }
	// }
	
	@Override
	public void onNewClientAdded(InetAddress ip, int port, String uid) {
		try {
			server.sendObject("UID", uid, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ServerName", Common.serverSettings.get("SERVERNAME"));
			map.put("OnlineClients", server.getTcpConnections().size());
			map.put("Currency", Common.serverSettings.get("CURRENCY") + " (" + Commands.getCurrencySymbol() + ")");
			server.sendObjectMap(map, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sendOnlineClients();
	}
	
	public void sendOnlineClients() {
		try {
			Object[] keys = server.getTcpConnections().keySet().toArray();
			for (int i = 0; i < server.getTcpConnections().size(); i++) {
				InetAddress ip2 = server.getTcpConnections().get(keys[i]).getIp();
				int port2 = server.getTcpConnections().get(keys[i]).getPort();
				server.sendObject("OnlineClients", server.getTcpConnections().size(), ip2, port2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings( "unchecked" )
	@Override
	public void onObjectRecievedFromClient(InetAddress ip, int port, Object obj) {
		System.out.println(obj);
		String UID = server.getTCPConnectionFromIP(ip, port).getUID();
		String command = (String) ((Map<String, Object>) obj).get("Command");
		server.getGUI().addCommand(UID + ">> " + command);
		try {
			parseMessage(command, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClientDisconnect(InetAddress ip, int port, String uid) {
		server.getGUI().addCommand(uid + "> Has Disconnected From Server");
		server.getTcpConnections().remove(uid);
		server.getGUI().updateClients();
		sendOnlineClients();
	}
}
