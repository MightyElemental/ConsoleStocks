package net.iridgames.consolestocks.client;

import java.util.Map;

import net.iridgames.consolestocks.ConsoleStocks;
import net.mightyelemental.network.client.TCPClient;
import net.mightyelemental.network.listener.MessageListenerClient;

public class Client extends TCPClient implements MessageListenerClient {
	
	
	public Client( String name, String address, int port, int maxBytes ) {
		super(address, port, false, maxBytes);
		this.name = name;
	}
	
	public Map<String, Object> serverInfo;
	
	protected String name = "UND_USER";
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@SuppressWarnings( "unchecked" )
	@Override
	public void onObjectRecievedFromServer(Object obj) {
		System.out.println(obj);
		if (((Map<String, Object>) obj).containsKey("UID")) {
			this.clientUID = (String) ((Map<String, Object>) obj).get("UID");
		} else if (((Map<String, Object>) obj).containsKey("ServerName")) {
			serverInfo = ((Map<String, Object>) obj);
			ConsoleStocks.stateGame.console.updatePrefix();
			ConsoleStocks.stateGame.console.console.remove("Connecting...");
			ConsoleStocks.stateGame.console.addText("Connected to server '" + serverInfo.get("ServerName") + "'");
			ConsoleStocks.stateGame.console.localMode = false;
		} else if (((Map<String, Object>) obj).containsKey("OnlineClients")) {
			if (serverInfo != null) {
				serverInfo.putAll(((Map<String, Object>) obj));
			}
		} else if (((Map<String, Object>) obj).containsKey("U2UvS")) {// Player to Player chat
			String command = (String) ((Map<String, Object>) obj).get("U2UvS");
			ConsoleStocks.stateGame.console.addText(command);
		} else {
			String command = (String) ((Map<String, Object>) obj).get("ServerMessage");
			ConsoleStocks.stateGame.console.addText(">> " + command);
		}
	}
	
	@Override
	public void onServerClosed() {
		ConsoleStocks.stateGame.console.addText("SERVER HAS BEEN CLOSED");
		try {
			ConsoleStocks.client.stopClient();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ConsoleStocks.client = null;
	}
	
}
