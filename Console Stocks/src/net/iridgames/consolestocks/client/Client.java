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
		String command = (String) ((Map<String, Object>) obj).get("ServerMessage");
		ConsoleStocks.stateGame.console.addText(command);
	}
	
	@Override
	public void onServerClosed() {
		ConsoleStocks.stateGame.console.addText("SERVER HAS BEEN CLOSED");
	}
	
}
