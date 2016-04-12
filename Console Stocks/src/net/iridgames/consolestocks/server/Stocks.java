package net.iridgames.consolestocks.server;

import java.util.ArrayList;
import java.util.Random;

import net.iridgames.consolestocks.Calculators;
import net.iridgames.consolestocks.common.Common;

/** @author WolfgangTS */
public class Stocks {
	
	
	private static Random random = new Random();
	
	public ArrayList<Stock> stockList = new ArrayList<Stock>();
	
	public Stocks() {
		this.generateForest();
	}
	
	public void generateForest() {
		int min = Integer.parseInt(Common.serverSettings.get("MINNUMOFSTOCKS"));
		int max = Integer.parseInt(Common.serverSettings.get("MAXNUMOFSTOCKS"));
		for (int i = 0; i < random.nextInt(max - min) + min; i++) {
			stockList.add(Stock.generateRandom());
		}
	}
	
	public Stock getStock(String name) {
		for (int i = 0; i < stockList.size(); i++) {
			if (stockList.get(i).getName().equals(name.toUpperCase())) { return stockList.get(i); }
		}
		
		return null;
	}
	
	public void updateValues() {
		for (int i = 0; i < stockList.size(); i++) {
			float value = stockList.get(i).getValue();
			float randomPercent = (random.nextInt(200) - 100) / 10f;
			value += value * (randomPercent / 100);
			value = (float) Calculators.round(value, 2);
			stockList.get(i).setValue(value);
			if (i == 0) {
				System.out.println(stockList.get(0).getValue());
				System.out.println(randomPercent + "%");
			}
		}
	}
}
