package net.iridgames.consolestocks.server.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.server.CommandServer;

public class CommandUser extends CommandServer {
	
	
	public CommandUser() {
		super("user", "get a list of users or get information about specific ones!");
		this.addAlias("getUser");
	}
	
	public CommandUser( String command ) {
		super(command);
	}
	
	@Override
	public void run(ArrayList<String> args, InetAddress ip, int port) {
		try {
			if (args.size() < 2 || args.get(1).toUpperCase().equals("LIST")) { // If it has no arguments
				String mess = "";
				for (String key : ConsoleStocks.server.getConnectedUIDs()) {
					if (key.equals(ConsoleStocks.server.getTCPConnectionFromIP(ip, port).getUID())) {
						mess += "alert{";
					}
					if (!key.equals(ConsoleStocks.server.getConnectedUIDs().get(ConsoleStocks.server.getConnectedUIDs().size() - 1))) {
						mess += key + ",";
					} else {
						mess += key;
					}
					if (key.equals(ConsoleStocks.server.getTCPConnectionFromIP(ip, port).getUID())) {
						mess += "}";
					}
					mess += " ";
				}
				mess += " " + mess;
				mess += " " + mess;
				mess += " " + mess;
				mess += " " + mess;
				mess += " " + mess;
				this.sendTextToClient(mess, ip, port);
				return;
			}
			this.sendTextToClient("alert{Invalid User: '" + args.get(1) + "'}", ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUsage() {
		return "user <list|UID>";
	}
	
}
