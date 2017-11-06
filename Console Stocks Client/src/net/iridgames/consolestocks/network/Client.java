package net.iridgames.consolestocks.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import net.iridgames.consolestocks.ClientListener;

public class Client implements Runnable {

	private Socket	clientSocket;
	private Thread	updateThread	= new Thread(this);
	private boolean	running			= true;

	InputStream		is;
	OutputStream	os;

	List<ClientListener> listeners;

	public Client() {
		listeners = new ArrayList<ClientListener>();
	}

	public void connect(int timeout, String address, int port) throws IOException {
		clientSocket = new Socket(address, port);
		clientSocket.setSoTimeout(timeout);
		clientSocket.setKeepAlive(true);
		// clientSocket.connect(new InetSocketAddress(address, port), timeout);
		is = clientSocket.getInputStream();
		os = clientSocket.getOutputStream();
	}

	public void start() {
		updateThread.start();
	}

	public void send(String message) {
		// TODO add protocol

		try {
			// message+=" | \u30AB\u30BF\u30AB\u30CA";
			System.out.println(message);
			os.write(message.getBytes("UTF-8"));// Converts to UTF-8 and sends the bytes
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
		while (running) {

			try {
				byte[] bytes = new byte[256];
				is.read(bytes);
				String text = new String(bytes).trim();
				for ( ClientListener l : listeners ) {
					l.received(text);
				}
			} catch (SocketException se) {
				running = false;
			} catch (SocketTimeoutException ste) {
				System.out.println("Timeout");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		for ( ClientListener l : listeners ) {
			l.onDisconnect(-1);
		}
	}
}
