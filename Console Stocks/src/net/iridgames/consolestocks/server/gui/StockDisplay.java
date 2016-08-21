package net.iridgames.consolestocks.server.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.server.Stock;
import net.iridgames.consolestocks.server.Stocks;

@SuppressWarnings( "serial" )
public class StockDisplay extends JPanel {
	
	
	public Stock stock;
	
	public StockDisplay( Stock stock ) {
		this.stock = stock;
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	}
	
	final int tof = 14;
	
	public void paint(Graphics gg) {
		super.paint(gg);
		Graphics2D g = (Graphics2D) gg;
		g.setColor(Color.lightGray);
		g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
		g.setColor(Color.black);
		g.drawString("Stock: " + stock.getName(), 4, tof);
		// int textX = g.getFontMetrics().stringWidth("Stock: " + stock.getName()) + 4;
		int len = g.getFontMetrics().stringWidth(Common.getCurrencySymbol() + stock.getValue());
		int x = getWidth() - len - 22;
		g.drawString(Common.getCurrencySymbol() + stock.getValue(), getWidth() - len - 4, tof);
		drawShapes(g, x);
		
		float percent = Stocks.calculateValueIncrease(stock);
		String s = percent + "%";
		if (percent > 0) {
			s = "+" + s;
		}
		len = g.getFontMetrics().stringWidth(s);
		g.drawString(s, getWidth() - len - 4, tof * 2);
	}
	
	public void drawShapes(Graphics2D g, int x) {
		Polygon loss = new Polygon(new int[] { 5 + x, 10 + x, 15 + x }, new int[] { 5, 15, 5 }, 3);
		Polygon gain = new Polygon(new int[] { 5 + x, 10 + x, 15 + x }, new int[] { 15, 5, 15 }, 3);
		try {
			if (stock.getPastValues().get(0) < stock.getValue()) {
				g.setColor(Color.green.darker());
				g.fillPolygon(gain);
			} else if (stock.getPastValues().get(0) > stock.getValue()) {
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
