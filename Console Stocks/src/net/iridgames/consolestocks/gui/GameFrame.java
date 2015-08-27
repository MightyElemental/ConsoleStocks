package net.iridgames.consolestocks.gui;

import javax.swing.JFrame;

@SuppressWarnings( "serial" )
@Deprecated
public class GameFrame extends JFrame {

	public TopLeftPanel		tlPanel;
	public TopRightPanel	trPanel;
	public BottomLeftPanel	blPanel;
	public ConsolePanel		brPanel;

	public GameFrame() {
		setTitle("That good game right there");
		getContentPane().setLayout(null);
		this.setSize(656, 422);

		tlPanel = new TopLeftPanel();
		tlPanel.setBounds(0, 0, 328, 211);
		add(tlPanel);

		trPanel = new TopRightPanel();
		trPanel.setBounds(328, 0, 328, 211);
		add(trPanel);

		blPanel = new BottomLeftPanel();
		blPanel.setBounds(0, 211, 328, 211);
		add(blPanel);

		brPanel = new ConsolePanel();
		brPanel.setBounds(328, 211, 328, 211);
		add(brPanel);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
