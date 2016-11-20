package net.iridgames.consolestocks.server.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.server.CommandServer;
import net.mightyelemental.network.TCPConnection;

public class CommandMessage extends CommandServer {
	
	
	public CommandMessage() {
		super("msg");
		this.addAlias("message");
	}
	
	@Override
	public void run(ArrayList<String> args, InetAddress ip, int port) {
		if (args.size() < 3) { return; }
		try {
			TCPConnection con = ConsoleStocks.server.getTCPConnectionFromUID(args.get(1));
			
			if (con == null) {
				sendTextToClient("alert{'" + args.get(1) + "' is not a valid ClientUID}", ip, port);
				// ConsoleStocks.serverParser.sendMessage("'" + args.get(1) + "' is not a valid ClientUID", ip, port);
				return;
			}
			
			String senderUID = ConsoleStocks.server.getTCPConnectionFromIP(ip, port).getUID();
			
			String sendMessage = "user{" + senderUID + "} >> ";
			for (int i = 2; i < args.size(); i++) {
				sendMessage += args.get(i) + " ";
			}
			// U2UvS - User To User Via Server
			ConsoleStocks.server.sendObject("U2UvS", sendMessage, con.getIp(), con.getPort());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public String getUsage() {
		return "msg <user code> <message>";
	}
	
}
