package net.iridgames.consolestocks.server.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.server.Stock;
import net.mightyelemental.network.Server;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings( "serial" )
public class ServerFrame extends net.mightyelemental.network.gui.ServerGUI {
	
	
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	
	JPanel panel_3;
	public JPanel stockScrollPaneInside;
	
	public List<StockDisplay> stockDisplays = new ArrayList<StockDisplay>();
	private JScrollPane scrollPane_1;
	
	/** Create the frame. */
	public ServerFrame( Server server, String IPAddress ) {
		super(Common.serverSettings.get("SERVERNAME"), server, IPAddress);
		addComponentListener(new ComponentAdapter() {
			
			
			@Override
			public void componentResized(ComponentEvent e) {
				int height = getHeight();
				if (getHeight() < 328) {
					height = 328;
				}
				setSize(new Dimension(700, height));// Force window to be certain width
				super.componentResized(e);
			}
			
		});
		setBounds(100, 100, 700, 387);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 177, 233, 171);
		contentPane.add(panel);
		panel.setLayout(null);
		
		clientList.setBounds(10, 24, 213, 137);
		panel.add(clientList);
		
		lblClients = new JLabel("Clients");
		lblClients.setHorizontalAlignment(SwingConstants.CENTER);
		lblClients.setBounds(0, 4, 233, 14);
		panel.add(lblClients);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(253, 177, 206, 171);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblServerDetails = new JLabel("Server Details");
		lblServerDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblServerDetails.setBounds(0, 0, 206, 21);
		panel_1.add(lblServerDetails);
		
		JLabel lblServerIp = new JLabel("Server IP: ");
		lblServerIp.setBounds(10, 32, 62, 21);
		panel_1.add(lblServerIp);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField.setBounds(72, 32, 124, 21);
		panel_1.add(textField);
		textField.setEditable(false);
		textField.setText(IPAddress);
		textField.setColumns(10);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(10, 11, 449, 155);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		commands.setBounds(10, 23, 429, this.getHeight() - (387 - 121));
		panel_2.add(commands);
		
		JLabel lblClientCommands = new JLabel("Console");
		lblClientCommands.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientCommands.setBounds(10, 2, 429, 14);
		panel_2.add(lblClientCommands);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(469, 11, 205, 337);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblStocks = new JLabel("Stocks");
		lblStocks.setHorizontalAlignment(SwingConstants.CENTER);
		lblStocks.setBounds(10, 0, 185, 14);
		panel_3.add(lblStocks);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(0, 25, 195, 301);
		panel_3.add(scrollPane_1);
		
		stockScrollPaneInside = new JPanel();
		updateStocks();
		scrollPane_1.setViewportView(stockScrollPaneInside);
		scrollPane_1.getViewport().setPreferredSize(new Dimension(195, 301));
		stockScrollPaneInside.setLayout(null);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		try {
			
		} catch (Exception e) {
		}
		try {
			panel_3.repaint();
			commands.setBounds(10, 23, 429, this.getHeight() - (387 - 121));
			panel_2.setBounds(10, 11, 449, this.getHeight() - (387 - 155));
			panel_1.setBounds(253, 177 + (this.getHeight() - 387), 206, 171);
			panel.setBounds(10, 177 + (this.getHeight() - 387), 233, 171);
			scrollPane_1.setBounds(0, 25, 195, this.getHeight() - 80);
			panel_3.setBounds(469, 11, 205, this.getHeight() - 50);
		} catch (Exception e) {
		}
	}
	
	public void updateStocks() {
		if (stockDisplays.size() != ConsoleStocks.serverParser.stocks.stockList.size()) {
			stockDisplays.clear();
			int i = 0;
			for (Stock s : ConsoleStocks.serverParser.stocks.stockList) {
				StockDisplay sd = new StockDisplay(s);
				sd.setBounds(5, i * 52 + 6, 160, 50);
				stockDisplays.add(sd);
				stockScrollPaneInside.add(stockDisplays.get(i));
				i++;
			}
			stockScrollPaneInside.setPreferredSize(new Dimension(stockScrollPaneInside.getWidth(), i * 52 + 6));
		}
		for (StockDisplay s : stockDisplays) {
			s.repaint();
		}
	}
	
	public void addCommand(String command) {
		commands.add(command);
		this.repaint();
		commands.select(commands.getItemCount() - 1);
	}
}
