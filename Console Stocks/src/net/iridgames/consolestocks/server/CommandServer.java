package net.iridgames.consolestocks.server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public abstract class CommandServer {
	
	
	protected final String command;
	
	protected String description = "No description has been given";
	
	protected List<String> alias = new ArrayList<String>();
	
	protected boolean adminOnly = false;
	
	public CommandServer( String command, String desc ) {
		this(command);
		this.description = desc;
	}
	
	public CommandServer( String command ) {
		this.command = command;
	}
	
	/** Used to call the command */
	public abstract void run(ArrayList<String> args, InetAddress ip, int port);
	
	/** Get the command */
	public String getCommand() {
		return command;
	}
	
	/** Get the usage */
	public abstract String getUsage();
	
	/** Returns the aliases */
	public List<String> getAlias() {
		return alias;
	}
	
	/** Adds and alias */
	public void addAlias(String alias) {
		this.alias.add(alias);
	}
	
	/** Is the command for admins only */
	public boolean isAdminOnly() {
		return adminOnly;
	}
	
	/** Set if command is admin only */
	public void setAdminOnly(boolean adminOnly) {
		this.adminOnly = adminOnly;
	}
	
	/** Get the description of the command */
	public String getDescription() {
		return this.description;
	}
	
	/** Set the description of the command */
	public void setDescription(String desc) {
		this.description = desc;
	}
	
}
