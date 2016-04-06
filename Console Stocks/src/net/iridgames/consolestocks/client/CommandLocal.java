package net.iridgames.consolestocks.client;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandLocal {
	
	
	protected final String command;
	
	protected String description = "No description has been given";
	
	protected List<String> alias = new ArrayList<String>();
	
	public CommandLocal( String command, List<String> alias ) {
		this(command);
		this.alias = alias;
	}
	
	public CommandLocal( String command ) {
		this.command = command;
	}
	
	/** Used to call the command */
	public abstract void run(ArrayList<String> args);
	
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
	
	/** Get the description of the command */
	public String getDescription() {
		return this.description;
	}
	
	/** Set the description of the command */
	public void setDescription(String desc) {
		this.description = desc;
	}
	
}
