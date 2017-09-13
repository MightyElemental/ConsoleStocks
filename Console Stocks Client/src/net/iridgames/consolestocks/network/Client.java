package net.iridgames.consolestocks.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import net.iridgames.consolestocks.CSClient;
import net.iridgames.consolestocks.ClientListener;

public class Client implements Runnable {

	Socket	clientSocket;
	Thread	updateThread	= new Thread(this);

	BufferedReader	br;
	BufferedWriter	bw;

	List<ClientListener> listeners;

	public Client() {
		listeners = new ArrayList<ClientListener>();
	}

	public void connect(int timeout, String address, int port) throws IOException {
		clientSocket = new Socket(address, port);
		clientSocket.setSoTimeout(timeout);
		clientSocket.setKeepAlive(true);
		// clientSocket.connect(new InetSocketAddress(address, port), timeout);
		br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		bw = new BufferedWriter(new PrintWriter(clientSocket.getOutputStream()));
	}

	public void start() {

	}

	public void send(String message) {
		// TODO add protocol

		try {
			// message+=" | \u30AB\u30BF\u30AB\u30CA";
			System.out.println(message);
			clientSocket.getOutputStream().write(message.getBytes("UTF-8"));// Converts to UTF-8 and sends the bytes
		} catch (NullPointerException e) {
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addListener(ClientListener cListen) {
		listeners.add(cListen);
	}

	@Override
	public void run() {
		while (CSClient.running) {
			try {
				String text = br.readLine();
				for ( ClientListener l : listeners ) {
					l.received(text);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
