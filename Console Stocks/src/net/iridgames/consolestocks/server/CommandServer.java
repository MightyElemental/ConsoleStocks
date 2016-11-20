package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Command;

public abstract class CommandServer extends Command {
	
	
	protected boolean adminOnly = false;
	
	public CommandServer( String command, String desc ) {
		this(command);
		this.description = desc;
	}
	
	public CommandServer( String command ) {
		super(command);
	}
	
	/** Used to call the command
	 * 
	 * @param args
	 *            the arguments of the command including the command. E.g. msg, abcd, hello, world*/
	public abstract void run(ArrayList<String> args, InetAddress ip, int port);
	
	/** Is the command for admins only */
	public boolean isAdminOnly() {
		return adminOnly;
	}
	
	/** Set if command is admin only */
	public void setAdminOnly(boolean adminOnly) {
		this.adminOnly = adminOnly;
	}
	
	public void addTextToConsole(String text) {
		ConsoleStocks.server.getGUI().addCommand(text);
	}
	
	public void sendTextToClient(String text, InetAddress ip, int port) throws IOException {
		ConsoleStocks.server.sendObject("ServerMessage", text, ip, port);
	}
	
	public void sendObjectToClient(String key, Object obj, InetAddress ip, int port) throws IOException {
		ConsoleStocks.server.sendObject(key, obj, ip, port);
	}
	
}
