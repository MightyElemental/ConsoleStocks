package net.iridgames.consolestocks.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.CSClient;

public class PRender {

	public static final int	PANEL_CONSOLE		= 0;
	public static final int	PANEL_ACCOUNT_INFO	= 1;
	public static final int	PANEL_CHAT			= 2;
	public static final int	PANEL_STOCK_INFO	= 3;

	public static void renderPanel(GameContainer gc, StateBasedGame sbg, Graphics g, int pNum, int panel)
			throws SlickException {
		Point p = getPointFromPanelNum(gc, panel);
		switch (pNum) {
		case PANEL_CONSOLE:
			renderConsole(gc, sbg, g, (int) p.getX(), (int) p.getY());
			break;
		default:
			renderConsole(gc, sbg, g, (int) p.getX(), (int) p.getY());
		}
	}

	/**
	 * Used to convert a panel number (0-3) into an x,y position.<br>
	 * 0 - top left<br>
	 * 1 - top right<br>
	 * 2 - bottom left<br>
	 * 3 - bottom right<br>
	 * 
	 * @return Point - the x,y position of the panel
	 */
	private static Point getPointFromPanelNum(GameContainer gc, int panel) {
		switch (panel) {
		case 0:
			return new Point(0, 0);
		case 1:
			return new Point(gc.getWidth() / 2, 0);
		case 2:
			return new Point(0, gc.getHeight() / 2);
		case 3:
			return new Point(gc.getWidth() / 2, gc.getHeight() / 2);
		}
		return new Point(0, 0);
	}

	private static void renderConsole(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y)
			throws SlickException {
		CSClient.userInterface.pConsole.render(gc, sbg, g, x, y);
	}

}
