package net.iridgames.consolestocks.network;

public class Common {
	
	public enum DataType {
		AliveTick,
		Acknowledge,
		Ping,
		Command
	}
	
	public enum Commands {
		PersonalMessage,
		Ping,
		GetStocks
	}
	
}
