package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.server.gui.ServerFrame;
import net.mightyelemental.network.TCPServer;
import net.mightyelemental.network.listener.MessageListenerServer;

public class Parser implements MessageListenerServer, Runnable {
	
	
	public TCPServer server;
	public Stocks stocks;
	
	public Thread serverThread = new Thread(this);
	
	public Parser( TCPServer server ) {
		this.server = server;
		this.stocks = new Stocks();
	}
	
	/** Used to process commands sent to the server */
	public void parseMessage(String message, InetAddress ip, int port) throws IOException {
		if (message.length() < 1) return;
		String UID = server.getTCPConnectionFromIP(ip, port).getUID();
		try {
			ArrayList<ArrayList<String>> msg = Common.interpretCommandLine(message);
			
			for (int j = 0; j < msg.size(); j++) {
				boolean flag = false;
				for (String s : Commands.commands.keySet()) {
					if (msg.get(j).get(0).equalsIgnoreCase(s) || Commands.commands.get(s).getAlias().contains(msg.get(0))) {
						Commands.commands.get(s).run(msg.get(j), ip, port);
						flag = true;
					} // Add invalid command
				}
				if (!flag) {
					sendMessage("alert{Invalid Command.}", ip, port);
				} else {
					server.getGUI().addCommand(UID + ">> " + message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("error{Internal Server Error.}", ip, port);
		}
	}
	
	public void sendMessage(String message, InetAddress ip, int port) {
		try {
			// System.out.println(message);
			server.sendObject("ServerMessage", message, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			try {
				server.killConnection(uid);
				return;
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ServerName", Common.serverSettings.get("SERVERNAME"));
		map.put("OnlineClients", server.getTcpConnections().size());
		map.put("Currency", Common.serverSettings.get("CURRENCY") + " (" + Common.getCurrencySymbol() + ")");
		server.sendObjectMap(map, ip, port);
		sendOnlineClients();
	}
	
	public void sendOnlineClients() {
		String key = "";
		try {
			Object[] keys = server.getTcpConnections().keySet().toArray();
			for (int i = 0; i < server.getTcpConnections().size(); i++) {
				key = (String) keys[i];
				InetAddress ip2 = server.getTcpConnections().get(keys[i]).getIp();
				int port2 = server.getTcpConnections().get(keys[i]).getPort();
				server.sendObject("OnlineClients", server.getTcpConnections().size(), ip2, port2);
			}
		} catch (SocketException e) {
			e.printStackTrace();
			try {
				server.getTcpConnections().get(key).stopThread("has been kicked (socket error)");
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
			server.getTcpConnections().remove(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onObjectRecievedFromClient(InetAddress ip, int port, Map<String, Object> obj) {
		System.out.println(obj);
		String UID = server.getTCPConnectionFromIP(ip, port).getUID();
		if (!obj.containsKey("Command")) {
			server.getGUI().addCommand(UID + ">> Verification Sent");
			return;
		}
		String command = (String) obj.get("Command");
		try {
			parseMessage(command, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClientDisconnect(InetAddress ip, int port, String uid) {
		uid = (uid == null ? "#null" : uid);
		server.getGUI().addCommand(uid + "> Has Disconnected From Server");
		server.getTcpConnections().remove(uid);
		server.getGUI().updateClients();
		sendOnlineClients();
	}
	
	int count;
	
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000);
				if (count % 60 == 0) {
					stocks.updateStocks();
					
				}
				((ServerFrame) server.getGUI()).updateStocks(count % 60);
				count++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
