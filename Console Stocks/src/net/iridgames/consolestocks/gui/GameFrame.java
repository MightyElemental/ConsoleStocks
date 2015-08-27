package net.iridgames.consolestocks.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings( "serial" )
public class GameFrame extends JFrame{
	public GameFrame() {
		setTitle("Dat gud game rit dere");
		getContentPane().setLayout(null);
		
		JPanel tlPanel = new JPanel();
		tlPanel.setBounds(0, 0, 328, 211);
		getContentPane().add(tlPanel);
		
		JPanel trPanel = new JPanel();
		trPanel.setBounds(328, 0, 328, 211);
		getContentPane().add(trPanel);
		
		JPanel blPanel = new JPanel();
		blPanel.setBounds(0, 211, 328, 211);
		getContentPane().add(blPanel);
		
		JPanel brPanel = new JPanel();
		brPanel.setBounds(328, 211, 328, 211);
		getContentPane().add(brPanel);
	}
}
