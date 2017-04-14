package com.rozwadowska.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa odpowiedzialna za odczytawanie i przekawyanie informacji o wcisnietych
 * klawiszach do klasy UserInput
 * 
 * @author Monika
 *
 */
public class GameKeyListener implements KeyListener {

	/**
	 * Metoda odpowiadajaca za reakcje po wcisnieciu przycisku
	 * 
	 * Gracz 0:
	 * poruszanie do gory VK_W
	 * porusznanie do dolu VK_S
	 * poruszanie w lewo VK_A
	 * poruszanie w prawo VK_D
	 * stawianie dynamitu VK_CONTROL
	 * 
	 * Gracz 1:
	 * poruszanie do gory VK_UP
	 * porusznanie do dolu VK_DOWN
	 * poruszanie w lewo VK_LEFT
	 * poruszanie w prawo VK_RIGHT
	 * stawianie dynamitu VK_SPACE
	 * 
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			UserInput.setUp(true, 1);
			break;
		case KeyEvent.VK_DOWN:
			UserInput.setDown(true, 1);
			break;
		case KeyEvent.VK_LEFT:
			UserInput.setLeft(true, 1);
			break;
		case KeyEvent.VK_RIGHT:
			UserInput.setRight(true, 1);
			break;
		case KeyEvent.VK_SPACE:
			UserInput.setAction(true, 1);
			break;
		case KeyEvent.VK_W:
			UserInput.setUp(true, 0);
			break;
		case KeyEvent.VK_S:
			UserInput.setDown(true, 0);
			break;
		case KeyEvent.VK_A:
			UserInput.setLeft(true, 0);
			break;
		case KeyEvent.VK_D:
			UserInput.setRight(true, 0);
			break;
		case KeyEvent.VK_CONTROL:
			UserInput.setAction(true, 0);
			break;
		}
	}

	/**
	 * Metoda odpowiadajaca za reakcje po wycisnieciu przycisku
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			UserInput.setUp(false, 1);
			break;
		case KeyEvent.VK_DOWN:
			UserInput.setDown(false, 1);
			break;
		case KeyEvent.VK_LEFT:
			UserInput.setLeft(false, 1);
			break;
		case KeyEvent.VK_RIGHT:
			UserInput.setRight(false, 1);
			break;
		case KeyEvent.VK_SPACE:
			UserInput.setAction(false, 1);
			break;
		case KeyEvent.VK_W:
			UserInput.setUp(false, 0);
			break;
		case KeyEvent.VK_S:
			UserInput.setDown(false, 0);
			break;
		case KeyEvent.VK_A:
			UserInput.setLeft(false, 0);
			break;
		case KeyEvent.VK_D:
			UserInput.setRight(false, 0);
			break;
		case KeyEvent.VK_CONTROL:
			UserInput.setAction(false, 0);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
