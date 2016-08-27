package net.iridgames.stockAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class StockFetcher {
	
	
	public static IStockListener stockListener = new IStockListener();
	
	/** Returns a Stock Object that contains info about a specified stock.
	 * 
	 * @param symbol
	 *            the company's stock symbol
	 * @return a stock object containing info about the company's stock
	 * @see Stock */
	public static Stock getStock(String symbol) {
		String sym = symbol.toUpperCase();
		double price = 0.0;
		double percentChange = 0.0;
		double changeAmount = 0.0;
		String timeEdit = "";
		String dateEdit = "";
		long ID = 0;
		String name = sym;
		try {
			
			// Retrieve JSON String From Google Finance
			URL googleStocks = new URL("https://www.google.com/finance/info?q=NASDAQ%3a" + sym);
			URLConnection connection = googleStocks.openConnection();
			InputStreamReader is = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(is);
			
			String s = "";
			String line = "";
			
			while ((s = br.readLine()) != null) {
				line += s;
			}
			line = line.replaceAll("// ", "");
			// System.out.println(line);
			
			// Convert to JSON
			JSONArray j = (JSONArray) JSONValue.parse(line);
			JSONObject settings = ((JSONObject) j.get(0));
			
			// Save Data
			
			price = StockHelper.handleDouble(settings.get("l") + "");
			percentChange = StockHelper.handleDouble(settings.get("cp_fix") + "");
			changeAmount = StockHelper.handleDouble(settings.get("c_fix") + "");
			timeEdit = settings.get("elt").toString().split(",")[1].replace("EDT", "");
			dateEdit = settings.get("elt").toString().split(",")[0];
			ID = Long.parseLong(settings.get("id") + "");
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new Stock(sym, price, percentChange, changeAmount, timeEdit, dateEdit, ID, name);
		
	}
}
