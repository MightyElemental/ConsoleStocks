package net.iridgames.stockAPI;

import java.util.ArrayList;
import java.util.List;

public class Stock {
	
	
	private String symbol;
	private double price;
	private double percentChange;
	private double changeAmount;
	private String timeEdit;
	private String dateEdit;
	private long ID;
	private String name;
	
	protected List<Float> pastValues = new ArrayList<Float>();
	
	public Stock( String sym, double price, double percentChange, double changeAmount, String timeEdit, String dateEdit, long ID,
			String name ) {
		this.symbol = sym;
		this.price = price;
		this.percentChange = percentChange;
		this.changeAmount = changeAmount;
		this.timeEdit = timeEdit;
		this.dateEdit = dateEdit;
		this.ID = ID;
		this.name = name;
	}
	
	public String getSymbol() {
		return this.symbol;
	}
	
	public float getPrice() {
		return (float) this.price;
	}
	
	public void addValue(float value) {
		this.pastValues.add(value);
	}
	
	public List<Float> getPastValues() {
		return pastValues;
	}
	
	public double getPercentChange() {
		return percentChange;
	}
	
	public double getChangeAmount() {
		return changeAmount;
	}
	
	public String getTimeEdit() {
		return timeEdit;
	}
	
	public String getDateEdit() {
		return dateEdit;
	}
	
	public long getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	/** Updates stock with information from another stock */
	public void update(Stock stock) {
		if (!this.timeEdit.equals(stock.timeEdit) || !this.dateEdit.equals(stock.dateEdit)) {
			pastValues.add((float) this.price);
			this.price = stock.price;
			this.percentChange = stock.percentChange;
			this.changeAmount = stock.changeAmount;
			this.timeEdit = stock.timeEdit;
			this.dateEdit = stock.dateEdit;
		}
	}
}
