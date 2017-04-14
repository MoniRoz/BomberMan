package com.rozwadowska.bomberman.main;

import javax.swing.SwingUtilities;

import com.rozwadowska.bomberman.game.GameData;
import com.rozwadowska.bomberman.game.GameRenderer;
import com.rozwadowska.bomberman.gui.GameWindow;

/**
 * Głowna klasa wywolujaca rysowanie okna, w ktorym odbywac sie bedzie gra
 * 
 * @author Monika
 *
 */
public class Main {
	/**
	 * Rozmiar okna w zaleznosci od wielkosci planszy oraz wielkosci planszy
	 */
	public static final int WIDTH = GameData.BOARD_WIDTH * GameRenderer.FIELD_SIZE;
	public static final int HEIGHT = GameData.BOARD_HEIGHT * GameRenderer.FIELD_SIZE;

	/**
	 * Wywolanie metody rysowania okna gry
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameWindow.createAndShowGUI();
			}
		});
	}
}
