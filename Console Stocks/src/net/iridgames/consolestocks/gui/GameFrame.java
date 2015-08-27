package net.iridgames.consolestocks.gui;

import javax.swing.JFrame;

@SuppressWarnings( "serial" )
public class GameFrame extends JFrame {

	public GameFrame() {
		setTitle("Dat gud game rit dere");
		getContentPane().setLayout(null);
		this.setVisible(true);

		TopLeftPanel tlPanel = new TopLeftPanel();
		tlPanel.setBounds(0, 0, 328, 211);
		getContentPane().add(tlPanel);

		TopRightPanel trPanel = new TopRightPanel();
		trPanel.setBounds(328, 0, 328, 211);
		getContentPane().add(trPanel);

		BottomLeftPanel blPanel = new BottomLeftPanel();
		blPanel.setBounds(0, 211, 328, 211);
		getContentPane().add(blPanel);

		ConsolePanel brPanel = new ConsolePanel();
		brPanel.setBounds(328, 211, 328, 211);
		getContentPane().add(brPanel);
	}
}
