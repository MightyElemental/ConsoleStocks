package net.iridgames.consolestocks.common;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
	
	
	protected final String command;
	
	protected String usage = "No usage has been specified";
	
	protected List<String> alias;
	
	protected boolean adminOnly = false;
	
	public Command( String command, String usage, List<String> alias ) {
		this(command, usage);
		this.alias = alias;
	}
	
	public Command( String command, String usage ) {
		this(command);
		this.usage = usage;
	}
	
	public Command( String command ) {
		this.command = command;
	}
	
	/** Used to call the command */
	public abstract void run(ArrayList<String> commandArgs);
	
	/** Get the command */
	public String getCommand() {
		return command;
	}
	
	/** Get the usage */
	public String getUsage() {
		return usage;
	}
	
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
	
}
