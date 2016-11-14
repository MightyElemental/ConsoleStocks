package net.iridgames.consolestocks.client.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.ResourceLoader;
import net.iridgames.consolestocks.common.Common;

public class StateGame extends BasicGameState {
	
	
	public Sound noot;
	
	private final int ID;
	
	public Console console = new Console();
	
	public float ticks;
	
	public StateGame( int id ) {
		this.ID = id;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		ConsoleStocks.stateGame.noot = ResourceLoader.loadSound("noot");
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		this.renderBL(gc, sbg, g);
		this.renderBR(gc, sbg, g);
		this.renderTL(gc, sbg, g);
		this.renderTR(gc, sbg, g);
		g.setColor(Color.white);
		int w = gc.getWidth() / 2;
		int h = gc.getHeight() / 2;
		g.drawRect(0, 0, gc.getWidth() / 2, gc.getHeight() / 2);
		g.drawRect(w - 1, 0, gc.getWidth() / 2, gc.getHeight() / 2);
		g.drawRect(0, h - 1, gc.getWidth() / 2, gc.getHeight() / 2);
		g.drawRect(w - 1, h - 1, gc.getWidth() / 2, gc.getHeight() / 2);
	}
	
	public void renderTL(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = 0;
		final int yDisp = 0;
		PRender.renderPanel(PRender.ACCOUNT_INFO, gc, sbg, g, xDisp, yDisp);
		// if (console.keyCodePressed >= 0) {
		// g.drawString(Input.getKeyName(console.keyCodePressed), xDisp + 10, yDisp + 10);
		// }
		renderPanelNumber(g, gc, 1, xDisp, yDisp);
	}
	
	public void renderTR(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = gc.getWidth() / 2;
		final int yDisp = 0;
		PRender.renderPanel(PRender.CLIENT_INFO, gc, sbg, g, xDisp, yDisp);
		renderPanelNumber(g, gc, 2, xDisp, yDisp);
	}
	
	public void renderBR(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = gc.getWidth() / 2;
		final int yDisp = gc.getHeight() / 2;
		PRender.renderPanel(PRender.CONSOLE, gc, sbg, g, xDisp, yDisp);
		renderPanelNumber(g, gc, 4, xDisp, yDisp);
	}
	
	public void renderBL(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = 0;
		final int yDisp = gc.getHeight() / 2;
		PRender.renderPanel(PRender.SERVER_INFO, gc, sbg, g, xDisp, yDisp);
		renderPanelNumber(g, gc, 3, xDisp, yDisp);
	}
	
	public void renderPanelNumber(Graphics g, GameContainer gc, int number, int x, int y) {
		if (Common.clientConfig.getVal("SHOWPANELNUMBERS").equals("false")) { return; }
		float xD = x + gc.getWidth() / 2f;
		float yD = y + gc.getHeight() / 2f;
		g.setColor(new Color(100, 100, 100, 0.5f));
		g.fillRoundRect(xD - 26, yD - 28, 20, 23, 5);
		g.drawString(number + "", xD - 21, yD - 25);
		g.setColor(new Color(255, 255, 255, 1f));
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		ticks += delta / 17;
		console.lControlDown = gc.getInput().isKeyDown(Input.KEY_LCONTROL);
		console.updateCommandLine(ticks);
		ctrlDown = gc.getInput().isKeyDown(29);
	}
	
	@Override
	public int getID() {
		return ID;
	}
	
	boolean ctrlDown = false;
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		console.keyCodePressed = key;
		console.keyPressedTime = System.currentTimeMillis();
		console.keyChar = c;
		console.processCommandLineInput();
		if (key == Input.KEY_V && ctrlDown) {
			console.onPaste();
		}
		if (key == Input.KEY_C && ctrlDown) {
			console.onCopy();
		}
	}
	
	@Override
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		console.keyCodePressed = -1;
		console.keyPressedTime = -1;
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// TODO Auto-generated method stub
		super.mouseClicked(button, x, y, clickCount);
	}
	
	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		super.mouseReleased(button, x, y);
	}
	
	@Override
	public void mouseWheelMoved(int newValue) {
		// TODO Auto-generated method stub
		super.mouseWheelMoved(newValue);
	}
	
}
