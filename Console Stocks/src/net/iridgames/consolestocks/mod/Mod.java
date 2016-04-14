package net.iridgames.consolestocks.mod;

public abstract class Mod {
	
	
	protected String name;
	
	public Mod( String name ) {
		this.name = name;
	}
	
	public abstract void init();
	
}
