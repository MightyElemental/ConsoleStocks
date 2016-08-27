package net.iridgames.consolestocks.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.iridgames.consolestocks.Calculators;
import net.iridgames.stockAPI.Stock;
import net.iridgames.stockAPI.StockFetcher;
import net.iridgames.stockAPI.StockUpdateListener;

/** @author WolfgangTS */

public class Stocks implements StockUpdateListener {
	
	
	private static Random random = new Random();
	
	/** Symbol, Stock */
	public Map<String, Stock> stockList = new HashMap<String, Stock>();
	
	public ArrayList<String> symbols = new ArrayList<String>();
	
	{
		symbols.add("FB");
		symbols.add("GOOG");
		symbols.add("AAPL");
		symbols.add("MSFT");
	}
	
	public Stocks() {
		// this.generateForest();
		StockFetcher.stockListener.addListener(this);
		this.loadStocks();
	}
	
	public void loadStocks() {
		for (String s : symbols) {
			stockList.put(s, StockFetcher.getStock(s));
		}
	}
	
	// @Deprecated
	// public void generateForest() {
	// int min = Integer.parseInt(Common.serverSettings.get("MINNUMOFSTOCKS"));
	// int max = Integer.parseInt(Common.serverSettings.get("MAXNUMOFSTOCKS"));
	// for (int i = 0; i < random.nextInt(max - min) + min; i++) {
	// stockList.add(StockOLD.generateRandom());
	// }
	// }
	
	public Stock getStock(String name) {
		for (int i = 0; i < stockList.size(); i++) {
			if (stockList.get(i).getName().equals(name.toUpperCase())) { return stockList.get(i); }
		}
		
		return null;
	}
	
	/** @author Mighty Elemental */
	@Deprecated
	public void updateValues() {
		for (int i = 0; i < stockList.size(); i++) {
			stockList.get(i).addValue((float) stockList.get(i).getPrice());
			float value = (float) stockList.get(i).getPrice();
			float randomPercent = (random.nextInt(200) - 100) / 10f;
			value += value * (randomPercent / 100);
			value = (float) Calculators.round(value, 2);
			stockList.get(i).setPrice(value);
			calculateValueIncrease(stockList.get(i));
		}
	}
	
	/** @author Mighty Elemental */
	public static float calculateValueIncrease(Stock stock) {
		if (stock.getPastValues().size() == 0) { return 0; }
		float valueOri = stock.getPastValues().get(0);
		float temp = (float) ((stock.getPrice() - valueOri) / valueOri * 100);
		// System.out.println(valueOri + " | " + stock.getValue());
		// System.out.println(temp + "% increase");
		return Calculators.round(temp, 2);
	}
	
	@Override
	public void onStockUpdate(Stock stock, String symbol) {
		stockList.get(symbol).update(stock);
	}
	
	public void updateStocks() {
		for (String s : symbols) {
			StockFetcher.stockListener.onStockUpdated(StockFetcher.getStock(s));
		}
	}
	
}
