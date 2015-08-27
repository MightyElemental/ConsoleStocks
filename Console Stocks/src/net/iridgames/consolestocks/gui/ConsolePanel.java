package net.iridgames.consolestocks.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings( "serial" )
@Deprecated
public class ConsolePanel extends JPanel {

	private static String		areaText	= "";
	private static JPanel		panel1		= new JPanel();
	private static JTextArea	textArea	= new JTextArea();
	private static JTextField	input		= new JTextField();
	private static JScrollPane	scrolling	= new JScrollPane();

	public ConsolePanel() {
		this.setLayout(new BorderLayout());
		textArea.setPreferredSize(new Dimension(328, 20));
		scrolling.setPreferredSize(new Dimension(328, 410));
		textArea.setEditable(false);
		scrolling.setHorizontalScrollBarPolicy(31);
		scrolling.setVerticalScrollBarPolicy(22);
		scrolling.setViewportView(textArea);
		this.add(scrolling, BorderLayout.NORTH);
		//input.addKeyListener(ConsoleStocks.input);
		panel1.setLayout(new BorderLayout());
		panel1.add(input, BorderLayout.WEST);
		this.add(panel1, BorderLayout.SOUTH);
	}

	public static void addText(String message) {
		String[] temp = message.split("");
		for (int i = 0; i < temp.length; i++) {
			areaText += temp[i];
			textArea.setText(areaText);
			try {
				Thread.sleep(20L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		areaText += "\n";
		textArea.setText(areaText);
		try {
			Thread.sleep(100L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		refresh();
	}

	public static void refresh() {
		int lines = areaText.split("\n").length;
		textArea.setPreferredSize(new Dimension(328, 20 + lines * 18));
	}

}
