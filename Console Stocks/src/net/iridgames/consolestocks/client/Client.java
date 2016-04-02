package net.iridgames.consolestocks.client;

import net.mightyelemental.network.client.TCPClient;

public class Client extends TCPClient {

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

}
