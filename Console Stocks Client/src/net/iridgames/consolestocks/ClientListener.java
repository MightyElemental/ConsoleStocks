package net.iridgames.consolestocks;

public class ClientListener {

	int i = 0;

	public void received(String object) {
		System.out.println(object.toString());
		try {
			CSClient.userInterface.pConsole.addEntry(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		i++;
	}

}
