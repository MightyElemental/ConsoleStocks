package net.iridgames.consolestocks.server;

import java.util.Random;

public class Stock
{
	private static Random random = new Random();
	
	private String name;
	private float value;
	
	public Stock(String name, float value)
	{
		this.setName(name);
		this.setValue(value);
	}
	
	public Stock(String name)
	{
		this(name, random.nextFloat()*100+1);
	}
	
	public static Stock generateRandom()
	{
		return new Stock(generateCharacters(5));
	}
	
	public static String generateCharacters(int n)
	{
		String characters = "";
		
		for (int i = 0; i < n; i++)
		{
			characters+=(char)(random.nextInt(26) + 'a');
		}
		
		return characters.toUpperCase();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public float getValue()
	{
		return value;
	}

	public void setValue(float value)
	{
		this.value = value;
	}
}
