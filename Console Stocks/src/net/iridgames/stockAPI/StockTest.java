package net.iridgames.stockAPI;

public class StockTest {
	
	
	public static void main(String[] args) {
		
		Stock facebook = StockFetcher.getStock("FB");
		System.out.println("Price: " + facebook.getPrice());
		System.out.println("Change Amount: " + facebook.getChangeAmount());
		System.out.println("Edit Date: " + facebook.getDateEdit());
		System.out.println("Time Change: "+facebook.getTimeEdit());
		System.out.println("ID: " + facebook.getID());
		System.out.println("Percentage Change: " + facebook.getPercentChange());
		System.out.println("Symbol: " + facebook.getSymbol());
	}
	
}
