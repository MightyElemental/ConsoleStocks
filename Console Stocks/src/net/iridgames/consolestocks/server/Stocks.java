package net.iridgames.consolestocks.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.iridgames.consolestocks.Helper;
import net.iridgames.stockAPI.Stock;
import net.iridgames.stockAPI.StockFetcher;
import net.iridgames.stockAPI.StockUpdateListener;

/** @author Mighty Elemental */

public class Stocks implements StockUpdateListener {
	
	
	private static Random random = new Random();
	
	/** Symbol, Stock */
	public Map<String, Stock> stockList = new HashMap<String, Stock>();
	
	public ArrayList<String> symbols = new ArrayList<String>();
	
	{
		symbols.add("FB:Facebook");
		symbols.add("GOOG:Google");
		symbols.add("AAPL:Apple");
		symbols.add("MSFT:Microsoft");
		symbols.add("MSFT:Microsoft");
		symbols.add("GBP:Global Petroleum Limited");
		symbols.add("ATVI:Activision");
		symbols.add("GOLD:Randgold Resources");
	}
	
	public Stocks() {
		// this.generateForest();
		StockFetcher.stockListener.addListener(this);
		this.loadStocks();
	}
	
	public void loadStocks() {
		for (String s : symbols) {
			stockList.put(s.split(":")[0], StockFetcher.getStock(s.split(":")[0], s.split(":")[1]));
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
		if (stockList.containsKey(name.toUpperCase())) { return stockList.get(name.toUpperCase()); }
		return null;
	}
	
	@Deprecated
	public void updateValues() {
		for (int i = 0; i < stockList.size(); i++) {
			stockList.get(i).addValue((float) stockList.get(i).getPrice());
			float value = (float) stockList.get(i).getPrice();
			float randomPercent = (random.nextInt(200) - 100) / 10f;
			value += value * (randomPercent / 100);
			value = (float) Helper.round(value, 2);
			stockList.get(i).setPrice(value);
			calculateValueIncrease(stockList.get(i));
		}
	}
	
	public static float calculateValueIncrease(Stock stock) {
		if (stock.getPastValues().size() == 0) { return 0; }
		float valueOri = stock.getPastValues().get(0);
		float temp = (float) ((stock.getPrice() - valueOri) / valueOri * 100);
		// System.out.println(valueOri + " | " + stock.getValue());
		// System.out.println(temp + "% increase");
		return Helper.round(temp, 2);
	}
	
	@Override
	public void onStockUpdate(Stock stock) {
		stockList.get(stock.getSymbol()).update(stock);
	}
	
	public void updateStocks() {
		for (String s : symbols) {
			StockFetcher.stockListener.onStockUpdated(StockFetcher.getStock(s.split(":")[0], s.split(":")[1]));
		}
	}
	
}
