package net.iridgames.consolestocks.client.commands;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.client.Client;
import net.iridgames.consolestocks.client.CommandLocal;
import net.iridgames.consolestocks.common.Common;

public class CommandConnect extends CommandLocal {
	
	
	public CommandConnect() {
		super("connect");
		this.setDescription("Use to connect to a server");
	}
	
	public CommandConnect( String command ) {
		super(command);
	}
	
	@Override
	public void run(ArrayList<String> args) {
		this.addTextToConsole("alert{Connecting...}");
		if (ConsoleStocks.client != null) {
			this.addTextToConsole("alert{You must disconnect from the current server}");
			return;
		}
		if (args.size() > 2) {
			try {
				InetAddress.getByName(args.get(2));
			} catch (Exception e) {
				this.addTextToConsole("alert{That is not a valid ip address}");
				return;
			}
			ConsoleStocks.address = args.get(2);
		}
		
		if (args.size() > 3) {
			ConsoleStocks.port = Integer.parseInt(args.get(3));
		}
		
		try {
			ConsoleStocks.client = new Client(Common.clientSettings.get("USER"), ConsoleStocks.address, ConsoleStocks.port, 1024);
			ConsoleStocks.client.setup();
			ConsoleStocks.client.addListener(ConsoleStocks.client);
		} catch (ConnectException e) {
			ConsoleStocks.client.getInitiater().onConnectionRefused();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!ConsoleStocks.client.hasBeenSetup()) {
			ConsoleStocks.client.getInitiater().onConnectionRefused();
		}
	}
	
	@Override
	public String getUsage() {
		return "connect <ip> <port>";
	}
	
}
