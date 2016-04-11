package net.iridgames.consolestocks.common;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
	
	
	protected final String command;
	
	protected String usage;
	
	protected String description = "No description has been given";
	
	protected boolean isCommandHidden;
	
	protected List<String> alias = new ArrayList<String>();
	
	public Command( String command ) {
		this.command = command;
		usage = command;
	}
	
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
	
	/** Get the description of the command */
	public String getDescription() {
		return this.description;
	}
	
	/** Set the description of the command */
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	/** Adds and alias */
	public void addAlias(String alias) {
		this.alias.add(alias);
	}
	
	public abstract void addTextToConsole(String text);
	
	public boolean isCommandHidden() {
		return isCommandHidden;
	}
	
	public void setHidden(boolean isHidden) {
		this.isCommandHidden = isHidden;
	}
}
