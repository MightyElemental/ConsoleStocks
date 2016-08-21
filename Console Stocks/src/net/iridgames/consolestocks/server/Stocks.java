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
	
	/** @author Mighty Elemental */
	public void updateValues() {
		for (int i = 0; i < stockList.size(); i++) {
			stockList.get(i).addValue(stockList.get(i).getValue());
			float value = stockList.get(i).getValue();
			float randomPercent = (random.nextInt(200) - 100) / 10f;
			value += value * (randomPercent / 100);
			value = (float) Calculators.round(value, 2);
			stockList.get(i).setValue(value);
			calculateValueIncrease(stockList.get(i));
		}
	}
	
	/** @author Mighty Elemental */
	public static float calculateValueIncrease(Stock stock) {
		if (stock.getPastValues().size() == 0) { return 0; }
		float valueOri = stock.getPastValues().get(0);
		float temp = (stock.getValue() - valueOri) / valueOri * 100;
		// System.out.println(valueOri + " | " + stock.getValue());
		// System.out.println(temp + "% increase");
		return Calculators.round(temp, 2);
	}
}
