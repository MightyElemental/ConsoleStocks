package net.iridgames.consolestocks.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;

public class StateGame extends BasicGameState {

	private final int ID;

	public Console console = new Console();

	public StateGame( int id ) {
		this.ID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub

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
		if (console.keyCodePressed >= 0) {
			g.drawString(Input.getKeyName(console.keyCodePressed), xDisp + 10, yDisp + 10);
		}
	}

	public void renderTR(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = gc.getWidth() / 2;
		final int yDisp = 0;
		console.renderConsole(gc, sbg, g, xDisp, yDisp);
	}

	public void renderBR(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = gc.getWidth() / 2;
		final int yDisp = gc.getHeight() / 2;
		console.renderConsole(gc, sbg, g, xDisp, yDisp);
	}

	public void renderBL(GameContainer gc, StateBasedGame sbg, Graphics g) {
		final int xDisp = 0;
		final int yDisp = gc.getHeight() / 2;
		console.renderConsole(gc, sbg, g, xDisp, yDisp);
	}

	public float ticks;

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		ticks += delta / 17;
		console.updateCommandLine(ticks);
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		console.keyCodePressed = key;
		console.keyPressedTime = System.currentTimeMillis();
		console.keyChar = c;
		console.processCommandLineInput();
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
