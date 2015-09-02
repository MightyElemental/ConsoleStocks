package net.iridgames.consolestocks.server;

import java.util.ArrayList;
import java.util.Random;

public class Stocks
{
	private static Random random = new Random();
	
	public ArrayList<Stock> stockList = new ArrayList<Stock>();

	public Stocks()
	{
		this.generateForest();
	}
	
	public void generateForest()
	{
		for (int i = 0; i < random.nextInt(300)+100; i++)
		{
			stockList.add(Stock.generateRandom());
		}
	}
	
	public Stock getStock(String name)
	{
		for (int i = 0; i < stockList.size(); i++)
		{
			if(stockList.get(i).getName().equals(name))
			{
				return stockList.get(i);
			}
		}
		
		return null;
	}
	
	public static void progressDay()
	{
		
	}
}
