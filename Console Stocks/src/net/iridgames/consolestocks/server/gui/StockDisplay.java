package net.iridgames.consolestocks.server.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import net.iridgames.consolestocks.common.Common;
import net.iridgames.stockAPI.Stock;

@SuppressWarnings( "serial" )
public class StockDisplay extends JPanel {
	
	
	public Stock stock;
	
	public StockDisplay( Stock stock ) {
		this.stock = stock;
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	}
	
	final int tof = 14;
	DecimalFormat df = new DecimalFormat("#,##0.00");
	
	public void paint(Graphics gg) {
		super.paint(gg);
		if (stock == null) return;
		Graphics2D g = (Graphics2D) gg;
		g.setColor(Color.lightGray);
		g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
		g.setColor(Color.black);
		g.drawString("Stock: " + stock.getSymbol(), 4, tof);
		// int textX = g.getFontMetrics().stringWidth("Stock: " + stock.getName()) + 4;
		int len = g.getFontMetrics().stringWidth(Common.getCurrencySymbol() + df.format(stock.getPrice()));
		int x = getWidth() - len - 22;
		g.drawString(Common.getCurrencySymbol() + df.format(stock.getPrice()), getWidth() - len - 4, tof);
		drawShapes(g, x);
		drawStockPercent(g, len);
		g.setColor(Color.black);
		g.drawString(stock.getDateEdit() + ", " + stock.getTimeEdit(), 4, tof * 2);
		g.drawString("Updates: " + stock.getPastValues().size(), 4, tof * 3);
		g.drawString(stock.getName(), 4, tof * 4);
	}
	
	public void drawStockPercent(Graphics2D g, int len) {
		float percent = (float) stock.getPercentChange();// Stocks.calculateValueIncrease(stock);
		String s = df.format(percent) + "%";
		if (percent > 0) {
			s = "+" + s;
		}
		len = g.getFontMetrics().stringWidth(s);
		g.drawString(s, getWidth() - len - 4, tof * 2);
		
		if (stock.getChangeAmount() > 0) {
			s = Common.getCurrencySymbol() + "+" + stock.getChangeAmount();
		} else {
			s = Common.getCurrencySymbol() + stock.getChangeAmount();
		}
		len = g.getFontMetrics().stringWidth(s);
		g.drawString(s, getWidth() - len - 4, tof * 3);
	}
	
	public void drawShapes(Graphics2D g, int x) {
		Polygon loss = new Polygon(new int[] { 5 + x, 10 + x, 15 + x }, new int[] { 5, 15, 5 }, 3);
		Polygon gain = new Polygon(new int[] { 5 + x, 10 + x, 15 + x }, new int[] { 15, 5, 15 }, 3);
		try {
			if (stock.getPercentChange() > 0) {
				g.setColor(Color.green.darker());
				g.fillPolygon(gain);
			} else if (stock.getPercentChange() < 0) {
				g.setColor(Color.red);
				g.fillPolygon(loss);
			} else {
				g.setColor(Color.blue);
				g.fillOval(x + 4, 5, 8, 8);
			}
		} catch (Exception e) {
			g.setColor(Color.blue);
			g.fillOval(x + 4, 5, 8, 8);
		}
	}
	
}
