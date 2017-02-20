package net.iridgames.consolestocks.server.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.server.CommandServer;
import net.iridgames.consolestocks.server.Commands;

public class CommandHelp extends CommandServer {
	
	
	public CommandHelp() {
		super("help");
		this.setDescription("Use to get information about commands or get the list of usable commands");
	}
	
	public String getUsage() {
		return "help {command}";
	}
	
	@Override
	public void run(ArrayList<String> args, InetAddress ip, int port) {
		if (args.size() < 2) {
			for (String key : Commands.commands.keySet()) {
				ConsoleStocks.serverParser.sendMessage(key, ip, port);
			}
			return;
		}
		try { // message to client
			try { // command
				CommandServer toUse = null;
				for (String s : Commands.commands.keySet()) {
					if (args.get(1).equalsIgnoreCase(s) || Commands.commands.get(s).getAlias().contains(args.get(1))) {
						toUse = Commands.commands.get(s);
						break;
					}
				}
				
				String usage = toUse.getUsage();
				String desc = toUse.getDescription();
				this.sendTextToClient(desc, ip, port);
				this.sendTextToClient("Usage: " + usage, ip, port);
			} catch (NullPointerException e) {
				this.sendTextToClient("alert{Invalid Command: '" + args.get(1) + "'}", ip, port);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
