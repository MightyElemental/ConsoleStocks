package net.iridgames.consolestocks.server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
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

	public int	maxMemory;
	public int	totalMemory;
	public int	freeMemory;

	JProgressBar currentRam;

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

		currentRam = new JProgressBar();
		currentRam.setBounds(10, 11, 193, 37);
		panel.add(currentRam);

		JLabel lblCurrentRamUsage = new JLabel("Current RAM Usage");
		lblCurrentRamUsage.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentRamUsage.setBounds(10, 48, 193, 14);
		panel.add(lblCurrentRamUsage);
		setVisible(true);
		serverGUIThread.start();
	}

	public void update() {
		Runtime rt = Runtime.getRuntime();
		long maxMem = rt.maxMemory();
		long totalMem = rt.totalMemory();
		long freeMem = rt.freeMemory();

		maxMemory = (int) (maxMem / 1024);
		totalMemory = (int) (totalMem / 1024);
		freeMemory = (int) (freeMem / 1024);

		System.out.println(maxMemory + "KB | " + totalMemory + "KB | " + freeMemory + "KB");
		updateRAMBar();
	}

	public void updateRAMBar() {
		currentRam.setValue((int) (((freeMemory + 0.0) / (totalMemory + 0.0)) * 100.0));
	}
}
