package net.iridgames.consolestocks.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.Helper;

public class Interface extends BasicGameState {

	private int[] panelNumbers = { PRender.PANEL_ACCOUNT_INFO, PRender.PANEL_CHAT, PRender.PANEL_STOCK_INFO,
			PRender.PANEL_CONSOLE };

	public PanelConsole pConsole = new PanelConsole();

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		Helper.setGameContainer(gc);
		Helper.setGraphics(gc.getGraphics());
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawLine(gc.getWidth() / 2, 0, gc.getWidth() / 2, gc.getHeight());
		g.drawLine(0, gc.getHeight() / 2, gc.getWidth(), gc.getHeight() / 2);
		for (int i = 0; i < 4; i++) {
			PRender.renderPanel(gc, sbg, g, panelNumbers[i], i);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
