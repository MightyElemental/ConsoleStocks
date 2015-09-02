package net.iridgames.consolestocks.server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.iridgames.consolestocks.ConsoleStocks;

public class ServerGUI extends JFrame {

	private static final long serialVersionUID = -1053462912348822726L;

	private Thread serverGUIThread = new Thread("ServerGUIThread") {

		public void run() {
			while (ConsoleStocks.server.running) {
				update();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	private JPanel contentPane;

	/** Create the frame. */
	public ServerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 608, 374);
		contentPane.add(panel);
		panel.setLayout(null);
		setVisible(true);
		serverGUIThread.start();
	}

	public void update() {

	}
}
