package net.iridgames.consolestocks;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {

	int i = 0;

	public void received(Connection connection, Object object) {
		if ( object instanceof String ) {
			System.out.println(object.toString());
			try {

				CSClient.userInterface.pConsole
						.addEntry(object.toString());
			} catch (Exception e) {
			}
			i++;
		}
	}

}
