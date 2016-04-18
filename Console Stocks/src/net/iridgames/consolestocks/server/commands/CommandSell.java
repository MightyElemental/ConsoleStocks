package net.iridgames.consolestocks.server.commands;

import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.server.CommandServer;


public class CommandSell extends CommandServer {
	
	
	public CommandSell( String command, String desc ) {
		super(command, desc);
		// TODO Auto-generated constructor stub
	}
	
	public CommandSell( String command ) {
		super(command);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run(ArrayList<String> args, InetAddress ip, int port) {
		// TODO Auto-generated method stub
		
	}
	
}
