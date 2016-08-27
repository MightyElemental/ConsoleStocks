package net.iridgames.stockAPI;

import java.util.ArrayList;
import java.util.List;

public class IStockListener {
	
	
	private List<StockUpdateListener> listeners = new ArrayList<StockUpdateListener>();
	
	public void onStockUpdated(Stock stock) {
		// notify listeners
		for (StockUpdateListener i : listeners) {
			i.onStockUpdate(stock, stock.getSymbol());
		}
	}
	
	public void addListener(StockUpdateListener sul) {
		listeners.add(sul);
	}
	
}
