package net.iridgames.consolestocks.server;

import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.iridgames.consolestocks.ConsoleStocks;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

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

	private JPanel		contentPane;
	private JTextPane	connectedClients;

	/** Create the frame. */
	public ServerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 363, 213);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 347, 175);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 11, 180, 52);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblServerInfo = new JLabel("Server Info");
		lblServerInfo.setBounds(0, 0, 82, 14);
		panel_1.add(lblServerInfo);

		JLabel lblServerIp = new JLabel("Server IP: " + Server.getExternalIPAddress() + ":" + ConsoleStocks.port);
		lblServerIp.setBounds(0, 25, 180, 14);
		panel_1.add(lblServerIp);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(200, 25, 137, 139);
		panel.add(scrollPane);

		connectedClients = new JTextPane();
		connectedClients.setEditable(false);
		scrollPane.setViewportView(connectedClients);

		JLabel lblClientsOnThis = new JLabel("Clients on this server");
		lblClientsOnThis.setBounds(200, 11, 137, 14);
		panel.add(lblClientsOnThis);
		setVisible(true);
		serverGUIThread.start();
	}

	public void update() {
		String text = "";
		if (ConsoleStocks.server != null) {
			for (String user : ConsoleStocks.server.attachedClients.keySet()) {
				text += user + "\n";
				text += "-" + ((InetAddress) (ConsoleStocks.server.attachedClients.get(user).get(0))).getHostName() + ":"
						+ ConsoleStocks.server.attachedClients.get(user).get(1) + "\n";
			}
		}
		connectedClients.setText(text);
	}
}
