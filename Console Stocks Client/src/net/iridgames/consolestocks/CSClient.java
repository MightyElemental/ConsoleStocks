package net.iridgames.consolestocks;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

public class CSClient implements Runnable {

	Thread mainThread = new Thread(this);

	ClientListener cListen = new ClientListener();

	Client client;

	public boolean running;

	public CSClient() {
		running = true;
		mainThread.start();
		client = new Client();
		client.start();
		try {
			client.connect(5000, "localhost", 4040);
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.addListener(cListen);
		client.sendTCP("Hello");
	}

	public static void main(String[] args) {
		new CSClient();
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			client.sendTCP("Hello");
		}
	}

}
