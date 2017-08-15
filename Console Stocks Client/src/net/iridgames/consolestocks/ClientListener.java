package net.iridgames.consolestocks;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {

	public void received(Connection connection, Object object) {
		System.out.println(object.toString());
	}

}
