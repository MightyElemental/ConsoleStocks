package net.iridgames.consolestocks.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.iridgames.consolestocks.Calculators;
import net.iridgames.consolestocks.ConsoleStocks;

/** @author WolfgangTS */
@Deprecated
public class StockOLD {
	
	private static Random random = new Random();
	
	private String name;
	private float value;
	
	protected List<Float> pastValues = new ArrayList<Float>();
	
	public StockOLD( String name, float value ) {
		this.setName(name);
		this.setValue(value);
	}
	
	public StockOLD( String name ) {
		// Name, Value
		this(name, (float) (Calculators.round(random.nextFloat() * 100 + 1, 2)));
	}
	
	public static StockOLD generateRandom() {
		return new StockOLD(generateCharacters(5));
	}
	
	public static String generateCharacters(int n) {
		String characters = "";
		
		for (int i = 0; i < n; i++) {
			characters += (char) (ConsoleStocks.rand.nextInt(26) + 'a');
		}
		
		return characters.toUpperCase();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	public List<Float> getPastValues() {
		return pastValues;
	}
	
	public void addValue(float value) {
		this.pastValues.add(value);
	}
}
