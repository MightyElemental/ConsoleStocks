package net.iridgames.consolestocks.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Common;
import net.mightyelemental.network.client.TCPClient;
import net.mightyelemental.network.listener.MessageListenerClient;

public class Client extends TCPClient implements MessageListenerClient {
	
	
	public Client( String name, String address, int port, int maxBytes ) {
		super(address, port, false, maxBytes, Common.getVerifyCode());
		accountInfo.put("username", "");
		accountInfo.put("displayName", Common.clientSettings.get("USER"));
		accountInfo.put("balance", 0);
		accountInfo.put("stocksOwned", 0);
		accountInfo.put("stocksBought", 0);
		accountInfo.put("stocksSold", 0);
	}
	
	public Map<String, Object> serverInfo;
	
	public Map<String, Object> accountInfo = new HashMap<String, Object>();
	
	public void setName(String name) {
		accountInfo.put("username", name);
	}
	
	public String getName() {
		return accountInfo.get("displayName") + "";
	}
	
	@SuppressWarnings( "unchecked" )
	@Override
	public void onObjectRecievedFromServer(Object obj) {
		System.out.println(obj);
		if (((Map<String, Object>) obj).containsKey("UID")) {
			this.clientUID = (String) ((Map<String, Object>) obj).get("UID");
			accountInfo.put("username", clientUID);
		} else if (((Map<String, Object>) obj).containsKey("ServerName")) {
			connected(obj);
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
	
	@SuppressWarnings( "unchecked" )
	public void connected(Object obj) {
		serverInfo = ((Map<String, Object>) obj);
		ConsoleStocks.stateGame.console.console.remove("Connecting...");
		ConsoleStocks.stateGame.console.addText("Connected to server '" + serverInfo.get("ServerName") + "'");
		ConsoleStocks.stateGame.console.localMode = false;
		ConsoleStocks.stateGame.console.updatePrefix();
	}
	
	@Override
	public void onServerClosed() {
		try {
			ConsoleStocks.client.stopClient();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		ConsoleStocks.stateGame.console.onServerClosed();
		ConsoleStocks.client = null;
	}
	
	public void onConnectionRefused() {
		System.err.println("Could not connect to server!");
		ConsoleStocks.stateGame.console.addText("error{~WARNING~} Could not connect to " + this.getAddress() + ":" + this.getPort());
		try {
			ConsoleStocks.client.stopClient();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		ConsoleStocks.client = null;
	}
	
}
