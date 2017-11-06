package net.iridgames.consolestocks;

public interface ClientListener {

	public void received(String object);
	
	public void onDisconnect(int disconnectCode);

}
